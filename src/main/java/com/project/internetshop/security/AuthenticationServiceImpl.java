package com.project.internetshop.security;

import com.project.internetshop.exeptions.AuthenticationException;
import com.project.internetshop.lib.Inject;
import com.project.internetshop.lib.Service;
import com.project.internetshop.model.User;
import com.project.internetshop.service.UserService;
import com.project.internetshop.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDB = userService.findByLogin(login)
                .orElseThrow(() -> new AuthenticationException("Incorrect login or password"));

        String hashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        if (userFromDB.getPassword().equals(hashedPassword)) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
