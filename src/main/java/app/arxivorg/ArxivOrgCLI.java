package app.arxivorg;

import app.arxivorg.commandeLine.*;

import java.io.IOException;
import java.util.Scanner;

public class ArxivOrgCLI {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the arXiv organizer!");
        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        Scanner scanner = new Scanner(System.in);
        while(true){
            //getting user input
            System.out.println("Enter your command:");
            String userInput = scanner.nextLine();

            String[] splitedInput = userInput.split("[ ]+");

            //preparing data for creating and executing new command
            String interpreterName = null;
            String commandName = null;
            String[] commandArgs = null;
            try{
                interpreterName = splitedInput[0];
                commandName = splitedInput[1];
                commandArgs = new String[splitedInput.length-2];
                System.arraycopy(splitedInput,2,commandArgs,0,commandArgs.length);
            }catch (Exception e){

            }

            //executing command
            if(interpreterName.equals("arxivorg")){
                Command command =CommandFactory.getCommand(commandName);
                if(command!=null)
                    command.execute(commandArgs);
                else
                    System.out.println("Invalid command. Try again or use help.");
            }else{
                System.out.println("Invalid command, start your command by arxivorg ");
            }
        }
    }
}
