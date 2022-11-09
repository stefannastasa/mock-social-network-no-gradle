package Strategies;

import Exceptions.EmptyInput.EmptyUserNameException;

public interface Strategy {
    void setData(String name, String userName, String email, String password);
    void execute() throws Exception;

    void checkUserName(String userName) throws Exception;

    void checkMail(String email) throws Exception;
    void checkPassword(String password) throws Exception;

    void checkName(String name) throws Exception;
}
