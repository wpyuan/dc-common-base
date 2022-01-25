package com.github.dc.common.base.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.dc.common.base.structure.BaseEnum;

import java.io.IOException;

/**
 * <p>
 * 枚举jackson序列化器
 * </p>
 *
 * @author wangpeiyuan
 * @date 2022/1/21 9:51
 */
public class DcEnumSerializer<V, D> extends JsonSerializer<BaseEnum<V, D>> {

    @Override
    public void serialize(BaseEnum<V, D> kviEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("value", kviEnum.value());
        jsonGenerator.writeObjectField("desc", kviEnum.desc());
        jsonGenerator.writeObjectField("data", kviEnum.all());
        jsonGenerator.writeEndObject();
    }
}
