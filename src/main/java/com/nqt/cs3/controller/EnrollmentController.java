package com.nqt.cs3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.service.EnrollmentService;

@Controller
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/enrollment")
    public String getAllEnrollment() {
        return "enrollment/show";
    }

    @GetMapping("/enrollment/create")
    public String getCreateEnrollment(Model model){
        model.addAttribute("newEnrollment", new Enrollment());
        return "enrollment/create";
    }
    
    @PostMapping("/enrollment/create")
    public String postCreateEnrollment(@ModelAttribute("newEnrollment") Enrollment enrollment){
        this.enrollmentService.save(enrollment);
        return "redirect:/enrollment";
    }

    @GetMapping("/enrollment/detail/{id}")
    public String getDetailEnrollment(@PathVariable("id") long id, Model model){
        Enrollment enrollment = this.enrollmentService.findById(id);
        model.addAttribute("enrollment", enrollment);
        return "enrollment/detail";
    }

    @GetMapping("/enrollment/update/{id}")
    public String getUpdateEnrollment(@PathVariable("id") long id, Model model){
        Enrollment currentEnrollment = this.enrollmentService.findById(id);
        model.addAttribute("currentEnrollment", currentEnrollment);
        return "enrollment/update";
    }

    @PostMapping("/enrollment/update")
    public String postUpdateEnrollment(@ModelAttribute("currentCourse") Enrollment enrollment){
        this.enrollmentService.update(enrollment);
        return "redirect:/enrollment";
    }

    @GetMapping("/enrollment/delete/{id}")
    public String getDeleteEnrollment(@PathVariable("id") long id, Model model){
        Enrollment enrollment = this.enrollmentService.findById(id);
        model.addAttribute("enrollment", enrollment);
        return "enrollment/delete";
    }

    @PostMapping("/enrollment/delete")
    public String postDeleteEnrollment(@ModelAttribute("enrollment") Enrollment enrollment){
        this.enrollmentService.delete(enrollment.getId());
        return "redirect:/enrollment";
    }
}
