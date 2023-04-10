package cn.cpoet.blog.core.configuration;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.core.support.EnumHandler;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 序列化配置和扩展类型转换器
 *
 * @author Cpoet
 * @see Converter
 */
@Configuration
@SuppressWarnings("all")
public class JacksonConvertersConfig {
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private final static String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    private final static String DEFAULT_DATE_TIME_FORMAT = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;

    private final DateTimeFormatter dateFormat;

    private final DateTimeFormatter timeFormat;

    private final DateTimeFormatter dateTimeFormatter;

    public JacksonConvertersConfig(JacksonProperties jacksonProperties) {
        String jacksonDateFormat = jacksonProperties.getDateFormat();
        dateFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        timeFormat = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
        dateTimeFormatter = DateTimeFormatter.ofPattern(StringUtils.hasLength(jacksonDateFormat) ? jacksonDateFormat : DEFAULT_DATE_TIME_FORMAT, Locale.CHINESE);
    }

    @Bean
    public Converter<String, LocalTime> str2localTime() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String str) {
                return LocalTime.parse(str, timeFormat);
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> str2localDate() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String str) {
                return LocalDate.parse(str, dateFormat);
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime> str2localDatetime() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String str) {
                return LocalDateTime.parse(str, dateTimeFormatter);
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(EnumHandler enumHandler) {
        return builder -> {
            // 时间处理
            builder
                .serializerByType(LocalDate.class, new LocalDateSerializer(dateFormat))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(timeFormat))
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(dateFormat))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(timeFormat))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
            // 枚举处理
            builder.serializerByType(Enum.class, new JsonSerializer<Enum>() {
                @Override
                public void serialize(Enum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeObject(enumHandler.getEnumAppear(value));
                }
            }).deserializerByType(Enum.class, new JsonDeserializer<Object>() {
                @Override
                public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                    return null;
                }
            });
            // bool值处理
            builder.serializerByType(Boolean.class, new JsonSerializer<Boolean>() {
                @Override
                public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeNumber(Boolean.TRUE.equals(value) ? SystemConst.BOOL_TRUE : SystemConst.BOOL_FALSE);
                }
            }).deserializerByType(Boolean.class, new JsonDeserializer<Object>() {
                @Override
                public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                    return null;
                }
            });
        };
    }
}
