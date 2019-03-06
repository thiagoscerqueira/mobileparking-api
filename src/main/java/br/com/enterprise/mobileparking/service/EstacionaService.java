package br.com.enterprise.mobileparking.service;

import br.com.enterprise.mobileparking.dto.ConsultaEstacionaDto;
import br.com.enterprise.mobileparking.dto.EstacionaDto;
import br.com.enterprise.mobileparking.dto.FinalizaEstacionamentoDto;
import br.com.enterprise.mobileparking.dto.RenovaEstacionamentoDto;
import br.com.enterprise.mobileparking.exception.*;
import br.com.enterprise.mobileparking.model.EstacionamentoUsuario;
import br.com.enterprise.mobileparking.model.TempoPermanencia;
import br.com.enterprise.mobileparking.model.Usuario;
import br.com.enterprise.mobileparking.repository.EstacionamentoUsuarioRepository;
import br.com.enterprise.mobileparking.repository.TempoPermanenciaRepository;
import br.com.enterprise.mobileparking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EstacionaService {

    private final UsuarioRepository usuarioRepository;
    private final EstacionamentoUsuarioRepository estacionamentoUsuarioRepository;
    private TempoPermanenciaRepository tempoPermanenciaRepository;
    private BuscaEstacionaService buscaEstacionaService;

    @Autowired
    public EstacionaService(UsuarioRepository usuarioRepository,
                            EstacionamentoUsuarioRepository estacionamentoUsuarioRepository,
                            TempoPermanenciaRepository tempoPermanenciaRepository,
                            BuscaEstacionaService buscaEstacionaService) {
        this.usuarioRepository = usuarioRepository;
        this.estacionamentoUsuarioRepository = estacionamentoUsuarioRepository;
        this.tempoPermanenciaRepository = tempoPermanenciaRepository;
        this.buscaEstacionaService = buscaEstacionaService;
    }

    @Transactional(rollbackFor = Exception.class)
    public ConsultaEstacionaDto estaciona(EstacionaDto dto) throws UsuarioNaoExistenteException,
            TempoPermanenciaInvalidoException, UsuarioNaoPossuiSaldoSuficienteException,
            ExisteEstacionamentoVigenteParaUsuarioException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getUsuario());
        if (!usuarioOpt.isPresent()) {
            throw new UsuarioNaoExistenteException();
        }

        Usuario usuario = usuarioOpt.get();
        finalizaUltimoEstacionamentoExpirado(usuario);

        Optional<TempoPermanencia> taxaPermanenciaOpt = tempoPermanenciaRepository.findById(dto.getTempoPermanencia());

        if (!taxaPermanenciaOpt.isPresent()) {
            throw new TempoPermanenciaInvalidoException();
        }

        TempoPermanencia tempoPermanencia = taxaPermanenciaOpt.get();

        usuario.decrementaSaldo(tempoPermanencia.getValor());
        usuarioRepository.save(usuario);

        EstacionamentoUsuario estacionamentoUsuario = new EstacionamentoUsuario();
        estacionamentoUsuario.setUsuario(usuario);
        estacionamentoUsuario.setDataHoraInicio(LocalDateTime.now());
        estacionamentoUsuario.setPlacaVeiculo(dto.getPlacaVeiculo());
        estacionamentoUsuario.setTempoPermanencia(tempoPermanencia);
        estacionamentoUsuarioRepository.save(estacionamentoUsuario);

        return buscaEstacionaService.buildRetornoEstacionaDto(estacionamentoUsuario);
    }

    private void finalizaUltimoEstacionamentoExpirado(Usuario usuario) throws ExisteEstacionamentoVigenteParaUsuarioException {
        EstacionamentoUsuario ultimoEstacionamentoUsuario = buscaEstacionaService.buscaUltimoEstacionamento(usuario);

        if (ultimoEstacionamentoUsuario == null) return;
        ultimoEstacionamentoUsuario.finaliza();
        estacionamentoUsuarioRepository.save(ultimoEstacionamentoUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public ConsultaEstacionaDto renova(RenovaEstacionamentoDto dto)
            throws EstacionamentoUsuarioNaoExistenteException, TempoPermanenciaInvalidoException,
            UsuarioNaoPossuiSaldoSuficienteException, EstacionamentoExpiradoException {
        EstacionamentoUsuario estacionamentoUsuario =
                buscaEstacionaService.buscaEstacionamentoVigente(dto.getEstacionamentoUsuarioId());

        Optional<TempoPermanencia> novaTaxaPermanenciaOpt = tempoPermanenciaRepository.findAll().stream()
                .filter(item -> item.getTempo()
                        .equals(estacionamentoUsuario.getTempoPermanencia().getTempo() + dto.getTempoPermanencia()))
                .findFirst();

        if (!novaTaxaPermanenciaOpt.isPresent()) {
            throw new TempoPermanenciaInvalidoException();
        }

        TempoPermanencia novaTempoPermanencia = novaTaxaPermanenciaOpt.get();
        estacionamentoUsuario.renova(novaTempoPermanencia);
        estacionamentoUsuarioRepository.save(estacionamentoUsuario);

        return buscaEstacionaService.buildRetornoEstacionaDto(estacionamentoUsuario);
    }

    public void finaliza(FinalizaEstacionamentoDto dto) throws EstacionamentoUsuarioNaoExistenteException,
            EstacionamentoExpiradoException {
        EstacionamentoUsuario estacionamentoUsuario =
                buscaEstacionaService.buscaEstacionamentoVigente(dto.getEstacionamentoUsuarioId());

        estacionamentoUsuario.setDataHoraFim(LocalDateTime.now());
        estacionamentoUsuarioRepository.save(estacionamentoUsuario);
    }


}
