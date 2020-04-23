package app.arxivorg.commandeLine;


public class Exiter implements Command {

    @Override
    public void execute(Object[] args) {
        if(args.length!=0){
            System.out.println("exit command don't take parameter");
        }
        System.exit(0);
    }

}
