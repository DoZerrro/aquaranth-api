package com.dq.aquaranth.login.dto;

import com.dq.aquaranth.company.dto.CompanyDTO;
import com.dq.aquaranth.dept.dto.DeptDTO;
import com.dq.aquaranth.emp.dto.EmpDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RedisDTO {
    private CompanyDTO company;
    private DeptDTO dept;
    private EmpDTO emp;
    private List<MenuResponseDTO> menuList;
}
