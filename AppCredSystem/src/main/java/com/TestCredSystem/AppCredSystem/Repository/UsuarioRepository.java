package com.TestCredSystem.AppCredSystem.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.TestCredSystem.AppCredSystem.Models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	Usuario findById(long id);
	
	//busca
	Usuario findByNome(String nome);

	@Query(value = "select u from Usuario u where u.nome like %?1%")
	List<Usuario>findByNomes(String nome);
}
