package com.sagecodefellowship.codefellowship.controller;

import com.sagecodefellowship.codefellowship.model.UserModel;
import com.sagecodefellowship.codefellowship.repositories.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class Secret {
    @Autowired
    UserInterface userInterface;

    @GetMapping("/secret")
    public String getSecret(Principal p, Model m){
        String username = p.getName();
//        String firstname =

        if(username != null){
            m.addAttribute("username", username);
//            m.addAttribute("firstname", firstname);
            UserModel userModel = userInterface.findByUsername(username);
            m.addAttribute("secret", userModel.getBio());
            m.addAttribute("firstname", userModel.getFirstName());
            m.addAttribute("lastname", userModel.getLastName());
            m.addAttribute("birthday", userModel.getDateOfBirth());
            m.addAttribute("biography", userModel.getBio());
            return "secrets";
        }
        return "login";
    }
}
