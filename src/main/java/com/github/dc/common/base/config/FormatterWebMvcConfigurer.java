package com.github.dc.common.base.config;

import com.github.dc.common.base.annotation.ConverterHandler;
import com.github.dc.common.base.coverter.DateConverter;
import org.reflections.Reflections;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

/**
 * <p>
 *     接口入参格式化转换器
 * </p>
 *
 * @author wangpeiyuan
 * @date 2022/1/25 9:40
 */
@Configuration
public class FormatterWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(new DateConverter());
        Reflections reflections = new Reflections();
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(ConverterHandler.class);
        for (Class clazz: classSet) {
            if (Enum.class.isAssignableFrom(clazz)) {
                formatterRegistry.addConverter((Converter<?, ?>) clazz.getEnumConstants()[0]);
            }
        }
    }
}
