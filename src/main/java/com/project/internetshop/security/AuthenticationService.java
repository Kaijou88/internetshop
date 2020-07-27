package com.project.internetshop.security;

import com.project.internetshop.exeptions.AuthenticationException;
import com.project.internetshop.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;

}
