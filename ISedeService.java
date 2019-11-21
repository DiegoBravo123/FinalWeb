package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Sede;

public interface ISedeService {
	
	public Integer insertar(Sede sede);
	public List<Sede> list();
}
