import Entities.Friendships;
import Entities.EID;
import Entities.User;
import Repository.FriendshipRepository;

import Service.Service;
import Strategies.InputValidator;

import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Service appBackend = new Service(new InputValidator());
    private static Integer PrintMenu(){
        System.out.println("1.Adauga utilizator");
        System.out.println("2.Sterge utilizator");
        System.out.println("3.Adauga prietenie");
        System.out.println("4.Sterge prietenie");
        System.out.println("5.Afiseaza numar comunitati din aplicatie");
        System.out.println("6.Afiseaza cea mai mare comunitate");
        System.out.println("7.Exit");

        System.out.println("Astept selectie...");
        return Integer.parseInt(scanner.nextLine());

    }

    private static void AddUserBehav(){
        System.out.println();
        System.out.println();
        boolean succesfull = false;
        while(!succesfull){
            succesfull = true;
            System.out.println("Introdu nume...");
            String name = scanner.nextLine();

            System.out.println("Introdu userName...");
            String userName = scanner.nextLine();

            System.out.println("Introdu email...");
            String email = scanner.nextLine();

            System.out.println("Introdu parola");
            String password = scanner.nextLine();

            try{
                appBackend.addUser(name, userName, email, password);

            }catch (Exception any){
                System.out.println(any.getMessage());
                succesfull = false;
            }
        }
        System.out.println("User added");
        System.out.println();
        System.out.println();


    }

    private static void RemoveUserBehav(){
        System.out.println();
        System.out.println();
        boolean succesfull = false;
        while(!succesfull){
            succesfull = true;

            System.out.println("Introdu userName...");
            String userName = scanner.nextLine();

            System.out.println("Introdu email...");
            String email = scanner.nextLine();

            try{
                appBackend.removeUser(userName, email);

            }catch (Exception any){
                System.out.println(any.getMessage());
                succesfull = false;
            }
        }
        System.out.println("User removed");
        System.out.println();
        System.out.println();

    }

    public static void addFriendshipBehav(){
        System.out.println();
        System.out.println();
        boolean succesfull = false;
        while(!succesfull){
            succesfull = true;

            System.out.println("Introdu userName of the first user...");
            String userNameA = scanner.nextLine();

            System.out.println("Introdu email of the first user...");
            String emailA = scanner.nextLine();

            System.out.println("Introdu userName of the second user...");
            String userNameB = scanner.nextLine();

            System.out.println("Introdu email of the second user...");
            String emailB = scanner.nextLine();

            try{
                appBackend.addFriendship(userNameA, emailA, userNameB, emailB);

            }catch (Exception any){
                System.out.println(any.getMessage());
                succesfull = false;
            }
        }
        System.out.println("Users are now friends");
        System.out.println();
        System.out.println();
    }

    public static void removeFriendshipBehav(){
        System.out.println();
        System.out.println();
        boolean succesfull = false;
        while(!succesfull){
            succesfull = true;

            System.out.println("Introdu userName of the first user...");
            String userNameA = scanner.nextLine();

            System.out.println("Introdu email of the first user...");
            String emailA = scanner.nextLine();

            System.out.println("Introdu userName of the second user...");
            String userNameB = scanner.nextLine();

            System.out.println("Introdu email of the second user...");
            String emailB = scanner.nextLine();

            try{
                appBackend.removeFriendship(userNameA, emailA, userNameB, emailB);

            }catch (Exception any){
                System.out.println(any.getMessage());
                succesfull = false;
            }
        }
        System.out.println("Users are now friends");
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args){


        while(true){
            int sel = PrintMenu();
            switch (sel){
                case 1:
                    AddUserBehav();
                    break;
                case 2:
                    RemoveUserBehav();
                    break;
                case 3:
                    addFriendshipBehav();
                    break;
                case 4:
                    removeFriendshipBehav();
                    break;
                case 5 :
                    System.out.printf("Nr comunitati: %d\n",appBackend.getNrComunitati());
                    break;

                case 6:
                    System.out.println("Most active community is composed of: ");
                    for (User U :
                            appBackend.getMostActiveCommunity()) {
                        System.out.println(U.getUsername());
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
            }


        }


    }
}