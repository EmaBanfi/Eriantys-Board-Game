package it.polimi.ingsw;

import it.polimi.ingsw.network.client.CLI;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.gui.GUI;
import it.polimi.ingsw.network.server.Server;

import java.util.Scanner;

public class Eriantys {

    public static void main(String[] args) throws Exception {
        System.out.println("\n");
        System.out.println("Welcome to Eriantys!\n");
        System.out.println("Do you want to run the Server or a Client? \n\n0 -> Server\n1 -> Client (CLI)\n2 -> Client (GUI)");
        System.out.println("\nInsert your choice: ");

        Scanner scanner = new Scanner(System.in);

        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 0 -> Server.main(null);
            case 1 -> CLI.main(null);
            case 2 -> GUI.main(null);
            default -> System.err.println("Not a valid choice, the executable will be closed");
        }
    }
}