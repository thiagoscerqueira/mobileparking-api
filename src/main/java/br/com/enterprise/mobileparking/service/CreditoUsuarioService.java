package br.com.enterprise.mobileparking.service;

import br.com.enterprise.mobileparking.dto.AdicionaCreditosDto;
import br.com.enterprise.mobileparking.dto.ConsultaCreditoDto;
import br.com.enterprise.mobileparking.exception.UsuarioNaoExistenteException;
import br.com.enterprise.mobileparking.model.Usuario;
import br.com.enterprise.mobileparking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditoUsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public CreditoUsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void adicionaCreditos(AdicionaCreditosDto dto) throws UsuarioNaoExistenteException {
        Optional<Usuario> usuarioOpt = consultaUsuario(dto.getUsuario());

        Usuario usuario = usuarioOpt.get();
        usuario.incrementaSaldo(dto.getValor());
        repository.save(usuario);
    }

    public ConsultaCreditoDto consultaCreditos(String usuarioId) throws UsuarioNaoExistenteException {
        Optional<Usuario> usuarioOpt = consultaUsuario(usuarioId);

        Usuario usuario = usuarioOpt.get();
        return new ConsultaCreditoDto(usuario.getId(), usuario.getSaldoAtualizado());
    }

    private Optional<Usuario> consultaUsuario(String usuario) throws UsuarioNaoExistenteException {
        Optional<Usuario> usuarioOpt = repository.findById(usuario);

        if (!usuarioOpt.isPresent()) {
            throw new UsuarioNaoExistenteException();
        }
        return usuarioOpt;
    }
}
