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

import pe.edu.upc.entity.Sede;
import pe.edu.upc.service.ISedeService;

@Controller
@RequestMapping("/sedes")
public class SedeController {
	
	@Autowired
	public ISedeService sS;
	
	@GetMapping("/new")
	@Secured({ "ROLE_ADMIN" })
	public String nuevaSede(Model model) {
		model.addAttribute("sede", new Sede());
		return "sede/sede";
	}
	
	@PostMapping("/save")
	public String saveSede(@Valid Sede sede, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "sede/sede";
		} else {
			int rpta = sS.insertar(sede);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "/sede/sede";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("listSedes", sS.list());

		return "/sede/sede";
	}
	
	@GetMapping("/list")
	public String listSedes(Model model) {
		try {
			model.addAttribute("sede", new Sede());
			model.addAttribute("listSedes", sS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/sede/listSedes";
	}

}
