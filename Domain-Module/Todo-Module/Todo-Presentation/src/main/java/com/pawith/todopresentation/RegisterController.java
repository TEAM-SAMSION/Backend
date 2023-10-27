package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.request.AuthorityChangeRequest;
import com.pawith.todoapplication.dto.response.ManageRegisterListResponse;
import com.pawith.todoapplication.dto.response.RegisterListResponse;
import com.pawith.todoapplication.service.ChangeRegisterUseCase;
import com.pawith.todoapplication.service.RegistersGetUseCase;
import com.pawith.todoapplication.service.TodoTeamRegisterUseCase;
import com.pawith.todoapplication.service.UnregisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registers")
public class RegisterController {

    private final UnregisterUseCase unregisterUseCase;
    private final TodoTeamRegisterUseCase todoTeamRegisterUseCase;
    private final RegistersGetUseCase registersGetUseCase;
    private final ChangeRegisterUseCase changeRegisterUseCase;

    @DeleteMapping("/{todoTeamId}")
    public void deleteRegister(@PathVariable Long todoTeamId) {
        unregisterUseCase.unregisterTodoTeam(todoTeamId);
    }

    @PostMapping
    public void postRegister(@RequestParam String todoTeamCode) {
        todoTeamRegisterUseCase.registerTodoTeam(todoTeamCode);
    }

    @GetMapping("/{todoTeamId}")
    public RegisterListResponse getRegisters(@PathVariable Long todoTeamId){
        return registersGetUseCase.getRegisters(todoTeamId);
    }

    @GetMapping("/{todoTeamId}/manage")
    public ManageRegisterListResponse getRegistersForManage(@PathVariable Long todoTeamId){
        return registersGetUseCase.getManageRegisters(todoTeamId);
    }

    @PutMapping("/{registerId}")
    public void putAuthority(@PathVariable Long registerId, @RequestBody AuthorityChangeRequest authorityChangeRequest) {
        changeRegisterUseCase.changeAuthority(registerId, authorityChangeRequest);
    }
}
