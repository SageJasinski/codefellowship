package controller;

import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import repositories.UserInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class Secret {
    @Autowired
    UserInterface userInterface;

    @GetMapping("/secret")
    public String getSecret(HttpServletRequest request, Model m){
        HttpSession session = request.getSession();
        String userName = session.getAttribute("username").toString();

        if(userName != null){
            m.addAttribute("username", userName);
            UserModel userModel = userInterface.findUserByUserName(userName);
            m.addAttribute("secret", userModel.getSecret());
            return "secrets.html";
        }
        return "main.html";
    }
}
