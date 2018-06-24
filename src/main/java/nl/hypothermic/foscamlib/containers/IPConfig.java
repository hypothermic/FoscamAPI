package nl.hypothermic.foscamlib.containers;

public class IPConfig {
	
	public boolean isDHCP;
	public String ip, gate, mask, dns1, dns2;
	
	public IPConfig(boolean isDHCP, String ip, String gate, String mask, String dns1, String dns2) {
		super();
		this.isDHCP = isDHCP;
		this.ip = ip;
		this.gate = gate;
		this.mask = mask;
		this.dns1 = dns1;
		this.dns2 = dns2;
	}

	@Override public String toString() {
		return "IPConfig [isDHCP=" + this.isDHCP + ", ip=" + this.ip + ", gate=" + this.gate + ", mask=" + this.mask + ", dns1=" + this.dns1 + ", dns2=" + this.dns2 + "]";
	}
}
