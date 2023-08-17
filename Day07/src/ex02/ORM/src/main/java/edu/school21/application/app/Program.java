package edu.school21.application.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.application.model.Car;
import edu.school21.application.model.User;
import edu.school21.orm.manager.OrmManager;

import javax.sql.DataSource;

public class Program {
    public static DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/test");
        dataSource.setUsername("postgres");
        dataSource.setPassword("");
        return dataSource;
    }

    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager(getDataSource());
        System.out.println("--- ORM Manager created ---");

        User user = new User(12, "Vasya", "Pupkin", 18);
        ormManager.save(user);
        user.setAge(20);
        ormManager.update(user);
        User returnedUser = ormManager.findById(12L, User.class);
        if (returnedUser.equals(user))
            System.out.println("--- OK ---");
        else
            System.out.println("--- FAIL ---");

        Car car = new Car(1L, "BMW", "X5", 2021, 123.33);
        ormManager.save(car);
        car.setYear(2020);
        ormManager.update(car);
        Car returnedCar = ormManager.findById(1L, Car.class);
        if (returnedCar.equals(car))
            System.out.println("--- OK ---");
        else
            System.out.println("--- FAIL ---");
    }
}
