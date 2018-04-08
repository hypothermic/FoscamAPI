package nl.hypothermic.foscamlib.containers;

/******************************\
 * > Info485.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class Info485 {
	
	// I would've rather named this class '485Info'. rip.
	
	/** rs485 protocol */
	public final String protocol;
	/** rs485 address */
	public final String address;
	/** rs485 baud rate */
	public final String baudRate;
	/** rs485 data bit */
	public final String dataBit;
	/** rs485 stop bit */
	public final String stopBit;
	/** rs485 parity check */
	public final String parity;

	/** Constructor for a new Info485 instance. */
	public Info485(final String protocol, final String address, final String baudRate,
				   final String dataBit, final String stopBit, final String parity) {
		this.protocol = protocol;
		this.address = address;
		this.baudRate = baudRate;
		this.dataBit = dataBit;
		this.stopBit = stopBit;
		this.parity = parity;
	}
}
