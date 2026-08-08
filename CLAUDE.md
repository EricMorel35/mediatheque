# mediatheque

Application de gestion de médiathèque personnelle (films) : scan/import depuis TheMovieDB (TMDB), stockage en base H2, consultation via une webapp Spring Boot.

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

Versions actuelles : **Java 21**, **Spring Boot 4.1.0**.

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

```powershell
java -jar mediatheque-webapp/target/mediatheque-webapp-1.0.0-SNAPSHOT.jar
java -jar mediatheque-scan/target/MoviesSearch.jar <nom-de-film>
```

- Webapp : Tomcat embarqué sur le port 8080. Endpoints : `/movies`, `/getKinds`, `/movie/{id}`, `/searchMovie/{name}`, `/moviesByKind/{kind}`.
- Console H2 : `http://localhost:8080/console` (nécessite la dépendance `spring-boot-h2console`, voir plus bas).
- `mediatheque-scan` appelle l'API TMDB réelle avec une clé API en dur (`mediatheque-tmdb/src/main/resources/tmdb.properties`) et écrit dans la vraie base — ne pas lancer sans le vouloir vraiment.

## Base de données

H2 en fichier, chemin fixe : `~/mediatheque.mv.db` (donc `C:\Users\<user>\mediatheque.mv.db` sous Windows). Configuré dans `mediatheque-webapp/src/main/resources/application.properties` et `mediatheque-scan/src/main/resources/application.properties`. `spring.jpa.hibernate.ddl-auto=update`.

**Le format de fichier H2 change entre versions majeures de H2** (rencontré à plusieurs reprises lors de la migration Spring Boot : `Unsupported database file version`). Si le démarrage échoue avec une erreur de ce type après une montée de version de dépendances, c'est presque toujours ça — pas une vraie régression. Sur une base de test jetable (pas de données réelles à préserver), supprimer `~/mediatheque.mv.db` et `~/mediatheque.trace.db` et relancer suffit à recréer le schéma à neuf.

## Pièges connus (JDK 21+)

- **Lombok** : depuis JDK 21+, `javac` ne découvre plus les annotation processors depuis le simple classpath — il faut un `-processorpath` explicite. Sans `<annotationProcessorPaths>` dans la config de `maven-compiler-plugin`, Lombok ne génère **aucun code, sans la moindre erreur ni warning** (silencieux). Cette config est en place dans `mediatheque-dto`, `mediatheque-persistence`, `mediatheque-service` — ne pas la retirer même si la version de Lombok change.
- **Vieux jars avec en-tête zip64 non conforme** : provoquent `Invalid CEN header (invalid zip64 extra data field size)` sous JDK 21+. Déjà rencontré avec `aspectjweaver` (résolu en laissant Boot gérer une version récente depuis la migration Spring Boot 4). Si ça revient sur une autre dépendance transitive ancienne, forcer une version plus récente est la solution habituelle.

## Style de codage

Observé dans le code existant — à respecter pour rester cohérent :

- **Indentation** : tabulations, pas des espaces.
- **Accolades** : style K&R, accolade ouvrante sur la même ligne (`public class Foo {`).
- **Imports** : groupés et triés alphabétiquement dans chaque groupe, une ligne vide entre les groupes, dans cet ordre : `java.*` → bibliothèques tierces / Spring (`org.*`, `com.fasterxml.*`...) → `lombok.*` → `com.xtt.mediatheque.*`. Pas de wildcard imports.
- **Injection de dépendances** : constructeur annoté `@Autowired`, jamais d'injection par champ. Champs `private` (non `final`, y compris dans les classes où ils ne sont réassignés qu'au constructeur — cohérence avec le code existant plutôt que "correction").
- **Entités JPA** : `@Data` (Lombok) pour générer getters/setters/equals/hashCode/toString plutôt que les écrire à la main. Constante `public static final String TABLE_NAME` sur chaque entité, utilisée dans `@Table(name = X.TABLE_NAME)`. Les classes `@EmbeddedId` doivent implémenter `Serializable` (obligatoire côté JPA, oublié à l'origine — voir historique git).
- **Repositories** : interfaces `XxxDAO` dans `com.xtt.mediatheque.dao`, annotées `@Repository`, étendent `JpaRepository<Entity, IdType>` (pas `PagingAndSortingRepository` seul, cf. historique — Spring Data 3+ n'y inclut plus les méthodes CRUD).
- **Services** : interface `XxxService` + implémentation `XxxServiceImpl` dans le sous-package `impl`, annotée `@Service`. Javadoc `{@inheritDoc}` sur les méthodes qui implémentent l'interface.
- **Javadoc** : bloc `/** */` avec tag `@author` sur les classes principales (services, managers).
- **Nommage** : `PascalCase` pour les classes, `camelCase` pour méthodes/champs, `UPPER_SNAKE_CASE` pour les constantes.
- **Langue des commentaires** : en anglais dans le code (Javadoc compris), même si les échanges avec Claude se font en français. Note : plusieurs commentaires ajoutés en français dans les `pom.xml` pendant les migrations Java/Spring Boot récentes ne respectent pas cette règle et devraient être traduits à l'occasion.

## Historique récent

- Migration Java 1.8 → 21 puis Spring Boot 2.0.3.RELEASE (2018) → 4.1.0, faite par paliers (2.7.18 → 3.1.12 → 3.5.16 → 4.0.0 → 4.1.0) pour respecter la règle Spring de ne jamais sauter une frontière majeure. Détails dans l'historique git (commits "Migration Spring Boot...", "Met à jour la version de Java...").
- Bascule `javax.*` → `jakarta.*` faite au passage à Spring Boot 3.x.
