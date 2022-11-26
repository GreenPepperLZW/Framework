package com.lzw.converter;

import com.lzw.bean.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 自定义消息转化器
 *
 * @author : lzw
 * @date : 2022/11/26
 * @since : 1.0
 */
public class LzwMessageConverter implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // 当请求参数标注有@RequestBody 注解时，将会执行这个方法
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        // 返回值类型为Person时，则该消息转换器会生效
        return clazz.isAssignableFrom(Person.class);
    }

    /**
     * 服务器要统计所有的MessageConverter都能写出哪些内容类型
     * 参考原生方法的写法：{@link AbstractJackson2HttpMessageConverter#getSupportedMediaTypes(Class)}
     *
     * @return
     */
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes("application/x-lzw");
    }

    @Override
    public Person read(Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //自定义协议数据的写出
        String data = person.getUserName() + ";" + person.getAge() + ";" + person.getBirth();
        //数据写出
        OutputStream body = outputMessage.getBody();

        body.write(data.getBytes(StandardCharsets.UTF_8));
    }
}
