package com.example.demo.dao;

import com.example.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@CrossOrigin(origins="*")
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long> {
    @RestResource(path="/byName")
    public List<User> findByUsernameContains(@Param("mc") String des);

    @RestResource(path="/byNamePage")
    public Page<User> findByUsernameContains(@Param("mc") String des, Pageable pageable);

    public User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:name% ")
    public Page<User> findByName(@Param("name") String name,Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.username=:name ")
    public User findByName1(@Param("name") String name);

    @Query("SELECT u.id FROM User u WHERE u.username=:name ")
    public String findByUsername1(@Param("name") String name);


    @Query(nativeQuery = true,value = "SELECT * from User WHERE id=:id")
    public User findUserById(@Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT count(*) FROM User u WHERE u.id IN (Select pu.id_user from processus_user pu where pu.id_processus=:idP)")
    Long countNbSigners(@Param(value = "idP") Long idP);

    @Query(nativeQuery = true,value = "SELECT u.* FROM User u WHERE u.id IN (Select pu.id_user from processus_user pu where pu.id_processus=:idP)")
    public List<User> findUsersByProcessusId(@Param(value = "idP") Long idP);



//    @Query(nativeQuery = true,value ="UPDATE User u set u.first_name=user.first_name,u.last_name=user.last_name,u.username=user.username, u.password=user.password, u.id_dep=user.id_dep,u.processuses=user.processuses WHERE u.username=:name ")
//    public String updateByUsername1(@Param("name") String name,@Param("user") User user);



}
