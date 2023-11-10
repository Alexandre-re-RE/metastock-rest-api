### Get Current User :
```java
public Response get(@Context HttpServletRequest request) {
    Account account = (Account) request.getAttribute("user");
}
```

### Keycloak configuration :

La configuration de Keycloak pour ce projet, se trouve dans le dossier `src/main/webapp/WEB-INF/keycloak-config.json`

Pour l'utiliser, connectez vous à votre Keycloak et créez un nouveau realm, en important la configuration.