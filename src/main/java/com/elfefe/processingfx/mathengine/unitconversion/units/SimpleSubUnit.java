package com.elfefe.processingfx.mathengine.unitconversion.units;

import com.elfefe.processingfx.mathengine.BigRational;

public class SimpleSubUnit extends SubUnit
{
	private BigRational conversion;

	public BigRational getConversion()
	{
		return conversion;
	}

	public void setConversion(BigRational conversion)
	{
		this.conversion = conversion;
	}
	
	public void setConversion(double conversion)
	{
		this.conversion = new BigRational(conversion);
	}
}
