package com.generation.crud_farmacia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.crud_farmacia.model.Categoria;
import com.generation.crud_farmacia.model.Produto;
import com.generation.crud_farmacia.repository.CategoriaRepository;
import com.generation.crud_farmacia.repository.ProdutoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@BeforeAll
	void start() {
		
		produtoRepository.deleteAll();	
	}
	
	@Test
	@DisplayName("Cadastrar produto")
	public void mustCreateAProduct() {
		
		Categoria x = categoriaRepository.save(new Categoria(0L,
				"cat 1",
				"description from cat 1"));
		
		HttpEntity<Produto> requisitionBody = new HttpEntity<Produto>(new Produto(0L,
				"Novalgina", 
				"Dipirona monoidratada", 
				"Atua como antitérmico (reduz a febre) e analgésico (reduz a dor). Uso oral. Uso adulto e pediátrico acima de 15 anos de idade.", 
				10.50, 
				"Sanofi", 
				8, 
				"https://cdn.ultrafarma.com.br/static/produtos/781290/medium-781290.jpg",
				x));
		
		ResponseEntity<Produto> responseBody = testRestTemplate
				.exchange("/produtos", HttpMethod.POST, requisitionBody, Produto.class);
		
		assertEquals(HttpStatus.CREATED, responseBody.getStatusCode());
	}

}
