package cn.cpoet.blog.core.param;

import cn.cpoet.blog.api.annotation.term.Order;
import cn.cpoet.blog.api.constant.OrderMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * @author CPoet
 */
@Data
@Schema(title = "带排序条件的分页")
public class PageOrderParam extends PageParam {

    private static final long serialVersionUID = 1L;

    @Order
    private Map<String, OrderMode> queryOrders;
}
