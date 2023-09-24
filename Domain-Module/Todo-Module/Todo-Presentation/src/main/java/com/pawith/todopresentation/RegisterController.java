package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.response.RegisterListResponse;
import com.pawith.todoapplication.service.RegistersGetUseCase;
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
    private final RegistersGetUseCase registersGetUseCase;

    @DeleteMapping("/{todoTeamId}")
    public void deleteRegister(@PathVariable Long todoTeamId) {
        unregisterUseCase.unregisterTodoTeam(todoTeamId);
    }

    @PostMapping
    public void postRegister(@RequestParam String todoTeamCode) {
        todoTeamRegisterUseCase.registerTodoTeam(todoTeamCode);
    }

    @GetMapping("/list")
    public RegisterListResponse getRegisters(@RequestParam Long teamId){
        return registersGetUseCase.getRegisters(teamId);
    }
}
