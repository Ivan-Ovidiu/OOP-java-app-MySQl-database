package DAO;

import java.sql.SQLException;

public interface InterfaceDAO<T> {
    void add(T entity) throws SQLException;
    T read(String id) throws SQLException;
    void delete(T entity) throws SQLException;
    void update(T entity) throws SQLException;
}
