
public class UsersArrayList implements UsersList {

    private int capacity = 10;
    private int size = 0;

    private User[] users = new User[capacity];

    @Override
    public void addUser(User user) throws NullPointerException {
        if (size == capacity - 1) {
            capacity *= 2;
            User[] newUsers = new User[capacity];
            for (int i = 0; i < size; i++) {
                newUsers[i] = users[i];
            }
            users = newUsers;
        }
        if (user == null) throw new NullPointerException("User is null");
        users[size] = user;
        size++;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) return users[i];
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {
        if (index < 0 || index >= size) throw new UserNotFoundException("User with index " + index + " not found");
        return users[index];
    }

    @Override
    public int size() {
        return size;
    }
}
