package Service;

import Entities.EID;
import Entities.Friendships;
import Entities.User;
import Exceptions.SocialNetworkException;
import Repository.UserRepository;
import Repository.FriendshipRepository;
import Strategies.InputValidator;
import Strategies.Strategy;
import Utils.SimpleEncoder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Service {
    private UserRepository UserRepo = new UserRepository();
    private FriendshipRepository FriendsRepo = new FriendshipRepository();
    private Strategy validator = null;

    /**
     * Constructor needs validation strategy.
     * @param validator strategy for the service
     * */
    public Service(Strategy validator){
        this.validator = validator;
    }

    /**
     * Validates then adds a user with the given values to the application.
     * @param name the new user's name
     * @param userName the new user's userName
     * @param email the new user's email
     * @param password the new user's password
     *
     * @throws Exception
     * */
    public void addUser(String name, String userName, String email, String password) throws Exception {
        validator.setData(name, userName, email, password);



        User U = new User(email, userName, name, password);
        UserRepo.addElem(U);
        FriendsRepo.addElem(new Friendships(U.getUserId()));
    }
    /**
     * Removes the user with the given userName and email combination
     * @param userName username of the user to be removed
     * @param email email of the user to be removed
     *
     * @throws SocialNetworkException in case of user not existing or data not being valid
     * */
    public void removeUser(String userName, String email) throws SocialNetworkException {
        validator.setData(null, userName, email,null);
        EID key = new EID(email + userName);

        UserRepo.removeElem(key);

        FriendsRepo.removeElem(key);
        FriendsRepo.getStream().forEach(E -> {
            try {
                E.removeFriend(key);
            } catch (SocialNetworkException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * Adds a friendship between two users.
     * @param userNameA username of the first user
     * @param emailA email of the first user
     * @param userNameB username of the second user
     * @param emailB email of the second user
     * @throws Exception
     * */
    public void addFriendship(String userNameA, String emailA, String userNameB, String emailB) throws Exception {
        validator.setData(null, userNameA, emailA, null);
        validator.execute();
        validator.setData(null, userNameB, emailB, null);
        validator.execute();


        EID keyA = new EID(emailA + userNameA);
        EID keyB = new EID(emailB + userNameB);

        FriendsRepo.lookUp(keyA).addFriend(keyB);
        FriendsRepo.lookUp(keyB).addFriend(keyA);
    }
    public void removeFriendship(String userNameA, String emailA, String userNameB, String emailB) throws Exception {
        validator.setData(null, userNameA, emailA, null);
        validator.execute();
        validator.setData(null, userNameB, emailB, null);
        validator.execute();


        EID keyA = new EID(emailA + userNameA);
        EID keyB = new EID(emailB + userNameB);

        FriendsRepo.lookUp(keyA).removeFriend(keyB);
        FriendsRepo.lookUp(keyB).removeFriend(keyA);
    }


    /**
     * Gathers the number of communities in the social network.
     * @return int
     * */
    public int getNrComunitati(){
        int community_count = 0;
        ConcurrentHashMap<EID, Boolean> passed = new ConcurrentHashMap<>();
        LinkedList<EID> buffer = new LinkedList<>();
        UserRepo.getStream().forEach(E -> passed.put(E.getUserId(), false));
        Set<EID> list = passed.keySet();
        for (EID user : list) {

            if(!passed.get(user)){
                community_count++;
                buffer.add(user);
            }

            EID current = null;
            while(!buffer.isEmpty()){
                current = buffer.remove();
                if(!passed.get(current)){
                    passed.put(current, true);
                    FriendsRepo.lookUp(current).getStream().forEach(friend -> buffer.add(friend));
                }
            }
        }

        return community_count;

    }
    /**
     * Private function. Searches for the longest path with unique nodes from  the given node.
     * */
    private void DFS(EID start, List<EID> base ){
        base.add(start);
        List<EID> friends = FriendsRepo.lookUp(start).getStream().toList();
        for (EID friend :
                friends) {
            if(!base.contains(friend)){
                List<EID> aux = base;
                DFS(friend, aux);
                if(aux.size() > base.size())
                    base = aux;
            }
        }

    }

    /**
     * Gathers the list of users in the most active community.
     * @return List
     * */
    public List<User> getMostActiveCommunity(){
        ConcurrentHashMap<EID, Boolean> passed = new ConcurrentHashMap<>();
        UserRepo.getStream().forEach(E -> passed.put(E.getUserId(), false));
        List<EID> answer = new ArrayList<>();
        for (EID friend :
                passed.keySet()) {
            if (!passed.get(friend)) {
                passed.put(friend, true);
                List<EID> aux = new ArrayList<>();
                DFS(friend, aux);
                if (answer.size() < aux.size())
                    answer = aux;
            }
        }
        List<User> Users = new ArrayList<>();

        for (EID user : answer) {
            Users.add(UserRepo.lookUp(user));
        }

        return Users;


    }
}
