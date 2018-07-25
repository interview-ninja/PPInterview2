package com.example.phonepe;


public class Or extends Operation {
	public boolean interpret() {
		return left.interpret() || right.interpret();
	}

}
