package com.example.phonepe;


public class Not extends Operation {
	public void setChild(EvaluateExpression child) {
		setRight(child);
	}

	public boolean interpret() {
		return !right.interpret();
	}
}
