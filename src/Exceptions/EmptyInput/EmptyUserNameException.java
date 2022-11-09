package Exceptions.EmptyInput;

import Exceptions.SocialNetworkException;

public class EmptyUserNameException extends SocialNetworkException {
    public EmptyUserNameException() {
        super("Empty usernames are not allowed!");
    }
}
