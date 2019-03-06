package br.com.enterprise.mobileparking.repository;

import br.com.enterprise.mobileparking.model.EstacionamentoUsuario;
import br.com.enterprise.mobileparking.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstacionamentoUsuarioRepository extends JpaRepository<EstacionamentoUsuario, UUID> {
    Optional<EstacionamentoUsuario> findFirstByUsuarioOrderByDataHoraInicioDesc(Usuario usuario);
}
