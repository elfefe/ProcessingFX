package com.elfefe.processingfx.mathengine.parser.operators.unary;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeMatrix;
import com.elfefe.processingfx.mathengine.parser.operators.UnaryOperator;

public abstract class MatrixOperator extends UnaryOperator
{
	protected abstract NodeConstant calculateResultFromMatrix(NodeMatrix arg1);
	
	@Override
	public NodeConstant toResult(NodeConstant arg1)
	{
		if(arg1 instanceof NodeMatrix)
			return calculateResultFromMatrix((NodeMatrix) arg1);
		
		throw new IllegalArgumentException("Argument to operator: " + toString() + " must be a matrix");
	}
}
