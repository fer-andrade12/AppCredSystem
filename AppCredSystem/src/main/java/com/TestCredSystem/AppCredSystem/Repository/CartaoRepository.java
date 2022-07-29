package com.TestCredSystem.AppCredSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.TestCredSystem.AppCredSystem.Models.Cartao;
import com.TestCredSystem.AppCredSystem.Models.Usuario;


public interface CartaoRepository extends CrudRepository<Cartao, String>{
	
	Iterable<Cartao>findByUsuario(Usuario usuario);
	
	//busca
	//List<Cartao>findByCartoes(String conta_cc);
	@Query(value = "select u from Cartao u where u.conta_cc like %?1%")
	
	Cartao findByConta(String conta_cc);
	
	Cartao findById(long id);
}
