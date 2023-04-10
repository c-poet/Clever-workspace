package cn.cpoet.blog.core.common;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.api.core.IdGenerator;
import cn.cpoet.blog.core.util.UUIDUtil;

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
