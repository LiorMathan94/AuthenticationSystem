package AuthApp;

import java.io.IOException;

public class UserService {

    private static UserService singleInstance = null;
    private UserRepository userRepository;

    private UserService() throws IOException {
        userRepository = AuthApp.UserRepository.getInstance();
    }

    public static UserService getInstance() throws IOException {
        if (singleInstance == null) {
            singleInstance = new UserService();
        }

        return singleInstance;
    }

    public void createUser(String email, String name, String password)
    {
        User user = User.createUser(email, name, password);
        userRepository.addUser(user);
    }

//
//    public void updatedUserName(int id, String name) {
//        userRepository.updatedUserName(id, name);
//    }
//
//    public void updateUserPassword(int id, String password) {
//        userRepository.updateUserPassword(id, password);
//
//    }
//
//    public void updateUserEmail(int id, String email) {
//        userRepository.updateUserEmail(id, email);
//    }

//    public void deleteUser(int id) {
//        userRepository.deleteUser(id);
//    }

}
