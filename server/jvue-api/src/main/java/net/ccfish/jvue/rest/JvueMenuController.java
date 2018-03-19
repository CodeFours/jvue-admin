/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package net.ccfish.jvue.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.ccfish.common.web.BaseModel;
import net.ccfish.jvue.model.JvueMenu;
import net.ccfish.jvue.model.JvueModule;
import net.ccfish.jvue.rest.vm.CodeDto;
import net.ccfish.jvue.service.JvueMenuService;
import net.ccfish.jvue.service._AbstractService;
import net.ccfish.jvue.service.acl.AclResc;

/**
 * API相关
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
@RestController
@RequestMapping("menu")
@AclResc(id = 5200, code = "JvueMenu", name = "画面管理", homePage = "")
@Api(tags  = "画面管理")
public class JvueMenuController implements _BaseController<JvueMenu, Integer> {

    @Autowired
    private JvueMenuService jvueMenuService;

    /* (non-Javadoc)
     * @see net.ccfish.jvue.rest._BaseController#baseService()
     */
    @Override
    public _AbstractService<JvueMenu, Integer> baseService() {
        return this.jvueMenuService;
    }
    
    @GetMapping("names")
    public BaseModel<List<CodeDto<Integer>>> names(@RequestParam("moduleId") Integer moduleId){
        List<JvueMenu> menus = jvueMenuService.findByModule(moduleId);
        List<CodeDto<Integer>> codeList = new ArrayList<>();
        for (JvueMenu module: menus) {
            CodeDto<Integer> codeDto = new CodeDto<>();
            codeDto.setCode(module.getId());
            codeDto.setName(module.getName());
            codeList.add(codeDto);
        }
        return new BaseModel<List<CodeDto<Integer>>>().setData(codeList);
    }
}
