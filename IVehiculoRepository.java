package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.entity.Vehiculo;

@Repository
public interface IVehiculoRepository extends JpaRepository<Vehiculo, Integer> {
	List<Vehiculo> findAllByCliente(Cliente cliente);

	Vehiculo findByIdVehiculo(int idVehiculo);
	
	@Query("select v from Vehiculo v where v.placaVehiculo like %?1% and v.cliente.username like %?2%")
	List<Vehiculo> findByPlaca(String valor, String username);
	
	@Query("select v from Vehiculo v where v.descripcionVehiculo like %?1% and v.cliente.username like %?2%")
	List<Vehiculo> findByDescripcion(String valor, String username);
}
