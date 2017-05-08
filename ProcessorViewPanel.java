package project;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProcessorViewPanel implements Observer {
	private MachineModel model;
	private JTextField acc = new JTextField(); 
	private JTextField prg = new JTextField();
	private JTextField mem = new JTextField();

	public ProcessorViewPanel(GUIMediator gui, MachineModel mdl) {
		model = mdl;
		gui.addObserver(this);
	}

	public JComponent createProcessorDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(new JLabel("Accumulator: ", JLabel.LEFT));
		panel.add(acc);
		panel.add(new JLabel("Program Counter: ", JLabel.CENTER));
		panel.add(prg);
		panel.add(new JLabel("Memory Base: ", JLabel.RIGHT));
		panel.add(mem);
		return panel;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(model != null) {
			acc.setText("" + model.getAccum());
			prg.setText(""+model.getpCounter());
			mem.setText(""+model.getMemBase());
		}
	}
	
	public static void main(String[] args) {
		GUIMediator view = new GUIMediator(); 
		MachineModel model = new MachineModel();
		ProcessorViewPanel panel = 
			new ProcessorViewPanel(view, model);
		JFrame frame = new JFrame("TEST");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 60);
		frame.setLocationRelativeTo(null);
		frame.add(panel.createProcessorDisplay());
		frame.setVisible(true);
	}
}
