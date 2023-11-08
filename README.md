### Get Current User :
```java
public Response get(@Context HttpServletRequest request) {
    Account account = (Account) request.getAttribute("user");
}
```