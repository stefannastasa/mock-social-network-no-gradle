package Exceptions;

public abstract class SocialNetworkException extends Exception{
    public SocialNetworkException(String Message){
        super(Message);
    }

    public String getMessage(){
        return super.getMessage();
    }
}
