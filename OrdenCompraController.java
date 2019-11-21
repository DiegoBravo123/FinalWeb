package pe.edu.upc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.OrdenCompra;
import pe.edu.upc.service.IOrdenCompraService;
import pe.edu.upc.service.IServicioService;

@Controller
@RequestMapping("/ordencompras")
public class OrdenCompraController {

	@Autowired
	private IServicioService sS;

	@Autowired
	private IOrdenCompraService ocS;

	@GetMapping("/nuevo/{idServicio}")
	public String nuevaOrdenCompra(@PathVariable(value = "idServicio") int idServicio, Model model) {

		OrdenCompra ordencompra = new OrdenCompra();
		ordencompra.setServicio(sS.findByIdServicio(idServicio));
		model.addAttribute("ordencompra", ordencompra);

		return "/ordencompra/ordencompra";
	}

	@PostMapping("/guardar")
	public String guardarOrdenCompra(@Valid OrdenCompra ordencompra, BindingResult result, Model model,
			SessionStatus status) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (result.hasErrors()) {
			return "/ordencompra/ordencompra";
		} else {
			ocS.insertar(ordencompra);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
		}

		model.addAttribute("listaOrdenCompra", ocS.listOrdenCompraByUsernameMecanico(authentication.getName()));

		return "/ordencompra/listaOrdenCompra";
	}

	@GetMapping("/listar")
	public String listarOrdenCompra(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			model.addAttribute("listaOrdenCompra", ocS.listOrdenCompraByUsernameMecanico(authentication.getName()));

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/ordencompra/listaOrdenCompra";
	}

}
