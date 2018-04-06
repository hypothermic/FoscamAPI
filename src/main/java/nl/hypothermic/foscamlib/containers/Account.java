package nl.hypothermic.foscamlib.containers;

/******************************\
 * > Account.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class Account {
	
	/** Username of the account */
	public String username;
	/** Password of the account */
	public String password;
	/** Privilege level of the account */
	public Privilege privilege;
	
	/**
	 * Create a new Account object which stores the username, password and privilege level. <br><br>
	 * <b>NOTE</b>: this is different from a Credentials object, because Credentials is final and Account is modifiable.
	 */
	public Account(final String username, final String password, final Privilege privilege) {
		this.username = username;
		this.password = password;
		this.privilege = privilege;
	}
	
	/**
	 * Enum of Account Privilege levels with their respective integers attached.
	 */
	public static enum Privilege {
		VISITOR(0),
		OPERATOR(1),
		ADMINISTRATOR(1);
		
		private final int value;
		
		private Privilege(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of Privilege instance
		 */
		public int getValue() {
			return this.value;
		}
	}
}
