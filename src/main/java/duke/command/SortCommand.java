package duke.command;

import duke.enums.SortMetric;
import duke.enums.SortOrder;
import duke.exception.DukeException;
import duke.exception.InvalidCommandFormatException;
import duke.gui.Ui;
import duke.task.TaskList;
import duke.util.Storage;

/**
 * Represents the command that is executed when the user inputs sort.
 */
public class SortCommand extends Command {
    /** Order of the tasks to sort a TaskList by. */
    private SortOrder sortOrder;
    /** Measurement used to determine the order of the tasks in a TaskList. */
    private SortMetric sortMetric;

    /**
     * Returns a SortCommand with default sortOrder and sortMetric, which is ascending order and deadline metric
     * respectively.
     */
    public SortCommand() {
        this.sortOrder = SortOrder.ASCENDING;
        this.sortMetric = SortMetric.DEADLINE;
    }

    /**
     * Returns a SortCommand with default sortOrder, which is ascending order, and a given sortMetric.
     *
     * @param sortMetric Measurement used to determine the order of the tasks in a TaskList.
     */
    public SortCommand(SortMetric sortMetric) {
        this.sortOrder = SortOrder.ASCENDING;
        this.sortMetric = sortMetric;
    }

    /**
     * Returns a SortCommand with default sortMetric, which is the task deadline, and a given sortOrder.
     *
     * @param sortOrder Order of the tasks to sort a TaskList by.
     */
    public SortCommand(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        this.sortMetric = SortMetric.DEADLINE;
    }

    /**
     * Returns a SortCommand with a given sortOrder and sortMetric.
     *
     * @param sortOrder Order of the tasks to sort a TaskList by.
     * @param sortMetric Measurement used to determine the order of the tasks in a TaskList.
     */
    public SortCommand(SortOrder sortOrder, SortMetric sortMetric) {
        this.sortOrder = sortOrder;
        this.sortMetric = sortMetric;
    }

    /**
     * Returns a SortCommand initialised with the correct sortOrder and sortMetric by parsing the command argument.
     *
     * @param commandArg The command argument to parse.
     * @return SortCommand with the correct sortOrder and sortMetric.
     * @throws InvalidCommandFormatException If the command argument cannot be parsed due to an incorrect sort metric
     *              and/or sort order.
     */
    public static SortCommand parse(String commandArg) throws InvalidCommandFormatException {
        if (commandArg.length() == 0) {
            return new SortCommand();
        }
        String[] commandArgSplit = commandArg.split("\\s+");

        if (commandArgSplit.length == 2) {
            return new SortCommand(SortOrder.parse(commandArgSplit[0]), SortMetric.parse(commandArgSplit[1]));
        } else if (commandArgSplit.length == 1) {
            if (SortOrder.isValidOrder(commandArgSplit[0])) {
                return new SortCommand(SortOrder.parse(commandArgSplit[0]));
            } else if (SortMetric.isValidMetric(commandArgSplit[0])) {
                return new SortCommand(SortMetric.parse(commandArgSplit[0]));
            }
        }
        throw new InvalidCommandFormatException(getFormat());
    }

    /**
     * Executes the mark command. Sorts the TaskList according to the sortMetric and sortOrder.
     *
     * @param tasks The TaskList to be sorted.
     * @param ui Ui object which interacts with the user.
     * @param storage Storage object which loads and saves tasks.
     * @return Message to be shown to the user after successful execution.
     * @throws DukeException If storage object is unable to save the list of tasks, or if TaskList cannot be properly
     *              sorted.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.setSortMetric(sortMetric);
        tasks.setSortOrder(sortOrder);
        tasks.sort();
        storage.save(tasks);
        return ui.getPrettyTaskList(tasks);
    }

    /**
     * Returns the format of the sort command.
     *
     * @return The format of the sort command.
     */
    public static String getFormat() {
        return "sort <ORDER> <TYPE>";
    }

    /**
     * Returns false, because sort is not an application terminating command.
     *
     * @return False.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}