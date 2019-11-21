package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estado_servicios")
public class EstadoServicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEstadoServicio;
	
	@Column(name="nombreEstadoServicio", nullable=false, length=45)
	private String nombreEstadoServicio;

	public int getIdEstadoServicio() {
		return idEstadoServicio;
	}

	public void setIdEstadoServicio(int idEstadoServicio) {
		this.idEstadoServicio = idEstadoServicio;
	}

	public String getNombreEstadoServicio() {
		return nombreEstadoServicio;
	}

	public void setNombreEstadoServicio(String nombreEstadoServicio) {
		this.nombreEstadoServicio = nombreEstadoServicio;
	}
}
