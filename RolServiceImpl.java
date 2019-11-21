package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Rol;
import pe.edu.upc.repository.IRolRepository;
import pe.edu.upc.service.IRolService;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	private IRolRepository rR;

	@Override
	@Transactional(readOnly = true)
	public List<Rol> list() {
		// TODO Auto-generated method stub
		return rR.findAll();
	}

}
