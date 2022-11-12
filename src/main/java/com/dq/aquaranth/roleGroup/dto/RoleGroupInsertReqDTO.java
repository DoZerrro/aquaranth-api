package com.dq.aquaranth.roleGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroupInsertReqDTO {
    private String roleGroupName;
    private Long companyNo;
    private boolean roleGroupUse;
    private String regUser;
}
