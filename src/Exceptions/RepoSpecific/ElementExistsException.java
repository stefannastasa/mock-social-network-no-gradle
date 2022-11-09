package Exceptions.RepoSpecific;

import Exceptions.SocialNetworkException;

public class ElementExistsException extends SocialNetworkException {

    public ElementExistsException() {
        super("Element already in repository.");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
