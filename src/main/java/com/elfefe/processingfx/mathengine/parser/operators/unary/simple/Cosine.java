package com.elfefe.processingfx.mathengine.parser.operators.unary.simple;

import com.elfefe.processingfx.mathengine.parser.nodes.NodeConstant;
import com.elfefe.processingfx.mathengine.parser.nodes.NodeFactory;
import com.elfefe.processingfx.mathengine.parser.operators.unary.TrigOperator;

public final class Cosine extends TrigOperator {

    @Override
    public String[] getAliases() {
        return new String[]{"cos", "cosine"};
    }

    @Override
    public NodeConstant getResult(double num) {
        double result = Math.cos(num);
        return NodeFactory.createNodeNumberFrom(result);
    }

    @Override
    public String toLongString() {
        return "cosine";
    }

    @Override
    public String toString() {
        return "cos";
    }
}
