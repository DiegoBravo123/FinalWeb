package pe.edu.upc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.entity.Admin;
import pe.edu.upc.repository.IAdminRepository;
import pe.edu.upc.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService{
	
	@Autowired
	private IAdminRepository aR;
	
	@Override
	public Integer insertar(Admin admin) {
		
		int rpta = 0;
		if (rpta == 0) {
			aR.save(admin);
		}
		return rpta;
	}

}
