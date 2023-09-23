package dev.robglason.admin.service.impl;

import dev.robglason.admin.dao.CourseDao;
import dev.robglason.admin.dao.InstructorDao;
import dev.robglason.admin.dao.StudentDao;
import dev.robglason.admin.dto.CourseDTO;
import dev.robglason.admin.entity.Course;
import dev.robglason.admin.entity.Instructor;
import dev.robglason.admin.entity.Student;
import dev.robglason.admin.mapper.CourseMapper;
import dev.robglason.admin.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
@Transactional
public class CourseServiceImpl implements CourseService {


    private CourseDao courseDao;
    private InstructorDao instructorDao;

    private StudentDao studentDao;
    private CourseMapper courseMapper;

    public CourseServiceImpl(CourseDao courseDao, InstructorDao instructorDao, CourseMapper courseMapper) {
        this.courseDao = courseDao;
        this.instructorDao = instructorDao;
        this.courseMapper = courseMapper;
    }

    @Override
    public Course loadCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id" + courseId + "."));
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.fromCourseDTO(courseDTO);
        Instructor instructor = instructorDao.findById(courseDTO.getInstructor().getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id" + courseDTO.getInstructor().getInstructorId() + "."));
        course.setInstructor(instructor);
        Course savedCourse = courseDao.save(course);

        return courseMapper.fromCourse(savedCourse);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course loadedCourse = loadCourseById(courseDTO.getCourseId());
        Instructor instructor = instructorDao.findById(courseDTO.getInstructor().getInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id"
                        + courseDTO.getInstructor().getInstructorId() + "."));
        Course course = courseMapper.fromCourseDTO(courseDTO);
        course.setInstructor(instructor);
        course.setStudents(loadedCourse.getStudents());
        Course updatedCourse = courseDao.save(course);

        return courseMapper.fromCourse(updatedCourse);


    }

    @Override
    public Page<CourseDTO> findCoursesByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesPage = courseDao.findCoursesByCourseNameContains(keyword, pageRequest);
        return new PageImpl<>(coursesPage.getContent().stream()
                .map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, coursesPage.getTotalElements());

    }


    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found with id" + studentId + "."));
        Course course = loadCourseById(courseId);
        course.assignStudentToCourse(student);

    }

    @Override
    public Page<CourseDTO> getCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> studentCoursesPage = courseDao.getCourseByStudentId(studentId, pageRequest);
        return new PageImpl<>(studentCoursesPage.getContent().stream()
                .map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, studentCoursesPage.getTotalElements());
    }

    @Override
    public Page<CourseDTO> getNotEnrolledInCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> notEnrolledInCoursesPage = courseDao.getNotEnrolledInCoursesByStudentId(studentId, pageRequest);
        return new PageImpl<>(notEnrolledInCoursesPage.getContent().stream()
                .map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, notEnrolledInCoursesPage.getTotalElements());
    }

    @Override
    public void removeCourse(Long courseId) {
        courseDao.deleteById(courseId);
    }

    @Override
    public Page<CourseDTO> getCoursesForInstructor(Long instructorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> instructorCoursesPage = courseDao.getCourseByInstructorId(instructorId, pageRequest);
        return new PageImpl<>(instructorCoursesPage.getContent().stream()
                .map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, instructorCoursesPage.getTotalElements());
    }
}
