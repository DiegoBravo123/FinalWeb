package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.OrdenCompra;
import pe.edu.upc.entity.Servicio;
import pe.edu.upc.repository.IOrdenCompraRepository;
import pe.edu.upc.service.IOrdenCompraService;

@Service
public class OrdenCompraServiceImpl implements IOrdenCompraService {

	@Autowired
	private IOrdenCompraRepository ocR;

	@Override
	@Transactional
	public void insertar(OrdenCompra ordencompra) {
		// TODO Auto-generated method stub
		ocR.save(ordencompra);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenCompra> list(Servicio servicio) {
		// TODO Auto-generated method stub
		return ocR.findAllByServicio(servicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenCompra> listOrdenCompraByUsernameMecanico(String username) {
		// TODO Auto-generated method stub
		return ocR.findOrdenCompraByUsernameMecanico(username);
	}

}
