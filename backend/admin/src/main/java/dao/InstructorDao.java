package dao;

import dev.robglason.admin.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorDao extends JpaRepository<Instructor, Long> {


    // gets the instructor by their name (first or last)
    @Query(value = "select i from Instructor as i where i.firstName like %:name% or i.lastName like %:name%")
    List<Instructor> findInstructorByName(@Param("name") String name);


    @Query(value = "select i from Instructor as i where i.user.email = :email")
    List<Instructor> findInstructorByEmail(@Param("email") String email);
}
