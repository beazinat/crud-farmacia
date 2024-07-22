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
import com.generation.crud_farmacia.model.Produto;
import com.generation.crud_farmacia.repository.CategoriaRepository;
import com.generation.crud_farmacia.repository.ProdutoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoControllerTeste {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@BeforeAll
	void start() {

		produtoRepository.deleteAll();
		categoriaRepository.deleteAll();
	}

	@Test
	@DisplayName("Cadastrar produto")
	public void deveCriarUmProduto() {
		Categoria categoria = new Categoria(0L, "Categoria Existente", "Descrição da categoria existente");
		categoria = categoriaRepository.save(categoria);

		Produto produto = new Produto(0L, "Novalgina", "Dipirona monoidratada",
				"Atua como antitérmico (reduz a febre) e analgésico (reduz a dor). Uso oral. Uso adulto e pediátrico acima de 15 anos de idade.",
				10.50, "Sanofi", 8, "https://cdn.ultrafarma.com.br/static/produtos/781290/medium-781290.jpg", categoria);
		produto.setCategoria(categoria);

		HttpEntity<Produto> corpoRequisicao = new HttpEntity<Produto>(produto);

		ResponseEntity<Produto> corpoResposta = testRestTemplate.exchange("/produtos", HttpMethod.POST, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar produto")
	public void deveAtualizarUmProduto() {
		Categoria categoria = new Categoria(0L, "Categoria Atualizada", "Descrição da categoria atualizada");
		categoria = categoriaRepository.save(categoria);

		Produto produto = new Produto(0L, "Paracetamol", "Paracetamol",
				"Analgesico e antipirético. Uso oral. Uso adulto e pediátrico acima de 12 anos de idade.", 5.00,
				"Neo Química", 15, "https://cdn.ultrafarma.com.br/static/produtos/781290/medium-781290.jpg", categoria);
		produto.setCategoria(categoria);
		produto = produtoRepository.save(produto);

		produto.setNome("Paracetamol Atualizado");
		HttpEntity<Produto> corpoRequisicao = new HttpEntity<Produto>(produto);

		ResponseEntity<Produto> corpoResposta = testRestTemplate.exchange("/produtos", HttpMethod.PUT, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		assertEquals("Paracetamol Atualizado", corpoResposta.getBody().getNome());
	}

	@Test
	@DisplayName("Deletar produto")
	public void deveDeletarUmProduto() {
		Categoria categoria = new Categoria(0L, "Categoria a ser Deletada", "Descrição da categoria a ser deletada");
		categoria = categoriaRepository.save(categoria);

		Produto produto = new Produto(0L, "Ibuprofeno", "Ibuprofeno",
				"Anti-inflamatório, analgésico e antitérmico. Uso oral. Uso adulto e pediátrico acima de 12 anos de idade.",
				12.00, "Bayer", 10, "https://cdn.ultrafarma.com.br/static/produtos/781290/medium-781290.jpg", categoria);
		produto.setCategoria(categoria);
		produto = produtoRepository.save(produto);

		ResponseEntity<Void> corpoResposta = testRestTemplate.exchange("/produtos/" + produto.getId(),
				HttpMethod.DELETE, null, Void.class);

		assertEquals(HttpStatus.NO_CONTENT, corpoResposta.getStatusCode());
	}

	@Test
    @DisplayName("Listar produtos")
    public void deveListarProdutos() {
        Categoria categoria = new Categoria(0L, "Categoria Teste", "Descrição da categoria teste");
        categoria = categoriaRepository.save(categoria);

        produtoRepository.save(new Produto(0L, 
        		"Produto 1", 
        		"Descrição do produto 1", 
        		"Detalhes do produto 1", 
        		10.00, 
        		"Fabricante 1", 
        		5, 
        		"url1.jpg", 
        		categoria));
        produtoRepository.save(new Produto(0L, 
        		"Produto 2", 
        		"Descrição do produto 2", 
        		"Detalhes do produto 2", 
        		15.00, 
        		"Fabricante 2", 
        		10, 
        		"url2.jpg", 
        		categoria));

        ResponseEntity<List<Produto>> corpoResposta = testRestTemplate.exchange("/produtos", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Produto>>() {});

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(2, corpoResposta.getBody().size());
    }

	@Test
    @DisplayName("Buscar produtos por nome")
    public void deveBuscarProdutosPorNome() {
        Categoria categoria = new Categoria(0L, "Categoria Teste Nome", "Descrição da categoria teste nome");
        categoria = categoriaRepository.save(categoria);

        produtoRepository.save(new Produto(0L, 
        		"Produto Nome 1", 
        		"Descrição do produto nome 1", 
        		"Detalhes do produto nome 1", 
        		12.00, 
        		"Fabricante Nome 1", 
        		4, 
        		"urlnome1.jpg", 
        		categoria));
        produtoRepository.save(new Produto(0L, 
        		"Produto Nome 2", 
        		"Descrição do produto nome 2", 
        		"Detalhes do produto nome 2", 
        		18.00, 
        		"Fabricante Nome 2", 
        		7, 
        		"urlnome2.jpg", 
        		categoria));

        ResponseEntity<List<Produto>> corpoResposta = testRestTemplate.exchange("/produtos/nome/Produto Nome", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Produto>>() {});

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(2, corpoResposta.getBody().size());
    }

    @Test
    @DisplayName("Buscar produtos por fabricante")
    public void deveBuscarProdutosPorFabricante() {
        Categoria categoria = new Categoria(0L, "Categoria Teste Fabricante", "Descrição da categoria teste fabricante");
        categoria = categoriaRepository.save(categoria);

        produtoRepository.save(new Produto(0L, 
        		"Produto Fab 1", 
        		"Descrição do produto fab 1", 
        		"Detalhes do produto fab 1", 
        		9.00, 
        		"Fabricante Teste", 
        		2, 
        		"urlfab1.jpg", 
        		categoria));
        produtoRepository.save(new Produto(0L, 
        		"Produto Fab 2", 
        		"Descrição do produto fab 2", 
        		"Detalhes do produto fab 2", 
        		11.00, 
        		"Fabricante Teste", 
        		6, 
        		"urlfab2.jpg", 
        		categoria));

        ResponseEntity<List<Produto>> corpoResposta = testRestTemplate.exchange("/produtos/fabricante/Fabricante Teste", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Produto>>() {});

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(2, corpoResposta.getBody().size());
    }

}
