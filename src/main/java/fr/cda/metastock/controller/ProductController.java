package fr.cda.metastock.controller;

import java.util.List;

import fr.cda.metastock.model.Movement;
import fr.cda.metastock.model.Product;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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


@Transactional
@RequestScoped
@Path("/products")
@DenyAll
public class ProductController {

	
	@PersistenceContext
    private EntityManager em;
	
	
	/**
	 * Return la liste de tout les Produits 
	 * @return Response
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed("warehouseman")
	public Response getProduits( @QueryParam("filtre") String filtre,
	        @QueryParam("tri") @DefaultValue("ASC") String tri) {
		
	    String queryString = "SELECT p FROM Product p";

	    if (filtre != null && !filtre.trim().isEmpty()) {
	        queryString += " WHERE LOWER(p.name) LIKE LOWER(:filtre)"; 
	    }

	    String direction = "ASC".equalsIgnoreCase(tri) ? "ASC" : "DESC";
	    queryString += " ORDER BY p.name " + direction; 
	    
	 
	    TypedQuery<Product> requete = em.createQuery(queryString, Product.class);
	    
	    if (filtre != null && !filtre.trim().isEmpty()) {
	        requete.setParameter("filtre", "%" + filtre.toLowerCase() + "%");
	    }
	    
	    List<Product> listProduit = requete.getResultList();

	    return Response.ok(listProduit).build();
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	@RolesAllowed("warehouseman")
	public Response getProduit(@PathParam("id") Long id) {
		Product product = this.em.find(Product.class, id);
		return Response.ok(product).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed("logistician")
	public Response create(Product product) {
		em.persist(product);
		return Response.status(201).entity(product).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@RolesAllowed("logistician")
	public Response update(Product product,@PathParam("id") Long id) {
		Product existingProduct = em.find(Product.class, id);
		existingProduct.merge(product);
		this.em.merge(existingProduct);
		return Response.ok(existingProduct).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") Long id) {
		Product product = em.find(Product.class, id);
		em.remove(product);
		 return Response.ok().entity("Product supprimer avec succés !").build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}/movements")
	@RolesAllowed("logistician")
	/**
	 * Liste des mouvements de produits
	 * @param id
	 * @return Response
	 */
	public Response movements(@PathParam("id") Long id) {
		TypedQuery<Movement> query = this.em.createQuery(
			"SELECT p.movements FROM Product p WHERE p.id = :id", 
			Movement.class
		);
		query.setParameter("id", id);

		List<Movement> movements = query.getResultList();
		
		return Response.ok(movements).build();
	}

	@GET
    @Path("/{id}/archive")
    @RolesAllowed("logistician")
    public Response archive(@PathParam("id") Long id) {

        Product product = this.em.find(Product.class, id);

		if(product == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

        product.setArchive(true);
        this.em.persist(product);

        return Response.noContent().build();
    }
	
}
