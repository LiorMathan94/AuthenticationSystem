package AuthApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

class UserService {
    private static UserService singleInstance = null;
    private UserRepository userRepository;
    private static Logger logger = LogManager.getLogger(UserController.class.getName());

    private UserService() throws IOException {
        logger.debug("in UserService constructor - int Level:500");

        userRepository = AuthApp.UserRepository.getInstance();
    }

    public static UserService getInstance() throws IOException {
        if (singleInstance == null) {
            logger.info("Creating new UserService instance - int Level:400");
            singleInstance = new UserService();
        }

        return singleInstance;
    }

    public void updateUserName(String email, String name, String token) throws IOException {
        logger.debug("in UserService.updateUserName() - int Level:500");

        validateToken(email, token);
        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isPresent()) {
            user.get().setName(name);
            userRepository.updatedUser(user.get());
        } else {
            logger.error("In UserService.updateUserName: Email does not exist - int Level:200");
            throw new IllegalArgumentException(String.format("Email address: %s does not exist", email));
        }
    }

    public void updateUserEmail(String email, String newEmail, String token) throws IOException {
        logger.debug("in UserService.updateUserEmail() - int Level:500");

        validateToken(email, token);

        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            user.get().setEmail(newEmail);
            userRepository.updatedUser(user.get());
        } else {
            logger.error("In UserService.updateUserEmail: Email does not exist - int Level:200");
            throw new IllegalArgumentException(String.format("Email address %s does not match any user", email));
        }
    }

    public void updateUserPassword(String email, String password, String token) throws IOException {
        logger.debug("in UserService.updateUserPassword() - int Level:500");

        validateToken(email, token);

        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            user.get().setPassword(password);
            userRepository.updatedUser(user.get());
        } else {
            logger.error("In UserService.updateUserPassword: Email does not exist - int Level:200");
            throw new IllegalArgumentException(String.format("Email address %s does not match any user", email));
        }
    }

    public void deleteUser(String email, String token) throws IOException {
        logger.debug("in UserService.deleteUser() - int Level:500");

        validateToken(email, token);

        Optional<User> user = userRepository.getUserByEmail(email);
        if (user.isPresent()) {
            userRepository.deleteUser(user.get());
        } else {
            logger.error("In UserService.deleteUser: Email does not exist - int Level:200");
            throw new IllegalArgumentException(String.format("Email address %s does not match any user", email));
        }
    }

    private void validateToken(String email, String token) throws IOException {
        logger.debug("in UserService.validateToken() - int Level:500");

        AuthenticationService authenticationService = AuthenticationService.getInstance();

        if (!authenticationService.isValidToken(email, token)) {
            logger.fatal("In UserService.validateToken: User is not logged in - int Level:200");
            throw new AccessDeniedException(String.format("User with email address: %s is not logged in!", email));
        }
    }
}
