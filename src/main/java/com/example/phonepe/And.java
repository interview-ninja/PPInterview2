package com.example.phonepe;


public class And extends Operation {
	public boolean interpret() {
		return left.interpret() && right.interpret();
	}
}
