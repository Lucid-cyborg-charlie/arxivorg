package app.arxivorg.commandeLine;


public class Helper implements Command {

    @Override
    public void execute(Object[] args) {
        if(args.length==0){
            printAll();
        } else{
            printCommandHelp((String)args[0]);
        }
    }

    /**
     * Print all commands with information
     */
    private void printAll(){
        System.out.println(
                "-list: list all articles\n" +
                "-download: downloading articles with params\n"+
                "-exit: exiting program(take no params)");
    }

    /**
     * Print information for a command
     * @param commandName
     */
    private void printCommandHelp(String commandName){
        if(commandName.equals("list")){
            System.out.println(
                    " -p: print articles by period\n"+
                    " -c: print articles by category\n"+
                    " -a: print articles by author\n"+
                    " -w: print articles by key word\n"+
                    "for example: << list -p >>");
        }else if(commandName.equals("download")){
            System.out.println(
                    " -p: download articles by period\n"+
                    " -c: download articles by category\n"+
                    " -a: download articles by author\n"+
                    " -w: download articles by key word\n"+
                    "for example: << download -p >>");
        }else if(commandName.equals("exit")){
            System.out.println("-exiting program and don't take parameters");
        }else {
            System.out.println("invalid command, enter command <help> to show  all commands.\n"+
                    "for example: << help list >>");
        }
    }
}
