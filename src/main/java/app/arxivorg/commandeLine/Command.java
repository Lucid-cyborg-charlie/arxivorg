package app.arxivorg.commandeLine;



public interface Command {

    /**
     * execute command
     * @param args
     */
    void execute(Object[] args);
}
