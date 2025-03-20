
package undoCommand;

import javax.swing.JLabel;

public class Counter {
	private int value = 0;
	private JLabel label;

	public Counter(JLabel label) {
		this.label = label;
		updateLabel();
	}

	public void increment() {
		value++;
		updateLabel();
	}

	public void decrement() {
		value--;
		updateLabel();
	}

	private void updateLabel() {
		label.setText("Value: " + value);
	}
}


