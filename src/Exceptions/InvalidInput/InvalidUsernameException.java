package Exceptions.InvalidInput;

import Exceptions.SocialNetworkException;

public class InvalidUsernameException extends SocialNetworkException {
    public InvalidUsernameException() {
        super("Invalid username.");
    }
}
