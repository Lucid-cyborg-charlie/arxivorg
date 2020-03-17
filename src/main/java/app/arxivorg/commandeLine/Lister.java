package app.arxivorg.commandeLine;

/**
 * Class Lister
 */
public class Lister implements Command {

    @Override
    public void execute(Object[] args) {
        //TODO: Implement code to get info based on args
        //Too much code will be here so I just do sth
        System.out.println("You running command action. It's implementation awesome");
        System.out.println("Your parameter list is:");
        if(args.length==0){
            System.out.println("[EMPTY]");
        }else
            for(int i=0; i<args.length;i++){
                System.out.format("[%d]\t"+args[i]+"\n",i);
            }
    }
}
