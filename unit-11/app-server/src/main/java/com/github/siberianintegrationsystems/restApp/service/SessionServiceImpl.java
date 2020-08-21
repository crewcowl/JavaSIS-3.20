package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.*;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.data.SelectedAnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.SessionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import com.github.siberianintegrationsystems.restApp.entity.SelectedAnswer;
import com.github.siberianintegrationsystems.restApp.entity.Session;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SessionRepository sessionRepository;
    private final SelectedAnswerRepository selectedAnswerRepository;

    public SessionServiceImpl(QuestionRepository questionRepository,
                              AnswerRepository answerRepository,
                              SessionRepository sessionRepository,
                              SelectedAnswerRepository selectedAnswerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.sessionRepository = sessionRepository;
        this.selectedAnswerRepository = selectedAnswerRepository;
    }

    @Override
    public List<QuestionItemForSessionDTO> showSession() {

        List<Question> questions = questionRepository.findAll();
        List<QuestionItemForSessionDTO> getQuestions = new LinkedList<>();
        for(Question question : questions) {
            getQuestions.add(new QuestionItemForSessionDTO(question,
                    answerRepository.findByQuestion(question)));
        }
        return getQuestions;
    }

    @Override
    public String endSession(SessionEndDTO dto) {
        double result = 0.0;
        List<Answer> sessionAnswers = new LinkedList<>();

        if(dto.questionsList == null || dto.questionsList.size() == 0) {
            throw new RuntimeException("Не получен список вопросов");
        }

        for(AnsweredQuestionDTO question : dto.questionsList) {
            result = result + getResultForSession(question, sessionAnswers);
        }

        double percent = takePercent(result,dto);

        Session session = createSession(dto,percent);

        sessionAnswers.forEach(s -> createSelectedAnswer(session,s));

        return String.valueOf(percent);
    }

    @Override
    public Session createSession (SessionEndDTO dto, double percent) {
        Session session = new Session();
        session.setFio(dto.name);
        session.setPercent(percent);
        session.setDate(new Date());
        sessionRepository.save(session);
        return session;
    }

    @Override
    public void createSelectedAnswer(Session session, Answer answer) {
            SelectedAnswer selectedAnswer = new SelectedAnswer();
            selectedAnswer.setSession(session);
            selectedAnswer.setAnswer(answer);
            selectedAnswerRepository.save(selectedAnswer);
    }

    @Override
    public double takePercent (double result,SessionEndDTO dto ) {
        double percent = (result / dto.questionsList.size()) * 100;
        percent = new BigDecimal(percent).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return percent;
    }

    @Override
    public Answer findAnswerById(SessionQuestionAnswer sessionQuestionAnswer) {
        return answerRepository.findById(Long.parseLong(sessionQuestionAnswer.id)).orElseThrow(() -> new RuntimeException("Не найден ответ с данным id"));
    }

    @Override
    public double countOfAnswersCorrect (List<SessionQuestionAnswer> sessionQuestionAnswers, boolean correct) {
        return (double) sessionQuestionAnswers.stream()
                .filter(a -> findAnswerById(a).getCorrect() == correct)
                .count();
    }

    @Override
    public double resultForOneCorrectAnswer (List<SessionQuestionAnswer> checkedAnswers) {
        return (checkedAnswers.stream().anyMatch(a -> findAnswerById(a).getCorrect()))
                &&
                (checkedAnswers.size() == 1)
                ?  1 : 0;
    }

    @Override
    public double resultForManyCorrectAnswer (double allAnswers, double correctCheckedAnswers, double unCorrectCheckedAnswers, int correctAnswers) {

        /*Суть проверки скорее в том что бы Количество всех ответов не совпадало с количеством правильных,
        но если нет неправильных то мы в любом случае избегаем деления на 0, а если нет неправильных выбраных то эта функция все равно будет 0,
        поэтому проверку оставил такой
         */
        double unCorrects = (unCorrectCheckedAnswers == 0) ? 0 : unCorrectCheckedAnswers/(allAnswers-correctAnswers);
        double manyAnswerResult = ((correctCheckedAnswers/(double)correctAnswers) - unCorrects);

        return Math.max(0.0,manyAnswerResult);
    }

    @Override
    public double getResultForSession (AnsweredQuestionDTO question, List<Answer> sessionAnswers) {
        List<Answer> answers = answerRepository
                .findByQuestion(questionRepository
                        .findById(Long.parseLong(question.id))
                        .orElseThrow(() -> new RuntimeException("Вопрос с данным id не найден")));

        List<SessionQuestionAnswer> checkedAnswers = question.answersList.stream()
                .filter(a -> a.isSelected)
                .collect(Collectors.toList());

        int correctAnswers = (int) answers.stream().filter(Answer::getCorrect).count();

        checkedAnswers.forEach(a -> sessionAnswers.add(findAnswerById(a)));

        if(correctAnswers == 1 ) {
            return resultForOneCorrectAnswer(checkedAnswers);
        } else {
            double allAnswers = answers.size();
            double correctCheckedAnswers = countOfAnswersCorrect(checkedAnswers,true);
            double unCorrectCheckedAnswers = countOfAnswersCorrect(checkedAnswers,false);
            return resultForManyCorrectAnswer(allAnswers,correctCheckedAnswers,unCorrectCheckedAnswers,correctAnswers);
        }
    }
}
