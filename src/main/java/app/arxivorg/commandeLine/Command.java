package app.arxivorg.commandeLine;

import java.text.ParseException;

/**
 * Interface Command
 */
public interface Command {
    /**
     * execute command
     * @param args
     */
    void execute(Object[] args);
}
