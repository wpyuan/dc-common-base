package com.github.dc.common.base.structure;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.dc.common.base.json.DcEnumDeserializer;
import com.github.dc.common.base.json.DcEnumSerializer;

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
public interface IEnum<V, D> {
    /**
     * 值，枚举的name属性
     * @return 值
     */
    V value();

    /**
     * 描述，枚举的desc属性
     * @return 描述
     */
    D desc();

    /**
     * 所有枚举值与描述
     * @return 所有枚举值与描述
     */
    Map<V, D> all();
}