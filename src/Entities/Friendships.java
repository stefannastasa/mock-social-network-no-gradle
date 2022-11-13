package Entities;

import Exceptions.CustomException;
import Exceptions.SocialNetworkException;
import Utils.pair;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class created to manage the friendships of a certain user.
 * */
public class Friendships {

    private EID userId;

    /* If we want to add any properties to the friendship, we should replace LocalDatetime
    * with a custom class called properties */
    private final HashSet< pair<EID, LocalDateTime> >friendships = new HashSet<>();

    /**
     * Constructor.
     * @param U EID of the User whose friendships these are
     * */
    public Friendships(EID U){
        this.userId = U;
    }

    /**
     * Returns the EID of the User whose friendships these are.
     * @returns EID id of the user
     * */
    public EID getUserId() {
        return userId;
    }

    /**
     * Setter for userId
     * @param userId the new id of the user
     * */
    public void setUserId(EID userId) {
        this.userId = userId;
    }

    /**
     * Adds a new friend to the friend list of the user.
     * @param F id of the new friend.
     * @returns none
     * @throws SocialNetworkException in case of the friendship already existing
     * */
    public void addFriend(EID F) throws SocialNetworkException {
        if(!friendships.isEmpty()){
            boolean err = friendships.stream().anyMatch(E -> E.first == F);
            if(err)
                throw new CustomException("Users are already friends!");
        }
        friendships.add(new pair(F, LocalDateTime.now()));

    }

    public void addFriend(EID F, LocalDateTime friendsSince) throws SocialNetworkException{
        if(!friendships.isEmpty()){
            boolean err = friendships.stream().anyMatch(E -> E.first == F);
            if(err)
                throw new CustomException("Users are already friends!");
        }
        friendships.add(new pair(F, friendsSince));
    }
    /**
     * Removes the friend from the friendship list of the user.
     *
     * @param F id of the friend to be removed
     * @returns none
     * @throws SocialNetworkException in case of there not being a friendship between the users beforehand
     *
     * */
    public void removeFriend(EID F) throws SocialNetworkException {
        boolean err = friendships.removeIf(E -> E.first == F);
        if(err)
            throw new CustomException("Users were not friends!");
    }

    /**
     * Checks if given user is a friend of the user.
     * @param F id of the friend
     * @returns True if F is a friend, else false
     * */
    public boolean isFriend(EID F){
        return friendships.contains(F);
    }

    /**
     * Returns a stream for easier data browsing.
     * @returns Stream of all the friends of the user.
     * */
    public Stream<pair<EID, LocalDateTime> > getStream(){
        return friendships.stream();
    }
    /**
     * Checks if the user has any friends =(.
     * @returns  True if the user has friends.
     * */
    public boolean hasFriends(){
        return !friendships.isEmpty();
    }

    /**
     * Gathers the number of friends the user has.
     * @returns int the number of friends the user has
     * */
    public int nrOfFriends(){
        return friendships.size();
    }


    public String getCSVString(){
        String output = Base64.getEncoder().encodeToString(userId.raw) + ",";
        List<pair<EID, LocalDateTime> > elem_list = this.getStream().collect(Collectors.toList());
        for(int i = 0; i < elem_list.size() ; i++){
            pair<EID, LocalDateTime> elem = elem_list.get(i);
            output += Base64.getEncoder().encodeToString(elem.first.raw) + "," + elem.second.toString();
        }

        return output+"\n";
    }

    public static Friendships convertFromString(String[] content){
        EID user = new EID(Base64.getDecoder().decode(content[0]));
        Friendships to_ret = new Friendships(user);

        for(int i=1;i<content.length;i+=2){
            EID c_us = new EID(Base64.getDecoder().decode(content[i]));
            LocalDateTime c_date = LocalDateTime.parse(content[i+1]);
            try {
                to_ret.addFriend(c_us, c_date);
            } catch (SocialNetworkException e) {
                throw new RuntimeException(e);
            }
        }
        return to_ret;
    }
}
