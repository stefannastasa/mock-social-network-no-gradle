package Repository;

import Entities.EID;
import Entities.User;
import Exceptions.RepoSpecific.ElementExistsException;
import Exceptions.RepoSpecific.ElementNotFoundException;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.System.exit;

public class UserRepository implements Repository<EID, User>{
    private final HashMap<EID, User> elemList = new HashMap<>();
    private final String fileName = "users.csv";
    public UserRepository(){
        readFromFile();
    }


    private void readFromFile()  {
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
                String email = content[0];
                String name = content[1];
                String username = content[2];
                byte[] password = Base64.getDecoder().decode(content[3]);
                try {
                    this.addElem(new User(email, username,name, password));
                } catch (ElementExistsException e) {
                    System.out.println("User already in repository");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void writeToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName) );

            this.getStream().forEach(E -> {
                try {
                    writer.write(E.getCSVFormat());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addElem(User O) throws ElementExistsException {
        User err = elemList.put(O.getUserId(), O);
        if(err != null){
            elemList.put(err.getUserId(), err);
            throw new ElementExistsException();
        }
        this.writeToFile();
    }

    @Override
    public void removeElem(EID key) throws ElementNotFoundException {
        User err = elemList.remove(key);
        if(err == null){
            throw new ElementNotFoundException();
        }
        this.writeToFile();
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
