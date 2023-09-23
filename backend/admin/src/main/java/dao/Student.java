package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Student extends JpaRepository<Student, Long> {

    @Query(value = "select s from Student as s where s.firstName like %:name% or s.lastName like %:name%")
    List<Student> findStudentsByNameContains(@Param("name") String name);

    @Query(value = "select s from Student as s where s.user.email = :email")
    List<Student> findStudentsByEmail(@Param("email") String email);

}
