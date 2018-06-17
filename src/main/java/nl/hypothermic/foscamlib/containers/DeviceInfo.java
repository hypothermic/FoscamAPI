package nl.hypothermic.foscamlib.containers;

/******************************\
 * > DeviceInfo.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class DeviceInfo {
	
	public String productName;
	public String serialNo;
	public String name;
	public String macAddr;
	public String year;
	public String month;
	public String day;
	public String hour;
	public String minute;
	public String second;
	public String timeZone;
	public String firmwareVer;
	public String hardwareVer;

	public DeviceInfo(String productName, String serialNo, String name, String macAddr, String year,
					  String month, String day, String hour, String minute, String second, String timeZone,
					  String firmwareVer, String hardwareVer) {
		this.productName = productName;
		this.serialNo = serialNo;
		this.name = name;
		this.macAddr = macAddr;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.timeZone = timeZone;
		this.firmwareVer = firmwareVer;
		this.hardwareVer = hardwareVer;
	}

	@Override public String toString() {
		return "DeviceInfo [productName=" + this.productName + ", serialNo=" + this.serialNo + ", name=" + this.name + ", macAddr=" + this.macAddr + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + ", timeZone=" + this.timeZone + ", firmwareVer=" + this.firmwareVer + ", hardwareVer=" + this.hardwareVer + "]";
	}
}
