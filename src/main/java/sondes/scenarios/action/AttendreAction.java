package sondes.scenarios.action;

import org.openqa.selenium.WebDriver;

public class AttendreAction extends Action {
	protected int time;
	
	public AttendreAction(String time) {
		if(time == null) {
			this.time = 5000;
		} else {
			this.time = Integer.parseInt(time);	
		}
		
	}
	@Override
	public void executeAction(WebDriver driver) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
