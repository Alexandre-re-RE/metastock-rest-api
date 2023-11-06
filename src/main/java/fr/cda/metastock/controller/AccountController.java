package fr.cda.metastock.controller;

import java.util.List;

import fr.cda.metastock.filter.AccountFilter;
import fr.cda.metastock.model.Account;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    public Response index(@QueryParam("filter") @DefaultValue("{}") String filter) {
        AccountFilter filters = null;

        try {
            filters = AccountFilter.buildFromJson(filter, AccountFilter.class);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Filtres invalide").build();
        }

        CriteriaBuilder builder = this.em.getCriteriaBuilder();

        CriteriaQuery<Account> query = builder.createQuery(Account.class);
        Root<Account> a = query.from(Account.class);
        query.select(a);

        if(filters.getArchive() != null) {
            query.where(builder.equal(a.get("archive").as(Boolean.class), filters.getArchive()));
        }

        List<Account> result = this.em.createQuery(query).getResultList();

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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Long id, Account account) {
        Account target = this.em.find(Account.class, id);

        target.merge(account);

        this.em.merge(target);

        return Response.ok(target).build();
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

    @GET
    @Path("/{id}/archive")
    public Response archive(@PathParam("id") Long id) {
        TypedQuery<Account> query = this.em.createQuery("SELECT a FROM Account AS a WHERE a.id = :id", Account.class);
        query.setParameter("id", id);

        Account account = null;

        try {
            account = query.getSingleResult();
        } catch (Exception ignored) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        account.setArchive(true);
        this.em.persist(account);

        return Response.noContent().build();
    }
    
}
