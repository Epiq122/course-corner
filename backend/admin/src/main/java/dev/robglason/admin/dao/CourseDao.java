package dev.robglason.admin.dao;

import dev.robglason.admin.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Long> {


    List<Course> findCoursesByCourseNameContains(String keyword);


    // Retrieve courses for a specific student based on their studentId
    @Query(value = "select * from courses as c where c.course_id in (select e.course_id from enrolled_in as e where e.student_id = :studentId)", nativeQuery = true)
    List<Course> getCourseByStudentId(@Param("studentId") Long studentId);

}
