package com.TestCredSystem.AppCredSystem.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.TestCredSystem.AppCredSystem.Repository.CartaoRepository;
import com.TestCredSystem.AppCredSystem.Repository.UsuarioRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="*")
@Api(value= "API REST USUARIOS")
@RequestMapping(value = "/api")
@Controller
public class BuscaController {
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private CartaoRepository cr;
	
	
	@RequestMapping("/")
	@ApiOperation(value="Retorna index")
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value = "/", method= RequestMethod.POST)
	@ApiOperation(value="Retorna index com a busca")
	public ModelAndView buscaIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome) {
		ModelAndView mv = new ModelAndView("index");
		String mensagem = "Resultado da busca por " + buscar;
		
		if(nome.equals("nomeUsuario")) {
			mv.addObject("usuarios", ur.findByNomes(buscar));
		}else if(nome.equals("nomeCartao")) {
			mv.addObject("cartoes", cr.findByConta(buscar));
		}else {
			mv.addObject("usuarios", ur.findByNomes(buscar));
			mv.addObject("cartoes", cr.findByConta(buscar));
		}
		
		mv.addObject("mensagem", mensagem);
		
		return mv;
	}
}
