package cn.cpoet.workspace.ibatis.support;

import cn.cpoet.workspace.ibatis.util.EntityUtil;
import tk.mybatis.mapper.genid.GenId;

/**
 * 适配tk生成主键
 *
 * @author CPoet
 */
public class TkGenIdImpl implements GenId<Long> {
    @Override
    public Long genId(String table, String column) {
        return EntityUtil.nextId();
    }
}
