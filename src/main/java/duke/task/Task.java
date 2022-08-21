package duke.task;

import duke.Parser;

import java.time.LocalDateTime;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    public static Task parse(String fileFormatString) {
        String[] taskSplit = fileFormatString.split("\\|");
        String taskSymbol = taskSplit[0];
        boolean isComplete = taskSplit[1].equals("1");
        String taskDescription = taskSplit[2];
        Task task;
        
        if (taskSymbol.equals("T")) {
            task = new Todo(taskDescription);
        } else if (taskSymbol.equals("D")) {
            LocalDateTime byDateTime = Parser.parseDateTime(taskSplit[3]);
            task = new Deadline(taskDescription, byDateTime);
        } else {
            LocalDateTime atDateTime = Parser.parseDateTime(taskSplit[3]);
            task = new Event(taskDescription, atDateTime);
        }
        
        if (isComplete) {
            task.markAsDone();
        }
        return task;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
    
    public String toFileFormatString() {
        int i = this.isDone ? 1 : 0;
        return "|" + i + "|";
    }
}