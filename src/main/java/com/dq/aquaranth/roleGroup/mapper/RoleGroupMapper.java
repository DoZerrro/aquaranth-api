package com.dq.aquaranth.roleGroup.mapper;

import com.dq.aquaranth.roleGroup.dto.RoleGroupDTO;
import com.dq.aquaranth.roleGroup.dto.RoleGroupNameUpdateDTO;
import com.dq.aquaranth.roleGroup.dto.RoleGroupUpdateDTO;
import com.dq.aquaranth.roleGroup.dto.RoleGroupUseUpdateDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleGroupMapper {

    /**
     * 권한그룹들을 조회합니다.
     */
    List<RoleGroupDTO> findAll();

    /**
     * 권한그룹번호로 권한그룹들을 조회합니다.
     *
     * @param roleGroupNo : 찾으려는 권한그룹번호입니다.
     * @return 찾은 권한그룹을 전부 반환합니다.
     */
    RoleGroupDTO findById(Long roleGroupNo);

    /**
     * 권한그룹 등록
     *
     * @param insertDTO : 등록할 권한그룹 객체
     */
    Long insert(RoleGroupDTO insertDTO);

    /**
     * 권한그룹번호로 권한그룹을 수정합니다.
     * @param updateDTO : 수정할 권한그룹 객체
     */
    void update(RoleGroupUpdateDTO updateDTO);

    /**
     * 권한그룹번호로 권한그룹을 삭제합니다.
     * @param roleGroupNo : 삭제할 권한그룹번호
     */
    void deleteById(Long roleGroupNo);
}