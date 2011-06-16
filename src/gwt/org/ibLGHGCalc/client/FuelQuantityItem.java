package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;


public class FuelQuantityItem extends FloatItem {
    private final IsFloatValidator floatValidator = new IsFloatValidator();
    private final FloatPrecisionValidator floatPrecisionValidator = new FloatPrecisionValidator();

	public FuelQuantityItem() {
	    super();
	    this.setName("fuelQuantity");
	    //this.setValidators(floatValidator,floatPrecisionValidator);
            this.setValidators(floatValidator);
	    this.setValidateOnExit(Boolean.TRUE);
	    this.setValidateOnChange(Boolean.TRUE);
	    this.setRequired(Boolean.TRUE);
            //this.setValueFormatter("#,##0.00");
	}
}
