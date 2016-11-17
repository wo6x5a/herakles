/**
 * 
 */
package ddl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import com.lcw.herakles.platform.common.ddl.dialect.MysqlTableDDL;
import com.lcw.herakles.platform.common.ddl.dialect.OracleTableDDL;
import com.lcw.herakles.platform.common.util.scan.EntityScan;
public class DdlTest {
	
//	private final static String dialect = "ORACLE";
    
    private final static String dialect = "MYSQL";
	
	private final static String db_name = "herakles";
	private final static String db_username = "herakles";
	private final static String db_password = "123456";
	// oracle config
	private final static String db_oracle_data_tablespace = "HERAKLES_DATA";
	private final static String db_oracle_index_tablespace = "HERAKLES_IDX";
	private final static String db_oracle_hist_tablespace = "HERAKLES_HIST";
	private final static String db_oracle_data_files = "/u01/app/oracle/oradata/orcl/";
	
	private boolean isMysql(){
		return dialect.equalsIgnoreCase("MYSQL");
	}
	
	private boolean isOracle(){
		return dialect.equalsIgnoreCase("ORACLE");
	}
	
	@Test
	public void createDatabaseScriptTest(){
		if(isMysql()){
			createMysqlDatabaseScript(true);
		}
		else if(isOracle()){
			createOracleDatabaseScript(true);
		}
	}
	
	private void createMysqlDatabaseScript(boolean rebuild){
		MysqlTableDDL tableDDL = new MysqlTableDDL(rebuild);
		String sql = tableDDL.createDatabase(db_name, db_username, db_password);
		System.out.println(sql);
	}
	private void createOracleDatabaseScript(boolean rebuild){
		OracleTableDDL tableDDL = new OracleTableDDL(rebuild, db_oracle_data_tablespace, 
				db_oracle_index_tablespace, db_oracle_hist_tablespace, db_oracle_data_files);
		String sql = tableDDL.createDatabase(db_name, db_username, db_password);
		System.out.println(sql);
	}

//	@Test
//	public void createTableScriptTest(){
//		for(Class<?> clazz :Arrays.asList(
//				BizLogPo.class
//				)){
//			if(isMysql()){
//				createMysqlTableScript(clazz, true);
//			}
//			else if(isOracle()){
//				createOracleTableScript(clazz, true);
//			}
//		}
//	}
//	
//	private void createMysqlTableScript(Class<?> entityClass, boolean rebuild){
//		MysqlTableDDL tableDDL = new MysqlTableDDL(entityClass, true);
//		String tableScripts = tableDDL.createTableScript();
//		System.out.println(tableScripts);
//	}
//	private void createOracleTableScript(Class<?> entityClass, boolean rebuild){
//		OracleTableDDL tableDDL = new OracleTableDDL(entityClass, true, db_oracle_data_tablespace, 
//				db_oracle_index_tablespace, db_oracle_hist_tablespace, db_oracle_data_files);
//		String tableScripts = tableDDL.createTableScript();
//		System.out.println(tableScripts);
//	}
	
	@Test
	public void generateDropTablesScriptTest() throws IOException{
		EntityScan entityScan = new EntityScan();
		ImmutableSet<Class<?>> entities = entityScan.getEntities();
		StringBuilder scripts = new StringBuilder();
		for (Class<?> entityClass : entities) {
			String tableScripts = "";
			if(isMysql()){
				MysqlTableDDL tableDDL = new MysqlTableDDL(entityClass, false);
				tableScripts = tableDDL.dropTableScript();
			}
			else if(isOracle()){
				OracleTableDDL tableDDL = new OracleTableDDL(entityClass, false, db_oracle_data_tablespace, 
						db_oracle_index_tablespace, db_oracle_hist_tablespace, db_oracle_data_files);
				tableScripts = tableDDL.dropTableScript();
			}
			scripts.append(tableScripts);
		}
		System.out.println(scripts.toString());
	}
	
	@Test
	public void generateTableScriptFileTest() throws IOException{
		EntityScan entityScan = new EntityScan();
		ImmutableSet<Class<?>> entities = entityScan.getEntities();
		StringBuilder scripts = new StringBuilder();
		for (Class<?> entityClass : entities) {
			String tableScripts = "";
			if(isMysql()){
				MysqlTableDDL tableDDL = new MysqlTableDDL(entityClass, false);
				tableScripts = tableDDL.createTableScript();
			}
			else if(isOracle()){
				OracleTableDDL tableDDL = new OracleTableDDL(entityClass, false, db_oracle_data_tablespace, 
						db_oracle_index_tablespace, db_oracle_hist_tablespace, db_oracle_data_files);
				tableScripts = tableDDL.createTableScript();
			}
			scripts.append(tableScripts);
		}
		Files.write(scripts, new File("table.sql"), Charset.forName("UTF-8"));
	}
	
}
