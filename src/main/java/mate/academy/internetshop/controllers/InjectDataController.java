package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User bob = new User("Bob", "bob", "qwerty");
        bob.setRoles(Set.of(Role.of("USER")));
        User alisa = new User("Alisa", "alisa", "qwerty");
        alisa.setRoles(Set.of(Role.of("USER")));
        User dmytro = new User("Dmytro", "dmytro", "qwerty");
        dmytro.setRoles(Set.of(Role.of("USER")));
        User tom = new User("Tom", "tom", "qwerty");
        tom.setRoles(Set.of(Role.of("USER")));
        User ted = new User("Ted", "ted", "qwerty");
        ted.setRoles(Set.of(Role.of("USER")));
        User kira = new User("Kira", "kira", "qwerty");
        kira.setRoles(Set.of(Role.of("USER")));
        User admin = new User("Admin", "admin", "123");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        User god = new User("God", "god", "123");
        god.setRoles(Set.of(Role.of("ADMIN"), Role.of("USER")));
        userService.create(bob);
        userService.create(alisa);
        userService.create(dmytro);
        userService.create(tom);
        userService.create(ted);
        userService.create(kira);
        userService.create(admin);
        userService.create(god);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
