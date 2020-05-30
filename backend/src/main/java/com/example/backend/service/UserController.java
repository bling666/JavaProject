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
        System.out.printf("sign up from %s %s\n",username,password);
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
                insert_user.setAvatar("/home/java20/default.png");
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
        System.out.printf("sign in from %s %s\n",username,password);
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
    public baseResult<String> logout(HttpServletRequest request)
    {

        HttpSession session = request.getSession();
        try {
            String uid = session.getAttribute("uid").toString();
            System.out.printf("logout from %s\n",uid);
            session.invalidate();
            return resultUtil.success("成功退出");
        }
        catch(Exception e)
        {
            return resultUtil.error("您似乎尚未登录");
        }
    }

    @RequestMapping("personal")
    public baseResult<User> personal_information(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            int uid = Integer.parseInt(session.getAttribute("uid").toString());
            User user = userRepo.getOne(uid);
            System.out.println(user.getUsername());
        return resultUtil.success(user, "这就是您的信息");
        } catch (Exception e)
        {
            return resultUtil.error("您似乎尚未登录");
        }
    }

}
