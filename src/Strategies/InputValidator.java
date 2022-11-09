package Strategies;

import Exceptions.EmptyInput.EmptyEmailException;
import Exceptions.EmptyInput.EmptyNameException;
import Exceptions.EmptyInput.EmptyPasswordException;
import Exceptions.EmptyInput.EmptyUserNameException;
import Exceptions.InvalidInput.InvalidEmailException;
import Exceptions.InvalidInput.InvalidNameException;
import Exceptions.InvalidInput.InvalidUsernameException;
import Exceptions.SocialNetworkException;

public class InputValidator implements Strategy{
    private String name, userName, email, password;


    public void setData(String name, String userName, String email, String password)  {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void checkName(String name) throws EmptyNameException, InvalidNameException {
        if(name == null)
            return;
        if(name.isEmpty())
            throw new EmptyNameException();
        if(!name.matches("[^1-9!@#$%^&*()].*"))
            throw new InvalidNameException();



    }
    public void checkUserName(String userName) throws InvalidUsernameException, EmptyUserNameException {
        if(userName == null)
            return;
        if(userName.isEmpty())
            throw new EmptyUserNameException();
        if(!userName.matches("[^1-9!@#$%^&*()].*"))
            throw new InvalidUsernameException();

    }
    public void checkMail(String email) throws InvalidEmailException, EmptyEmailException {
        if(email == null)
            return;
        if(email.isEmpty())
            throw new EmptyEmailException();
        if(!email.matches("[^1-9!@#$%^&*()].*@.*\\..*"))
            throw new InvalidEmailException();


    }
    public void checkPassword(String password) throws EmptyPasswordException {
        if(password == null)
            return;

        if(password.isEmpty())
            throw new EmptyPasswordException();
    }
    @Override
    public void execute() throws SocialNetworkException {
        this.checkMail(this.email);
        this.checkUserName(this.userName);
        this.checkName(this.name);
        this.checkPassword(this.password);
    }
}
