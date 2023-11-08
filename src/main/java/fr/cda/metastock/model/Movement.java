package fr.cda.metastock.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Movement extends AbstractModel<Movement> implements Serializable {

	public enum Type {
		ENTRY, EXIT;
	}
	
	private static final long serialVersionUID = -6369260465693445713L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	protected String date;
	protected Integer quantity;
	protected String comment;

	@Enumerated(EnumType.STRING)
	protected Type type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	protected Account account;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	@JsonIgnore
	protected Product product;
	
	public Movement() {}

	public Movement(Long id, String date, Integer quantity, String comment, Type type, Account account, Product product) {
		super();
		this.id = id;
		this.date = date;
		this.quantity = quantity;
		this.comment = comment;
		this.type = type;
		this.account = account;
		this.product = product;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Movement [id=" + id + ", date=" + date + ", quantity=" + quantity + ", comment=" + comment
				+ ", product=" + product + "]";
	}
}
