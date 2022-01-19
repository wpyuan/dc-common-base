package com.github.dc.common.base.controller;

import com.github.dc.common.base.coverter.DateConverter;
import com.github.dc.common.base.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PeiYuan
 */
public class DefaultController<E> {

    @Autowired
    private DefaultService<E> service;

    @PostMapping("/create")
    public ResponseEntity<E> create(@RequestBody E entity) {
        service.insertSelective(entity);
        return ResponseEntity.ok(entity);
    }

    @PostMapping("/create-form")
    public ResponseEntity<E> createByForm(E entity) {
        service.insertSelective(entity);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> remove(E entity) {
        service.delete(entity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-form")
    public ResponseEntity<E> updateByForm(E entity) {
        service.updateByPrimaryKey(entity);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/update")
    public ResponseEntity<E> update(@RequestBody E entity) {
        service.updateByPrimaryKey(entity);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/detail")
    public ResponseEntity<E> detail(E entity) {
        return ResponseEntity.ok(service.detail(entity));
    }

    @GetMapping("/list")
    public ResponseEntity<List<E>> list(E entity) {
        return ResponseEntity.ok(service.list(entity));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
        if (genericConversionService != null) {
            genericConversionService.addConverter(new DateConverter());
        }
    }
}