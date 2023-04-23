package cn.cpoet.workspace.core.module;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 路径绑定信息
 *
 * @author CPoet
 */
@Data
@Setter(AccessLevel.PACKAGE)
public class Mapping {
    /**
     * 路径
     */
    private String path;
}
