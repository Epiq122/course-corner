package dev.robglason.admin;

import dev.robglason.admin.dao.*;
import dev.robglason.admin.utility.OperationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApplication implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StudentDao studentDao;

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        OperationUtility.usersOperation(userDao);
//        OperationUtility.rolesOperations(roleDao);
//        OperationUtility.assignRolesToUsers(userDao, roleDao);
//        OperationUtility.instructorsOperations(userDao, instructorDao, roleDao);
//        OperationUtility.studentOperations(userDao, studentDao, roleDao);
        OperationUtility.coursesOperations(courseDao, instructorDao, studentDao);

    }
}
