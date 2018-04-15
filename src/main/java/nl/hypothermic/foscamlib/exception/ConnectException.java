package nl.hypothermic.foscamlib.exception;

import nl.hypothermic.foscamlib.core.Result;

/******************************\
 * > ConnectException.java	< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class ConnectException extends Exception {
	
	public ConnectException(String s) {
		super(s);
	}
}
