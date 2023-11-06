package fr.cda.metastock.controller;

import java.util.List;



import fr.cda.metastock.model.Product;
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
public class ProductsController {

	
	@PersistenceContext(unitName = "jakartaee-hello-world-persistence-unit")
	private EntityManager em;
	
	
	/**
	 * Return la liste de tout les Produits 
	 * @return Response
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
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
	public Response getProduit(@PathParam("id") Long id) {
		Product product = new Product();

		TypedQuery<Product> request = em.createQuery("select b from Product as b where b.id=:id", Product.class);
		request.setParameter("id", id);
		product = request.getSingleResult();
		return Response.ok(product).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response create(Product product) {
		em.persist(product);
		return Response.status(201).entity(product).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(Product product,@PathParam("id") Long id) {
		Product existingProduct = em.find(Product.class, id);
		Product produitaMettreaJour = new Product(
				null,
				product.getName() == null ? existingProduct.getName() : product.getName(),
				product.getDescription() == null ? existingProduct.getDescription() : product.getDescription(),
				product.getUnitPrice() ==  0 ? existingProduct.getUnitPrice() : product.getUnitPrice(),	
				product.getStock() ==  0 ? existingProduct.getStock() : product.getStock(),	
				product.getThreshold() ==  0 ? existingProduct.getThreshold() : product.getThreshold(),	
				product.getPicture() ==  null ? existingProduct.getPicture() : product.getPicture(),
				product.getArchive() ==  null ? existingProduct.getArchive() : product.getArchive()
				);
		
		
		produitaMettreaJour.setId(id);
		em.merge(produitaMettreaJour);
		return Response.ok(produitaMettreaJour).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") Long id) {
		Product product = em.find(Product.class, id);
		em.remove(product);
		 return Response.ok().entity("Product supprimer avec succés !").build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

}
