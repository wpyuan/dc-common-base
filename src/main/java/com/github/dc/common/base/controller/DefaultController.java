package com.github.dc.common.base.controller;

import com.github.dc.common.base.service.DefaultService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PeiYuan
 */
public class DefaultController<E> {

    @Autowired
    private DefaultService<E> service;

    @ApiOperation("新建")
    @PostMapping("/create")
    public ResponseEntity<E> create(@RequestBody E entity) {
        service.insertSelective(entity);
        return ResponseEntity.ok(entity);
    }

    @ApiOperation("新建")
    @PostMapping("/create-form")
    public ResponseEntity<E> createByForm(E entity) {
        service.insertSelective(entity);
        return ResponseEntity.ok(entity);
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(E entity) {
        service.delete(entity);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("修改")
    @PutMapping("/update-form")
    public ResponseEntity<E> updateByForm(E entity) {
        service.updateByPrimaryKey(entity);
        return ResponseEntity.ok(entity);
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public ResponseEntity<E> update(@RequestBody E entity) {
        service.updateByPrimaryKey(entity);
        return ResponseEntity.ok(entity);
    }

    @ApiOperation("保存")
    @PostMapping("/save")
    public ResponseEntity<E> save(@RequestBody E entity) {
        service.save(entity);
        return ResponseEntity.ok(entity);
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    public ResponseEntity<E> detail(E entity) {
        return ResponseEntity.ok(service.detail(entity));
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    public ResponseEntity<List<E>> list(E entity) {
        return ResponseEntity.ok(service.list(entity));
    }
}
