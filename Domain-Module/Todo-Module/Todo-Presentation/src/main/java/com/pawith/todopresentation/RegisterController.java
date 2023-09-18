package com.pawith.todopresentation;

import com.pawith.todoapplication.service.TodoTeamRegisterUseCase;
import com.pawith.todoapplication.service.UnregisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UnregisterUseCase unregisterUseCase;
    private final TodoTeamRegisterUseCase todoTeamRegisterUseCase;

    @DeleteMapping("/{todoTeamId}")
    public void deleteRegister(@PathVariable Long todoTeamId) {
        unregisterUseCase.unregisterTodoTeam(todoTeamId);
    }

    @PostMapping
    public void postRegister(@RequestParam String todoTeamCode) {
        todoTeamRegisterUseCase.registerTodoTeam(todoTeamCode);
    }
}
