package dev.robglason.admin.service;

import dev.robglason.admin.dto.StudentDTO;
import dev.robglason.admin.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {

    Student loadStudentById(Long studentId);

    Page<StudentDTO> loadStudentsByName(String name, int page, int size);

    StudentDTO loadStudentByEmail(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(StudentDTO studentDTO);

    void removeStudent(Long studentId);

}
