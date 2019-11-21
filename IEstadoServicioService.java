package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.EstadoServicio;

public interface IEstadoServicioService {
	
	public Integer insertar(EstadoServicio estadoServicio);
	public List<EstadoServicio> list();
}
