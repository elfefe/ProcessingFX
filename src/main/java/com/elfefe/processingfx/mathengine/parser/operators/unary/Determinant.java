package com.elfefe.processingfx.mathengine.parser.operators.unary;

import com.elfefe.fonctionne.mathengine.parser.nodes.NodeConstant;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeFactory;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeMatrix;

public class Determinant extends MatrixOperator
{

	@Override
	protected NodeConstant calculateResultFromMatrix(NodeMatrix arg1)
	{
		return NodeFactory.createNodeNumberFrom(arg1.toDoubleMatrix()
				.determinant());
	}

	@Override
	public String[] getAliases()
	{
		return new String[] { "determinant", "determ" };
	}

	@Override
	public int getPrecedence()
	{
		return 3;
	}

	@Override
	public String toLongString()
	{
		return toString();
	}

	@Override
	public String toString()
	{
		return "determinant";
	}

}
