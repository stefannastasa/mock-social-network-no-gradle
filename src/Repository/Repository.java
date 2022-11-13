package Repository;

import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;

import java.util.stream.Stream;

/**
 * Master interface for any repository. It uses a HashMap for maximum lookup and adding efficiency.
 * */
public interface Repository<key_type,obj_type> {

    /**
     * Adds an element to the repo
     * @param O new object to be added
     * @throws ElementExistsException if the given key already has an object assigned
     * */
    void addElem(obj_type O) throws ElementExistsException;

    /**
     * Removes the element linked to the given key.
     * @param key the key of the object
     * @throws ElementNotFoundException if the given key doesn't have any element linked to itself
     * */
    void removeElem(key_type key) throws ElementNotFoundException;

    /**
     * Performs a lookup for the object linked to the given key.
     * @param key the key of the object
     * @return obj_type
     * */
    obj_type lookUp(key_type key);

    /**
     * Gets a Stream for the underlying objects.
     *
     * @return Stream
     * */
    Stream<obj_type> getStream();

    /**
     * Checks if repository is empty.
     * @return boolean True if the repo is empty
     * */
    boolean isEmpty();


    /**
     * Gets the number of elements in the repo.
     * @return int
     * */
    int size();
}
