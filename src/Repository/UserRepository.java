package Repository;

import Entities.EID;
import Entities.User;
import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;

import java.util.HashMap;
import java.util.stream.Stream;

public class UserRepository implements Repository<EID, User>{
    private final HashMap<EID, User> elemList = new HashMap<>();

    public UserRepository(){
        sync();
    }


    @Override
    public void sync() {
        return;
    }

    @Override
    public void addElem(User O) throws ElementExistsException {
        User err = elemList.put(O.getUserId(), O);
        if(err != null){
            elemList.put(err.getUserId(), err);
            throw new ElementExistsException();
        }
    }

    @Override
    public void removeElem(EID key) throws ElementNotFoundException {
        User err = elemList.remove(key);
        if(err == null){
            throw new ElementNotFoundException();
        }
    }

    @Override
    public User lookUp(EID key) {
        return elemList.get(key);
    }

    @Override
    public Stream<User> getStream() {
        return elemList.values().stream();
    }

    @Override
    public boolean isEmpty() {
        return elemList.isEmpty();
    }

    @Override
    public int size() {
        return elemList.size();
    }
}
