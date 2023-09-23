package dev.robglason.admin.utility;

import dev.robglason.admin.dao.*;
import dev.robglason.admin.entity.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class OperationUtility {

    //    ---------------- USERS --------------------------
    public static void usersOperation(UserDao userDao) {
        createUsers(userDao);
//        updateUser(userDao);
//        deleteUser(userDao);
//        getUsers(userDao);
    }


    private static void createUsers(UserDao userDao) {
        User user1 = new User("user1@gmail.com", "pass1");
        userDao.save(user1);
        User user2 = new User("user2@gmail.com", "pass2");
        userDao.save(user2);
        User user3 = new User("user3@gmail.com", "pass3");
        userDao.save(user3);
        User user4 = new User("user4@gmail.com", "pass4");
        userDao.save(user4);
    }

    private static void updateUser(UserDao userDao) {
        User user = userDao.findById(2L).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        user.setEmail("newEmail@gmail.com");
        userDao.save(user);
    }

    private static void deleteUser(UserDao userDao) {
        User user = userDao.findById(3L).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        userDao.delete(user);

    }

    private static void getUsers(UserDao userDao) {
        userDao.findAll().forEach(user -> System.out.println(user.toString()));
    }

    //    ---------------- ROLE --------------------------

    public static void rolesOperations(RoleDao roleDao) {
        createRole(roleDao);
//        updateRole(roleDao);
//        deleteRole(roleDao);
//        getRole(roleDao);

    }


    private static void createRole(RoleDao roleDao) {
        Role role1 = new Role("Admin");
        roleDao.save(role1);
        Role role2 = new Role("Instructor");
        roleDao.save(role2);
        Role role3 = new Role("Student");
        roleDao.save(role3);
    }

    private static void updateRole(RoleDao roleDao) {
        Role role = roleDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
        role.setName("NewAdmin");
        roleDao.save(role);
    }

    private static void deleteRole(RoleDao roleDao) {
        roleDao.deleteById(2L);
    }

    private static void getRole(RoleDao roleDao) {
        roleDao.findAll().forEach(role -> System.out.println(role.toString()));

    }

    public static void assignRolesToUsers(UserDao userDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Admin");
        if (role == null) throw new EntityNotFoundException("Role Not Found");
        List<User> users = userDao.findAll();
        users.forEach(user -> {
            user.assignRoleToUser(role);
            userDao.save(user);
        });

    }

    //    ---------------- Instructor --------------------------

    public static void instructorsOperations(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        createInstructors(userDao, instructorDao, roleDao);
//        updateInstructor(instructorDao);
//        removeInstructor(instructorDao);
//        getInstructors(instructorDao);

    }


    private static void createInstructors(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Instructor");
        if (role == null) throw new EntityNotFoundException("Role Not Found");

        User user1 = new User("instructorUser1@gmail.com", "pass1");
        userDao.save(user1);
        user1.assignRoleToUser(role);


        Instructor instructor1 = new Instructor("instructor1FN", "instructor1LN", "Experienced", user1);
        instructorDao.save(instructor1);

        User user2 = new User("instructorUser2@gmail.com", "pass2");
        userDao.save(user2);
        user1.assignRoleToUser(role);


        Instructor instructor2 = new Instructor("instructor2FN", "instructor2LN", "Novice", user2);
        instructorDao.save(instructor2);


    }

    private static void updateInstructor(InstructorDao instructorDao) {
        Instructor instructor = instructorDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Instructor Not Found"));
        instructor.setSummary("Juggernaut");
        instructorDao.save(instructor);
    }

    private static void removeInstructor(InstructorDao instructorDao) {
        instructorDao.deleteById(2L);
    }

    private static void getInstructors(InstructorDao instructorDao) {
        instructorDao.findAll().forEach(instructor -> System.out.println(instructor.toString()));
    }

    //    ---------------- Students --------------------------

    public static void studentOperations(UserDao userDao, StudentDao studentDao, RoleDao roleDao) {
        createStudents(userDao, studentDao, roleDao);
//        updateStudent(studentDao);
//        removeStudent(studentDao);
//        getStudents(studentDao);
    }


    private static void createStudents(UserDao userDao, StudentDao studentDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Student");
        if (role == null) throw new EntityNotFoundException("Role Not Found");

        User user1 = new User("studentUser1@gmail.com", "pass1");
        userDao.save(user1);
        user1.assignRoleToUser(role);
        Student student1 = new Student("student1FN", "student1LN", "Beginner", user1);
        studentDao.save(student1);

        User user2 = new User("studentUser2@gmail.com", "pass2");
        userDao.save(user2);
        user2.assignRoleToUser(role);
        Student student2 = new Student("student2FN", "student2LN", "Master", user1);
        studentDao.save(student2);

    }

    private static void updateStudent(StudentDao studentDao) {
        Student student = studentDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Student Not Found"));
        student.setFirstName("updatedStudentFN");
        student.setLastName("updatedStudentLN");
        studentDao.save(student);
    }

    private static void removeStudent(StudentDao studentDao) {
        studentDao.deleteById(2L);
    }

    private static void getStudents(StudentDao studentDao) {
        studentDao.findAll().forEach(student -> System.out.println(student.toString()));
    }

    //    ---------------- Courses --------------------------

    public static void coursesOperations(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao) {
//        createCourses(courseDao, instructorDao);
//        updateCourse(courseDao);
//        deleteCourse(courseDao);
//        fetchCourses(courseDao);
//        assignStudentsToCourse(courseDao, studentDao);
        fetchCoursesForStudent(courseDao);
    }


    private static void createCourses(CourseDao courseDao, InstructorDao instructorDao) {
        Instructor instructor = instructorDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Instructor Not Found"));

        Course course1 = new Course("Ready With React", "7 Hours", "A modern react course", instructor);
        courseDao.save(course1);
        Course course2 = new Course("Ready With Vue", "7.5 Hours", "A cool vue course", instructor);
        courseDao.save(course2);
    }

    private static void updateCourse(CourseDao courseDao) {
        Course course = courseDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Course Not Found"));
        course.setCourseDescription("16 Hours");
        courseDao.save(course);

    }

    private static void deleteCourse(CourseDao courseDao) {
        courseDao.deleteById(2L);
    }

    private static void fetchCourses(CourseDao courseDao) {
        courseDao.findAll().forEach(course -> System.out.println(course.toString()));
    }

    private static void assignStudentsToCourse(CourseDao courseDao, StudentDao studentDao) {
        Optional<Student> student1 = studentDao.findById(1L);
        Optional<Student> student2 = studentDao.findById(2L);
        Course course = courseDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Course Not Found"));

        student1.ifPresent(course::assignStudentToCourse);
        student2.ifPresent(course::assignStudentToCourse);
        courseDao.save(course);


    }

    private static void fetchCoursesForStudent(CourseDao courseDao) {
        courseDao.getCourseByStudentId(1L).forEach(course -> System.out.println(course.toString()));
    }


}
