package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectOtherItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class launchModule implements EntryPoint {
    /**
     * This is the entry point method.
     */
    private final DynamicForm organizationForm = new DynamicForm();
    private final HLayout organizationLayout = new HLayout();

    public void onModuleLoad() {

        final OrganizationDS organizationDS = OrganizationDS.getInstance();
        organizationForm.setDataSource(organizationDS);

/*
        TextItem organizatioName = new TextItem("name");
        organizatioName.setTitle("Organization Name");
        organizatioName.setSelectOnFocus(true);
        organizationForm.setItems(organizatioName);
*/
        SelectOtherItem selectOtherItem = new SelectOtherItem();
        selectOtherItem.setOtherTitle("Add new Organization");
        selectOtherItem.setOtherValue("name");

        selectOtherItem.setTitle("Select Organization");
        //selectOtherItem.setValueMap("Ea", "Pkt", "Bag", "Ctn");
        selectOtherItem.setOptionDataSource(organizationDS);

        organizationForm.setFields(selectOtherItem);
        organizationLayout.addMember(organizationForm);

        Button saveOrganiztionButton = new Button("Save");
        organizationLayout.addMember(saveOrganiztionButton);

	saveOrganiztionButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
		organizationForm.saveData();
		}
            });
         RootPanel.get("orgLayout").add(organizationLayout);
    }
}

