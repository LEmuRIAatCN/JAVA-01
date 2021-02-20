package noob.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StudentTable {
    Connection con;

    public StudentTable(Connection con) {
        this.con = con;
    }

    public void createTable() throws SQLException {
        String createString =
                "create table STUDENTS " + "(ID integer NOT NULL, " +
                        "NAME varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS STUDENTS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select() throws SQLException {
        String query = "select ID, NAME from STUDENTS";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("STUDENTS and their ID Numbers:");
            while (rs.next()) {
                String s = rs.getString("NAME");
                int n = rs.getInt("ID");
                System.out.println(s + "   " + n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("delete from STUDENTS where ID>1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("update STUDENTS set NAME='haha' where ID=1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("insert into STUDENTS values(1, 'qwe')");
            stmt.executeUpdate("insert into STUDENTS values(2, 'asd')");
            stmt.executeUpdate("insert into STUDENTS values(3, 'zxc')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparedStatementAndTransactionUpdate() throws SQLException {
        String updateName =
                "update STUDENTS set NAME = ? where ID = ?";
        Map<Integer, String> datas = new HashMap<>();
        datas.put(1, "aaaa");
        datas.put(2, "bbbb");
        datas.put(3, "cccc");
        try (PreparedStatement updateSales = con.prepareStatement(updateName)) {
            con.setAutoCommit(false);
            for (Map.Entry<Integer, String> e : datas.entrySet()) {
                updateSales.setString(1, e.getValue());
                updateSales.setInt(2, e.getKey());
                updateSales.executeUpdate();
                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException excep) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void batchInsert() throws SQLException {
        con.setAutoCommit(false);
        try (Statement stmt = con.createStatement()) {
            stmt.addBatch("insert into STUDENTS VALUES(4, 'wer')");
            stmt.addBatch("insert into STUDENTS VALUES(5, 'sdf')");
            stmt.addBatch("insert into STUDENTS VALUES(6, 'xcv')");
            stmt.addBatch("insert into STUDENTS VALUES(7, 'ert')");
            int[] updateCounts = stmt.executeBatch();
            con.commit();
        } catch (BatchUpdateException b) {
            b.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            con.setAutoCommit(true);
        }
    }
}
