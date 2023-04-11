package cn.cpoet.blog.core.ibatis;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.core.support.EnumHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

/**
 * @author CPoet
 */
public class IBatisEnumTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    private static EnumHandler enumHandler;
    private final Class<E> type;

    public IBatisEnumTypeHandler(Class<E> type) {
        this.type = type;
        // 获取枚举处理器
        if (enumHandler == null) {
            enumHandler = AppContextHolder.getBean(EnumHandler.class);
        }
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setObject(i, Types.NULL);
        } else {
            Object id = enumHandler.getId(parameter);
            ps.setObject(i, id);
        }
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        Object object = rs.getObject(columnName, enumHandler.getIdType(type));
        return object == null ? null : enumHandler.enumOfId(type, object);
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        Object object = rs.getObject(columnIndex, enumHandler.getIdType(type));
        return object == null ? null : enumHandler.enumOfId(type, object);
    }

    @Override
    public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object object = cs.getObject(columnIndex, enumHandler.getIdType(type));
        return object == null ? null : enumHandler.enumOfId(type, object);
    }
}
