package br.com.enterprise.mobileparking.resource;

import br.com.enterprise.mobileparking.dto.ConsultaEstacionaDto;
import br.com.enterprise.mobileparking.dto.EstacionaDto;
import br.com.enterprise.mobileparking.dto.FinalizaEstacionamentoDto;
import br.com.enterprise.mobileparking.dto.RenovaEstacionamentoDto;
import br.com.enterprise.mobileparking.exception.*;
import br.com.enterprise.mobileparking.service.BuscaEstacionaService;
import br.com.enterprise.mobileparking.service.EstacionaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/estacionamento")
@Api(value = "Estacionamento", description = "Recursos para manipulação dos estacionamentos dos usuários")
public class EstacionamentoResource extends BaseResource{

    private final EstacionaService estacionaService;
    private BuscaEstacionaService buscaEstacionaService;

    @Autowired
    public EstacionamentoResource(EstacionaService estacionaService,
                                  BuscaEstacionaService buscaEstacionaService) {
        this.estacionaService = estacionaService;
        this.buscaEstacionaService = buscaEstacionaService;
    }

    @PostMapping
    @ApiOperation(value = "Registra estacionamento do usuário")
    public ResponseEntity estaciona(@RequestBody EstacionaDto estacionaDto)
            throws ExisteEstacionamentoVigenteParaUsuarioException, UsuarioNaoPossuiSaldoSuficienteException,
            UsuarioNaoExistenteException, TempoPermanenciaInvalidoException {
        ConsultaEstacionaDto estaciona = estacionaService.estaciona(estacionaDto);
        return ResponseEntity.ok(estaciona);
    }

    @GetMapping
    @ApiOperation(value = "Consulta estacionamento em vigência retornando suas informações")
    public ResponseEntity consulta(@RequestParam("estacionamentoId") String estacionamentoId)
            throws EstacionamentoUsuarioNaoExistenteException, EstacionamentoExpiradoException {
        ConsultaEstacionaDto consultaEstacionaDto = buscaEstacionaService.consultaDadosEstacionamento(estacionamentoId);
        return ResponseEntity.ok(consultaEstacionaDto);
    }

    @GetMapping("/vigente")
    @ApiOperation(value = "Consulta estacionamento em vigência de determinado usuário")
    public ResponseEntity consultaVigente(@RequestParam("usuarioId") String usuarioId)
            throws EstacionamentoVigenteNaoExistenteException {
        ConsultaEstacionaDto consultaEstacionaDto = buscaEstacionaService.consultaEstacionamentoVigenteUsuario(usuarioId);
        return ResponseEntity.ok(consultaEstacionaDto);
    }

    @PostMapping(value = "/renova")
    @ApiOperation(value = "Renova estacionamento em vigência")
    public ResponseEntity renova(@RequestBody RenovaEstacionamentoDto dto)
            throws UsuarioNaoPossuiSaldoSuficienteException, TempoPermanenciaInvalidoException,
            EstacionamentoUsuarioNaoExistenteException, EstacionamentoExpiradoException {
        ConsultaEstacionaDto estaciona = estacionaService.renova(dto);
        return ResponseEntity.ok(estaciona);
    }

    @PostMapping(value = "/finaliza")
    @ApiOperation(value = "Finaliza estacionamento em vigência")
    public ResponseEntity finaliza(@RequestBody FinalizaEstacionamentoDto dto)
            throws EstacionamentoUsuarioNaoExistenteException, EstacionamentoExpiradoException {
        estacionaService.finaliza(dto);
        return ResponseEntity.ok().build();
    }
}
