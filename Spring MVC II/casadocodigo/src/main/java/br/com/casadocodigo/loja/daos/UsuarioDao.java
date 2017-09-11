package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Usuario;

@Repository
public class UsuarioDao implements UserDetailsService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return entityManager.createQuery("SELECT u FROM Usuario u "
					+ "JOIN FETCH u.permissoes p WHERE u.email = :email", Usuario.class)
					.setParameter("email", username)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Usuário não encontrado pelo e-mail", e);
		}
	}

	public void gravar(Usuario usuario) {
		entityManager.persist(usuario);
	}
}
