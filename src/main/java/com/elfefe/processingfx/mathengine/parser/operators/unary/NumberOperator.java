package com.elfefe.processingfx.mathengine.parser.operators.unary;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeNumber;
import com.elfefe.processingfx.mathengine.parser.operators.UnaryOperator;

import java.util.function.Function;

public abstract class NumberOperator extends UnaryOperator {

    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public NodeConstant toResult(NodeConstant arg1) {
        return arg1.applyUniFunc(getFunc());
    }

    protected abstract Function<NodeNumber, NodeConstant> getFunc();
}
