
package undoCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Main {
	private Stack<Command> history = new Stack<>();

	public Main() {
		JFrame frame = new JFrame("Undo with Command Pattern");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);

		JLabel label = new JLabel("Value: 0", SwingConstants.CENTER);
		JButton increaseButton = new JButton("Increase");
		JButton decreaseButton = new JButton("Decrease");
		JButton undoButton = new JButton("Undo");

		Counter counter = new Counter(label);

		increaseButton.addActionListener(e -> executeCommand(new IncreaseCommand(counter)));
		decreaseButton.addActionListener(e -> executeCommand(new DecreaseCommand(counter)));
		undoButton.addActionListener(e -> undoLastCommand());

		JPanel panel = new JPanel();
		panel.add(increaseButton);
		panel.add(decreaseButton);
		panel.add(undoButton);

		frame.add(label, "North");
		frame.add(panel, "Center");
		frame.setVisible(true);
	}

	private void executeCommand(Command command) {
		command.execute();
		history.push(command);
	}

	private void undoLastCommand() {
		if (!history.isEmpty()) {
			history.pop().undo();
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}



