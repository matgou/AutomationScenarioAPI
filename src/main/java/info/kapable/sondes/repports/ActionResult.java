package info.kapable.sondes.repports;

import info.kapable.sondes.scenarios.ScenarioException;

public class ActionResult {
	public static final short RUNNING = 1;
	public static final short NOT_STARTING = 0;
	public static final short END_OK = 2;
	public static final short END_KO = 3;
	
	public Long startTimeMillis;
	public Long endTimeMillis;
	public short status = ActionResult.NOT_STARTING;
	private String description ="";
	private String erreur;
	
	public String getErreur() {
		return erreur;
	}
	public void setErreur(String erreur) {
		this.erreur = erreur;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getOK() {
		if (this.status == ActionResult.END_OK) {
			return true;
		}
		return false;
	}
	public short getStatus() {
		return status;
	}

	public ActionResult() {
		this.startTimeMillis = System.currentTimeMillis();
		this.status = ActionResult.RUNNING;
	}

	public void finishOK() {
		this.endTimeMillis = System.currentTimeMillis();
		this.status = ActionResult.END_OK;
	}
	
	public void finishKO(ScenarioException e) {
		this.endTimeMillis = System.currentTimeMillis();
		this.status = ActionResult.END_KO;
		this.erreur = e.getErreur();
	}

	public Long getDuration() {		
		Long duration = this.endTimeMillis - this.startTimeMillis;
		return duration;
	}

	public String getDurationStr() {
		Long duration = this.getDuration();
		Long minutes = duration / (60 * 1000);
		Long secondes = (duration - minutes * (60 * 1000))/ (1000);
		return minutes + "minutes, " + secondes + " secondes";
	}
}
