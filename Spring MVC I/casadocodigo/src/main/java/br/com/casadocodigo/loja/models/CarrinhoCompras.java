package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable {
	private static final long serialVersionUID = -2106272778536854933L;
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}
	
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) + 1);		
	}
	
	public int getQuantidade() {
		return itens.values().stream().reduce(0, (p, a) -> p + a);
	}

	public Integer getQuantidade(CarrinhoItem item) {
		if (!itens.containsKey(item)) {
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}
	
	public BigDecimal getTotal() {
		return itens.keySet().stream()
			.flatMap(carrinhoItem -> Stream.of(getTotal(carrinhoItem)))
			.reduce(BigDecimal.ZERO, (i, v) -> i.add(v));			
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto() { { setId(produtoId); }  };
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
}