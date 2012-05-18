package kr.co.swtest.test.util;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.db2.Db2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Test Case with DBunit
 *
 * @author <a href="mailto:davidchoi@nextree.co.kr">최영목</a>
 * @since 2012. 5. 19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED)
public abstract class SpringDbUnitTestCase {

	/** 데이터소스 */
	@Autowired
	private DataSource dataSource;

	// -------------------------------------------------------------------------
	// Public Method
	// -------------------------------------------------------------------------

	/**
	 * 트랜젝션 시작전 수행할 작업
	 *
	 * @throws Exception
	 *             초기 데이터 로딩 중 문제가 생길 경우 발생
	 */
	@BeforeTransaction
	public void beforeTransaction() throws Exception {
		this.createTable();
		this.loadTestData();
	}

	/**
	 * 트랜젝션 종료후 수행할 작업
	 *
	 * @throws Exception
	 *             테스트 데이터 삭제 중 문제가 생길 경우 발생
	 */
	@AfterTransaction
	public void afterTransaction() throws Exception {
		this.cleanTestData();
	}

	// -------------------------------------------------------------------------
	// Protected Method
	// -------------------------------------------------------------------------

	/**
	 * 테스트데이터를 로드하기 위해서는 이 메소드를 구현해야 함
	 *
	 * @return 테스트데이터 XML 리소스 위치. 예)
	 *         kr/co/swtest/customerentity/dao/sqlmap/xxx.xml
	 */
	protected List<String> addDataFiles() {
		return null;
	}

	/**
	 * 테스트데이터를 로드하기 위해서는 이 메소드를 구현해야 함
	 *
	 * @return 테스트데이터 XML 리소스 위치. 예)
	 *         kr/co/swtest/customerentity/dao/sqlmap/xxx.xml
	 */
	protected String addDataFile() {
		return null;
	}

	/**
	 * @return 데이터소스
	 */
	protected DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * @return DbUnit 데이터타입
	 */
	protected DbUnitDataType getDbUnitDataType() {
		return DbUnitDataType.DEFAULT;
	}

	/**
	 * @return 스키마명
	 */
	protected String getSchemaName() {
		return null;
	}

	/**
	 * 테이블 생성
	 */
	protected void createTable() {
		// 테이블 생성
	}

	// -------------------------------------------------------------------------
	// Private Method
	// -------------------------------------------------------------------------

	/**
	 * 초기 테스트 데이터 로딩
	 */
	private void loadTestData() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	}

	/**
	 * 테스트 데이터 삭제
	 */
	private void cleanTestData() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
	}

	/**
	 * IDatabaseConnection 생성
	 *
	 * @return 오라클용 IDatabaseConnection. <code>not null</code> 보장.
	 */
	private IDatabaseConnection getConnection() throws Exception {
		DatabaseConnection connection = new DatabaseConnection(
				this.dataSource.getConnection(), getSchemaName());
		DatabaseConfig config = connection.getConfig();
		setDataTypeFa1ctory(config, getDbUnitDataType());
		return connection;
	}

	/**
	 * IDataSet 생성
	 *
	 * @return IDataSet. <code>not null</code> 보장.
	 */
	private IDataSet getDataSet() throws Exception {
		List<String> dataFiles = this.getDataFiles();
		if (dataFiles == null) {
			dataFiles = new ArrayList<String>();
		}

		List<IDataSet> dbUnitDataSets = new ArrayList<IDataSet>(
				dataFiles.size());

		for (String file : dataFiles) {
			ReplacementDataSet dataSet = new ReplacementDataSet(
					new FlatXmlDataSetBuilder().build(Thread.currentThread()
							.getContextClassLoader().getResourceAsStream(file)));
			// 값이 [NULL] 이면 null 값으로 처리함.
			dataSet.addReplacementObject("[NULL]", null);
			dbUnitDataSets.add(dataSet);
		}

		return new CompositeDataSet(
				dbUnitDataSets.toArray(new IDataSet[dbUnitDataSets.size()]));
	}

	/**
	 * 테스트데이터 XML 파일 위치 목록
	 *
	 * @return 테스트데이터 XML 파일 위치 목록. <code>not null</code> 보장.
	 */
	private List<String> getDataFiles() {
		List<String> dataFiles = new ArrayList<String>();

		if (this.addDataFiles() != null) {
			dataFiles.addAll(this.addDataFiles());
		}

		if (this.addDataFile() != null) {
			dataFiles.add(this.addDataFile());
		}

		return dataFiles;
	}

	/**
	 * DataTypeFa1ctory 설정
	 *
	 * @param config DatabaseConfig
	 * @param dbUnitDataType DbUnitDataType
	 */
	private void setDataTypeFa1ctory(DatabaseConfig config, DbUnitDataType dbUnitDataType) {
		switch (dbUnitDataType) {
			case DEFAULT:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new DefaultDataTypeFactory());
				break;

			case MYSQL:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
				break;

			case MSSQL:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
				break;

			case DB2:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Db2DataTypeFactory());
				break;

			case ORACLE:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
				break;

			case ORACLE10G:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
				break;

			case HSQLDB:
				config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
				break;

			default:
				break;
		}
	}

}
