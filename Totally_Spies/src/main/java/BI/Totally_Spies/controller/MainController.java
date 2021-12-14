package BI.Totally_Spies.controller;

import BI.Totally_Spies.database.Interest;
import BI.Totally_Spies.database.WrapperInfo;
import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.database.repositories.UserRepository;
import BI.Totally_Spies.service.CallRS;
import BI.Totally_Spies.service.Hash;
import BI.Totally_Spies.service.UserInformation;
import BI.Totally_Spies.service.UserService;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserInformation userInformation;
    @Autowired
    UserService userService;
    private CallRS callRS = new CallRS();

    @RequestMapping("/main")
    public ModelAndView getHomePage(@AuthenticationPrincipal OAuth2User principal) {
        ModelAndView mav = new ModelAndView();
        User userT = userInformation.getInformation(principal);
        User user = this.userService.getUser(userT.getUserId());

        List<WrapperInfo> users = this.callRS.getRS(user.getRsId());

        for (Integer index = 0; index < users.size(); index++) {
            String name = users.get(index).firstName + " " + users.get(index).lastName;
            users.get(index).setGlobalName(name);
            String url = "https://www.gravatar.com/avatar/" + Hash.md5Hex(users.get(index).getFirstName()) + "?d=robohash";
            users.get(index).setPicture(url);
        }
        mav.addObject("userList", users);
        mav.setViewName("main");
        return mav;
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
    public String registerUser(@RequestBody WrapperInfo info) {
        if (userRepository.existsByUsername(info.personName)) {
            return "Error: An account with this email already exist!";
        }
        String rsId = this.callRS.addUserToRs(info.personName, info.lastName, info.getInterest());
        List<String> tmp = info.getInterest();
        User user = new User();
        user.setUsername(info.personName);
        user.setLastname(info.lastName);
        user.setRsId(rsId);
        user.setPassword(encoder.encode(info.password));
        userRepository.save(user);
        return "redirect:/signin";
    }
}
