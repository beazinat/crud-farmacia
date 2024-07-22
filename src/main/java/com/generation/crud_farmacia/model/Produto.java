package com.generation.crud_farmacia.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório!")
	@Size(min = 3, max = 255, message = "O nome deve conter no mínimo 03 e no máximo 255 caracteres")
	private String name;
	
	private String genericName;
	
	@NotBlank(message = "A descrição é obrigatória!")
    @Size(min = 10, message = "A descrição deve conter no mínimo 10 caracteres")
    private String description;
    
    @NotNull(message = "O preço é obrigatório!")
    private Double price;
    
    @NotNull(message = "A marca é obrigatória!")
    private String brand;
    
    @NotNull(message = "A quantidade em estoque é obrigatória!")
    private Integer stockQnt;

    private String imageUrl;
    
    @NotNull(message = "A validade do produto é obrigatória!")
    private Date expiration;
    
    @ManyToOne
    @JsonIgnoreProperties("produto")
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "A categoria é obrigatória!")
    private Categoria categoria;
    

	public Integer getStockQnt() {
		return stockQnt;
	}

	public void setStockQnt(Integer stockQnt) {
		this.stockQnt = stockQnt;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getStockQuantity() {
		return stockQnt;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQnt = stockQuantity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Categoria getCategory() {
		return categoria;
	}

	public void setCategory(Categoria category) {
		this.categoria = category;
	}

}
