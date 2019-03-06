package br.com.enterprise.mobileparking.repository;

import br.com.enterprise.mobileparking.model.TempoPermanencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempoPermanenciaRepository extends JpaRepository<TempoPermanencia, Integer> {


}
