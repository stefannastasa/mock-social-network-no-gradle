package Entities;
import Utils.SimpleEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * Class created for the management of the user entity.
 * */
public class User {
    private String email, username, name;
    private byte[] passwordHash;
    private EID userId = null;

    /**
     * Getter for the user's ID.
     * @return EID
     * */
    public EID getUserId() {
        return userId;
    }

    /**
     * Resets the id of the user based on the user's email and username.
     * */
    public void resetEID() {
        this.userId = new EID(this.email + this.username);
    }

    /**
     * Constructor. It memorises the hash of the password and generates an EID based on the received data.
     * @param email future email of the user
     * @param username future username of the user
     * @param name future name of the user
     * @param password future password of the user
     * */
    public User(String email, String username, String name, String password) throws NoSuchAlgorithmException {
        SimpleEncoder pd = SimpleEncoder.getInstance();
        this.setEmail(email);
        this.setName(name);
        this.setUsername(username);
        this.setPasswordHash(pd.Encode(password));
        this.resetEID();
    }
    public User(String email, String username, String name, byte[]password){
        this.setEmail(email);
        this.setName(name);
        this.setUsername(username);
        this.setPasswordHash(password);
        this.resetEID();
    }
    /**
     * Getter for the user's email.
     * @return String
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the user's email.
     * @param email new value
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the user's username.
     * @return String
     * */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the user's username.
     * @param username new value
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's name.
     * @return String
     * */
    public String getName() {
        return name;
    }


    /**
     * Setter for the user's name.
     * @param name new value
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the password hash of the user.
     * @return byte[]
     * */
    public byte[] getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter for the user's password
     * @param password new value
     * */
    public void setPasswordHash(byte[] password) {
        this.passwordHash = password;
    }

    @Override
    public int hashCode(){
        return this.name.hashCode() * this.username.hashCode() * this.email.hashCode() * Arrays.hashCode(this.passwordHash);
    }

    @Override
    public boolean equals(Object O){
        User U = (User) O;
        return Objects.equals(U.getUsername(), this.username) &&
                Objects.equals(U.getEmail(), this.email) &&
                Objects.equals(U.getName(), this.name) &&
                Arrays.equals(U.getPasswordHash(), this.getPasswordHash());
    }

    @Override
    public String toString(){
        return this.email + this.username;
    }

    /**
     * Replaces the values of this object with the not null values of O
     * @param O auxiliary object for changes
     * */
    public void replaceNotNull(User O){
        if(O.getEmail() != null)
            this.setEmail(O.getEmail());
        if(O.getUsername() != null)
            this.setUsername(O.getUsername());
        if(O.getName() != null)
            this.setName(O.getName());
        if(!Arrays.equals(null, O.getPasswordHash()))
            this.setPasswordHash(O.getPasswordHash());

    }
    public String getCSVFormat(){
        return this.email + "," + this.name + "," + this.username + "," + Base64.getEncoder().encodeToString(this.passwordHash)+"\n";
    }
}
