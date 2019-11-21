package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
