package com.software.educational.data.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private  String questionText;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "question_module",joinColumns = @JoinColumn(name = "question_id"),inverseJoinColumns = @JoinColumn(name="module_id"))
    private Module module;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "question_course",joinColumns = @JoinColumn(name = "question_id"),inverseJoinColumns = @JoinColumn(name="course_id"))
    private Course course;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Answer> answers;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
