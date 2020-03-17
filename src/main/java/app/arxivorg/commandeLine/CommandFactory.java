package app.arxivorg.commandeLine;

/**
 * Class CommandFactory
 */
public abstract class CommandFactory {
    public static Command getCommand(String commandName){
        if(commandName.equalsIgnoreCase("action")){
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
