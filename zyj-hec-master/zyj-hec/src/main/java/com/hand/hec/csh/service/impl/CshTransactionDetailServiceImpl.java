package com.hand.hec.csh.service.impl;

import com.hand.hap.generator.service.impl.DBUtil;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshTransactionDetail;
import com.hand.hec.csh.service.ICshTransactionDetailService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshTransactionDetailServiceImpl extends BaseServiceImpl<CshTransactionDetail> implements ICshTransactionDetailService {

	@Autowired
	@Qualifier("sqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<CshTransactionDetail> queryCshTransactionDetail() {
		List<CshTransactionDetail> trxDetail = new ArrayList<>();
		SqlSession sqlSession = null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			Table table = CshTransactionDetail.class.getAnnotation(Table.class);
			String tableName = table.name();
			sqlSession = sqlSessionFactory.openSession();
			connection = DBUtil.getConnectionBySqlSession(sqlSession);
			resultSet = DBUtil.getTableColumnInfo(tableName, connection.getMetaData());
			while (resultSet.next()) {
				String string = resultSet.getString("COLUMN_NAME");
				String string2 = resultSet.getString("TYPE_NAME");
				if(string.length()>=7) {
					if (!string.substring(0, string.length()-1).equals("SEGMENT") && !string.substring(0, string.length()-2).equals("SEGMENT") && !string.substring(0,5).equals("LAST_")&& !string.substring(0,7).equals("CREATIO") && !string.substring(string.length()-3).equals("_TZ") &&!string.substring(string.length()-4).equals("_LTZ") && !string.substring(string.length()-7).equals("CHANNEL") && !string.substring(string.length()-7).equals("_STATUS") && !string.equals("PAYEE_ACCOUNT_TYPE") && !string2.equals("BIGINT")) {
						CshTransactionDetail cshTransactionDetail = new CshTransactionDetail();
						cshTransactionDetail.setColumnName(resultSet.getString("COLUMN_NAME").toLowerCase());
						cshTransactionDetail.setColumnType(resultSet.getString("TYPE_NAME"));
						cshTransactionDetail.setColumnComment(resultSet.getString("REMARKS"));
						trxDetail.add(cshTransactionDetail);
					}
				}else{
					if(string.equals("AMOUNT")){
						CshTransactionDetail cshTransactionDetail = new CshTransactionDetail();
						cshTransactionDetail.setColumnName(resultSet.getString("COLUMN_NAME").toLowerCase());
						cshTransactionDetail.setColumnType(resultSet.getString("TYPE_NAME"));
						cshTransactionDetail.setColumnComment(resultSet.getString("REMARKS"));
						trxDetail.add(cshTransactionDetail);
					}
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				DBUtil.closeResultSet(resultSet);
				DBUtil.closeConnection(connection);
				DBUtil.closeSqlSession(sqlSession);
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return trxDetail;
	}
}