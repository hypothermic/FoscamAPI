package nl.hypothermic.foscamlib.net;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import nl.hypothermic.foscamlib.containers.Credentials;
import nl.hypothermic.foscamlib.core.Result;
import nl.hypothermic.foscamlib.core.RxData;

/******************************\
 * > NetManager.java		< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class NetManager {
	
	private final NetExecutor x;
	private final NetParser p;
	public final String addr;
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
	
	//------- PROPER METHODS -------//
	
	/**
	 * Execute a HTTP GET request
	 * Warning: privileges may be needed!
	 * @param command = Command that needs to be executed
	 * @param params = {@literal HashMap<String (parameter), String (value)>}. Can be null if no parameters.
	 * @return RxData with Result and xml
	 */
	public RxData exec(String command, HashMap<String, String> params) {
		Result deferr = Result.UNKNOWNERR;
		if (params == null) {
			params = new HashMap<String, String>();
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(addr + "cmd=" + command);
			for (Map.Entry<String, String> p : params.entrySet()) {
				sb.append("&" + URLEncoder.encode(p.getKey()) + "=" + URLEncoder.encode(p.getValue()));
			}
			sb.append("&usr=" + creds.user + "&pwd=" + creds.password);
			String xml = x.get(sb.toString());
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
	
//	//------- LEGACY METHODS ------//
//	// For backwards compatibility //
//	
//	/**
//	 * Execute a HTTP GET request with one command.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(addr + "cmd=" + URLEncoder.encode(command) + "&usr=" + creds.user + "&pwd=" + creds.password);
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/**
//	 * Execute a HTTP GET request with one command and one parameter with value.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @param param1name
//	 * @param param1value
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command, String param1name, String param1value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(addr + "cmd=" + URLEncoder.encode(command) + "&" + URLEncoder.encode(param1name) + "=" + URLEncoder.encode(param1value) + "&usr=" + URLEncoder.encode(creds.user) + "&pwd=" + creds.password);
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/**
//	 * Execute a HTTP GET request with one command and two parameters with one value each.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @param param1name
//	 * @param param1value
//	 * @param param2name
//	 * @param param2value
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command, String param1name, String param1value, String param2name, String param2value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(addr + "cmd=" + URLEncoder.encode(command) + "&"
//								+ URLEncoder.encode(param1name) + "=" + URLEncoder.encode(param1value) + "&"
//								+ URLEncoder.encode(param2name) + "=" + URLEncoder.encode(param2value)
//								+ "&usr=" + URLEncoder.encode(creds.user) + "&pwd=" + URLEncoder.encode(creds.password));
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/**
//	 * Execute a HTTP GET request with one command and three parameters with one value each.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @param param1name
//	 * @param param1value
//	 * @param param2name
//	 * @param param2value
//	 * @param param3name
//	 * @param param3value
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command, String param1name, String param1value, String param2name, String param2value, String param3name, String param3value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(URLEncoder.encode(addr + "cmd=" + command + "&"
//								+ param1name + "=" + param1value + "&"
//								+ param2name + "=" + param2value + "&"
//								+ param3name + "=" + param3value
//								+ "&usr=" + creds.user + "&pwd=" + creds.password));
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/**
//	 * Execute a HTTP GET request with one command and four parameters with one value each.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @param param1name
//	 * @param param1value
//	 * @param param2name
//	 * @param param2value
//	 * @param param3name
//	 * @param param3value
//	 * @param param4name
//	 * @param param4value
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command, String param1name, String param1value, String param2name, String param2value, String param3name, String param3value, String param4name, String param4value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(URLEncoder.encode(addr + "cmd=" + command + "&"
//								+ param1name + "=" + param1value + "&"
//								+ param2name + "=" + param2value + "&"
//								+ param3name + "=" + param3value + "&"
//								+ param4name + "=" + param4value
//								+ "&usr=" + creds.user + "&pwd=" + creds.password));
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/**
//	 * Execute a HTTP GET request with one command and five parameters with one value each.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @param param1name
//	 * @param param1value
//	 * @param param2name
//	 * @param param2value
//	 * @param param3name
//	 * @param param3value
//	 * @param param4name
//	 * @param param4value
//	 * @param param5name
//	 * @param param5value
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command, String param1name, String param1value, String param2name, String param2value, String param3name, String param3value, String param4name, String param4value, String param5name, String param5value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(URLEncoder.encode(addr + "cmd=" + command + "&"
//								+ param1name + "=" + param1value + "&"
//								+ param2name + "=" + param2value + "&"
//								+ param3name + "=" + param3value + "&"
//								+ param4name + "=" + param4value + "&"
//								+ param5name + "=" + param5value
//								+ "&usr=" + creds.user + "&pwd=" + creds.password));
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/**
//	 * Execute a HTTP GET request with one command and 6 parameters with one value each.
//	 * Warning: privileges may be needed!
//	 * @param command
//	 * @param param1name
//	 * @param param1value
//	 * @param param2name
//	 * @param param2value
//	 * @param param3name
//	 * @param param3value
//	 * @param param4name
//	 * @param param4value
//	 * @param param5name
//	 * @param param5value
//	 * @param param6name
//	 * @param param6value
//	 * @return RxData with Result and xml
//	 */
//	@Deprecated
//	public RxData exec(String command, String param1name, String param1value, String param2name, String param2value, String param3name, String param3value, String param4name, String param4value, String param5name, String param5value, String param6name, String param6value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(URLEncoder.encode(addr + "cmd=" + command + "&"
//								+ param1name + "=" + param1value + "&"
//								+ param2name + "=" + param2value + "&"
//								+ param3name + "=" + param3value + "&"
//								+ param4name + "=" + param4value + "&"
//								+ param5name + "=" + param5value + "&"
//								+ param6name + "=" + param6value
//								+ "&usr=" + creds.user + "&pwd=" + creds.password));
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}
//	
//	/*  // This code should *probably* be cleaned up lmfao. 35 parameters inside a method! What the fuck!
//	 *  // EDIT 8/4/2018: commented this method out and replaced references with new method: exec(String command, HashMap<String, String>);
//	public RxData exec(String command, String param1name, String param1value, String param2name, String param2value, String param3name, String param3value, String param4name, String param4value, String param5name, String param5value, String param6name, String param6value,
//					   String param7name, String param7value, String param8name, String param8value, String param9name, String param9value, String param10name, String param10value, String param11name, String param11value, String param12name, String param12value,
//					   String param13name, String param13value, String param14name, String param14value, String param15name, String param15value, String param16name, String param16value, String param17name, String param17value) {
//		Result deferr = Result.UNKNOWNERR;
//		try {
//			String xml = x.get(URLEncoder.encode(addr + "cmd=" + command + "&"
//								+ param1name + "=" + param1value + "&"
//								+ param2name + "=" + param2value + "&"
//								+ param3name + "=" + param3value + "&"
//								+ param4name + "=" + param4value + "&"
//								+ param5name + "=" + param5value + "&"
//								+ param6name + "=" + param6value + "&"
//								+ param7name + "=" + param7value + "&"
//								+ param8name + "=" + param8value + "&"
//								+ param9name + "=" + param9value + "&"
//								+ param10name + "=" + param10value + "&"
//								+ param11name + "=" + param11value + "&"
//								+ param12name + "=" + param12value + "&"
//								+ param13name + "=" + param13value + "&"
//								+ param14name + "=" + param14value + "&"
//								+ param15name + "=" + param15value + "&"
//								+ param16name + "=" + param16value + "&"
//								+ param17name + "=" + param17value
//								+ "&usr=" + creds.user + "&pwd=" + creds.password));
//			deferr = p.getResult(xml);
//			if (deferr == Result.INVALID_RESPONSE) {
//				throw new IOException();
//			}
//			RxData out = new RxData(deferr, xml);
//			return out;
//		} catch (IOException e) {
//			return new RxData(deferr, null);
//		}
//	}*/
}
