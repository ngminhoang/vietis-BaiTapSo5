package com.nqt.cs3.service;

import com.nqt.cs3.domain.Course;
import com.nqt.cs3.repository.CourseRepository;
import com.nqt.cs3.service.IService.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findById(long id) {
        return this.courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course update(Course course) {
        Course courseOptional = this.findById(course.getId());
        if(courseOptional != null) {
            Course currentCourse = new Course();
            currentCourse.setId(course.getId());
            currentCourse.setName(course.getName());
            currentCourse.setDescription(course.getDescription());
            currentCourse.setMaxStudent(course.getMaxStudent());
            currentCourse.setImage(course.getImage());
            currentCourse.setInstructor(course.getInstructor());
            return this.save(currentCourse);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        this.courseRepository.deleteById(id);
    }
}
