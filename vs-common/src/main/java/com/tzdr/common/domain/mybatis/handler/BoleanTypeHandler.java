package com.tzdr.common.domain.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

public class BoleanTypeHandler extends BaseTypeHandler<Boolean> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Boolean parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		Integer result = null;
		if (parameter == null)
			result = null;
		else {
			if (parameter.booleanValue() == false)
				result = 0;
			if (parameter.booleanValue() == true)
				result = 1;
			ps.setInt(i, result.intValue());
		}		
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return this.getStringBoolean(rs.getString(columnName));
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return this.getStringBoolean(rs.getString(columnIndex));
	}

	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return this.getStringBoolean(cs.getString(columnIndex));
	}

	private Boolean getStringBoolean(String columnValue) {
		if (columnValue == null)
			return false;
		if (0 == Integer.parseInt(columnValue))
			return false;
		if (1 == Integer.parseInt(columnValue))
			return true;
		return false;
	}
}
