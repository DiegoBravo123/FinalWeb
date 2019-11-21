package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Sede;

@Repository
public interface ISedeRepository extends JpaRepository<Sede, Integer> {

}
