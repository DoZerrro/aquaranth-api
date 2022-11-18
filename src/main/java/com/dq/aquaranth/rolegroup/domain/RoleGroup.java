package com.dq.aquaranth.rolegroup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroup {
    @Id
    private Long roleGroupNo;
    private String roleGroupName;
    private boolean roleGroupUse;
    private Long companyNo;
    private String regUser;
    //    default
    private LocalDateTime regDate;
    //    null
    private String modUser;
    //    null
    private LocalDateTime modDate;
}
