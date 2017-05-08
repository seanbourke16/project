package project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBarBuilder implements Observer {

	private JMenuItem assemble = new JMenuItem("Assemble Source...");
	private JMenuItem load = new JMenuItem("Load Program...");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem go = new JMenuItem("Go");
	private JMenuItem job0 = new JMenuItem("Job 0");
	private JMenuItem job1 = new JMenuItem("Job 1");
	private JMenuItem job2 = new JMenuItem("Job 2");
	private JMenuItem job3 = new JMenuItem("Job 3");
	private GUIMediator gui;

	public MenuBarBuilder(GUIMediator gm) {
		gui = gm;
		gui.addObserver(this);
	}
	
	public JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.add(assemble);
		assemble.setMnemonic(KeyEvent.VK_M);
		assemble.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		assemble.addActionListener(e -> gui.assembleFile());
		menu.add(assemble);
		//menu.add();
		
		menu.add(load);
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.addActionListener(e -> gui.assembleFile());
		menu.add(load);
		//menu.add();
		
		menu.add(exit);
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exit.addActionListener(e -> gui.assembleFile());
		menu.add(exit);
		//menu.add();
		
		menu.addSeparator();
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		assemble.setEnabled(gui.getCurrentState().getAssembleFileActive());

	}

}
