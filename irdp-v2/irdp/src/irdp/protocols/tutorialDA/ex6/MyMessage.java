package irdp.protocols.tutorialDA.ex6;

import java.io.Serializable;

public class MyMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5165561603097399763L;
	private int id;
	private String string;

	public MyMessage(int id, String string) {
		this.id = id;
		this.string = string;
	}

	public int getId() {
		return id;
	}

	public String getString() {
		return string;
	}
}