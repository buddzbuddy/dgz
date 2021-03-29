package com.webdatabase.dgz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "answers")
public class Answer extends AuditModel {
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(
            name = "answer_generator",
            sequenceName = "answer_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }

    @Column(columnDefinition = "text")
    private String text;
    public String getText() {
    	return text;
    }
    public void setText(String text) {
    	this.text = text;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;
    
    public Question getQuestion() {
    	return question;
    }
    public void setQuestion(Question question) {
    	this.question = question;
    }

    // Getters and Setters (Omitted for brevity)
}