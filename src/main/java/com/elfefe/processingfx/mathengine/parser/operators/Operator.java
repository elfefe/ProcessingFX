package com.elfefe.processingfx.mathengine.parser.operators;

import com.elfefe.fonctionne.mathengine.parser.EvaluationContext;

public abstract class Operator {

    protected EvaluationContext evaluationContext;

    protected EvaluationContext getEvaluationContext() {
        return evaluationContext;
    }

    public abstract String[] getAliases();

    // TODO : Implement correct precedence structure for all operators
    public abstract int getPrecedence();

    public abstract String toLongString();

    @Override
    public abstract String toString();
}
