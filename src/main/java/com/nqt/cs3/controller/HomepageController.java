package com.nqt.cs3.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nqt.cs3.constant.RoleEnum;
import com.nqt.cs3.domain.Course;
import com.nqt.cs3.domain.Student;
import com.nqt.cs3.dto.RegisterDTO;
import com.nqt.cs3.service.CourseService;
import com.nqt.cs3.service.EnrollmentService;
import com.nqt.cs3.service.StudentService;

@Controller
public class HomepageController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;  
    
    @Autowired 
    private EnrollmentService enrollmentService;

    @GetMapping("/")
    public String homepage(Model model) {
        List<Course> courses = this.courseService.findAll();
        List<Course> advancedCourses = courses.stream().filter(course -> course.getPrice() > 0).collect(Collectors.toList());
        List<Course> freeCourses = courses.stream().filter(course -> course.getPrice() == 0).collect(Collectors.toList());
        model.addAttribute("advancedCourses", advancedCourses);
        model.addAttribute("freeCourses", freeCourses);
        return "client/index";
    }

    // Enrollment
    @GetMapping("/enrollment-course")
    public String enrollmentCourse() {
        return "client/enrollmented_course";
    }

    // Enrollment
    @GetMapping("/detail-course/{id}")
    public String getEnrollmentCourseById(@PathVariable("id") long id, Model model) {
        Course course = this.courseService.findById(id);
        model.addAttribute("course", course);
        return "client/detail_course";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("register", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("register") RegisterDTO registerDTO) {
        Student newStudent = this.studentService.registerDtoToStudent(registerDTO);
        newStudent.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newStudent.setRole(this.studentService.getRoleByName(RoleEnum.USER));
        this.studentService.save(newStudent);
        return "redirect:/login";
    }
}
