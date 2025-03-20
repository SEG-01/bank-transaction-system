package undoCommand;

public class IncreaseCommand implements Command {
	private Counter counter;

	public IncreaseCommand(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void execute() {
		counter.increment();
	}

	@Override
	public void undo() {
		counter.decrement();
	}
}
