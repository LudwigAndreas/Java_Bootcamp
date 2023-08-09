
public class Program {
    public static void main(String[] args) throws UserNotFoundException {
        UsersArrayList ua = new UsersArrayList();
        ua.addUser(new User("name1", 100));
        ua.addUser(new User("name2", 200));
        ua.addUser(new User("name3", 300));
        ua.addUser(new User("name4", 400));
        System.out.println(ua.getUserById(2));
        System.out.println(ua.getUserByIndex(1));
        System.out.println(ua.size());

        for (int i = 0; i < 100; i++) {
            ua.addUser(new User("name" + i, i));
        }
        System.out.println(ua.getUserById(102));
        System.out.println(ua.size());
        System.out.println(ua.getUserByIndex(200));
    }
}
