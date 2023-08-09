
public class UserIdsGenerator {
    private static final UserIdsGenerator INSTANCE = new UserIdsGenerator();
    private static int id = 0;

    private UserIdsGenerator() {
        System.out.println("UserIdsGenerator created");
    }

    public static UserIdsGenerator getInstance() {
        return INSTANCE;
    }

    int generateId() {
        return id++;
    }

}
