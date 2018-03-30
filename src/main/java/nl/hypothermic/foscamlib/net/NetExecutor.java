package nl.hypothermic.foscamlib.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetExecutor {

	/** Construct a NetExecutor */
	public NetExecutor() {
		;
	}
	
	/** 
	 * Raw HTTP GET request executor
	 * @param address
	 */
	public String get(String address) throws IOException {
		StringBuilder result = new StringBuilder();
	    URL url = new URL(address);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) {
	       result.append(line);
	    }
	    rd.close();
	    return result.toString();
	}
}
