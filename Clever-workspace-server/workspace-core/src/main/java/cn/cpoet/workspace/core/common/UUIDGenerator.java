package cn.cpoet.workspace.core.common;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.core.IdGenerator;
import cn.cpoet.workspace.core.util.UUIDUtil;

/**
 * 获取uuid
 *
 * @author CPoet
 */
public class UUIDGenerator implements IdGenerator<String> {
    @Override
    public String getName() {
        return SystemConst.ID_GENERATOR_UUID;
    }

    @Override
    public String nextId() {
        return UUIDUtil.random();
    }
}
