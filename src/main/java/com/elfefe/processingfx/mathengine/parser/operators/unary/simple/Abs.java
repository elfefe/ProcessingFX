package com.elfefe.processingfx.mathengine.parser.operators.unary.simple;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeFactory;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeNumber;
import com.elfefe.processingfx.mathengine.parser.operators.unary.NumberOperator;

import java.util.function.Function;

public class Abs extends NumberOperator {

    @Override
    protected Function<NodeNumber, NodeConstant> getFunc() {
        return (num -> NodeFactory.createNodeNumberFrom(Math.abs(num.doubleValue())));
    }

    @Override
    public String[] getAliases() {
        return new String[]{"abs", "absolute"};
    }

    @Override
    public String toLongString() {
        return "aboslute";
    }

    @Override
    public String toString() {
        return "abs";
    }

}
