package pe.edu.upc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Admin;
import pe.edu.upc.entity.Cliente;
import pe.edu.upc.entity.Mecanico;
import pe.edu.upc.entity.Usuario;
import pe.edu.upc.service.IAdminService;
import pe.edu.upc.service.IClienteService;
import pe.edu.upc.service.IMecanicoService;
import pe.edu.upc.service.IRolService;

@Controller
@RequestMapping("/registros")
public class RegistroController {

	@Autowired
	private IClienteService cS;

	@Autowired
	private IMecanicoService mS;

	@Autowired
	private IRolService rS;
	
	@Autowired
	private IAdminService aS;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/nuevo")
	public String nuevoUsuario(Model model) {

		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaRoles", rS.list());
		return "/registro";
	}

	@PostMapping("/guardar")
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status)
			throws Exception {

		if (result.hasErrors()) {
			model.addAttribute("listaRoles", rS.list());
			return "/registro";
		} else {

			switch (usuario.getRol().getNombreRol()) {
			case "Cliente":
				Cliente cliente = new Cliente();
				cliente.setNombreUsuario(usuario.getNombreUsuario());
				cliente.setDniUsuario(usuario.getDniUsuario());
				cliente.setEmailUsuario(usuario.getEmailUsuario());
				cliente.setTelefonoUsuario(usuario.getTelefonoUsuario());
				cliente.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
				cliente.setUsername(usuario.getUsername());
				cliente.setPassword(encoder.encode(usuario.getPassword()));
				cliente.setRol(usuario.getRol());
				cS.insertar(cliente);

				status.setComplete();
				break;
			case "Mecánico":
				Mecanico mecanico = new Mecanico();
				mecanico.setNombreUsuario(usuario.getNombreUsuario());
				mecanico.setDniUsuario(usuario.getDniUsuario());
				mecanico.setEmailUsuario(usuario.getEmailUsuario());
				mecanico.setTelefonoUsuario(usuario.getTelefonoUsuario());
				mecanico.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
				mecanico.setUsername(usuario.getUsername());
				mecanico.setPassword(encoder.encode(usuario.getPassword()));
				mecanico.setRol(usuario.getRol());
				mS.insertar(mecanico);

				status.setComplete();
				break;
				
			case "Admin":
				Admin admin = new Admin();
				admin.setNombreUsuario(usuario.getNombreUsuario());
				admin.setDniUsuario(usuario.getDniUsuario());
				admin.setEmailUsuario(usuario.getEmailUsuario());
				admin.setTelefonoUsuario(usuario.getTelefonoUsuario());
				admin.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
				admin.setUsername(usuario.getUsername());
				admin.setPassword(encoder.encode(usuario.getPassword()));
				admin.setRol(usuario.getRol());
				aS.insertar(admin);

				status.setComplete();
				break;
				
			}

			return "redirect:/login";
		}
	}
}
