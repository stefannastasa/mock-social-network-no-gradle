package Repository;

import Entities.EID;
import Entities.User;
import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;

import java.sql.*;
import java.util.HashMap;
import java.util.stream.Stream;

public class UserRepository implements Repository<EID, User>{
    private final HashMap<EID, User> elemList = new HashMap<>();


    private final String url = "jdbc:postgresql://localhost:5432/mock-social-network";

    private void addToDb(User user){
        String sql = "INSERT INTO USERS (uid, email, username, name, password) VALUES (?,?,?,?,?)";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement(sql)) {

            st.setBytes(1, user.getUserId().getRaw());
            st.setString(2,user.getEmail());
            st.setString(3, user.getUsername());
            st.setString(4, user.getName());
            st.setBytes(5, user.getPasswordHash());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteFromDb(User user){
        String sql = "DELETE FROM USERS WHERE uid=?";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement(sql)) {
            st.setBytes(1,user.getUserId().getRaw());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public UserRepository(){
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS");
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                byte[] password = resultSet.getBytes("password");

                User to_add = new User(email, username, name, password);
                elemList.put(to_add.getUserId(), to_add);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addElem(User O) throws ElementExistsException {
        User err = elemList.put(O.getUserId(), O);
        if(err != null){
            elemList.put(err.getUserId(), err);
            throw new ElementExistsException();
        }
        addToDb(O);
    }

    @Override
    public void removeElem(EID key) throws ElementNotFoundException {
        User err = elemList.remove(key);
        if(err == null) {
            throw new ElementNotFoundException();
        }
        deleteFromDb(err);
    }

    @Override
    public User lookUp(EID key) {
        return elemList.get(key);
    }

    @Override
    public Stream<User> getStream() {
        return elemList.values().stream();
    }

    @Override
    public boolean isEmpty() {
        return elemList.isEmpty();
    }

    @Override
    public int size() {
        return elemList.size();
    }
}
