package com.generation.crud_farmacia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String nome;

	private String nomeGenerico;

	@NotBlank(message = "A descrição é obrigatória!")
	@Size(min = 10, message = "A descrição deve conter no mínimo 10 caracteres")
	private String descricao;

	@NotNull(message = "O preço é obrigatório!")
	private Double preco;

	@NotNull(message = "A marca é obrigatória!")
	private String marca;

	@NotNull(message = "A quantidade em estoque é obrigatória!")
	private Integer qntEstoque;

	private String imagemUrl;

	@ManyToOne
	@JsonIgnoreProperties("produto")
	private Categoria categoria;

	public Produto(Long id, String nome, String nomeGenerico, String descricao, Double preco, String marca,
			Integer qntEstoque, String imagemUrl, Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.nomeGenerico = nomeGenerico;
		this.descricao = descricao;
		this.preco = preco;
		this.marca = marca;
		this.qntEstoque = qntEstoque;
		this.imagemUrl = imagemUrl;
		this.categoria = categoria;
	}

	public Produto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeGenerico() {
		return nomeGenerico;
	}

	public void setNomeGenerico(String nomeGenerico) {
		this.nomeGenerico = nomeGenerico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getQntEstoque() {
		return qntEstoque;
	}

	public void setQntEstoque(Integer qntEstoque) {
		this.qntEstoque = qntEstoque;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
