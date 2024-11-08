package com.nqt.cs3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.nqt.cs3.domain.Student;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentService studentService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentService.findByEmail(username);
        if(student == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản");
        }
        return new User(
            student.getEmail(),
            student.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + student.getRole().getName())));
    }
    
}
