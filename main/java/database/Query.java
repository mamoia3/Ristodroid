package database;

import java.sql.SQLException;

public class Query {
    private void select(String table) throws SQLException {
        String query="select * from " + table;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void select(String selection, String table) throws SQLException {
        String query="select ";
        query += selection + " from ";
        query += table;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void selectWhere(String table, String condition) throws SQLException {
        String query="select * from ";
        query += table;
        query += " where ";
        query += condition;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void select(String[] selection, String table) throws SQLException {
        String query = "select ";
        String sel = null;
        for(String s : selection) {
            sel += s + ", ";
        }
        sel = sel.substring(0, sel.length()-2);
        query += sel + " from " + table;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void selectwhere(String[] selection, String table, String condition) throws SQLException {
        String query = "select ";
        String sel = null;
        for(String s : selection) {
            sel += s + ", ";
        }
        sel = sel.substring(0, sel.length()-2);
        query += sel + " from " + table;
        query += " where " + condition;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void insert(String table, String...values) throws SQLException {
        String query = "insert into " + table +" values (";
        String val=null;
        for(String s : values) {
            val += s +", ";
        }
        val = val.substring(0, val.length()-2);
        query += val + ")";
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void update(String table, String column, String condition) throws SQLException {
        String query = "update " + table + " set " + column + " where " + condition;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void incrementQuantity(String table, String column, String condition, int id) throws SQLException {
        String query = "update " + table + " set " + column +"=" + column + "+1 where id =" + id;
        DataBase.connection().createStatement().executeQuery(query);
    }


    private void decrementQuantity(String table, String column, String condition, int id) throws SQLException {
        String query = "update " + table + " set " + column +"=" + column + "-1 where id =" + id;
        DataBase.connection().createStatement().executeQuery(query);
    }
}
