package cn.cpoet.blog.core.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;

/**
 * @author CPoet
 */
@Data
@Schema(title = "分页查询参数")
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "当前页号")
    private Integer pageNo;

    @Schema(title = "每页大小")
    private Integer pageSize;

    /**
     * 填充查询信息
     *
     * @param query 查询
     * @return 查询信息
     */
    public Query fillQuery(Query query) {
        return query.skip((long) (pageNo - 1) * pageSize).limit(pageSize);
    }
}
