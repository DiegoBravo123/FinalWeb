package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.EstadoServicio;

@Repository
public interface IEstadoServicioRepository extends JpaRepository<EstadoServicio, Integer> {

}
