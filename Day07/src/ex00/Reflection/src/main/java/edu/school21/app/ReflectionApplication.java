package edu.school21.app;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.*;
import java.lang.reflect.*;

public class ReflectionApplication {
    List<Class<?>> classes;

    ReflectionApplication(List<Class<?>> classes) {
        this.classes = classes;
    }

    private List<Class<?>> getClassesFromPackage(String packageName) {
        try (ScanResult scanResult = new ClassGraph()
                .verbose()
                .enableAllInfo()
                .acceptPackages(packageName)
                .scan()) {
            return scanResult.getAllClasses().loadClasses();
        }
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("Classes:");
        for (Class<?> clazz : classes) {
            System.out.println("\t- " + clazz.getSimpleName());
        }
        printSeparator();
        System.out.println("Enter class name:");
        printInputSymbol();
        String className = in.nextLine();
        Class<?> clazz = getClassByName(className);
        if (clazz == null) {
            printError("Class not found");
            return;
        }
        printSeparator();
        Map<String, Method> methodMap = printClassInfo(clazz);
        if (methodMap == null) {
            printError("Can not get class info");
            return;
        }
        printSeparator();
        Object instance = createInstance(clazz);
        if (instance == null) {
            printError("Can not create instance");
            return;
        }
        printSeparator();
        System.out.println("Enter name of the field for changing:");
        printInputSymbol();
        String fieldName = in.nextLine();
        Field field = getFieldByName(instance, fieldName);
        if (field == null) {
            printError("Field not found");
            return;
        }
        changeField(field, instance);
        printSeparator();
        System.out.println("Enter name of the method for call:");
        printInputSymbol();
        String methodName = in.nextLine();
        Method method = methodMap.get(methodName);
        if (method == null) {
            printError("Method not found");
            return;
        }
        if (method.getParameterCount() == 0) {
            callMethod(method, instance);
        } else {
            callMethodWithArgs(method, instance);
        }
    }

    private void callMethodWithArgs(Method method, Object instance) {
        Scanner in = new Scanner(System.in);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            System.out.println("Enter " + parameterTypes[i].getSimpleName() + " value:");
            printInputSymbol();
            String stringValue = in.nextLine();
            try {
                args[i] = parseValue(stringValue, parameterTypes[i]);
            } catch (IllegalArgumentException e) {
                printError("Can not parse value");
                return;
            }
        }
        callMethod(method, instance, args);
    }

    private void callMethod(Method method, Object instance, Object... args) {
        try {
            method.setAccessible(true);
            Object result = null;
            if (method.getParameterCount() == args.length)
                result = method.invoke(instance, Arrays.copyOfRange(args, 0, args.length));
            else {
                printError("Wrong number of arguments");
                return;
            }
            if (result != null) {
                System.out.println("Method returned:");
                System.out.println(result);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            printError("Can not call method");
            return;
        }
    }

    private Method getMethodByName(Object instance, String methodName) {
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private void changeField(Field field, Object instance) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter " + field.getType().getSimpleName() + " value:");
        printInputSymbol();
        String stringValue = in.nextLine();
        try {
            setField(field, instance, stringValue);
            System.out.println("Object updated: " + instance);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            printError("Can not set value");
        }
    }

    private Field getFieldByName(Object instance, String fieldName) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    private Object createInstance(Class<?> clazz) {
        Scanner in = new Scanner(System.in);
        System.out.println("Let's create an object.");
        Constructor<?> constructor;
        Object instance;
        try {
            constructor = clazz.getConstructor();
            instance = constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            printError("Can't create instance");
            return null;
        }
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + ":");
            printInputSymbol();
            String value = in.nextLine();
            try {
                setField(field, instance, value);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                printError("Illegal argument");
            }
        }
        System.out.println("Object created: " + instance);
        return instance;
    }

    private Object parseValue(String value, Class<?> type) {
        if (type.equals(String.class)) {
            return value;
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return Long.parseLong(value);
        }
        return null;
    }

    private void setField(Field field, Object instance, String value) throws IllegalAccessException {
        field.setAccessible(true);
        Object parsedValue = parseValue(value, field.getType());
        field.set(instance, parsedValue);
    }

    private Class<?> getClassByName(String className) {
        for (Class<?> clazz : classes) {
            if (clazz.getSimpleName().equals(className))
                return clazz;
        }
        return null;
    }

    private void printSeparator() {
        System.out.println("---------------------");
    }

    private void printInputSymbol() {
        System.out.print("-> ");
    }

    private void printError(String message) {
        System.out.println("Error: " + message);
    }

    private Map<String, Method> printClassInfo(Class<?> clazz) {
        if (clazz == null) {
            System.out.println("Class not found");
            return null;
        }
        System.out.println("fields: ");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("\t\t" + field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("methods: ");
        Map<String, Method> methodNames = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getModifiers() == Modifier.PUBLIC) {
                StringBuilder methodName = new StringBuilder(method.getName() + "(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    methodName.append(parameterTypes[i].getSimpleName());
                    if (i != parameterTypes.length - 1) {
                        methodName.append(", ");
                    }
                }
                methodName.append(")");
                String returnType = method.getReturnType().getSimpleName();
                if (!returnType.equals("void")) {
                    System.out.println("\t\t" + returnType + " " + methodName);
                } else {
                    System.out.println("\t\t" + methodName);
                }
                methodNames.put(methodName.toString(), method);
            }
        }
        return methodNames;
    }
}
