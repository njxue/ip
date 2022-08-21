package duke.command;

import duke.Storage;
import duke.Ui;
import duke.exception.DukeException;

import duke.task.Task;
import duke.task.TaskList;

public class DeleteCommand extends Command {
    private int taskIndex;

    public static String getFormat() {
        return "delete <Integer>";
    }
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task task = tasks.delete(this.taskIndex);
        ui.showDeleteTask(task, tasks);
        storage.save(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
