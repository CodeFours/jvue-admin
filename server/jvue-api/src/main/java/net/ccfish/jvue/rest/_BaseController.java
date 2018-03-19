/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package net.ccfish.jvue.rest;

import java.io.Serializable;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiOperation;
import net.ccfish.common.web.BaseModel;
import net.ccfish.common.web.PageParam;
import net.ccfish.common.web.PagedModel;
import net.ccfish.jvue.service._AbstractService;
import net.ccfish.jvue.service.acl.AclResc;

/**
 * 
 * @author 袁贵
 * @version 1.0
 * @since  1.0
 */
public interface _BaseController<T, ID extends Serializable> {

    _AbstractService<T, ID> baseService();
    
    public static final Logger logger = LoggerFactory.getLogger(_BaseController.class);
    
    @GetMapping("")
    @AclResc(id = 1, code = "list", name = "列表")
    @ApiOperation(value = "列表")
    default PagedModel<T> list(PageParam pageParam) {

        Pageable page;
        if (StringUtils.isBlank(pageParam.getSort())) {
            page = new PageRequest(pageParam.getPage(), pageParam.getPageSize());
        } else {
            Sort sort = new Sort(Direction.ASC, pageParam.getSort());
            page = new PageRequest(pageParam.getPage(), pageParam.getPageSize(), sort);
        }
        
        Page<T> result = baseService().getAll(page);
        return PagedModel.from(result);
    }

    @AclResc(id = 2, code = "detail", name = "详情")
    @GetMapping("{id}")
    @ApiOperation(value = "详情")
    default BaseModel<T> detail(@PathVariable("id") ID id) {        
        T result = baseService().getOne(id);
        logger.debug("detail result: {}", result);
        return new BaseModel<T>().setData(result);
    }

    @PostMapping("")
    @AclResc(id = 3, code = "add", name = "追加")
    @ApiOperation(value = "追加")
    default BaseModel<T> add(@RequestBody T data) {
        baseService().save(data);
        return new BaseModel<T>().setData(data);
    }
    
    @PutMapping("{id}")
    @AclResc(id = 4, code = "update", name = "更新")
    @ApiOperation(value = "更新")
    default BaseModel<ID> update(@PathVariable("id") ID id, @RequestBody T data) {
        baseService().update(id, data);
        return new BaseModel<ID>().setData(id);
    }
    
    @DeleteMapping("{id}")
    @AclResc(id = 5, code = "delete", name = "删除")
    @ApiOperation(value = "删除")
    default BaseModel<ID> delete(@PathVariable("id") ID id) {
        baseService().delete(id);
        return new BaseModel<ID>().setData(id);
    }
    
}
