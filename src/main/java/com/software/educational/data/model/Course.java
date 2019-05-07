package com.software.educational.data.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseName;

    private String courseTheory;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id")
    private List<Question> questionId;

    private Long moduleId;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }



    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTheory() {
        return courseTheory;
    }

    public void setCourseTheory(String courseTheory) {
        this.courseTheory = courseTheory;
    }


}
