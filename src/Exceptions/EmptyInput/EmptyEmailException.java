package Exceptions.EmptyInput;

import Exceptions.SocialNetworkException;

public class EmptyEmailException extends SocialNetworkException {
    public EmptyEmailException() {
        super("Empty emails are not allowed");
    }
}
