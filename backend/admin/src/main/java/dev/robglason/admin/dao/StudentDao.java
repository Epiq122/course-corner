package dev.robglason.admin.dao;

import dev.robglason.admin.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentDao extends JpaRepository<Student, Long> {

    @Query(value = "select s from Student as s where s.firstName like %:name% or s.lastName like %:name%")
    Page<Student> findStudentsByNameContains(@Param("name") String name, PageRequest pageRequest);


    @Query(value = "select s from Student as s where s.user.email = :email")
    Student findStudentByEmail(@Param("email") String email);

}
