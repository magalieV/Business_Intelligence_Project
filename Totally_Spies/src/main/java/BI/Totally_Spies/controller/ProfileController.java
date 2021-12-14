package BI.Totally_Spies.controller;

import BI.Totally_Spies.database.WrapperInfo;
import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.service.CallRS;
import BI.Totally_Spies.service.Hash;
import BI.Totally_Spies.service.UserInformation;
import BI.Totally_Spies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserInformation userInformation;
    @Autowired
    UserService userService;
    private CallRS callRS = new CallRS();

    @RequestMapping("/profile")
    public ModelAndView getHomePage(@AuthenticationPrincipal OAuth2User principal) {
        ModelAndView mav = new ModelAndView();
        User userT = userInformation.getInformation(principal);
        User user = this.userService.getUser(userT.getUserId());

        String url = "https://www.gravatar.com/avatar/" + Hash.md5Hex(user.getUsername()) + "?d=robohash";
        mav.addObject("username", user.getUsername() + " " + user.getLastname());
        mav.addObject("picture", url);
        mav.addObject("interestList", callRS.getUserInterest(user.getRsId()));
        mav.setViewName("profile");
        return mav;
    }

    @PostMapping("/profile/save")
    @ResponseBody
    public void saveNewInterestList(@AuthenticationPrincipal OAuth2User principal, @RequestBody WrapperInfo info)
    {
        User userT = userInformation.getInformation(principal);
        User user = this.userService.getUser(userT.getUserId());

        String id = this.callRS.saveNewInterestList(user.getRsId(), info.getInterest(), user.getUsername(), user.getLastname());
        this.userService.updateUser(user, id);
    }
}
