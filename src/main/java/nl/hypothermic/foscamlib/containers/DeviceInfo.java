package nl.hypothermic.foscamlib.containers;

/******************************\
 * > DeviceInfo.java		< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class DeviceInfo {
	
	public final String productName;
	public final String serialNo;
	public final String name;
	public final String macAddr;
	public final String year;
	public final String month;
	public final String day;
	public final String hour;
	public final String minute;
	public final String second;
	public final String timeZone;
	public final String firmwareVer;
	public final String hardwareVer;

	public DeviceInfo(final String productName, final String serialNo, final String name, final String macAddr, final String year,
					  final String month, final String day, final String hour, final String minute, final String second, final String timeZone,
					  final String firmwareVer, final String hardwareVer) {
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
}
