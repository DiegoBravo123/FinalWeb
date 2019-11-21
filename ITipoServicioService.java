package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.TipoServicio;

public interface ITipoServicioService {
	
	public Integer insertar(TipoServicio tiposervicio);
	
	public List<TipoServicio> list();
}
