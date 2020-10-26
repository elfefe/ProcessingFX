package com.elfefe.processingfx.mathengine.parser.operators.unary.simple;

import com.elfefe.fonctionne.mathengine.parser.nodes.NodeConstant;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeDouble;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeNumber;
import com.elfefe.fonctionne.mathengine.parser.operators.unary.NumberOperator;

import java.util.function.Function;

public class ToDouble extends NumberOperator {

    @Override
    public String[] getAliases() {
        return new String[]{"d", "todouble", "double", "num"};
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    protected Function<NodeNumber, NodeConstant> getFunc() {
        return (num -> new NodeDouble(num.doubleValue()));
    }

    @Override
    public String toLongString() {
        return toString();
    }

    @Override
    public String toString() {
        return "double";
    }
}
