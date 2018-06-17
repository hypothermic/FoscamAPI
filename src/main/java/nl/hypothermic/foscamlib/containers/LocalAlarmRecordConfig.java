package nl.hypothermic.foscamlib.containers;

/******************************\
 * > LocalAlarmRecordConfig	< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class LocalAlarmRecordConfig {
	
	/** Is local alarm-record enabled (0-1) */
	public String isEnabled;
	/** Record duration in seconds */
	public String recordSecs;
	
	public LocalAlarmRecordConfig(String isEnabled, String recordSecs) {
		this.isEnabled = isEnabled;
		this.recordSecs = recordSecs;
	}

	@Override public String toString() {
		return "LocalAlarmRecordConfig [isEnabled=" + this.isEnabled + ", recordSecs=" + this.recordSecs + "]";
	}
}
