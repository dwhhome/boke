package com.user.controller;

import com.base.repository.SystemRepository;
import com.user.entity.User;
import com.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private SystemRepository systemRepository;
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model){
        List<User> list = systemRepository.queryByHql("from User");
            if(list.size()>0){
                if((User) request.getSession().getAttribute("user")!=null){
                    return "system/index";
                }else{
                    return "system/login";
                }
            }else{
                return "system/start";
            }
    }

    @RequestMapping("/loginUser")
    public String loginUser(HttpServletRequest request, @ModelAttribute User user,Model model){
            List<User> list = systemRepository.queryByHql("from User");
            if(list.size()>0){
                if(list.get(0).getUsername().equals(user.getUsername()) && MD5Util.md5(user.getPassword()).equals(list.get(0).getPassword())){
                    request.getSession().setAttribute("user",user);
                    return "system/index";
                }else{
                    model.addAttribute("user",user);
                    model.addAttribute("msg","用户名或密码错误！");
                    return "system/login";
                }
            }else{
                return "system/start";
            }
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, @ModelAttribute User user,Model model){
        List<User> list  = systemRepository.queryByHql("from User");
        if(list.size()>0){
            return "system/login";
        }else{
            user.setPassword(MD5Util.md5(user.getPassword()));
            systemRepository.save(user);
            return "system/login";
        }
    }

    @RequestMapping("/exit")
    public String exit(HttpServletRequest request, @ModelAttribute User user,Model model){
        request.getSession().removeAttribute("user");
        return "system/login";
    }
}
