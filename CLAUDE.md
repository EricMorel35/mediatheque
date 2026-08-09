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

- Connexion : `jdbc:mysql://localhost:3306/mediatheque`, utilisateur/mot de passe `mediatheque`/`mediatheque` (identifiants de dev locaux en dur dans `docker-compose.yml` et les `application.properties`, même logique que la clé API TMDB — pas prévus pour être réutilisés ailleurs).
- Les données persistent dans le volume nommé `mediatheque-mysql-data` entre redémarrages. Pour repartir d'un schéma vide : `docker compose down -v`.
- Interface web de consultation (remplace l'ancienne console H2) : Adminer sur `http://localhost:8081` (service `adminer` du compose), serveur `mysql`, utilisateur/mot de passe comme ci-dessus.
- Migration depuis H2 (commit "Remplace H2 par MySQL...") : `mediatheque.mv.db`/`mediatheque.trace.db` ne sont plus utilisés et peuvent être supprimés ; aucune donnée n'a été migrée automatiquement, le schéma MySQL est recréé à neuf par `ddl-auto=update` au premier démarrage.

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
- Remplacement de H2 par MySQL (`mediatheque-webapp`, `mediatheque-scan`) : dépendance `com.h2database:h2` → `com.mysql:mysql-connector-j` (version gérée par le BOM Spring Boot, comme H2 avant elle), `spring-boot-h2console` supprimée. Base démarrée via `docker-compose.yml` (nouveau fichier à la racine) au lieu d'un fichier `~/mediatheque.mv.db`. Build Maven vérifié sur JDK 25 ; démarrage réel contre un conteneur MySQL **non vérifié** sur la machine où ce changement a été fait (Docker Desktop absent) — à valider manuellement après `docker compose up -d`.
