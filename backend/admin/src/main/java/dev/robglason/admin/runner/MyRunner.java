package dev.robglason.admin.runner;


import dev.robglason.admin.dto.CourseDTO;
import dev.robglason.admin.dto.InstructorDTO;
import dev.robglason.admin.dto.StudentDTO;
import dev.robglason.admin.dto.UserDTO;
import dev.robglason.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createAdmin();
        createInstructors();
        createCourses();
        StudentDTO student = createStudent();
        assignCourseToStudent(student);

    }


    private void createRoles() {
        Arrays.asList("Admin", "Instructor", "Student").forEach(role -> {
            roleService.createRole(role);
        });
    }

    private void createAdmin() {
        userService.createUser("admin@gmail.com", "pass1");
        userService.assignRoleToUser("admin@gmail.com", "Admin");

    }

    private void createInstructors() {
        // create 10 instructors
        for (int i = 0; i < 10; i++) {
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setFirstName("instructor" + i + "FN");
            instructorDTO.setLastName("instructor" + i + "LN");
            instructorDTO.setSummary("master" + i);
            UserDTO userDto = new UserDTO();
            userDto.setEmail("instructor" + i + "@gmail.com");
            userDto.setPassword("pass" + i);
            instructorDTO.setUser(userDto);
            instructorService.createInstructor(instructorDTO);
        }
    }

    private void createCourses() {
        // create 20 courses
        for (int i = 0; i < 20; i++) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseDescription("Java" + i);
            courseDTO.setCourseDuration(i + "hours");
            courseDTO.setCourseName("Java Course" + i);
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setInstructorId(1L);
            courseDTO.setInstructor(instructorDTO);
            courseService.createCourse(courseDTO);
        }
    }

    private StudentDTO createStudent() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName("studentFN");
        studentDTO.setLastName("studentLN");
        studentDTO.setLevel("master");
        UserDTO userDto = new UserDTO();
        userDto.setEmail("student@gmail.com");
        userDto.setPassword("pass");
        studentDTO.setUser(userDto);
        return studentService.createStudent(studentDTO);
    }

    private void assignCourseToStudent(StudentDTO student) {
        courseService.assignStudentToCourse(1L, student.getStudentId());
    }

}
