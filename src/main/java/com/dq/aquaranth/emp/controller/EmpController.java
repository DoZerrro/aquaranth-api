package com.dq.aquaranth.emp.controller;

import com.dq.aquaranth.emp.dto.EmpDTO;
import com.dq.aquaranth.emp.dto.EmpUpdateDTO;
import com.dq.aquaranth.emp.service.EmpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/emp")
public class EmpController {
    private final EmpService service;

    @GetMapping("/information")
    public List<EmpDTO> getEmpList() {
        return service.empList();
    }

//    @GetMapping("/information")
//    public Long getEmpCount(){
//        return service.empCount();
//    }

    @GetMapping("/read/{empNo}")
    public EmpDTO getEmp(@PathVariable("empNo") Long empNo) {
        return service.empRead(empNo);
    }

    @PostMapping("/register")
    public Long registerEmp(@Valid @RequestBody EmpDTO empInsertDTO){
        return service.empRegister(empInsertDTO);
    }

    @PutMapping(value = "/modify/{empNo}")
    public Long modifyEmp(@Valid @RequestBody EmpUpdateDTO empUpdateDTO){
        return service.empModify(empUpdateDTO);
    }

    @DeleteMapping(value = "/remove/{empNo}")
    public Long removeEmp(@PathVariable("empNo") Long empNo){
        return service.empRemove(empNo);
    }
}
