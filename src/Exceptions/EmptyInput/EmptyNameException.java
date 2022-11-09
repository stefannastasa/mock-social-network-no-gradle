package Exceptions.EmptyInput;

import Exceptions.SocialNetworkException;

public class EmptyNameException extends SocialNetworkException {

    public EmptyNameException() {
        super("Empty names are not allowed!.");
    }
}
