package com.gabriel.drawfx.command;

import java.util.Stack;

public class CommandService {
    static Stack<Command> undoStack = new Stack<Command>();
    static Stack<Command> redoStack = new Stack<Command>();

    public static void ExecuteCommand(Command command) {
        // DEBUG: Print what command is being added
        System.out.println("ExecuteCommand: " + command.getClass().getSimpleName() + " - Stack size: " + undoStack.size());

        command.execute();
        undoStack.push(command);
        redoStack.clear();

        // DEBUG: Print stack size after
        System.out.println("After execute - Stack size: " + undoStack.size());
    }

    public static void undo() {
        if (undoStack.empty()) {
            System.out.println("Undo: Stack is empty");
            return;
        }

        Command command = undoStack.pop();
        System.out.println("Undoing: " + command.getClass().getSimpleName() + " - Stack size: " + undoStack.size());

        command.undo();
        redoStack.push(command);
    }

    public static void redo() {
        if (redoStack.empty()) {
            System.out.println("Redo: Stack is empty");
            return;
        }

        Command command = redoStack.pop();
        System.out.println("Redoing: " + command.getClass().getSimpleName());

        command.execute();
        undoStack.push(command);
    }
}