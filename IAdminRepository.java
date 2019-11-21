package pe.edu.upc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.upc.entity.Admin;

public interface IAdminRepository extends JpaRepository<Admin, Integer>{

}
