package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.FacturaServicio;
import pe.edu.upc.repository.IFacturaServicioRepository;
import pe.edu.upc.service.IFacturaServicioService;

@Service
public class FacturaServicioServiceImpl implements IFacturaServicioService {

	@Autowired
	private IFacturaServicioRepository fsR;

	@Override
	public void insertar(FacturaServicio facturaServicio) {
		// TODO Auto-generated method stub
		fsR.save(facturaServicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaServicio> listFacturaByUsernameMecanico(String username) {
		// TODO Auto-generated method stub
		return fsR.findFacturaByUsernameMecanico(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FacturaServicio> listFacturaByUsernameCliente(String username) {
		// TODO Auto-generated method stub
		return fsR.findFacturaByUsernameCliente(username);
	}
	@Override
	public FacturaServicio findByIdFactura(int idFactura) {
		// TODO Auto-generated method stub
		return fsR.findByIdFactura(idFactura);
	}

	
}
