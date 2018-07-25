package com.example.phonepe;

public abstract class Operator implements EvaluateExpression {
	protected boolean value;

	public Operator(boolean value) {
		this.value = value;
	}
}
