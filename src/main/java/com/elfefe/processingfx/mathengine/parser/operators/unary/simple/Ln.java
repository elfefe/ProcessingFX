package com.elfefe.processingfx.mathengine.parser.operators.unary.simple;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeFactory;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeNumber;
import com.elfefe.processingfx.mathengine.parser.operators.unary.NumberOperator;

import java.util.function.Function;

public class Ln extends NumberOperator {

    @Override
    public String[] getAliases() {
        return new String[]{"ln", "nlog", "naturallog", "natlog"};
    }

    @Override
    public int getPrecedence() {
        return 5;
    }

    @Override
    protected Function<NodeNumber, NodeConstant> getFunc() {
        return (num -> NodeFactory.createNodeNumberFrom(Math.log(num.doubleValue())));
    }

    @Override
    public String toLongString() {
        return "natural logarithm";
    }

    @Override
    public String toString() {
        return "ln";
    }
}
