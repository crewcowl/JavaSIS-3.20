package com.github.siberianintegrationsystems.restApp.controller.dto;

import com.github.siberianintegrationsystems.restApp.entity.Answer;

public class AnswerItemForSessionDTO {
    public String id;
    public String answerText;

    public AnswerItemForSessionDTO(){

    }

    public AnswerItemForSessionDTO (Answer answer) {
        this.id = answer.getId().toString();
        this.answerText = answer.getName();
    }

}
