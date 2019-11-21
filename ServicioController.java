package pe.edu.upc.controller;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.entity.Mecanico;
import pe.edu.upc.entity.Servicio;
import pe.edu.upc.service.IClienteService;
import pe.edu.upc.service.IEstadoServicioService;
import pe.edu.upc.service.IMecanicoService;
import pe.edu.upc.service.ISedeService;
import pe.edu.upc.service.IServicioService;
import pe.edu.upc.service.ITipoServicioService;
import pe.edu.upc.service.IUploadFileService;
import pe.edu.upc.service.IUsuarioService;
import pe.edu.upc.service.IVehiculoService;

@Controller
@RequestMapping("servicios")
public class ServicioController {

	@Autowired
	private IServicioService sS;

	@Autowired
	private ITipoServicioService tsS;

	@Autowired
	private ISedeService sdS;

	@Autowired
	private IEstadoServicioService esS;

	@Autowired
	private IVehiculoService vS;

	@Autowired
	private IClienteService cS;

	@Autowired
	private IMecanicoService mS;

	@Autowired
	private IUsuarioService uS;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/nuevo")
	@Secured({ "ROLE_CLIENTE" })
	public String nuevoServicio(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = new Cliente();
		cliente = cS.findByUsername(authentication.getName());

		model.addAttribute("servicio", new Servicio());

		model.addAttribute("listaTipoServicios", tsS.list());
		model.addAttribute("listaSedes", sdS.list());
		model.addAttribute("listaVehiculos", vS.list(cliente));

		return "servicio/servicio";
	}

	@PostMapping("/guardar")
	@Secured({ "ROLE_CLIENTE" })
	public String guardarServicio(@Valid Servicio servicio, BindingResult result, Model model, SessionStatus status)
			throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = new Cliente();
		cliente = cS.findByUsername(authentication.getName());

		if (result.hasErrors()) {
			model.addAttribute("listaTipoServicios", tsS.list());
			model.addAttribute("listaSedes", sdS.list());
			model.addAttribute("listaVehiculos", vS.list(cliente));

			return "servicio/servicio";
		} else {

			servicio.setEstadoServicio(esS.list().get(0));
			servicio.setMecanico(null);

			Date fecha = new Date();
			servicio.setFechaServicio(fecha);

			sS.insertar(servicio);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
		}
		model.addAttribute("listaServicios", sS.listServiciosCliente(authentication.getName()));

		return "/servicio/listaMisServicios";
	}

	@GetMapping("/listarMisServicios")
	@Secured({ "ROLE_CLIENTE" })
	public String listarMisServicios(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			model.addAttribute("listaServicios", sS.listServiciosCliente(authentication.getName()));

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/servicio/listaMisServicios";
	}

	@GetMapping("/listarServicios")
	@Secured({ "ROLE_MECANICO" })
	public String listarServiciosPendientes(Model model) {
		try {

			model.addAttribute("listaServicios", sS.listServiciosPendiente(esS.list().get(0)));// Por confirmar

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/servicio/listaServiciosPendiente";
	}

	@RequestMapping("/aceptarSolicitud")
	@Secured({ "ROLE_MECANICO" })
	public String aceptarSolicitud(Map<String, Object> model, @RequestParam(value = "idServicio") Integer idServicio) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			Mecanico mecanico = new Mecanico();
			mecanico = mS.findByUsername(authentication.getName());

			if (idServicio != null && idServicio > 0) {
				Servicio servicio = new Servicio();
				servicio = sS.findByIdServicio(idServicio);
				servicio.setEstadoServicio(esS.list().get(1));
				servicio.setMecanico(mecanico);
				sS.insertar(servicio);
				model.put("mensaje", "Se guardaron los cambios");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Hubo un error");
		}
		model.put("listaServicios", sS.listServiciosPendiente(esS.list().get(0)));

		return "/servicio/listaServiciosPendiente";
	}

	@GetMapping("/listarServiciosAtendidos")
	@Secured({ "ROLE_MECANICO" })
	public String listarServiciosAtendidos(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			Mecanico mecanico = new Mecanico();
			mecanico = mS.findByUsername(authentication.getName());

			model.addAttribute("listaServicios", sS.listServiciosMecanico(mecanico));

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/servicio/listaServiciosMecanico";
	}

	@RequestMapping("/eliminar")
	@Secured({ "ROLE_CLIENTE" })
	public String eliminarServicio(Model model, @RequestParam(value = "id") Integer id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		try {
			if (sS.findByIdServicio(id).getEstadoServicio().getIdEstadoServicio() == 1) {// POR CONFIRMAR
				if (id != null && id > 0) {
					sS.eliminar(id);
					model.addAttribute("mensaje", "Se eliminó correctamente");
				}
			} else {
				model.addAttribute("mensaje", "No se puede eliminar servicios en proceso o finalizados");
				model.addAttribute("listaServicios", sS.listServiciosCliente(authentication.getName()));
				return "/servicio/listaMisServicios";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute("mensaje", "No se puede eliminar servicios en proceso o finalizados");
		}

		model.addAttribute("listaServicios", sS.listServiciosCliente(authentication.getName()));
		return "/servicio/listaMisServicios";
	}

	@GetMapping("/actualizar/{id}")
	@Secured({ "ROLE_CLIENTE" })
	public String actualizarServicio(@PathVariable(value = "id") int id, Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (sS.findByIdServicio(id).getEstadoServicio().getIdEstadoServicio() == 1) {// POR CONFIRMAR
				Servicio servicio = new Servicio();
				servicio = sS.findByIdServicio(id);

				model.addAttribute("servicio", servicio);

				model.addAttribute("listaTipoServicios", tsS.list());
				model.addAttribute("listaSedes", sdS.list());
				model.addAttribute("listaVehiculos", vS.list(cS.findByUsername(authentication.getName())));

			} else {
				model.addAttribute("mensaje", "No se puede actualizar servicios en proceso o finalizados");
				model.addAttribute("listaServicios", sS.listServiciosCliente(authentication.getName()));
				return "/servicio/listaMisServiciosroo";
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/servicio/actualizar";
	}

	@GetMapping("/detalle/{id}")
	@Secured({ "ROLE_CLIENTE", "ROLE_MECANICO" })
	public String detalleServicio(@PathVariable(value = "id") int id, Model model) {
		try {
			Servicio servicio = new Servicio();
			servicio = sS.findByIdServicio(id);
			model.addAttribute("servicio", servicio);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (uS.findByUsername(authentication.getName()).getRol().getId() == 1) { // Cliente
				model.addAttribute("ruta", "/servicios/listarMisServicios");
			} else { // Mecánico
				if (sS.findByIdServicio(id).getEstadoServicio().getIdEstadoServicio() == 1) { // Por Confirmar
					model.addAttribute("ruta", "/servicios/listarServicios");
				} else { // En proceso - Finalizado
					model.addAttribute("ruta", "/servicios/listarServiciosAtendidos");
				}
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/servicio/detalle";
	}

	@GetMapping(value = "/uploads/{filename:.+}")
	@Secured({ "ROLE_CLIENTE", "ROLE_MECANICO" })
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@RequestMapping("/buscarMisServicios")
	@Secured({ "ROLE_CLIENTE" })
	public String buscarMisServicio(Model model, @RequestParam("busqueda") String busqueda,
			@RequestParam("valor") String valor) {

		switch (busqueda) {
		case "tipo":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByTipoAndCliente(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaMisServicios";
		case "vehiculo":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByVehiculoAndCliente(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaMisServicios";
		case "estado":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByEstadoAndCliente(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaMisServicios";
		default:
			return "/servicio/listaMisServicios";
		}
	}

	@RequestMapping("/buscarServiciosPendientes")
	@Secured({ "ROLE_MECANICO" })
	public String buscarServiciosPendientes(Model model, @RequestParam("busqueda") String busqueda,
			@RequestParam("valor") String valor) {

		switch (busqueda) {
		case "tipo":
			try {
				List<Servicio> listaServicios;
				listaServicios = sS.findByTipoAndEstado(valor, "Por confirmar");

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosPendiente";
		case "vehiculo":
			try {
				List<Servicio> listaServicios;
				listaServicios = sS.findByVehiculoAndEstado(valor, "Por confirmar");

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosPendiente";
		case "sede":
			try {
				List<Servicio> listaServicios;
				listaServicios = sS.findBySedeAndEstado(valor, "Por confirmar");

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosPendiente";
		case "cliente":
			try {
				List<Servicio> listaServicios;
				listaServicios = sS.findByClienteAndEstado(valor, "Por confirmar");

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosPendiente";
		default:
			return "/servicio/listaServiciosPendiente";
		}
	}

	@RequestMapping("/buscarServiciosAtendidos")
	@Secured({ "ROLE_MECANICO" })
	public String buscarServiciosAtendidos(Model model, @RequestParam("busqueda") String busqueda,
			@RequestParam("valor") String valor) {

		switch (busqueda) {
		case "tipo":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByTipoAndMecanico(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosMecanico";
		case "vehiculo":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByVehiculoAndMecanico(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosMecanico";
		case "sede":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findBySedeAndMecanico(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosMecanico";
		case "cliente":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByClienteAndMecanico(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosMecanico";
		case "estado":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Servicio> listaServicios;
				listaServicios = sS.findByEstadoAndMecanico(valor, authentication.getName());

				model.addAttribute("listaServicios", listaServicios);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/servicio/listaServiciosMecanico";
		default:
			return "/servicio/listaServiciosMecanico";
		}
	}
}
