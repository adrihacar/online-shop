package com.onlineshop.product.api.repository;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue (strategy = IDENTITY)
	private int id;
	
	@Column(name="seller")
	@NotNull
	private int seller;
	
	@Column(name="name")
	@NotNull
	private String name;
	
	@Column(name="status")
	@NotNull
	private int status;
	
	@Column(name="category")
	@NotNull
	private int category;
	
	@Column(name="description")
	@NotNull
	private String description;
	
	@Column(name="image")
	private byte[] image;
	
	@Column(name="price")
	@NotNull
	private double price;

	public Product(int seller, String name, int status, int category, String description, byte[] image,
			double price) {
		this.seller = seller;
		this.name = name;
		this.status = status;
		this.category = category;
		this.description = description;
		this.image = image;
		this.price = price;
	}

}
