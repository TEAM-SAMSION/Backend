package com.pawith.todopresentation;

import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.request.AuthorityChangeRequest;
import com.pawith.todoapplication.dto.response.RegisterInfoResponse;
import com.pawith.todoapplication.dto.response.RegisterManageInfoResponse;
import com.pawith.todoapplication.dto.response.RegisterTermResponse;
import com.pawith.todoapplication.service.ChangeRegisterUseCase;
import com.pawith.todoapplication.service.RegistersGetUseCase;
import com.pawith.todoapplication.service.TodoTeamRegisterUseCase;
import com.pawith.todoapplication.service.UnregisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class RegisterController {

    private final UnregisterUseCase unregisterUseCase;
    private final TodoTeamRegisterUseCase todoTeamRegisterUseCase;
    private final RegistersGetUseCase registersGetUseCase;
    private final ChangeRegisterUseCase changeRegisterUseCase;

    @DeleteMapping("/{todoTeamId}/registers")
    public void deleteRegister(@PathVariable Long todoTeamId) {
        unregisterUseCase.unregisterTodoTeam(todoTeamId);
    }

    @PostMapping("/registers")
    public void postRegister(@RequestParam String todoTeamCode) {
        todoTeamRegisterUseCase.registerTodoTeam(todoTeamCode);
    }

    @GetMapping("/{todoTeamId}/registers")
    public ListResponse<RegisterInfoResponse> getRegisters(@PathVariable Long todoTeamId){
        return registersGetUseCase.getRegisters(todoTeamId);
    }

    @GetMapping("/{todoTeamId}/registers/manage")
    public ListResponse<RegisterManageInfoResponse> getRegistersForManage(@PathVariable Long todoTeamId){
        return registersGetUseCase.getManageRegisters(todoTeamId);
    }

    @GetMapping("/{todoTeamId}/registers/term")
    public RegisterTermResponse getTodoRegisterTerm(@PathVariable Long todoTeamId){
        return registersGetUseCase.getRegisterTerm(todoTeamId);
    }

    @PutMapping("/{todoTeamId}/registers/{registerId}")
    public void putAuthority(@PathVariable Long todoTeamId, @PathVariable Long registerId, @RequestBody AuthorityChangeRequest authorityChangeRequest) {
        changeRegisterUseCase.changeAuthority(todoTeamId, registerId, authorityChangeRequest);
    }


}
