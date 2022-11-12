package com.dq.aquaranth.menu.service;

import com.dq.aquaranth.menu.dto.request.MenuUpdateDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMenuService implements MenuService {

    private final MenuMapper menuMapper;


    @Override
    public List<MenuResponseDTO> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public Optional<MenuResponseDTO> findByMenuNo(Long menuNo) {
        return menuMapper.findByMenuNo(menuNo);
    }

    @Override
    public Optional<MenuResponseDTO> findByMenuCode(String menuCode) {
        return menuMapper.findByMenuCode(menuCode);
    }

    @Override
    public Integer update(MenuUpdateDTO menuUpdateDTO) {
        return menuMapper.update(menuUpdateDTO);
    }

    @Override
    public List<MenuResponseDTO> findAllMenuInformation() {
        return menuMapper.findAllMenuInformation();
    }

    @Override
    public List<MenuResponseDTO> findByUpperMenuNoIsNull() {
        return menuMapper.findByUpperMenuNoIsNull();

    }

    @Override
    public List<MenuResponseDTO> findByMenuCodeUpperRecursive(String menuCode) {
        return menuMapper.findByMenuCodeUpperRecursive(menuCode);
    }

    @Override
    public List<MenuResponseDTO> findByMenuCodeUnderRecursive(String menuCode) {
        return menuMapper.findByMenuCodeUnderRecursive(menuCode);
    }

    @Override
    public List<MenuResponseDTO> findMenusByLoginUsername(String username) {
        return menuMapper.findMenusByLoginUsername(username);
    }
}
