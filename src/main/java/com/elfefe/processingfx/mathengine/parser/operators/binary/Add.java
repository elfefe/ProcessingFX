package com.elfefe.processingfx.mathengine.parser.operators.binary;

import com.elfefe.fonctionne.mathengine.parser.nodes.NodeConstant;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeNumber;

import java.util.function.BiFunction;

public class Add extends SimpleBinaryOperator
{
	@Override
	public String[] getAliases()
	{
		return new String[] { "+", "add", "plus", "and" };
	}

	@Override
	public int getPrecedence()
	{
		return 6;
	}

	@Override
	public String toLongString()
	{
		return "add";
	}

	@Override
	protected BiFunction<NodeNumber, NodeNumber, NodeConstant> getBiFunc() {
		return NodeNumber::add;
	}

	@Override
	public String toString()
	{
		return "+";
	}
}
