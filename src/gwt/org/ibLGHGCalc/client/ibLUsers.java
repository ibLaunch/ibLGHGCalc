package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.*;
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
import com.smartgwt.client.widgets.layout.*;
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
    private final HLayout mainLayout = new HLayout();
    private final OrganizationDS organizationDS = OrganizationDS.getInstance();
    private final DynamicForm organizationForm = new DynamicForm();
    private final DynamicForm selectOrganizationForm = new DynamicForm();

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
                    //editImg.setHeight100();
                    //editImg.setWidth100();

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
                    //button.setTitle("Remove");
                    button.setPrompt("Remove");
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

   mainLayout.setWidth100();
   mainLayout.setHeight100();

//-Header

    //final HLayout headerLayout = new HLayout();
    //Img launchImg = new Img("/ibLGHGCalc/images/launch_yourself.png");
    Img logoImg = new Img("/ibLGHGCalc/images/logo.gif");
/*
    VLayout launchImgLayout = new VLayout();
    launchImgLayout.addMember(launchImg);

    VLayout logoImgLayout = new VLayout();
    logoImgLayout.addMember(launchImg);

    headerLayout.addMember(launchImgLayout);
    headerLayout.addMember(logoImgLayout);

    topLayout.addMember(headerLayout);
*/

//--Defining sections

//    HLayout sectionHLayout = new HLayout();

//-Layout for Mid top mid section
    final VLayout topVLayout = new VLayout();
    final Label testLable = new Label("I am testing you");

    topVLayout.addChild(testLable);

//--- Left Sections...
    final SectionStack leftSectionStack = new SectionStack();
    leftSectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
    leftSectionStack.setWidth(225);
    leftSectionStack.setHeight(600);

    SectionStackSection reportSection = new SectionStackSection("Organization");
    reportSection.setID("organization");
    reportSection.setExpanded(true);
    //reportSection.addItem(logoImg);
//-temporry place for selecting adding organization

     organizationForm.setDataSource(organizationDS);


     final TextItem organizatioName = new TextItem("organizationName");
     organizatioName.setTitle("Organization Name");
     organizatioName.setSelectOnFocus(true);
     //organizationForm.setItems(organizatioName);

     IntegerItem organizationId = new IntegerItem();
     organizationId.setTitle("Organization Id");
     organizationId.setName("id");

     organizationForm.setFields(organizatioName,organizationId);
     reportSection.addItem(organizationForm);
     
     Button saveOrganiztionButton = new Button("Save");
     reportSection.addItem(saveOrganiztionButton);
     saveOrganiztionButton.addClickHandler(new ClickHandler(){
        public void onClick(ClickEvent event) {
		organizationForm.saveData();
            }
         });

//-- Org SelectItem
     final SelectItem organizationSelectItem = new SelectItem("id");
     organizationSelectItem.setTitle("Select Organization id");
     organizationSelectItem.setOptionDataSource(organizationDS);

     selectOrganizationForm.setFields(organizationSelectItem);
     reportSection.addItem(selectOrganizationForm);

     organizationSelectItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            //String orgNameString = (String) organizationSelectItem.getValue();
            //String orgNameString = (String) event.getValue();
            Integer orgId = (Integer) event.getValue();
            //Integer orgId = (Integer) event.();
            //organizationForm.getField("organizationName").setValueMap(organizationSelectItem.get(orgNameString));
            //Criteria populateOrganizationFormCriteria = selectOrganizationForm.getValuesAsCriteria();
            //SC.say("Organization Id + : "+ orgId);
            //organizationForm.setValue("organizatioName", orgNameString);
            //organizationForm.fetchData(populateOrganizationFormCriteria);

            //organizationForm.getField("id").setValue(orgId);

            Criteria fetchStationaryCombustionCriteria = new Criteria();
            //Integer organizationIdInteger =  (Integer) organizationForm.getField("id").getValue();
            fetchStationaryCombustionCriteria.addCriteria("organizationId", orgId);

            //stationaryCombustionDataGrid.fetchData(fetchStationaryCombustionCriteria);
            stationaryCombustionDataGrid.filterData(fetchStationaryCombustionCriteria);
            topVLayout.addChild(topTabSet);
        }
     });


// - end of temporary organization place.

    leftSectionStack.addSection(reportSection);

    SectionStackSection emissionsSection = new SectionStackSection("Emission Sources");
    emissionsSection.setID("emissionSources");
    emissionsSection.setExpanded(true);

    IButton stationaryCombustionButton = new IButton("Stationary combustion source");
    //newStationaryCombustionButton.setWidth(225);
    stationaryCombustionButton.setIcon("/ibLGHGCalc/images/addIcon.jpg");

    stationaryCombustionButton.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
            //topVLayout.removeChild(testLable);
           
           //Record organizationRecord = organizationForm.getValuesAsRecord();
           //stationaryCombustionDataGrid.fetchRelatedData(organizationRecord, organizationDS);
           
            //Criteria fetchStationaryCombustionCriteria = organizationForm.getValuesAsCriteria();

            
            Criteria fetchStationaryCombustionCriteria = new Criteria();
            Integer organizationIdInteger =  (Integer) organizationForm.getField("id").getValue();
            fetchStationaryCombustionCriteria.addCriteria("organizationId", organizationIdInteger);
            
            //stationaryCombustionDataGrid.fetchData(fetchStationaryCombustionCriteria);
            stationaryCombustionDataGrid.filterData(fetchStationaryCombustionCriteria);
            //SC.say("Organization Record  : " + organizationRecord);
            
            /*
            organizationForm.fetchData(organizationForm.getValuesAsCriteria(), new DSCallback() {
                @Override
                public void execute(DSResponse response, Object rawData,DSRequest request) {
                    stationaryCombustionDataGrid.fetchData(organizationForm.getValuesAsCriteria());
                }});
            */
            topVLayout.addChild(topTabSet);
        }
    });


    emissionsSection.addItem(stationaryCombustionButton);
    leftSectionStack.addSection(emissionsSection);

    mainLayout.addMember(leftSectionStack);

//--- Mid Sections...
/*
    final SectionStack midSectionStack = new SectionStack();
    midSectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
    midSectionStack.setWidth(800);
    midSectionStack.setHeight(600);

    SectionStackSection detailSection = new SectionStackSection("Stationary Combustion Details");
    detailSection.setID("stationaryCombustionD");
    detailSection.setExpanded(true);
    detailSection.addItem(topTabSet);
    midSectionStack.addSection(detailSection);

    SectionStackSection midLowSection = new SectionStackSection("Mid Low");
    midLowSection.setID("midLow");
    midLowSection.setExpanded(true);
    midLowSection.addItem(logoImg);
    midSectionStack.addSection(midLowSection);
*/
    mainLayout.addMember(topVLayout);

//-Defining tab set
    //topTabSet.setTabBarPosition(Side.TOP);
    topTabSet.setTabBarAlign(Side.LEFT);
    topTabSet.setWidth100();
    topTabSet.setHeight(600);

//--Defining Stationary Combustion tab layout
    stationaryCombustionTab();

//--Calling form to Add New Record to Stationary Combustion Source
    initStationaryCombustionEditForm();

//--Defining File Upload  tab
    fileUploadTab();

//--add tab set to top Layout
    //topLayout.setMembersMargin(15);
    //topLayout.addMember(sectionHLayout);
    //topLayout.setHeight("auto");

//--Add topLayout to "main"
    RootPanel.get("main").add(mainLayout);

    //topLayout.draw();
}

private void stationaryCombustionTab() {

     VLayout layout = new VLayout(15);

        Label stationaryCombustionDataLabel = new Label("Current stationary combustion sources");
        stationaryCombustionDataLabel.setHeight(20);
        layout.addMember(stationaryCombustionDataLabel);

//--ListGrid setup
        stationaryCombustionDataGrid.setWidth("100%");
        stationaryCombustionDataGrid.setHeight(300);
        stationaryCombustionDataGrid.setShowRecordComponents(true);
        stationaryCombustionDataGrid.setShowRecordComponentsByCell(true);
        //stationaryCombustionDataGrid.setCanRemoveRecords(true);
        //stationaryCombustionDataGrid.setShowAllRecords(true);
        stationaryCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        stationaryCombustionDataGrid.setDataSource(stationaryCombustionInfoDS);

//--Only fetch data that is related to THE organization
        //Record organizationRecord= organizationForm.getValuesAsRecord();
        //stationaryCombustionDataGrid.fetchRelatedData(organizationRecord,organizationDS);

        //stationaryCombustionDataGrid.fetchData();

//        ListGridField organizationNameField = new ListGridField("name", "Organization Name");
//        organizationNameField.setType(ListGridFieldType.TEXT);

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);

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

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        removeButtonField.setWidth(100);

        stationaryCombustionDataGrid.setFields(sourceDescriptionField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField,editButtonField, removeButtonField);

        layout.addMember(stationaryCombustionDataGrid);

        IButton newStationaryCombustionButton = new IButton("Add new stationary combustion source");
        newStationaryCombustionButton.setWidth100();
        newStationaryCombustionButton.setIcon("/ibLGHGCalc/images/addIcon.jpg");

        newStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                stationaryCombustionForm.editNewRecord();
                stationaryCombustionFormWindow.show();
            }
        });

/*
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
*/
        HLayout gridButtonLayout = new HLayout(15);

        gridButtonLayout.addMember(newStationaryCombustionButton);
//        gridButtonLayout.addMember(editStationaryCombustionButton);
//        gridButtonLayout.addMember(removeStationaryCombustionButton);
        layout.addMember(gridButtonLayout);

//--Defining Stationary Combustion tab
        final Tab stationaryCombustionTab = new Tab("Stationary Combustion");
        stationaryCombustionTab.setPane(layout);

//---Adding Stationary Combustion tab to topTab
        topTabSet.addTab(stationaryCombustionTab);

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

    TextItem organizationName = new TextItem();
//    Integer currentOrganizationId = (Integer)organizationForm.getItem("id").getValue();
    organizationName.setName("organizationName");
//    organizationId.setValue(currentOrganizationId);

    stationaryCombustionForm.setItems(organizationName, fuelSourceDescription,fuelTypeItem, fuelQuantityItem, fuelUnitItem,fuelUsedBeginDateItem,fuelUsedEndDateItem);

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
