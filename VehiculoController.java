package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.entity.Vehiculo;
import pe.edu.upc.service.IClienteService;
import pe.edu.upc.service.IUploadFileService;
import pe.edu.upc.service.IVehiculoService;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

	@Autowired
	private IVehiculoService vS;

	@Autowired
	private IClienteService cS;

	@Autowired
	private IUploadFileService uploadFileService;
	
	@RequestMapping("/bienvenido")
	@Secured({ "ROLE_CLIENTE" ,"ROLE_MECANICO"})
	public String irBienvenido() {
		return "bienvenido";
	}
	@GetMapping("/nuevo")
	@Secured({ "ROLE_CLIENTE" })
	public String nuevoVehiculo(Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		return "vehiculo/vehiculo";
	}

	@PostMapping("/guardar")
	@Secured({ "ROLE_CLIENTE" })
	public String guardarVehiculo(@Valid Vehiculo vehiculo, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = new Cliente();
		cliente = cS.findByUsername(authentication.getName());

		if (result.hasErrors()) {
			return "vehiculo/vehiculo";
		} else {

			if (!foto.isEmpty()) {
				if (vehiculo.getFotoVehiculo() != null && vehiculo.getFotoVehiculo().length() > 0) {
					uploadFileService.delete(vehiculo.getFotoVehiculo());
				}

				String uniqueFilename = null;

				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}
				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				vehiculo.setFotoVehiculo(uniqueFilename);
			} else {
				return "vehiculo/listaVehiculos"; //*
			}

			vehiculo.setCliente(cliente);

			vS.insertar(vehiculo);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
		}
		model.addAttribute("listaVehiculos", vS.list(cliente));

		return "/vehiculo/listaVehiculos";
	}

	@GetMapping("/listar")
	@Secured({ "ROLE_CLIENTE" })
	public String listarVehiculos(Model model) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Cliente cliente = new Cliente();
			cliente = cS.findByUsername(authentication.getName());

			model.addAttribute("vehiculo", new Vehiculo());
			model.addAttribute("listaVehiculos", vS.list(cliente));
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/vehiculo/listaVehiculos";
	}

	@RequestMapping("/eliminar")
	@Secured({ "ROLE_CLIENTE" })
	public String eliminarVehiculo(Model model, @RequestParam(value = "id") Integer id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Cliente cliente = new Cliente();
		cliente = cS.findByUsername(authentication.getName());

		try {
			if (id != null && id > 0) {
				vS.eliminar(id);
				model.addAttribute("mensaje", "Se eliminó correctamente");
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			model.addAttribute("mensaje", "No se puede eliminar vehículos relacionados con un servicio");
		}

		model.addAttribute("listaVehiculos", vS.list(cliente));
		return "/vehiculo/listaVehiculos";
	}

	@GetMapping("/actualizar/{id}")
	@Secured({ "ROLE_CLIENTE" })
	public String actualizarVehiculo(@PathVariable(value = "id") int id, Model model) {
		try {
			Vehiculo vehiculo = new Vehiculo();
			vehiculo = vS.findByIdVehiculo(id);
			model.addAttribute("vehiculo", vehiculo);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/vehiculo/actualizar";
	}

	@GetMapping("/detalle/{id}")
	@Secured({ "ROLE_CLIENTE" })
	public String detalleVehiculo(@PathVariable(value = "id") int id, Model model) {
		try {
			Vehiculo vehiculo = new Vehiculo();
			vehiculo = vS.findByIdVehiculo(id);
			model.addAttribute("vehiculo", vehiculo);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/vehiculo/detalle";
	}

	@GetMapping(value = "/uploads/{filename:.+}")
	@Secured({ "ROLE_CLIENTE" })
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

	@RequestMapping("/buscar")
	@Secured({ "ROLE_CLIENTE" })
	public String buscarVehiculo(Model model, @RequestParam("busqueda") String busqueda,
			@RequestParam("valor") String valor) {

		switch (busqueda) {
		case "placa":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Vehiculo> listaVehiculos;
				listaVehiculos = vS.findByPlaca(valor, authentication.getName());

				model.addAttribute("listaVehiculos", listaVehiculos);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/vehiculo/listaVehiculos";
		case "descripcion":
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				List<Vehiculo> listaVehiculos;
				listaVehiculos = vS.findByDescripcion(valor, authentication.getName());

				model.addAttribute("listaVehiculos", listaVehiculos);
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
			}
			return "/vehiculo/listaVehiculos";
		default:
			return "/vehiculo/listaVehiculos";
		}
	}
}
