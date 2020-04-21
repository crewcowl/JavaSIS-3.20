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
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.name=myapp-test-h2","myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
@Transactional
public class SessionServiceImplTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionService sessionService;


    @Before
    public void init () {
        Question question = new Question();
        question.setName("2+2");
        questionRepository.save(question);

        Question question1 = new Question();
        question1.setName("Съедобно?");
        questionRepository.save(question1);

        Answer answer = new Answer();
        answer.setName("4");
        answer.setCorrect(true);
        answer.setQuestion(question);
        answerRepository.save(answer);

        Answer answer1 = new Answer();
        answer1.setName("5");
        answer1.setCorrect(false);
        answer1.setQuestion(question);
        answerRepository.save(answer1);

        Answer answer2 = new Answer();
        answer2.setName("Грибы");
        answer2.setCorrect(true);
        answer2.setQuestion(question1);
        answerRepository.save(answer2);

        Answer answer3 = new Answer();
        answer3.setName("Картошка");
        answer3.setCorrect(true);
        answer3.setQuestion(question1);
        answerRepository.save(answer3);

        Answer answer4 = new Answer();
        answer4.setName("Сапог");
        answer4.setCorrect(false);
        answer4.setQuestion(question1);
        answerRepository.save(answer4);
    }

    @After
    public void end() {

        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void showSessionTest() {
        List<QuestionItemForSessionDTO> questionsDTO = new LinkedList<>();

        AnswerItemForSessionDTO answerItemDto = new AnswerItemForSessionDTO();
        answerItemDto.answerText = "4";
        AnswerItemForSessionDTO answerItemDto1 = new AnswerItemForSessionDTO();
        answerItemDto1.answerText = "5";
        AnswerItemForSessionDTO answerItemDto2 = new AnswerItemForSessionDTO();
        answerItemDto2.answerText = "Грибы";
        AnswerItemForSessionDTO answerItemDto3 = new AnswerItemForSessionDTO();
        answerItemDto3.answerText = "Картошка";
        AnswerItemForSessionDTO answerItemDto4 = new AnswerItemForSessionDTO();
        answerItemDto4.answerText = "Сапог";

        List<AnswerItemForSessionDTO> answerItemsDto = new ArrayList<>();
        answerItemsDto.add(answerItemDto);
        answerItemsDto.add(answerItemDto1);

        List<AnswerItemForSessionDTO> answerItemsDto1 = new ArrayList<>();
        answerItemsDto1.add(answerItemDto2);
        answerItemsDto1.add(answerItemDto3);
        answerItemsDto1.add(answerItemDto4);

        QuestionItemForSessionDTO dto = new QuestionItemForSessionDTO();
        dto.name = "2+2";
        dto.answers = answerItemsDto;
        questionsDTO.add(dto);

        QuestionItemForSessionDTO dto1 = new QuestionItemForSessionDTO();
        dto1.name = "Съедобно?";
        dto1.answers = answerItemsDto1;
        questionsDTO.add(dto1);

        List<QuestionItemForSessionDTO> dto2 = sessionService.showSession();


        assertThat(questionsDTO,sameBeanAs(dto2).ignoring("id").ignoring("answers"));
        for(int i = 0; i < dto2.size(); i++) {
        assertThat(questionsDTO.get(i).answers,sameBeanAs(dto2.get(i).answers).ignoring("id")); }
    }

    @Test
    public void takePercentTest() {
        SessionEndDTO dto = new SessionEndDTO();
        List<AnsweredQuestionDTO> answeredQuestionDTOS = new ArrayList<>();
        AnsweredQuestionDTO answeredQuestionDTO = new AnsweredQuestionDTO();
        SessionQuestionAnswer sessionQuestionAnswer = new SessionQuestionAnswer();
        SessionQuestionAnswer sessionQuestionAnswer1 = new SessionQuestionAnswer();
        sessionQuestionAnswer.isSelected = true;
        sessionQuestionAnswer1.isSelected = false;
        List<SessionQuestionAnswer> sessionQuestionAnswerList = new ArrayList<>();
        sessionQuestionAnswerList.add(sessionQuestionAnswer);
        sessionQuestionAnswerList.add(sessionQuestionAnswer1);
        answeredQuestionDTO.answersList = sessionQuestionAnswerList;
        answeredQuestionDTOS.add(answeredQuestionDTO);
        dto.name = "admin";
        dto.questionsList = answeredQuestionDTOS;

        double percent = sessionService.takePercent(1,dto);

        assertEquals(percent,100,0);
    }

    @Test
    public void countOfAnswersCorrectTest() {
        List<SessionQuestionAnswer> sessionQuestionAnswers = new ArrayList<>();
        SessionQuestionAnswer sessionQuestionAnswer = new SessionQuestionAnswer();
        sessionQuestionAnswer.id = String.valueOf(answerRepository.findByName("4").getId());
        SessionQuestionAnswer sessionQuestionAnswer2 = new SessionQuestionAnswer();
        sessionQuestionAnswer2.id = String.valueOf(answerRepository.findByName("5").getId());
        SessionQuestionAnswer sessionQuestionAnswer1 = new SessionQuestionAnswer();
        sessionQuestionAnswer1.id = String.valueOf(answerRepository.findByName("Сапог").getId());

        sessionQuestionAnswers.add(sessionQuestionAnswer);
        sessionQuestionAnswers.add(sessionQuestionAnswer1);
        sessionQuestionAnswers.add(sessionQuestionAnswer2);

        double result = sessionService.countOfAnswersCorrect(sessionQuestionAnswers, true);
        assertEquals(1, result,0);
        result = sessionService.countOfAnswersCorrect(sessionQuestionAnswers, false);
        assertEquals(2, result,0);
    }

    @Test
    public void resultForOneCorrectAnswerTest() {
        List<SessionQuestionAnswer> sessionQuestionAnswers = new ArrayList<>();
        SessionQuestionAnswer sessionQuestionAnswer = new SessionQuestionAnswer();
        sessionQuestionAnswer.id = String.valueOf(answerRepository.findByName("4").getId());

        SessionQuestionAnswer sessionQuestionAnswer2 = new SessionQuestionAnswer();
        sessionQuestionAnswer2.id = String.valueOf(answerRepository.findByName("5").getId());


        sessionQuestionAnswers.add(sessionQuestionAnswer);
        double result = sessionService.resultForOneCorrectAnswer(sessionQuestionAnswers);
        assertEquals(1, result,0);

        sessionQuestionAnswers.add(sessionQuestionAnswer2);
        result = sessionService.resultForOneCorrectAnswer(sessionQuestionAnswers);
        assertEquals(0, result, 0);
    }

    @Test
    public void resultForManyCorrectAnswerTest() {
        List<SessionQuestionAnswer> sessionQuestionAnswers = new ArrayList<>();
        SessionQuestionAnswer sessionQuestionAnswer = new SessionQuestionAnswer();
        sessionQuestionAnswer.id = String.valueOf(answerRepository.findByName("Картошка").getId());
        SessionQuestionAnswer sessionQuestionAnswer2 = new SessionQuestionAnswer();
        sessionQuestionAnswer2.id = String.valueOf(answerRepository.findByName("Грибы").getId());
        SessionQuestionAnswer sessionQuestionAnswer1 = new SessionQuestionAnswer();
        sessionQuestionAnswer1.id = String.valueOf(answerRepository.findByName("Сапог").getId());


        sessionQuestionAnswers.add(sessionQuestionAnswer2);

        List<Answer> answers = answerRepository.findByQuestion(questionRepository.findByNameContainingIgnoreCase("Съедобно?").get(0));
        double result = sessionService.resultForManyCorrectAnswer(sessionQuestionAnswers,answers,2);
        assertEquals(0.5,result,0);

        sessionQuestionAnswers.add(sessionQuestionAnswer);
        result = sessionService.resultForManyCorrectAnswer(sessionQuestionAnswers,answers,2);
        assertEquals(1,result,0);
    }


    @Test
    public void endSession() {

        List<SessionQuestionAnswer> sessionQuestionAnswers = new ArrayList<>();
        SessionQuestionAnswer sessionQuestionAnswer = new SessionQuestionAnswer();
        sessionQuestionAnswer.id = String.valueOf(answerRepository.findByName("4").getId());
        sessionQuestionAnswer.isSelected = true;
        SessionQuestionAnswer sessionQuestionAnswer2 = new SessionQuestionAnswer();
        sessionQuestionAnswer2.id = String.valueOf(answerRepository.findByName("5").getId());
        sessionQuestionAnswer2.isSelected = false;

        sessionQuestionAnswers.add(sessionQuestionAnswer);
        sessionQuestionAnswers.add(sessionQuestionAnswer2);

        List<SessionQuestionAnswer> sessionQuestionAnswers1 = new ArrayList<>();
        SessionQuestionAnswer sessionQuestionAnswer3 = new SessionQuestionAnswer();
        sessionQuestionAnswer3.id = String.valueOf(answerRepository.findByName("Картошка").getId());
        sessionQuestionAnswer3.isSelected = true;
        SessionQuestionAnswer sessionQuestionAnswer4 = new SessionQuestionAnswer();
        sessionQuestionAnswer4.id = String.valueOf(answerRepository.findByName("Грибы").getId());
        sessionQuestionAnswer4.isSelected = false;
        SessionQuestionAnswer sessionQuestionAnswer5 = new SessionQuestionAnswer();
        sessionQuestionAnswer5.id = String.valueOf(answerRepository.findByName("Сапог").getId());
        sessionQuestionAnswer5.isSelected = false;

        sessionQuestionAnswers1.add(sessionQuestionAnswer3);
        sessionQuestionAnswers1.add(sessionQuestionAnswer4);
        sessionQuestionAnswers1.add(sessionQuestionAnswer5);

        List<AnsweredQuestionDTO> questions = new ArrayList<>();
        AnsweredQuestionDTO question = new AnsweredQuestionDTO();
        question.id = String.valueOf(questionRepository.findByNameContainingIgnoreCase("2+2").get(0).getId());
        question.answersList = sessionQuestionAnswers;

        AnsweredQuestionDTO question2 = new AnsweredQuestionDTO();
        question2.id = String.valueOf(questionRepository.findByNameContainingIgnoreCase("Съедобно?").get(0).getId());
        question2.answersList = sessionQuestionAnswers1;

        questions.add(question);
        questions.add(question2);

        SessionEndDTO session = new SessionEndDTO();
        session.name = "Lev";
        session.questionsList = questions;

        String result = sessionService.endSession(session);

        assertEquals(result,"75.0");

    }

}