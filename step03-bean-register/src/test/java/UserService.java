public class UserService {
    private String name = "abc";
    private String id = "abc001";

    public UserService() {
    }

    public UserService(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public void queryUserInfo() {
        System.out.println("查询用户信息" + name + " " + id);
    }
}
