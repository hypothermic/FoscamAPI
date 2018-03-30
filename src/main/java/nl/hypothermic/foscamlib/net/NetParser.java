package nl.hypothermic.foscamlib.net;

import nl.hypothermic.foscamlib.Result;

public class NetParser {

	public NetParser() {
		;
	}
	
	/** Get result from xml response
	 * @param xml response
	 * @return Result
	 */
	public Result getResult(String xml) {
		if (xml == null || !xml.contains("<result>") || !xml.contains("</result>")) {
			// All cgi responses must have result tags, so invalid response
			return Result.INVALID_RESPONSE;
		} else if (xml.contains("<result>0</result>")) {
			return Result.SUCCESS;
		} else if (xml.contains("<result>-1</result>")) {
			return Result.FORMATERR;
		} else if (xml.contains("<result>-2</result>")) {
			return Result.CREDSERR;
		} else if (xml.contains("<result>-3</result>")) {
			return Result.ACCESSDENIED;
		} else if (xml.contains("<result>-4</result>")) {
			return Result.EXECFAIL;
		} else if (xml.contains("<result>-5</result>")) {
			return Result.TIMEOUT;
		} else if (xml.contains("<result>-6</result>")) {
			return Result.RESERVE1;
		} else if (xml.contains("<result>-7</result>")) {
			return Result.UNKNOWNERR;
		} else if (xml.contains("<result>-8</result>")) {
			return Result.RESERVE2;
		} else {
			return Result.INVALID_RESPONSE;
		}
	}
	
	/**
	 * Get value in specified tag
	 * @param xml body
	 * @param tag name
	 * @return element value
	 */
	// source: https://stackoverflow.com/questions/4076910/how-to-retrieve-element-value-of-xml-using-java
	public String getTagValue(String xml, String tagName){
	    return xml.split("<" + tagName + ">")[1].split("</" + tagName + ">")[0];
	}
}
