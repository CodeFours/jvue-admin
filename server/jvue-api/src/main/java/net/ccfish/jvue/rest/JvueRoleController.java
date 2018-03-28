/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package net.ccfish.jvue.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ccfish.common.web.BaseModel;
import net.ccfish.jvue.model.JvueRole;
import net.ccfish.jvue.service.JvueRoleService;
import net.ccfish.jvue.service._AbstractService;
import net.ccfish.jvue.service.acl.AclResc;
import net.ccfish.jvue.vm.RoleMenuDetails;

/**
 * Role相关
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
@RestController
@RequestMapping("role")
@AclResc(id = 5300, code = "JvueRole", name = "角色管理", homePage = "")
@Api(tags  = "角色管理")
public class JvueRoleController implements _BaseController<JvueRole, Integer> {

    @Autowired
    private JvueRoleService jvueRoleService;

    /* (non-Javadoc)
     * @see net.ccfish.jvue.rest._BaseController#baseService()
     */
    @Override
    public _AbstractService<JvueRole, Integer> baseService() {
        return this.jvueRoleService;
    }
    
    @ApiOperation(value = "更新是否有效")
    @AclResc(id = 11, code = "patchEnabled", name = "更新是否有效")
    @PatchMapping("/{id}/{enabled}") 
    public BaseModel<JvueRole> patchEnabled(@PathVariable("id") Integer id,
            @PathVariable("enabled") byte enabled){
        
        JvueRole jvueRole = jvueRoleService.updateEnabled(id, enabled);
        return new BaseModel<JvueRole>().setData(jvueRole);
    }

    @ApiOperation(value = "角色权限")
    @AclResc(id = 12, code = "getRoleInfo", name = "角色权限")
    @GetMapping("/ext/{id}/grant") 
    public BaseModel<RoleMenuDetails<Integer>> getRoleInfo(@PathVariable("id") Integer id){
        
        RoleMenuDetails<Integer> jvueRole = jvueRoleService.getRoleInfo(id);
        return new BaseModel<RoleMenuDetails<Integer>>().setData(jvueRole);
    }

}
