package undoCommand;

public class DecreaseCommand implements Command {
	private Counter counter;

	public DecreaseCommand(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void execute() {
		counter.decrement();
	}

	@Override
	public void undo() {
		counter.increment();
	}
}


