package com.example.phonepe;

public abstract class Operation implements EvaluateExpression {
	protected EvaluateExpression left, right;

	public void setLeft(EvaluateExpression left) {
		this.left = left;
	}

	public void setRight(EvaluateExpression right) {
		this.right = right;
	}
}
