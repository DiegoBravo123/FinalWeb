package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.TipoServicio;

@Repository
public interface ITipoServicioRepository extends JpaRepository<TipoServicio, Integer> {

}
