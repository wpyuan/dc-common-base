package com.github.dc.common.base.structure;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.dc.common.base.json.DcEnumDeserializer;
import com.github.dc.common.base.json.DcEnumSerializer;
import com.github.mybatis.crud.type.IEnum;

import java.util.Map;

/**
 * <p>
 *     枚举接口类
 * </p>
 *
 * @author wangpeiyuan
 * @date 2022/1/21 11:30
 */
@JsonDeserialize(using = DcEnumDeserializer.class)
@JsonSerialize(using = DcEnumSerializer.class)
public interface BaseEnum<V, D> extends IEnum<V, D> {
}