package com.generation.crud_farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.crud_farmacia.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	public List<Produto> getAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	
	public List<Produto> getAllByMarcaContainingIgnoreCase(@Param("marca") String marca);

}
