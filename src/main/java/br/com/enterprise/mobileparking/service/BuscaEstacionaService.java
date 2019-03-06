package br.com.enterprise.mobileparking.service;

import br.com.enterprise.mobileparking.dto.ConsultaEstacionaDto;
import br.com.enterprise.mobileparking.exception.EstacionamentoExpiradoException;
import br.com.enterprise.mobileparking.exception.EstacionamentoUsuarioNaoExistenteException;
import br.com.enterprise.mobileparking.exception.EstacionamentoVigenteNaoExistenteException;
import br.com.enterprise.mobileparking.exception.ExisteEstacionamentoVigenteParaUsuarioException;
import br.com.enterprise.mobileparking.model.EstacionamentoUsuario;
import br.com.enterprise.mobileparking.model.Usuario;
import br.com.enterprise.mobileparking.repository.EstacionamentoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuscaEstacionaService {

    private final EstacionamentoUsuarioRepository estacionamentoUsuarioRepository;

    @Autowired
    public BuscaEstacionaService(EstacionamentoUsuarioRepository estacionamentoUsuarioRepository) {
        this.estacionamentoUsuarioRepository = estacionamentoUsuarioRepository;
    }

    public EstacionamentoUsuario buscaEstacionamentoVigente(String estacionamentoUsuarioId)
            throws EstacionamentoUsuarioNaoExistenteException, EstacionamentoExpiradoException {
        Optional<EstacionamentoUsuario> estacionamentoOpt =
                estacionamentoUsuarioRepository.findById(UUID.fromString(estacionamentoUsuarioId));

        if (!estacionamentoOpt.isPresent()) {
            throw new EstacionamentoUsuarioNaoExistenteException();
        }
        EstacionamentoUsuario estacionamentoUsuario = estacionamentoOpt.get();

        if (estacionamentoUsuario.isExpirado()) {
            throw new EstacionamentoExpiradoException();
        }

        return estacionamentoUsuario;
    }

    public ConsultaEstacionaDto consultaDadosEstacionamento(String estacionamentoId)
            throws EstacionamentoUsuarioNaoExistenteException, EstacionamentoExpiradoException {
        return buildRetornoEstacionaDto(buscaEstacionamentoVigente(estacionamentoId));
    }

    public ConsultaEstacionaDto consultaEstacionamentoVigenteUsuario(String usuarioId)
            throws EstacionamentoVigenteNaoExistenteException {
        EstacionamentoUsuario ultimoEstacionamentoUsuario = buscaUltimoEstacionamento(new Usuario(usuarioId));

        if (ultimoEstacionamentoUsuario == null) {
            throw new EstacionamentoVigenteNaoExistenteException();
        }

        return finalizaUltimoEstacionamentoSeExpiradoOuRetornaDadosSeVigente(ultimoEstacionamentoUsuario);
    }

    private ConsultaEstacionaDto finalizaUltimoEstacionamentoSeExpiradoOuRetornaDadosSeVigente(EstacionamentoUsuario estacionamentoUsuario)
            throws EstacionamentoVigenteNaoExistenteException {
        try {
            estacionamentoUsuario.finaliza();
            estacionamentoUsuarioRepository.save(estacionamentoUsuario);
            throw new EstacionamentoVigenteNaoExistenteException();
        } catch (ExisteEstacionamentoVigenteParaUsuarioException e) {
            return buildRetornoEstacionaDto(estacionamentoUsuario);
        }
    }

    public EstacionamentoUsuario buscaUltimoEstacionamento(Usuario usuario) {
        Optional<EstacionamentoUsuario> ultimoEstacionamentoOpt =
                estacionamentoUsuarioRepository.findFirstByUsuarioOrderByDataHoraInicioDesc(usuario);

        return ultimoEstacionamentoOpt.orElse(null);
    }

    public ConsultaEstacionaDto buildRetornoEstacionaDto(EstacionamentoUsuario estacionamentoUsuario) {
        ConsultaEstacionaDto consultaEstacionaDto = new ConsultaEstacionaDto();
        consultaEstacionaDto.setEstacionamentoUsuarioId(estacionamentoUsuario.getId().toString());
        consultaEstacionaDto.setTempoRestante(estacionamentoUsuario.calculaTempoRestante());
        consultaEstacionaDto.setInicio(estacionamentoUsuario.getDataHoraInicio());
        consultaEstacionaDto.setExpirado(estacionamentoUsuario.isExpirado());
        return consultaEstacionaDto;
    }

}
