package com.example.demo.dao;

import com.example.demo.entities.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@CrossOrigin(origins="*")
@RepositoryRestResource
public interface DepartementRepository extends JpaRepository<Departement,Long> {

    @RestResource(path="/byDep")
    public List<Departement> findByNameContains(@Param("mc") String des);

    @RestResource(path="/byDepPage")
    public Page<Departement> findByNameContains(@Param("mc") String des, Pageable pageable);

    @Query(value = "SELECT name FROM Departement WHERE id_dep=:id")
    String findById_dep(@Param(value = "id") Long id);

    @Query(value = "SELECT d FROM Departement d WHERE d.id_dep=:id")
    Departement findDepById_dep(@Param(value = "id") Long id);


}
