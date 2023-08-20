package edu.school21.tanksserver.app;

import edu.school21.tanksserver.server.GameServer;
import edu.school21.tanksserver.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(GameServer.class);
        Server server = context.getBean(GameServer.class);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
