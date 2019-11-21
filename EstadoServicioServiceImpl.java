package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.EstadoServicio;
import pe.edu.upc.repository.IEstadoServicioRepository;
import pe.edu.upc.service.IEstadoServicioService;

@Service
public class EstadoServicioServiceImpl implements IEstadoServicioService {

	@Autowired
	private IEstadoServicioRepository esR;

	@Override
	public Integer insertar(EstadoServicio estadoServicio) {
		
		int rpta = 0;
		if (rpta == 0) {
			esR.save(estadoServicio);
		}
		return rpta;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<EstadoServicio> list() {
		// TODO Auto-generated method stub
		return esR.findAll();
	}

}
