package edu.school21.orm.manager;

import edu.school21.orm.annotation.OrmColumn;
import edu.school21.orm.annotation.OrmColumnId;
import edu.school21.orm.annotation.OrmEntity;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrmManager {

    private DataSource dataSource;
    private Map<Class<?>, String> classTableMap = new HashMap<>();

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
        try  {
            File file = new File("./target/schema.sql");
            if (!file.exists()) {
                return;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    stringBuilder.append(scanner.nextLine());
                }
                String[] queries = stringBuilder.toString().split(";");
                for (String query : queries) {
                    System.out.println(query);
                    dataSource.getConnection().prepareStatement(query).execute();
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(Object entity) {
        String insertSql = generateInsertSql(entity);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            setPreparedStatementValues(preparedStatement, entity, false);

            System.out.println(extractCleanQuery(preparedStatement.toString()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Object entity) {
        String updateSql = generateUpdateSql(entity);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            setPreparedStatementValues(preparedStatement, entity, true);

            System.out.println(extractCleanQuery(preparedStatement.toString()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> T findById(Long id, Class<T> clazz) {
        T entity = null;

        try (Connection connection = dataSource.getConnection()) {
            String tableName = clazz.getAnnotation(OrmEntity.class).table();
            if (tableName.isEmpty()) {
                tableName = clazz.getSimpleName();
            }

            String idColumnName = getIdColumnName(clazz);


            String selectSql = "SELECT * FROM " + tableName + " WHERE "+ idColumnName + " = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                preparedStatement.setLong(1, id);
                System.out.println(extractCleanQuery(preparedStatement.toString()));
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        entity = mapResultSetToEntity(resultSet, clazz);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entity;
    }

    private void setPreparedStatementValues(PreparedStatement preparedStatement, Object entity, boolean idIsLast) {
        Class<?> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();

        int parameterIndex = 1;
        Object idValue = null;

        for (Field field : fields) {
            OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
            OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
            if (Modifier.isStatic(field.getModifiers())
                || (ormColumn == null && ormColumnId == null)) {
                continue;
            }

            field.setAccessible(true);
            try {
                if (ormColumnId != null) {
                    idValue = field.get(entity);
                    if (idIsLast) {
                        continue;
                    }
                }
                preparedStatement.setObject(parameterIndex, field.get(entity));
                parameterIndex++;
            } catch (IllegalAccessException | SQLException e) {
                System.out.println("Error while setting values to prepared statement");
            }
        }
        if (idIsLast)
            try {
                preparedStatement.setObject(parameterIndex, idValue);
            } catch (SQLException e) {
                System.out.println("Error while setting values to prepared statement");
            }

    }

    private String generateInsertSql(Object entity) {
        Class<?> entityClass = entity.getClass();
        String tableName = entityClass.getAnnotation(OrmEntity.class).table();
        if (tableName.isEmpty()) {
            tableName = entityClass.getSimpleName();
        }

        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        sqlBuilder.append("INSERT INTO ").append(tableName).append(" (");

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
            OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
            if (Modifier.isStatic(field.getModifiers()) ||
                    (ormColumnId == null && ormColumn == null)) {
                continue;
            }
            if (ormColumnId != null) {
                if (ormColumnId.name().isEmpty())
                    sqlBuilder.append(field.getName().toLowerCase()).append(",");
                else
                    sqlBuilder.append(ormColumnId.name()).append(",");
            } else {
                if (ormColumn.name().isEmpty())
                    sqlBuilder.append(field.getName().toLowerCase()).append(",");
                else
                    sqlBuilder.append(ormColumn.name()).append(",");
            }
            valuesBuilder.append("?,");
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        valuesBuilder.deleteCharAt(valuesBuilder.length() - 1);

        sqlBuilder.append(") VALUES (").append(valuesBuilder).append(")");
        return sqlBuilder.toString();
    }

    private String generateUpdateSql(Object entity) {
        Class<?> entityClass = entity.getClass();
        String tableName = entityClass.getAnnotation(OrmEntity.class).table();
        if (tableName.isEmpty()) {
            tableName = entityClass.getSimpleName();
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");

        String idColumnName = getIdColumnName(entityClass);

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || field.getName().equals(idColumnName)) {
                continue;
            }

            sqlBuilder.append(getColumnName(field)).append(" = ?,");
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);

        sqlBuilder.append(" WHERE ").append(idColumnName).append(" = ?");
        return sqlBuilder.toString();
    }

    private String getIdColumnName(Class<?> clazz) {
        String idColumnName = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
            if (ormColumnId != null) {
                idColumnName = ormColumnId.name();
                if (idColumnName.isEmpty())
                    idColumnName = field.getName().toLowerCase();
                break;
            }
        }
        return idColumnName;
    }

    private String getColumnName(Field field) {
        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
        if (ormColumn.name().isEmpty())
            return field.getName().toLowerCase();
        else
            return ormColumn.name();
    }

    private <T> T mapResultSetToEntity(ResultSet resultSet, Class<T> clazz) {
        T entity = null;

        try {
            entity = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                if (Modifier.isStatic(field.getModifiers()) || (ormColumnId == null && ormColumn == null)) {
                    continue;
                }

                field.setAccessible(true);
                String fieldName;
                if (ormColumnId != null) {
                    fieldName = getIdColumnName(clazz);
                } else
                    fieldName = getColumnName(field);
                Object value = resultSet.getObject(fieldName);
                field.set(entity, value);
            }
        } catch (Exception e) {
            System.out.println("Error while mapping result set to entity," +
                    " please, check that all fields are annotated properly and class have" +
                    " a default constructor");
        }
        return entity;
    }

    public static String extractCleanQuery(String wrappedStatement) {
        int startIndex = wrappedStatement.indexOf("wrapping ") + "wrapping ".length();
        int endIndex = wrappedStatement.length();
        if (startIndex != -1 && endIndex != -1) {
            return wrappedStatement.substring(startIndex, endIndex);
        }
        return null;
    }

    public void close() throws SQLException {
        dataSource.getConnection().close();
    }
}
