package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.SelectedAnswer;
import com.github.siberianintegrationsystems.restApp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedAnswerRepository
        extends JpaRepository<SelectedAnswer, Long> {

}
