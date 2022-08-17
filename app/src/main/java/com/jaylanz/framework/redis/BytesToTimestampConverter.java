package com.jaylanz.framework.redis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@ReadingConverter
public class BytesToTimestampConverter implements Converter<byte[], Timestamp> {
    @Override
    public Timestamp convert(byte[] source) {
        return new Timestamp(Long.parseLong(new String(source)));
    }
}
