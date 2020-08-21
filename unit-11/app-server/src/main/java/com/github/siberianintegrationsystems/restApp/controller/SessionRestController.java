package com.github.siberianintegrationsystems.restApp.controller;

import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionItemForSessionDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.QuestionsItemDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionEndDTO;
import com.github.siberianintegrationsystems.restApp.controller.dto.SessionItemDTO;
import com.github.siberianintegrationsystems.restApp.service.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/session")
public class SessionRestController {

    private final SessionService sessionService;

    public SessionRestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("questions-new")
    public List<QuestionItemForSessionDTO> create() {
        return sessionService.showSession();
    }

    @PostMapping("")
    public String endSession(@RequestBody SessionEndDTO dto) { return  sessionService.endSession(dto); }
}
