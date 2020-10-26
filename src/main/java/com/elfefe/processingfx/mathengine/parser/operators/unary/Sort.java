package com.elfefe.processingfx.mathengine.parser.operators.unary;

import com.elfefe.fonctionne.mathengine.parser.nodes.Node;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeConstant;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeVector;

import java.util.Arrays;

public class Sort extends VectorOperator
{
	@Override
	protected NodeConstant calculateResultFromVector(NodeVector arg1)
	{
		Node[] elements = ((NodeVector) arg1).getValues();

		Node[] results = elements.clone();
		Arrays.sort(results);
		return new NodeVector(results);
	}
	
	@Override
	protected void fillAcceptedArguments()
	{
		acceptedArgumentLengths.add(INFINITE_ARGUMENT_LENGTH);
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "sort", "arrange" };
	}
	
	@Override
	protected String getExpectedArgumentsString()
	{
		return INFINITE_ARG_LENGTH_EXPECTED_USAGE;
	}

	@Override
	public int getPrecedence()
	{
		return 2;
	}

	@Override
	public String toLongString()
	{
		return toString();
	}

	@Override
	public String toString()
	{
		return "sort";
	}
}
