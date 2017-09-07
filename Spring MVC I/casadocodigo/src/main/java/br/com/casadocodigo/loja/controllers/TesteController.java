package br.com.casadocodigo.loja.controllers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.Produto;

@Controller
@RequestMapping("/limpa")
public class TesteController {
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView limparBase() {
		manager.createQuery("SELECT p FROM Produto p", Produto.class)
			.getResultList().forEach(p -> {
				manager.remove(p);
			});
		
		return new ModelAndView("redirect:/produtos");
	}
}