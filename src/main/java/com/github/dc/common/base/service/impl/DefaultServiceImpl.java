package com.github.dc.common.base.service.impl;

import com.github.dc.common.base.service.DefaultService;
import com.github.mybatis.crud.mapper.BatchInsertMapper;
import com.github.mybatis.crud.mapper.DefaultMapper;
import com.github.mybatis.crud.structure.Condition;
import com.github.mybatis.crud.structure.LeftJoin;
import com.github.mybatis.crud.structure.Three;
import com.github.mybatis.crud.util.EntityUtil;
import com.github.mybatis.crud.util.ReflectionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author PeiYuan
 */
public class DefaultServiceImpl<E> implements DefaultService<E> {

    @Autowired
    private DefaultMapper<E> defaultMapper;
    @Autowired
    private BatchInsertMapper<E> batchInsertMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(E entity) {
        return defaultMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(E entity) {
        return defaultMapper.insertSelective(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<E> list) {
        batchInsertMapper.batchInsert(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertSelective(List<E> list) {
        batchInsertMapper.batchInsertSelective(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Serializable id) {
        return defaultMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(E entity) {
        return defaultMapper.deleteByPrimaryKey(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(E entity) {
        return defaultMapper.delete(this.createEqCondition(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<E> list) {
        return defaultMapper.batchDelete(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(E entity) {
        return defaultMapper.updateByPrimaryKey(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(E entity) {
        return defaultMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateField(E entity, String... fields) {
        return defaultMapper.updateField(entity, fields);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdate(List<E> list) {
        int updateCount = 0;
        for (E entity : list) {
            updateCount += this.updateByPrimaryKey(entity);
        }
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateSelective(List<E> list) {
        int updateCount = 0;
        for (E entity : list) {
            updateCount += this.updateByPrimaryKeySelective(entity);
        }
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateField(List<E> list, String... fields) {
        int updateCount = 0;
        for (E entity : list) {
            updateCount += this.updateField(entity, fields);
        }
        return updateCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(E entity) {
        int affectedCount = 0;
        Three<String, Object, Class> id = EntityUtil.getIdNameAndValueAndType(entity);
        if (id.getSecond() == null) {
            affectedCount = this.insertSelective(entity);
        } else {
            affectedCount = this.updateByPrimaryKey(entity);
        }
        return affectedCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveSelective(E entity) {
        int affectedCount = 0;
        Three<String, Object, Class> id = EntityUtil.getIdNameAndValueAndType(entity);
        if (id.getSecond() == null) {
            affectedCount = this.insertSelective(entity);
        } else {
            affectedCount = this.updateByPrimaryKeySelective(entity);
        }
        return affectedCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public E selectByPrimaryKey(Serializable id) {
        return defaultMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public E selectByPrimaryKey(E entity) {
        return defaultMapper.selectByPrimaryKey(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public List<E> listAll() {
        return defaultMapper.listAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public List<E> list(Condition<E> condition) {
        return defaultMapper.list(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public List<E> list(E entity) {
        if (entity == null) {
            return this.listAll();
        }
        Condition<E> condition = this.createEqCondition(entity);
        if (CollectionUtils.isEmpty(condition.getWheres())) {
            condition.diy(null, null, "1", "=", 1, null);
        }
        return this.list(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public E detail(Condition<E> condition) {
        return defaultMapper.detail(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public E detail(E entity) {
        Objects.requireNonNull(entity, "单条明细的查询条件不能为空");
        return this.detail(this.createEqCondition(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public <R> List<R> list(Class<R> resultTypeClass, LeftJoin<E> leftJoin) {
        return defaultMapper.list(resultTypeClass, leftJoin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public <R> R detail(Class<R> resultTypeClass, LeftJoin<E> leftJoin) {
        return defaultMapper.detail(resultTypeClass, leftJoin);
    }

    /**
     * 由entity有值字段作为等于关系条件构造Condition
     * @param entity 实体类有值字段作为等于关系条件
     * @return Condition条件
     */
    public Condition<E> createEqCondition(E entity) {
        Condition<E> condition = Condition.<E>builder(entity).build();
        List<String> eqFields = EntityUtil.getAllHaveValueFieldName(entity, null);
        Method eq = ReflectionUtil.findMethod(condition.getClass(), "eq", String.class);
        ReflectionUtil.makeAccessible(eq);
        for (String eqField: eqFields) {
            condition = (Condition<E>) ReflectionUtil.invokeMethod(eq, condition, eqField);
        }

        return condition;
    }
}
