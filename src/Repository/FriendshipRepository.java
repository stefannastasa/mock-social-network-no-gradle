package Repository;

import Entities.Friendships;
import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;
import Entities.EID;
import Exceptions.SocialNetworkException;
import java.sql.*;
import java.util.HashMap;
import java.util.stream.Stream;

public class FriendshipRepository implements Repository<EID, Friendships> {
    private final HashMap<EID, Friendships> elemList = new HashMap<>();
    private final String url = "jdbc:postgresql://localhost:5432/mock-social-network";

    private void addToDb(EID user1, EID user2){
        String sql = "INSERT INTO FRIENDSHIPS (user1, user2) VALUES (?,?)";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement(sql)) {

            st.setBytes(1, user1.getRaw());
            st.setBytes(2, user2.getRaw());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteFromDb(EID user1, EID user2){
        String sql = "DELETE FROM FRIENDSHIPS WHERE user1=? AND user2=?";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement(sql)) {
            st.setBytes(1,user1.getRaw());
            st.setBytes(2, user2.getRaw());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public FriendshipRepository(){
        // fetches all friendships from the database
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM FRIENDSHIPS");
             ResultSet resultSet = statement.executeQuery()){
            while(resultSet.next()){
                EID user1 = new EID(resultSet.getBytes("user1"));
                EID user2 = new EID(resultSet.getBytes("user2"));

                try{
                    Friendships Fs = new Friendships(user1);
                    this.elemList.put(Fs.getUserId(), Fs);
                    Fs = new Friendships(user2);
                    this.elemList.put(Fs.getUserId(), Fs);

                    this.lookUp(user1).addFriend(user2);
                    this.lookUp(user2).addFriend(user1);

                } catch (ElementExistsException e) {
                    e.printStackTrace();
                }


            }
        } catch (SQLException | SocialNetworkException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addElem(Friendships Fs) throws ElementExistsException {
        if(this.elemList.containsKey(Fs.getUserId()))
            throw new ElementExistsException();

        this.elemList.put(Fs.getUserId(), Fs);
    }

    public void addFriends(EID keyA, EID keyB) throws SocialNetworkException {
        this.lookUp(keyA).addFriend(keyB);
        this.lookUp(keyB).addFriend(keyA);
        addToDb(keyA, keyB);
        addToDb(keyB, keyA);
    }

    public void removeFriends(EID keyA, EID keyB) throws SocialNetworkException {
        this.lookUp(keyA).removeFriend(keyB);
        this.lookUp(keyB).removeFriend(keyA);
        deleteFromDb(keyA,keyB);
        deleteFromDb(keyB, keyA);
    }

    @Override
    public void removeElem(EID key) throws ElementNotFoundException {
        if(!elemList.containsKey(key))
            throw new ElementNotFoundException();

        elemList.remove(key);
    }

    @Override
    public Friendships lookUp(EID key) {
        return elemList.get(key);
    }

    @Override
    public Stream<Friendships> getStream() {
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
