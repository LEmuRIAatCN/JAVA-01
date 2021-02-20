package noob;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import noob.jdbc.StudentTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NoobJDBC {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.
                getConnection("jdbc:h2:~/test");
        StudentTable studentTable = new StudentTable(conn);
        studentTable.dropTable();
        studentTable.createTable();
        //增删改查都和具体的sql绑定死了，不灵活
        studentTable.insert();
        studentTable.select();
        studentTable.update();
        studentTable.select();
        studentTable.delete();
        studentTable.select();
        //批量操作，加载多个insert语句，通过一次commit一起执行
        studentTable.batchInsert();
        studentTable.select();
        //preparedStatement通过占位符进一步提升了sql的灵活性；事务提交保证了，复杂的多个sql执行的原子性
        studentTable.preparedStatementAndTransactionUpdate();
        studentTable.select();
        //没有连接池，需要自己维护连接的生命周期，还要考虑连接的性能
        conn.close();


        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:~/test");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);
        //连接池集中管理了连接，提升了应用操作数据库的性能，连接的生命周期也进行了合理的维护，应用自己不需要再额外的考虑
        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("select * from STUDENTS");
    }
}
