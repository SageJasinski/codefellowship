package com.sagecodefellowship.codefellowship.controller;

import com.sagecodefellowship.codefellowship.model.UserModel;
import com.sagecodefellowship.codefellowship.repositories.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
    public RedirectView signUp(String password, String username, String firstname, String lastname, Date birthday, String biography) {
        String hashedPW = passwordEncoder.encode(password);
        UserModel newUser = new UserModel(username, hashedPW,firstname,lastname, birthday, biography);
        userInterface.save(newUser);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    @GetMapping("/others/{id}")
    public String getOtherUsers(Model m, Principal p, @PathVariable Long id){
        UserModel loggedUser = userInterface.findByUsername(p.getName());
        m.addAttribute("loggedUser", loggedUser.getUsername());

        UserModel viewingUser = userInterface.findById(id).orElseThrow();
        m.addAttribute("viewedUserName", viewingUser.getUsername());
        m.addAttribute("viewedUserId", viewingUser.getId());
        m.addAttribute("viewedUserBio", viewingUser.getBio());
        m.addAttribute("usersIfollow", viewingUser.getUsersIFollow());
        m.addAttribute("usersWhofollowMe", viewingUser.getUsersWhoFollowThisUser());
        return "others";
    }

    @PutMapping("/follow-user/{id}")
    public RedirectView followUser(Principal p, @PathVariable Long id){
        UserModel userFollowing = userInterface.findById(id).orElseThrow(() -> new RuntimeException("Error retrieving user id: " + id));
        UserModel currentUser = userInterface.findByUsername(p.getName());

        if(currentUser.getUsername().equals(userFollowing.getUsername())){
            throw new IllegalArgumentException("Can not follow yourself");
        }

        currentUser.getUsersIFollow().add(userFollowing);
        userInterface.save(currentUser);

        return new RedirectView("/others/" + id);
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}
