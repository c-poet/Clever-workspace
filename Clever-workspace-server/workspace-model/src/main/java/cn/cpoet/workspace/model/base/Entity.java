package cn.cpoet.workspace.model.base;

import java.io.Serializable;

/**
 * 普通实体对象
 *
 * @author CPoet
 */
public interface Entity<ID> extends Serializable {
    /**
     * 获取实体id
     *
     * @return 实体id
     */
    ID getId();
}
