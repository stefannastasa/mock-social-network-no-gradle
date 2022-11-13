package Repository;

import Entities.Friendships;
import Entities.User;
import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;

import Entities.EID;
import Exceptions.SocialNetworkException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class FriendshipRepository implements Repository<EID, Friendships> {
    private final HashMap<EID, Friendships> elemList = new HashMap<>();
    private final String fileName = "friends.csv";
    private void writeToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName) );

            this.getStream().forEach(E -> {
                try {
                    writer.write(E.getCSVString());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void readFromFile(){
        File obj = new File(fileName);
        try {
            if(!obj.exists()){
                obj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            Scanner scanner = new Scanner(obj);

            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                String[] content = data.split(",");

                this.addElem(Friendships.convertFromString(content));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ElementExistsException e) {
            throw new RuntimeException(e);
        }
    }

    public FriendshipRepository(){
        readFromFile();
    }

    @Override
    public void addElem(Friendships Fs) throws ElementExistsException {
        if(this.elemList.containsKey(Fs.getUserId()))
            throw new ElementExistsException();

        this.elemList.put(Fs.getUserId(), Fs);

        this.writeToFile();
    }

    public void addFriends(EID keyA, EID keyB) throws SocialNetworkException {
        this.lookUp(keyA).addFriend(keyB);
        this.lookUp(keyB).addFriend(keyA);
        this.writeToFile();
    }

    public void removeFriends(EID keyA, EID keyB) throws SocialNetworkException {
        this.lookUp(keyA).removeFriend(keyB);
        this.lookUp(keyB).removeFriend(keyA);
        this.writeToFile();
    }

    @Override
    public void removeElem(EID key) throws ElementNotFoundException {
        if(!elemList.containsKey(key))
            throw new ElementNotFoundException();

        elemList.remove(key);
        this.writeToFile();
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
