package fr.cda.metastock.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "movement_type", discriminatorType = DiscriminatorType.STRING)
public class Movement extends AbstractModel<Movement>{
	
	private static final long serialVersionUID = -6369260465693445713L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	protected String date;
	protected Integer quantity;
	protected String comment;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	protected Product product;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	protected Account account;
	
	public Movement() {}

	public Movement(Long id, String date, Integer quantity, String comment, Product product, Account account) {
		super();
		this.id = id;
		this.date = date;
		this.quantity = quantity;
		this.comment = comment;
		this.product = product;
		this.account = account;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Movement [id=" + id + ", date=" + date + ", quantity=" + quantity + ", comment=" + comment
				+ ", product=" + product + ", account=" + account + "]";
	}
}
