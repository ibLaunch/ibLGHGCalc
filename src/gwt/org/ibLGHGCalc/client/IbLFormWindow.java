package org.ibLGHGCalc.client;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.*;


public class IbLFormWindow extends Window {  
    
     private final DynamicForm dataForm;     
     private final ListGrid dataGrid;
     
     public IbLFormWindow(String windowTitleString, DynamicForm df, ListGrid dg) {
                
	    this.dataForm = df;    
            this.dataGrid = dg;

    	    dataForm.setCellPadding(5);
    	    dataForm.setWidth("100%");
                
	    final IButton cancelButton = new IButton();
	    final IButton saveButton = new IButton();

	    saveButton.setTitle("SAVE");
	    saveButton.setTooltip("Save this Stationary Combustion Source");
	    saveButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent clickEvent) {
		if ((!dataForm.getErrors().isEmpty()) || !dataForm.validate()){
		    SC.say("Please clear errors before submitting this information!");
		}
		else {
		    Record formRecord = dataForm.getValuesAsRecord();
		    Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();		    
		    formRecord.setAttribute("organizationId", organizationIdValue);
		    dataGrid.updateData(formRecord);		    
		    dataForm.markForRedraw();
		    //this.hide();
		}
	      }
	    });

	    cancelButton.setTitle("CANCEL");
	    cancelButton.setTooltip("Cancel");
	    cancelButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent clickEvent) {
		dataForm.clearValues();
		//this.hide();
	      }
	    });

	    HLayout buttons = new HLayout(10);
	    buttons.setAlign(Alignment.CENTER);
	    buttons.addMember(cancelButton);
	    buttons.addMember(saveButton);

	    VLayout dialog = new VLayout(10);
	    dialog.setPadding(10);
	    dialog.addMember(dataForm);
	    dialog.addMember(buttons);
	    this.setShowShadow(true);	    
	    this.setIsModal(true);
	    this.setPadding(20);
	    this.setWidth(500);
	    this.setHeight(260);
	    this.setShowMinimizeButton(false);
	    this.setShowCloseButton(true);
	    this.setShowModalMask(true);
	    this.centerInPage();
	    this.setTitle(windowTitleString);
	    this.setStyleName("labels");
	    this.addItem(dialog);    
     }
}  
    