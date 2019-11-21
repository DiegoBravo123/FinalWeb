package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.FacturaServicio;

public interface IFacturaServicioService {
	public void insertar(FacturaServicio facturaServicio);

	List<FacturaServicio> listFacturaByUsernameMecanico(String username);

	List<FacturaServicio> listFacturaByUsernameCliente(String username);
	
	FacturaServicio findByIdFactura(int idFactura);

}
