package app.arxivorg.commandeLine;

import java.io.IOException;

/**
 * Class CommandFactory
 */
public abstract class CommandFactory {

    public static Command getCommand(String commandName) throws IOException {
        if(commandName.equalsIgnoreCase("list")){
            return new Lister();
        }else if(commandName.equalsIgnoreCase("exit")){
            return new Exiter();
        }else if(commandName.equalsIgnoreCase("help")){
            return new Helper();
        }
        else{
            return null;
        }
    }

}
