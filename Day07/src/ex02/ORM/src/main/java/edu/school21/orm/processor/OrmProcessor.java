package edu.school21.orm.processor;

import com.google.auto.service.AutoService;
import edu.school21.orm.annotation.OrmColumn;
import edu.school21.orm.annotation.OrmColumnId;
import edu.school21.orm.annotation.OrmEntity;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("edu.school21.orm.annotation.OrmEntity")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Process.class)
public class OrmProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(OrmEntity.class);
        try {
            FileWriter fileWriter = new FileWriter("./target/schema.sql", true);
            for (Element e : elements) {
                createTable(e, fileWriter);
            }
            fileWriter.flush();
            return true;
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Can't create schema.sql file");
        }
        return false;
    }

    private void createTable(Element element, FileWriter fileWriter) throws IOException {
        OrmEntity ormEntity = element.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();
        if (tableName.isEmpty()) {
            tableName = element.getSimpleName().toString().toLowerCase();
        }
        String dropTableQuery = "DROP TABLE IF EXISTS " +
                tableName +
                " CASCADE" +
                ";";

        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableName)
                .append(" (");

        Field[] fields = new Field[0];
        try {
            fields = Class.forName(element.toString()).getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Element> elements = new ArrayList<>();
        for (Element subElement : element.getEnclosedElements()) {
            if (subElement.getKind() == ElementKind.FIELD) {
                elements.add(subElement);
            }
        }
        for (int i = 0; i < elements.size(); i++) {
            Field field = fields[i];
            createTableQuery.append(getColumnQuery(field, elements.get(i)));
        }

        createTableQuery.setLength(createTableQuery.length() - 1);
        createTableQuery.append(")");
        fileWriter.append(dropTableQuery).append("\n").append(createTableQuery.toString()).append(";\n");

    }

    private String getColumnQuery(Field field, Element fieldElement) {
        OrmColumn ormColumn = fieldElement.getAnnotation(OrmColumn.class);
        OrmColumnId ormColumnId = fieldElement.getAnnotation(OrmColumnId.class);
        if (ormColumnId != null || ormColumn != null) {
            StringBuilder createTableQuery = new StringBuilder();
            String columnName = "";
            if (ormColumn != null)
                columnName = ormColumn.name();
            if (columnName.isEmpty()) {
                columnName = field.getName();
            }
            String columnType = getColumnType(field.getType());
            if (ormColumn != null && ormColumn.length() > 0) {
                createTableQuery.append(columnName)
                        .append(" ")
                        .append(columnType)
                        .append("(")
                        .append(ormColumn.length())
                        .append(")");
            } else {
                createTableQuery.append(columnName)
                        .append(" ")
                        .append(columnType);
            }
            if (ormColumnId != null) {
                createTableQuery.append(" PRIMARY KEY");
            }

            createTableQuery.append(",");
            return createTableQuery.toString();
        }
        return "";
    }

    private String getColumnType(Class<?> fieldType) {
        if (fieldType == String.class) {
            return "VARCHAR";
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return "INT";
        } else if (fieldType == Double.class || fieldType == double.class) {
            return "DOUBLE PRECISION";
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return "BOOLEAN";
        } else if (fieldType == Long.class || fieldType == long.class) {
            return "BIGINT";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }
}
