package cn.cpoet.workspace.api.core;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.context.AppContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * id生成工具
 *
 * @author CPoet
 */
public abstract class IdTool {

    private  static IdGenerator<Long> ID_GENERATOR;

    private IdTool() {
    }

    /**
     * 获取id
     *
     * @return 唯一id
     */
    @SuppressWarnings("unchecked")
    public static Long nextId() {
        if (ID_GENERATOR == null) {
            ID_GENERATOR = (IdGenerator<Long>) AppContextHolder.getBean(SystemConst.ID_GENERATOR,IdGenerator.class);
        }
        return ID_GENERATOR.nextId();
    }

    /**
     * 获取指定大小的id列表
     *
     * @param size 列表大小
     * @return id列表
     */
    public static List<Long> nextId(int size) {
        List<Long> list = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            list.add(nextId());
        }
        return list;
    }
}
