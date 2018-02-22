/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package net.ccfish.jvue.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ccfish.common.JvueDataStatus;
import net.ccfish.common.web.BaseModel;
import net.ccfish.jvue.rest.vm.ReqLogin;
import net.ccfish.jvue.security.JwtUserDetails;
import net.ccfish.jvue.service.AuthService;
import net.ccfish.jvue.service.JvueMenuService;
import net.ccfish.jvue.vm.ModuleAndMenus;
import net.ccfish.jvue.vm.UserInfo;

/**
 * 登录
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
@RestController
@Api(tags  = "登录")
public class AuthController {

    @Autowired
    private AuthService userService;
    @Autowired
    private JvueMenuService jvueMenuService;
    
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @ApiOperation(value = "自动登录")
    @GetMapping(value = "/login")
    public String check() throws AuthenticationException {
        // 处理自动登录/记住密码等
        return "";
    }
    
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public BaseModel<UserInfo> login(@RequestBody ReqLogin req) throws AuthenticationException {
        try {
            UserInfo userInfo = userService.login(req.getUsername(), req.getPassword());
            
            return new BaseModel<UserInfo>().setData(userInfo);
        } catch (UsernameNotFoundException e) {
            BaseModel<UserInfo> result = new BaseModel<>();
            result.setError("401");
            result.setMessage("用户不存在");
            return result;
        }
    }
    /**
     * 用户菜单生成
     * @param principal
     * @return
     * @since  1.0
     */
    @ApiOperation(value = "用户菜单")
    @GetMapping(value = "/auth/menu")
    public BaseModel<ModuleAndMenus> getMenu(Principal principal) {
        //logger.info(principal.toString());
        if (principal instanceof Authentication ) {
            // JwtUserDetails
            Authentication authentication = (Authentication) principal;
            if (authentication.getPrincipal() instanceof JwtUserDetails) {
                JwtUserDetails jwtUser = (JwtUserDetails) authentication.getPrincipal();
                if (jwtUser.getSuperUser() == JvueDataStatus.SUPER_USER_TRUE) {
                    // 返回所有菜单
                    ModuleAndMenus menus = jvueMenuService.findModuleAndMenu();
                    return new BaseModel<ModuleAndMenus>().setData(menus);
                } else {
                    // 返回用户菜单
                    // FIXME 根据用户role和缓存中的role权限生成菜单
                }
            } else {
                logger.warn("无法获取登录信息  {}", authentication.getPrincipal());
            }
        } else {
            logger.warn("无法获取登录信息  {}", principal);
        }
        return null;
    }

    /**
     * 刷新密钥
     *
     * @param authorization 原密钥
     * @return 新密钥
     * @throws AuthenticationException 错误信息
     */
    @GetMapping(value = "/auth/refreshToken")
    public String refreshToken(@RequestHeader String authorization) throws AuthenticationException {
        return userService.refreshToken(authorization);
    }


}
