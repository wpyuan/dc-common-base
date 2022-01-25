package com.github.dc.common.base.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.github.dc.common.base.structure.BaseEnum;
import com.github.mybatis.crud.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * <p>
 * 枚举jackson反序列化器
 * </p>
 *
 * @author wangpeiyuan
 * @date 2022/1/21 9:55
 */
@Slf4j
public class DcEnumDeserializer<V, D> extends JsonDeserializer<BaseEnum<V, D>> {
    @Override
    public BaseEnum<V, D> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String enumFieldName = jsonParser.currentName();
        Object target = jsonParser.getCurrentValue();
        Class enumFieldClass = null;
        if (target instanceof Collection) {
            JsonStreamContext parsingContext = jsonParser.getParsingContext();
            JsonStreamContext parent = parsingContext.getParent();
            target = parent.getCurrentValue();
            String listFieldName = parent.getCurrentName();
            try {
                Field listField = target.getClass().getDeclaredField(listFieldName);
                ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
                Type listActualTypeArguments = listGenericType.getActualTypeArguments()[0];
                enumFieldClass = (Class) listActualTypeArguments;
            } catch (Exception e) {
                ;
            }
        } else {
            enumFieldClass = BeanUtils.findPropertyType(enumFieldName, target.getClass());
        }

        if (enumFieldClass == null) {
            log.warn("获取枚举类型class异常，反序列化失败，该字段返回null：{}.{}", target.getClass(), enumFieldName);
            return null;
        }

        String name = null;
        if (node.getNodeType() == JsonNodeType.STRING) {
            name = node.asText();
        } else if (node.getNodeType() == JsonNodeType.NUMBER) {
            for (Object obj : enumFieldClass.getEnumConstants()) {
                String value = String.valueOf(ReflectionUtils.getField(ReflectionUtils.findField(enumFieldClass, "value"), obj));
                if (node.asText().equals(value)) {
                    return (BaseEnum<V, D>) obj;
                }
            }
        } else {
            if (isNumeric(node.get("value").asText())) {
                name = node.get("desc").asText();
            } else {
                name = node.get("value").asText();
            }
        }
        BaseEnum<V, D> iEnum = null;
        if (StringUtil.isNotBlank(name)) {
            iEnum = (BaseEnum<V, D>) Enum.valueOf(enumFieldClass, name);
        }
        return iEnum;
    }

    private static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
