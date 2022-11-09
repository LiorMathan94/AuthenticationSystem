package AuthApp;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class Client {
    private final AuthenticationController authenticationController;
    private final UserController userController;
    private String token;

    public Client() throws IOException {
        this.authenticationController = AuthenticationController.getInstance();
        this.userController = UserController.getInstance();
    }

    public boolean login(String email, String password) {
        this.token = authenticationController.login(email, password);

        boolean success = token != null;
        return success;
    }

    public boolean register(String email, String name, String password) {
        return authenticationController.register(email, name, password);
    }

    public void updateUserName(String email, String name) throws IOException {
        if (this.token == null) {
            throw new AccessDeniedException(String.format("User with email address: %s is not logged in!", email));
        }

        this.userController.updateUserName(email, name, this.token);
    }

    public void updateUserEmail(String email, String newEmail) throws IOException {
        if (this.token == null) {
            throw new AccessDeniedException(String.format("User with email address: %s is not logged in!", email));
        }

        this.userController.updateUserEmail(email, newEmail, this.token);
    }

    public void updateUserPassword(String email, String password) throws IOException {
        if (this.token == null) {
            throw new AccessDeniedException(String.format("User with email address: %s is not logged in!", email));
        }

        userController.updateUserPassword(email, password, token);
    }

    public void deleteUser(String email) throws IOException {
        if (this.token == null) {
            throw new AccessDeniedException(String.format("User with email address: %s is not logged in!", email));
        }

        userController.deleteUser(email, this.token);
    }
}
