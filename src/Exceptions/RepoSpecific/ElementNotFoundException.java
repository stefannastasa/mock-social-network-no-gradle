package Exceptions.RepoSpecific;


import Exceptions.SocialNetworkException;

public class ElementNotFoundException extends SocialNetworkException {
    public ElementNotFoundException() {
        super("Element not found in repository");
    }


    @Override
    public String getMessage(){
        return super.getMessage();
    }

}
