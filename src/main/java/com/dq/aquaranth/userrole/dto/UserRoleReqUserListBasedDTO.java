package com.dq.aquaranth.userrole.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleReqUserListBasedDTO {
    Long orgaNo;
    String searchEmp;
    String searchRole;
}
