package AuthApp;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static final AtomicInteger count = new AtomicInteger(0);

    private final int id;
    private String email;
    private String name;
    private String password;

    private User(String email, String name, String password) {
        this.id = count.incrementAndGet();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public int getId() {return id;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public static User createUser(String email, String name, String password) {
        return new User(email, name, password);}
}
