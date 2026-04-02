public class UserIdsGenerator {

    private static UserIdsGenerator instance; //хранит единственный объект
    private Integer lastId = 0;

    private UserIdsGenerator() {}//нельзя создать new

    public static UserIdsGenerator getInstance() {
        if (instance == null)
            instance = new UserIdsGenerator();
        return instance;
    }

    public Integer generateId() {
        lastId++;
        return lastId;
    }
}