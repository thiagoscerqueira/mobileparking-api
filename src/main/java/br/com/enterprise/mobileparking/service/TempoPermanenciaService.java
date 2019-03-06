package br.com.enterprise.mobileparking.service;

import br.com.enterprise.mobileparking.exception.EstacionamentoExpiradoException;
import br.com.enterprise.mobileparking.exception.EstacionamentoUsuarioNaoExistenteException;
import br.com.enterprise.mobileparking.model.EstacionamentoUsuario;
import br.com.enterprise.mobileparking.model.TempoPermanencia;
import br.com.enterprise.mobileparking.repository.TempoPermanenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempoPermanenciaService {

    private final TempoPermanenciaRepository tempoPermanenciaRepository;
    private BuscaEstacionaService buscaEstacionaService;

    @Autowired
    public TempoPermanenciaService(TempoPermanenciaRepository tempoPermanenciaRepository,
                                   BuscaEstacionaService buscaEstacionaService) {
        this.tempoPermanenciaRepository = tempoPermanenciaRepository;
        this.buscaEstacionaService = buscaEstacionaService;
    }

    public List<TempoPermanencia> consultaTempoPermanenciaParaNovoEstacionamento() {
        return tempoPermanenciaRepository.findAll();
    }

    public List<TempoPermanencia> consultaTempoPermanenciaParaRenovacao(String estacionamentoId)
            throws EstacionamentoExpiradoException, EstacionamentoUsuarioNaoExistenteException {
        EstacionamentoUsuario estacionamentoUsuario =
                buscaEstacionaService.buscaEstacionamentoVigente(estacionamentoId);

        List<TempoPermanencia> listaPermanenciasCadastradas = consultaTempoPermanenciaParaNovoEstacionamento();

        TempoPermanencia tempoPermanencia = estacionamentoUsuario.getTempoPermanencia();

        return listaPermanenciasCadastradas.stream()
                .filter(item -> item.getTempo() > tempoPermanencia.getTempo())
                .map(item -> new TempoPermanencia(item.getTempo() - tempoPermanencia.getTempo(),
                        item.getValor().subtract(tempoPermanencia.getValor())))
                .collect(Collectors.toList());
    }
}
