package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.BaseEntity;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.name=myapp-test-h2","myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
@Transactional
public class QuestionServiceImplTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;


    @After
    public void clearBase()
    {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    public void getQuestionTest () {

        AnswerItemDTO answerDTO = new AnswerItemDTO();
        answerDTO.answerText = "4";
        answerDTO.isCorrect = true;

        List<AnswerItemDTO> answers = new ArrayList<>();
        answers.add(answerDTO);
        QuestionsItemDTO dto = new QuestionsItemDTO();
        dto.name = "2+2";
        dto.answers = answers;

        Question question = questionService.getQuestion(dto);
        Question question1 = questionRepository.findByNameContainingIgnoreCase(dto.name).get(0);

        assertThat(question,sameBeanAs(question1));
    }

    @Test
    public void setAnswerTest () {
        AnswerItemDTO answerDTO = new AnswerItemDTO();
        answerDTO.answerText = "4";
        answerDTO.isCorrect = true;

        List<AnswerItemDTO> answersDTO = new ArrayList<>();
        answersDTO.add(answerDTO);
        QuestionsItemDTO dto = new QuestionsItemDTO();
        dto.name = "2+2";
        dto.answers = answersDTO;

        Question question = questionService.getQuestion(dto);
        Answer answer = new Answer();
        answer.setName("4");
        answer.setCorrect(true);
        answer.setQuestion(question);
        List<Answer> answers = new ArrayList<>();
        answers.add(answer);

        dto.answers.forEach(a -> questionService.setAnswer(a,question));

        List<Answer> answers1 = answerRepository.findByQuestion(question);

        assertThat(answers,sameBeanAs(answers1).ignoring("id"));
        assertNotNull(answers.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Test
    public void createQuestionTest() {
        AnswerItemDTO answerDTO = new AnswerItemDTO();
        answerDTO.answerText = "4";
        answerDTO.isCorrect = true;

        List<AnswerItemDTO> answers = new ArrayList<>();
        answers.add(answerDTO);
        QuestionsItemDTO dto = new QuestionsItemDTO();
        dto.name = "2+2";
        dto.answers = answers;

        QuestionsItemDTO dto1 = questionService.createQuestion(dto);

        assertThat(dto1,sameBeanAs(dto).ignoring("id").ignoring("answers"));
        assertThat(dto1.answers,sameBeanAs(dto.answers).ignoring("id"));
        assertNotNull(dto1.answers.stream().map(a -> a.id).collect(Collectors.toList()));
        assertNotNull(dto1.id);
    }

    @Test
    public void editQuestionTest() {
        AnswerItemDTO answerDTO = new AnswerItemDTO();
        answerDTO.answerText = "4";
        answerDTO.isCorrect = true;

        AnswerItemDTO answerDTO1 = new AnswerItemDTO();
        answerDTO.answerText = "5";
        answerDTO.isCorrect = false;

        List<AnswerItemDTO> answers = new ArrayList<>();
        answers.add(answerDTO);
        QuestionsItemDTO dto = new QuestionsItemDTO();
        dto.name = "2+2";
        dto.answers = answers;

        QuestionsItemDTO dto1 = questionService.createQuestion(dto);

        Long idOfQuestion = questionRepository.findByNameContainingIgnoreCase(dto.name).get(0).getId();

        Question question = questionRepository.findById(idOfQuestion).get();
        List<Answer> answers1 = answerRepository.findByQuestion(question);
        //проверка создание ответа для вопроса
        assertEquals(answers1.size(), 1);

        answers.add(answerDTO1);
        dto.id = dto1.id;
        dto.name = "2*2";
        dto.answers = answers;

        QuestionsItemDTO dto2 = questionService.editQuestion(dto);
        answers1 = answerRepository.findByQuestion(question);
        //Проверка добавление ответа для вопроса при редактировании
        assertEquals(answers1.size(), 2);


        Long idOfQuestion1 = questionRepository.findByNameContainingIgnoreCase(dto.name).get(0).getId();
        //Проверка на то что вопрос действительно изменяется
        assertEquals(idOfQuestion,idOfQuestion1);

    }
}