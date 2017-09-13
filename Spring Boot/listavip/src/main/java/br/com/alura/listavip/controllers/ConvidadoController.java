package br.com.alura.listavip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.repository.ConvidadoRepository;

@Controller
@RequestMapping("/")
public class ConvidadoController {	
	@Autowired
	private ConvidadoRepository repository;
		
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@Cacheable(cacheNames = "listaDeConvidadosCache")
	@RequestMapping(value = "/listaconvidados", method = RequestMethod.GET)
	public ModelAndView listaConvidados(){		
		Iterable<Convidado> convidados = repository.findAll();
		
		ModelAndView model = new ModelAndView("listaconvidados");
		model.addObject("convidados", convidados);
		
		return model;
	}
	
	@CacheEvict(cacheNames = "listaDeConvidadosCache", allEntries = true)
	@RequestMapping(value = "/salvar", method = RequestMethod.POST )
	public ModelAndView salvar(Convidado convidado, RedirectAttributes attributes){
		repository.save(convidado);
		attributes.addFlashAttribute("convidadoCadastrado", convidado);
		
		return new ModelAndView("redirect:/listaconvidados");		
	}
}