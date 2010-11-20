package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.DynamicForm;


                      
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ibLGHGCalcUser implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        VLayout main = new VLayout();
        main.setWidth100();
        main.setHeight100();
        main.setLayoutMargin(5);

        final DynamicForm form = new DynamicForm();
        form.setWidth(1000);

        StationaryCombustionInfoDS stationaryCombustionInfoDS = StationaryCombustionInfoDS.getInstance();

        final SelectItem fuelTypeItem = new SelectItem();
        fuelTypeItem.setName("fuelType");
        fuelTypeItem.setPickListWidth(310);
        fuelTypeItem.setTitle("Fuel Type");
        fuelTypeItem.setOptionDataSource(stationaryCombustionInfoDS);

        form.setItems(fuelTypeItem);

        main.addMember(form);

        Button button = new Button("You can do it!");
        button.setAutoFit(true);
        button.setPadding(5);
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent evt) {
                SC.say("by Dec 7, 2010");
            }
        });
        main.addMember(button);

        main.draw();
    }
}
