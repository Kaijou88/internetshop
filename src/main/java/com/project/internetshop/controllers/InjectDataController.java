package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.model.Role;
import com.project.internetshop.model.User;
import com.project.internetshop.service.RoleService;
import com.project.internetshop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final RoleService roleService = (RoleService) INJECTOR.getInstance(RoleService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Role adminRole = new Role(Role.RoleName.ADMIN);
        roleService.create(adminRole);
        Role userRole = new Role(Role.RoleName.USER);
        roleService.create(userRole);

        User admin = new User("Admin", "admin", "qwerty");
        User superAdmin = new User("SuperAdmin", "superadmin", "qwerty");
        User customer = new User("Customer", "customer", "1234");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        customer.setRoles(Set.of(Role.of("USER")));
        superAdmin.setRoles(Set.of(Role.of("ADMIN"), Role.of("USER")));
        userService.create(admin);
        userService.create(superAdmin);
        userService.create(customer);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
