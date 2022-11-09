package AuthApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UserController {
    private static UserController singleInstance = null;
    private UserService userService;

    private static Logger logger = LogManager.getLogger(UserController.class.getName());


    private UserController() throws IOException {
        logger.debug("in UserController constructor - int Level:500");
        userService = UserService.getInstance();
    }

    public static UserController getInstance() throws IOException {
        if (singleInstance == null) {
            singleInstance = new UserController();
            logger.info("Creating new UserController instance - int Level:400");
        }

        return singleInstance;
    }

    public void updateUserName(String email, String name, String token) throws IOException {
        logger.debug("in UserController.updateUserName() - int Level:500");

        AuthenticationController authenticationController = AuthenticationController.getInstance();

        if (!authenticationController.isValidName(name)) {
            logger.error("In UserController.updateUserName: invalid name - int Level:200");
            throw new IllegalArgumentException(String.format("%s is invalid name!", name));
        }

        userService.updateUserName(email, name, token);
    }

    public void updateUserEmail(String email, String newEmail, String token) throws IOException {
        logger.debug("in UserController.updateUserEmail() - int Level:500");

        AuthenticationController authenticationController = AuthenticationController.getInstance();

        if (!authenticationController.isValidEmail(newEmail)) {
            logger.error("In UserController.updateUserEmail: invalid email - int Level:200");
            throw new IllegalArgumentException(String.format("%s is invalid email!", newEmail));
        }

        userService.updateUserEmail(email, newEmail, token);
        authenticationController.updateTokenEmailKey(email, newEmail);
    }

    public void updateUserPassword(String email, String password, String token) throws IOException {
        logger.debug("in UserController.updateUserPassword() - int Level:500");

        AuthenticationController authenticationController = AuthenticationController.getInstance();

        if (!authenticationController.isValidPassword(password)) {
            logger.error("In UserController.updateUserPassword: invalid password - int Level:200");

            throw new IllegalArgumentException(String.format("%s is invalid password!", password));
        }

        userService.updateUserPassword(email, password, token);
    }

    public void deleteUser(String email, String token) throws IOException {
        logger.debug("in UserController.deleteUser() - int Level:500");

        userService.deleteUser(email, token);
    }
}
