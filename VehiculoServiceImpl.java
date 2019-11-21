package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.entity.Vehiculo;
import pe.edu.upc.repository.IVehiculoRepository;
import pe.edu.upc.service.IVehiculoService;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

	@Autowired
	private IVehiculoRepository vR;

	@Override
	@Transactional
	public void insertar(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		vR.save(vehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Vehiculo> list(Cliente cliente) {
		// TODO Auto-generated method stub
		return vR.findAllByCliente(cliente);
	}

	@Override
	public Vehiculo findByIdVehiculo(int idVehiculo) {
		// TODO Auto-generated method stub
		return vR.findByIdVehiculo(idVehiculo);
	}

	@Override
	@Transactional
	public void eliminar(int idVehiculo) {
		// TODO Auto-generated method stub
		vR.deleteById(idVehiculo);
	}

	@Override
	public List<Vehiculo> findByPlaca(String valor, String username) {
		// TODO Auto-generated method stub
		return vR.findByPlaca(valor, username);
	}

	@Override
	public List<Vehiculo> findByDescripcion(String valor, String username) {
		// TODO Auto-generated method stub
		return vR.findByDescripcion(valor, username);
	}

}
