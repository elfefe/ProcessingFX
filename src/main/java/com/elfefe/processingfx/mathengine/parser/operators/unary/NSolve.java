package com.elfefe.processingfx.mathengine.parser.operators.unary;

import com.elfefe.processingfx.mathengine.parser.nodes.*;
import com.elfefe.processingfx.mathengine.solvers.ConvergenceCriteria;
import com.elfefe.processingfx.mathengine.solvers.NewtonRaphsonSolver;

public class NSolve extends VectorOperator
{
	@Override
	protected NodeConstant calculateResultFromVector(NodeVector arg1)
	{
		Node[] elements = arg1.getValues();
		
		if(!(elements[0] instanceof NodeFunction))
			throw new IllegalArgumentException("First argument must be a function");
		
		NewtonRaphsonSolver solver = new NewtonRaphsonSolver(((NodeFunction) elements[0]).toFunction());
		solver.setIterations(25);
		solver.setConvergenceCriteria(ConvergenceCriteria.NumberOfIterations);
		
		if (arg1.getSize() == 2)
		{
			solver.setInitialGuess((int) elements[1].getTransformer().toNodeNumber().doubleValue());
		}
		
		return new NodeDouble(solver.solve());
	}

	@Override
	protected String getExpectedArgumentsString()
	{
		return "function, initialGuess";
	}

	@Override
	protected void fillAcceptedArguments()
	{
		acceptedArgumentLengths.add(1);
		acceptedArgumentLengths.add(2);
	}

	@Override
	public String[] getAliases()
	{
		return new String[] { "nsolve" };
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
		return "nsolve";
	}
}
