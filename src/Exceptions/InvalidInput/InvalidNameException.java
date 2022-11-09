package Exceptions.InvalidInput;

import Exceptions.SocialNetworkException;

public class InvalidNameException extends SocialNetworkException {
    public InvalidNameException() {
        super("Invalid name");
    }
}
