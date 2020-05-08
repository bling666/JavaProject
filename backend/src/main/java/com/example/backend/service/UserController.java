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


}
