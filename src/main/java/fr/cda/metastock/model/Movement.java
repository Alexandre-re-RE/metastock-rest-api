package fr.cda.metastock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movement extends AbstractModel<Movement>{
	
	private static final long serialVersionUID = -6369260465693445713L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	protected String date;
	protected Integer quantity;
	protected String comment;
	
	public Movement() {}

	public Movement(Long id, String date, Integer quantity, String comment) {
		super();
		this.id = id;
		this.date = date;
		this.quantity = quantity;
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Movement [id=" + id + ", date=" + date + ", quantity=" + quantity + ", comment=" + comment + "]";
	}
}
