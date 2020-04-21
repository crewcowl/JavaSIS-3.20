package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.entity.Question;

public interface QuestionService {
    QuestionsItemDTO createQuestion(QuestionsItemDTO dto);

    QuestionsItemDTO editQuestion(QuestionsItemDTO dto);

    Question getQuestion (QuestionsItemDTO dto);

    void setAnswer (AnswerItemDTO dto, Question question);
}
