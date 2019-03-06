package br.com.enterprise.mobileparking.resource;

import br.com.enterprise.mobileparking.dto.ErroDto;
import br.com.enterprise.mobileparking.exception.RegraNegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseResource {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity handleRegraNegocio(RegraNegocioException ex) {
        return ResponseEntity
                .status(ex.getMensagem().getStatus())
                .body(new ErroDto(ex.getMensagem()));
    }
}
