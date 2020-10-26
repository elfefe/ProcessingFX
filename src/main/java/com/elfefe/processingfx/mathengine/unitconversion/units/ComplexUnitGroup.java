package com.elfefe.processingfx.mathengine.unitconversion.units;

import com.elfefe.fonctionne.mathengine.BigRational;
import com.elfefe.fonctionne.mathengine.parser.Evaluator;

public class ComplexUnitGroup extends UnitGroup {

    private static Evaluator evaluator = Evaluator.newSimpleBinaryEvaluator();

    @Override
    protected BigRational doConversion(Conversion params) {
        ComplexSubUnit from, to;

        if ((from = (ComplexSubUnit) params.getFrom()) != null
                && (to = (ComplexSubUnit) params.getTo()) != null) {
            evaluator.addVariable(from.getVariable(), params.getValue().doubleValue());

            String equation = from.getEquationFor(to);
            if (equation != null) {
                return new BigRational(evaluator.evaluateDouble(equation));
            }

            return params.getValue();
        }

        return null;
    }
}