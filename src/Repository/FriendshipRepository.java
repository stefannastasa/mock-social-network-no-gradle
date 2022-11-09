package Repository;

import Entities.Friendships;
import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;

import Entities.EID;
import java.util.HashMap;
import java.util.stream.Stream;

public class FriendshipRepository implements Repository<EID, Friendships> {
    private final HashMap<EID, Friendships> elemList = new HashMap<>();
    public FriendshipRepository(){
        sync();
    }
    @Override
    public void sync() {
        return;
    }

    @Override
    public void addElem(Friendships Fs) throws ElementExistsException {
        if(this.elemList.containsKey(Fs.getUserId()))
            throw new ElementExistsException();

        this.elemList.put(Fs.getUserId(), Fs);
    }

    @Override
    public void removeElem(EID key) throws ElementNotFoundException {
        if(!elemList.containsKey(key))
            throw new ElementNotFoundException();

        elemList.remove(key);
    }

    @Override
    public Friendships lookUp(EID key) {
        return elemList.get(key);
    }

    @Override
    public Stream<Friendships> getStream() {
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
