package com.example.phonepe;

public class False extends Operator {
	public False() {
		super(false);
	}

	public boolean interpret() {
		return value;
	}
}
