package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.entity.Vehiculo;

public interface IVehiculoService {
	public void insertar(Vehiculo vehiculo);

	public void eliminar(int idVehiculo);

	List<Vehiculo> list(Cliente cliente);

	Vehiculo findByIdVehiculo(int idVehiculo);

	List<Vehiculo> findByPlaca(String valor, String username);
	
	List<Vehiculo> findByDescripcion(String valor, String username);
}
