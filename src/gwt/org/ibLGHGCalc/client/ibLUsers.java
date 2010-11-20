package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FilterCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import java.util.ArrayList;
import java.util.List;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ibLUsers implements EntryPoint {
    /**
     * This is the entry point method.
     */

    private String formSubmitAction = "EF_StationaryCombustion_EPA";

public void onModuleLoad() {

//Defining tab set
        final TabSet topTabSet = new TabSet();
        topTabSet.setTabBarPosition(Side.TOP);
        topTabSet.setTabBarAlign(Side.LEFT);
        topTabSet.setWidth(1000);
        topTabSet.setHeight(500);

//--Defining Stationary Combustion tab layout

        VLayout layout = new VLayout(15);

        StationaryCombustionInfoDS stationaryCombustionInfoDS = StationaryCombustionInfoDS.getInstance();
        EF_StationaryCombustion_EPADS eF_StationaryCombustion_EPADS = EF_StationaryCombustion_EPADS.getInstance();

        Label stationaryCombustionDataLabel = new Label("Current stationary combustion sources");
        stationaryCombustionDataLabel.setWidth("100%");
        //stationaryCombustionDataLabel.setHeight(25);
        //remoteDataLabel.setBaseStyle("exampleSeparator");
        layout.addMember(stationaryCombustionDataLabel);

        final ListGrid stationaryCombustionDataGrid = new ListGrid();
        stationaryCombustionDataGrid.setWidth("100%");
        stationaryCombustionDataGrid.setHeight(200);
        stationaryCombustionDataGrid.setCanEdit(true);
        //stationaryCombustionDataGrid.setDataSource(stationaryCombustionInfoDS);


        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        //sourceDescriptionField.setWidth(30);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        SelectItem fuelTypeSelectItem = new SelectItem();
        fuelTypeSelectItem.setOptionDataSource(eF_StationaryCombustion_EPADS);
        //fuelTypeField.setEditorType(fuelTypeSelectItem);



        fuelTypeField.addChangedHandler(new com.smartgwt.client.widgets.grid.events.ChangedHandler() {
            public void onChanged(com.smartgwt.client.widgets.grid.events.ChangedEvent event) {
                stationaryCombustionDataGrid.clearEditValue(event.getRowNum(), "fuelUnit");
            }
        });

        ListGridField fuelQuantityField = new ListGridField("fuelQuantity", "Fuel Quantity");
        fuelQuantityField.setType(ListGridFieldType.FLOAT);
        //fuelQuantityField.setWidth(30);

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        SelectItem fuelUnitEditor = new SelectItem();

        fuelUnitEditor.setPickListFilterCriteriaFunction(new FilterCriteriaFunction() {
            public Criteria getCriteria() {
                String selectedFuelType = (String) stationaryCombustionDataGrid.getEditedCell(stationaryCombustionDataGrid.getEditRow(), "fuelType");
                return new Criteria("fuelType", selectedFuelType);
            }
        });

        fuelUnitEditor.setOptionDataSource(eF_StationaryCombustion_EPADS);
        //fuelUnitField.setEditorType(fuelUnitEditor);

        //--Below is not working due to embeded datasource in fuelTypeField and fuelUnitField
        //stationaryCombustionDataGrid.setFields(sourceDescriptionField, fuelTypeField, fuelQuantityField, fuelUnitField);

        stationaryCombustionDataGrid.setFields(sourceDescriptionField, fuelTypeField, fuelQuantityField, fuelUnitField);

        layout.addMember(stationaryCombustionDataGrid);

        IButton newStationaryCombustionButton = new IButton("Add new stationary combustion source");
        newStationaryCombustionButton.setWidth(225);
        newStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //Map defaultValues = new HashMap();
                //defaultValues.put("quantity", 1);
                stationaryCombustionDataGrid.startEditingNew();
            }
        });

        layout.addMember(newStationaryCombustionButton);

//--Defining Stationary Combustion tab

        final Tab stationaryCombustionTab = new Tab("Stationary Combustion");
        //DynamicForm preferencesForm = new DynamicForm();
        //CheckboxItem useTabsCheckbox = new CheckboxItem();
        //useTabsCheckbox.setTitle("Use Smart GWT tabs");
        //preferencesForm.setFields(useTabsCheckbox);
        stationaryCombustionTab.setPane(layout);

//---Adding Stationary Combustion tab to topTab

        topTabSet.addTab(stationaryCombustionTab);

        VLayout topLayout = new VLayout();
        topLayout.setMembersMargin(15);
        topLayout.addMember(topTabSet);
        topLayout.setHeight("auto");

//--Defining File Upload  tab

        final DynamicForm uploadForm = new DynamicForm();
        uploadForm.setEncoding(Encoding.MULTIPART);
        //uploadForm.setMethod(POST);
        uploadForm.setAction(formSubmitAction);

        final UploadItem fileUpload = new UploadItem("fileUpload");

        fileUpload.setTitle("File");
        fileUpload.setWidth(300);
        //items.add(fileItem);
        fileUpload.addChangedHandler(new ChangedHandler() {
                public void onChanged(ChangedEvent e) {
                        System.out.println("change");
                }
        });
        Button uploadButton = new Button("Upload");
        uploadButton.addClickHandler(new ClickHandler(){
             public void onClick(ClickEvent e) {
                        Object obj = fileUpload.getDisplayValue();
                        if (obj != null) {
                                uploadForm.submitForm();
                        } else
                                SC.say("Please select a file.");
             }
        });

        List<FormItem> items = new ArrayList<FormItem>();

        items.add(fileUpload);

        FormItem[] fitems = new FormItem[items.size()];
        items.toArray(fitems);
        uploadForm.setItems(fitems);

//-Load form and button on Vstack

        VStack fileUploadLayout = new VStack();
        fileUploadLayout.setWidth100();
        fileUploadLayout.setMembersMargin(10);
        fileUploadLayout.setDefaultLayoutAlign(Alignment.LEFT);

        fileUploadLayout.addMember(uploadForm);
        fileUploadLayout.addMember(uploadButton);

//---Adding Stationary Combustion tab to topTab

        final Tab fileUploadTab = new Tab("Emission Factor File Upload");
        fileUploadTab.setPane(fileUploadLayout);

        topTabSet.addTab(fileUploadTab);

//--Add topLayout to "main"
         RootPanel.get("main").add(topLayout);
         //layout.draw();
    }
}
