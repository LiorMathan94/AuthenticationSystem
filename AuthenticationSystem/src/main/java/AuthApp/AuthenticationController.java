package AuthApp;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationController {
    private static AuthenticationController singleInstance = null;
    private AuthenticationService authenticationService;

    private static Logger logger = LogManager.getLogger(AuthenticationController.class.getName());


    private AuthenticationController() throws IOException {
        logger.debug("in AuthenticationController constructor - int Level:500");
        authenticationService = AuthenticationService.getInstance();
    }

    public static AuthenticationController getInstance() throws IOException {
        if (singleInstance == null) {
            logger.info("Creating new AuthenticationController instance - int Level:400");
            singleInstance = new AuthenticationController();
        }

        return singleInstance;
    }

    public String login(String email, String password) {
        logger.debug("in AuthenticationController.login() - int Level:500");

        if (!isValidEmail(email)) {
            logger.error("In AuthenticationController.login: invalid email - int Level:200");
            throw new IllegalArgumentException("Your email address is invalid!");
        }

        return this.authenticationService.login(email, password);
    }

    public boolean register(String email, String name, String password) {
        logger.debug("in AuthenticationController.register() - int Level:500");

        if (!isValidEmail(email)) {
            logger.error("In AuthenticationController.register: invalid email - int Level:200");
            throw new IllegalArgumentException("Invalid email address!");
        }
        if (!isValidName(name)) {
            logger.error("In AuthenticationController.register: invalid name - int Level:200");
            throw new IllegalArgumentException("Invalid name!");
        }
        if (!isValidPassword(password)) {
            logger.error("In AuthenticationController.register: invalid password - int Level:200");
            throw new IllegalArgumentException("Invalid password!");
        }

        return this.authenticationService.register(email, name, password);
    }

    public boolean isValidPassword(String password) {
        logger.debug("in AuthenticationController.isValidPassword() - int Level:500");

        return password.matches(".*[A-Z].*") && password.length() >= 6;
    }

    public boolean isValidName(String Name) {
        logger.debug("in AuthenticationController.isValidName() - int Level:500");

        return Name.matches("^[ A-Za-z]+$");
    }

    public static boolean isValidEmail(String emailAddress) {
        logger.debug("in AuthenticationController.isValidEmail() - int Level:500");

        String regexPattern = "^(.+)@(\\S+)$";

        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public void updateTokenEmailKey(String oldEmail, String newEmail) {
        logger.debug("in AuthenticationController.updateTokenEmailKey() - int Level:500");
        this.authenticationService.updateTokenEmailKey(oldEmail, newEmail);
    }
}
