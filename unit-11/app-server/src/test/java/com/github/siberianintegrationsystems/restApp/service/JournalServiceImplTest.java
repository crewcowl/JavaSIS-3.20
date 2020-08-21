package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.JournalItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.JournalRequestDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.JournalRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.data.SessionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.Journal;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import com.github.siberianintegrationsystems.restApp.entity.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JournalServiceImplTest {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalService journalService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void getJournalTest() {
        Journal journal = new Journal();
        journal.setId(JournalServiceImpl.QUESTIONS_JOURNAL_ID);
        journal.setName("Вопросы");
        journal.setDefaultPageSize(15L);

        Journal questionsJournal = journalService.getJournal(journal.getId());
        assertThat(journal,sameBeanAs(questionsJournal));

        Journal journal1 = new Journal();
        journal1.setId(JournalServiceImpl.SESSIONS_JOURNAL_ID);
        journal1.setName("Сессии");
        journal1.setDefaultPageSize(15L);

        Journal sessionsJournal = journalService.getJournal(journal1.getId());
        assertThat(journal1,sameBeanAs(sessionsJournal));
    }


    @Test
    public void getJournalOfQuestionsRowsTest() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionsItemDTO> questionsDto = new ArrayList<>();
        JournalRequestDTO journal = new JournalRequestDTO();
        journal.search = "";
        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestion(question);
            questionsDto.add(new QuestionsItemDTO(question,answers));
        }
        List<? extends JournalItemDTO> questionDto1 = journalService.getJournalRows("questions",journal);

        assertThat(questionDto1,sameBeanAs(questionsDto));
    }

    @Test
    public void getJournalOfSessionsRowsSearchTest() {
        List<Session> sessions = sessionRepository.findByFioContainingIgnoreCase("Крю");
        List <SessionItemDTO> sessionItems = new ArrayList<>();
        sessions.forEach(s -> sessionItems.add(new SessionItemDTO(s)));

        JournalRequestDTO journal = new JournalRequestDTO();
        journal.search = "Крю";
        List<? extends JournalItemDTO> sessionItems1 = journalService.getJournalRows("sessions",journal);

        assertThat(sessionItems,sameBeanAs(sessionItems1));
    }
}