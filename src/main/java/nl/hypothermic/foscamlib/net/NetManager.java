package nl.hypothermic.foscamlib.net;

import java.io.IOException;
import java.net.URLEncoder;

import nl.hypothermic.foscamlib.Credentials;
import nl.hypothermic.foscamlib.Result;
import nl.hypothermic.foscamlib.RxData;

public class NetManager {
	
	private final NetExecutor x;
	private final NetParser p;
	private final String addr;
	private final Credentials creds;

	/** Construct a NetManager */
	public NetManager(final NetExecutor x, final NetParser p, final String addr, final Credentials creds) {
		this.x = x;
		this.addr = addr + "/cgi-bin/CGIProxy.fcgi?";
		this.creds = creds;
		this.p = p;
	}
	
	/** 
	 * Test the connection.
	 * @return Result
	 */
	public Result testconn() {
		try {
			String xml = x.get(addr + "cmd=getIPInfo&usr=" + creds.user + "&pwd=" + creds.password);
			return p.getResult(xml);
		} catch (Exception x) {
			return Result.INVALID_ADDRESS;
		}
	}
	
	/**
	 * Execute a HTTP GET request with one command.
	 * Warning: privileges may be needed!
	 * @param command
	 * @return RxData with Result and xml
	 */
	public RxData exec(String command) {
		Result deferr = Result.UNKNOWNERR;
		try {
			String xml = x.get(addr + "cmd=" + URLEncoder.encode(command) + "&usr=" + creds.user + "&pwd=" + creds.password);
			deferr = p.getResult(xml);
			if (deferr == Result.INVALID_RESPONSE) {
				throw new IOException();
			}
			RxData out = new RxData(deferr, xml);
			return out;
		} catch (IOException e) {
			return new RxData(deferr, null);
		}
	}
	
	/**
	 * Execute a HTTP GET request with one command and one parameter with value.
	 * Warning: privileges may be needed!
	 * @param command
	 * @param param1name
	 * @param param1value
	 * @return RxData with Result and xml
	 */
	public RxData exec(String command, String param1name, String param1value) {
		Result deferr = Result.UNKNOWNERR;
		try {
			String xml = x.get(addr + "cmd=" + URLEncoder.encode(command) + "&" + URLEncoder.encode(param1name) + "=" + URLEncoder.encode(param1value) + "&usr=" + creds.user + "&pwd=" + creds.password);
			deferr = p.getResult(xml);
			if (deferr == Result.INVALID_RESPONSE) {
				throw new IOException();
			}
			RxData out = new RxData(deferr, xml);
			return out;
		} catch (IOException e) {
			return new RxData(deferr, null);
		}
	}
}
