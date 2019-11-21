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

import pe.edu.upc.entity.TipoServicio;
import pe.edu.upc.service.ITipoServicioService;

@Controller
@RequestMapping("/tiposervicios")
public class TipoServicioController {
	

	@Autowired
	private ITipoServicioService tsS;
	
	@GetMapping("/new")
	@Secured({ "ROLE_ADMIN" })
	public String nuevotipoServicio(Model model) {
		model.addAttribute("tipoServicio", new TipoServicio());
		return "tipoServicio/tipoServicio";
	}
	
	@PostMapping("/save")
	public String savetipoServicio(@Valid TipoServicio tipoServicio, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "tipoServicio/tipoServicio";
		} else {
			int rpta = tsS.insertar(tipoServicio);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/tipoServicio/tipoServicio";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listTipoServicios", tsS.list());

		return "/tipoServicio/tipoServicio";
	}
	
	@GetMapping("/list")
	public String listtipoServicios(Model model) {
		try {
			model.addAttribute("tipoServicio", new TipoServicio());
			model.addAttribute("listTipoServicios", tsS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/tipoServicio/listTipoServicios";
	}

}
