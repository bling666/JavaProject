package com.example.backend.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    private static int cnt = 0;

    @RequestMapping("query")
    @ResponseBody
    public List<Student> queryAll() {
        List<Student> list = new ArrayList<>();
        list = studentRepository.findAll();
        return list;
    }

    @RequestMapping("add")
    @ResponseBody
    public void add() {
        Student user = new Student();
        user.setName("junjun" + (cnt++));
        user.setAge(6);
        studentRepository.save(user);
    }

    @RequestMapping("update")
    @ResponseBody
    public void update() {
        Student user = studentRepository.findById(1).get();
        user.setName("duoduo");
        studentRepository.save(user);
    }
}