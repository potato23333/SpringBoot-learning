package com.example.springbootchapter2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lzc
 * 2019/6/1 9:50
 */

@Controller
public class LoginController {
    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("data1","传递给登录页面的第一个参数");
        model.addAttribute("data2","传递给登录页面的第二个参数");
        // 这里返回的login指的是src/main/resources/templates目录下的login.html
        // 因此，我们需要在src/main/resources/templates目录下新建一个login.html
        // 当我们通过浏览器访问localhost:8080/login时即可访问到我们编写的login.html
        return "login";
    }

    /**
     * 登录验证，注意该方法的请求方式为POST
     * @return
     */
    @PostMapping("/dologin")
    public String dologin(@RequestParam(name = "username")String username,@RequestParam(name="password")String password,Model model) {
        // 假设用户名为admin，密码为admin
        if (username.equals("admin") && password.equals("admin")) {
            // 登录成功，返回到home.html
            model.addAttribute("message","登录成功");
            return "home";
        } else {
            // 登录失败，error.html
            model.addAttribute("message","账号或密码输入错误！");
            return "error";
        }
    }


}
