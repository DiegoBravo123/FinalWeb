package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vehiculos")
public class Vehiculo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVehiculo;
	
	
	@NotEmpty(message = "Debe ingresar Descripcion*")
	@Column(name="descripcion_vehiculo",nullable = false,length = 50)
	private String descripcionVehiculo;
	
	
	
	@Size(min = 6, max = 7)
	@NotEmpty(message = "Debe ingresar la Placa*")
	@Column(name="placa_vehiculo",nullable = false,length = 50)
	private String placaVehiculo;

	
	private String fotoVehiculo;

	@ManyToOne
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getDescripcionVehiculo() {
		return descripcionVehiculo;
	}

	public void setDescripcionVehiculo(String descripcionVehiculo) {
		this.descripcionVehiculo = descripcionVehiculo;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getFotoVehiculo() {
		return fotoVehiculo;
	}

	public void setFotoVehiculo(String fotoVehiculo) {
		this.fotoVehiculo = fotoVehiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
