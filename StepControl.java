package project;

import javax.swing.Timer;

public class StepControl {
	static final int TICK = 500;
	boolean autoStepOn = false;
	Timer timer;
	GUIMediator gui;
	public StepControl(GUIMediator aGui){
		this.gui=aGui;
	}
	public boolean isAutoStepOn() {
		return autoStepOn;
	}
	public void setAutoStepOn(boolean autoStepOn) {
		this.autoStepOn = autoStepOn;
	}
	public void toggleAutoStep(){
		if (autoStepOn){
			autoStepOn=false;
		}
		autoStepOn=true;
	}
	public void setPeriod(int period){
		timer.setDelay(period);
	}
	public void start() {
		timer = new Timer(TICK, e -> {if(autoStepOn) gui.step();});
		timer.start();
	}
	
}
