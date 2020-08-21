package com.github.siberianintegrationsystems.restApp.data;

import com.github.siberianintegrationsystems.restApp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository
        extends JpaRepository<Session, Long> {

    List<Session> findByFioContainingIgnoreCase(String search);
}
