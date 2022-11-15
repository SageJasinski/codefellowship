package com.sagecodefellowship.codefellowship.controller;

import com.sagecodefellowship.codefellowship.model.UserModel;
import com.sagecodefellowship.codefellowship.repositories.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class Secret {
    @Autowired
    UserInterface userInterface;

    @GetMapping("/secret")
    public String getSecret(Principal p, Model m){
        String username = p.getName();

        if(username != null){
            m.addAttribute("username", username);
            UserModel userModel = userInterface.findByUsername(username);
            m.addAttribute("secret", userModel.getSecret());
            return "secrets";
        }
        return "login";
    }
}
