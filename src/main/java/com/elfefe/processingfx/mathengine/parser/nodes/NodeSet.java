package com.elfefe.processingfx.mathengine.parser.nodes;

import java.util.function.Function;

public interface NodeSet {

    NodeConstant resolve(Function<Node, NodeConstant> func);

}
