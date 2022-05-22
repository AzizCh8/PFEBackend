package com.example.demo.dao;


import com.example.demo.entities.File;
import com.example.demo.entities.Processus;
import com.example.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@CrossOrigin(origins="*")
@RepositoryRestResource
public interface ProcessusRepository extends JpaRepository<Processus,Long> {

    @Query(value = "SELECT p.id_processus FROM Processus p WHERE p.name_processus=:name")
    Long findByName_processus(@Param(value = "name") String name);

    @Modifying
    @Query("update Processus p set p.finalFile = ?1  where p.id_processus = ?2")
    void setFinalFileById(File finalF, Long id);

    @Query("SELECT p FROM Processus p WHERE p.name_processus LIKE %:name% ")
    public Page<Processus> findByName(@Param("name") String name, Pageable pageable);


    @Query(value = "SELECT initialFile FROM Processus WHERE id_processus=:id")
    Long findByName_processus(@Param(value = "id") Long id);

    @Query(value = "SELECT p FROM Processus p WHERE p.name_processus=:name")
    Processus findProcessusByName(@Param(value = "name") String name);

    @Query(nativeQuery = true,value = "SELECT p.initial_file FROM Processus p WHERE p.emission_date=(SELECT MAX(emission_date) from processus))")
    String findBySignataire(@Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT id_user FROM processus_user where id_processus=52")
    List<Long> ff();

    @Modifying()
    @Query(nativeQuery = true,value = "update processus_user set signed='true' WHERE id_user=(SELECT id from user where username=:email) and id_processus=:id_pr")
    int updateprocessus_userTrue(@Param(value = "email") String email,@Param(value = "id_pr") Long id_pr );

    @Modifying()
    @Query(nativeQuery = true,value = "update processus_user set signed='false' WHERE id_user=(SELECT id from user where username=:email) and id_processus=:id_pr")
    int updateprocessus_userFalse(@Param(value = "email") String email,@Param(value = "id_pr") Long id_pr );

//departement
//    @Modifying
//    @Query("update Processus p set p.signataire = ?1  where p.id_processus = ?2")
//    void setSigantaireById(User user, Long id);
}
