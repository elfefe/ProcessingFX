package com.elfefe.processingfx.mathengine.unitconversion.units;

import com.elfefe.processingfx.mathengine.BigRational;

public class SimpleUnitGroup extends UnitGroup {

    @Override
    protected BigRational doConversion(Conversion params) {
        SimpleSubUnit from, to;

        if ((from = (SimpleSubUnit) params.getFrom()) != null
                && (to = (SimpleSubUnit) params.getTo()) != null) {
            return params.getValue().divide(to.getConversion()).multiply(from.getConversion());
        }

        return null;
    }
}
