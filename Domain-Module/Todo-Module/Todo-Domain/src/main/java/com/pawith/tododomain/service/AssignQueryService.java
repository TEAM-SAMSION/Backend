package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignQueryService {

}
