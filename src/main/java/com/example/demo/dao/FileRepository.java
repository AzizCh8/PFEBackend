package com.example.demo.dao;

import com.example.demo.entities.File;
import com.example.demo.entities.Processus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@CrossOrigin(origins="*")
@Repository
public interface FileRepository extends JpaRepository<File,String> {

        @Query(value = "SELECT id_file FROM File WHERE name_file=:name")
        String findByName_file(@Param(value = "name") String name);

    @Query(value = " SELECT f FROM File f WHERE f.name_file=:name ")
    File findByName_filee(@Param(value = "name") String name);

    @Query(value = " SELECT f.name_file FROM File f WHERE f.id_file=:id ")
    File findNameById_file(@Param(value = "id") String id);

    @Query(nativeQuery = true,value = "SELECT * From File f WHERE f.id_file=(Select p.initial_file FROM Processus p WHERE p.emission_date=(SELECT MAX(emission_date) from processus ))")
    File findByProcessus(@Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT name_file From File f WHERE f.id_file IN (Select p.initial_file FROM Processus p WHERE p.status='EN_COURS' AND p.id_processus IN (Select pu.id_processus from processus_user pu where pu.id_user=:id and pu.signed='false'))")
    List<String> findFilesByProcessus(@Param(value="id") Long id);

    @Query(value = " SELECT f.nbSignatures FROM File f WHERE f.name_file=:name ")
    int findNbSign(@Param(value = "name") String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "Delete From File WHERE name_file=:name ")
    int deleteByName_file(@Param(value = "name") String name);


    @Modifying()
    @Query(nativeQuery = true,value = "update File set nb_signatures=:nbSign WHERE name_file=:name ")
    int updateNbSign(@Param(value = "name") String name,@Param(value = "nbSign") int nbSign);






}
