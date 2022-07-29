package com.TestCredSystem.AppCredSystem.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.TestCredSystem.AppCredSystem.Models.Cartao;
import com.TestCredSystem.AppCredSystem.Models.Usuario;
import com.TestCredSystem.AppCredSystem.Repository.CartaoRepository;
import com.TestCredSystem.AppCredSystem.Repository.UsuarioRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@CrossOrigin(origins="*")
@Api(value= "API REST USUARIOS e CARTÕES")
@RequestMapping(value = "/api")
public class UsuarioController<AjaxBehaviorEvent> {

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private CartaoRepository cr;

	@RequestMapping("/cadastrarUsuario")
	@ApiOperation(value="Chama o formulário")
	public String form() {
		return "usuario/form-usuario";
	}
	
	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	@ApiOperation(value="Salva usuário")
	public String form(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarUsuario";
		}

		ur.save(usuario);
		attributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
		return "redirect:/cadastrarUsuario";
	}

	@RequestMapping("/ListarUsuario")
	@ApiOperation(value="Retorna uma lista de usuários")
	public ModelAndView listaUsuario() {
		ModelAndView mv = new ModelAndView("usuario/lista-usuario");
		Iterable<Usuario> usuarios = ur.findAll();
		mv.addObject("usuarios", usuarios);// tava "usuarios"
		return mv;
	}

	@RequestMapping("/detalhes-usuario/{id}")
	@ApiOperation(value="Retorna usuário único")
	public ModelAndView detalhesUsuario(@PathVariable("id") long id) {
		Usuario usuario = ur.findById(id);
		ModelAndView mv = new ModelAndView("usuario/detalhes-usuario");
		mv.addObject("usuarios", usuario); 

		Iterable<Cartao> cartoes = cr.findByUsuario(usuario);
		mv.addObject("cartoes", cartoes);

		return mv;
	}

	@RequestMapping(value = "/detalhes-usuario/{id}", method = RequestMethod.POST)
	@ApiOperation(value="Salva um Cartão")
	public String detalhesUsuarioPost(@PathVariable("id") long id, Cartao cartoes, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/detalhes-usuario/{id}";
		}

		if (cr.findByConta(cartoes.getConta_cc()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "Conta duplicada");
			return "redirect:/detalhes-usuario/{id}";
		}

		Usuario usuario = ur.findById(id);
		cartoes.setUsuario(usuario);
		cr.save(cartoes);
		attributes.addFlashAttribute("mensagem", "Cartão adicionado com sucesso");
		return "redirect:/detalhes-usuario/{id}";

	}

	
	@RequestMapping("/edita-usuario")
	@ApiOperation(value="Chama usuário")
	public ModelAndView editarUsuario(long id) {
		Usuario usuario = ur.findById(id);
		ModelAndView mv = new ModelAndView("usuario/update-usuario");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@RequestMapping(value = "/edita-usuario", method = RequestMethod.POST)
	@ApiOperation(value="Retorna usuário alterado")
	public String updateUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {

		ur.save(usuario);
		attributes.addFlashAttribute("success", "Usuário alterado com sucesso!");

		long idLong = usuario.getId();
		String id = "" + idLong;
		return "redirect:/detalhes-usuario/" + id;

	}

	@RequestMapping("/deletarUsuario")
	@ApiOperation(value="Deleta usuário")
	public String deletarUsuario(long id) {
		Usuario usuario = ur.findById(id);
		ur.delete(usuario);
		return "redirect:/ListarUsuario";
	}

	@RequestMapping("/deletarCartao")
	@ApiOperation(value="Deleta cartão do usuário")
	public String deletarCartao(String conta_cc) {
		Cartao cartao = cr.findByConta(conta_cc);

		Usuario usuario = cartao.getUsuario();
		String id = "" + usuario.getId();

		cr.delete(cartao);
		return "redirect:/detalhes-usuario/" + id;

	}
}
