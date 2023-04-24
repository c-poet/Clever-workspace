package cn.cpoet.workspace.ibatis.support;

import cn.cpoet.workspace.api.core.EnumTool;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

/**
 * @author CPoet
 */
public class IBatisEnumTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    private final Class<E> type;

    public IBatisEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setObject(i, Types.NULL);
        } else {
            Object id = EnumTool.getId(parameter);
            ps.setObject(i, id);
        }
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        Object object = rs.getObject(columnName, EnumTool.getIdType(type));
        return object == null ? null : EnumTool.enumOfId(type, object);
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        Object object = rs.getObject(columnIndex, EnumTool.getIdType(type));
        return object == null ? null : EnumTool.enumOfId(type, object);
    }

    @Override
    public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object object = cs.getObject(columnIndex, EnumTool.getIdType(type));
        return object == null ? null : EnumTool.enumOfId(type, object);
    }
}
