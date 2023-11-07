package fr.cda.metastock.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/rest")
@ApplicationScoped
@DeclareRoles({"warehouseman", "logistician"})
public class RestApplication extends Application {
}
