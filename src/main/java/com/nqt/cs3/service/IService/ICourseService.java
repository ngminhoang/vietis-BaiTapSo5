package com.nqt.cs3.service.IService;

import com.nqt.cs3.domain.Course;

import java.util.List;

public interface ICourseService {
    Course save(Course course);
    Course findById(long id);
    List<Course> findAll();
    void delete(long id);
    Course update(Course course);
}
