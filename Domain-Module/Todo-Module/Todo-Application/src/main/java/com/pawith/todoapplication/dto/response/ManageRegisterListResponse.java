package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ManageRegisterListResponse {

    List<ManageRegisterInfoResponse> registers;
}
