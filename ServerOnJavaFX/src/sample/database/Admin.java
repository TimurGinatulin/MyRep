package sample.database;

public class Admin {
    private final String login;
    private String firstName;
    private String lastName;
    private String phone;
    private final String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public Admin(String login, String firstName, String lastName, String phone, String password) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
    }


}
