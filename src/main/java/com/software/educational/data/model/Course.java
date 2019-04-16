package com.software.educational.data.model;

import javax.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseName;

    private String courseTheory;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "course_question",joinColumns = @JoinColumn(name = "course_id"),inverseJoinColumns = @JoinColumn(name="question_id"))
    private Question questions;

    public Question getQuestions() {
        return questions;
    }

    public void setQuestions(Question questions) {
        this.questions = questions;
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
