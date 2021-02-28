package sample.database;

import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private final String login;
    private final String password;
    private String location;
    private String gender;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String firstName, String lastName, String login, String password, String location, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.location = location;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User authEntry = (User) o;
        return Objects.equals(firstName, authEntry.firstName)
                && Objects.equals(lastName, authEntry.lastName) && Objects.equals(login, authEntry.login)
                && Objects.equals(password, authEntry.password) && Objects.equals(location, authEntry.location)
                && Objects.equals(gender, authEntry.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, login, password, location, gender);
    }
}
