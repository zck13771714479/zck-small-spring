public class UserService {
    private String userId;
    private UserDAO userDAO;

    public String queryUsernameById(String id) {
        String s = userDAO.queryUserById(id);
        System.out.println("查询获取的姓名是： " + s);
        return s;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
