package pe.edu.upc.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.EstadoServicio;
import pe.edu.upc.entity.Mecanico;
import pe.edu.upc.entity.Servicio;
import pe.edu.upc.repository.IServicioRepository;
import pe.edu.upc.service.IServicioService;

@Service
public class ServicioServiceImpl implements IServicioService {

	@Autowired
	private IServicioRepository sR;

	@Override
	@Transactional
	public void insertar(Servicio servicio) {
		// TODO Auto-generated method stub
		sR.save(servicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> listServiciosMecanico(Mecanico mecanico) {
		// TODO Auto-generated method stub
		return sR.findAllByMecanico(mecanico);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> listServiciosPendiente(EstadoServicio estadoServicio) {
		// TODO Auto-generated method stub
		return sR.findAllByEstadoServicio(estadoServicio);
	}

	@Override
	public Servicio findByIdServicio(int idServicio) {
		// TODO Auto-generated method stub
		return sR.findByIdServicio(idServicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> listServiciosCliente(String username) {
		// TODO Auto-generated method stub
		return sR.findServicioByCliente(username);
	}

	@Override
	@Transactional
	public void eliminar(int idServicio) {
		// TODO Auto-generated method stub
		sR.deleteById(idServicio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByTipoAndCliente(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByTipoAndCliente(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByVehiculoAndCliente(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByVehiculoAndCliente(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByEstadoAndCliente(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByEstadoAndCliente(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByTipoAndEstado(String valor, String estado) {
		// TODO Auto-generated method stub
		return sR.findByTipoAndEstado(valor, estado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByVehiculoAndEstado(String valor, String estado) {
		// TODO Auto-generated method stub
		return sR.findByVehiculoAndEstado(valor, estado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findBySedeAndEstado(String valor, String estado) {
		// TODO Auto-generated method stub
		return sR.findBySedeAndEstado(valor, estado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByClienteAndEstado(String valor, String estado) {
		// TODO Auto-generated method stub
		return sR.findByClienteAndEstado(valor, estado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByTipoAndMecanico(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByTipoAndMecanico(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByVehiculoAndMecanico(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByVehiculoAndMecanico(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findBySedeAndMecanico(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findBySedeAndMecanico(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByClienteAndMecanico(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByClienteAndMecanico(valor, username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByEstadoAndMecanico(String valor, String username) {
		// TODO Auto-generated method stub
		return sR.findByEstadoAndMecanico(valor, username);
	}

}
