package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.*;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Session;

import java.util.List;

public interface SessionService {

    List<QuestionItemForSessionDTO> showSession();

    String endSession(SessionEndDTO dto);

    Session createSession (SessionEndDTO dto, double percent);

    void createSelectedAnswer(Session session, Answer answer);

    double takePercent (double result,SessionEndDTO dto );

    Answer findAnswerById(SessionQuestionAnswer sessionQuestionAnswer);

    double countOfAnswersCorrect (List<SessionQuestionAnswer> sessionQuestionAnswers, boolean correct);

    double resultForOneCorrectAnswer (List<SessionQuestionAnswer> checkedAnswers);

    double resultForManyCorrectAnswer (List<SessionQuestionAnswer> checkedAnswers,List<Answer> answers, int correctAnswers);

    double getResultForSession (AnsweredQuestionDTO question, List<Answer> sessionAnswers);
}
