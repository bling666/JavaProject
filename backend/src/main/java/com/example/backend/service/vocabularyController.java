package com.example.backend.service;

import com.example.backend.Entity.vocabulary;
import com.example.backend.utility.baseResult;
import com.example.backend.utility.resultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class vocabularyController {
    @Autowired
    private vocabularyRepo vocabularyrepo;

    @RequestMapping("getoneword")
    @ResponseBody
    public baseResult<List<vocabulary>> get_one_word(String keyword, Integer top)
    {
        try {
            System.out.println(keyword);
            System.out.println(top);
            if (top == null) {
                top = 1;
            }
            List<vocabulary> re = vocabularyrepo.findAllByWordLike(keyword, top);
            if(re.isEmpty())
                return resultUtil.error(404,"no result");
            return resultUtil.success(re);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return resultUtil.error(555,"something wrong but i don't know,please call me");
        }
    }
}
