package cn.cpoet.blog.core.support;

import cn.cpoet.blog.core.constant.StatusConst;
import cn.cpoet.blog.core.vo.RetVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * 自定义转json
 *
 * @author CPoet
 */
public class Jackson2JsonEncoder extends org.springframework.http.codec.json.Jackson2JsonEncoder {

    private final static ResolvableType RET_VO_RESOLVABLE_TYPE = ResolvableType.forClass(RetVO.class);

    public Jackson2JsonEncoder() {
        super();
    }

    public Jackson2JsonEncoder(ObjectMapper mapper, MimeType... mimeTypes) {
        super(mapper, mimeTypes);
    }

    @Override
    public Flux<DataBuffer> encode(Publisher<?> inputStream, DataBufferFactory bufferFactory, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        return super.encode(inputStream, bufferFactory, elementType, mimeType, hints);
    }

    @Override
    public DataBuffer encodeValue(Object value, DataBufferFactory bufferFactory, ResolvableType valueType, MimeType mimeType, Map<String, Object> hints) {
        if (valueType.isAssignableFrom(RET_VO_RESOLVABLE_TYPE)) {
            return super.encodeValue(value, bufferFactory, valueType, mimeType, hints);
        }
        RetVO<Object> retVO = new RetVO<>();
        retVO.setCode(StatusConst.OK.code());
        retVO.setMessage(StatusConst.OK.message());
        retVO.setData(value);
        return super.encodeValue(retVO, bufferFactory, RET_VO_RESOLVABLE_TYPE, mimeType, hints);
    }
}
