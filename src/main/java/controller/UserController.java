package controller;

import model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import repositories.UserInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserInterface userInterface;

    @GetMapping("/")
    public String home(){
        return "main.html";
    }

    @PostMapping("/signup")
    public RedirectView signUp(String password, String username){
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        UserModel createUser = new UserModel(username, encryptedPassword);
        userInterface.save(createUser);
        return new RedirectView("/");
    }

    @PostMapping("/login")
    public RedirectView login(String username, String password, HttpServletRequest request){
        UserModel userModel = userInterface.findUserByUserName(username);
        if(userModel != null){
            if(BCrypt.checkpw(password, userModel.getPassword())){
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                return new RedirectView("/secrets");
            }
            return new RedirectView("/?message=incorrect credentials");
        }
        return new RedirectView("/?message=No User");
    }
}
