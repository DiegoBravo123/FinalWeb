package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.OrdenCompra;
import pe.edu.upc.entity.Servicio;

public interface IOrdenCompraService {
	public void insertar(OrdenCompra ordencompra);

	List<OrdenCompra> list(Servicio servicio);

	List<OrdenCompra> listOrdenCompraByUsernameMecanico(String username);
}
