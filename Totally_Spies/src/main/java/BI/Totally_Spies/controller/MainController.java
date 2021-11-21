package BI.Totally_Spies.controller;

import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @RequestMapping("/main")
    public String getHomePage() {
        return "main";
    }

    @RequestMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @RequestMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @PostMapping("/register")
    public String registerUser(String personName, String password) {
        if (userRepository.existsByUsername(personName)) {
            return "Error: An account with this email already exist!";
        }
        User user = new User();
        user.setUsername(personName);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return "redirect:/signin";
    }
}
