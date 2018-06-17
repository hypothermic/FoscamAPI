package nl.hypothermic.foscamlib.containers;

/******************************\
 * > Info485.java			< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class Info485 {
	
	// I would've rather named this class '485Info'. rip.
	
	/** rs485 protocol */
	public String protocol;
	/** rs485 address */
	public String address;
	/** rs485 baud rate */
	public String baudRate;
	/** rs485 data bit */
	public String dataBit;
	/** rs485 stop bit */
	public String stopBit;
	/** rs485 parity check */
	public String parity;

	/** Constructor for a new Info485 instance.
	 * @param protocol = Protocol
	 * @param address = Address
	 * @param baudRate = Baud rate
	 * @param dataBit = Data bit
	 * @param stopBit = Stop bit
	 * @param parity = Parity check
	 */
	public Info485(String protocol, String address, String baudRate, String dataBit, String stopBit, String parity) {
		this.protocol = protocol;
		this.address = address;
		this.baudRate = baudRate;
		this.dataBit = dataBit;
		this.stopBit = stopBit;
		this.parity = parity;
	}

	@Override public String toString() {
		return "Info485 [protocol=" + this.protocol + ", address=" + this.address + ", baudRate=" + this.baudRate + ", dataBit=" + this.dataBit + ", stopBit=" + this.stopBit + ", parity=" + this.parity + "]";
	}
}
