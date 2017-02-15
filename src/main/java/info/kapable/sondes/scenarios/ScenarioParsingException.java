package info.kapable.sondes.scenarios;

public class ScenarioParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScenarioParsingException(Throwable cause) {
		this.initCause(cause);
	}

	public ScenarioParsingException(String string) {
		super(string);
	}

}
