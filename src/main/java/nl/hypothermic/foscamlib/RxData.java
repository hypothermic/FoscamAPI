package nl.hypothermic.foscamlib;

public class RxData {
	
	public final Result result;
	public final String xml;

	public RxData(final Result result, final String xml) {
		this.result = result;
		this.xml = xml;
	}
}