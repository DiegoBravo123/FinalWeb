package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.OrdenCompra;
import pe.edu.upc.entity.Servicio;

@Repository
public interface IOrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
	List<OrdenCompra> findAllByServicio(Servicio Servicio);

	@Query("select oc from OrdenCompra oc where oc.servicio.mecanico.username like %?1%")
	List<OrdenCompra> findOrdenCompraByUsernameMecanico(String username);
}
