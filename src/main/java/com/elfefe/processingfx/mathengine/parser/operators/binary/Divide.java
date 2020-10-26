package com.elfefe.processingfx.mathengine.parser.operators.binary;

import com.elfefe.fonctionne.mathengine.parser.nodes.NodeConstant;
import com.elfefe.fonctionne.mathengine.parser.nodes.NodeNumber;

import java.util.function.BiFunction;

public class Divide extends SimpleBinaryOperator {
    @Override
    public String[] getAliases() {
        return new String[]{"/", "div", "divide", "over"};
    }

    @Override
    public int getPrecedence() {
        return 4;
    }

    @Override
    public String toLongString() {
        return "divide";
    }

    @Override
    protected BiFunction<NodeNumber, NodeNumber, NodeConstant> getBiFunc() {
        return NodeNumber::divide;
    }

    @Override
    public String toString() {
        return "/";
    }
}
