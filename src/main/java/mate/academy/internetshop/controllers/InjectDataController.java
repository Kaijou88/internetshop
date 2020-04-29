package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User bob = new User("Bob", "bob", "qwerty");
        User alisa = new User("Alisa", "alisa", "qwerty");
        userService.create(bob);
        userService.create(alisa);
        req.getRequestDispatcher("WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
