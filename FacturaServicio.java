package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "factura_servicios")
public class FacturaServicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFactura;

	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "idServicio", nullable = false)
	private Servicio servicio;

	
	
	private double montoTotal;
	

	private String fotoFactura;

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getFotoFactura() {
		return fotoFactura;
	}

	public void setFotoFactura(String fotoFactura) {
		this.fotoFactura = fotoFactura;
	}
}
