import java.util.ArrayDeque;
import java.util.Queue;

public class RemoteCommandQueue {  // invoker
    Queue<Command> commands;
    Command undoCommand;

    public RemoteCommandQueue() {
        commands = new ArrayDeque<>();
    }

    public void buttonPressed(Command command) {
        commands.add(command);
    }

    public void commandExecute() {
        if (commands.isEmpty()) {
            System.out.println("commandExecute: no command to perform");
        } else {
            Command now_command = commands.poll();
            undoCommand = now_command;
            now_command.execute();
        }
    }

    public void commandUndo() {
        if (undoCommand == null) {
            System.out.println("commandUndo: no command to undo");
        } else {
            undoCommand.undo();
            undoCommand = null;
        }
    }
}
