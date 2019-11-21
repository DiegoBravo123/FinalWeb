package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.TipoServicio;
import pe.edu.upc.repository.ITipoServicioRepository;
import pe.edu.upc.service.ITipoServicioService;

@Service
public class TipoServicioServiceImpl implements ITipoServicioService {

	@Autowired
	private ITipoServicioRepository tsR;

	@Override
	public Integer insertar(TipoServicio tiposervicio) {
		
		int rpta = 0;
		if (rpta == 0) {
			tsR.save(tiposervicio);
		}
		return rpta;
	}
	@Override
	@Transactional(readOnly = true)
	public List<TipoServicio> list() {
		// TODO Auto-generated method stub
		return tsR.findAll();
	}

}
