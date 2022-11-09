package AuthApp;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        testSystem();
    }

    private static void testSystem() throws IOException {
        Client client = new Client();

        testRegistration(client);
        testLogin(client);
        testUpdate(client);
        testDelete(client);
    }

    private static void testRegistration(Client client) {
        boolean success;

        System.out.println(">>>>>>>>> Registration of new users");
        try {
            success = client.register("lior.mathan@gmail.com", "Lior Mathan", "1234As");
            success = client.register("Nitzan@gmail.com", "Nitzan Lahav", "987Nl11");
            success = client.register("Elchanan@gmail.com", "Elchanan Madmon", "El9622");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void testLogin(Client client) {
        System.out.println(">>>>>>>>> Login of lior.mathan@gmail.com");
        try {
            client.login("lior.mathan@gmail.com", "1234As");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void testUpdate(Client client) {
        boolean success;

        System.out.println(">>>>>>>>> Updating user name: Lior Mathan -> Ellie Mathan");
        try {
            success = client.updateUserName("lior.mathan@gmail.com", "Ellie Mathan");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(">>>>>>>>> Updating user email: \"lior.mathan@gmail.com\" -> \"ellie.mathan@gmail.com\"");
        try {
            success = client.updateUserEmail("lior.mathan@gmail.com", "ellie.mathan@gmail.com");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(">>>>>>>>> Updating user's password: 1234As -> TrustNo1");
        try {
            success = client.updateUserPassword("ellie.mathan@gmail.com", "TrustNo1");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void testDelete(Client client) {
        boolean success;

        System.out.println(">>>>>>>>> Deleting user ellie.mathan@gmail.com");
        try {
            success = client.deleteUser("ellie.mathan@gmail.com");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
