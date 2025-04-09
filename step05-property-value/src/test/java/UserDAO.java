import java.util.HashMap;

public class UserDAO {
    private static final HashMap<String,String> map = new HashMap<>();
    static {
        map.put("user001","tom");
        map.put("user002","jerry");
        map.put("user003","dick");
    }

    public String queryUserById(String id) {
        return map.get(id);
    }
}
