package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Sede;
import pe.edu.upc.repository.ISedeRepository;
import pe.edu.upc.service.ISedeService;

@Service
public class SedeServiceImpl implements ISedeService {

	@Autowired
	private ISedeRepository sR;

	@Override
	public Integer insertar(Sede sede) {
		
		int rpta = 0;
		if (rpta == 0) {
			sR.save(sede);
		}
		return rpta;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Sede> list() {
		// TODO Auto-generated method stub
		return sR.findAll();
	}
	
	

}
