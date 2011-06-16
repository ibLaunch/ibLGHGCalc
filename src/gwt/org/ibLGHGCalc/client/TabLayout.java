package org.ibLGHGCalc.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.*;
import java.util.Date;
import java.util.HashMap;


public class TabLayout extends VLayout {  
    
     private final DynamicForm dataForm;
     private final Window dataFormWindow;
     private final ListGrid dataGrid;
     private String ADD_NEW_SOURCE_TEXT = "Add new source";
     private int ADD_BUTTON_WIDTH = 150;
     
     public TabLayout(String labelString, DynamicForm df, Window dfw, ListGrid dg) {  
                
        this.setWidth100();
        this.setHeight100();
        //this.setLayoutMargin(15);
        
        this.dataForm = df;
        this.dataFormWindow = dfw;
        this.dataGrid = dg;

        Label tabLabel = new Label(labelString);
        tabLabel.setHeight(15);
        tabLabel.setWidth100();
        tabLabel.setAlign(Alignment.LEFT);
        tabLabel.setStyleName("labels");
        
        IButton addButton = new IButton(ADD_NEW_SOURCE_TEXT);
        addButton.setWidth(ADD_BUTTON_WIDTH);        
        addButton.setIcon("addIcon.jpg");

        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ibLUsers.initializeValidators();
                //dataForm.setValidateOnExit(Boolean.TRUE);
                dataForm.editNewRecord();
                dataForm.setValues(getInitialValues());
                dataFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(tabLabel);
        gridButtonLayout.addMember(addButton);
        this.addMember(gridButtonLayout);
        this.addMember(dataGrid);
     }

    private HashMap getInitialValues() {
        Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
        Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
        HashMap formDefaultValue = new HashMap();
        formDefaultValue.put("fuelUsedBeginDate", currentInventoryBeginDateMin);
        formDefaultValue.put("fuelUsedEndDate", currentInventoryEndDateMax);
        return formDefaultValue;
    }
}  
    