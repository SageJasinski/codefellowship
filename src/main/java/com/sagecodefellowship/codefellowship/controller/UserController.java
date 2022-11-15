package com.sagecodefellowship.codefellowship.controller;

import com.sagecodefellowship.codefellowship.model.UserModel;
import com.sagecodefellowship.codefellowship.repositories.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    UserInterface userInterface;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String home() {
        return "signup";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signUp(String password, String username, String firstname, String lastname, Date birthday) {
        String hashedPW = passwordEncoder.encode(password);
        UserModel newUser = new UserModel(username, hashedPW,firstname,lastname, birthday);
        userInterface.save(newUser);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}
