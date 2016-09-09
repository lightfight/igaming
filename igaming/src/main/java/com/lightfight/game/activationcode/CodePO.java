package com.lightfight.game.activationcode;

public class CodePO {

	private int id;
	
	private String code;

	public CodePO(int id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
	

	@Override
	public String toString() {
		return id + " = " + code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
