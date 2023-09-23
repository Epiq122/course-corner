package dev.robglason.admin.service;

import dev.robglason.admin.dto.CourseDTO;
import dev.robglason.admin.entity.Course;
import org.springframework.data.domain.Page;

public interface CourseService {

    Course loadCourseById(Long courseId);

    CourseDTO createCourse(CourseDTO courseDTO);

    CourseDTO updateCourse(CourseDTO courseDTO);

    Page<CourseDTO> findCoursesByCourseName(String keyword, int page, int size);

    void assignStudentToCourse(Long courseId, Long studentId);

    Page<CourseDTO> getCoursesForStudent(Long studentId, int page, int size);

    Page<CourseDTO> getNotEnrolledInCoursesForStudent(Long studentId, int page, int size);

    void removeCourse(Long courseId);

    Page<CourseDTO> getCoursesForInstructor(Long instructorId, int page, int size);
}
