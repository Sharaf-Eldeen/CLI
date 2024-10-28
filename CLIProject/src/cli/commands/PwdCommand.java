package cli.commands;

import cli.CommandLineInterpreter;
import cli.Command;

public class PwdCommand implements Command {
    private CommandLineInterpreter cli;

    public PwdCommand(CommandLineInterpreter cli) {
        this.cli = cli;
    }

    @Override
    public String execute(String[] args) {
        return cli.getCurrentDirectory();
    }
}

