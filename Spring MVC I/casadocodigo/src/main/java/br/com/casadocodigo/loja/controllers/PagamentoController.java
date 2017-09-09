package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {
	@Autowired
	private CarrinhoCompras carrinho;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/finalizar")
	public Callable<ModelAndView> finalizar(RedirectAttributes attributes) {
		return () -> {
			String url = "http://book-payment.herokuapp.com/payment";
			try {
				String response = restTemplate.postForObject(url, new DadosPagamento(carrinho.getTotal()), String.class);
				attributes.addFlashAttribute("sucesso", response);			
				return new ModelAndView("redirect:/produtos");
			} catch (RestClientException e) {
				attributes.addFlashAttribute("falha", "Valor maior que o permitido");			
				return new ModelAndView("redirect:/produtos");
			}			
		};
	}
}