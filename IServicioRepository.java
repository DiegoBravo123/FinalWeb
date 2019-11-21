package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.EstadoServicio;
import pe.edu.upc.entity.Mecanico;
import pe.edu.upc.entity.Servicio;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

	@Query("select s from Servicio s where s.vehiculo.cliente.username like %?1%")
	List<Servicio> findServicioByCliente(String username);

	List<Servicio> findAllByMecanico(Mecanico mecanico);

	List<Servicio> findAllByEstadoServicio(EstadoServicio estadoServicio);

	Servicio findByIdServicio(int idServicio);
	
	
	@Query("select s from Servicio s where s.tipoServicio.nombreTipoServicio like %?1% and s.vehiculo.cliente.username like %?2%")
	List<Servicio> findByTipoAndCliente(String valor, String username);
	
	@Query("select s from Servicio s where s.vehiculo.placaVehiculo like %?1% and s.vehiculo.cliente.username like %?2%")
	List<Servicio> findByVehiculoAndCliente(String valor, String username);
	
	@Query("select s from Servicio s where s.estadoServicio.nombreEstadoServicio like %?1% and s.vehiculo.cliente.username like %?2%")
	List<Servicio> findByEstadoAndCliente(String valor, String username);
	
	
	@Query("select s from Servicio s where s.tipoServicio.nombreTipoServicio like %?1% and s.estadoServicio.nombreEstadoServicio like %?2%")
	List<Servicio> findByTipoAndEstado(String valor, String estado);
	
	@Query("select s from Servicio s where s.vehiculo.placaVehiculo like %?1% and s.estadoServicio.nombreEstadoServicio like %?2%")
	List<Servicio> findByVehiculoAndEstado(String valor, String estado);
		
	@Query("select s from Servicio s where s.sede.nombreSede like %?1% and s.estadoServicio.nombreEstadoServicio like %?2%")
	List<Servicio> findBySedeAndEstado(String valor, String estado);
	
	@Query("select s from Servicio s where s.vehiculo.cliente.nombreUsuario like %?1% and s.estadoServicio.nombreEstadoServicio like %?2%")
	List<Servicio> findByClienteAndEstado(String valor, String estado);
	
	
	@Query("select s from Servicio s where s.tipoServicio.nombreTipoServicio like %?1% and s.mecanico.username like %?2%")
	List<Servicio> findByTipoAndMecanico(String valor, String username);
	
	@Query("select s from Servicio s where s.vehiculo.placaVehiculo like %?1% and s.mecanico.username like %?2%")
	List<Servicio> findByVehiculoAndMecanico(String valor, String username);
		
	@Query("select s from Servicio s where s.sede.nombreSede like %?1% and s.mecanico.username like %?2%")
	List<Servicio> findBySedeAndMecanico(String valor, String username);
	
	@Query("select s from Servicio s where s.vehiculo.cliente.nombreUsuario like %?1% and s.mecanico.username like %?2%")
	List<Servicio> findByClienteAndMecanico(String valor, String username);
	
	@Query("select s from Servicio s where s.estadoServicio.nombreEstadoServicio like %?1% and s.mecanico.username like %?2%")
	List<Servicio> findByEstadoAndMecanico(String valor, String username);
}
