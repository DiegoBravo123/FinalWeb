package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import pe.edu.upc.entity.FacturaServicio;

import pe.edu.upc.entity.Servicio;


import pe.edu.upc.service.IEstadoServicioService;
import pe.edu.upc.service.IFacturaServicioService;

import pe.edu.upc.service.IServicioService;
import pe.edu.upc.service.IUploadFileService;
import pe.edu.upc.service.IUsuarioService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

	@Autowired
	private IFacturaServicioService fS;

	@Autowired
	private IServicioService sS;
	

	@Autowired
	private IEstadoServicioService esS;
	@Autowired
	private IUsuarioService uS;
	
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/nuevo/{idServicio}")
	public String nuevaFacturaServicio(@PathVariable(value = "idServicio") int idServicio, Model model) {

		Servicio servicio = new Servicio();
		servicio = sS.findByIdServicio(idServicio);
		servicio.setEstadoServicio(esS.list().get(2));
		sS.insertar(servicio);

		FacturaServicio factura = new FacturaServicio();
		factura.setServicio(sS.findByIdServicio(idServicio));
		
		model.addAttribute("factura", factura);

		return "/factura/factura";
	}

	@PostMapping("/guardar")

	public String guardarFacturaServicio(@Valid FacturaServicio factura, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (result.hasErrors()) {
			return "/factura/factura";
		} else {

			if (!foto.isEmpty()) {
				if (factura.getFotoFactura() != null && factura.getFotoFactura().length() > 0) {
					uploadFileService.delete(factura.getFotoFactura());
				}
				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}
				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				factura.setFotoFactura(uniqueFilename);
			} else {
				return "/factura/factura";
			}
			
			Servicio servicio = new Servicio();
			servicio = sS.findByIdServicio(factura.getServicio().getIdServicio());
			factura.setServicio(servicio);
			servicio.setFactura(factura);
				
			fS.insertar(factura);

			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
		}

		model.addAttribute("listaFactura", fS.listFacturaByUsernameMecanico(authentication.getName()));

		return "/factura/listaFacturaMecanico";
	}

	
	@GetMapping("/listarFacturaMecanico")

	public String listarFacturaMecanico(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			model.addAttribute("listaFactura", fS.listFacturaByUsernameMecanico(authentication.getName()));
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/factura/listaFacturaMecanico";
	}
	
	@GetMapping("/listarFacturaCliente")

	public String listarFacturaCliente(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			model.addAttribute("listaFactura", fS.listFacturaByUsernameCliente(authentication.getName()));
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/factura/listaFacturaCliente";
	}
	
	@GetMapping("/detalle/{id}")
	@Secured({ "ROLE_CLIENTE", "ROLE_MECANICO" })
	public String detalleFactura(@PathVariable(value = "id") int id, Model model) {
		try {
			FacturaServicio factura = new FacturaServicio();
			
			factura = fS.findByIdFactura(id);
			model.addAttribute("factura", factura);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (uS.findByUsername(authentication.getName()).getRol().getId() == 1) { // Cliente
				model.addAttribute("ruta", "/facturas/listarFacturaCliente");
			} else { // Mecánico
				if (uS.findByUsername(authentication.getName()).getRol().getId() == 2) { // Mecanico
					model.addAttribute("ruta", "/facturas/listarFacturaMecanico");
				}
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/factura/detalle";
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
	
	

}
