package BI.Totally_Spies.controller;

import BI.Totally_Spies.database.Interest;
import BI.Totally_Spies.database.WrapperInfo;
import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.database.repositories.UserRepository;
import BI.Totally_Spies.service.Hash;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @RequestMapping("/main")
    public ModelAndView getHomePage() {
        ModelAndView mav = new ModelAndView();
        List<Interest> interestList = new ArrayList<Interest>();

        interestList.add(new Interest("Chess", "chess-icon"));
        interestList.add(new Interest("Music", "music-icon"));
        interestList.add(new Interest("Game", "game-icon"));
        mav.addObject("name", "Josh");
        mav.addObject("age", "22");
        mav.addObject("picture", "img/users/josh.jpg");
        mav.addObject("interestList", interestList);
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
        List<String> tmp = info.getInterest();
        User user = new User();
        user.setUsername(info.personName);
        user.setPassword(encoder.encode(info.password));
        userRepository.save(user);

        RestTemplate restTemplate = new RestTemplate();
        JSONObject personJsonObject = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        personJsonObject.put("first_name", "Jean");
        personJsonObject.put("last_name", "Jacques");
        personJsonObject.put("interests", info.getInterest());
        final HttpEntity<String> entity = new HttpEntity<String>(personJsonObject.toString(), headers);
        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/users", HttpMethod.POST, entity, String.class);
        System.out.println(res.getStatusCodeValue());
        return "redirect:/signin";
    }
}
