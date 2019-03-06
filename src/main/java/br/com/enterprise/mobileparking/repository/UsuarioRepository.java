package br.com.enterprise.mobileparking.repository;

import br.com.enterprise.mobileparking.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
