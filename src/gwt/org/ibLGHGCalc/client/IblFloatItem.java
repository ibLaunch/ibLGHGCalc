package org.ibLGHGCalc.client;

import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;

public class IblFloatItem extends FloatItem {
        private static final IsFloatValidator floatValidator = new IsFloatValidator();

	public IblFloatItem(String name) {
            floatValidator.setErrorMessage("Not a valid float value");
            this.setName(name);
            this.setValidators(floatValidator);
            this.setValidateOnExit(Boolean.TRUE);
            this.setValidateOnChange(Boolean.TRUE);
            //this.setRequired(Boolean.TRUE);
	}
}