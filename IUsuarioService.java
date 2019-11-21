package pe.edu.upc.service;

import pe.edu.upc.entity.Usuario;

public interface IUsuarioService {
	public Integer insertar(Usuario usuario);

	public Usuario findByUsername(String username);
}
