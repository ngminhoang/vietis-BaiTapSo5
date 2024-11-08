package com.nqt.cs3.controller;

import com.nqt.cs3.domain.Student;
import com.nqt.cs3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    public StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/student")
    public String getAllStudent(Model model) {
        List<Student> students = this.studentService.findAll();
        model.addAttribute("students", students);
        return "student/show";
    }

    @GetMapping("/student/detail/{id}")
    public String getDetailStudent(@PathVariable("id") long id, Model model) {
        Student student = this.studentService.findById(id);
        model.addAttribute("student", student);
        return "student/detail";
    }

    @GetMapping("/student/create")
    public String getCreateStudent(Model model) {
        model.addAttribute("newStudent", new Student());
        return "student/create";
    }

    @PostMapping("/student/create")
    public String postCreateStudent(@ModelAttribute("newStudent") Student newStudent) {
        this.studentService.save(newStudent);
        return "redirect:/student";
    }

    @GetMapping("/student/update/{id}")
    public String getUpdateStudent(@PathVariable("id") long id, Model model) {
        Student student = this.studentService.findById(id);
        model.addAttribute("student", student);
        return "student/update";
    }

    @PostMapping("/student/update")
    public String postUpdateStudent(@ModelAttribute("student") Student student) {
        this.studentService.update(student);
        return "redirect:/student";
    }

    @GetMapping("/student/delete/{id}")
    public String getDeleteStudent(@PathVariable("id") long id, Model model) {
        Student student = this.studentService.findById(id);
        model.addAttribute("student", student);
        return "student/delete";
    }

    @PostMapping("/student/delete")
    public String postDeleteStudent(@ModelAttribute("student") Student student) {
        this.studentService.delete(student.getId());
        return "redirect:/student";
    }

    
}
