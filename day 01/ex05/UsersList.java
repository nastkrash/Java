public interface UsersList {
    public void addUser(User user);
    public User getUserByID(Integer id);
    public User getUserByIndex(Integer index);
    public int getNumOfUsers();
}