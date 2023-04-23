package cn.cpoet.workspace.core.vo;

import cn.cpoet.workspace.core.constant.Status;
import cn.cpoet.workspace.core.constant.StatusConst;
import cn.cpoet.workspace.core.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Data
@Schema(title = "分页结果响应")
public class PageVO<T> extends RetVO<List<T>> {
    @Schema(title = "数据总计")
    private Long total;

    @Schema(title = "当前页号")
    private Integer pageNo;

    @Schema(title = "分页大小")
    private Integer pageSize;

    public PageVO() {
    }

    public PageVO(PageVO<?> other, List<T> data) {
        super(other, data);
        total = other.total;
        pageNo = other.pageNo;
        pageSize = other.pageSize;
    }

    public <R> PageVO<R> map(Function<T, R> mapper) {
        List<R> list = this.getData()
            .stream()
            .map(mapper)
            .collect(Collectors.toList());
        return new PageVO<>(this, list);
    }

    public PageVO<T> consume(Consumer<List<T>> consumer) {
        consumer.accept(getData());
        return this;
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(getData());
    }

    public static <T> PageVO<T> ok(Long total, List<T> data, PageParam param) {
        return of(StatusConst.OK, total, data, param);
    }

    public static <T> PageVO<T> of(Status status, Long total, List<T> data, PageParam param) {
        return of(status, total, data, param.getPageNo(), param.getPageSize());
    }

    public static <T> PageVO<T> of(Status status, Long total, List<T> data, Integer pageNo, Integer pageSize) {
        return of(status.code(), status.message(), total, data, pageNo, pageSize);
    }

    public static <T> PageVO<T> of(String code, String message, Long total, List<T> data, Integer pageNo, Integer pageSize) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setCode(code);
        pageVO.setMessage(message);
        pageVO.setData(data);
        pageVO.setTotal(total);
        pageVO.setPageNo(pageNo);
        pageVO.setPageSize(pageSize);
        return pageVO;
    }
}
