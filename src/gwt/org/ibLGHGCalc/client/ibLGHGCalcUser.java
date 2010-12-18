package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
                      
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ibLGHGCalcUser implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        HLayout mainLayout = new HLayout();
        mainLayout.setWidth100();
        mainLayout.setHeight100();
        mainLayout.setLayoutMargin(5);

        final DynamicForm form = new DynamicForm();
        //form.setWidth(500);

        EF_StationaryCombustion_EPADS eF_StationaryCombustion_EPADS = EF_StationaryCombustion_EPADS.getInstance();

        final SelectItem fuelTypeItem = new SelectItem();
        fuelTypeItem.setName("fuelType");
        fuelTypeItem.setPickListWidth(310);
        fuelTypeItem.setTitle("Fuel Type");
        fuelTypeItem.setOptionDataSource(eF_StationaryCombustion_EPADS);

        //form.setItems(fuelTypeItem);

        mainLayout.addMember(form);

        Button button = new Button("You can do it!");
        button.setAutoFit(true);
        button.setPadding(5);
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent evt) {
                SC.say("by Dec 7, 2010");
            }
        });
        mainLayout.addMember(button);

        RootPanel.get("main").add(mainLayout);

        //main.draw();
    }
}
