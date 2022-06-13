package com.example.demo.dao;

import com.example.demo.entities.processusUserId;
import com.example.demo.entities.processus_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProcessusUserRepository extends JpaRepository<processus_user,processusUserId> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "Delete From processus_user WHERE id_processus=:idP ")
    int deleteByProcessusId(@Param(value = "idP") Long idP);

    @Query(nativeQuery = true,value = "select * From processus_user WHERE id_processus=:idP ")
    List<processus_user> findByProcessusId(@Param(value = "idP") Long idP);
}
