# Mettre en place Keycloak avec Wildfly

## Environnement:

- Keycloak: 22.0.5
- Wildfly: 30.0.0.Final

## Installer Keycloak

- Avec Docker:

```console
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.5 start-dev
```

- Télécharger les sources [ici](https://www.keycloak.org/downloads) et lancer la commande suivante pour démarrer Keycloak:

```console
bin\kc.bat start-dev
```

## Configurer Keycloak

Il faut ensuite se rendre sur l'application Keycloak (ex: [http://localhost:8888](http://localhost:8888)) et configurer un realm.

Exemple ici: [https://www.youtube.com/watch?v=a2871FQ7Cj4](https://www.youtube.com/watch?v=a2871FQ7Cj4) (suivre la config à partir de 20:00)

## Configurer le projet Java EE

Il faut à présent configurer notre projet Java pour utiliser l'authentification avec OpenID Connect.

Se rendre dans le dossier `src/main/webapp/META-INF/` et créez le fichier `oidc.json` avec le contenu suivant:

```json
{
    "client-id" : "<client-id>",
    "provider-url" : "http://<keycloak-app>/realms/<realm-name>"
}
```

Remplacez les valeurs suivants par les bonnes valeurs provenant de votre Keycloak:

- `<client-id>`: L'identifiant du client
- `<keycloak-app>`: L'url de votre application Keycloak
- `<realm-name>`: Le nom du realm que vous avez créé

**INFO**: `client-id` et `provider-url` est la configuration minimale pour faire fonctionner l'authentification. La liste des configurations est disponible [ici](https://docs.wildfly.org/26/Admin_Guide.html#Elytron_OIDC_Client).

Il faut ensuite préciser quel méthode d'authentification utilise notre application dans le fichier `src/main/webapp/META-INF/web.xml`:

```xml
<login-config>
    <auth-method>OIDC</auth-method>
</login-config>
```

On peut aussi ajouter le `security-constraint` pour préciser les routes protégées:

```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>secured</web-resource-name>
        <url-pattern>/rest/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>admin</role-name>
    </auth-constraint>   
</security-constraint>

<security-role>
    <role-name>admin</role-name>
</security-role>
```

Notre application est désormais sécurisé avec Keycloak.
