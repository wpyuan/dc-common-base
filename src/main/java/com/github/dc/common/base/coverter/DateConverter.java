package com.github.dc.common.base.coverter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *     日期格式转换器
 * </p>
 *
 * @author wangpeiyuan
 * @date 2022/1/19 16:45
 */
public class DateConverter implements Converter<String, Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_STAMP_FORMAT = "^\\d+$";

    @Override
    public Date convert(String value) {

        if(value == null || value.trim().length() == 0) {
            return null;
        }

        value = value.trim();
        Date date = null;
        try {
            if (value.contains("-")) {
                SimpleDateFormat formatter;
                if (value.contains(":")) {
                    formatter = new SimpleDateFormat(DATE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                date = formatter.parse(value);
            } else if (value.matches(TIME_STAMP_FORMAT)) {
                Long lDate = new Long(value);
                date = new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", value));
        }

        return date;
    }
}
