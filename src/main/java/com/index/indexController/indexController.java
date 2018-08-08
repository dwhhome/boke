package com.index.indexController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/index")
public class indexController {
    /**
     * 去首页
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(HttpServletRequest request, Model model){
        return "bokeIndex/index";
    }
}
