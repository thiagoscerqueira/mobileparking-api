package br.com.enterprise.mobileparking.resource;

import br.com.enterprise.mobileparking.exception.EstacionamentoExpiradoException;
import br.com.enterprise.mobileparking.exception.EstacionamentoUsuarioNaoExistenteException;
import br.com.enterprise.mobileparking.service.TempoPermanenciaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tempoPermanencia")
@Api(value = "Tempo de permanência", description = "Recursos para busca de dados relacionados ao tempo de permanência")
public class TempoPermanenciaResource extends BaseResource{

    private final TempoPermanenciaService service;

    @Autowired
    public TempoPermanenciaResource(TempoPermanenciaService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Pesquisa tempos de permanência disponíveis para novo estacionamento")
    public ResponseEntity pesquisaTempoPermanenciaParaNovoEstacionamento() {
        return ResponseEntity.ok(service.consultaTempoPermanenciaParaNovoEstacionamento());
    }

    @GetMapping("/renovacao")
    @ApiOperation(value = "Pesquisa tempos de permanência disponíveis para renovação")
    public ResponseEntity pesquisaTempoPermanenciaParaRenovacao(@RequestParam("estacionamentoId") String estacionamentoId)
            throws EstacionamentoExpiradoException, EstacionamentoUsuarioNaoExistenteException {
        return ResponseEntity.ok(service.consultaTempoPermanenciaParaRenovacao(estacionamentoId));
    }
}
