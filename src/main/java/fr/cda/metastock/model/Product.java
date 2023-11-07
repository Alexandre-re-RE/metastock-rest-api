package fr.cda.metastock.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product extends AbstractModel<Product> implements Serializable  {
	

	
	protected static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	public Product(Long id, String name, String description, float unitPrice, int stock, int threshold, String picture,
			Boolean archive, List<Movement> movements) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.threshold = threshold;
		this.picture = picture;
		this.archive = archive;
		this.movements = movements;
	}

	public Product() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	protected String name;
	protected String description;
	protected float unitPrice;
	protected int stock;
	protected int threshold;
	protected String picture;
	protected Boolean archive;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	protected List<Movement> movements;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Boolean getArchive() {
		return archive;
	}
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", unitPrice=" + unitPrice
				+ ", stock=" + stock + ", threshold=" + threshold + ", picture=" + picture + ", archive=" + archive
				+ ", movements=" + movements + "]";
	}

}
