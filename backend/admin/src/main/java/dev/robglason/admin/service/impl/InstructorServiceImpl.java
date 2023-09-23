package dev.robglason.admin.service.impl;

import dev.robglason.admin.dao.InstructorDao;
import dev.robglason.admin.dto.InstructorDTO;
import dev.robglason.admin.entity.Course;
import dev.robglason.admin.entity.Instructor;
import dev.robglason.admin.entity.User;
import dev.robglason.admin.mapper.InstructorMapper;
import dev.robglason.admin.service.CourseService;
import dev.robglason.admin.service.InstructorService;
import dev.robglason.admin.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {


    InstructorDao instructorDao;
    InstructorMapper instructorMapper;

    private UserService userService;

    private CourseService courseService;

    public InstructorServiceImpl(InstructorDao instructorDao, InstructorMapper instructorMapper, UserService userService, CourseService courseService) {
        this.instructorDao = instructorDao;
        this.instructorMapper = instructorMapper;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorDao.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor with id " + instructorId + " not found"));

    }

    @Override
    public Page<InstructorDTO> findInstructorsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Instructor> instructorPage = instructorDao.findInstructorByName(name, pageRequest);

        return new PageImpl<>(instructorPage.getContent().stream()
                .map(instructor -> instructorMapper.fromInstructor(instructor))
                .collect(Collectors.toList()), pageRequest, instructorPage.getTotalElements());

    }

    @Override
    public InstructorDTO loadInstructorByEmail(String email) {
        return instructorMapper.fromInstructor(instructorDao.findInstructorByEmail(email));
    }

    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        User user = userService.createUser(instructorDTO.getUser().getEmail(), instructorDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Instructor");
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorDao.save(instructor);

        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
        Instructor loadedInstructor = loadInstructorById(instructorDTO.getInstructorId());
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(loadedInstructor.getUser());
        instructor.setCourses(loadedInstructor.getCourses());
        Instructor updatedInstructor = instructorDao.save(instructor);
        return instructorMapper.fromInstructor(updatedInstructor);
    }

    @Override
    public List<InstructorDTO> getInstructors() {
        return instructorDao.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor))
                .collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for (Course course : instructor.getCourses()) {
            courseService.removeCourse(course.getCourseId());

        }
        instructorDao.deleteById(instructorId);

    }
}