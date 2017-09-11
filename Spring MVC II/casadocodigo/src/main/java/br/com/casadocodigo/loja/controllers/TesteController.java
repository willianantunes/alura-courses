package br.com.casadocodigo.loja.controllers;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.UsuarioDao;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;

@Controller
@RequestMapping("/limpa")
@Transactional
public class TesteController {
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private UsuarioDao usuarioDao;
		
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView limparBase() {
		manager.createQuery("SELECT p FROM Produto p", Produto.class)
			.getResultList().forEach(p -> {
				manager.remove(p);
			});
		
		return new ModelAndView("redirect:/produtos");
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/limpa-mas-essa-cria")
	public String urlMagicaMaluca() {
	    Usuario usuario = new Usuario(); 
	    usuario.setNome("Antunes Tunes");
	    usuario.setEmail("antunes@willian.com.br");
	    // Mesmo que 123456
	    usuario.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
	    usuario.setPermissoes(Arrays.asList(new Role("ROLE_ADMIN")));

	    usuarioDao.gravar(usuario);

	    return "Url Mágica executada";
	}	
}