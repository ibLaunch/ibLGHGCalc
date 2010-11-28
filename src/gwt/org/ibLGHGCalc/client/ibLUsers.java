package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.*;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.*;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ibLUsers implements EntryPoint {
    /**
     * This is the entry point method.
     */

    private final String eFUploadFormSubmitAction = "EF_StationaryCombustion_EPA";
    //private String stationayCombustionFormSubmitAction = "EF_StationaryCombustion_EPA";
    //private final String stationayCombustionFormSubmitAction = "StationaryCombustionInfo";
    private final Window stationaryCombustionFormWindow = new Window();
    private final DynamicForm stationaryCombustionForm = new DynamicForm();
    private final IButton cancelButton = new IButton();
    private final IButton saveButton = new IButton();
    private final StationaryCombustionInfoDS stationaryCombustionInfoDS = StationaryCombustionInfoDS.getInstance();
    private final EF_StationaryCombustion_EPADS eF_StationaryCombustion_EPADS = EF_StationaryCombustion_EPADS.getInstance();
    private final TabSet topTabSet = new TabSet();
    private final DynamicForm uploadForm = new DynamicForm();
    private final VLayout topLayout = new VLayout();


    private final ListGrid stationaryCombustionDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    ImgButton editImg = new ImgButton();
                    editImg.setShowDown(false);
                    editImg.setShowRollOver(false);
                    editImg.setLayoutAlign(Alignment.CENTER);
                    editImg.setSrc("/ibLGHGCalc/images/editIcon.gif");
                    editImg.setPrompt("Edit Comments");
                    editImg.setHeight100();
                    editImg.setWidth100();

                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            stationaryCombustionForm.editSelectedData(stationaryCombustionDataGrid);
                            stationaryCombustionFormWindow.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    IButton button = new IButton();
                    //button.setHeight(18);
                    //button.setWidth(65);
                    button.setIcon("/ibLGHGCalc/images/deleteIcon.png");
                    button.setTitle("Remove");
                    button.setPrompt("Remove this source");
                    button.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				                            public void execute(Boolean value) {
					                                    if (value)
						                                    stationaryCombustionDataGrid.removeSelectedData();
				                            }
			                    });

                        }
                    });
                    return button;
                } else {
                    return null;
                }

            }
    };

public void onModuleLoad() {

//Defining tab set
        topTabSet.setTabBarPosition(Side.TOP);
        topTabSet.setTabBarAlign(Side.LEFT);
        topTabSet.setWidth(1000);
        topTabSet.setHeight(500);

//--Defining Stationary Combustion tab layout
    stationaryCombustionTab();

//--Calling form to Add New Record to Stationary Combustion Source
    initStationaryCombustionEditForm();

//--Defining File Upload  tab
    fileUploadTab();

//--Add topLayout to "main"
    RootPanel.get("main").add(topLayout);
    //topLayout.draw();
}

private void stationaryCombustionTab() {

        VLayout layout = new VLayout(15);

        Label stationaryCombustionDataLabel = new Label("Current stationary combustion sources");
        //stationaryCombustionDataLabel.setWidth("100%");
        layout.addMember(stationaryCombustionDataLabel);

//--ListGrid setup
        stationaryCombustionDataGrid.setWidth("100%");
        stationaryCombustionDataGrid.setHeight(200);
        stationaryCombustionDataGrid.setShowRecordComponents(true);
        stationaryCombustionDataGrid.setShowRecordComponentsByCell(true);
        //stationaryCombustionDataGrid.setCanRemoveRecords(true);
        stationaryCombustionDataGrid.setShowAllRecords(true);
        stationaryCombustionDataGrid.setDataSource(stationaryCombustionInfoDS);
        stationaryCombustionDataGrid.fetchData();

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField fuelQuantityField = new ListGridField("fuelQuantity", "Fuel Quantity");
        fuelQuantityField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit this source");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove this source");
        removeButtonField.setWidth(100);

        stationaryCombustionDataGrid.setFields(sourceDescriptionField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField, removeButtonField);

        layout.addMember(stationaryCombustionDataGrid);

        IButton newStationaryCombustionButton = new IButton("Add new stationary combustion source");
        //newStationaryCombustionButton.setWidth(225);
        newStationaryCombustionButton.setIcon("/ibLGHGCalc/images/addIcon.jpg");

        newStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                stationaryCombustionForm.editNewRecord();
                stationaryCombustionFormWindow.show();
            }
        });

        IButton editStationaryCombustionButton = new IButton("Edit selected stationary combustion source");
        //editStationaryCombustionButton.setWidth(225);
        editStationaryCombustionButton.setIcon("/ibLGHGCalc/images/editIcon.gif");

        editStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                stationaryCombustionForm.editSelectedData(stationaryCombustionDataGrid);
                stationaryCombustionFormWindow.show();
            }
        });
        IButton removeStationaryCombustionButton = new IButton("Remove selected stationary combustion source");
        removeStationaryCombustionButton.setWidth(225);
        removeStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				      public void execute(Boolean value) {
					    if (value)
						    stationaryCombustionDataGrid.removeSelectedData();
				      }
			});

            }
        });

        HLayout gridButtonLayout = new HLayout(15);

        gridButtonLayout.addMember(newStationaryCombustionButton);
        gridButtonLayout.addMember(editStationaryCombustionButton);
        gridButtonLayout.addMember(removeStationaryCombustionButton);
        layout.addMember(gridButtonLayout);

//--Defining Stationary Combustion tab
        final Tab stationaryCombustionTab = new Tab("Stationary Combustion");
        stationaryCombustionTab.setPane(layout);

//---Adding Stationary Combustion tab to topTab
        topTabSet.addTab(stationaryCombustionTab);
        topLayout.setMembersMargin(15);
        topLayout.addMember(topTabSet);
        topLayout.setHeight("auto");
}



private void initStationaryCombustionEditForm() {

    stationaryCombustionForm.setCellPadding(5);
    stationaryCombustionForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    TextItem fuelSourceDescription = new TextItem("fuelSourceDescription");
    fuelSourceDescription.setTitle("Fuel Source Description");
    fuelSourceDescription.setSelectOnFocus(true);
    fuelSourceDescription.setWrapTitle(false);
    fuelSourceDescription.setDefaultValue("Source");

    final SelectItem fuelTypeItem = new SelectItem();
    fuelTypeItem.setName("fuelType");
    fuelTypeItem.setPickListWidth(310);
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(eF_StationaryCombustion_EPADS);

    fuelTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            stationaryCombustionForm.clearValue("fuelUnit");
        }
    });

    final SelectItem fuelUnitItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String fuelType = (String) fuelTypeItem.getValue();
            Criteria criteria = new Criteria("fuelType", fuelType);
            return criteria;
        }
    };

    fuelUnitItem.setName("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");
    fuelUnitItem.setPickListWidth(250);
    fuelUnitItem.setOptionDataSource(eF_StationaryCombustion_EPADS);

    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    stationaryCombustionForm.setItems(fuelSourceDescription,fuelTypeItem, fuelQuantityItem, fuelUnitItem,fuelUsedBeginDateItem,fuelUsedEndDateItem);

    saveButton.setTitle("SAVE");
    saveButton.setTooltip("Save this Project instance");
    saveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record stationaryCombustionFormRecord = stationaryCombustionForm.getValuesAsRecord();
        //Date beginDate = (Date)fuelUsedBeginDateItem.getValue();
        //Date endDate = (Date)fuelUsedEndDateItem.getValue();
        //stationaryCombustionFormRecord.setAttribute("fuelUsedBeginDate", beginDate);
        //stationaryCombustionFormRecord.setAttribute("fuelUsedEndDate", endDate);
        stationaryCombustionDataGrid.updateData(stationaryCombustionFormRecord);
        //stationaryCombustionForm.submitForm();
        stationaryCombustionFormWindow.hide();
      }
    });

    cancelButton.setTitle("CANCEL");
    cancelButton.setTooltip("Cancel");
    cancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        //stationaryCombustionForm.saveData();
        stationaryCombustionFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(cancelButton);
    buttons.addMember(saveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(stationaryCombustionForm);
    dialog.addMember(buttons);
    stationaryCombustionFormWindow.setShowShadow(true);
    stationaryCombustionFormWindow.setShowTitle(false);
    stationaryCombustionFormWindow.setIsModal(true);
    stationaryCombustionFormWindow.setPadding(20);
    stationaryCombustionFormWindow.setWidth(500);
    stationaryCombustionFormWindow.setHeight(350);
    stationaryCombustionFormWindow.setShowMinimizeButton(false);
    stationaryCombustionFormWindow.setShowCloseButton(true);
    stationaryCombustionFormWindow.setShowModalMask(true);
    stationaryCombustionFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //stationaryCombustionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    stationaryCombustionFormWindow.addItem(dialog);
}
private void fileUploadTab(){

    uploadForm.setEncoding(Encoding.MULTIPART);
    uploadForm.setAction(eFUploadFormSubmitAction);

    final UploadItem fileUpload = new UploadItem("fileUpload");
    fileUpload.setTitle("File");
    fileUpload.setWidth(300);

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
    }

}
