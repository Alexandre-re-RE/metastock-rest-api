package fr.cda.metastock.controller;

import java.util.List;

import fr.cda.metastock.model.Account;
import fr.cda.metastock.model.Movement;
import fr.cda.metastock.model.Product;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movements")
@RequestScoped()
@Transactional
@DenyAll
public class MovementController {
	
	@PersistenceContext
	EntityManager em;
	
	@POST()
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("warehouseman")
	/**
	 * Créer un mouvement.
	 * @param movement
	 * @return Response
	 */
	public Response create(Movement movement, @Context HttpServletRequest request) {
		Account account = (Account) request.getAttribute("user");
		movement.setAccount(account);

		if(movement.getQuantity() < 0) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid quantity").build();
		}
		
		Product product = this.em.find(Product.class, movement.getProduct().getId());

		if(product == null) {
			return Response
			.status(Response.Status.BAD_REQUEST)
			.entity(String.format("Product '%s' does'nt exists", movement.getProduct().getId()))
			.build();
		}

		switch (movement.getType()) {
			case EXIT:
				this.em.persist(movement);
				product.removeStock(movement.getQuantity());
				this.em.persist(product);
				break;
			case ENTRY:
				this.em.persist(movement);
				product.addStock(movement.getQuantity());
				this.em.persist(product);
				break;
		}

		return Response.ok(movement).build();
	}
	
	@GET()
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("logistician")
	/**
	 * Liste des mouvements de stock
	 * @return 
	 */
	public Response index() {
		TypedQuery<Movement> query = this.em.createQuery("SELECT c FROM Movement c", Movement.class);
		List<Movement> movements = query.getResultList();
		return Response.ok(movements).build();
	}
	
	@GET()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("logistician")
	/**
	 * Vue d'un mouvement
	 * @param id
	 * @return
	 */
	public Response view(@PathParam("id") Long id) {
		Movement movement = this.em.find(Movement.class, id);
		return Response.ok(movement).build();
	}
	
	@PUT()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Mise à jour mouvement
	 * @param movement
	 * @param id
	 * @return Response
	 */
	public Response update(Movement movement, @PathParam("id") Long id) {
		Movement target = this.em.find(Movement.class, id);
		target.merge(movement);
		this.em.merge(target);

		return Response.ok(target).build();
	}
	
	@DELETE()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @param id
	 * @return Response
	 */
	public Response delete(@PathParam("id") Long id) {
		Movement movement = this.em.find(Movement.class, id);
		this.em.remove(movement);
		return Response.ok(movement).build();
	}
	

}
