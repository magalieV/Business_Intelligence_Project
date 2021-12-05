package BI.Totally_Spies.controller;

import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.service.Hash;
import BI.Totally_Spies.service.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserInformation userInformation;

    @RequestMapping("/profile")
    public ModelAndView getHomePage(@AuthenticationPrincipal OAuth2User principal) {
        ModelAndView mav = new ModelAndView();
        User user = userInformation.getInformation(principal);
        List<String> interest = new ArrayList<String>();

        interest.add("Baseball");
        interest.add("Ice Skating");
        String url = "https://www.gravatar.com/avatar/" + Hash.md5Hex(user.getUsername()) + "?d=robohash";
        mav.addObject("username", user.getUsername());
        mav.addObject("picture", url);
        mav.addObject("interestList", interest);
        mav.setViewName("profile");
        return mav;
    }
}
