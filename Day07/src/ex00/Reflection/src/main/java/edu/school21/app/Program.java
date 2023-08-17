package edu.school21.app;

import edu.school21.classes.Car;
import edu.school21.classes.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(User.class);
        classes.add(Car.class);
        ReflectionApplication app = new ReflectionApplication(classes);
        app.run();
    }
}
