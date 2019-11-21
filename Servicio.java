package pe.edu.upc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "servicios")
public class Servicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idServicio;

	@ManyToOne
	@JoinColumn(name = "idTipoServicio", nullable = false)
	private TipoServicio tipoServicio;

	
	
	private String descripcionServicio;

	@ManyToOne
	@JoinColumn(name = "idSede", nullable = false)
	private Sede sede;

	
	@Column(name = "fechaServicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaServicio;

	@ManyToOne
	@JoinColumn(name = "idEstadoServicio", nullable = false)
	private EstadoServicio estadoServicio;

	@ManyToOne
	@JoinColumn(name = "idMecanico", nullable = true)
	private Mecanico mecanico;

	@ManyToOne
	@JoinColumn(name = "idVehiculo", nullable = false)
	private Vehiculo vehiculo;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "servicio")
	private FacturaServicio factura;

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}

	public Date getFechaServicio() {
		return fechaServicio;
	}

	public void setFechaServicio(Date fechaServicio) {
		this.fechaServicio = fechaServicio;
	}

	public EstadoServicio getEstadoServicio() {
		return estadoServicio;
	}

	public void setEstadoServicio(EstadoServicio estadoServicio) {
		this.estadoServicio = estadoServicio;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	public void setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public FacturaServicio getFactura() {
		return factura;
	}

	public void setFactura(FacturaServicio factura) {
		this.factura = factura;
	}

}
