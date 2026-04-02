public class UsersArrayList implements UsersList {
    private User[] users;
    private int count;
    public UsersArrayList(){
        users = new User[10];
        count = 0;
    }
    public void addUser(User user) {
        if (count == users.length) {
            User[] newUsers = new User[users.length + users.length/2];
            for (int i = 0; i < users.length; i++)
                newUsers[i] = users[i];
            users = newUsers;
        }
        users[count] = user;
        count++;

    }
    public User getUserByID(Integer id) {
        for (int i = 0; i < count; i++){
            if (users[i].getId().equals(id))
                return users[i];
        }
        throw new UserNotFoundException("User not found");

    }
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    public User getUserByIndex(Integer index){
        if (index < count && index >= 0)
            return users[index];
        throw new UserNotFoundException("User not found");
    }
    public int getNumOfUsers(){
        return count;
    }
}