package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.FacturaServicio;

@Repository
public interface IFacturaServicioRepository extends JpaRepository<FacturaServicio, Integer> {

	FacturaServicio findByIdFactura(int idFactura);
	
	@Query("select f from FacturaServicio f where f.servicio.mecanico.username like %?1%")
	List<FacturaServicio> findFacturaByUsernameMecanico(String username);

	@Query("select f from FacturaServicio f where f.servicio.vehiculo.cliente.username like %?1%")
	List<FacturaServicio> findFacturaByUsernameCliente(String username);
}
