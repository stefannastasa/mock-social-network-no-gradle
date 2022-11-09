package Exceptions.InvalidInput;

import Exceptions.SocialNetworkException;

public class InvalidEmailException extends SocialNetworkException {
    public InvalidEmailException() {
        super("Invalid email!");
    }
}
