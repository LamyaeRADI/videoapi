# VideoAPI

API REST pour gérer une collection de vidéos comprenant des films et des séries, avec fonctionnalités de recherche, ajout, suppression et recommandations basées sur des labels communs.

---

## Fonctionnalités

- Ajouter un film ou une série
- Récupérer une vidéo par ID
- Rechercher des vidéos par titre
- Lister tous les films ou toutes les séries
- Supprimer une vidéo
- Consulter la liste des vidéos supprimées
- Obtenir des recommandations de vidéos similaires selon les labels partagés

---

## Technologies utilisées

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 (base de données en mémoire)
- Validation Bean (Jakarta Validation)
- Maven

---

## Configuration

Fichier `application.properties` :

```properties
spring.application.name=videoapi
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:h2:mem:videodb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

```
---

## Endpoints HTTP
Méthode	URL	Description	Body JSON exemple
---

##  Endpoints HTTP

| Méthode | URL | Description 
|--------|-----|-------------
| `POST` | `/videos` | Ajouter une vidéo (film ou série) 
| `GET`  | `/videos/{id}` | Récupérer une vidéo par ID 
| `GET`  | `/videos/search?keyword=mot` | Rechercher des vidéos par mot-clé dans le titre 
| `GET`  | `/videos/movies` | Obtenir la liste de tous les films
| `GET`  | `/videos/series` | Obtenir la liste de toutes les séries 
| `DELETE` | `/videos/{id}` | Supprimer une vidéo par son ID 
| `GET` | `/videos/deleted` | Récupérer la liste des IDs des vidéos supprimées 
| `GET` | `/videos/{id}/recommendations?minCommonLabels=2` | Recommander des vidéos similaires partageant au moins 2 labels (défaut = 1) 

---

### Exemple de corps JSON

####  Ajouter un film :
```json
{
  "id": "movie-123",
  "title": "Indiana Jones",
  "labels": ["adventure", "whip", "archeology"],
  "type": "movie",
  "director": "Steven Spielberg",
  "releaseDate": "1981-06-12T00:00:00Z"
}