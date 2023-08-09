package model.User;

public class UserIdsGenerator {
    private static final UserIdsGenerator INSTANCE = new UserIdsGenerator();
    private static int id = 1;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return INSTANCE;
    }

    int generateId() {
        return id++;
    }

}
