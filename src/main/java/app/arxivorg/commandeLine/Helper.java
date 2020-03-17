package app.arxivorg.commandeLine;

/**
 * Class Helper
 */
public class Helper implements Command {

    @Override
    public void execute(Object[] args) {
        if(args.length==0){
            printAll();
        } else{
            printCommandHelp((String)args[0]);
        }
    }

    private void printAll(){
        System.out.println(
                "action - do some action\n" +
                "exit - exiting program(take no params)");
        //TODO: Print all methods help
    }

    private void printCommandHelp(String commandName){
        //Todo: implement me
        System.out.println("Here should be detailed command info about "+commandName+" if exist");
    }
}
