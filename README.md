### Get Current User :
```java
public Response get(@Context HttpServletRequest request) {
    Account account = (Account) request.getAttribute("user");
}
```

### Keycloak configuration :

La configuration de Keycloak pour ce projet, se trouve dans le dossier `src/main/webapp/WEB-INF/keycloak-config.json`

Pour l'utiliser, connectez vous à votre Keycloak et créez un nouveau realm, en important la configuration.

### Export insomia

Pour tester les endpoint un export insomia est présent dans le dossier `src\main\webapp\WEB-INF\metastock-insomia`

> /!\ Attention pour utiliser toutes les requêtes Insomia il faut lancer la requête d'authentification afin que la variable d'environnement du token se remplisse et s'envoie automatiquement en Bearer Token.

![alt text](https://raw.githubusercontent.com/Alexandre-re-RE/metastock-rest-api/main/src/main/webapp/WEB-INF/erreur_insomia.PNG "erreur")

L'erreur ci-dessus indique qu'il faut aller dans les variables d'environnement ré mapper le token sur la bonne Endpoint qui réalise l'authentification voir ci-dessous (ATTENTION ASSURER VOUS D' AVOIR LANCER AU MOINS UNE FOIS LA REQUETE D4AUTHENTIFICATION).

![alt text](https://raw.githubusercontent.com/Alexandre-re-RE/metastock-rest-api/main/src/main/webapp/WEB-INF/fix_token.PNG "fix")