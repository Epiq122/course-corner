package dev.robglason.admin.mapper;


import dev.robglason.admin.dto.CourseDTO;
import dev.robglason.admin.entity.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

    private final InstructorMapper instructorMapper;

    public CourseMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }

    public CourseDTO fromCourse(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        courseDTO.setInstructor(instructorMapper.fromInstructor(course.getInstructor()));
        return courseDTO;
    }

    public Course fromCourseDTO(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        return course;
    }


}
