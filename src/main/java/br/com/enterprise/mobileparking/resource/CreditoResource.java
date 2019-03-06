package br.com.enterprise.mobileparking.resource;

import br.com.enterprise.mobileparking.dto.AdicionaCreditosDto;
import br.com.enterprise.mobileparking.dto.ConsultaCreditoDto;
import br.com.enterprise.mobileparking.exception.UsuarioNaoExistenteException;
import br.com.enterprise.mobileparking.service.CreditoUsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/creditos")
@Api(value = "Créditos", description = "Recursos para manipulação dos créditos dos usuários")
public class CreditoResource extends BaseResource{

    private final CreditoUsuarioService creditoUsuarioService;

    @Autowired
    public CreditoResource(CreditoUsuarioService creditoUsuarioService) {
        this.creditoUsuarioService = creditoUsuarioService;
    }

    @GetMapping
    @ApiOperation(value = "Consulta créditos de determinado usuário")
    public ResponseEntity consultaCreditosUsuario(@RequestParam("usuarioId") String usuarioId)
            throws UsuarioNaoExistenteException {
        ConsultaCreditoDto consultaCreditoDto = creditoUsuarioService.consultaCreditos(usuarioId);

        return ResponseEntity.ok(consultaCreditoDto);
    }

    @PostMapping
    @ApiOperation(value = "Adiciona créditos a determinado usuário")
    public ResponseEntity adicionaCreditosUsuario(@RequestBody AdicionaCreditosDto dto)
            throws UsuarioNaoExistenteException {
        creditoUsuarioService.adicionaCreditos(dto);
        return ResponseEntity.ok().build();
    }
}
