package pe.edu.upc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Usuario;
import pe.edu.upc.repository.IUsuarioRepository;
import pe.edu.upc.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioRepository uR;

	@Override
	@Transactional
	public Integer insertar(Usuario usuario) {
		// TODO Auto-generated method stub
		int rpta = 0;
		if (rpta == 0) {
			uR.save(usuario);
		}
		return rpta;
	}

	@Override
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return uR.findByUsername(username);
	}

}
