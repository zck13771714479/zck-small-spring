public class UserService {
    private String uId;
    private UserDAO userDAO;

    public String queryUsernameById(String id) {
        String s = userDAO.queryUserById(id);
        System.out.println("查询获取的姓名是： " + s);
        return s;
    }

    public String getUserId() {
        return uId;
    }

    public void setUserId(String userId) {
        this.uId = userId;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
