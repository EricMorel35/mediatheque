# mediatheque

Application de gestion de médiathèque personnelle (films) : scan/import depuis TheMovieDB (TMDB), stockage en base MySQL (via Docker Compose), consultation via une webapp Spring Boot.

## Structure du projet

Projet Maven multi-module. `mediatheque-parent` est un pom agrégateur (packaging `pom`) mais **n'est hérité par aucun module enfant** — chaque module pointe directement vers `spring-boot-starter-parent` comme `<parent>`. Toute config commune (version Java, plugins) doit donc être répétée dans chaque pom enfant, pas centralisée dans `mediatheque-parent`.

Ordre de build (dépendances) :

```
mediatheque-utils
mediatheque-model
mediatheque-tmdb        (client API TheMovieDB)
mediatheque-persistence (entités JPA + repositories Spring Data)
mediatheque-dto
mediatheque-service     (logique métier)
mediatheque-scan        (jar exécutable "MoviesSearch" : scan CLI vers TMDB)
mediatheque-webapp      (jar exécutable : API REST + Tomcat embarqué)
```

Versions actuelles : **Java 25**, **Spring Boot 4.1.0**.

## Environnement de build (spécifique à cette machine)

`java` et `mvn` ne sont **pas dans le PATH** système. Toujours poser explicitement avant toute commande Maven :

- JDK : `C:\Program Files\Eclipse Adoptium\jdk-25.0.4.7-hotspot`
- Maven : `C:\apache-maven-3.9.16`

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-25.0.4.7-hotspot"
$env:Path = "$env:JAVA_HOME\bin;C:\apache-maven-3.9.16\bin;$env:Path"
```

## Build

Toujours lancer depuis `mediatheque-parent` (racine du reactor) :

```powershell
mvn clean install
```

## Lancer l'application

Démarrer la base avant tout (voir "Base de données" ci-dessous) :

```powershell
docker compose up -d
java -jar mediatheque-webapp/target/mediatheque-webapp-1.0.0-SNAPSHOT.jar
java -jar mediatheque-scan/target/MoviesSearch.jar <nom-de-film>
```

- Webapp : Tomcat embarqué sur le port 8080. Endpoints : `/movies`, `/getKinds`, `/movie/{id}`, `/searchMovie/{name}`, `/moviesByKind/{kind}`.
- `mediatheque-scan` appelle l'API TMDB réelle avec une clé API en dur (`mediatheque-tmdb/src/main/resources/tmdb.properties`) et écrit dans la vraie base — ne pas lancer sans le vouloir vraiment.

## Base de données

MySQL (image `mysql:latest`), démarré via `docker-compose.yml` à la racine du repo : `docker compose up -d`. Configuré dans `mediatheque-webapp/src/main/resources/application.properties` et `mediatheque-scan/src/main/resources/application.properties`. `spring.jpa.hibernate.ddl-auto=update`.

- **Sur cette machine, pas de Docker Desktop** : Docker Engine + Compose tournent dans la distro WSL2 `Ubuntu-22.04` (déjà installés, service systemd `docker` activé). Lancer les commandes `docker compose` depuis un shell WSL (`wsl -d Ubuntu-22.04`, projet monté sous `/mnt/c/...`), pas depuis PowerShell. Le port 3306 est forwardé vers `localhost` côté Windows automatiquement — voir le piège WSL2 dans l'historique récent si la connexion échoue.

- Connexion : `jdbc:mysql://localhost:3306/mediatheque`, utilisateur/mot de passe `mediatheque`/`mediatheque` (identifiants de dev locaux en dur dans `docker-compose.yml` et les `application.properties`, même logique que la clé API TMDB — pas prévus pour être réutilisés ailleurs).
- Les données persistent dans le volume nommé `mediatheque-mysql-data` entre redémarrages. Pour repartir d'un schéma vide : `docker compose down -v`.
- Interface web de consultation (remplace l'ancienne console H2) : Adminer sur `http://localhost:8081` (service `adminer` du compose), serveur `mysql`, utilisateur/mot de passe comme ci-dessus.
- Migration depuis H2 (commit "Remplace H2 par MySQL...") : `mediatheque.mv.db`/`mediatheque.trace.db` ne sont plus utilisés et peuvent être supprimés ; aucune donnée n'a été migrée automatiquement, le schéma MySQL est recréé à neuf par `ddl-auto=update` au premier démarrage.

## Tests

- `mediatheque-webapp` a un test d'intégration (`MoviePaginationIntegrationTest`) qui valide `GET /movies` (page pleine, page partielle, page hors limites, taille par défaut de Spring Data) contre un **vrai conteneur MySQL** via Testcontainers (`@ServiceConnection` + `MySQLContainer`), pas contre H2 ni des mocks — l'objectif est de couvrir les particularités de pagination propres au dialecte MySQL réellement utilisé en prod.
- Tests unitaires (Mockito, `spring-boot-starter-test`) sur la logique métier :
  - `mediatheque-service` : `MovieServiceImplTest`, `KindServiceImplTest`, `MovieDTOFactoryImplTest`, `KindDTOFactoryImplTest`, `MovieManagerTest`.
  - `mediatheque-tmdb` : `WSMovieDAOImplTest` (client TMDB, `RestTemplate` mocké).
  - `mediatheque-webapp` : `MovieResourceTest`, `KindResourceTest` (`@WebMvcTest` + `MockMvc`, couvrent aussi `GlobalExceptionHandler`).
  - Les entités/DTO Lombok et les interfaces DAO (pas de logique propre) sont hors périmètre.
  - Plusieurs tests documentent volontairement des comportements existants surprenants plutôt que de les corriger (voir "Bugs latents découverts en écrivant les tests" ci-dessous) — ce n'était pas l'objet de la tâche.
- `mediatheque-model`, `mediatheque-dto`, `mediatheque-persistence`, `mediatheque-utils`, `mediatheque-scan` n'ont pas de tests pour l'instant.

### Bugs latents découverts en écrivant les tests (non corrigés, hors périmètre)

- `MovieServiceImpl.movie(long)` : le filtre `Optional.filter(movie -> isEmpty(releaseYear) || isEmpty(synopsis))` ne garde le film que si des données sont **manquantes** ; un film déjà complet en base échoue le filtre et déclenche `MovieNotFoundException` au lieu d'être simplement retourné. Semble être une condition inversée. Testé et documenté dans `MovieServiceImplTest#movie_whenMovieDataAlreadyComplete_throwsMovieNotFoundException`.
- `MovieManager.updateFullDatas` : `movie.setSynopsis(movieItem.getSynopsis().substring(0, 255))` plante (`StringIndexOutOfBoundsException`) dès que le synopsis TMDB fait moins de 255 caractères, au lieu de tronquer ce qui est disponible. Testé dans `MovieManagerTest#updateFullDatas_whenSynopsisShorterThan255Chars_throwsIndexOutOfBounds`.
- `WSMovieDAOImpl.getSearchAllResultsMovie` : si l'appel TMDB échoue (`RestClientException`), l'exception est avalée et une `MoviesList` vide (`results == null`) est retournée ; l'itération `for (Movie movie : movies.getResults())` lève alors une `NullPointerException` au lieu de renvoyer une liste vide. `getSearchResultsMovie` (singulier) a le même souci mais en différé : l'appel DAO réussit, la `NullPointerException` n'arrive qu'au premier accès à un getter du `MovieSearchWrapped` retourné. Testés dans `WSMovieDAOImplTest`.

### Lancer les tests (spécifique à cette machine : Docker via WSL2, pas Docker Desktop)

Testcontainers doit parler au daemon Docker, qui tourne dans la distro WSL2 `Ubuntu-22.04` (voir "Base de données"). Le daemon a été exposé en TCP en plus du socket Unix, via un override systemd **hors du repo** (à refaire si la distro WSL est recréée) :

```bash
# Dans wsl -d Ubuntu-22.04, une seule fois :
sudo mkdir -p /etc/systemd/system/docker.service.d
cat <<'EOF' | sudo tee /etc/systemd/system/docker.service.d/override.conf
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375 --containerd=/run/containerd/containerd.sock
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

Puis, côté Windows, avant `mvn test` / `mvn clean install` :

```powershell
$wslIp = (wsl -d Ubuntu-22.04 -- hostname -I).Trim().Split(" ")[0]
$env:DOCKER_HOST = "tcp://${wslIp}:2375"
```

- **Utiliser l'IP directe de la VM WSL (`hostname -I`), pas `localhost`** : le forwarding `localhost` de WSL2 s'est montré peu fiable pour une connexion Java fraîche vers le daemon Docker (`Connection refused` intermittent constaté en pratique, alors que `Test-NetConnection localhost -Port 2375` réussissait juste avant/après depuis PowerShell). L'IP directe de la VM contourne le relais NAT et a été fiable à chaque essai.
- Cette IP change à chaque redémarrage de la VM WSL — la recalculer à chaque session, ne pas la coder en dur dans un `.env`.
- Garder une session WSL active pendant les tests (même piège que pour `docker compose`, voir "Base de données") : `wsl -d Ubuntu-22.04 -- sleep 3600` lancé en tâche de fond évite que la VM et le daemon s'éteignent en cours de build.
- **Sécurité** : `-H tcp://0.0.0.0:2375` expose l'API Docker (root-équivalent) sans authentification. Sur WSL2, ce port n'est normalement joignable que depuis cette machine Windows (pas depuis le réseau local), donc le risque reste contenu à cette machine — mais ne pas reproduire cette config sur un serveur exposé.

## Pièges connus (JDK 21+)

- **Lombok** : depuis JDK 21+, `javac` ne découvre plus les annotation processors depuis le simple classpath — il faut un `-processorpath` explicite. Sans `<annotationProcessorPaths>` dans la config de `maven-compiler-plugin`, Lombok ne génère **aucun code, sans la moindre erreur ni warning** (silencieux). Cette config est en place dans `mediatheque-dto`, `mediatheque-persistence`, `mediatheque-service` — ne pas la retirer même si la version de Lombok change.
- **Vieux jars avec en-tête zip64 non conforme** : provoquent `Invalid CEN header (invalid zip64 extra data field size)` sous JDK 21+. Déjà rencontré avec `aspectjweaver` (résolu en laissant Boot gérer une version récente depuis la migration Spring Boot 4). Si ça revient sur une autre dépendance transitive ancienne, forcer une version plus récente est la solution habituelle.

## Style de codage

Observé dans le code existant — à respecter pour rester cohérent :

- **Indentation** : tabulations, pas des espaces.
- **Accolades** : style K&R, accolade ouvrante sur la même ligne (`public class Foo {`).
- **Imports** : groupés et triés alphabétiquement dans chaque groupe, une ligne vide entre les groupes, dans cet ordre : `java.*` → bibliothèques tierces / Spring (`org.*`, `com.fasterxml.*`...) → `lombok.*` → `com.xtt.mediatheque.*`. Pas de wildcard imports.
- **Injection de dépendances** : **injection par constructeur obligatoire, injection par champ interdite.** Un seul constructeur annoté `@Autowired` ; jamais de `@Autowired` directement sur un champ. Champs `private` (non `final`, y compris dans les classes où ils ne sont réassignés qu'au constructeur — cohérence avec le code existant plutôt que "correction"). Note : plusieurs classes existantes ne respectent pas encore cette règle (injection par champ dans `Main.java`, `WSMovieDAOImpl.java`, `AppTMDBConfiguration.java`) et devraient être corrigées à l'occasion plutôt que servir de modèle.
- **Entités JPA** : `@Data` (Lombok) pour générer getters/setters/equals/hashCode/toString plutôt que les écrire à la main. Constante `public static final String TABLE_NAME` sur chaque entité, utilisée dans `@Table(name = X.TABLE_NAME)`. Les classes `@EmbeddedId` doivent implémenter `Serializable` (obligatoire côté JPA, oublié à l'origine — voir historique git).
- **Repositories** : interfaces `XxxDAO` dans `com.xtt.mediatheque.dao`, annotées `@Repository`, étendent `JpaRepository<Entity, IdType>` (pas `PagingAndSortingRepository` seul, cf. historique — Spring Data 3+ n'y inclut plus les méthodes CRUD).
- **Services** : interface `XxxService` + implémentation `XxxServiceImpl` dans le sous-package `impl`, annotée `@Service`. Javadoc `{@inheritDoc}` sur les méthodes qui implémentent l'interface.
- **Javadoc** : bloc `/** */` avec tag `@author` sur les classes principales (services, managers).
- **Nommage** : `PascalCase` pour les classes, `camelCase` pour méthodes/champs, `UPPER_SNAKE_CASE` pour les constantes.
- **Langue des commentaires** : en anglais dans le code (Javadoc compris), même si les échanges avec Claude se font en français. Note : plusieurs commentaires ajoutés en français dans les `pom.xml` pendant les migrations Java/Spring Boot récentes ne respectent pas cette règle et devraient être traduits à l'occasion.

## Historique récent

- Migration Java 1.8 → 21 puis Spring Boot 2.0.3.RELEASE (2018) → 4.1.0, faite par paliers (2.7.18 → 3.1.12 → 3.5.16 → 4.0.0 → 4.1.0) pour respecter la règle Spring de ne jamais sauter une frontière majeure. Détails dans l'historique git (commits "Migration Spring Boot...", "Met à jour la version de Java...").
- Bascule `javax.*` → `jakarta.*` faite au passage à Spring Boot 3.x.
- Montée Java 21 → 25 (LTS) : simple bump de la propriété `java.version` dans les 9 poms (`mediatheque-parent` inclus, même si non hérité — gardé cohérent). Aucun changement de code nécessaire ; build et démarrage webapp vérifiés sur JDK 25.0.4 (Temurin), pas de régression sur le format de fichier H2.
- `commons-collections` 3.2.1 → 3.2.2 (`mediatheque-tmdb`, `mediatheque-persistence`) : corrige 4 alertes Dependabot (2 critical, 2 high — GHSA-fjq5-5j5f-mvxh et GHSA-6hgm-866r-3cjv, désérialisation via `InvokerTransformer`). Version épinglée en dur car non gérée par le BOM Spring Boot ; API `CollectionUtils` inchangée entre les deux versions, aucun changement de code nécessaire.
- Remplacement de H2 par MySQL (`mediatheque-webapp`, `mediatheque-scan`) : dépendance `com.h2database:h2` → `com.mysql:mysql-connector-j` (version gérée par le BOM Spring Boot, comme H2 avant elle), `spring-boot-h2console` supprimée. Base démarrée via `docker-compose.yml` (nouveau fichier à la racine) au lieu d'un fichier `~/mediatheque.mv.db`. Docker Desktop absent de la machine ; Docker Engine + Compose (déjà installés, gérés par systemd) utilisés depuis la distro WSL2 `Ubuntu-22.04` à la place. Démarrage réel de la webapp contre un conteneur MySQL vérifié avec succès (schéma créé par `ddl-auto=update`, dialecte `MySQLDialect` auto-détecté) ; pagination `/movies` testée avec 100 films de test insérés directement en base (page pleine et page partielle correctes).
  - **Piège WSL2** : par défaut, la VM WSL2 s'éteint après quelques secondes d'inactivité (pas de session WSL active), ce qui coupe le daemon Docker et le port forwarding vers Windows — `localhost:3306` devient alors injoignable depuis la webapp (tourne côté Windows), même si `docker compose up -d` a réussi. `vmIdleTimeout=-1` dans `%USERPROFILE%\.wslconfig` (`[wsl2]`) ne suffit pas toujours à lui seul dans cet environnement ; garder une session WSL active (ex. `wsl -d Ubuntu-22.04` ouvert, ou un process qui s'y maintient) pendant le développement est la solution fiable.
- Ajout d'un test d'intégration (`MoviePaginationIntegrationTest`, `mediatheque-webapp`) pour `GET /movies`, via Testcontainers + vrai MySQL plutôt que H2/mocks. A nécessité d'exposer le daemon Docker de la distro WSL2 en TCP (override systemd, hors repo, voir section "Tests") car Docker Desktop est absent de cette machine. Dépendances ajoutées : `spring-boot-starter-test`, `spring-boot-webmvc-test` (Spring Boot 4 a déplacé `@AutoConfigureMockMvc` hors de `spring-boot-test-autoconfigure` vers ce module dédié), `spring-boot-testcontainers`, `org.testcontainers:testcontainers-junit-jupiter`/`testcontainers-mysql` (Testcontainers 2.x a renommé ces artefacts, préfixés `testcontainers-`, géré par le BOM Spring Boot). 4 tests, tous verts, build complet vérifié.
- Ajout de tests unitaires sur la logique métier (`mediatheque-service`, `mediatheque-tmdb`, `mediatheque-webapp`) : 33 tests supplémentaires (Mockito + `@WebMvcTest`/`MockMvc`), voir section "Tests" pour le détail et les bugs latents découverts (non corrigés) au passage. `spring-boot-starter-test` ajouté en scope test à `mediatheque-service` et `mediatheque-tmdb` (`mediatheque-webapp` l'avait déjà). 37 tests au total sur le reactor, tous verts, build complet vérifié sur JDK 25.
