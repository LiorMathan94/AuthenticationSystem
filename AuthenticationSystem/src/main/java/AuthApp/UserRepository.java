package AuthApp;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private static UserRepository singleInstance = null;
    private final String usersFilepath = "UsersDB";
    private Map<Integer, User> usersMap;
    private static Logger logger = LogManager.getLogger(UserRepository.class.getName());


    private UserRepository() throws IOException {
        logger.debug("in UserRepository constructor - int Level:500");

        Files.createDirectories(Paths.get(this.usersFilepath));
        parseConfigToMap();
    }

    public static UserRepository getInstance() throws IOException {
        if (singleInstance == null) {
            singleInstance = new UserRepository();
        }

        return singleInstance;
    }

    public void addUser(User user) {
        logger.debug("in UserRepository.addUser() - int Level:500");

        this.usersMap.put(user.getId(), writeToFile(user));
    }

    public void updatedUser(User user) {
        logger.debug("in UserRepository.updatedUser() - int Level:500");

        this.usersMap.put(user.getId(), writeToFile(user));
    }

    public void deleteUser(User user) {
        logger.debug("in UserRepository.deleteUser() - int Level:500");

        File userFile = new File(getUserFilepath(user));
        userFile.delete();
        this.usersMap.remove(user.getId());
    }

    public Optional<User> getUserByEmail(String email) {
        logger.debug("in UserRepository.getUserByEmail() - int Level:500");

        for (User user : usersMap.values()) {
            if (user.getEmail().compareTo(email) == 0) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private String getUserFilepath(User user) {
        logger.debug("in UserRepository.getUserFilepath() - int Level:500");

        return this.usersFilepath + File.separator + user.getId() + ".json";
    }

    public void parseConfigToMap() {
        logger.debug("in UserRepository.parseConfigToMap() - int Level:500");

        this.usersMap = new HashMap<>();

        final File folder = new File(this.usersFilepath);
        for (final File fileEntry : folder.listFiles()) {
            try (FileInputStream fileInputStream = new FileInputStream(fileEntry.getPath())) {
                Gson gson = new Gson();
                JsonReader jsonReader = new JsonReader(new InputStreamReader(fileInputStream));
                User user = gson.fromJson(jsonReader, User.class);
                this.usersMap.put(user.getId(), user);
            } catch (IOException ex) {
                throw new RuntimeException(String.format("Error occurred while trying to open: %s", fileEntry.getPath()));
            }
        }
    }

    private User writeToFile(User user) {
        logger.debug("in UserRepository.writeToFile() - int Level:500");

        String absoluteFilePath = getUserFilepath(user);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absoluteFilePath)))) {
            Gson gson = new Gson();
            gson.toJson(user, writer);
            return user;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Filename: \"" + absoluteFilePath + "\" was not found.");
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while trying to open new default json file.");
        }
    }
}
