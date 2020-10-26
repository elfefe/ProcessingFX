package com.elfefe.processingfx.mathengine.parser.operators.unary.simple;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeNumber;
import com.elfefe.processingfx.mathengine.parser.nodes.NodePercent;
import com.elfefe.processingfx.mathengine.parser.operators.unary.NumberOperator;

import java.util.function.Function;

public class Percent extends NumberOperator {

    @Override
    public String[] getAliases() {
        return new String[]{"percent", "%"};
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    protected Function<NodeNumber, NodeConstant> getFunc() {
        return (num -> new NodePercent(num.doubleValue()));
    }

    @Override
    public String toLongString() {
        return "percent";
    }

    @Override
    public String toString() {
        return "%";
    }

}