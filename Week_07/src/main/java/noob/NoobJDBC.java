package noob;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class NoobJDBC {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.
                getConnection("jdbc:mysql://localhost:3306/noob?" +
                        "user=root&password=111111");
//        long start = 0;
//        try (Statement stmt = conn.createStatement()) {
//            for (int i = 0; i < 1000000; i++) {
//                stmt.executeUpdate("insert into noob_order(member_id, receiver_name, receiver_phone, order_sn) values(1, '1', '123123123', '1');");
//                System.out.println(i);
//            }
//                //life-long
//            System.out.println("cost: "+(System.currentTimeMillis()-start)/1000);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try (Statement statement = conn.createStatement()) {
//            conn.setAutoCommit(false);
//            try (Statement stmt = conn.createStatement()) {
//                for (int i = 0; i < 1000000; i++) {
//                    stmt.addBatch("insert into noob_order(member_id, receiver_name, receiver_phone, order_sn) values(1, '1', '123123123', '1');");
//                    //System.out.println(i);
//                }
//                start = System.currentTimeMillis();
//                int[] updateCounts = stmt.executeBatch();
//                conn.commit();
//            } catch (BatchUpdateException b) {
//                b.printStackTrace();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            } finally {
//                conn.setAutoCommit(true);
//                //132s
//                System.out.println("cost: "+(System.currentTimeMillis()-start)/1000);
//            }
//        }
//        try (Statement stmt = conn.createStatement()) {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("insert into noob_order(member_id, receiver_name, receiver_phone, order_sn) values(1, '1', '123123123', '1')");
//            for (int i = 0; i < 999999; i++) {
//                stringBuffer.append(",(1, '1', '123123123', '1')");
//                System.out.println(i);
//            }
//            stringBuffer.append(";");
//            start = System.currentTimeMillis();
//            stmt.executeUpdate(stringBuffer.toString());
//            //8s
//            System.out.println("cost: "+(System.currentTimeMillis()-start)/1000);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        try (Statement stmt = conn.createStatement()) {
//            StringBuffer stringBuffer = new StringBuffer();
//            //翻车，只能插入一行
//            stringBuffer.append("load data infile 'E:/idea_projects/JAVA-01/Week_07/src/main/resources/1M-data.txt' into table noob_order FIELDS TERMINATED BY ',' LINES TERMINATED BY ';' (member_id, receiver_name, receiver_phone, order_sn)");
//            stringBuffer.append(";");
//            start = System.currentTimeMillis();
//            stmt.executeUpdate(stringBuffer.toString());
//            System.out.println("cost: "+(System.currentTimeMillis()-start)/1000);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        conn.close();


        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/noob?" +
                "user=root&password=111111");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);
        //连接池集中管理了连接，提升了应用操作数据库的性能，连接的生命周期也进行了合理的维护，应用自己不需要再额外的考虑
        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("truncate noob_order;");
        for(int i=0;i<10;i++){
            new Thread(new Worker(ds.getConnection()), "Worker--"+i).start();
        }
    }

    static class Worker implements Runnable {
        Connection connection;

        public Worker(Connection c) {
            this.connection = c;
        }
        @Override
        public void run() {
            long start=0;
            try {
                connection.setAutoCommit(false);
                try (Statement stmt = connection.createStatement()) {
                    for (int i = 0; i < 100000; i++) {
                        stmt.addBatch("insert into noob_order(member_id, receiver_name, receiver_phone, order_sn) values(1, '1', '123123123', '1');");
                        //System.out.println(Thread.currentThread().getName()+"--"+i);
                    }
                    start = System.currentTimeMillis();
                    int[] updateCounts = stmt.executeBatch();
                    connection.commit();
                } catch (BatchUpdateException b) {
                    b.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    connection.setAutoCommit(true);
                    connection.close();
                    System.out.println(Thread.currentThread().getName()+"cost: " + (System.currentTimeMillis() - start) / 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
