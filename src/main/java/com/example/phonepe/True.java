package com.example.phonepe;

public class True extends Operator {
	public True() {
		super(true);
	}

	public boolean interpret() {
		return value;
	}
}
