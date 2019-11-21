package pe.edu.upc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.EstadoServicio;
import pe.edu.upc.service.IEstadoServicioService;

@Controller
@RequestMapping("/estadoServicios")
public class EstadoServicioController {
	
	@Autowired
	private IEstadoServicioService esS;
	
	@GetMapping("/new")
	@Secured({ "ROLE_ADMIN" })
	public String nuevoEstadoServicio(Model model) {
		model.addAttribute("estadoServicio", new EstadoServicio());
		return "estadoServicio/estadoServicio";
	}
	
	@PostMapping("/save")
	public String saveEstadoServicio(@Valid EstadoServicio estadoServicio, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "estadoServicio/estadoServicio";
		} else {
			int rpta = esS.insertar(estadoServicio);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/estadoServicio/estadoServicio";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listEstadoServicios", esS.list());

		return "/estadoServicio/estadoServicio";
	}
	
	@GetMapping("/list")
	public String listEstadoServicios(Model model) {
		try {
			model.addAttribute("estadoServicio", new EstadoServicio());
			model.addAttribute("listEstadoServicios", esS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/estadoServicio/listEstadoServicios";
	}

}
