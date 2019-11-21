package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.EstadoServicio;
import pe.edu.upc.entity.Mecanico;
import pe.edu.upc.entity.Servicio;

public interface IServicioService {
	public void insertar(Servicio servicio);

	public void eliminar(int idServicio);

	List<Servicio> listServiciosCliente(String username);

	List<Servicio> listServiciosMecanico(Mecanico mecanico);

	List<Servicio> listServiciosPendiente(EstadoServicio estadoServicio);

	Servicio findByIdServicio(int idServicio);
	
	
	List<Servicio> findByTipoAndCliente(String valor, String username);
	
	List<Servicio> findByVehiculoAndCliente(String valor, String username);
	
	List<Servicio> findByEstadoAndCliente(String valor, String username);
	
	
	List<Servicio> findByTipoAndEstado(String valor, String estado);
	
	List<Servicio> findByVehiculoAndEstado(String valor, String estado);
	
	List<Servicio> findBySedeAndEstado(String valor, String estado);
	
	List<Servicio> findByClienteAndEstado(String valor, String estado);
	
	
	List<Servicio> findByTipoAndMecanico(String valor, String username);
	
	List<Servicio> findByVehiculoAndMecanico(String valor, String username);
	
	List<Servicio> findBySedeAndMecanico(String valor, String username);
	
	List<Servicio> findByClienteAndMecanico(String valor, String username);
	
	List<Servicio> findByEstadoAndMecanico(String valor, String username);
}
