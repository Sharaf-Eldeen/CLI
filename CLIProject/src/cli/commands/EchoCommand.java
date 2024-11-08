package cli.commands;



public class EchoCommand implements cli.Command {

    @Override
    public String execute(String[] args) {
       
        if (args.length < 2) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            output.append(args[i]).append(" ");
        }

        return output.toString().trim();
    }
}
