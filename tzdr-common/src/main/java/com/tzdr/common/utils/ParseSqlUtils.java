package com.tzdr.common.utils;

import java.util.List;

import jodd.util.ObjectUtil;

import org.springframework.util.CollectionUtils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

/**
 * @author zhouchen
 * @version 创建时间：2015年9月23日 下午9:50:02
 * 类说明
 */
public class ParseSqlUtils {

	private static final String  SELECT_COUNT_PREFIX=" SELECT COUNT(0) FROM ";
	
	private static final String SELECT_WHERE=" WHERE ";

	/**
	 * sql 最小值必须大于0
	 */
	private static final int SQL_MIN_LENGTH=0;
	/**
	 * 根据原sql 解析为求总数的sql 
	 * @param sql
	 * @return
	 */
	public static String createCountSql(String sql){
		StringBuffer fromSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer();
		// parser得到AST
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
		List<SQLStatement> stmtList = parser.parseStatementList(); //
		if (CollectionUtils.isEmpty(stmtList)){
			return sql;
		}
		// 将AST通过visitor输出
		SQLASTOutputVisitor fromVisitor = SQLUtils.createFormatOutputVisitor(fromSql, stmtList, JdbcUtils.MYSQL);
		SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(whereSql, stmtList, JdbcUtils.MYSQL);

		for (SQLStatement stmt : stmtList) {
			if(stmt instanceof SQLSelectStatement){
				SQLSelectStatement sstmt = (SQLSelectStatement)stmt;
				SQLSelect sqlselect = sstmt.getSelect();
				SQLSelectQueryBlock query = (SQLSelectQueryBlock)sqlselect.getQuery();
				//获取from
				SQLTableSource tableSource = query.getFrom();
				if (!ObjectUtil.equals(null, tableSource)){
					tableSource.accept(fromVisitor);
				}
				//获取where
				SQLExpr whereExpr = query.getWhere();
				if (!ObjectUtil.equals(null, whereExpr)){
					whereExpr.accept(whereVisitor);
				}
			}
		}
		
		// 校验from值是否存在
		if (fromSql.length()<=SQL_MIN_LENGTH){
			return sql;
		}
		String resultSql = sql ;
		// 检测是否存在where语句
		if (whereSql.length()>SQL_MIN_LENGTH){
			 resultSql = SELECT_COUNT_PREFIX+fromSql.append(SELECT_WHERE).append(whereSql).toString();
		}
		
		resultSql = SELECT_COUNT_PREFIX+fromSql.toString();
		return resultSql;
	}
}
