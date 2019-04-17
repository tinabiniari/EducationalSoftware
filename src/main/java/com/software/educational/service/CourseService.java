package com.software.educational.service;

import com.software.educational.data.model.Course;
import com.software.educational.data.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Iterable<Course> getAllCourses(){
        return courseRepository.findAll();
    }


     public List<Course> getCourseByModuleId(Long userId) {
        return courseRepository.findCourseByModuleId(userId);

    }

    public Course getCourseTheorybyId(Long id){
        return courseRepository.getOne(id);
    }
}
