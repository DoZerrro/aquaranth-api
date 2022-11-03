package com.dq.aquaranth.userRole.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleReqRoleGroupBasedOrgaLIstDTO {
    Long roleGroupNo;
    String search;
}
