package p03_05_BarracksWars.core.commands;

public class FightCommand extends Command {

	public FightCommand(String[] data) {
		super(data);
	}

	@Override
	public String execute() {
		return "fight";
	}

}