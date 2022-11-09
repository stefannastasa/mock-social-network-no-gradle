package Entities;

import Exceptions.CustomException;
import Exceptions.SocialNetworkException;

import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Class created to manage the friendships of a certain user.
 * */
public class Friendships {

    private EID userId;
    private final HashSet<EID> friendships = new HashSet<>();

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
        boolean err = friendships.add(F);
        if(!err)
            throw new CustomException("Users are already friends!");
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
        boolean err = friendships.remove(F);
        if(!err)
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
    public Stream<EID> getStream(){
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
}
