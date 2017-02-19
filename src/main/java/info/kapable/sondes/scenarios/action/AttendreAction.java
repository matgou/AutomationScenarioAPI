package info.kapable.sondes.scenarios.action;

import org.openqa.selenium.WebDriver;

public class AttendreAction extends Action {
	protected int time;

	public AttendreAction(String time) {
		if (time == null) {
			this.time = 5000;
		} else {
			this.time = Integer.parseInt(time);
		}

	}

	@Override
	public void executeAction(WebDriver driver) {
		try {
			int max = (time / 1000);
			for (int i = 0; i < max; i++) {
				Thread.sleep(1000);
				this.logEvent("Attente", (i + 1) + "/" + max);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "Attendre " + (this.time /1000) + "secondes";
	}

}
