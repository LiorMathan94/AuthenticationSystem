package AuthApp;

import java.io.IOException;
import java.util.regex.Pattern;

public class AuthenticationController {
    private static AuthenticationController singleInstance = null;
    private AuthenticationService authenticationService;

    private AuthenticationController() throws IOException {
        authenticationService = AuthenticationService.getInstance();
    }

    public static AuthenticationController getInstance() throws IOException {
        if (singleInstance == null) {
            singleInstance = new AuthenticationController();
        }

        return singleInstance;
    }

    public String login(String email, String password) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Your email address is invalid!");
        }

        return this.authenticationService.login(email, password);
    }

    public void register(String email, String name, String password) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid name!");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password!");
        }

        this.authenticationService.register(email, name, password);
    }

    public boolean isValidPassword(String password) {
        return password.matches(".*[A-Z].*") && password.length() >= 6;
    }

    public boolean isValidName(String Name) {
        return Name.matches("^[ A-Za-z]+$");
    }

    public boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";

        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
