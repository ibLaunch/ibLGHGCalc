package org.ibLGHGCalc.client;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;
import com.google.gwt.i18n.client.NumberFormat;

    public class IblFloatItem extends FloatItem {
        private static final IsFloatValidator floatValidator = new IsFloatValidator();
        //private final String NUMBER_FORMAT_2 = "#,##0.00";

	public IblFloatItem(String name) {
            floatValidator.setErrorMessage("Not a valid float value");
            this.setName(name);
            this.setValidators(floatValidator);
            this.setValidateOnExit(Boolean.TRUE);
            this.setValidateOnChange(Boolean.TRUE);
            //this.setRequired(Boolean.TRUE);
	}

/*
	public IblFloatItem() {
            final NumberFormat amountFormat = NumberFormat.getFormat(NUMBER_FORMAT_2);
            FormItemValueFormatter amountFormItemValueFormatter = new FormItemValueFormatter() {
                public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
                    if (value == null) return "";
                    return amountFormat.format(Float.valueOf(value.toString()));
                }
            };

            this.setEditorValueFormatter(amountFormItemValueFormatter);

            floatValidator.setErrorMessage("Not a valid float value");
            //this.setName(name);
            this.setValidators(floatValidator);
            this.setValidateOnExit(Boolean.TRUE);
            this.setValidateOnChange(Boolean.TRUE);
            //this.setRequired(Boolean.TRUE);
	}
*/
}