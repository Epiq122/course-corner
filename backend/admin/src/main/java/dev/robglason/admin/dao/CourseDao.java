package dev.robglason.admin.dao;

import dev.robglason.admin.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseDao extends JpaRepository<Course, Long> {


    Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);


    // Retrieve courses for a specific student based on their studentId
    @Query(value = "select * from courses where course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
    Page<Course> getCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);


    // view the courses there not enrolled in
    @Query(value = "select * from courses where course_id not in (select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
    Page<Course> getNonEnrolledInCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);

    // get courses by instructor id
    @Query(value = "select c from Course as c where c.instructor.instructorId=:instructorId")
    Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);
}
