package com.example.backend.service;

import com.example.backend.Entity.User;
import com.example.backend.Dao.UserRepo;
import com.example.backend.utility.baseResult;
import com.example.backend.utility.resultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @RequestMapping(value = "signUp",method = RequestMethod.POST)
    public baseResult<String> SignUp(@RequestParam("username") String username,@RequestParam("password") String password)
    {
        try{
            User user = userRepo.findByUsername(username);
            if(user!=null)
            {
                return resultUtil.error("用户名已存在");

            }
            else
            {
                User insert_user = new User();
                insert_user.setUsername(username);
                insert_user.setPassword(password);
                userRepo.save(insert_user);
                return resultUtil.success("sign up success");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return resultUtil.error(500,"error in sign up");
        }
    }

    @RequestMapping("signIn")
    public baseResult<String> signIn(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password)
    {
        try {
            User user = userRepo.findByUsername(username);
            if(user==null)
            {
                return resultUtil.error("您尚未注册");
            }
            if(!user.getPassword().equals(password))
            {
                return resultUtil.error("用户名或密码错误");
            }
            else
            {

                request.getSession();
                request.getSession().setAttribute("uid", user.getUid());
                return resultUtil.success("登录成功");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return resultUtil.error(500,"在登录的时候发生了内部错误，真令人悲伤");
        }

    }

    @RequestMapping("logout")
    public baseResult<String> logout(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password)
    {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("uid"));
        return resultUtil.success("test");
    }

}
