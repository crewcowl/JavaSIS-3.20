package com.github.siberianintegrationsystems.restApp.service;

import com.github.siberianintegrationsystems.restApp.controller.dto.AnswerItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.data.AnswerRepository;
import com.github.siberianintegrationsystems.restApp.data.QuestionRepository;
import com.github.siberianintegrationsystems.restApp.entity.Answer;
import com.github.siberianintegrationsystems.restApp.entity.BaseEntity;
import com.github.siberianintegrationsystems.restApp.entity.Question;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public QuestionsItemDTO createQuestion(QuestionsItemDTO dto) {
        Question question = getQuestion(dto);

        dto.answers.forEach(a -> setAnswer(a,question));

        return new QuestionsItemDTO(question,
                answerRepository.findByQuestion(question));
    }

    @Override
    public QuestionsItemDTO editQuestion(QuestionsItemDTO dto) {
        Question question = getQuestion(dto);

        answerRepository.deleteAll(answerRepository.findByQuestion(question));

        dto.answers.forEach(a -> setAnswer(a,question));

        return new QuestionsItemDTO(question,
                answerRepository.findByQuestion(question));
    }

    @Override
    public Question getQuestion (QuestionsItemDTO dto) {

        Question question = (dto.id != null) ?
                questionRepository
                        .findById(Long.parseLong(dto.id))
                        .orElseThrow(() -> new RuntimeException("нет вопроса с таким Id"))
                : new Question();

        question.setName(dto.name);
        questionRepository.save(question);
        return question;
    }

    @Override
    public void setAnswer (AnswerItemDTO dto, Question question) {
        Answer answer = new Answer();
        answer.setName(dto.answerText);
        answer.setCorrect(dto.isCorrect);
        answer.setQuestion(question);

        answerRepository.save(answer);
    }
}
