package com.elfefe.processingfx.mathengine.parser.operators.binary.logical;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeBoolean;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeNumber;
import com.elfefe.processingfx.mathengine.parser.operators.BinaryOperator;

public class And extends BinaryOperator
{
	@Override
	public String[] getAliases()
	{
		return new String[] { "and", "&&" };
	}

	@Override
	public int getPrecedence()
	{
		return 10;
	}

	@Override
	public String toLongString()
	{
		return toString();
	}

	@Override
	public NodeConstant toResult(NodeConstant arg1, NodeConstant arg2)
	{
		if (arg1 instanceof NodeNumber)
		{
			if (arg2 instanceof NodeNumber)
			{
				boolean result = arg1.getTransformer().toNodeNumber().doubleValue() == 1 && arg2.getTransformer().toNodeNumber().doubleValue() == 1;

				return new NodeBoolean(result);
			}
		}

		throw new IllegalArgumentException("Must have two logical arguments to operator 'and'");
	}

	@Override
	public String toString()
	{
		return "and";
	}
}
