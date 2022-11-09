package Exceptions.EmptyInput;

import Exceptions.SocialNetworkException;

public class EmptyPasswordException extends SocialNetworkException {
    public EmptyPasswordException() {
        super("Empty passwords are not allowed!");
    }
}
