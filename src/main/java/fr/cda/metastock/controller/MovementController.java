package fr.cda.metastock.controller;

import java.util.List;

import fr.cda.metastock.model.Movement;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/movements")
@RequestScoped()
@Transactional
public class MovementController {
	
	@PersistenceContext
	EntityManager em;
	
	
	@POST()
	@Path("/")
	@Produces("application/json")
	/**
	 * Créer un mouvement.
	 * @param _movement
	 * @return Response
	 */
	public Response create(Movement _movement) {
		this.em.persist(_movement);
		return Response.ok(_movement).build();
	}
	
	@GET()
	@Path("/")
	@Produces("application/json")
	/**
	 * Liste des mouvements de stock
	 * @return
	 */
	public Response liste() {
		Query q = this.em.createQuery("select c from Movement c");
		List<Movement> movements = q.getResultList();
		return Response.ok(movements).build();
	}
	
	@GET()
	@Path("/{id}")
	@Produces("application/json")
	/**
	 * Vue d'un mouvement
	 * @param id
	 * @return
	 */
	public Response view(@PathParam("id") long id) {
		Movement _movement = this.em.find(Movement.class, id);
		return Response.ok(_movement).build();
	}
	
	@PUT()
	@Path("/{id}")
	@Produces("application/json")
	/**
	 * Mise à jour mouvement
	 * @param _movement
	 * @param id
	 * @return Response
	 */
	public Response update(Movement _movement, @PathParam("id") long id) {
		Movement _findMovement = this.em.find(Movement.class, id);
		this.em.merge(_movement);
		return Response.ok(_movement).build();
	}
	
	@DELETE()
	@Path("/{id}")
	@Produces("application/json")
	/**
	 * 
	 * @param id
	 * @return Response
	 */
	public Response delete(@PathParam("id") long id) {
		Movement _movement = this.em.find(Movement.class, id);
		this.em.remove(_movement);
		return Response.ok(_movement).build();
	}
	

}
