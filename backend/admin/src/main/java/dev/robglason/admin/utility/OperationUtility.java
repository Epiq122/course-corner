package dev.robglason.admin.utility;

import dev.robglason.admin.dao.RoleDao;
import dev.robglason.admin.dao.UserDao;
import dev.robglason.admin.entity.Role;
import dev.robglason.admin.entity.User;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class OperationUtility {

    public static void usersOperation(UserDao userDao) {
        createUsers(userDao);
        updateUser(userDao);
        deleteUser(userDao);
        getUsers(userDao);
    }

    public static void rolesOperations(RoleDao roleDao) {
        createRole(roleDao);
        updateRole(roleDao);
        deleteRole(roleDao);
        getRole(roleDao);
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


}
