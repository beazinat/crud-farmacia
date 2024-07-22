package com.generation.crud_farmacia.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.crud_farmacia.model.Categoria;
import com.generation.crud_farmacia.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaControllerTeste {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@BeforeAll
	void start() {
		
		categoriaRepository.deleteAll();	
	}
	
	@Test
    @DisplayName("Cadastrar Categoria")
    public void deveCriarUmaCategoria() {
        HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(new Categoria(0L,
                "nome aleatorio",
                "descrição aleatória"));
        
        ResponseEntity<Categoria> corpoResposta = testRestTemplate
                .exchange("/categorias", HttpMethod.POST, corpoRequisicao, Categoria.class);
        
        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
    }
    
    @Test
    @DisplayName("Não deve permitir duplicação de Categoria")
    public void naoDuplicarCategoria() {
        Categoria categoria = new Categoria(0L, "Categoria x", "Descrição da categoria x");
        categoriaRepository.save(categoria);
        
        HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(new Categoria(0L,
                "Categoria x", "Descrição da categoria x"));
        
        ResponseEntity<Categoria> corpoResposta = testRestTemplate
                .exchange("/categorias", HttpMethod.POST, corpoRequisicao, Categoria.class);
        
        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }
    
    @Test
    @DisplayName("Atualizar Categoria")
    public void deveAtualizarUmaCategoria() {
        Categoria categoria = new Categoria(0L, "Categoria y", "Descrição da categoria y");
        categoria = categoriaRepository.save(categoria);
        
        categoria.setNome("Categoria y Atualizada");
        HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(categoria);
        
        ResponseEntity<Categoria> corpoResposta = testRestTemplate
                .exchange("/categorias", HttpMethod.PUT, corpoRequisicao, Categoria.class);
        
        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals("Categoria y Atualizada", corpoResposta.getBody().getNome());
    }
    
    @Test
    @DisplayName("Deletar Categoria")
    public void deveDeletarUmaCategoria() {
        Categoria categoria = new Categoria(0L, "Categoria z", "Descrição da categoria z");
        categoria = categoriaRepository.save(categoria);
        
        ResponseEntity<Void> corpoResposta = testRestTemplate.exchange("/categorias/" + categoria.getId(),
                HttpMethod.DELETE, null, Void.class);
        
        assertEquals(HttpStatus.NO_CONTENT, corpoResposta.getStatusCode());
    }
    
    @Test
    @DisplayName("Listar Categorias")
    public void deveListarCategorias() {
        categoriaRepository.save(new Categoria(0L, "Categoria A", "Descrição da categoria A"));
        categoriaRepository.save(new Categoria(0L, "Categoria B", "Descrição da categoria B"));
        
        ResponseEntity<List<Categoria>> corpoResposta = testRestTemplate.exchange("/categorias", HttpMethod.GET, null, 
            new ParameterizedTypeReference<List<Categoria>>() {});
        
        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(2, corpoResposta.getBody().size());
    }
    
    @Test
    @DisplayName("Buscar Categoria por Nome")
    public void deveBuscarCategoriaPorNome() {
        categoriaRepository.save(new Categoria(0L, "Categoria C", "Descrição da categoria C"));
        
        ResponseEntity<List<Categoria>> corpoResposta = testRestTemplate.exchange("/categorias/nome/Categoria C", HttpMethod.GET, null, 
            new ParameterizedTypeReference<List<Categoria>>() {});
        
        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(1, corpoResposta.getBody().size());
    }
}
