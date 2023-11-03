package fr.cda.metastock.controller;

import java.util.List;

import fr.cda.metastock.model.Account;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
@RequestScoped
@Transactional
public class AccountController {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id") Long id) {

        TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account AS a WHERE a.id = :id", Account.class);
        query.setParameter("id", id);

        try {
            Account result = query.getSingleResult();
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {

        TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account AS a", Account.class);
        List<Account> result = query.getResultList();

        return Response.ok(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Account account) {

        try {
            this.em.persist(account);
            return Response.status(Response.Status.CREATED).entity(account).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(Account account) {

        this.em.merge(account);

        return Response.ok(account).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account AS a WHERE a.id = :id", Account.class);
        query.setParameter("id", id);

        try {
            Account result = query.getSingleResult();
            this.em.remove(result);

            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
