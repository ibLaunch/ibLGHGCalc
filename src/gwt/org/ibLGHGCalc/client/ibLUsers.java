package org.ibLGHGCalc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.*;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.*;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

//import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
//import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
//import com.smartgwt.client.widgets.form.fields.LinkItem;
//import com.smartgwt.client.widgets.form.fields.;

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
import com.smartgwt.client.widgets.menu.*;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import java.util.Date;
import java.util.HashMap;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ibLUsers implements EntryPoint {
    /**
     * This is the entry point method.
     */

    private static final int NORTH_HEIGHT = 117-62; // MASTHEAD_HEIGHT + APPLICATION_MENU_HEIGHT + USER_ORGANIZATION_HEADER_HEIGHT
    private VLayout mainVLayout; // This is main Layout
    private HLayout northHLayout;  // This is for header purpose only

    private static final int LEFT_SECTION_BUTTON_WIDTH = 180;
    private static final int LEFT_SECTION_BUTTON_HEIGHT = 25;
    private static final String NUMBER_FORMAT_1 ="#,##0.00";

//-- Listgrid Field Widths
    //private static final int SOURCE_DESCRIPTION_WIDTH = 200;
    private static final String SOURCE_DESCRIPTION_WIDTH = "20%";
    private static final String VEHICLE_TYPE_FIELD_WIDTH = "15%";
    private static final String FUEL_TYPE_FIELD_WIDTH = "20%";
    private static final String FUEL_QUANTITY_FIELD_WIDTH = "10%";
    private static final String FUEL_UNIT_FIELD_WIDTH = "5%";
    private static final String BEGIN_DATE_FIELD_WIDTH = "12%";
    private static final String END_DATE_FIELD_WIDTH = "12%";
    private static final String EDIT_BUTTON_FIELD_WIDTH = "5%";
    private static final String REMOVE_BUTTON_FIELD_WIDTH = "9%";

    //-- purchased Electricity fields width
    private static final String eGRIDSubregion_FIELD_WIDTH = "15%";
    private static final String purchasedElectricityUnit_FIELD_WIDTH = "5%";

    //-- purchased Electricity fields width
    private static final String boilerEfficiencyPercent_WIDTH = "5%";

    //-- purchased Electricity fields width
    //private static final String VEHICLE_TYPE_FIELD_WIDTH = "20%";


    private static final String PROGRAM_TYPE_FIELD_WIDTH = "15%";
    private static final String TOTAL_EMISSIONS_FIELD_WIDTH = "20%";
    private static final String REPORT_FILE_NAME_FIELD_WIDTH = "20%";
    private static final String GAS_TYPE_FIELD_WIDTH = "20%";
    private static final String EQUIPMENT_TYPE_FIELD_WIDTH = "20%";

    private final HLayout mainHLayout = new HLayout();
    private final VLayout middleVLayout = new VLayout();
    private final HLayout topMenuHLayout = new HLayout();
    private final HLayout middleTopHLayout = new HLayout();
    private final HLayout middleMiddleHLayout = new HLayout();
    private final HLayout middleBottomHLayout = new HLayout();
    private final SectionStack leftSectionStack = new SectionStack();
    private final VLayout rightVLayout = new VLayout();

    private String ADD_ICON_IMAGE = "addIcon.jpg";
    private String ADD_NEW_SOURCE_TEXT = "Add new source";
    private int ADD_BUTTON_WIDTH = 150;
    private int LABEL_HEIGHT = 15;

    private DetailViewerField[] detailViewerFieldList;
    
//- Data upload Options
    private String[] epaDataLoadOptions = {
				"Stationary Combustion Info",
				"Purchased Electricity",
				"Purchased Steam",
				"Employee Business Travel - By Vehicle",
				"Employee Business Travel - By Bus",
				"Employee Business Travel - By Rail",
				"Employee Business Travel - By Air",
				"Employee Commuting - By Vehicle",
				"Employee Commuting - By Rail",
				"Employee Commuting - By Bus",
				"Product Transport - By Vehicle",
				"Product Transport - By Heavy Duty Trucks",
				"Product Transport - By Rail",
				"Product Transport - By Water or Air",
				"Mobile Combustion Info",
				"Refridgeration Air Conditioning - Company-Wide Material Balance Method",
				"Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method",
				"Refridgeration Air Conditioning - Source Level Screening Method",
				"Fire Suppression - Company-Wide Material Balance Method",
				"Fire Suppression - Company-Wide Simplified Material Balance Method",
				"Fire Suppression - Source Level Screening Method"
			      };

    private String[] emissionFactorsLoadOptions = {
				"EF - EPA Stationary Combustion",
				"EF - EPA Purchased Electricity",
				"EF - EPA Purchased Steam",
				"EF - EPA Waste Stream Combustion",
				"EF - EPA Vehicle Type",
				"EF - EPA Rail Type",
				"EF - EPA Bus Type",
				"EF - EPA Air Travel Type",
				"EF - EPA Product Transport Vehicle Type",
				"EF - EPA Product Transport Type",
				"Mobile Combustion Info - EF_CO2_MobileCombustion_EPA",
				"Mobile Combustion Info - VehicleType_EPA",
				"Mobile Combustion Info - EF_CH4N2O_MobileCombustionGasoline_EPA",
				"Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonGasoline_EPA",
				"Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonHighway_EPA",
				"Mobile Combustion Info - EF_CH4N2O_MobileCombustion_EPA",
				"RefridgerationAirConditioning - GWP_RefridgerationAirConditioning_EPA",
				"RefridgerationAirConditioning - EquipmentCapacityRange_EPA"
			      };
//-- file upload
    private VStack fileUploadLayout = new VStack();
    private final SelectItem fileTypeItem = new SelectItem("fileType");
    private final HiddenItem fileUploadOrganizatonID = new HiddenItem("organizationId");
    
//-- User Organization Layot
    //private static UserOrganizationHeader userOrganizationHeader = new UserOrganizationHeader();

//-- Validators
    public static Date currentInventoryBeginDateMin;
    public static Date currentInventoryEndDateMax;
    public static Integer orgId; //--Remove this, I don't believe this is used.
    public static Integer mainOrganizationId;// = (Integer) UserOrganizationHeader.organizationSelectForm.getField("id").getValue();

    public static final DateRangeValidator validateDateRange = new DateRangeValidator();
    public static final IsFloatValidator floatValidator = new IsFloatValidator();
    public static final FloatPrecisionValidator floatPrecisionValidator = new FloatPrecisionValidator();

    private final TabSet tabSet = new TabSet();
    //private final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd-MM-yyyy"+" "+"h:mm:a" );
    private final DateTimeFormat displayDateFormatter = DateTimeFormat.getFormat("MMM d, yyyy");
    private final DateTimeFormat inputDateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ss");
    //public final static DateDisplayFormat detailViewerDateFormat = DateDisplayFormat.getFormat("MMM d, yyyy");

    public final NumberFormat floatSimpleFormat = NumberFormat.getFormat("#,##0.00");
    public static SimpleType floatSimpleType = new SimpleType("floatSimpleTypeID", FieldType.FLOAT);

    //private final String eFUploadFormSubmitAction = "EF_StationaryCombustion_EPA";
    private final String eFUploadFormSubmitAction = "fileUpload";
    private final DynamicForm uploadForm = new DynamicForm();

    //private final FileUploadDS fileUploadDS = FileUploadDS.getInstance();

//--Stationary Combustions
    private final VLayout stationaryCombustionLayout = new VLayout();
    private final TabSet stationaryCombustionTabSet = new TabSet();

    private final Window stationaryCombustionFormWindow = new Window();
    private final DynamicForm stationaryCombustionForm = new DynamicForm();
    private final StationaryCombustionInfoDS stationaryCombustionInfoDS = StationaryCombustionInfoDS.getInstance();
    private final EF_StationaryCombustion_EPADS eF_StationaryCombustion_EPADS = EF_StationaryCombustion_EPADS.getInstance();
   
//--Mobile Combustion
    private final TabSet mobileCombustionTabSet = new TabSet();
    private final VLayout mobileCombustionLayout = new VLayout(15);
    
    private final Window mobileCombustionFormWindow = new Window();
    private final DynamicForm mobileCombustionForm = new DynamicForm();
    private final MobileCombustionInfoDS mobileCombustionInfoDS = MobileCombustionInfoDS.getInstance();
    private final EF_CH4N2O_MobileCombustion_EPADS eF_CH4N2O_MobileCombustion_EPADS = EF_CH4N2O_MobileCombustion_EPADS.getInstance();
    private final VehicleType_EPADS vehicleType_EPADS = VehicleType_EPADS.getInstance();

//--RefridgerationAirConditioningInfo
    private final TabSet refridgerationAirConditioningTabSet = new TabSet();
    private final VLayout refridgerationAirConditioningLayout = new VLayout(15);

    private final RefridgerationAirConditioningInfoDS refridgerationAirConditioningInfoDS = RefridgerationAirConditioningInfoDS.getInstance();
    
//-- refridgerationAirConditioning For Material balance method
    private final Window refridgerationAirConditioningFormWindow_1 = new Window();
    private final DynamicForm refridgerationAirConditioningForm_1 = new DynamicForm();
    private final IblListGrid refridgerationAirConditioningDataGrid_1 = new IblListGrid(refridgerationAirConditioningInfoDS, 
                                                                                        refridgerationAirConditioningForm_1,
                                                                                        refridgerationAirConditioningFormWindow_1);
    //-- For refridgerationAirConditioning Simplified Material balance method
    private final Window refridgerationAirConditioningFormWindow_2 = new Window();
    private final DynamicForm refridgerationAirConditioningForm_2 = new DynamicForm();
    private final IblListGrid refridgerationAirConditioningDataGrid_2 = new IblListGrid(refridgerationAirConditioningInfoDS, 
                                                                                        refridgerationAirConditioningForm_2,
                                                                                        refridgerationAirConditioningFormWindow_2 );
//-- For refridgerationAirConditioning Screening method
    private final Window refridgerationAirConditioningFormWindow_3 = new Window();
    private final DynamicForm refridgerationAirConditioningForm_3 = new DynamicForm();
    private final IblListGrid refridgerationAirConditioningDataGrid_3 = new IblListGrid(refridgerationAirConditioningInfoDS, 
                                                                                        refridgerationAirConditioningForm_3,
                                                                                        refridgerationAirConditioningFormWindow_3 );
    
    /*private final ListGrid refridgerationAirConditioningDataGrid_3 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            refridgerationAirConditioningForm_3.editRecord(record);
                            refridgerationAirConditioningFormWindow_3.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
                                   public void execute(Boolean value) {
                                       if (value){
                                           //refridgerationAirConditioningDataGrid.removeSelectedData();
                                           refridgerationAirConditioningDataGrid_3.removeData(record);
                                       }
                                   }
                            });

                        }
                    });
                    return button;
                } else {
                    return null;
                }

            }
    };*/

//-- fireSuppression
    private final TabSet fireSuppressionTabSet = new TabSet();
    private final VLayout fireSuppressionLayout = new VLayout(15);

//-- fireSuppression For Material balance method
    private final Window fireSuppressionFormWindow_1 = new Window();
    private final DynamicForm fireSuppressionForm_1 = new DynamicForm();
    private final IblListGrid fireSuppressionDataGrid_1 = new IblListGrid(refridgerationAirConditioningInfoDS,
                                                                                    fireSuppressionForm_1,
                                                                                    fireSuppressionFormWindow_1);

    /*
    private final ListGrid fireSuppressionDataGrid_1 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            fireSuppressionForm_1.editRecord(record);
                            fireSuppressionFormWindow_1.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       public void execute(Boolean value) {
					   if (value){
					       //fireSuppressionDataGrid.removeSelectedData();
                                               fireSuppressionDataGrid_1.removeData(record);
                                           }
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
     *
     */
//-- For fireSuppression Simplified Material balance method
    private final Window fireSuppressionFormWindow_2 = new Window();
    private final DynamicForm fireSuppressionForm_2 = new DynamicForm();
    private final IblListGrid fireSuppressionDataGrid_2 = new IblListGrid(refridgerationAirConditioningInfoDS,
                                                                                    fireSuppressionForm_2,
                                                                                    fireSuppressionFormWindow_2);

    /*
    private final ListGrid fireSuppressionDataGrid_2 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            fireSuppressionForm_2.editRecord(record);
                            fireSuppressionFormWindow_2.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       public void execute(Boolean value) {
					   if (value){
					       //fireSuppressionDataGrid.removeSelectedData();
                                               fireSuppressionDataGrid_2.removeData(record);
                                           }
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
     *
     */

//-- For fireSuppression Screening method    
    private final Window fireSuppressionFormWindow_3 = new Window();
    private final DynamicForm fireSuppressionForm_3 = new DynamicForm();
    private final IblListGrid fireSuppressionDataGrid_3 = new IblListGrid(refridgerationAirConditioningInfoDS,
                                                                                    fireSuppressionForm_3,
                                                                                    fireSuppressionFormWindow_3);
    /*
    private final ListGrid fireSuppressionDataGrid_3 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            fireSuppressionForm_3.editRecord(record);
                            fireSuppressionFormWindow_3.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       public void execute(Boolean value) {
					   if (value){
					       //fireSuppressionDataGrid.removeSelectedData();
                                               fireSuppressionDataGrid_3.removeData(record);
                                           }
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
    */
    
    private final GWP_RefridgerationAirConditioning_EPADS gWP_RefridgerationAirConditioning_EPADS = GWP_RefridgerationAirConditioning_EPADS.getInstance();
    private final EquipmentCapacityRange_EPADS equipmentCapacityRange_EPADS = EquipmentCapacityRange_EPADS.getInstance();

//--Organization Related Forms
    private final OrganizationDS organizationDS = OrganizationDS.getInstance();
    private final DynamicForm organizationForm = new DynamicForm();
    private final DynamicForm organizationProfileForm = new DynamicForm();
    private final VLayout organizationProfileVLayout = new VLayout();
    //private final DynamicForm organizationSelectForm = new DynamicForm();
    private final DynamicForm organizationInventoryYearForm = new DynamicForm();
    private final VLayout organizationInventoryYearVLayout = new VLayout();

    private final Window detailViewerWindow = new Window();

    //private final HLayout emissionsSummaryLayout = new HLayout();
    private final DynamicForm emissionsSummaryInputForm = new DynamicForm();
    //private final ListGrid emissionsSummaryDataGrid = new ListGrid();
    private final DetailViewer emissionsSummaryDetailViewer = new DetailViewer();
    private final EmissionsSummaryDS emissionsSummaryDS = EmissionsSummaryDS.getInstance();
    private final VLayout emissionsSummaryInputVLayout = new VLayout();
    private final VLayout emissionsSummaryVLayout = new VLayout();
    private final VLayout detailViewerVLayout = new VLayout();
    private final Label emissionsSummaryDetailViewerLabel = new Label("Emission Details:");
    //private final ListGrid emissionsSummaryDataGrid = new ListGrid();
    
    //private final ListGrid emissionsSummaryDataGrid = new IblReportListGrid(emissionsSummaryDS);
/*
    private final ListGrid emissionsSummaryDataGrid = new ListGrid()
        {
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
                final String fieldName = this.getFieldName(colNum);
                if (fieldName.equals("downloadReportField")) {
                    String fileName = record.getAttribute("reportFileName");

		    Label reportFileNameLabel = getReportLink(fileName, new ClickHandler() {
		       @Override
		       public void onClick(ClickEvent event) {

	                    Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                            String emissionsSummaryReportId = (String)record.getAttribute("id");
                            //SC.say("I am in createRecord Overide");

                            String url = "/ibLGHGCalc/reports?organizationId="+orgId+",emissionsSummaryReportId="+emissionsSummaryReportId;
                            //String url = "/ibLGHGCalc/reports?organizationId={$orgId},emissionsSummaryReportId={$emissionsSummaryReportId}";
                            SC.say("URL is---"+url);
                            com.google.gwt.user.client.Window.open(url,null,null);
		       }
		    });
                    return reportFileNameLabel;
                } else {
                    return null;
                }

            }
        };
*/
  
//-- Below works!!
    private final ListGrid emissionsSummaryDataGrid = new ListGrid() ;
    /*
        {
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
                final String fieldName = this.getFieldName(colNum);
                if (fieldName.equals("downloadReportField")) {
                    //Button viewReportButton = new Button();
                    Button viewReportButton = new Button();
                    //viewReportButton.setAutoFit(Boolean.TRUE);
                    String reportFileName = (String)record.getAttribute("reportFileName");
                    viewReportButton.setTitle(reportFileName);
                    //viewReportButton.setPosition(Positioning.ABSOLUTE);

                    HLayout recordCanvas = new HLayout(3);
                    recordCanvas.setHeight(22);
                    recordCanvas.setAlign(Alignment.CENTER);
                    recordCanvas.setSnapTo("T");
                    recordCanvas.setSnapToGrid(true);

                    //viewReportButton.setSnapTo("T");
                    //viewReportButton.setSnapToGrid(true);

                    viewReportButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
	                    Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                            //String fileName = record.getAttribute("reportFileName");
                            String emissionsSummaryReportId = (String)record.getAttribute("id");                            
                            String url = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                            com.google.gwt.user.client.Window.open(url,null,null);
                        }
                    });
                    recordCanvas.addMember(viewReportButton);
                    return recordCanvas;
                    //return viewReportButton;
                } else {
                    return null;
                }
                
            }
        };
     */
/*
//-- Below works!!
private final ListGrid emissionsSummaryDataGrid = new ListGrid()
        {
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
                final String fieldName = this.getFieldName(colNum);

                //SC.say("I am in createRecord Overide");
                if (fieldName.equals("downloadReportField")) {
                    //Button viewReportButton = new Button();
                    Button viewReportButton = new Button();
                    viewReportButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
	                    Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                            //String fileName = record.getAttribute("reportFileName");
                            String emissionsSummaryReportId = (String)record.getAttribute("id");
                            //String url = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                            //com.google.gwt.user.client.Window.open(url,null,null);

   	                    HTMLPane htmlPane = new HTMLPane();
			    htmlPane.setHeight100();
			    htmlPane.setWidth100();
			    htmlPane.setContentsURL("/ibLGHGCalc/reports");
                            //htmlPane.setContentsURL(url);

                            HashMap urlParams = new HashMap();
                            urlParams.put("organizationId", orgId);
                            //urlParams.put("fileName", fileName);
                            urlParams.put("emissionsSummaryReportId", emissionsSummaryReportId);
                            htmlPane.setContentsURLParams(urlParams);
                            htmlPane.setContentsType(ContentsType.PAGE);
                            //htmlPane.show();

                            Window reportWindow = new Window();
                            reportWindow.setWidth("50%");
                            reportWindow.setHeight("70%");
                            reportWindow.addItem(htmlPane);
                            reportWindow.show();

                            //HLayout newHLayout = new HLayout();
                            //newHLayout.addChild(htmlPane);
                            //middleBottomHLayout.addChild(htmlPane);
                            //newHLayout.show();
                        }
                    });
                    return viewReportButton;
                } else {
                    return null;
                }

            }
        };
*/

/*
    {
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
                String fieldName = this.getFieldName(colNum);
                if (fieldName.equals("reportFileNameField")) {
                    String orgName = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
                    ListGridField reportFileNameField = this.getField(colNum);
                    reportFileNameField.setLinkURLPrefix("Reports/"+orgId+"/");
                    return reportFileNameField;
                    //return null;
                }else {
                    return null;
                }
            }
    };
*/    
    private final IblListGrid stationaryCombustionDataGrid = new IblListGrid(stationaryCombustionInfoDS, stationaryCombustionForm, stationaryCombustionFormWindow );

    private final ListGrid stationaryCombustionDataGrid_2 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //stationaryCombustionForm.editSelectedData(stationaryCombustionDataGrid);
                            stationaryCombustionForm.editRecord(record);
                            stationaryCombustionFormWindow.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       public void execute(Boolean value) {
					   if (value){
					       //stationaryCombustionDataGrid.removeSelectedData();
                                               stationaryCombustionDataGrid.removeData(record);
                                           }
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

    private final IblListGrid mobileCombustionDataGrid = new IblListGrid(mobileCombustionInfoDS, mobileCombustionForm, mobileCombustionFormWindow);

    private final ListGrid mobileCombustionDataGrid_2 = new ListGrid(){

            @Override
            protected Canvas getCellHoverComponent(Record record, Integer rowNum, Integer colNum) {
                DetailViewer detailViewer = new DetailViewer();
                detailViewer.setWidth(400);
                detailViewer.setUseAllDataSourceFields(true);
                //detailViewer.viewSelectedData(mobileCombustionDataGrid);
                detailViewer.setDataSource(mobileCombustionInfoDS);
                
                Criteria criteria = new Criteria();
                criteria.addCriteria("id", record.getAttribute("id"));
                detailViewer.fetchData(criteria);                
                return detailViewer;
            }

            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);
                //HLayout recordCanvas = new HLayout();
                //recordCanvas.setHeight(22);
                //recordCanvas.setAlign(Alignment.CENTER);

                if (fieldName.equals("editButtonField")) {
                    
                    EditImageButton editImg = new EditImageButton();
                    //IButton editImg = new IButton();
                    //editImg.set
                    //editImg.setWidth(10);
                    //editImg.setContents("Edit");
                    //editImg.setTitle("Edit");
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //mobileCombustionForm.editSelectedData(mobileCombustionDataGrid);
                            mobileCombustionForm.editRecord(record);
                            //mobileCombustionForm.editNewRecord();
                            mobileCombustionFormWindow.show();
                        }
                    });
                    //recordCanvas.addMember(editImg);
                    //return recordCanvas;
                    return editImg;                    
                    //return null;                    
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    //IButton button = new IButton();
                    //button.setWidth(10);
                    button.addClickHandler(new ClickHandler() {
                       public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       				public void execute(Boolean value) {
					   					if (value){
					   					    //mobileCombustionDataGrid.removeSelectedData();
                       			             mobileCombustionDataGrid.removeData(record);
                       		            }
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

//-- Generic Fields-------------------begin
    private final List<ListGridField> mobileCombustionListGridFields = new ArrayList<ListGridField>();
    private final List<FormItem> mobileCombustionFormItems = new ArrayList<FormItem>();
    private final List<ListGridField> stationaryCombustionListGridFields = new ArrayList<ListGridField>();
    private final List<FormItem> stationaryCombustionFormItems = new ArrayList<FormItem>();
    
    private final DynamicForm dataForm = new DynamicForm();
    private final Window dataFormWindow = new Window();
    private RestDataSource dataDS = new RestDataSource();
    private final VLayout dataLayout = new VLayout();
    
//--Generic Listgrid to be used with all components
    private final ListGrid dataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);
                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //mobileCombustionForm.editSelectedData(mobileCombustionDataGrid);
                            dataForm.editRecord(record);
                            dataFormWindow.show();
                        }
                    });

                    return editImg;

                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                       public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       				public void execute(Boolean value) {
					   					if (value){
					   					    //mobileCombustionDataGrid.removeSelectedData();
                       			             dataGrid.removeData(record);
                       		            }
				       				}
			        	});

                       }
                    });
                    return button;
                } else {
                    return null;
                }

            }
/*
            @Override
            protected Canvas getCellHoverComponent(Record record, Integer rowNum, Integer colNum) {
                DetailViewer detailViewer = new DetailViewer();
                detailViewer.setWidth(400);
                detailViewer.setDataSource(dataDS);
                //detailViewer.viewSelectedData(dataGrid);
                Criteria criteria = new Criteria();
                criteria.addCriteria("id", record.getAttribute("id"));
                detailViewer.fetchData(criteria);
                //detailViewer.
                return detailViewer;
            }
*/
    };

//-- Generic Fields-------------------end

//--Purchased Electricity
    private final TabSet purchasedElectricityTabSet = new TabSet();
    private final VLayout purchasedElectricityLayout = new VLayout(15);

    private final Window purchasedElectricityFormWindow = new Window();
    private final DynamicForm purchasedElectricityForm = new DynamicForm();
    private final PurchasedElectricityInfoDS purchasedElectricityInfoDS = PurchasedElectricityInfoDS.getInstance();
    private final EF_PurchasedElectricity_EPADS theEF_PurchasedElectricity_EPADS = EF_PurchasedElectricity_EPADS.getInstance();

    private final IblListGrid purchasedElectricityDataGrid = new IblListGrid(purchasedElectricityInfoDS, purchasedElectricityForm, purchasedElectricityFormWindow);

    /*
    private final ListGrid purchasedElectricityDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //purchasedElectricityForm.editSelectedData(purchasedElectricityDataGrid);
                            purchasedElectricityForm.editRecord(record);
                            purchasedElectricityFormWindow.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                       public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       				public void execute(Boolean value) {
					   					if (value){
					   					    //purchasedElectricityDataGrid.removeSelectedData();
                       			             purchasedElectricityDataGrid.removeData(record);
                       		            }
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
    */

//--Purchased Steam
    private final TabSet purchasedSteamTabSet = new TabSet();
    private final VLayout purchasedSteamLayout = new VLayout(15);

    private final Window purchasedSteamFormWindow = new Window();
    private final DynamicForm purchasedSteamForm = new DynamicForm();
    private final PurchasedSteamInfoDS purchasedSteamInfoDS = PurchasedSteamInfoDS.getInstance();
    private final EF_PurchasedSteam_EPADS theEF_PurchasedSteam_EPADS = EF_PurchasedSteam_EPADS.getInstance();
    private final IblListGrid purchasedSteamDataGrid = new IblListGrid(purchasedSteamInfoDS, purchasedSteamForm, purchasedSteamFormWindow);

/*
    private final ListGrid purchasedSteamDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //purchasedSteamForm.editSelectedData(purchasedSteamDataGrid);
                            purchasedSteamForm.editRecord(record);
                            purchasedSteamFormWindow.show();
                        }
                    });

                    return editImg;
                } else if (fieldName.equals("removeButtonField")) {
                    RemoveIButton button = new RemoveIButton();
                    button.addClickHandler(new ClickHandler() {
                       public void onClick(ClickEvent event) {
                                SC.confirm("ibL GHG Calc","Are you sure?", new BooleanCallback() {
				       				public void execute(Boolean value) {
					   					if (value){
					   					    //purchasedSteamDataGrid.removeSelectedData();
                       			             purchasedSteamDataGrid.removeData(record);
                       		            }
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
*/
//--Optinal Source DS
    private final OptionalSourceInfoDS optionalSourceInfoDS = OptionalSourceInfoDS.getInstance();

//--employee Business Travel
    private final TabSet employeeBusinessTravelTabSet = new TabSet();
    private final VLayout employeeBusinessTravelLayout = new VLayout(15);
//--employeeBusinessTravelByVehicle
    private final Window employeeBusinessTravelByVehicleFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByVehicleForm = new DynamicForm();
    private final EF_VehicleType_EPADS theEF_VehicleType_EPADS = EF_VehicleType_EPADS.getInstance();
    private final IblListGrid employeeBusinessTravelByVehicleDataGrid = new IblListGrid(optionalSourceInfoDS, employeeBusinessTravelByVehicleForm, employeeBusinessTravelByVehicleFormWindow);
//--employeeBusinessTravelByRail
    private final Window employeeBusinessTravelByRailFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByRailForm = new DynamicForm();
    private final EF_RailType_EPADS theEF_RailType_EPADS = EF_RailType_EPADS.getInstance();
    private final IblListGrid employeeBusinessTravelByRailDataGrid = new IblListGrid(optionalSourceInfoDS, employeeBusinessTravelByRailForm, employeeBusinessTravelByRailFormWindow);

//--employeeBusinessTravelByBus
    private final Window employeeBusinessTravelByBusFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByBusForm = new DynamicForm();
    private final EF_BusType_EPADS theEF_BusType_EPADS = EF_BusType_EPADS.getInstance();
    private final IblListGrid employeeBusinessTravelByBusDataGrid = new IblListGrid(optionalSourceInfoDS, employeeBusinessTravelByBusForm, employeeBusinessTravelByBusFormWindow);

//--employeeBusinessTravelByAir
    private final Window employeeBusinessTravelByAirFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByAirForm = new DynamicForm();
    private final EF_AirTravelType_EPADS theEF_AirTravelType_EPADS = EF_AirTravelType_EPADS.getInstance();
    private final IblListGrid employeeBusinessTravelByAirDataGrid = new IblListGrid(optionalSourceInfoDS, employeeBusinessTravelByAirForm, employeeBusinessTravelByAirFormWindow);

//--employeeCommuting
    private final TabSet employeeCommutingTabSet = new TabSet();
    private final VLayout employeeCommutingLayout = new VLayout(15);

//--employeeCommutingByVehicle
    private final Window employeeCommutingByVehicleFormWindow = new Window();
    private final DynamicForm employeeCommutingByVehicleForm = new DynamicForm();
    //private final EF_VType_EPADS theEF_VehicleTypeType_EPADS = EF_VehicleTypeType_EPADS.getInstance();
    private final IblListGrid employeeCommutingByVehicleDataGrid = new IblListGrid(optionalSourceInfoDS, employeeCommutingByVehicleForm, employeeCommutingByVehicleFormWindow);

//--employeeCommutingByRail
    private final Window employeeCommutingByRailFormWindow = new Window();
    private final DynamicForm employeeCommutingByRailForm = new DynamicForm();
    //private final EF_VehicleType_EPADS theEF_RailType_EPADS = EF_RailType_EPADS.getInstance();
    private final IblListGrid employeeCommutingByRailDataGrid = new IblListGrid(optionalSourceInfoDS, employeeCommutingByRailForm, employeeCommutingByRailFormWindow);

//--employeeCommutingByBus
    private final Window employeeCommutingByBusFormWindow = new Window();
    private final DynamicForm employeeCommutingByBusForm = new DynamicForm();
    //private final EF_VehicleType_EPADS theEF_BusType_EPADS = EF_BusType_EPADS.getInstance();
    private final IblListGrid employeeCommutingByBusDataGrid = new IblListGrid(optionalSourceInfoDS, employeeCommutingByBusForm, employeeCommutingByBusFormWindow);

//--product Transport
    private final TabSet productTransportTabSet = new TabSet();
    private final VLayout productTransportLayout = new VLayout(15);

//--productTransportByVehicle
    private final Window productTransportByVehicleFormWindow = new Window();
    private final DynamicForm productTransportByVehicleForm = new DynamicForm();
    private final EF_ProductTransport_VehicleType_EPADS theEF_ProductTransport_VehicleType_EPADS = EF_ProductTransport_VehicleType_EPADS.getInstance();
    private final IblListGrid productTransportByVehicleDataGrid = new IblListGrid(optionalSourceInfoDS, productTransportByVehicleForm, productTransportByVehicleFormWindow);


//--productTransportByHeavyDutyTrucks
    private final Window productTransportByHeavyDutyTrucksFormWindow = new Window();
    private final DynamicForm productTransportByHeavyDutyTrucksForm = new DynamicForm();
    private final EF_ProductTransportType_EPADS theEF_ProductTransportType_EPADS= EF_ProductTransportType_EPADS.getInstance();
    private final IblListGrid productTransportByHeavyDutyTrucksDataGrid = new IblListGrid(optionalSourceInfoDS, productTransportByHeavyDutyTrucksForm, productTransportByHeavyDutyTrucksFormWindow);

//--productTransportByRail
    private final Window productTransportByRailFormWindow = new Window();
    private final DynamicForm productTransportByRailForm = new DynamicForm();
    //private final EF_ProductTransportType_EPADS theEF_ProductTransportType_EPADS= EF_ProductTransportType_EPADS.getInstance();
    private final IblListGrid productTransportByRailDataGrid = new IblListGrid(optionalSourceInfoDS, productTransportByRailForm, productTransportByRailFormWindow);

//--productTransportByWaterAir
    private final Window productTransportByWaterAirFormWindow = new Window();
    private final DynamicForm productTransportByWaterAirForm = new DynamicForm();
    //private final EF_ProductTransportType_EPADS theEF_ProductTransportType_EPADS= EF_ProductTransportType_EPADS.getInstance();
    private final IblListGrid productTransportByWaterAirDataGrid = new IblListGrid(optionalSourceInfoDS, productTransportByWaterAirForm, productTransportByWaterAirFormWindow);

//--wasteStreamCombustion
    private final VLayout wasteStreamCombustionLayout = new VLayout(15);
    private final TabSet wasteStreamCombustionTabSet = new TabSet();

    private final Window wasteStreamCombustionFormWindow = new Window();
    private final DynamicForm wasteStreamCombustionForm = new DynamicForm();
    private final WasteStreamCombustionInfoDS wasteStreamCombustionInfoDS= WasteStreamCombustionInfoDS.getInstance();
    //private final ListGrid wasteStreamCombustionDataGrid = new ListGrid();
    
    private final IblListGrid wasteStreamCombustionDataGrid = new IblListGrid(wasteStreamCombustionInfoDS, wasteStreamCombustionForm, wasteStreamCombustionFormWindow);


    private TabSelectedHandler refridgerationTabSelectedHandler = new TabSelectedHandler() {
        public void onTabSelected(TabSelectedEvent event) {
            //displayEmissionSourceInfo(event.getTab().getTitle());
            displayEmissionSourceInfo("Refridgeration and Air Conditioning Sources");
            GWT.log("TabSelectedHandler.onTabSelected:" + event.getTab().getTitle(), null);
        }
    };

    private TabSelectedHandler fireSuppressionTabSelectedHandler = new TabSelectedHandler() {
        public void onTabSelected(TabSelectedEvent event) {
            //displayEmissionSourceInfo(event.getTab().getTitle());
            displayEmissionSourceInfo("Fire Suppression Sources");
            GWT.log("TabSelectedHandler.onTabSelected:" + event.getTab().getTitle(), null);
        }
    };

    private TabSelectedHandler employeeBusinessTravelTabSelectedHandler = new TabSelectedHandler() {
        public void onTabSelected(TabSelectedEvent event) {
            //displayEmissionSourceInfo(event.getTab().getTitle());
            displayEmissionSourceInfo("Employee Business Travel");
            GWT.log("TabSelectedHandler.onTabSelected:" + event.getTab().getTitle(), null);
        }
    };

    private TabSelectedHandler employeeCommutingTabSelectedHandler = new TabSelectedHandler() {
        public void onTabSelected(TabSelectedEvent event) {
            //displayEmissionSourceInfo(event.getTab().getTitle());
            displayEmissionSourceInfo("Employee Commuting");
            GWT.log("TabSelectedHandler.onTabSelected:" + event.getTab().getTitle(), null);
        }
    };

    private TabSelectedHandler productTransportTabSelectedHandler = new TabSelectedHandler() {
        public void onTabSelected(TabSelectedEvent event) {
            //displayEmissionSourceInfo(event.getTab().getTitle());
            displayEmissionSourceInfo("Product Transport");
            GWT.log("TabSelectedHandler.onTabSelected:" + event.getTab().getTitle(), null);
        }
    };

public void onModuleLoad() {

// - New code added below for layout management
      GWT.log("init OnLoadModule()...", null);
      //SC.say(organ);
      // get rid of scroll bars, and clear out the window's built-in margin,
      // because we want to take advantage of the entire client area
      //Window.enableScrolling(false);
      //Window.setMargin("0px");

      // initialise the main layout container
      mainVLayout = new VLayout();
      mainVLayout.setStyleName("mainVLayout");

      
      mainVLayout.setWidth("100%");
      mainVLayout.setHeight("100%");
      
      //mainVLayout.setOverflow(Overflow.HIDDEN);
      //mainVLayout.setAlign(Alignment.RIGHT);
      //mainVLayout.setBorder("1px double orange");
      //mainVLayout.setShowEdges(true);
      
      //mainVLayout.setLayoutRightMargin(50);
      //mainVLayout.setLayoutLeftMargin(50);
      //mainVLayout.setLayoutTopMargin(20);
      //mainVLayout.setLayoutBottomMargin(20);
      //mainVLayout.setBackgroundColor("#A9F5F2");
      //mainVLayout.setBackgroundColor("#CCFFCC");
      //mainVLayout.setBackgroundColor("#B0E0E6");
      
      // initialise the North layout container
      northHLayout = new HLayout();
      northHLayout.setHeight(NORTH_HEIGHT);
      //northHLayout.setOverflow(Overflow.);
      //northHLayout.setWidth100();
      
      VLayout vLayout = new VLayout();

      //vLayout.setWidth100();
      // add the Masthead to the nested layout container
//---      vLayout.addMember(new Masthead());
      // add the UserOrganizationHeader to the nested layout container
      //UserOrganizationHeader userOrganizationHeader = new UserOrganizationHeader();
      vLayout.addMember(new UserOrganizationHeader());
      // add the Application menu to the nested layout container
      vLayout.addMember(new ApplicationMenu());


      // add the nested layout container to the  North layout container
      northHLayout.addMember(vLayout);

      middleBottomHLayout.setOverflow(Overflow.AUTO);
      middleMiddleHLayout.setOverflow(Overflow.AUTO);
      //middleMiddleHLayout.setShowCloseButton
      
//-- setValidators for the forms for common types.
//      setValidators();

   //stationaryCombustionDataGrid.setDateFormatter(dateFormatter);

   //DateUtil.setDefaultDisplayTimezone("+00:00");


   DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter() {
    public String format(Date date) {
        if(date == null) {
            return null;
        }
        // set the date format as per the WWF date preference
        //DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd-MM-yyyy"+" "+"h:mm:a" );
        //return displayDateFormatter.format(date, TimeZone.createTimeZone(0));
        return displayDateFormatter.format(date);
        /*
        final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("MMM d, yyyy");
        String format = dateFormatter.format(date);
        return format;
         *
         */
     }
   });


    // Create a SimpleType for float fields and set the formatters.
    floatSimpleType.setNormalDisplayFormatter(new SimpleTypeFormatter() {
            @Override
            public String format(Object value, DataClass field,
                            DataBoundComponent component, Record record) {
                    if (value == null) return "";
                    return floatSimpleFormat.format(Double.valueOf(value.toString()));
            }
    });
    floatSimpleType.register();


/* Below was causing problem for editOrganizationProfileForm - dateTime fields. Hence commented for a while.
//- Below is code for input date format, to avoide default timezone adjustment based on location of the user -- ??
   DateUtil.setDateInputFormatter(new DateInputFormatter(){
    public Date parse(String dateString) {
       //final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("MMM d, yyyy");
       Date date = inputDateFormatter.parse(dateString);
       return date;
     }
   });
*/
   mainHLayout.setWidth100();
   mainHLayout.setHeight100();
   //mainHLayout.setOverflow(Overflow.AUTO);
   /*
   topMenuHLayout.setWidth100();
   topMenuHLayout.setHeight("4%");

   middleTopHLayout.setWidth100();
   middleTopHLayout.setHeight("6%");
    *
    */

   middleMiddleHLayout.setWidth100();
   //middleMiddleHLayout.setHeight("60%");
   middleMiddleHLayout.setHeight100();
   middleMiddleHLayout.setBorder("1px double orange");
   middleMiddleHLayout.setShowEdges(true);
   middleMiddleHLayout.setCanDragResize(Boolean.TRUE);
   middleMiddleHLayout.setResizeFrom("B");

   middleBottomHLayout.setWidth100();
   //middleBottomHLayout.setHeight("40%");
   middleBottomHLayout.setHeight100();
   middleBottomHLayout.setBorder("1px double orange");
   middleBottomHLayout.setShowEdges(true);
   //middleBottomHLayout.setCanDragResize(Boolean.TRUE);
   //middleBottomHLayout.setResizeFrom("T");
   middleBottomHLayout.setAlign(Alignment.CENTER);

/*
//-- Top menu
   Menu emissionSourcesMenu = new Menu();
   emissionSourcesMenu.setShowShadow(true);
   emissionSourcesMenu.setShadowDepth(10);

   MenuItem directEmissionSourcesItem = new MenuItem("Direct Emission Sources", "/ibLGHGCalc/images/logo.gif", "Ctrl+N");
   MenuItem inDirectEmissionSourcesItem = new MenuItem("In-Direct Emission Sources", "/ibLGHGCalc/images/logo.gif", "Ctrl+N");
   MenuItem optionalEmissionSourcesItem = new MenuItem("Optional Emission Sources", "/ibLGHGCalc/images/logo.gif", "Ctrl+N");

   MenuItemSeparator separator = new MenuItemSeparator();

    Menu directEmissionsSourcesSubMenu = new Menu();
    directEmissionsSourcesSubMenu.setItems(
            new MenuItem("Stationary Combustions Sources"),
            new MenuItem("Mobile Combustions Sources"),
            new MenuItem("Refridgeration and Air Conditioning Sources"),
            new MenuItem("Fire Suppression Sources"),
            new MenuItem("Waste Stream Combustions Sources"));
    directEmissionSourcesItem.setSubmenu(directEmissionsSourcesSubMenu);

    Menu inDirectEmissionsSourcesSubMenu = new Menu();
    inDirectEmissionsSourcesSubMenu.setItems(
            new MenuItem("Purchased Electricity Info"),
            new MenuItem("Purchased Steam Info"));
    inDirectEmissionSourcesItem.setSubmenu(inDirectEmissionsSourcesSubMenu);

    Menu optionalEmissionsSourcesSubMenu = new Menu();
    optionalEmissionsSourcesSubMenu.setItems(
            new MenuItem("Employee Business Travel"),
            new MenuItem("Employee Commuting"),
            new MenuItem("Product Transport"));
    optionalEmissionSourcesItem.setSubmenu(optionalEmissionsSourcesSubMenu);

   emissionSourcesMenu.setItems(directEmissionSourcesItem, inDirectEmissionSourcesItem,separator, optionalEmissionSourcesItem);

   IMenuButton emissionSourceMenuButton = new IMenuButton("Emission Sources", emissionSourcesMenu);
   emissionSourceMenuButton.setWidth(100);
   emissionSourceMenuButton.setMenuAnimationEffect("fade");
   topMenuHLayout.addMember(emissionSourceMenuButton);
*/
/*
//-- Organization selection Form
   organizationSelectForm.setNumCols(4);
   organizationSelectForm.setTitleOrientation(TitleOrientation.TOP);
     //organizationSelectForm.setColWidths("25%");
    //organizationSelectForm.setEdgeBackgroundColor("CC3");
   final SelectItem organizationNameSelectItem = new SelectItem("organizationName");
   organizationNameSelectItem.setTitle("Select Organization Name");
   organizationNameSelectItem.setOptionDataSource(organizationDS);

   DateItem inventoryYearBeginDate = new DateItem("inventoryYearBeginDate");
   inventoryYearBeginDate.setTitle("Inventory Year Begin Date");

   DateItem inventoryYearEndDate = new DateItem("inventoryYearEndDate");
   inventoryYearEndDate.setTitle("Inventory Year End Date");

   final SelectItem programTypeSelectItem = new SelectItem();
   programTypeSelectItem.setName("programType");
   programTypeSelectItem.setTitle("Program Type");
   programTypeSelectItem.setValueMap("US EPA", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");

   organizationSelectForm.setFields(organizationNameSelectItem, inventoryYearBeginDate, inventoryYearEndDate,programTypeSelectItem);
   //middleTopHLayout.addMember(organizationSelectForm);
   middleTopHLayout.addMember(organizationSelectForm);
*/
   
//-Header
    //final HLayout headerLayout = new HLayout();
    //Img launchImg = new Img("/ibLGHGCalc/images/launch_yourself.png");
   // Img logoImg = new Img("/ibLGHGCalc/images/logo.gif");
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
    middleVLayout.setWidth("80%");
    //middleVLayout.setWidth100();
    middleVLayout.setHeight100();
    //middleVLayout.setAnimateMembers(true);
    
    final Label middleMiddleHLayoutLable = new Label("This middleMiddleHLayout");
    final Label middleBottomHLayoutLable = new Label("This middleBottomHLayout");

    //middleMiddleHLayout.addChild(middleMiddleHLayoutLable);
    middleMiddleHLayout.addChild(new Dashboard());
    //middleMiddleHLayout.setBackgroundImage("featurepic3.JPG");
    
    middleBottomHLayout.addChild(middleBottomHLayoutLable);
    middleBottomHLayout.animateShow(AnimationEffect.SLIDE);
    //middleBottomHLayout.set
    middleBottomHLayout.hide();
//---detailViewerWindow setting
    /*
    detailViewerWindow.setWidth100();
    detailViewerWindow.setHeight100();

    detailViewerWindow.addCloseClickHandler(new CloseClickHandler() {
      public void onCloseClick(ClickEvent clickEvent) {
        middleBottomHLayout.hide();
      }
    });
    */
//---


//--set Height and width of defauly deatil Viewer layout
    detailViewerVLayout.setWidth100();
    detailViewerVLayout.setHeight100();

//--- Left Sections...
    //final SectionStack leftSectionStack = new SectionStack();
    leftSectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
    leftSectionStack.setWidth("20%");
    leftSectionStack.setHeight100();
    leftSectionStack.setShowEdges(true);
    leftSectionStack.setCanDragResize(Boolean.TRUE);
    leftSectionStack.setResizeFrom("R");
    //leftSectionStack.setBackgroundColor("#CCFFCC");
    //leftSectionStack.setBackgroundColor("#A9F5F2");

    //leftSectionStack.setAlign(Alignment.CENTER);
    //leftSectionStack.setBorder("2px solid blue");

/*
    SectionStackSection oranizationSection = new SectionStackSection("Organization");
    oranizationSection.setID("organization");
    oranizationSection.setExpanded(true);
    
    //oranizationSection.addItem(logoImg);

//-temporry place for selecting adding organization

     organizationForm.setDataSource(organizationDS);

     final TextItem organizatioName = new TextItem("organizationName");
     organizatioName.setTitle("Organization Name");
     organizatioName.setSelectOnFocus(true);
     //organizationForm.setItems(organizatioName);

     IntegerItem organizationId = new IntegerItem();
     organizationId.setTitle("Organization Id");
     organizationId.setName("id");

     organizationForm.setFields(currentInventoryBeginDateInSection,currentInventoryEndDateInSection);
     oranizationSection.addItem(organizationForm);
     
     Button saveOrganiztionButton = new Button("Save");
     saveOrganiztionButton.setWidth100();
     saveOrganiztionButton.setHeight(20);
     
     oranizationSection.addItem(saveOrganiztionButton);
     saveOrganiztionButton.addClickHandler(new ClickHandler(){
        public void onClick(ClickEvent event) {
		organizationForm.saveData();
            }
         });

    leftSectionStack.addSection(oranizationSection);
*/
// - end of temporary organization creation place.

    //leftSectionStack.
//--Organization Section
    SectionStackSection oranizationSection = new SectionStackSection("Organization");
    oranizationSection.setID("organization");
    //oranizationSection.setExpanded(true);

    Label dashboardLabel = getSectionLink("Dashboard", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Dashboard");
       }
    });
    oranizationSection.addItem(dashboardLabel);

    Label updateOrganizationProfileLabel = getSectionLink("Organization Profile", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Organization Profile");
       }
    });
    oranizationSection.addItem(updateOrganizationProfileLabel);

    Label updateCurrentInventoryYearLabel = getSectionLink("Change Inventory Year", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Change Inventory Year");
       }
    });
    oranizationSection.addItem(updateCurrentInventoryYearLabel);
    
    leftSectionStack.addSection(oranizationSection);

//-- Calculate Emissions Section
    /*
    SectionStackSection calculateEmissionsSection = new SectionStackSection("Report");
    calculateEmissionsSection.setID("reportSection");
    //calculateEmissionsSection.setExpanded(true);
    */
//-- Calculating Emissions Summary Input Form
//-- Org SelectItem
    /*
     final SelectItem organizationSelectItem = new SelectItem("organizationId");
     organizationSelectItem.setTitle("Select Organization id");
     organizationSelectItem.setOptionDataSource(organizationDS);
    */
     //selectOrganizationForm.setFields(organizationSelectItem);
     //calculateEmissionsSection.addItem(selectOrganizationForm);
/* For now commenting code below. This is taken care in the displayEmissionSourceInfo()

     organizationSelectItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            Integer orgId = (Integer) event.getValue();
            Criteria fetchCriteria = new Criteria();
            fetchCriteria.addCriteria("organizationId", orgId);
            
            //Different criteria for refridgerationAC methods
            Criteria refridgerationACMaterialBalanceFetchCriteria = new Criteria();
            refridgerationACMaterialBalanceFetchCriteria.addCriteria("organizationId", orgId);
            refridgerationACMaterialBalanceFetchCriteria.addCriteria("methodType", "Refridgeration Air Conditioning - Company-Wide Material Balance Method");

            Criteria refridgerationACSimplifiedMaterialBalanceFetchCriteria = new Criteria();
            refridgerationACSimplifiedMaterialBalanceFetchCriteria.addCriteria("organizationId", orgId);
            refridgerationACSimplifiedMaterialBalanceFetchCriteria.addCriteria("methodType","Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method" );

            Criteria refridgerationACScreeningFetchCriteria = new Criteria();
            refridgerationACScreeningFetchCriteria.addCriteria("organizationId", orgId);
            refridgerationACScreeningFetchCriteria.addCriteria("methodType", "Refridgeration Air Conditioning - Source Level Screening Method");

            //Different criteria for refridgerationAC methods
            Criteria fireSuppressionMaterialBalanceFetchCriteria = new Criteria();
            fireSuppressionMaterialBalanceFetchCriteria.addCriteria("organizationId", orgId);
            fireSuppressionMaterialBalanceFetchCriteria.addCriteria("methodType", "Fire Suppression - Company-Wide Material Balance Method");

            Criteria fireSuppressionSimplifiedMaterialBalanceFetchCriteria = new Criteria();
            fireSuppressionSimplifiedMaterialBalanceFetchCriteria.addCriteria("organizationId", orgId);
            fireSuppressionSimplifiedMaterialBalanceFetchCriteria.addCriteria("methodType","Fire Suppression - Company-Wide Simplified Material Balance Method" );

            Criteria fireSuppressionACScreeningFetchCriteria = new Criteria();
            fireSuppressionACScreeningFetchCriteria.addCriteria("organizationId", orgId);
            fireSuppressionACScreeningFetchCriteria.addCriteria("methodType", "Fire Suppression - Source Level Screening Method");

            //Different criteria for optionalSourceInfo
            Criteria employeeBusinessTravelByVehicleFetchCriteria = new Criteria();
            employeeBusinessTravelByVehicleFetchCriteria.addCriteria("organizationId", orgId);
            employeeBusinessTravelByVehicleFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Vehicle");

            Criteria employeeBusinessTravelByRailFetchCriteria = new Criteria();
            employeeBusinessTravelByRailFetchCriteria.addCriteria("organizationId", orgId);
            employeeBusinessTravelByRailFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Rail");

            Criteria employeeBusinessTravelByBusFetchCriteria = new Criteria();
            employeeBusinessTravelByBusFetchCriteria.addCriteria("organizationId", orgId);
            employeeBusinessTravelByBusFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Bus");

            Criteria employeeBusinessTravelByAirFetchCriteria = new Criteria();
            employeeBusinessTravelByAirFetchCriteria.addCriteria("organizationId", orgId);
            employeeBusinessTravelByAirFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Air");

            Criteria employeeCommutingByVehicleFetchCriteria = new Criteria();
            employeeCommutingByVehicleFetchCriteria.addCriteria("organizationId", orgId);
            employeeCommutingByVehicleFetchCriteria.addCriteria("optionalSourceType", "Employee Commuting - By Vehicle");

            Criteria employeeCommutingByRailFetchCriteria = new Criteria();
            employeeCommutingByRailFetchCriteria.addCriteria("organizationId", orgId);
            employeeCommutingByRailFetchCriteria.addCriteria("optionalSourceType", "Employee Commuting - By Rail");

            Criteria employeeCommutingByBusFetchCriteria = new Criteria();
            employeeCommutingByBusFetchCriteria.addCriteria("organizationId", orgId);
            employeeCommutingByBusFetchCriteria.addCriteria("optionalSourceType", "Employee Commuting - By Bus");

            Criteria productTransportByVehicleFetchCriteria = new Criteria();
            productTransportByVehicleFetchCriteria.addCriteria("organizationId", orgId);
            productTransportByVehicleFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Vehicle");

            Criteria productTransportByHeavyDutyTrucksFetchCriteria = new Criteria();
            productTransportByHeavyDutyTrucksFetchCriteria.addCriteria("organizationId", orgId);
            productTransportByHeavyDutyTrucksFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Heavy Duty Trucks");

            Criteria productTransportByRailFetchCriteria = new Criteria();
            productTransportByRailFetchCriteria.addCriteria("organizationId", orgId);
            productTransportByRailFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Rail");

            Criteria productTransportByWaterAirFetchCriteria = new Criteria();
            productTransportByWaterAirFetchCriteria.addCriteria("organizationId", orgId);
            productTransportByWaterAirFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Water or Air");

            emissionsSummaryDataGrid.filterData(fetchCriteria);
            stationaryCombustionDataGrid.filterData(fetchCriteria);
            mobileCombustionDataGrid.filterData(fetchCriteria);
            purchasedElectricityDataGrid.filterData(fetchCriteria);
            purchasedSteamDataGrid.filterData(fetchCriteria);

            refridgerationAirConditioningDataGrid_1.filterData(refridgerationACMaterialBalanceFetchCriteria);
            refridgerationAirConditioningDataGrid_2.filterData(refridgerationACSimplifiedMaterialBalanceFetchCriteria);
            refridgerationAirConditioningDataGrid_3.filterData(refridgerationACScreeningFetchCriteria);

            fireSuppressionDataGrid_1.filterData(fireSuppressionMaterialBalanceFetchCriteria);
            fireSuppressionDataGrid_2.filterData(fireSuppressionSimplifiedMaterialBalanceFetchCriteria);
            fireSuppressionDataGrid_3.filterData(fireSuppressionACScreeningFetchCriteria);

	    wasteStreamCombustionDataGrid.filterData(fetchCriteria);

            //--optional emissions
            employeeBusinessTravelByVehicleDataGrid.filterData(employeeBusinessTravelByVehicleFetchCriteria);
            employeeBusinessTravelByRailDataGrid.filterData(employeeBusinessTravelByRailFetchCriteria);
            employeeBusinessTravelByBusDataGrid.filterData(employeeBusinessTravelByBusFetchCriteria);
            employeeBusinessTravelByAirDataGrid.filterData(employeeBusinessTravelByAirFetchCriteria);
            employeeCommutingByVehicleDataGrid.filterData(employeeCommutingByVehicleFetchCriteria);
            employeeCommutingByRailDataGrid.filterData(employeeCommutingByRailFetchCriteria);
            employeeCommutingByBusDataGrid.filterData(employeeCommutingByBusFetchCriteria);
            productTransportByVehicleDataGrid.filterData(productTransportByVehicleFetchCriteria);
            productTransportByHeavyDutyTrucksDataGrid.filterData(productTransportByHeavyDutyTrucksFetchCriteria);
            productTransportByRailDataGrid.filterData(productTransportByRailFetchCriteria);
            productTransportByWaterAirDataGrid.filterData(productTransportByWaterAirFetchCriteria);

            middleMiddleHLayout.addChild(tabSet);
        }
     });
*/
/*
     DateItem emissionsBeginDate = new DateItem("emissionsBeginDate");
     emissionsBeginDate.setTitle("Emissions Begin Date");

     DateItem emissionsEndDate = new DateItem("emissionsEndDate");
     emissionsEndDate.setTitle("Emissions End Date");

     final SelectItem programTypeItem = new SelectItem();
     programTypeItem.setName("programType2");
     programTypeItem.setTitle("Program Type");
     programTypeItem.setValueMap("US EPA", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");

     emissionsSummaryInputForm.setFields(emissionsBeginDate, emissionsEndDate,programTypeItem);
     calculateEmissionsSection.addItem(emissionsSummaryInputForm);
     
     Button calculateEmissionsSummaryButton = new Button("Calculate Emissions Summary");
     calculateEmissionsSummaryButton.setWidth100();
     calculateEmissionsSummaryButton.setHeight(15);

     calculateEmissionsSection.addItem(calculateEmissionsSummaryButton);
     calculateEmissionsSummaryButton.addClickHandler(new ClickHandler(){
        public void onClick(ClickEvent event) {
        	Record emissionsSummaryInputRecord = emissionsSummaryInputForm.getValuesAsRecord();
		emissionsSummaryDS.performCustomOperation("calculateEmissionsSummary", emissionsSummaryInputRecord);
            }
         });
*/

//--Direct Emissions Sources

    SectionStackSection directEmissionsSourcesSection = new SectionStackSection("Direct Emission Sources");
    directEmissionsSourcesSection.setID("directEmissionsSources");
    //directEmissionsSourcesSection.setExpanded(true);
    directEmissionsSourcesSection.setResizeable(Boolean.FALSE);

    Label getStationaryCombustionInfoLabel = getSectionLink("Stationary", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Stationary Combustions Sources");
           //displayData ("Stationary Combustions Sources", stationaryCombustionListGridFields, stationaryCombustionFormItems, stationaryCombustionInfoDS);
       }
    });
    directEmissionsSourcesSection.addItem(getStationaryCombustionInfoLabel);

    Label getMobileCombustionInfoLabel = getSectionLink("Mobile", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Mobile Combustions Sources");
           //initializeDataForm();
           //initMobileCombustionEditForm_2();
           //displayData ("Mobile Combustions Sources", mobileCombustionListGridFields, mobileCombustionFormItems, mobileCombustionInfoDS);
       }
    });
    directEmissionsSourcesSection.addItem(getMobileCombustionInfoLabel);

    Label getRefridgerationACInfoLabel = getSectionLink("Refridgeration & A/C", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Refridgeration and Air Conditioning Sources");
       }
    });
    directEmissionsSourcesSection.addItem(getRefridgerationACInfoLabel);

    Label getFireSuppressionInfoLabel = getSectionLink("Fire Suppression", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Fire Suppression Sources");
       }
    });
    directEmissionsSourcesSection.addItem(getFireSuppressionInfoLabel);

    Label getWasteStreamCombustionInfoLabel = getSectionLink("Waste Stream", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Waste Stream Combustions Sources");
       }
    });

    directEmissionsSourcesSection.addItem(getWasteStreamCombustionInfoLabel);
    leftSectionStack.addSection(directEmissionsSourcesSection);

//--In-Direct Emissions Sources
    SectionStackSection inDirectEmissionsSourcesSection = new SectionStackSection("In-Direct Emission Sources");
    inDirectEmissionsSourcesSection.setID("inDirectEmissionsSources");
    //inDirectEmissionsSourcesSection.setExpanded(true);
    inDirectEmissionsSourcesSection.setResizeable(Boolean.FALSE);

    Label getPurchasedElectricityInfoLabel = getSectionLink("Purchased Electricity", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Purchased Electricity");
       }
    });
    inDirectEmissionsSourcesSection.addItem(getPurchasedElectricityInfoLabel	);

    Label getPurchasedSteamInfoLabel = getSectionLink("Purchased Steam", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Purchased Steam");
       }
    });
    inDirectEmissionsSourcesSection.addItem(getPurchasedSteamInfoLabel);
    leftSectionStack.addSection(inDirectEmissionsSourcesSection);

//--Optional Emissions Sources
    SectionStackSection optionalEmissionsSourcesSection = new SectionStackSection("Optional Emission Sources");
    optionalEmissionsSourcesSection.setID("optionalEmissionsSources");
    //optionalEmissionsSourcesSection.setExpanded(true);
    optionalEmissionsSourcesSection.setResizeable(Boolean.FALSE);
    //optionalEmissionsSourcesSection.setHeight(150);

    Label getEmployeeBusinessTravelInfoLabel = getSectionLink("Employee Business Travel", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Employee Business Travel");
       }
    });
    optionalEmissionsSourcesSection.addItem(getEmployeeBusinessTravelInfoLabel);

    Label getEmployeeCommutingInfoLabel = getSectionLink("Employee Commuting", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Employee Commuting");
       }
    });
    optionalEmissionsSourcesSection.addItem(getEmployeeCommutingInfoLabel);

    Label getProductTransportInfoLabel = getSectionLink("Product Transport", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Product Transport");
       }
    });
    optionalEmissionsSourcesSection.addItem(getProductTransportInfoLabel);

    leftSectionStack.addSection(optionalEmissionsSourcesSection);

//-- Calculate Emissions Section
    SectionStackSection reportSection = new SectionStackSection("Report");
    reportSection.setID("reportSection");

    Label calculateEmissionsLabel = getSectionLink("Calculate Emissions", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Calculate Emissions");
       }
    });
    reportSection.addItem(calculateEmissionsLabel);

    /*
    Label emissionsSummaryLabel = getSectionLink("Emissions Summary", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Emissions Summary");
       }
    });
    reportSection.addItem(emissionsSummaryLabel);
    */

    Label emissionsReportLabel = getSectionLink("Emissions Report", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Emissions Report");
       }
    });
    reportSection.addItem(emissionsReportLabel);
    leftSectionStack.addSection(reportSection);

//--Upload Data
    SectionStackSection uploadDataSection = new SectionStackSection("Upload Data");
    uploadDataSection.setID("uploadDataSection");
    uploadDataSection.setResizeable(Boolean.FALSE);

    Label loadDataLabel = getSectionLink("Load Data", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Load Data");
       }
    });
    uploadDataSection.addItem(loadDataLabel);

    Label loadEmissionFactorsLabel = getSectionLink("Load Emission Factors", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Load Emission Factors");
       }
    });
    uploadDataSection.addItem(loadEmissionFactorsLabel);

    leftSectionStack.addSection(uploadDataSection);

//--add leftSectionStack to the mainHLayout
    mainHLayout.addMember(leftSectionStack);


//-- Top Menu click handler

    ApplicationMenu.directEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.inDirectEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.optionalEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.optionalEmissionsSourcesSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.organizationSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.reportSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.fileUploadSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.helpSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

    ApplicationMenu.helpSubMenu.addItemClickHandler(new ItemClickHandler() {
        public void onItemClick(ItemClickEvent event) {
            MenuItem item = event.getItem();
            //SC.say("You clicked : " + item.getTitle());
            displayEmissionSourceInfo(item.getTitle());
        }
    });

//--add layouts inside middleVLayout
    //middleVLayout.addMember(topMenuHLayout);
    //middleVLayout.addMember(middleTopHLayout);
    middleVLayout.addMember(middleMiddleHLayout);
    middleVLayout.addMember(middleBottomHLayout);
    //middleBottomHLayout.
    mainHLayout.addMember(middleVLayout);

//--Get the right Layout and add it to mainHLayout
    /*
    //For now I am getting rid of right section - 2011-02-03
    rightVLayout.setWidth("15%");
    Label temporaryLable = new Label("This is right section");
    rightVLayout.addMember(temporaryLable);
    mainHLayout.addMember(rightVLayout);
     *
     */

//-Defining tab set
    //tabSet.setTabBarPosition(Side.TOP);
    tabSet.setTabBarAlign(Side.LEFT);
    tabSet.setWidth100();
    tabSet.setHeight100();


//intitialize generic components
    initializeDataLayout();
    initializeDataForm();
    
    refridgerationAirConditioningTabSet.setTabBarAlign(Side.LEFT);
    refridgerationAirConditioningTabSet.setWidth100();
    refridgerationAirConditioningTabSet.setHeight100();

    fireSuppressionTabSet.setTabBarAlign(Side.LEFT);
    fireSuppressionTabSet.setWidth100();
    fireSuppressionTabSet.setHeight100();

//--Initialize organization Inventory Year Form
    initOrganizationInventoryYearEditForm();
//--Initialize organization Edit Profile Form
    initOrganizationProfileEditForm();

//--Initialize the emissions summary Tab
    emissionsSummaryTab();

//--init Emissions Summary Input Form
    initEmissionsSummaryInputForm();

//--Defining Stationary Combustion tab layout    
    stationaryCombustionTab();
    //stationaryCombustionTab_2();
    
//--Calling form to Add New Record to Stationary Combustion Source
    initStationaryCombustionEditForm();
    //initStationaryCombustionEditForm_2();
    
//--Defining Mobile Combustion tab layout
    mobileCombustionTab();
    //mobileCombustionTab_2();

//--Calling form to Add New Record to Mobile Combustion Source
    initMobileCombustionEditForm();
    //initMobileCombustionEditForm_2();

//--Defining Refridgeration Air Conditioning tab layout
    refridgerationAirConditioningTab ();

//--Initializing forms to Add New Record to Mobile Combustion Source
    initRefridgerationAirConditioningEditForm_123();
    //initRefridgerationAirConditioningEditForm_1();
    //initRefridgerationAirConditioningEditForm_2();
    //initRefridgerationAirConditioningEditForm_3();

//--Defining Fire Suppression tab layout
    fireSuppressionTab ();

//--Initializing forms to Add New Record to FireSuppression Source
    initFireSuppressionEditForm_123();
    //initFireSuppressionEditForm_1();
    //initFireSuppressionEditForm_2();
    //initFireSuppressionEditForm_3();

//--Defining Purchased Electricity tab layout
    purchasedElectricityTab();
//--Initializing forms to Add New Record to Purchased Electricity Source
    initPurchasedElectricityEditForm();

//--Defining Purchased Steam tab layout
    purchasedSteamTab();
//--Initializing forms to Add New Record to Purchased Steam Source
    initPurchasedSteamEditForm();

   //--wasteStreamCombustion
    wasteStreamCombustionTab();
    initWasteStreamCombustionEditForm();

//--Define layout size optional emissions
    employeeBusinessTravelLayout.setWidth100();
    employeeBusinessTravelLayout.setHeight100();
    /*
    Label employeeBusinessTravelDataLabel = new Label("Current Employee Business Travel Data");
    employeeBusinessTravelDataLabel.setHeight(20);
    employeeBusinessTravelLayout.addMember(employeeBusinessTravelDataLabel);
    */
    employeeCommutingLayout.setWidth100();
    employeeCommutingLayout.setHeight100();
    /*
    Label employeeCommutingDataLabel = new Label("Current Employee Commuting Data");
    employeeCommutingDataLabel.setHeight(20);
    employeeCommutingLayout.addMember(employeeCommutingDataLabel);
    */

    productTransportLayout.setWidth100();
    productTransportLayout.setHeight100();
    /*
    Label productTransportDataLabel = new Label("Current Product Transport Data");
    productTransportDataLabel.setHeight(20);
    productTransportLayout.addMember(productTransportDataLabel);
    *
     *
     */
//--Defining employeeBusinessTravelByVehicleTab
    employeeBusinessTravelByVehicleTab();
//-- Initializing employeeBusinessTravelByVehicleTab Edit form
    initEmployeeBusinessTravelByVehicleEditForm();

    //--employeeBusinessTravelByRail
    employeeBusinessTravelByRailTab();
    initEmployeeBusinessTravelByRailEditForm();

    //--employeeBusinessTravelByBus
    employeeBusinessTravelByBusTab();
    initEmployeeBusinessTravelByBusEditForm();

    //--employeeAirinessTravelByAir
    employeeBusinessTravelByAirTab();
    initEmployeeBusinessTravelByAirEditForm();

    //--employeeCommutingByVehicle
    employeeCommutingByVehicleTab();
    initEmployeeCommutingByVehicleEditForm();

    //--employeeCommutingByRail
    employeeCommutingByRailTab();
    initEmployeeCommutingByRailEditForm();

    //--employeeCommutingByBus
    employeeCommutingByBusTab();
    initEmployeeCommutingByBusEditForm();

    //--productTransportByVehicle
    productTransportByVehicleTab();
    initProductTransportByVehicleEditForm();

    //--productTransportByHeavyDutyTrucks
    productTransportByHeavyDutyTrucksTab();
    initProductTransportByHeavyDutyTrucksEditForm();

    //--productTransportByRail
    productTransportByRailTab();
    initProductTransportByRailEditForm();

    //--productTransportByWaterAir
    productTransportByWaterAirTab();
    initProductTransportByWaterAirEditForm();

//-- Add optinal emission tabset to Layouts
    employeeBusinessTravelLayout.addMember(employeeBusinessTravelTabSet);
    employeeCommutingLayout.addMember(employeeCommutingTabSet);
    productTransportLayout.addMember(productTransportTabSet);

//--Defining File Upload  tab
    fileUploadTab();

//--adding to mainVLayout
    mainVLayout.addMember(northHLayout);
    mainVLayout.addMember(mainHLayout);


//--Add topLayout to "main"
    //RootPanel.get("main").add(mainHLayout);
    RootPanel.get("main").add(mainVLayout);
    //topLayout.draw();
}

public void initializeDataForm(){
    dataForm.setCellPadding(5);
    dataForm.setWidth("100%");

    //--initialze Validators for the form
    initializeValidators();

    final IButton cancelButton = new IButton();
    final IButton saveButton = new IButton();

    saveButton.setTitle("SAVE");
    saveButton.setTooltip("Save this Source");
    saveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if (dataForm.validate()) {
            SC.warn("Hello Start!!");
            Record dataFormRecord = dataForm.getValuesAsRecord();
            //SC.warn("Hello!!");
            Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
            dataFormRecord.setAttribute("organizationId", organizationIdValue);
            dataGrid.updateData(dataFormRecord);
             
            
/*
            String fuelName = dataForm.getValueAsString("fuelSourceDescription");
            String fuelQty = dataForm.getValueAsString("fuelQuantity");
            Criteria formValues = dataForm.getValuesAsCriteria();
            //SC.warn(fuelName+"  "+fuelQty);
            //SC.warn(formValues.getValues().toString());
            Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
            //dataFormRecord.setAttribute("organizationId", organizationIdValue);
            formValues.setAttribute("organizationId", organizationIdValue);
            //Record dataFormRecord = (Record) formValues;
            //dataGrid.updateData(formValues.);
            SC.warn(formValues.toString());
            //dataForm.clearValues();
            //dataForm.markForRedraw();
            //dataForm.markForDestroy();
 *
 */
            dataFormWindow.hide();
        } else {
            SC.say("Please provide proper information");
        }
      }
    });

    cancelButton.setTitle("CANCEL");
    cancelButton.setTooltip("Cancel");
    cancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {        
        //dataForm.markForDestroy();
        dataFormWindow.hide();
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
    dataFormWindow.setShowShadow(true);
    dataFormWindow.setIsModal(true);
    dataFormWindow.setPadding(20);
    dataFormWindow.setWidth(500);
    dataFormWindow.setHeight(425);
    dataFormWindow.setShowMinimizeButton(false);
    dataFormWindow.setShowCloseButton(true);
    dataFormWindow.setShowModalMask(true);
    dataFormWindow.centerInPage();
    dataFormWindow.setTitle("Please enter combustion source information:");
    dataFormWindow.addItem(dialog);
}
public void initializeDataLayout(){
        dataLayout.setWidth100();
        dataLayout.setHeight100();
/*
        VLayout mobileCombustionTabLayout = new VLayout(15);
        mobileCombustionTabLayout.setWidth100();
        mobileCombustionTabLayout.setHeight100();
*/
        Label dataLabel = new Label("Current combustion sources");
        dataLabel.setHeight(15);
        dataLabel.setWidth100();
        dataLabel.setAlign(Alignment.LEFT);
        dataLabel.setStyleName("labels");

//--ListGrid setup
        dataGrid.setWidth100();
        dataGrid.setHeight100();
        dataGrid.setShowRecordComponents(true);
        dataGrid.setShowRecordComponentsByCell(true);
        dataGrid.setAutoFetchData(Boolean.FALSE);
        dataGrid.setCanEdit(Boolean.TRUE);
        dataGrid.setDataSource(dataDS);
        dataGrid.setCanHover(true);
        dataGrid.setShowHover(true);
        //dataGrid.setShowHoverComponents(true);

        IButton newCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        newCombustionButton.setIcon("addIcon.jpg");

        newCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dataForm.editNewRecord();
                dataFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(dataLabel);
        gridButtonLayout.addMember(newCombustionButton);

        dataLayout.addMember(gridButtonLayout);
        dataLayout.addMember(dataGrid);

 /*
        dataTabLayout.addMember(gridButtonLayout);
        dataTabLayout.addMember(dataGrid);

//--Defining data Combustion tab
        final Tab dataTab1 = new Tab();
        dataTab1.setPane(mobileCombustionTabLayout);

//---Adding data Combustion tab to tabSet
        dataTabSet.addTab(dataTab1);
        dataLayout.addMember(dataTabSet);
*/

}

private void fileUploadTab(){

    uploadForm.setEncoding(Encoding.MULTIPART);
    uploadForm.setAction(eFUploadFormSubmitAction);
    //uploadForm.setMethod(FormMethod.POST);
    
    final UploadItem fileUpload = new UploadItem("fileUpload");
    fileUpload.setTitle("File");
    fileUpload.setWidth(300);
    
    /*
    final FileItem fileUpload = new FileItem("fileUpload");
    fileUpload.setTitle("File");
    fileUpload.setWidth(300);
    */
    //final SelectItem fileTypeItem = new SelectItem("fileType");
    fileTypeItem.setTitle("Select File to upload");
    fileTypeItem.setValueMap(epaDataLoadOptions);

    fileUpload.addChangedHandler(new ChangedHandler() {
            public void onChanged(ChangedEvent e) {
                    System.out.println("change");
            }
    });

    //fileUploadOrganizatonID.
    
    List<FormItem> items = new ArrayList<FormItem>();
    items.add(fileTypeItem);
    items.add(fileUpload);
    items.add(fileUploadOrganizatonID);

    FormItem[] fitems = new FormItem[items.size()];
    items.toArray(fitems);
    uploadForm.setItems(fitems);
    
    Button uploadButton = new Button("Upload");

    uploadButton.addClickHandler(new ClickHandler(){
         public void onClick(ClickEvent e) {
                    Object obj = fileUpload.getDisplayValue();
                    if (obj != null) {
                            Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                            fileUploadOrganizatonID.setValue(orgId);
                            uploadForm.submitForm();
                            /*
                            uploadForm.submit(new DSCallback() {
                                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                                        SC.say("File Uploaded Successfully.");
                                        }
                            });
                             *
                             */
                            /*
                            Record formRecord = uploadForm.getValuesAsRecord();
                            //Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
                            formRecord.setAttribute("organizationId", fileUploadOrganizatonID);
                            //fileUploadDS.updateData(fileUpload,fileUploadOrganizatonID);
                            fileUploadDS.updateData(formRecord, new DSCallback() {
                                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                                            SC.say("File Uploaded Successfully.");
                                        }
                            });
                             
                            */

                    } else
                            SC.say("Please select a file.");
         }
    });

//-Load form and button on Vstack
    //VStack fileUploadLayout = new VStack();
    fileUploadLayout.setWidth100();
    fileUploadLayout.setMembersMargin(10);
    fileUploadLayout.setDefaultLayoutAlign(Alignment.LEFT);

    fileUploadLayout.addMember(uploadForm);
    fileUploadLayout.addMember(uploadButton);

    }
private void emissionsSummaryTab() {

        emissionsSummaryVLayout.setWidth100();
        emissionsSummaryVLayout.setHeight100();
        /*
        emissionsSummaryDetailViewer.setDataSource(emissionsSummaryDS);
        //emissionsSummaryDetailViewer.setTitle("Emissions Summary Details:");
        //emissionsSummaryDetailViewer.setGroupTitle("Emissions Summary Details:");

        //Detail viewer label
        emissionsSummaryDetailViewerLabel.setHeight(15);
        emissionsSummaryDetailViewerLabel.setWidth100();
        emissionsSummaryDetailViewerLabel.setAlign(Alignment.LEFT);
        //emissionsSummaryDetailViewerLabel.setShowEdges(true);
        //emissionsSummaryDetailViewerLabel.setBackgroundColor("#EFFBFB");
        emissionsSummaryDetailViewerLabel.setStyleName("labels");

        //detailViewerVLayout.addMember(emissionsSummaryDetailViewerLabel);
        //detailViewerVLayout.addMember(emissionsSummaryDetailViewer);
        //middleBottomHLayout.addMember(detailViewerVLayout);

        //HLayout detailViewerHeader = new HLayout();
        //detailViewerHeader.addMember(emissionsSummaryDetailViewerLabel);
        */
        //final VLayout emissionsSummaryVLayout = new VLayout();
        Label emissionReportLabel = new Label("Emissions Report");
        emissionReportLabel.setHeight(20);
        emissionReportLabel.setStyleName("labels");
        emissionsSummaryVLayout.addMember(emissionReportLabel);

        emissionsSummaryDataGrid.setWidth100();
        emissionsSummaryDataGrid.setHeight100();
        emissionsSummaryDataGrid.setShowRecordComponents(true);
        emissionsSummaryDataGrid.setShowRecordComponentsByCell(true);
        emissionsSummaryDataGrid.setRecordComponentPosition(EmbeddedPosition.WITHIN);
        //emissionsSummaryDataGrid.setS
        emissionsSummaryDataGrid.setCanExpandRecords(true);
        emissionsSummaryDataGrid.setExpansionMode(ExpansionMode.DETAILS);
        emissionsSummaryDataGrid.setCanRemoveRecords(true);
        

        emissionsSummaryDataGrid.setAutoFetchData(Boolean.TRUE);
        emissionsSummaryDataGrid.setDataSource(emissionsSummaryDS);

        ListGridField organizationIdField2 = new ListGridField("organizationId", "Organization Id");
        organizationIdField2.setType(ListGridFieldType.INTEGER);
/*
        FloatDetailViewerField directEmissionsField = new FloatDetailViewerField("directEmissions", "Direct Emissions");

       
        directEmissionsField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
       

        FloatDetailViewerField stationaryCombustionEmissionsField = new FloatDetailViewerField("stationaryCombustionEmissions", "Stationary Combustion Emissions");
        //stationaryCombustionEmissionsField.setType(DetailViewerField.FLOAT);

        FloatDetailViewerField mobileCombustionEmissionsField = new FloatDetailViewerField("mobileCombustionEmissions", "Mobile Source Emissions");
        //mobileCombustionEmissionsField.setType(ListGridFieldType.FLOAT);

        FloatDetailViewerField refridgerationAirConditioningEmissionsField = new FloatDetailViewerField("refridgerationAirConditioningEmissions", "Refridgeration And Ac Emissions");
        //refridgerationAirConditioningEmissionsField.setType(ListGridFieldType.FLOAT);

        FloatDetailViewerField fireSuppressantEmissionsField = new FloatDetailViewerField("fireSuppressantEmissions", "Fire Suppressant Emissions");
        //fireSuppressantEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField wasteStreamCombustionEmissionsField = new FloatDetailViewerField("wasteStreamCombustionEmissions", "WasteStream Combustion Emissions");
        //wasteStreamCombustionEmissionsField.setType(ListGridFieldType.FLOAT);

        FloatDetailViewerField  purchasedElectricityEmissionsField = new FloatDetailViewerField("purchasedElectricityEmissions", "Purchased Electricity Emissions");
        //purchasedElectricityEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField purchasedSteamEmissionsField = new FloatDetailViewerField("purchasedSteamEmissions", "Purchased Steam Emissions");
       //purchasedSteamEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField employeeBusinessTravelByVehicleEmissionsField = new FloatDetailViewerField("employeeBusinessTravelByVehicleEmissions", "Employee Business Travel By Vehicle Emissions");
        //employeeBusinessTravelByVehicleEmissionsField.setType(ListGridFieldType.FLOAT);

        FloatDetailViewerField employeeBusinessTravelByRailEmissionsField = new FloatDetailViewerField("employeeBusinessTravelByRailEmissions", "Employee Business Travel By Rail Emissions");
        //employeeBusinessTravelByRailEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField employeeBusinessTravelByBusEmissionsField = new FloatDetailViewerField("employeeBusinessTravelByBusEmissions", "Employee Business Travel By Bus Emissions");
        //employeeBusinessTravelByBusEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField employeeBusinessTravelByAirEmissionsField = new FloatDetailViewerField("employeeBusinessTravelByAirEmissions", "Employee Business Travel By Air Emissions");
        //employeeBusinessTravelByAirEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField employeeCommutingByVehicleEmissionsField = new FloatDetailViewerField("employeeCommutingByVehicleEmissions", "Employee Commuting By Vehicle Emissions");
        //employeeCommutingByVehicleEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField employeeCommutingByBusEmissionsField = new FloatDetailViewerField("employeeCommutingByBusEmissions", "Employee Commuting By Bus Emissions");
        //employeeCommutingByBusEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField productTransportByVehicleEmissionsField = new FloatDetailViewerField("productTransportByVehicleEmissions", "Product Transport By Vehicle Emissions");
        //productTransportByVehicleEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField productTransportByHeavyDutyTrucksEmissionsField = new FloatDetailViewerField("productTransportByHeavyDutyTrucksEmissions", "Product Transport By Heavy Duty Trucks Emissions");
        //productTransportByHeavyDutyTrucksEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField productTransportByRailEmissionsField = new FloatDetailViewerField("productTransportByRailEmissions", "Product Transport By Rail Emissions");
        //productTransportByRailEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField productTransportByWaterAirEmissionsField = new FloatDetailViewerField("productTransportByWaterAirEmissions", "Product Transport By Water/Air Emissions");
        //productTransportByWaterAirEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField biomassStationaryCombustionEmissionsField = new FloatDetailViewerField("biomassStationaryCombustionEmissions", "Biomass Stationary Combustion Emissions");
        //biomassStationaryCombustionEmissionsField.setType(ListGridFieldType.FLOAT);


        FloatDetailViewerField biomassMobileCombustionEmissionsField = new FloatDetailViewerField("biomassMobileCombustionEmissions", "Biomass Mobile Source Emissions");
        //biomassMobileCombustionEmissionsField.setType(ListGridFieldType.FLOAT);
*/

        FloatListGridField totalEmissionsField = new FloatListGridField("totalEmissions", "Total Emissions(MT CO2-e)");
        //totalEmissionsField.setType(ListGridFieldType.FLOAT);
        totalEmissionsField.setWidth(TOTAL_EMISSIONS_FIELD_WIDTH);

        ListGridField programTypeField = new ListGridField("programType", "Program Type");
        programTypeField.setType(ListGridFieldType.TEXT);
        programTypeField.setWidth(PROGRAM_TYPE_FIELD_WIDTH);

        ListGridField emissionsBeginDateField = new ListGridField("emissionsBeginDate", "Emissions Begin Date");
        emissionsBeginDateField.setType(ListGridFieldType.DATE);
        emissionsBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField emissionsEndDateField = new ListGridField("emissionsEndDate", "Emissions End Date");
        emissionsEndDateField.setType(ListGridFieldType.DATE);
        emissionsEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        //ListGridField reportFileLocationField = new ListGridField("reportFileLocation", "Report File Location");
        //reportFileLocationField.setType(ListGridFieldType.LINK);
        //reportFileLocationField.setWidth(PROGRAM_TYPE_FIELD_WIDTH);

        ListGridField reportFileNameField = new ListGridField("reportFileName", "Report Download");
        reportFileNameField.setType(ListGridFieldType.TEXT);        
        reportFileNameField.setBaseStyle("listgridField");
        reportFileNameField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    return "Click to download";
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
/*
        reportFileNameField.setIcon(ADD_ICON_IMAGE);
        reportFileNameField.setShowDownIcon(Boolean.TRUE);
        reportFileNameField.setShowSelectedIcon(Boolean.FALSE);
        reportFileNameField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    //Button newButton = new Button();
                    //newButton.setTitle(record.getAttribute("reportFileName"));
                    String fileName =record.getAttribute("reportFileName");
                    return Canvas.imgHTML(ADD_ICON_IMAGE, 20, 12, null, null,null);
                    //return record.getAttribute("reportFileName");
                    //return newButton;
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
*/

        //reportFileNameField.setShowD
        reportFileNameField.setWidth(REPORT_FILE_NAME_FIELD_WIDTH);
        //reportFileNameField.setWidth(PROGRAM_TYPE_FIELD_WIDTH);
        //reportFileNameField.setLinkURLPrefix("/ibLGHGCalc/reports/");


        ListGridField downloadReportField = new ListGridField("reportFileName", "Download Report");
        downloadReportField.setType(ListGridFieldType.ICON);
        downloadReportField.setIcon(ADD_ICON_IMAGE);
        downloadReportField.setDisplayField("Click to download");
        //downloadReportField.setValueField("reportFileName");
        downloadReportField.setWidth(PROGRAM_TYPE_FIELD_WIDTH);

/*
        ListGridField viewReportButtreportFileNameFieldonField = new ListGridField("viewReportButtonField", "Download Report");
        //viewReportButtonField.setType(ListGridFieldType.LINK);
        viewReportButtonField.setWidth(PROGRAM_TYPE_FIELD_WIDTH);
*/
        //viewReportButtonField
        ListGridField reportGeneratedDate = new ListGridField("lastUpdated", "Report Generation Date");
        reportGeneratedDate.setWidth(END_DATE_FIELD_WIDTH);
        
/*
        emissionsSummaryDataGrid.setFields(organizationIdField2, programTypeField, emissionsBeginDateField, emissionsEndDateField, directEmissionsField,
        					stationaryCombustionEmissionsField, mobileCombustionEmissionsField, refridgerationAirConditioningEmissionsField,
        					fireSuppressantEmissionsField, wasteStreamCombustionEmissionsField, purchasedElectricityEmissionsField,
                                                purchasedSteamEmissionsField, employeeBusinessTravelByVehicleEmissionsField,
                                                employeeBusinessTravelByRailEmissionsField,employeeBusinessTravelByBusEmissionsField,
                                                employeeBusinessTravelByAirEmissionsField,employeeCommutingByVehicleEmissionsField,
                                                employeeCommutingByBusEmissionsField,productTransportByVehicleEmissionsField,
                                                productTransportByHeavyDutyTrucksEmissionsField,productTransportByRailEmissionsField,
                                                productTransportByWaterAirEmissionsField,
                                                biomassStationaryCombustionEmissionsField, biomassMobileCombustionEmissionsField,
                                                totalEmissionsField);
 *
 */
        emissionsSummaryDataGrid.setFields(programTypeField, emissionsBeginDateField, emissionsEndDateField,totalEmissionsField,reportGeneratedDate,reportFileNameField);
        emissionsSummaryDataGrid.sort("lastUpdated", SortDirection.DESCENDING);

        //reportFileNameField.setHidden(true);
        //emissionsSummaryDataGrid.setFields(programTypeField, emissionsBeginDateField, emissionsEndDateField,totalEmissionsField,reportFileNameField,reportGeneratedDate);
        //reportFileNameField.setHidden(true);
        //emissionsSummaryDataGrid.setFields(programTypeField, emissionsBeginDateField, emissionsEndDateField,totalEmissionsField);
        //reportFileLocationField.setHidden(true);
        //emissionsSummaryDataGrid.setOverflow(Overflow.VISIBLE);
        //emissionsSummaryDataGrid.setAutoWidth();

        //- adding cell click handler
        //emissionsSummaryDataGrid.getR
        
        emissionsSummaryDataGrid.addCellClickHandler(new CellClickHandler() {
            public void onCellClick(CellClickEvent event) {
                ListGridRecord record =  event.getRecord();
                int colNum = event.getColNum();
                ListGridField field = emissionsSummaryDataGrid.getField(colNum);
                //String fieldName = emissionsSummaryDataGrid.getFieldName(colNum);
                String fieldTitle = field.getTitle();

                if (fieldTitle.contains("Report Download")){
                        //String orgId = (String)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                        //field.setLinkURLPrefix("Reports/"+orgId+"/");
                        Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                        //String fileName = record.getAttribute("reportFileName");
                        String emissionsSummaryReportId = (String)record.getAttribute("id");
                        String url = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                        com.google.gwt.user.client.Window.open(url,null,null);
                }
            }
        });        
        
        /*
        emissionsSummaryDataGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                //ListGridRecord record =  (ListGridRecord)event.getRecord();
                ListGridField listGridField = event.getField();
                //ListGridField field = emissionsSummaryDataGrid.getField(colNum);
                //String fieldName = emissionsSummaryDataGrid.getFieldName(colNum);
                String fieldTitle = listGridField.getTitle();

                if (fieldTitle.contains("Download Report")){
                        //String orgId = (String)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                        //field.setLinkURLPrefix("Reports/"+orgId+"/");
                        Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                        //String fileName = record.getAttribute("reportFileName");
                        String emissionsSummaryReportId = (String)event.getRecord().getAttribute("id");
                        String url = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                        com.google.gwt.user.client.Window.open(url,null,null);
                }
            }
        });

        */

        emissionsSummaryVLayout.addMember(emissionsSummaryDataGrid);

        //emissionsSummaryDetailViewer.setFields(stationaryCombustionEmissionsField);
        /*
        emissionsSummaryDetailViewer.setFields(directEmissionsField,
        					stationaryCombustionEmissionsField, mobileCombustionEmissionsField, refridgerationAirConditioningEmissionsField,
        					fireSuppressantEmissionsField, wasteStreamCombustionEmissionsField, purchasedElectricityEmissionsField,
                                                purchasedSteamEmissionsField, employeeBusinessTravelByVehicleEmissionsField,
                                                employeeBusinessTravelByRailEmissionsField,employeeBusinessTravelByBusEmissionsField,
                                                employeeBusinessTravelByAirEmissionsField,employeeCommutingByVehicleEmissionsField,
                                                employeeCommutingByBusEmissionsField,productTransportByVehicleEmissionsField,
                                                productTransportByHeavyDutyTrucksEmissionsField,productTransportByRailEmissionsField,
                                                productTransportByWaterAirEmissionsField,
                                                biomassStationaryCombustionEmissionsField, biomassMobileCombustionEmissionsField
                                                );
        */

        //set the Width and height of emissionsSummaryLayout
	//middleVLayout.addChild(emissionsSummaryLayout);
        //emissionsSummaryLayout.setWidth100();
        //emissionsSummaryLayout.setHeight(200);

//--Chart example
        /*
        PieChart pie = new PieChart(createTable(), createOptions());
        Widget panel = new Widget();
        panel.add(pie);
         *
         */
/*
        List<DetailViewerField> items = new ArrayList<DetailViewerField>();

        items.add(directEmissionsField);
        items.add(stationaryCombustionEmissionsField);
        items.add(mobileCombustionEmissionsField);
        items.add(refridgerationAirConditioningEmissionsField);
        items.add(fireSuppressantEmissionsField);
        items.add(wasteStreamCombustionEmissionsField);

        items.add(purchasedElectricityEmissionsField);
        items.add(purchasedSteamEmissionsField);

        items.add(employeeBusinessTravelByVehicleEmissionsField);
        items.add(employeeBusinessTravelByRailEmissionsField);
        items.add(employeeBusinessTravelByBusEmissionsField);
        items.add(employeeBusinessTravelByAirEmissionsField);
        items.add(employeeCommutingByVehicleEmissionsField);
        items.add(employeeCommutingByBusEmissionsField);

        items.add(productTransportByVehicleEmissionsField);
        items.add(productTransportByHeavyDutyTrucksEmissionsField);
        items.add(productTransportByRailEmissionsField);
        items.add(productTransportByWaterAirEmissionsField);

        items.add(biomassStationaryCombustionEmissionsField);
        items.add(biomassMobileCombustionEmissionsField);


        final DetailViewerField[] fitems = new DetailViewerField[items.size()];
        items.toArray(fitems);
*//*
        emissionsSummaryDataGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                displayDetailInfo(emissionsSummaryDataGrid, fitems);
            }
        });
*/
        final Tab emissionsSummaryTab = new Tab("Emissions Summary");
        emissionsSummaryTab.setPane(emissionsSummaryVLayout);

//---Adding Mobile Combustion tab to tabSet
        tabSet.addTab(emissionsSummaryTab);

 }

public static void initializeValidators(){

    currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
    currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
    //orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
    //SC.say("inventoryYearBeginDate :"+inventoryYearBeginDate);
    //SC.say("inventoryYearEndDate :"+inventoryYearEndDate);
    
    //DateRangeValidator validateDateRange = new DateRangeValidator();
    validateDateRange.setMin(currentInventoryBeginDateMin);
    validateDateRange.setMax(currentInventoryEndDateMax);
    validateDateRange.setErrorMessage("Date is not within reporting period");
    
    //IsFloatValidator floatValidator = new IsFloatValidator();
    floatValidator.setErrorMessage("Not a valid float value");

    //FloatPrecisionValidator floatPrecisionValidator = new FloatPrecisionValidator();
    floatPrecisionValidator.setErrorMessage("Need to have 2 decimal places");

}
private HashMap getInitialValues() {
    HashMap formDefaultValue = new HashMap();
    formDefaultValue.put("fuelUsedBeginDate", currentInventoryBeginDateMin);
    formDefaultValue.put("fuelUsedEndDate", currentInventoryEndDateMax);
    return formDefaultValue;
}

private void stationaryCombustionTab() {

        //--Get the mobile Combustion list grid fields
        final ListGridField[] listGridFields = getStationaryCombustionListGridFields();        
        stationaryCombustionDataGrid.setFields(listGridFields);

        stationaryCombustionLayout.setWidth100();
        stationaryCombustionLayout.setHeight100();
        
        //stationaryCombustionDataGrid.setFields(sourceDescriptionField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);
        TabLayout stationaryCombustionTabLayout = new TabLayout("Current stationary combustion sources",stationaryCombustionForm,
                                                                stationaryCombustionFormWindow, stationaryCombustionDataGrid);

//--Defining Stationary Combustion tab
        final Tab stationaryCombustionTab = new Tab("Stationary Combustion");
        stationaryCombustionTab.setPane(stationaryCombustionTabLayout);

//---Adding Stationary Combustion tab to topTab
        stationaryCombustionTabSet.addTab(stationaryCombustionTab);
        stationaryCombustionLayout.addMember(stationaryCombustionTabSet);

}
private void initStationaryCombustionEditForm() {

    FormItem[] formItemFields = getStationaryCombustionFormFields();
    stationaryCombustionForm.setItems(formItemFields);

    //stationaryCombustionForm.setItems(fuelSourceDescription, fuelTypeItem, fuelQuantityItem, fuelUnitItem, fuelUsedBeginDateItem, fuelUsedEndDateItem);
    setIbLFormWindow("Please enter stationary combustion source information:", stationaryCombustionForm,
                      stationaryCombustionFormWindow, stationaryCombustionDataGrid );
}

private void mobileCombustionTab() {
        //--Get the mobile Combustion list grid fields
        final ListGridField[] listGridFields = getMobileCombustionListGridFields();

        mobileCombustionDataGrid.setFields(listGridFields);
        //mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);

        //-set Height and Width to 100 for mobile Combustion layout
        mobileCombustionLayout.setWidth100();
        mobileCombustionLayout.setHeight100();

        TabLayout mobileCombustionTabLayout = new TabLayout("Current mobile combustion sources", mobileCombustionForm,
                                                            mobileCombustionFormWindow, mobileCombustionDataGrid);
//--Defining Mobile Combustion tab
        final Tab mobileCombustionTab = new Tab("Mobile Combustion");
        mobileCombustionTab.setPane(mobileCombustionTabLayout);

//---Adding Mobile Combustion tab to tabSet
        mobileCombustionTabSet.addTab(mobileCombustionTab);
        mobileCombustionLayout.addMember(mobileCombustionTabSet);
}
private void initMobileCombustionEditForm() {

    FormItem[] formItemFields = getMobileCombustionFormFields();
    mobileCombustionForm.setItems(formItemFields);
    //mobileCombustionForm.setItems(fuelSourceDescriptionItem ,vehicleTypeItem, vehicleYearItem, fuelTypeItem, fuelQuantityItem, fuelUnitItem, milesTravelledItem, bioFuelPercentItem,ethanolPercentItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    setIbLFormWindow("Please enter mobile combustion source information:", mobileCombustionForm,
                      mobileCombustionFormWindow, mobileCombustionDataGrid );
 }

private void refridgerationAirConditioningTab() {

	refridgerationAirConditioningLayout.setWidth100();
	refridgerationAirConditioningLayout.setHeight100();

//-- Material Balance Method
        final ListGridField[] listGridFields_1 = getRefridgerationAirConditioningListGridFields_1();
        refridgerationAirConditioningDataGrid_1.setFields(listGridFields_1);

	TabLayout refridgerationAirConditioningLayout_1 = new TabLayout("Current refridgeration & air conditioning sources", refridgerationAirConditioningForm_1,
	                                                     refridgerationAirConditioningFormWindow_1, refridgerationAirConditioningDataGrid_1);        
//-- Simplified Material Balance Method
        final ListGridField[] listGridFields_2 = getRefridgerationAirConditioningListGridFields_2();
        refridgerationAirConditioningDataGrid_2.setFields(listGridFields_2);

        TabLayout refridgerationAirConditioningLayout_2 = new TabLayout("Current refridgeration & air conditioning sources", refridgerationAirConditioningForm_2,
	                                                     refridgerationAirConditioningFormWindow_2, refridgerationAirConditioningDataGrid_2);        
//-- Screening Method
        final ListGridField[] listGridFields_3 = getRefridgerationAirConditioningListGridFields_3("RefridgerationAirConditioning");
        refridgerationAirConditioningDataGrid_3.setFields(listGridFields_3);

        TabLayout refridgerationAirConditioningLayout_3 = new TabLayout("Current refridgeration & air conditioning sources", refridgerationAirConditioningForm_3,
	                                                     refridgerationAirConditioningFormWindow_3, refridgerationAirConditioningDataGrid_3);
        
//--Defining Refridgeration Air Conditioning Combustion tab
        final Tab refridgerationAirConditioningTab_1 = new Tab("Company-Wide Material Balance Method");
        refridgerationAirConditioningTab_1.setPane(refridgerationAirConditioningLayout_1);

        final Tab refridgerationAirConditioningTab_2 = new Tab("Company-Wide Simplified Material Balance Method");
        refridgerationAirConditioningTab_2.setPane(refridgerationAirConditioningLayout_2);

        final Tab refridgerationAirConditioningTab_3 = new Tab("Source Level Screening Method");
        refridgerationAirConditioningTab_3.setPane(refridgerationAirConditioningLayout_3);

//---Adding Refridgeration Air Conditioning Combustion tabs to refridgerationAirConditioningTabSet
        refridgerationAirConditioningTabSet.addTab(refridgerationAirConditioningTab_1);
        refridgerationAirConditioningTabSet.addTab(refridgerationAirConditioningTab_2);
        refridgerationAirConditioningTabSet.addTab(refridgerationAirConditioningTab_3);
        
        //--Tab selected handler for all tabs
        refridgerationAirConditioningTabSet.addTabSelectedHandler(refridgerationTabSelectedHandler);

        refridgerationAirConditioningLayout.addMember(refridgerationAirConditioningTabSet);

 }
private void initRefridgerationAirConditioningEditForm_123() {

//-- Form_1 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_1 = getRefridgerationAirConditioningFormFields_1("RefridgerationAirConditioning");
    refridgerationAirConditioningForm_1.setItems(formItemFields_1);
    refridgerationAirConditioningForm_1.hideItem("methodType");
    setIbLFormWindow("Please enter Refridgeration & Air Conditioning source information:", refridgerationAirConditioningForm_1,
                          refridgerationAirConditioningFormWindow_1, refridgerationAirConditioningDataGrid_1 );

//-- Form_2 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_2 = getRefridgerationAirConditioningFormFields_2("RefridgerationAirConditioning");
    refridgerationAirConditioningForm_2.setItems(formItemFields_2);
    refridgerationAirConditioningForm_2.hideItem("methodType");

    setIbLFormWindow("Please enter Refridgeration & Air Conditioning source information:", refridgerationAirConditioningForm_2,
                          refridgerationAirConditioningFormWindow_2, refridgerationAirConditioningDataGrid_2 );


//-- Form_3 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_3 = getRefridgerationAirConditioningFormFields_3("RefridgerationAirConditioning");
    refridgerationAirConditioningForm_3.setItems(formItemFields_3);
    refridgerationAirConditioningForm_3.hideItem("methodType");
    
    setIbLFormWindow("Please enter Refridgeration & Air Conditioning source information:", refridgerationAirConditioningForm_3,
                          refridgerationAirConditioningFormWindow_3, refridgerationAirConditioningDataGrid_3 );

 }

private void fireSuppressionTab() {

        fireSuppressionLayout.setWidth100();
        fireSuppressionLayout.setHeight100();

//-- Material Balance Method
        final ListGridField[] listGridFields_1 = getRefridgerationAirConditioningListGridFields_1();
        fireSuppressionDataGrid_1.setFields(listGridFields_1);

	TabLayout fireSuppressionLayout_1 = new TabLayout("Current Fire Suppression sources", fireSuppressionForm_1,
	                                                     fireSuppressionFormWindow_1, fireSuppressionDataGrid_1);
//-- Simplified Material Balance Method
        final ListGridField[] listGridFields_2 = getRefridgerationAirConditioningListGridFields_2();
        fireSuppressionDataGrid_2.setFields(listGridFields_2);

        TabLayout fireSuppressionLayout_2 = new TabLayout("Current Fire Suppression sources", fireSuppressionForm_2,
	                                                     fireSuppressionFormWindow_2, fireSuppressionDataGrid_2);
//-- Screening Method
        final ListGridField[] listGridFields_3 = getRefridgerationAirConditioningListGridFields_3("FireSuppression");
        fireSuppressionDataGrid_3.setFields(listGridFields_3);

        TabLayout fireSuppressionLayout_3 = new TabLayout("Current Fire Suppression sources", fireSuppressionForm_3,
	                                                     fireSuppressionFormWindow_3, fireSuppressionDataGrid_3);

        final Tab fireSuppressionTab_1 = new Tab("Company-Wide Material Balance Method");
        fireSuppressionTab_1.setPane(fireSuppressionLayout_1);

        final Tab fireSuppressionTab_2 = new Tab("Company-Wide Simplified Material Balance Method");
        fireSuppressionTab_2.setPane(fireSuppressionLayout_2);

        final Tab fireSuppressionTab_3 = new Tab("Source Level Screening Method");
        fireSuppressionTab_3.setPane(fireSuppressionLayout_3);

//---Adding Fire Suppression tab to tabSet
        fireSuppressionTabSet.addTab(fireSuppressionTab_1);
        fireSuppressionTabSet.addTab(fireSuppressionTab_2);
        fireSuppressionTabSet.addTab(fireSuppressionTab_3);
        
        fireSuppressionTabSet.addTabSelectedHandler(fireSuppressionTabSelectedHandler);
        fireSuppressionLayout.addMember(fireSuppressionTabSet);
 }
private void initFireSuppressionEditForm_123() {

//-- Form_1 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_1 = getRefridgerationAirConditioningFormFields_1("FireSuppression");
    fireSuppressionForm_1.setItems(formItemFields_1);
    fireSuppressionForm_1.hideItem("methodType");
    setIbLFormWindow("Please enter Fire Suppression source information:", fireSuppressionForm_1,
                          fireSuppressionFormWindow_1, fireSuppressionDataGrid_1 );

//-- Form_2 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_2 = getRefridgerationAirConditioningFormFields_2("FireSuppression");
    fireSuppressionForm_2.setItems(formItemFields_2);
    fireSuppressionForm_2.hideItem("methodType");

    setIbLFormWindow("Please enter Fire Suppression source information:", fireSuppressionForm_2,
                          fireSuppressionFormWindow_2, fireSuppressionDataGrid_2 );

//-- Form_3 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_3 = getRefridgerationAirConditioningFormFields_3("FireSuppression");
    fireSuppressionForm_3.setItems(formItemFields_3);
    fireSuppressionForm_3.hideItem("methodType");

    setIbLFormWindow("Please enter Fire Suppression source information:", fireSuppressionForm_3,
                          fireSuppressionFormWindow_3, fireSuppressionDataGrid_3 );

 }

private void purchasedElectricityTab() {

        purchasedElectricityLayout.setWidth100();
        purchasedElectricityLayout.setHeight100();

        final ListGridField[] listGridFields = getPurchasedElecricityListGridFields();
        purchasedElectricityDataGrid.setFields(listGridFields);
        
	TabLayout purchasedElectricityTabLayout = new TabLayout("Current purchased electricity sources", purchasedElectricityForm,
	                                                     purchasedElectricityFormWindow, purchasedElectricityDataGrid);
//--Defining Purchased Electricity tab
        final Tab purchasedElectricityTab = new Tab("Purchased Electricity");
        purchasedElectricityTab.setPane(purchasedElectricityTabLayout);

//---Adding Purchased Electricity tab to tabSet
        purchasedElectricityTabSet.addTab(purchasedElectricityTab);
        purchasedElectricityLayout.addMember(purchasedElectricityTabSet);


}
private void initPurchasedElectricityEditForm() {

//-- Form fields  -------------------------------------------------------------------
    FormItem[] formItemFields = getPurchasedElectricityFormFields();
    purchasedElectricityForm.setItems(formItemFields);

    setIbLFormWindow("Please enter purchased electricity information:", purchasedElectricityForm,
                          purchasedElectricityFormWindow, purchasedElectricityDataGrid );

 }

private void purchasedSteamTab() {

        purchasedSteamLayout.setWidth100();
        purchasedSteamLayout.setHeight100();

        final ListGridField[] listGridFields = getPurchasedSteamListGridFields();
        purchasedSteamDataGrid.setFields(listGridFields);

	TabLayout purchasedSteamTabLayout = new TabLayout("Current purchased steam sources", purchasedSteamForm,
	                                                     purchasedSteamFormWindow, purchasedSteamDataGrid);

//--Defining Purchased Steam tab
        final Tab purchasedSteamTab = new Tab("Purchased Steam");
        purchasedSteamTab.setPane(purchasedSteamTabLayout);

//---Adding Purchased Steam tab to tabSet
        purchasedSteamTabSet.addTab(purchasedSteamTab);
        purchasedSteamLayout.addMember(purchasedSteamTabSet);
}
private void initPurchasedSteamEditForm() {
//-- Form fields  -------------------------------------------------------------------
    FormItem[] formItemFields = getPurchasedSteamFormFields();
    purchasedSteamForm.setItems(formItemFields);

    setIbLFormWindow("Please enter purchased steam information:", purchasedSteamForm,
                          purchasedSteamFormWindow, purchasedSteamDataGrid );

 }

//--employeeBusinessTravelByVehicle
private void employeeBusinessTravelByVehicleTab() {

        //VLayout employeeBusinessTravelByVehicleLayout = new VLayout(15);
        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeBusinessTravelByVehicle");
        employeeBusinessTravelByVehicleDataGrid.setFields(listGridFields);

	TabLayout employeeBusinessTravelByVehicleLayout = new TabLayout("Current Sources of Employee Business Travel By Vehicle", employeeBusinessTravelByVehicleForm,
	                                                     employeeBusinessTravelByVehicleFormWindow, employeeBusinessTravelByVehicleDataGrid);
//--Defining employeeBusinessTravel By Vehicle
        final Tab employeeBusinessTravelByVehicleTab = new Tab("Employee Business Travel By Vehicle");
        employeeBusinessTravelByVehicleTab.setPane(employeeBusinessTravelByVehicleLayout);

//---Adding employeeBusinessTravel By Vehicle tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByVehicleTab);
            //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByVehicleTab);
}
private void initEmployeeBusinessTravelByVehicleEditForm() {
    FormItem[] formItemFields = getOptionalSourceFormFields("employeeBusinessTravelByVehicle");
    employeeBusinessTravelByVehicleForm.setItems(formItemFields);
    employeeBusinessTravelByVehicleForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee business travel by vehicle information:", employeeBusinessTravelByVehicleForm,
                          employeeBusinessTravelByVehicleFormWindow, employeeBusinessTravelByVehicleDataGrid );
 }

//--employeeBusinessTravelByRail
private void employeeBusinessTravelByRailTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeBusinessTravelByRail");
        employeeBusinessTravelByRailDataGrid.setFields(listGridFields);

	TabLayout employeeBusinessTravelByRailLayout = new TabLayout("Current Sources of Employee Business Travel By Rail", employeeBusinessTravelByRailForm,
	                                                     employeeBusinessTravelByRailFormWindow, employeeBusinessTravelByRailDataGrid);
//--Defining employeeBusinessTravel By Rail
        final Tab employeeBusinessTravelByRailTab = new Tab("Employee Business Travel By Rail");
        employeeBusinessTravelByRailTab.setPane(employeeBusinessTravelByRailLayout);

//---Adding employeeBusinessTravel By Rail tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByRailTab);
}
private void initEmployeeBusinessTravelByRailEditForm() {
    FormItem[] formItemFields = getOptionalSourceFormFields("employeeBusinessTravelByRail");
    employeeBusinessTravelByRailForm.setItems(formItemFields);
    employeeBusinessTravelByRailForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee business travel by rail information:", employeeBusinessTravelByRailForm,
                          employeeBusinessTravelByRailFormWindow, employeeBusinessTravelByRailDataGrid );
 }

//--employeeBusinessTravelByBus
private void employeeBusinessTravelByBusTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeBusinessTravelByBus");
        employeeBusinessTravelByBusDataGrid.setFields(listGridFields);
	TabLayout employeeBusinessTravelByBusLayout = new TabLayout("Current Sources of Employee Business Travel By Bus", employeeBusinessTravelByBusForm,
	                                                     employeeBusinessTravelByBusFormWindow, employeeBusinessTravelByBusDataGrid);
//--Defining employeeBusinessTravel By Bus
        final Tab employeeBusinessTravelByBusTab = new Tab("Employee Business Travel By Bus");
        employeeBusinessTravelByBusTab.setPane(employeeBusinessTravelByBusLayout);

//---Adding employeeBusinessTravel By Bus tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByBusTab);
        //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByBusTab);
}
private void initEmployeeBusinessTravelByBusEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("employeeBusinessTravelByBus");
    employeeBusinessTravelByBusForm.setItems(formItemFields);
    employeeBusinessTravelByBusForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee business travel by bus information:", employeeBusinessTravelByBusForm,
                          employeeBusinessTravelByBusFormWindow, employeeBusinessTravelByBusDataGrid );
 }

//--employeeBusinessTravelByAir
private void employeeBusinessTravelByAirTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeBusinessTravelByAir");
        employeeBusinessTravelByAirDataGrid.setFields(listGridFields);
	TabLayout employeeBusinessTravelByAirLayout = new TabLayout("Current Sources of Employee Business Travel By Air", employeeBusinessTravelByAirForm,
	                                                     employeeBusinessTravelByAirFormWindow, employeeBusinessTravelByAirDataGrid);
    
//--Defining employeeBusinessTravel By Air
        final Tab employeeBusinessTravelByAirTab = new Tab("Employee Business Travel By Air");
        employeeBusinessTravelByAirTab.setPane(employeeBusinessTravelByAirLayout);

//---Adding employeeBusinessTravel By Air tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByAirTab);
        //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByAirTab);

        //--Tab selected handler for all tabs
        employeeBusinessTravelTabSet.addTabSelectedHandler(employeeBusinessTravelTabSelectedHandler);
}
private void initEmployeeBusinessTravelByAirEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("employeeBusinessTravelByAir");
    employeeBusinessTravelByAirForm.setItems(formItemFields);
    employeeBusinessTravelByAirForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee business travel by air information:", employeeBusinessTravelByAirForm,
                          employeeBusinessTravelByAirFormWindow, employeeBusinessTravelByAirDataGrid );
 }

//--employeeCommutingByVehicle
private void employeeCommutingByVehicleTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeCommutingByVehicle");
        employeeCommutingByVehicleDataGrid.setFields(listGridFields);
	TabLayout employeeCommutingByVehicleLayout = new TabLayout("Current Sources of Employee Commuting By Vehicle", employeeCommutingByVehicleForm,
	                                                     employeeCommutingByVehicleFormWindow, employeeCommutingByVehicleDataGrid);
//--Defining employeeCommutingByVehicle
        final Tab employeeCommutingByVehicleTab = new Tab("Employee Commuting By Vehicle");
        employeeCommutingByVehicleTab.setPane(employeeCommutingByVehicleLayout);

//---Adding employeeCommutingByVehicle tab to tabSet
        employeeCommutingTabSet.addTab(employeeCommutingByVehicleTab);
        //tabSet.addTab(employeeCommutingTabSet);
        //tabSet.addTab(employeeCommutingByVehicleTab);
}
private void initEmployeeCommutingByVehicleEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("employeeCommutingByVehicle");
    employeeCommutingByVehicleForm.setItems(formItemFields);
    employeeCommutingByVehicleForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee commuting by vehicle information:", employeeCommutingByVehicleForm,
                          employeeCommutingByVehicleFormWindow, employeeCommutingByVehicleDataGrid );
 }

//--employeeCommutingByRail
private void employeeCommutingByRailTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeCommutingByRail");
        employeeCommutingByRailDataGrid.setFields(listGridFields);
	TabLayout employeeCommutingByRailLayout = new TabLayout("Current Sources of Employee Commuting By Rail", employeeCommutingByRailForm,
	                                                     employeeCommutingByRailFormWindow, employeeCommutingByRailDataGrid);
//--Defining employeeCommutingByRail
        final Tab employeeCommutingByRailTab = new Tab("Employee Commuting By Rail");
        employeeCommutingByRailTab.setPane(employeeCommutingByRailLayout);

//---Adding employeeCommutingByRail tab to tabSet
       employeeCommutingTabSet.addTab(employeeCommutingByRailTab);
        //tabSet.addTab(employeeCommutingTabSet);
        //tabSet.addTab(employeeCommutingByRailTab);
}
private void initEmployeeCommutingByRailEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("employeeCommutingByRail");
    employeeCommutingByRailForm.setItems(formItemFields);
    employeeCommutingByRailForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee commuting by rail information:", employeeCommutingByRailForm,
                          employeeCommutingByRailFormWindow, employeeCommutingByRailDataGrid );
 }

//--employeeCommutingByBus
private void employeeCommutingByBusTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeCommutingByBus");
        employeeCommutingByBusDataGrid.setFields(listGridFields);
	TabLayout employeeCommutingByBusLayout = new TabLayout("Current Sources of Employee Commuting By Bus", employeeCommutingByBusForm,
	                                                     employeeCommutingByBusFormWindow, employeeCommutingByBusDataGrid);
//--Defining employeeCommutingByBus
        final Tab employeeCommutingByBusTab = new Tab("Employee Commuting By Bus");
        employeeCommutingByBusTab.setPane(employeeCommutingByBusLayout);

//---Adding employeeCommutingByBus tab to tabSet
        employeeCommutingTabSet.addTab(employeeCommutingByBusTab);
        //tabSet.addTab(employeeCommutingTabSet);
        //tabSet.addTab(employeeCommutingByBusTab);

        //--Tab selected handler
        employeeCommutingTabSet.addTabSelectedHandler(employeeCommutingTabSelectedHandler);
}
private void initEmployeeCommutingByBusEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("employeeCommutingByBus");
    employeeCommutingByBusForm.setItems(formItemFields);
    employeeCommutingByBusForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter employee commuting by bus information:", employeeCommutingByBusForm,
                          employeeCommutingByBusFormWindow, employeeCommutingByBusDataGrid );
 }

//--productTransportByVehicle
private void productTransportByVehicleTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("productTransportByVehicle");
        productTransportByVehicleDataGrid.setFields(listGridFields);
	TabLayout productTransportByVehicleLayout = new TabLayout("Current Sources of Product Transport - By Vehicle", productTransportByVehicleForm,
	                                                     productTransportByVehicleFormWindow, productTransportByVehicleDataGrid);

//--Defining productTransportByVehicle
        final Tab productTransportByVehicleTab = new Tab("Product Transport - By Vehicle");
        productTransportByVehicleTab.setPane(productTransportByVehicleLayout);

//---Adding productTransportByVehicle tab to tabSet
        productTransportTabSet.addTab(productTransportByVehicleTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByVehicleTab);
}
private void initProductTransportByVehicleEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("productTransportByVehicle");
    productTransportByVehicleForm.setItems(formItemFields);
    productTransportByVehicleForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter Product Transport By Vehicle information:", productTransportByVehicleForm,
                          productTransportByVehicleFormWindow, productTransportByVehicleDataGrid );
 }

//--productTransportByHeavyDutyTrucks
private void productTransportByHeavyDutyTrucksTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("productTransportByHeavyDutyTrucks");
        productTransportByHeavyDutyTrucksDataGrid.setFields(listGridFields);
	TabLayout productTransportByHeavyDutyTrucksLayout = new TabLayout("Current Sources of Product Transport - By Heavy Duty Trucks", productTransportByHeavyDutyTrucksForm,
	                                                     productTransportByHeavyDutyTrucksFormWindow, productTransportByHeavyDutyTrucksDataGrid);

//--Defining productTransportByHeavyDutyTrucks
        final Tab productTransportByHeavyDutyTrucksTab = new Tab("Product Transport - By Heavy Duty Trucks");
        productTransportByHeavyDutyTrucksTab.setPane(productTransportByHeavyDutyTrucksLayout);

//---Adding productTransportByHeavyDutyTrucks tab to tabSet
        productTransportTabSet.addTab(productTransportByHeavyDutyTrucksTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByHeavyDutyTrucksTab);
}
private void initProductTransportByHeavyDutyTrucksEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("productTransportByHeavyDutyTrucks");
    productTransportByHeavyDutyTrucksForm.setItems(formItemFields);
    productTransportByHeavyDutyTrucksForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter Product Transport By Heavy Duty Trucks information:", productTransportByHeavyDutyTrucksForm,
                          productTransportByHeavyDutyTrucksFormWindow, productTransportByHeavyDutyTrucksDataGrid );
 }

//--productTransportByRail
private void productTransportByRailTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("productTransportByRail");
        productTransportByRailDataGrid.setFields(listGridFields);
	TabLayout productTransportByRailLayout = new TabLayout("Current Sources of Product Transport - By Rail", productTransportByRailForm,
	                                                     productTransportByRailFormWindow, productTransportByRailDataGrid);
//--Defining productTransportByRail
        final Tab productTransportByRailTab = new Tab("Product Transport - By Rail");
        productTransportByRailTab.setPane(productTransportByRailLayout);

//---Adding productTransportByRail tab to tabSet
        productTransportTabSet.addTab(productTransportByRailTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByRailTab);

        //-Tab selected handler for all tabs.
        productTransportTabSet.addTabSelectedHandler(productTransportTabSelectedHandler);
}
private void initProductTransportByRailEditForm() {

    FormItem[] formItemFields = getOptionalSourceFormFields("productTransportByRail");
    productTransportByRailForm.setItems(formItemFields);
    productTransportByRailForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter Product Transport By Rail information:", productTransportByRailForm,
                          productTransportByRailFormWindow, productTransportByRailDataGrid );
 }

//--productTransportByWaterAir
private void productTransportByWaterAirTab() {

        final ListGridField[] listGridFields = getOptionalSourceListGridFields("productTransportByWaterAir");
        productTransportByWaterAirDataGrid.setFields(listGridFields);
	TabLayout productTransportByWaterAirLayout = new TabLayout("Current Sources of Product Transport - By Water Air", productTransportByWaterAirForm,
	                                                     productTransportByWaterAirFormWindow, productTransportByWaterAirDataGrid);
//--Defining productTransportByWaterAir
        final Tab productTransportByWaterAirTab = new Tab("Product Transport - By Water or Air");
        productTransportByWaterAirTab.setPane(productTransportByWaterAirLayout);

//---Adding productTransportByWaterAir tab to tabSet
        productTransportTabSet.addTab(productTransportByWaterAirTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByWaterAirTab);
}
private void initProductTransportByWaterAirEditForm() {
    FormItem[] formItemFields = getOptionalSourceFormFields("productTransportByWaterAir");
    productTransportByWaterAirForm.setItems(formItemFields);
    productTransportByWaterAirForm.hideItem("optionalSourceType");
    setIbLFormWindow("Please enter Product Transport By Water/Air information:", productTransportByWaterAirForm,
                          productTransportByWaterAirFormWindow, productTransportByWaterAirDataGrid );
 }

//--wasteStreamCombustion
private void wasteStreamCombustionTab() {

        final ListGridField[] listGridFields = getWasteStreamCombustionListGridFields();
        wasteStreamCombustionDataGrid.setFields(listGridFields);
	TabLayout wasteStreamCombustionTabLayout = new TabLayout("Current Sources of Waste Stream Combustion", wasteStreamCombustionForm,
	                                                     wasteStreamCombustionFormWindow, wasteStreamCombustionDataGrid);
        wasteStreamCombustionLayout.setWidth100();
        wasteStreamCombustionLayout.setHeight100();

//--Defining wasteStreamCombustion
        final Tab wasteStreamCombustionTab = new Tab("Waste Stream Combustion");
        wasteStreamCombustionTab.setPane(wasteStreamCombustionTabLayout);

//---Adding wasteStreamCombustion tab to tabSet
        wasteStreamCombustionTabSet.addTab(wasteStreamCombustionTab);
        wasteStreamCombustionLayout.addMember(wasteStreamCombustionTabSet);

}
private void initWasteStreamCombustionEditForm() {

    FormItem[] formItemFields = getWasteStreamCombustionFormFields();
    wasteStreamCombustionForm.setItems(formItemFields);
    setIbLFormWindow("Please enter Waste Stream Combustion information:", wasteStreamCombustionForm,
                          wasteStreamCombustionFormWindow, wasteStreamCombustionDataGrid );

 }

private void initOrganizationProfileEditForm() {

    organizationProfileForm.setCellPadding(5);
    //organizationProfileForm.setDefaultWidth(800);
    organizationProfileForm.setWidth("50%");
    organizationProfileForm.setNumCols(2);
    //organizationProfileForm.setColWidths("25%");

    organizationProfileVLayout.setWidth100();
    organizationProfileVLayout.setHeight100();
    //organizationProfileForm.setBorder("1px double orange");
    //organizationProfileForm.setTitleOrientation(TitleOrientation.TOP);
    organizationProfileForm.setDataSource(organizationDS);    
    //organizationProfileForm.setDisableValidation(Boolean.TRUE);
    organizationProfileForm.setAlign(Alignment.CENTER);

    //organizationProfileForm.animateShow(AnimationEffect.SLIDE);
    //organizationProfileForm.hide();
    //organizationProfileForm.setTitle("Please your organization information:");

//-- setValidators for the forms for common types.
    //initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    TextItem organizationNameItem = new TextItem("organizationName");
    organizationNameItem.setWidth(200);
    organizationNameItem.setColSpan(2);
    //organizationNameItem.

/*
    DateItem currentInventoryBeginDateItem = new DateItem();
    currentInventoryBeginDateItem.setName("currentInventoryBeginDate");
    //currentInventoryBeginDateItem.setValidateOnExit(Boolean.FALSE);
    //currentInventoryBeginDateItem.setValidateOnChange(Boolean.FALSE);
    //currentInventoryBeginDateItem.setEditorType();
    //currentInventoryBeginDateItem.setUseTextField(Boolean.TRUE);
    
    DateItem currentInventoryEndDateItem = new DateItem();
    currentInventoryEndDateItem.setName("currentInventoryEndDate");
    //currentInventoryEndDateItem.setValidateOnExit(Boolean.FALSE);
    //currentInventoryEndDateItem.setValidateOnChange(Boolean.FALSE);
*/
    TextItem organizationStreetAddress1Item = new TextItem("organizationStreetAddress1");
    organizationStreetAddress1Item.setValidateOnChange(Boolean.TRUE);
    //organizationNameItem.setWidth(100);

    TextItem organizationStreetAddress2Item = new TextItem("organizationStreetAddress2");
    organizationStreetAddress2Item.setRequired(Boolean.FALSE);
    organizationStreetAddress2Item.setValidateOnChange(Boolean.TRUE);
    //organizationNameItem.setWidth(200);

    TextItem organizationCityItem = new TextItem("organizationCity");
    organizationCityItem.setValidateOnChange(Boolean.TRUE);
    //organizationNameItem.setWidth(50);

    TextItem organizationStateItem = new TextItem("organizationState");
    organizationStateItem.setValidateOnChange(Boolean.TRUE);
    //organizationNameItem.setWidth(20);

    TextItem organizationZipCodeItem = new TextItem("organizationZipCode");
    organizationZipCodeItem.setValidators(new RegExpValidator("^\\d{5}(-\\d{4})?$"));
    organizationZipCodeItem.setValidateOnChange(Boolean.TRUE);
    //ZipCodeUSType organizationZipCodeItem = new ZipCodeUSType();
    organizationNameItem.setWidth(20);

    TextItem organizationCountryItem = new TextItem("organizationCountry");
    organizationCountryItem.setValidateOnChange(Boolean.TRUE);
    organizationNameItem.setWidth(200);

    TextItem organizationWebsiteItem = new TextItem("organizationWebsite");
    organizationWebsiteItem.setValidateOnChange(Boolean.TRUE);
    organizationNameItem.setWidth(200);

    TextItem organizationHQItem = new TextItem("organizationHQ");
    organizationHQItem.setValidateOnChange(Boolean.TRUE);
    organizationNameItem.setWidth(20);

    TextItem pointOfContactItem = new TextItem("pointOfContact");
    pointOfContactItem.setValidateOnChange(Boolean.TRUE);
    organizationNameItem.setWidth(200);

    //organizationProfileForm.setIsGroup(Boolean.TRUE);
    //organizationProfileForm.setGroupTitle("Update your organization profile");
    //organizationProfileForm.setRedrawOnResize(true);

    organizationProfileForm.setItems(organizationNameItem, 
                                        organizationStreetAddress1Item,
                                        organizationCityItem,organizationStateItem,organizationZipCodeItem,
                                        organizationCountryItem,organizationWebsiteItem,pointOfContactItem);

    final IButton organizationProfileCancelButton = new IButton();
    final IButton organizationProfileSaveButton = new IButton();

    organizationProfileSaveButton.setTitle("SAVE");
    organizationProfileSaveButton.setTooltip("Save");
    organizationProfileSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        //if (!organizationProfileForm.getErrors().isEmpty()|| !organizationProfileForm.validate()){
        if (!organizationProfileForm.validate()){
            SC.say("Please clear errors before submitting this information!");
        }
        else {
            //Record organizationRecord = organizationProfileForm.getValuesAsRecord();
            //organizationProfileForm.saveData();
            organizationProfileForm.saveData(
                          new DSCallback() {
                                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                                        UserOrganizationHeader.organizationSelectForm.fetchData();
                                        SC.say("Update Complete!");
                            }
                          });
        }
      }
    });

    organizationProfileCancelButton.setTitle("CANCEL");
    organizationProfileCancelButton.setTooltip("Cancel");
    organizationProfileCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        //organizationProfileForm.saveData();
        //organizationProfileVLayout.hide();
        Criteria fetchCriteria = new Criteria();

        String orgName = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
        fetchCriteria.addCriteria("organizationName", orgName);
        organizationProfileForm.fetchData(fetchCriteria);
      }
    });

    HLayout buttons = new HLayout();
    //buttons.setWidth("50%");
    //buttons.setAlign(Alignment.CENTER);
    buttons.addMember(organizationProfileCancelButton);
    buttons.addMember(organizationProfileSaveButton);

    Label updateOrganizationProfileLabel = new Label("Organization Profile");
    updateOrganizationProfileLabel.setHeight(15);
    updateOrganizationProfileLabel.setWidth100();
    updateOrganizationProfileLabel.setAlign(Alignment.LEFT);
    updateOrganizationProfileLabel.setStyleName("labels");

    organizationProfileVLayout.setAlign(Alignment.CENTER);
    organizationProfileVLayout.addMember(updateOrganizationProfileLabel);
    organizationProfileVLayout.addMember(organizationProfileForm);
    organizationProfileVLayout.addMember(buttons);
    organizationProfileVLayout.setStyleName("generalVLayout");
    
    /*
    organizationProfileFormWindow.setShowShadow(true);
    //organizationProfileFormWindow.setShowTitle(false);
    //organizationProfileFormWindow.setIsModal(true);
    organizationProfileFormWindow.setPadding(20);
    organizationProfileFormWindow.setWidth(500);
    organizationProfileFormWindow.setHeight(350);
    //organizationProfileFormWindow.setShowMinimizeButton(false);
    //organizationProfileFormWindow.setShowCloseButton(true);
    organizationProfileFormWindow.setShowModalMask(true);
    organizationProfileFormWindow.centerInPage();
    organizationProfileFormWindow.setTitle("Please update your organization profile: ");
	
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //organizationProfileFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    organizationProfileFormWindow.addItem(dialog);
    */
}

private void initOrganizationInventoryYearEditForm() {

    organizationInventoryYearVLayout.setWidth100();
    organizationInventoryYearVLayout.setHeight100();
    organizationInventoryYearForm.setCellPadding(5);
    //organizationInventoryYearForm.setDefaultWidth(800);
    organizationInventoryYearForm.setWidth("50%");
    organizationInventoryYearForm.setNumCols(2);
    //organizationInventoryYearForm.setColWidths("25%");

    //organizationInventoryYearForm.setBorder("1px double orange");
    //organizationInventoryYearForm.setTitleOrientation(TitleOrientation.TOP);
    organizationInventoryYearForm.setDataSource(organizationDS);
    //organizationInventoryYearForm.setDisableValidation(Boolean.TRUE);
    //organizationInventoryYearForm.setAlign(Alignment.CENTER);

    //organizationInventoryYearForm.animateShow(AnimationEffect.SLIDE);
    //organizationInventoryYearForm.hide();
    //organizationInventoryYearForm.setTitle("Please your organization information:");

//-- setValidators for the forms for common types.
    //initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    //TextItem organizationNameItem = new TextItem("organizationName");
    //organizationNameItem.setWidth(200);

    //organizationNameItem.

    DateItem currentInventoryBeginDateItem = new DateItem();
    currentInventoryBeginDateItem.setName("currentInventoryBeginDate");
    //currentInventoryBeginDateItem.setValidateOnExit(Boolean.FALSE);
    //currentInventoryBeginDateItem.setValidateOnChange(Boolean.FALSE);
    //currentInventoryBeginDateItem.setEditorType();
    //currentInventoryBeginDateItem.setUseTextField(Boolean.TRUE);
    //currentInventoryBeginDateItem.setUseMask(Boolean.TRUE);
    
    final DateItem currentInventoryEndDateItem = new DateItem();
    currentInventoryEndDateItem.setName("currentInventoryEndDate");
    //currentInventoryEndDateItem.setValidateOnExit(Boolean.FALSE);
    //currentInventoryEndDateItem.setValidateOnChange(Boolean.FALSE);
    //currentInventoryEndDateItem.setUseTextField(Boolean.TRUE);
    //currentInventoryEndDateItem.setUseMask(Boolean.TRUE);

    organizationInventoryYearForm.setItems(currentInventoryBeginDateItem, currentInventoryEndDateItem);

    final IButton organizationInventoryYearCancelButton = new IButton();
    final IButton organizationInventoryYearSaveButton = new IButton();

    //--Confirmation message
    final Label confirmationMessage = new Label("Update Complete!");
    /*
    final Portlet cMPortletWindow = new Portlet();
    cMPortletWindow.setShowCloseConfirmationMessage(Boolean.FALSE);
    cMPortletWindow.addItem(confirmationMessage);
    cMPortletWindow.animateShow(AnimationEffect.FADE, null, 100);
    cMPortletWindow.setAnimateFadeTime(1000);
    cMPortletWindow.hide();
    */
    
    confirmationMessage.setID("textbox");
    confirmationMessage.setAlign(Alignment.CENTER);
    confirmationMessage.setShowEdges(true);
    confirmationMessage.setPadding(5);
    confirmationMessage.setLeft(50);
    confirmationMessage.setTop(50);
    //confirmationMessage.setParentElement(layout);
    //confirmationMessage.setWidth(200);
    confirmationMessage.setSmoothFade(Boolean.TRUE);
    //confirmationMessage.setContents("The future has a way of arriving unannounced.");
    confirmationMessage.setVisibility(Visibility.HIDDEN);



    organizationInventoryYearSaveButton.setTitle("SAVE");
    organizationInventoryYearSaveButton.setTooltip("Save");
    organizationInventoryYearSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if (!organizationInventoryYearForm.getErrors().isEmpty()){
            SC.say("Please clear errors before submitting this information!");
        }
        else {
            //Record organizationRecord = organizationInventoryYearForm.getValuesAsRecord();           
            organizationInventoryYearForm.saveData(
                          new DSCallback() {
                                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                                        UserOrganizationHeader.organizationSelectForm.fetchData();
                                        SC.say("Update Complete!");
                                        //confirmationMessage.animateShow(AnimationEffect.FADE, null, (int) 1000);
                                        //SC.echo("Hello");
                                        //alert("Hello");
                                        //confirmationMessage.show();
                                        //organizationInventoryYearVLayout.showMember(cMPortletWindow);
                            }
                          });

            //--Update Inventory Year in the Header
            //UserOrganizationHeader.organizationSelectForm.fetchData();
            //UserOrganizationHeader.organizationSelectForm.fetchData();
            //organizationInventoryYearForm.clearValues();
            //organizationInventoryYearForm.markForRedraw();
            //organizationInventoryYearVLayout.hide();
        }
      }
    });

    organizationInventoryYearCancelButton.setTitle("CANCEL");
    organizationInventoryYearCancelButton.setTooltip("Cancel");
    organizationInventoryYearCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        //organizationInventoryYearForm.saveData();
        //organizationInventoryYearVLayout.hide();
        Criteria fetchCriteria = new Criteria();

        String orgName = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
        fetchCriteria.addCriteria("organizationName", orgName);
        organizationInventoryYearForm.fetchData(fetchCriteria);
      }
    });

    HLayout buttons = new HLayout();
    //buttons.setWidth("50%");
    //buttons.setAlign(Alignment.CENTER);
    buttons.addMember(organizationInventoryYearCancelButton);
    buttons.addMember(organizationInventoryYearSaveButton);

    Label currentInventoryYearLabel = new Label("Current Inventory Year");
    currentInventoryYearLabel.setHeight(15);
    currentInventoryYearLabel.setWidth100();
    currentInventoryYearLabel.setAlign(Alignment.LEFT);
    currentInventoryYearLabel.setStyleName("labels");

    organizationInventoryYearVLayout.addMember(currentInventoryYearLabel);
    organizationInventoryYearVLayout.addMember(organizationInventoryYearForm);
    organizationInventoryYearVLayout.addMember(buttons);
    organizationInventoryYearVLayout.addMember(confirmationMessage);
    //organizationInventoryYearVLayout.hideMember(cMPortletWindow);
}

private void initEmissionsSummaryInputForm() {

    emissionsSummaryInputVLayout.setWidth100();
    emissionsSummaryInputVLayout.setHeight100();

    emissionsSummaryInputForm.setCellPadding(5);
    //emissionsSummaryInputForm.setDefaultWidth(800);
    emissionsSummaryInputForm.setWidth("50%");
    emissionsSummaryInputForm.setNumCols(2);
    //emissionsSummaryInputForm.setColWidths("25%");

    //emissionsSummaryInputForm.setBorder("1px double orange");
    //emissionsSummaryInputForm.setTitleOrientation(TitleOrientation.TOP);
    emissionsSummaryInputForm.setDataSource(organizationDS);
    //emissionsSummaryInputForm.setDisableValidation(Boolean.TRUE);
    //emissionsSummaryInputForm.setAlign(Alignment.CENTER);

    //emissionsSummaryInputForm.animateShow(AnimationEffect.SLIDE);
    //emissionsSummaryInputForm.hide();
    //emissionsSummaryInputForm.setTitle("Please your organization information:");

//-- setValidators for the forms for common types.
    //initializeValidators();

//-- Form fields  ------------------------------------------------------------------
    
     //IntegerItem emissionsSummaryReportId = new IntegerItem("id");
     DateItem emissionsBeginDate = new DateItem("emissionsBeginDate");
     emissionsBeginDate.setTitle("Emissions Begin Date");
     
     DateItem emissionsEndDate = new DateItem("emissionsEndDate");
     emissionsEndDate.setTitle("Emissions End Date");

     final SelectItem programTypeItem = new SelectItem();
     programTypeItem.setName("programType");
     programTypeItem.setTitle("Program Type");
     programTypeItem.setValueMap("US EPA", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");
     programTypeItem.setDefaultToFirstOption(Boolean.TRUE);
     programTypeItem.setDisabled(Boolean.TRUE);
    //emissionsSummaryInputForm.setDataSource(emissionsSummaryDS);
    emissionsSummaryInputForm.setItems(emissionsBeginDate, emissionsEndDate, programTypeItem);
    //emissionsSummaryInputForm.hideItem("id");

    //final IButton emissionsSummaryCancelButton = new IButton();
    final IButton emissionsSummaryCalculateButton = new IButton();

    emissionsSummaryCalculateButton.setWidth(180);

    emissionsSummaryCalculateButton.setTitle("Calculate Emissions");
    emissionsSummaryCalculateButton.setTooltip("Calculate Emissions");
    emissionsSummaryCalculateButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if (!emissionsSummaryInputForm.getErrors().isEmpty()){
            SC.say("Please clear errors before submitting this information!");
        }
        else {
                //SC.say("I am in Emissions!");
        	Record emissionsSummaryInputRecord = emissionsSummaryInputForm.getValuesAsRecord();
                String orgName = (String) UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
                emissionsSummaryInputRecord.setAttribute("organizationName",orgName);
                final ListGrid lastEmissionsSummaryRecordGrid = new ListGrid();
                lastEmissionsSummaryRecordGrid.setDataSource(emissionsSummaryDS);
                
                lastEmissionsSummaryRecordGrid.addData(emissionsSummaryInputRecord, new DSCallback() {
                            public void execute(DSResponse response, Object rawData, DSRequest request) {
                                //SC.say("Response object STATUS_SUCCESS is:" + response.STATUS_SUCCESS);
                                //if (response.STATUS_SUCCESS<0) SC.say("Response object STATUS_SUCCESS is:" + response.STATUS_SUCCESS);
                                //String emissionsSummaryReportId = (String)emissionsSummaryDS.getField("id").getAttributeAsString("id");
                                
                                //SC.say("emissionsSummaryReportId=======:" + emissionsSummaryReportId);
                                final Record[] records = response.getData();
                                String emissionsSummaryReportId = records[0].getAttributeAsString("id");
                                displayEmissionsReport("Just Calculated", emissionsSummaryReportId);
                                //SC.say("emissionsSummaryReportId=======:" + emissionsSummaryReportId);
                            }
                        });
        }
      }
    });

    /*
    emissionsSummaryCancelButton.setTitle("CANCEL");
    emissionsSummaryCancelButton.setTooltip("Cancel");
    emissionsSummaryCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
      		SC.say("I need to cancel this!");
      }
    });
    */

    HLayout buttons = new HLayout();
    //buttons.setAlign(Alignment.CENTER);
    //buttons.addMember(emissionsSummaryCancelButton);
    buttons.addMember(emissionsSummaryCalculateButton);

    Label calculateEmissionsLabel = new Label("Calculate Emissions for the specific period");
    calculateEmissionsLabel.setHeight(15);
    calculateEmissionsLabel.setWidth100();
    calculateEmissionsLabel.setAlign(Alignment.LEFT);
    calculateEmissionsLabel.setStyleName("labels");

    emissionsSummaryInputVLayout.addMember(calculateEmissionsLabel);
    emissionsSummaryInputVLayout.addMember(emissionsSummaryInputForm);
    emissionsSummaryInputVLayout.addMember(buttons);
}

public void displayDetailInfo(ListGrid listGrid, DetailViewerField[] fieldList) {
    
    Canvas[] children = detailViewerVLayout.getChildren();
    if (children.length>0){
        for (int i=0; i < children.length; i++){
            detailViewerVLayout.removeChild(children[i]);
        }
    }
   Canvas[] children2 = middleBottomHLayout.getChildren();
    if (children2.length>0){
        for (int i=0; i < children2.length; i++){
            middleBottomHLayout.removeChild(children2[i]);
        }
    }

    emissionsSummaryDetailViewer.setFields(fieldList);
    emissionsSummaryDetailViewer.viewSelectedData(listGrid);
    //emissionsSummaryDetailViewer.set
    //detailViewerVLayout.addMember(emissionsSummaryDetailViewerLabel);
    detailViewerVLayout.addMember(emissionsSummaryDetailViewer);
    middleBottomHLayout.addMember(detailViewerVLayout);
    //middleBottomHLayout.addMember(panel);
    middleBottomHLayout.show();
    
    /*
    Canvas[] children = detailViewerWindow.getChildren();
    if (children.length>0){
        for (int i=0; i < children.length; i++){
            detailViewerWindow.removeChild(children[i]);
        }
    }

   Canvas[] children2 = middleBottomHLayout.getChildren();
    if (children2.length>0){
        for (int i=0; i < children2.length; i++){
            middleBottomHLayout.removeChild(children2[i]);
        }
    }
    
    emissionsSummaryDetailViewer.setFields(fieldList);
    emissionsSummaryDetailViewer.viewSelectedData(listGrid);
    //emissionsSummaryDetailViewer.set
    //detailViewerVLayout.addMember(emissionsSummaryDetailViewerLabel);
    detailViewerWindow.addMember(emissionsSummaryDetailViewer);
    //middleBottomHLayout.addMember(detailViewerVLayout);
    middleBottomHLayout.addMember(detailViewerWindow);
    middleBottomHLayout.show();
    */
}

public void displayEmissionsReport(String emissionsReportChoice, String emissionsSummaryReportId) {

    /*
    //--remove existing child from middleBottomHLayout
    Canvas[] children2 = middleBottomHLayout.getChildren();
    if (children2.length>0){
	for (int i=0; i < children2.length; i++){
	    middleBottomHLayout.removeChild(children2[i]);
	}
    }
    */
    
    orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
    String reportUrl = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
    com.google.gwt.user.client.Window.open(reportUrl,null,null);

    /*
    if (emissionsReportChoice.equals("Just Calculated")){
        HTMLPane htmlPane = new HTMLPane();
        htmlPane.setHeight100();
        htmlPane.setWidth100();
        //htmlPane.setContentsURL("/ibLGHGCalc/reports/ghgReport.pdf");
        htmlPane.setContentsURL(reportUrl);
        htmlPane.setContentsType(ContentsType.PAGE);
        middleBottomHLayout.addChild(htmlPane);
        //middleBottomHLayout.show();
    }
    */

    }

public void displayHelp (String helpChoice) {

            if (helpChoice.equals("User Guide")){
                /*
                Img eGridImage = new Img("eGrid-2005.jpg");
                eGridImage.setHeight100();
                eGridImage.setWidth100();
                Canvas eGridImageCanvas = new Canvas();
                eGridImageCanvas.addChild(eGridImage);
                middleBottomHLayout.addChild(eGridImageCanvas);
                middleBottomHLayout.show();
                */

                //com.google.gwt.user.client.Window.open("/ibLGHGCalc/images/eGird-2005.jpg",null,null);

            } else if (helpChoice.equals("Know your Electricity Grid Sub-Region")){
                /*
                Img eGridImage = new Img("eGrid-2005.jpg");
                Portlet imagePortlet = new Portlet();
                imagePortlet.addMember(eGridImage);
                imagePortlet.show();
                 *
                */

                //String hostImageURL=com.google.gwt.user.client.Window.Location.getHost()+"/eGrid-2005.jpg";
                String hostImageURL="http://www.google.com";
                com.google.gwt.user.client.Window.open(hostImageURL, null, null);
                /*
                Canvas eGridImageCanvas = new Canvas();
                eGridImageCanvas.setHeight100();
                eGridImageCanvas.setWidth100();
                eGridImageCanvas.addChild(eGridImage);
                 *
                 */
                
                /*
                middleBottomHLayout.addChild(eGridImageCanvas);
                middleBottomHLayout.show();
                 *
                 */
            }
}
public void displayEmissionSourceInfo(String emissionSourceChoice) {
            Criteria fetchCriteria = new Criteria();

            String orgName = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
            fetchCriteria.addCriteria("organizationName", orgName);

            orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();

            //SC.say("Org Name is " + orgName);
            Date currentInventoryBeginDate = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
            fetchCriteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDate);

            Date currentInventoryEndDate = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
            fetchCriteria.addCriteria("inventoryYearEndDate", currentInventoryEndDate);

            String programType = (String) UserOrganizationHeader.organizationSelectForm.getField("programType").getValue();
            fetchCriteria.addCriteria("programType", programType);

            //--remove existing child from middleMiddleHLayout
            Canvas[] children = middleMiddleHLayout.getChildren();

            if (children.length>0){
                for (int i=0; i < children.length; i++){
                    middleMiddleHLayout.removeChild(children[i]);
                }
            }

            //--remove existing child from middleMiddleHLayout
            Canvas[] children2 = middleBottomHLayout.getChildren();
            if (children2.length>0){
                for (int i=0; i < children2.length; i++){
                    middleBottomHLayout.removeChild(children2[i]);
                }
            }

            //--hide middleBottomHLayout
            middleBottomHLayout.hide();

            if (emissionSourceChoice.equals("Load Data")){
               //emissionsSummaryInputForm.fetchData(fetchCriteria);
               fileTypeItem.setValueMap(epaDataLoadOptions);
               middleMiddleHLayout.addChild(fileUploadLayout);
            } else if (emissionSourceChoice.equals("Load Emission Factors")){
               //emissionsSummaryInputForm.fetchData(fetchCriteria);
               fileTypeItem.setValueMap(emissionFactorsLoadOptions);
               middleMiddleHLayout.addChild(fileUploadLayout);
            } else if (emissionSourceChoice.equals("Emissions Summary")){
                HTMLPane htmlPane = new HTMLPane();
                htmlPane.setHeight100();
                htmlPane.setWidth100();
                htmlPane.setContentsURL("/ibLGHGCalc/reports/ghgReport.pdf");
                //htmlPane.setContentsURL("/ibLGHGCalc/reports/firstReport.jrxml");
                //htmlPane.setContentsURL("/ibLGHGCalc/reports/Document1.txt");
                //htmlPane.setContentsURLParams(params);
                htmlPane.setContentsType(ContentsType.PAGE);
                //htmlPane.markForDestroy();
               //htmlPane.draw();
                middleBottomHLayout.addChild(htmlPane);
                middleBottomHLayout.show();
              //emissionsSummaryInputForm.fetchData(fetchCriteria);
               //middleMiddleHLayout.addChild(emissionsSummaryInputVLayout);
            } else if (emissionSourceChoice.equals("Emissions Report")){
               //emissionsSummaryInputForm.fetchData(fetchCriteria);
               //mainOrganizationId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
               //emissionsSummaryDataGrid.getField("reportFileName").setLinkURLPrefix("/ibLGHGCalc/reports/"+mainOrganizationId+"/");
               //emissionsSummaryDataGrid.redraw();

               emissionsSummaryDataGrid.filterData(fetchCriteria);                
               middleMiddleHLayout.addChild(emissionsSummaryVLayout);
               //middleMiddleHLayout.addChild(emissionsSummaryDataGrid, orgName, true);
               //emissionsSummaryDataGrid.markForRedraw();
            } else if (emissionSourceChoice.equals("Calculate Emissions")){
               //emissionsSummaryInputForm.fetchData(fetchCriteria);
               middleMiddleHLayout.addChild(emissionsSummaryInputVLayout);
            } else if(emissionSourceChoice.equals("Change Inventory Year")) {
              //--Display Edit Organizatin form
               //stationaryCombustionDataGrid.filterData(fetchCriteria);
               //organizationProfileForm.filterData(fetchCriteria);
               organizationInventoryYearForm.fetchData(fetchCriteria);
               middleMiddleHLayout.addChild(organizationInventoryYearVLayout);
               //--There should be a better way to manage this
               //organizationProfileForm.show();
            } else if (emissionSourceChoice.equals("Organization Profile")){
              //--Display Edit Organizatin form
               //stationaryCombustionDataGrid.filterData(fetchCriteria);
               //organizationProfileForm.filterData(fetchCriteria);
               organizationProfileForm.fetchData(fetchCriteria);
               middleMiddleHLayout.addChild(organizationProfileVLayout);
               //--There should be a better way to manage this
               //organizationProfileForm.show();
            } else if (emissionSourceChoice.equals("Dashboard")){
               middleMiddleHLayout.addChild(new Dashboard());
               //organizationProfileForm.fetchData(fetchCriteria);
               //middleMiddleHLayout.addChild(organizationProfileVLayout);
               //--There should be a better way to manage this
               //organizationProfileForm.show();
            } else if (emissionSourceChoice.equals("Stationary Combustions Sources")){
               //--Display stationary Combustion Data
               //stationaryCombustionDataGrid.setCriteria(fetchCriteria);
               stationaryCombustionDataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(stationaryCombustionLayout);
            } else if (emissionSourceChoice.equals("Mobile Combustions Sources")){
               //--Display Mobile Combustion Data
               mobileCombustionDataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(mobileCombustionLayout);
            } else if (emissionSourceChoice.equals("Refridgeration and Air Conditioning Sources")){
               //--Display Refridgeration an Ar Conditioning Data
               Criteria refridgerationACMaterialBalanceFetchCriteria = new Criteria();
               refridgerationACMaterialBalanceFetchCriteria.addCriteria("methodType", "Refridgeration Air Conditioning - Company-Wide Material Balance Method");
               refridgerationACMaterialBalanceFetchCriteria.addCriteria(fetchCriteria);
               refridgerationAirConditioningDataGrid_1.filterData(refridgerationACMaterialBalanceFetchCriteria);

               Criteria refridgerationACSimplifiedMaterialBalanceFetchCriteria = new Criteria();
               refridgerationACSimplifiedMaterialBalanceFetchCriteria.addCriteria("methodType", "Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method");
               refridgerationACSimplifiedMaterialBalanceFetchCriteria.addCriteria(fetchCriteria);
               refridgerationAirConditioningDataGrid_2.filterData(refridgerationACSimplifiedMaterialBalanceFetchCriteria);

               Criteria refridgerationACScreeningFetchCriteria = new Criteria();
               refridgerationACScreeningFetchCriteria.addCriteria("methodType", "Refridgeration Air Conditioning - Source Level Screening Method");
               refridgerationACScreeningFetchCriteria.addCriteria(fetchCriteria);
               refridgerationAirConditioningDataGrid_3.filterData(refridgerationACScreeningFetchCriteria);

               middleMiddleHLayout.addChild(refridgerationAirConditioningLayout);
               //middleMiddleHLayout.addChild(refridgerationAirConditioningTabSet);
            } else if (emissionSourceChoice.equals("Fire Suppression Sources")){
               //--Display Refridgeration an Ar Conditioning Data
               Criteria fireSuppressionMaterialBalanceFetchCriteria = new Criteria();
               fireSuppressionMaterialBalanceFetchCriteria.addCriteria("methodType", "Fire Suppression - Company-Wide Material Balance Method");
               fireSuppressionMaterialBalanceFetchCriteria.addCriteria(fetchCriteria);
               fireSuppressionDataGrid_1.filterData(fireSuppressionMaterialBalanceFetchCriteria);
               //fireSuppressionDataGrid_1.setCriteria(fireSuppressionMaterialBalanceFetchCriteria);
               //fireSuppressionDataGrid_1.fetchData();
               

               Criteria fireSuppressionSimplifiedMaterialBalanceFetchCriteria = new Criteria();
               fireSuppressionSimplifiedMaterialBalanceFetchCriteria.addCriteria("methodType", "Fire Suppression - Company-Wide Simplified Material Balance Method");
               fireSuppressionSimplifiedMaterialBalanceFetchCriteria.addCriteria(fetchCriteria);
               fireSuppressionDataGrid_2.filterData(fireSuppressionSimplifiedMaterialBalanceFetchCriteria);
               //fireSuppressionDataGrid_2.setCriteria(fireSuppressionSimplifiedMaterialBalanceFetchCriteria);
               //fireSuppressionDataGrid_2.fetchData();

               Criteria fireSuppressionACScreeningFetchCriteria = new Criteria();
               fireSuppressionACScreeningFetchCriteria.addCriteria("methodType", "Fire Suppression - Source Level Screening Method");
               fireSuppressionACScreeningFetchCriteria.addCriteria(fetchCriteria);
               fireSuppressionDataGrid_3.filterData(fireSuppressionACScreeningFetchCriteria);
               //fireSuppressionDataGrid_3.setCriteria(fireSuppressionACScreeningFetchCriteria);
               //fireSuppressionDataGrid_3.fetchData();

               middleMiddleHLayout.addChild(fireSuppressionLayout);
               //middleMiddleHLayout.addChild(refridgerationAirConditioningTabSet);
            } else if (emissionSourceChoice.equals("Waste Stream Combustions Sources")){
               //--Display Waste Stream Combustions Sources Data
               wasteStreamCombustionDataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(wasteStreamCombustionLayout);
            } else  if (emissionSourceChoice.equals("Purchased Electricity")){
               //--Display purchased Electricity Data
               purchasedElectricityDataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(purchasedElectricityLayout);
            } else if (emissionSourceChoice.equals("Purchased Steam")){
               //--Display purchasedSteam Data
               purchasedSteamDataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(purchasedSteamLayout);
            } else if (emissionSourceChoice.equals("Employee Business Travel")){
               //--Display Mobile Combustion Data
                //Different criteria for optionalSourceInfo
                Criteria employeeBusinessTravelByVehicleFetchCriteria = new Criteria();
                employeeBusinessTravelByVehicleFetchCriteria.addCriteria(fetchCriteria);
                employeeBusinessTravelByVehicleFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Vehicle");

                Criteria employeeBusinessTravelByRailFetchCriteria = new Criteria();
                employeeBusinessTravelByRailFetchCriteria.addCriteria(fetchCriteria);
                employeeBusinessTravelByRailFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Rail");

                Criteria employeeBusinessTravelByBusFetchCriteria = new Criteria();
                employeeBusinessTravelByBusFetchCriteria.addCriteria(fetchCriteria);
                employeeBusinessTravelByBusFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Bus");

                Criteria employeeBusinessTravelByAirFetchCriteria = new Criteria();
                employeeBusinessTravelByAirFetchCriteria.addCriteria(fetchCriteria);
                employeeBusinessTravelByAirFetchCriteria.addCriteria("optionalSourceType", "Employee Business Travel - By Air");

                employeeBusinessTravelByVehicleDataGrid.filterData(employeeBusinessTravelByVehicleFetchCriteria);
                employeeBusinessTravelByRailDataGrid.filterData(employeeBusinessTravelByRailFetchCriteria);
                employeeBusinessTravelByBusDataGrid.filterData(employeeBusinessTravelByBusFetchCriteria);
                employeeBusinessTravelByAirDataGrid.filterData(employeeBusinessTravelByAirFetchCriteria);

                middleMiddleHLayout.addChild(employeeBusinessTravelLayout);
            } else if (emissionSourceChoice.equals("Employee Commuting")){
               //--Display Employee Commuting
                Criteria employeeCommutingByVehicleFetchCriteria = new Criteria();
                employeeCommutingByVehicleFetchCriteria.addCriteria(fetchCriteria);
                employeeCommutingByVehicleFetchCriteria.addCriteria("optionalSourceType", "Employee Commuting - By Vehicle");

                Criteria employeeCommutingByRailFetchCriteria = new Criteria();
                employeeCommutingByRailFetchCriteria.addCriteria(fetchCriteria);
                employeeCommutingByRailFetchCriteria.addCriteria("optionalSourceType", "Employee Commuting - By Rail");

                Criteria employeeCommutingByBusFetchCriteria = new Criteria();
                employeeCommutingByBusFetchCriteria.addCriteria(fetchCriteria);

                employeeCommutingByBusFetchCriteria.addCriteria("optionalSourceType", "Employee Commuting - By Bus");
                employeeCommutingByVehicleDataGrid.filterData(employeeCommutingByVehicleFetchCriteria);
                employeeCommutingByRailDataGrid.filterData(employeeCommutingByRailFetchCriteria);
                employeeCommutingByBusDataGrid.filterData(employeeCommutingByBusFetchCriteria);

                middleMiddleHLayout.addChild(employeeCommutingLayout);
            } else if (emissionSourceChoice.equals("Product Transport")){
               //--Display Product Transport Data
                Criteria productTransportByVehicleFetchCriteria = new Criteria();
                productTransportByVehicleFetchCriteria.addCriteria(fetchCriteria);
                productTransportByVehicleFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Vehicle");

                Criteria productTransportByHeavyDutyTrucksFetchCriteria = new Criteria();
                productTransportByHeavyDutyTrucksFetchCriteria.addCriteria(fetchCriteria);
                productTransportByHeavyDutyTrucksFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Heavy Duty Trucks");

                Criteria productTransportByRailFetchCriteria = new Criteria();
                productTransportByRailFetchCriteria.addCriteria(fetchCriteria);
                productTransportByRailFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Rail");

                Criteria productTransportByWaterAirFetchCriteria = new Criteria();
                productTransportByWaterAirFetchCriteria.addCriteria(fetchCriteria);
                productTransportByWaterAirFetchCriteria.addCriteria("optionalSourceType", "Product Transport - By Water or Air");

                productTransportByVehicleDataGrid.filterData(productTransportByVehicleFetchCriteria);
                productTransportByHeavyDutyTrucksDataGrid.filterData(productTransportByHeavyDutyTrucksFetchCriteria);
                productTransportByRailDataGrid.filterData(productTransportByRailFetchCriteria);
                productTransportByWaterAirDataGrid.filterData(productTransportByWaterAirFetchCriteria);

                middleMiddleHLayout.addChild(productTransportLayout);
            }
  }

public void displayData(String emissionSourceChoice, List<ListGridField> dataListGridFields,List<FormItem> dataFormItems, RestDataSource dataSource){
            Criteria fetchCriteria = new Criteria();

            String orgName = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
            fetchCriteria.addCriteria("organizationName", orgName);

            //SC.say("Org Name is " + orgName);
            Date currentInventoryBeginDate = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
            fetchCriteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDate);

            Date currentInventoryEndDate = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
            fetchCriteria.addCriteria("inventoryYearEndDate", currentInventoryEndDate);

            String programType = (String) UserOrganizationHeader.organizationSelectForm.getField("programType").getValue();
            fetchCriteria.addCriteria("programType", programType);

            //--remove existing child from middleMiddleHLayout
            Canvas[] children = middleMiddleHLayout.getChildren();

            if (children.length>0){
                for (int i=0; i < children.length; i++){
                    middleMiddleHLayout.removeChild(children[i]);
                }
            }
/*
            Canvas[] removeFormItems = dataForm.getChildren();
            if (removeFormItems.length>0){
                for (int i=0; i < children.length; i++){
                    dataForm.removeChild(children[i]);
                }
            }
*/
            //--set the datasource and fields for the dataGrid
            //dataGrid.redraw();
            dataDS = dataSource;
            final ListGridField[] listGridFields = new ListGridField[dataListGridFields.size()];
            dataListGridFields.toArray(listGridFields);
            dataGrid.setDataSource(dataDS);
            dataGrid.setFields(listGridFields);
            
            //--set the dataForm            

            //dataForm.setDataSource(dataDS);
            final FormItem[] formItems = new FormItem[dataFormItems.size()];
            dataFormItems.toArray(formItems);
            dataForm.setDataSource(dataDS);
            dataForm.setItems(formItems);

            //dataForm.redraw();
                        
            //--Display the middleMiddleHLayout
            //if (emissionSourceChoice.equals("Mobile Combustions Sources")){
               //--Display Mobile Combustion Data
               dataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(dataLayout);
            //}
}

public static Label getSectionLink(String message, ClickHandler handler) {
   Label link = new Label();
   link = new Label(message);
   link.setStyleName("sectionLink");
   link.setHeight(20);
   link.setAlign(Alignment.LEFT);

   //Set the width to the length of the text.
   //link.setWidth(message.length()*6);
   link.setWidth100();

   //link.setBorder("1px Solid orange");
   //link.setEdgeSize(15);
   //link.setShowRollOver(Boolean.TRUE) ;
   
   link.setShowDown(Boolean.TRUE);   
   link.setShowFocused(Boolean.TRUE);
   link.setShadowDepth(5);
   //link.setShadowSoftness(5);

   //link.setBackgroundColor("#EFBFB");
   link.addClickHandler(handler);
   return link;

   }

public static Label getReportLink(String message, ClickHandler handler) {
   Label link = new Label();
   link = new Label(message);
   link.setStyleName("sectionLink");
   //link.setHeight(20);
   link.setAlign(Alignment.LEFT);

   //Set the width to the length of the text.
   //link.setWidth(message.length()*6);
   //link.setWidth100();

   //link.setBorder("1px Solid orange");
   //link.setEdgeSize(15);
   //link.setShowRollOver(Boolean.TRUE) ;

   link.setShowDown(Boolean.TRUE);
   link.setShowFocused(Boolean.TRUE);
   link.setShadowDepth(5);
   //link.setShadowSoftness(5);

   //link.setBackgroundColor("#EFBFB");
   link.addClickHandler(handler);
   return link;

   }


private void initMobileCombustionEditForm_2() {

    mobileCombustionForm.setCellPadding(5);
    mobileCombustionForm.setWidth("100%");

//-- setValidators for the forms for common types.
    initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    TextItem fuelSourceDescriptionItem = new TextItem("fuelSourceDescription");
    fuelSourceDescriptionItem.setTitle("Fuel Source Description");
    fuelSourceDescriptionItem.setSelectOnFocus(true);
    fuelSourceDescriptionItem.setWrapTitle(false);
    fuelSourceDescriptionItem.setDefaultValue("Source");
    fuelSourceDescriptionItem.setRequired(Boolean.TRUE);
    //TextItem vehicleTypeItem = new TextItem("vehicleType");
    //TextItem vehicleYearItem = new TextItem("vehicleYear");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    //vehicleTypeItem.setPickListWidth(310);
    vehicleTypeItem.setTitle("Vehicle Type");
    vehicleTypeItem.setOptionDataSource(vehicleType_EPADS);
    vehicleTypeItem.setRequired(Boolean.TRUE);
    //vehicleTypeItem.fetchData();
    //vehicleTypeItem.setDefaultToFirstOption(Boolean.TRUE);

    vehicleTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            /*
            mobileCombustionForm.clearValue("vehicleYear");
            mobileCombustionForm.clearValue("fuelType");
            Record selectedFuelTypeRecord = vehicleTypeItem.getSelectedRecord();
            mobileCombustionForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
             *
             */
            dataForm.clearValue("vehicleYear");
            dataForm.clearValue("fuelType");
            Record selectedFuelTypeRecord = vehicleTypeItem.getSelectedRecord();
            dataForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
        }
    });

    final SelectItem vehicleYearItem = new SelectItem(){
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };

    vehicleYearItem.setName("vehicleYear");
    //vehicleYearItem.setPickListWidth(310);
    vehicleYearItem.setTitle("Vehicle Year");
    vehicleYearItem.setOptionDataSource(eF_CH4N2O_MobileCombustion_EPADS);

    //final SelectItem fuelTypeItem = new SelectItem();

    final SelectItem fuelTypeItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };

    fuelTypeItem.setName("fuelType");
    //fuelTypeItem.setPickListWidth(310);
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(vehicleType_EPADS);

    final StaticTextItem fuelUnitItem = new StaticTextItem();
    fuelUnitItem.setName("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");
    //fuelUnitItem.setPickListWidth(250);
    //fuelUnitItem.setOptionDataSource(vehicleType_EPADS);

    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");
    fuelQuantityItem.setValidators(floatValidator);
    fuelQuantityItem.setValidateOnExit(Boolean.TRUE);
    fuelQuantityItem.setValidateOnChange(Boolean.TRUE);
    fuelQuantityItem.setRequired(Boolean.TRUE);

    FloatItem milesTravelledItem = new FloatItem();
    milesTravelledItem.setName("milesTravelled");
    milesTravelledItem.setValidators(floatValidator);
    milesTravelledItem.setValidateOnExit(Boolean.TRUE);
    milesTravelledItem.setValidateOnChange(Boolean.TRUE);
    milesTravelledItem.setRequired(Boolean.TRUE);

    RequiredIfValidator ifBiofuelPctRequiredValidator = new RequiredIfValidator();
    ifBiofuelPctRequiredValidator.setExpression(new RequiredIfFunction() {
        public boolean execute(FormItem formItem, Object value) {
            //String valueStr = (String) mobileCombustionForm.getItem("fuelType").getValue();
            String valueStr = (String) dataForm.getItem("fuelType").getValue();
            if (valueStr !=  null){
                if (valueStr.contains("Biodiesel")){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    });
    ifBiofuelPctRequiredValidator.setErrorMessage("Please provide biofuel percent Value");

    FloatItem bioFuelPercentItem = new FloatItem();
    bioFuelPercentItem.setName("bioFuelPercent");
    bioFuelPercentItem.setValidators(floatValidator);
    bioFuelPercentItem.setValidateOnExit(Boolean.TRUE);
    bioFuelPercentItem.setValidateOnChange(Boolean.TRUE);
    bioFuelPercentItem.setValidators(ifBiofuelPctRequiredValidator);

    RequiredIfValidator ifEthanolPctRequiredValidator = new RequiredIfValidator();
    ifEthanolPctRequiredValidator.setExpression(new RequiredIfFunction() {
        public boolean execute(FormItem formItem, Object value) {
            //String valueStr = (String) mobileCombustionForm.getItem("fuelType").getValue();
            String valueStr = (String) dataForm.getItem("fuelType").getValue();
            if (valueStr !=  null){
                if (valueStr.contains("Ethanol")){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    });
    ifEthanolPctRequiredValidator.setErrorMessage("Please provide ethanol percent Value");

    FloatItem ethanolPercentItem = new FloatItem();
    ethanolPercentItem.setName("ethanolPercent");
    ethanolPercentItem.setValidators(floatValidator);
    ethanolPercentItem.setValidateOnExit(Boolean.TRUE);
    ethanolPercentItem.setValidateOnChange(Boolean.TRUE);
    ethanolPercentItem.setValidators(ifEthanolPctRequiredValidator);

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    //fuelUsedBeginDateItem.setValue(currentInventoryBeginDateMin);//setStartDate(currentInventoryBeginDateMin);
    //fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedBeginDateItem.setRequired(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    //fuelUsedEndDateItem.setValue(currentInventoryEndDateMax);//StartDate(currentInventoryBeginDateMin);
    //fuelUsedEndDateItem.setDefaultValue();//setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedEndDateItem.setRequired(Boolean.TRUE);

    //IntegerItem organizationId = new IntegerItem();
    //organizationId.setName("organizationId");

    //mobileCombustionForm.setItems(fuelSourceDescriptionItem ,vehicleTypeItem, vehicleYearItem, fuelTypeItem, fuelQuantityItem, fuelUnitItem, milesTravelledItem, bioFuelPercentItem,ethanolPercentItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    //final List<FormItem> mobileCombustionFormItems = new ArrayList<FormItem>();
    mobileCombustionFormItems.add(fuelSourceDescriptionItem);
    mobileCombustionFormItems.add(vehicleTypeItem);
    mobileCombustionFormItems.add(vehicleYearItem);
    mobileCombustionFormItems.add(fuelTypeItem);
    mobileCombustionFormItems.add(fuelQuantityItem);
    mobileCombustionFormItems.add(fuelUnitItem);
    mobileCombustionFormItems.add(milesTravelledItem);
    mobileCombustionFormItems.add(bioFuelPercentItem);
    mobileCombustionFormItems.add(ethanolPercentItem);
    mobileCombustionFormItems.add(fuelUsedBeginDateItem);
    mobileCombustionFormItems.add(fuelUsedEndDateItem);
/*
    //-Comment for dataGrid - below
    final IButton mobileCombustionCancelButton = new IButton();
    final IButton mobileCombustionSaveButton = new IButton();

    mobileCombustionSaveButton.setTitle("SAVE");
    mobileCombustionSaveButton.setTooltip("Save this Mobile Combustion Source");
    mobileCombustionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if (mobileCombustionForm.validate()) {
            Record mobileCombustionFormRecord = mobileCombustionForm.getValuesAsRecord();
            Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
            mobileCombustionFormRecord.setAttribute("organizationId", organizationIdValue);
            mobileCombustionDataGrid.updateData(mobileCombustionFormRecord);
            mobileCombustionForm.clearValues();
            mobileCombustionForm.markForRedraw();
            mobileCombustionFormWindow.hide();
        } else {
            SC.say("Please provide proper information");
        }
      }
    });

    mobileCombustionCancelButton.setTitle("CANCEL");
    mobileCombustionCancelButton.setTooltip("Cancel");
    mobileCombustionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        mobileCombustionFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(mobileCombustionCancelButton);
    buttons.addMember(mobileCombustionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(mobileCombustionForm);
    dialog.addMember(buttons);
    mobileCombustionFormWindow.setShowShadow(true);
    //mobileCombustionFormWindow.setShowTitle(false);
    mobileCombustionFormWindow.setIsModal(true);
    mobileCombustionFormWindow.setPadding(20);
    mobileCombustionFormWindow.setWidth(500);
    mobileCombustionFormWindow.setHeight(425);
    mobileCombustionFormWindow.setShowMinimizeButton(false);
    mobileCombustionFormWindow.setShowCloseButton(true);
    mobileCombustionFormWindow.setShowModalMask(true);
    mobileCombustionFormWindow.centerInPage();
    mobileCombustionFormWindow.setTitle("Please enter mobile combustion source information:");
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //mobileCombustionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    mobileCombustionFormWindow.addItem(dialog);
 * 
 */
 }
private void initMobileCombustionEditForm_OLD() {

    mobileCombustionForm.setCellPadding(5);
    mobileCombustionForm.setWidth("100%");

//-- setValidators for the forms for common types.
    initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    TextItem fuelSourceDescriptionItem = new TextItem("fuelSourceDescription");
    fuelSourceDescriptionItem.setTitle("Fuel Source Description");
    fuelSourceDescriptionItem.setSelectOnFocus(true);
    fuelSourceDescriptionItem.setWrapTitle(false);
    fuelSourceDescriptionItem.setDefaultValue("Source");
    fuelSourceDescriptionItem.setRequired(Boolean.TRUE);
    //TextItem vehicleTypeItem = new TextItem("vehicleType");
    //TextItem vehicleYearItem = new TextItem("vehicleYear");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    //vehicleTypeItem.setPickListWidth(310);
    vehicleTypeItem.setTitle("Vehicle Type");
    vehicleTypeItem.setOptionDataSource(vehicleType_EPADS);
    vehicleTypeItem.setRequired(Boolean.TRUE);
    //vehicleTypeItem.fetchData();
    //vehicleTypeItem.setDefaultToFirstOption(Boolean.TRUE);
    
    vehicleTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {

            mobileCombustionForm.clearValue("vehicleYear");
            mobileCombustionForm.clearValue("fuelType");            
            Record selectedFuelTypeRecord = vehicleTypeItem.getSelectedRecord();
            mobileCombustionForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));

            /*
            dataForm.clearValue("vehicleYear");
            dataForm.clearValue("fuelType");
            Record selectedFuelTypeRecord = vehicleTypeItem.getSelectedRecord();
            dataForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
             *
             */
        }
    });
   
    final SelectItem vehicleYearItem = new SelectItem(){
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };    
 
    vehicleYearItem.setName("vehicleYear");
    //vehicleYearItem.setPickListWidth(310);
    vehicleYearItem.setTitle("Vehicle Year");
    vehicleYearItem.setOptionDataSource(eF_CH4N2O_MobileCombustion_EPADS);

    //final SelectItem fuelTypeItem = new SelectItem();

    final SelectItem fuelTypeItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };

    fuelTypeItem.setName("fuelType");
    //fuelTypeItem.setPickListWidth(310);
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(vehicleType_EPADS);
    
    final StaticTextItem fuelUnitItem = new StaticTextItem();
    fuelUnitItem.setName("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");
    //fuelUnitItem.setPickListWidth(250);
    //fuelUnitItem.setOptionDataSource(vehicleType_EPADS);

    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");
    fuelQuantityItem.setValidators(floatValidator);
    fuelQuantityItem.setValidateOnExit(Boolean.TRUE);
    fuelQuantityItem.setValidateOnChange(Boolean.TRUE);
    fuelQuantityItem.setRequired(Boolean.TRUE);

    FloatItem milesTravelledItem = new FloatItem();
    milesTravelledItem.setName("milesTravelled");
    milesTravelledItem.setValidators(floatValidator);
    milesTravelledItem.setValidateOnExit(Boolean.TRUE);
    milesTravelledItem.setValidateOnChange(Boolean.TRUE);
    milesTravelledItem.setRequired(Boolean.TRUE);

    RequiredIfValidator ifBiofuelPctRequiredValidator = new RequiredIfValidator();
    ifBiofuelPctRequiredValidator.setExpression(new RequiredIfFunction() {
        public boolean execute(FormItem formItem, Object value) {
            String valueStr = (String) mobileCombustionForm.getItem("fuelType").getValue();
            //String valueStr = (String) dataForm.getItem("fuelType").getValue();
            if (valueStr !=  null){
                if (valueStr.contains("Biodiesel")){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    });
    ifBiofuelPctRequiredValidator.setErrorMessage("Please provide biofuel percent Value");

    FloatItem bioFuelPercentItem = new FloatItem();
    bioFuelPercentItem.setName("bioFuelPercent");
    bioFuelPercentItem.setValidators(floatValidator);
    bioFuelPercentItem.setValidateOnExit(Boolean.TRUE);
    bioFuelPercentItem.setValidateOnChange(Boolean.TRUE);
    bioFuelPercentItem.setValidators(ifBiofuelPctRequiredValidator);

    RequiredIfValidator ifEthanolPctRequiredValidator = new RequiredIfValidator();
    ifEthanolPctRequiredValidator.setExpression(new RequiredIfFunction() {
        public boolean execute(FormItem formItem, Object value) {
            String valueStr = (String) mobileCombustionForm.getItem("fuelType").getValue();
            //String valueStr = (String) dataForm.getItem("fuelType").getValue();
            if (valueStr !=  null){
                if (valueStr.contains("Ethanol")){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    });
    ifEthanolPctRequiredValidator.setErrorMessage("Please provide ethanol percent Value");

    FloatItem ethanolPercentItem = new FloatItem();
    ethanolPercentItem.setName("ethanolPercent");
    ethanolPercentItem.setValidators(floatValidator);
    ethanolPercentItem.setValidateOnExit(Boolean.TRUE);
    ethanolPercentItem.setValidateOnChange(Boolean.TRUE);
    ethanolPercentItem.setValidators(ifEthanolPctRequiredValidator);

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setDateFormatter(DateDisplayFormat.TOSTRING);
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);

    /*
    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin); //setValue(currentInventoryBeginDateMin);//
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedBeginDateItem.setRequired(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin); //setValue(currentInventoryBeginDateMin);//
    fuelUsedEndDateItem.setDefaultValue(currentInventoryEndDateMax);//setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedEndDateItem.setRequired(Boolean.TRUE);
*/
    //IntegerItem organizationId = new IntegerItem();
    //organizationId.setName("organizationId");

    mobileCombustionForm.setItems(fuelSourceDescriptionItem ,vehicleTypeItem, vehicleYearItem, fuelTypeItem, fuelQuantityItem, fuelUnitItem, milesTravelledItem, bioFuelPercentItem,ethanolPercentItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    /*
    //final List<FormItem> mobileCombustionFormItems = new ArrayList<FormItem>();
    mobileCombustionFormItems.add(fuelSourceDescriptionItem);
    mobileCombustionFormItems.add(vehicleTypeItem);
    mobileCombustionFormItems.add(vehicleYearItem);
    mobileCombustionFormItems.add(fuelTypeItem);
    mobileCombustionFormItems.add(fuelQuantityItem);
    mobileCombustionFormItems.add(fuelUnitItem);
    mobileCombustionFormItems.add(milesTravelledItem);
    mobileCombustionFormItems.add(bioFuelPercentItem);
    mobileCombustionFormItems.add(ethanolPercentItem);
    mobileCombustionFormItems.add(fuelUsedBeginDateItem);
    mobileCombustionFormItems.add(fuelUsedEndDateItem);
    */

    setIbLFormWindow("Please enter mobile combustion source information:", mobileCombustionForm,
                      mobileCombustionFormWindow, mobileCombustionDataGrid );

/*
    final IButton mobileCombustionCancelButton = new IButton();
    final IButton mobileCombustionSaveButton = new IButton();

    mobileCombustionSaveButton.setTitle("SAVE");
    mobileCombustionSaveButton.setTooltip("Save this Mobile Combustion Source");
    mobileCombustionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if (mobileCombustionForm.validate()) {
            Record mobileCombustionFormRecord = mobileCombustionForm.getValuesAsRecord();
            Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
            mobileCombustionFormRecord.setAttribute("organizationId", organizationIdValue);
            mobileCombustionDataGrid.updateData(mobileCombustionFormRecord);
            mobileCombustionForm.clearValues();
            //mobileCombustionForm.markForRedraw();
            mobileCombustionFormWindow.hide();
        } else {
            SC.say("Please provide proper information");
        }
      }
    });

    mobileCombustionCancelButton.setTitle("CANCEL");
    mobileCombustionCancelButton.setTooltip("Cancel");
    mobileCombustionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        mobileCombustionFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(mobileCombustionCancelButton);
    buttons.addMember(mobileCombustionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(mobileCombustionForm);
    dialog.addMember(buttons);
    mobileCombustionFormWindow.setShowShadow(true);
    //mobileCombustionFormWindow.setShowTitle(false);
    mobileCombustionFormWindow.setIsModal(true);
    mobileCombustionFormWindow.setPadding(20);
    mobileCombustionFormWindow.setWidth(500);
    mobileCombustionFormWindow.setHeight(425);
    mobileCombustionFormWindow.setShowMinimizeButton(false);
    mobileCombustionFormWindow.setShowCloseButton(true);
    mobileCombustionFormWindow.setShowModalMask(true);
    mobileCombustionFormWindow.centerInPage();
    mobileCombustionFormWindow.setTitle("Please enter mobile combustion source information:");
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //mobileCombustionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    mobileCombustionFormWindow.addItem(dialog);
 *
 */
 }
private void mobileCombustionTab_OLD() {
        mobileCombustionLayout.setWidth100();
        mobileCombustionLayout.setHeight100();

        VLayout mobileCombustionTabLayout = new VLayout(15);
        mobileCombustionTabLayout.setWidth100();
        mobileCombustionTabLayout.setHeight100();

        Label mobileCombustionDataLabel = new Label("Current mobile combustion sources");
        mobileCombustionDataLabel.setHeight(15);
        mobileCombustionDataLabel.setWidth100();
        mobileCombustionDataLabel.setAlign(Alignment.LEFT);
        mobileCombustionDataLabel.setStyleName("labels");

        //mobileCombustionTabLayout.addMember(mobileCombustionDataLabel);
/*
//--ListGrid setup
        mobileCombustionDataGrid.setWidth100();
        //mobileCombustionDataGrid.setHeight(200);
        mobileCombustionDataGrid.setHeight100();
        mobileCombustionDataGrid.setShowRecordComponents(true);
        mobileCombustionDataGrid.setShowRecordComponentsByCell(true);
        mobileCombustionDataGrid.setCanRemoveRecords(true);
        //mobileCombustionDataGrid.setShowAllRecords(true);
        mobileCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        //mobileCombustionDataGrid.setCanEdit(Boolean.TRUE);
        mobileCombustionDataGrid.setDataSource(mobileCombustionInfoDS);
        mobileCombustionDataGrid.setCanHover(true);
        mobileCombustionDataGrid.setShowHover(true);
        mobileCombustionDataGrid.setShowHoverComponents(true);
        //mobileCombustionDataGrid.setCanExpandRecords(true);
        //mobileCombustionDataGrid.setExpansionMode(ExpansionMode.DETAILS);
        mobileCombustionDataGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
            public void onRecordDoubleClick(RecordDoubleClickEvent event) {
                mobileCombustionForm.editRecord(event.getRecord());
                mobileCombustionFormWindow.show();
            }
        });
*/
        //mobileCombustionDataGrid.setS
//--Only fetch data that is related to THE organization
        //Record organizationRecord= organizationForm.getValuesAsRecord();
        //mobileCombustionDataGrid.fetchRelatedData(organizationRecord,organizationDS);

        //mobileCombustionDataGrid.fetchData();

//        ListGridField organizationNameField = new ListGridField("name", "Organization Name");
//        organizationNameField.setType(ListGridFieldType.TEXT);

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);
        vehicleTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        DetailViewerField vehicleYearField = new DetailViewerField("vehicleYear", "Vehicle Year");
        //vehicleYearField.setType(ListGridFieldType.TEXT);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);

        ListGridField fuelQuantityField = new ListGridField("fuelQuantity", "Fuel Quantity");
        fuelQuantityField.setType(ListGridFieldType.FLOAT);
        fuelQuantityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);
        fuelQuantityField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        fuelUnitField.setType(ListGridFieldType.TEXT);
        fuelUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        DetailViewerField milesTravelledField = new DetailViewerField("milesTravelled", "Miles Travelled");
        //milesTravelledField.setType(ListGridFieldType.FLOAT);

        milesTravelledField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        DetailViewerField bioFuelPercentField = new DetailViewerField("bioFuelPercent", "Biofuel Percent");
        //bioFuelPercentField.setType(ListGridFieldType.FLOAT);
        bioFuelPercentField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        DetailViewerField ethanolPercentField = new DetailViewerField("ethanolPercent", "Ethanol Percent");
        //ethanolPercentField.setType(ListGridFieldType.FLOAT);
        ethanolPercentField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);
        editButtonField.setWidth(EDIT_BUTTON_FIELD_WIDTH);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        removeButtonField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);

        //removeButtonField.setWidth(100);
        //mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField,vehicleYearField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, milesTravelledField, bioFuelPercentField, ethanolPercentField,fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);
        mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);
        //mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField , removeButtonField);

        //-Make a list of Fields to be be used in ListGrid for mobileCombustion
/*
        //List<ListGridField> mobileCombustionListGridFields = new ArrayList<ListGridField>();
        mobileCombustionListGridFields.add(sourceDescriptionField);
        mobileCombustionListGridFields.add(vehicleTypeField);
        mobileCombustionListGridFields.add(fuelTypeField);
        mobileCombustionListGridFields.add(fuelQuantityField);
        mobileCombustionListGridFields.add(fuelUnitField);
        mobileCombustionListGridFields.add(fuelUsedBeginDateField);
        mobileCombustionListGridFields.add(fuelUsedEndDateField);
        mobileCombustionListGridFields.add(editButtonField);
        mobileCombustionListGridFields.add(removeButtonField);
*/

/*
//--Getting rid of displaying detailViewer at the bottom
        List<DetailViewerField> items = new ArrayList<DetailViewerField>();
        items.add(vehicleYearField);
        items.add(milesTravelledField);
        items.add(bioFuelPercentField);
        items.add(ethanolPercentField);

        final DetailViewerField[] fitems = new DetailViewerField[items.size()];
        items.toArray(fitems);

        mobileCombustionDataGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                //emissionsSummaryDetailViewer.reset();
                //--remove existing child from middleMiddleHLayout
                displayDetailInfo(mobileCombustionDataGrid, fitems);

            }
        });
*/
        //detailViewerFieldList = {bioFuelPercentField, ethanolPercentField};

        //mobileCombustionDataGrid.setOverflow(Overflow.VISIBLE);
        //mobileCombustionDataGrid.setAutoWidth();

        //mobileCombustionTabLayout.addMember(mobileCombustionDataGrid);

        IButton newMobileCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newMobileCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        //newMobileCombustionButton.sets
        newMobileCombustionButton.setIcon("addIcon.jpg");

        newMobileCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                mobileCombustionForm.editNewRecord();
                mobileCombustionFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(mobileCombustionDataLabel);
        gridButtonLayout.addMember(newMobileCombustionButton);
//        gridButtonLayout.addMember(editMobileCombustionButton);
//        gridButtonLayout.addMember(removeMobileCombustionButton);
        mobileCombustionTabLayout.addMember(gridButtonLayout);
        mobileCombustionTabLayout.addMember(mobileCombustionDataGrid);

//--Defining Mobile Combustion tab
        final Tab mobileCombustionTab = new Tab("Mobile Combustion");
        mobileCombustionTab.setPane(mobileCombustionTabLayout);

//---Adding Mobile Combustion tab to tabSet
        mobileCombustionTabSet.addTab(mobileCombustionTab);
        mobileCombustionLayout.addMember(mobileCombustionTabSet);

}
private void mobileCombustionTab_2() {
  /*
        mobileCombustionLayout.setWidth100();
        mobileCombustionLayout.setHeight100();

        VLayout mobileCombustionTabLayout = new VLayout(15);
        mobileCombustionTabLayout.setWidth100();
        mobileCombustionTabLayout.setHeight100();

        Label mobileCombustionDataLabel = new Label("Current mobile combustion sources");
        mobileCombustionDataLabel.setHeight(15);
        mobileCombustionDataLabel.setWidth100();
        mobileCombustionDataLabel.setAlign(Alignment.LEFT);
        mobileCombustionDataLabel.setStyleName("labels");
*/
        //mobileCombustionTabLayout.addMember(mobileCombustionDataLabel);
/*
//--ListGrid setup
        mobileCombustionDataGrid.setWidth100();
        //mobileCombustionDataGrid.setHeight(200);
        mobileCombustionDataGrid.setHeight100();
        mobileCombustionDataGrid.setShowRecordComponents(true);
        mobileCombustionDataGrid.setShowRecordComponentsByCell(true);
        //mobileCombustionDataGrid.setCanRemoveRecords(true);
        //mobileCombustionDataGrid.setShowAllRecords(true);
        mobileCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        mobileCombustionDataGrid.setCanEdit(Boolean.TRUE);
        mobileCombustionDataGrid.setDataSource(mobileCombustionInfoDS);
        mobileCombustionDataGrid.setCanHover(true);
        mobileCombustionDataGrid.setShowHover(true);
        //mobileCombustionDataGrid.setShowHoverComponents(true);
*/
        //mobileCombustionDataGrid.setS
//--Only fetch data that is related to THE organization
        //Record organizationRecord= organizationForm.getValuesAsRecord();
        //mobileCombustionDataGrid.fetchRelatedData(organizationRecord,organizationDS);

        //mobileCombustionDataGrid.fetchData();

//        ListGridField organizationNameField = new ListGridField("name", "Organization Name");
//        organizationNameField.setType(ListGridFieldType.TEXT);

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);
        vehicleTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        DetailViewerField vehicleYearField = new DetailViewerField("vehicleYear", "Vehicle Year");
        //vehicleYearField.setType(ListGridFieldType.TEXT);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);

        ListGridField fuelQuantityField = new ListGridField("fuelQuantity", "Fuel Quantity");
        fuelQuantityField.setType(ListGridFieldType.FLOAT);
        fuelQuantityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);
        fuelQuantityField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        fuelUnitField.setType(ListGridFieldType.TEXT);
        fuelUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        DetailViewerField milesTravelledField = new DetailViewerField("milesTravelled", "Miles Travelled");
        //milesTravelledField.setType(ListGridFieldType.FLOAT);

        milesTravelledField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        DetailViewerField bioFuelPercentField = new DetailViewerField("bioFuelPercent", "Biofuel Percent");
        //bioFuelPercentField.setType(ListGridFieldType.FLOAT);
        bioFuelPercentField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        DetailViewerField ethanolPercentField = new DetailViewerField("ethanolPercent", "Ethanol Percent");
        //ethanolPercentField.setType(ListGridFieldType.FLOAT);
        ethanolPercentField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);
        editButtonField.setWidth(EDIT_BUTTON_FIELD_WIDTH);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        removeButtonField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);

        //removeButtonField.setWidth(100);

        //mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField,vehicleYearField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, milesTravelledField, bioFuelPercentField, ethanolPercentField,fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);
        //mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //-Make a list of Fields to be be used in ListGrid for mobileCombustion

        //List<ListGridField> mobileCombustionListGridFields = new ArrayList<ListGridField>();
        mobileCombustionListGridFields.add(sourceDescriptionField);
        mobileCombustionListGridFields.add(vehicleTypeField);
        mobileCombustionListGridFields.add(fuelTypeField);
        mobileCombustionListGridFields.add(fuelQuantityField);
        mobileCombustionListGridFields.add(fuelUnitField);
        mobileCombustionListGridFields.add(fuelUsedBeginDateField);
        mobileCombustionListGridFields.add(fuelUsedEndDateField);
        //mobileCombustionListGridFields.add(editButtonField);
        //mobileCombustionListGridFields.add(removeButtonField);

/*
//--Getting rid of displaying detailViewer at the bottom
        List<DetailViewerField> items = new ArrayList<DetailViewerField>();
        items.add(vehicleYearField);
        items.add(milesTravelledField);
        items.add(bioFuelPercentField);
        items.add(ethanolPercentField);

        final DetailViewerField[] fitems = new DetailViewerField[items.size()];
        items.toArray(fitems);

        mobileCombustionDataGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                //emissionsSummaryDetailViewer.reset();
                //--remove existing child from middleMiddleHLayout
                displayDetailInfo(mobileCombustionDataGrid, fitems);

            }
        });
*/
        //detailViewerFieldList = {bioFuelPercentField, ethanolPercentField};

        //mobileCombustionDataGrid.setOverflow(Overflow.VISIBLE);
        //mobileCombustionDataGrid.setAutoWidth();

        //mobileCombustionTabLayout.addMember(mobileCombustionDataGrid);

/*
        IButton newMobileCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newMobileCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        //newMobileCombustionButton.sets
        newMobileCombustionButton.setIcon("addIcon.jpg");

        newMobileCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                mobileCombustionForm.editNewRecord();
                mobileCombustionFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(mobileCombustionDataLabel);
        gridButtonLayout.addMember(newMobileCombustionButton);
//        gridButtonLayout.addMember(editMobileCombustionButton);
//        gridButtonLayout.addMember(removeMobileCombustionButton);
        mobileCombustionTabLayout.addMember(gridButtonLayout);
        mobileCombustionTabLayout.addMember(mobileCombustionDataGrid);

//--Defining Mobile Combustion tab
        final Tab mobileCombustionTab = new Tab("Mobile Combustion");
        mobileCombustionTab.setPane(mobileCombustionTabLayout);

//---Adding Mobile Combustion tab to tabSet
        mobileCombustionTabSet.addTab(mobileCombustionTab);
        mobileCombustionLayout.addMember(mobileCombustionTabSet);
*/

}

private void initStationaryCombustionEditForm_2() {
/*
    stationaryCombustionForm.setCellPadding(5);
    stationaryCombustionForm.setWidth("100%");
 *
 */
    //stationaryCombustionForm.setTitle("Please enter stationary combustion Source information:");

//-- setValidators for the forms for common types.
    //initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    /*
    TextItem fuelSourceDescription = new TextItem("fuelSourceDescription");
    fuelSourceDescription.setTitle("Fuel Source Description");
    fuelSourceDescription.setSelectOnFocus(true);
    fuelSourceDescription.setWrapTitle(false);
    fuelSourceDescription.setDefaultValue("[Enter Source Description]");
    fuelSourceDescription.setRequired(Boolean.TRUE);
    //fuelSourceDescription
    */

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("fuelSourceDescription");
    final SelectItem fuelTypeItem = new SelectItem();
    fuelTypeItem.setName("fuelType");
    //fuelTypeItem.setPickListWidth(310);
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(eF_StationaryCombustion_EPADS);
    fuelTypeItem.setRequired(Boolean.TRUE);

    fuelTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            //stationaryCombustionForm.clearValue("fuelUnit");
            Record selectedFuelTypeRecord = fuelTypeItem.getSelectedRecord();
            dataForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
        }
    });

    final StaticTextItem fuelUnitItem = new StaticTextItem("fuelUnit");
/*
    final SelectItem fuelUnitItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String fuelType = (String) fuelTypeItem.getValue();
            Criteria criteria = new Criteria("fuelType", fuelType);
            return criteria;
        }
    };
  */

    //fuelUnitItem.setName("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");
    //fuelUnitItem.setPickListWidth(250);
    //fuelUnitItem.setOptionDataSource(eF_StationaryCombustion_EPADS);

    //TextItem testTextItem = new TextItem("fuelUnit2");
///*

 //*/

    /*
    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");
    fuelQuantityItem.setValidators(floatValidator);
    fuelQuantityItem.setValidateOnExit(Boolean.TRUE);
    fuelQuantityItem.setValidateOnChange(Boolean.TRUE);
    fuelQuantityItem.setRequired(Boolean.TRUE);
    */

    FuelQuantityItem fuelQuantityItem = new FuelQuantityItem();

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setWidth("*");
    //fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    //fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    //fuelUsedBeginDateItem.setAttribute("dateFormatter", displayDateFormatter);
    fuelUsedBeginDateItem.setValidators(validateDateRange);

    fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedBeginDateItem.setRequired(Boolean.TRUE);
    //fuelUsedBeginDateItem.setUseTextField(Boolean.TRUE);
    //fuelUsedBeginDateItem.setValue(currentInventoryBeginDateMin);
    //fuelUsedBeginDateItem.setDefaultValue(currentInventoryBeginDateMin);

    /*
    FuelUsedDateItem fuelUsedBeginDateItem = new FuelUsedDateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    */

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setWidth("*");
    //fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    //fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    //fuelUsedEndDateItem.setAttribute("dateFormatter", displayDateFormatter);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    //fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedEndDateItem.setRequired(Boolean.TRUE);
    //fuelUsedEndDateItem.setValue(currentInventoryEndDateMax);

/*
    FuelUsedDateItem fuelUsedEndDateItem = new FuelUsedDateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
*/
    //SC.say("currentInventoryEndDateMax : "+currentInventoryEndDateMax);
    //Record organizationSelectFormRecord = UserOrganizationHeader.organizationSelectForm.getValuesAsRecord();
    //Integer currentOrganizationId = organizationSelectFormRecord.getAttributeAsInt("id");

    //IntegerItem organizationId = new IntegerItem();
    //organizationId.setName("organizationId");
    //SC.say("currentOrganizationId " + currentOrganizationId);
    //organizationId.setValue(currentOrganizationId);

//    organizationId.setValue(currentOrganizationId);
//    Integer currentOrganizationId = (Integer)organizationForm.getItem("id").getValue();

    //stationaryCombustionForm.setItems(fuelSourceDescriptionItem, fuelTypeItem, fuelQuantityItem, fuelUnitItem, fuelUsedBeginDateItem, fuelUsedEndDateItem);

    //final List<FormItem> stationaryCombustionFormItems = new ArrayList<FormItem>();
    stationaryCombustionFormItems.add(fuelSourceDescriptionItem);
    stationaryCombustionFormItems.add(fuelTypeItem);
    stationaryCombustionFormItems.add(fuelQuantityItem);
    stationaryCombustionFormItems.add(fuelUnitItem);
    stationaryCombustionFormItems.add(fuelUsedBeginDateItem);
    stationaryCombustionFormItems.add(fuelUsedEndDateItem);

/*
    HashMap formDefaultValue = new HashMap();
    formDefaultValue.put("fuelUsedBeginDate", currentInventoryBeginDateMin);
    stationaryCombustionForm.setValues(formDefaultValue);
*/
/*
    final IButton stationaryCombustionCancelButton = new IButton();
    final IButton stationaryCombustionSaveButton = new IButton();

    stationaryCombustionSaveButton.setTitle("SAVE");
    stationaryCombustionSaveButton.setTooltip("Save this Stationary Combustion Source");
    stationaryCombustionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if ((!stationaryCombustionForm.getErrors().isEmpty()) || !stationaryCombustionForm.validate()){
            SC.say("Please clear errors before submitting this information!");
        }
        else {
            Record stationaryCombustionFormRecord = stationaryCombustionForm.getValuesAsRecord();
            Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
            //String organizationNameValue = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
            stationaryCombustionFormRecord.setAttribute("organizationId", organizationIdValue);
            stationaryCombustionDataGrid.updateData(stationaryCombustionFormRecord);
            //stationaryCombustionForm.clearValues();
            stationaryCombustionForm.markForRedraw();
            stationaryCombustionFormWindow.hide();
        }
      }
    });

    stationaryCombustionCancelButton.setTitle("CANCEL");
    stationaryCombustionCancelButton.setTooltip("Cancel");
    stationaryCombustionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        stationaryCombustionForm.clearValues();
        stationaryCombustionFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(stationaryCombustionCancelButton);
    buttons.addMember(stationaryCombustionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(stationaryCombustionForm);
    dialog.addMember(buttons);
    stationaryCombustionFormWindow.setShowShadow(true);
    //stationaryCombustionFormWindow.setShowTitle(false);
    stationaryCombustionFormWindow.setIsModal(true);
    stationaryCombustionFormWindow.setPadding(20);
    stationaryCombustionFormWindow.setWidth(500);
    stationaryCombustionFormWindow.setHeight(260);
    stationaryCombustionFormWindow.setShowMinimizeButton(false);
    stationaryCombustionFormWindow.setShowCloseButton(true);
    stationaryCombustionFormWindow.setShowModalMask(true);
    stationaryCombustionFormWindow.centerInPage();
    stationaryCombustionFormWindow.setTitle("Please enter stationary combustion source information:");
    //stationaryCombustionFormWindow.setStyleName("labels");
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //stationaryCombustionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    stationaryCombustionFormWindow.addItem(dialog);
 *
 */
}
private void stationaryCombustionTab_OLD() {
        stationaryCombustionLayout.setWidth100();
        stationaryCombustionLayout.setHeight100();

        VLayout stationaryCombustionTabLayout = new VLayout(15);

        stationaryCombustionTabLayout.setWidth100();
        stationaryCombustionTabLayout.setHeight100();

        Label stationaryCombustionDataLabel = new Label("Current stationary combustion sources");
        stationaryCombustionDataLabel.setHeight(15);
        stationaryCombustionDataLabel.setWidth100();
        stationaryCombustionDataLabel.setAlign(Alignment.LEFT);
        stationaryCombustionDataLabel.setStyleName("labels");

        //stationaryCombustionTabLayout.addMember(stationaryCombustionDataLabel);

       /*
//--ListGrid setup
        stationaryCombustionDataGrid.setWidth100();
        //stationaryCombustionDataGrid.setHeight(200);
        stationaryCombustionDataGrid.setHeight100();
        stationaryCombustionDataGrid.setShowRecordComponents(true);
        stationaryCombustionDataGrid.setShowRecordComponentsByCell(true);
        //stationaryCombustionDataGrid.setCanRemoveRecords(true);
        //stationaryCombustionDataGrid.setShowAllRecords(true);
        stationaryCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        stationaryCombustionDataGrid.setDataSource(stationaryCombustionInfoDS);
        //stationaryCombustionDataGrid.setHeaderBackgroundColor("#4096EE");
*/
        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);

        FloatListGridField fuelQuantityField = new FloatListGridField("fuelQuantity", "Fuel Quantity");
        //fuelQuantityField.setType(ListGridFieldType.FLOAT);
        fuelQuantityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        /*
        fuelQuantityField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        fuelUnitField.setType(ListGridFieldType.TEXT);
        fuelUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);
        //fuelUsedBeginDateField.setAttribute("dateFormatter", displayDateFormatter);

        //fuelUsedBeginDateField.setAlign(Alignment.LEFT);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);
        //fuelUsedEndDateField.setAlign(Alignment.LEFT);
        //fuelUsedEndDateField.setAttribute("dateFormatter", displayDateFormatter);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        //editButtonField.setAlign(Alignment.CENTER);
        editButtonField.setWidth(EDIT_BUTTON_FIELD_WIDTH);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        removeButtonField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);
        //removeButtonField.setWidth(100);

        //stationaryCombustionDataGrid.setFields(sourceDescriptionField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField,editButtonField, removeButtonField);
        stationaryCombustionDataGrid.setFields(sourceDescriptionField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField);

        //stationaryCombustionTabLayout.addMember(stationaryCombustionDataGrid);

        IButton newStationaryCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newStationaryCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        newStationaryCombustionButton.setIcon(ADD_ICON_IMAGE);

        newStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                stationaryCombustionForm.editNewRecord();
                stationaryCombustionForm.setValues(getInitialValues());
                stationaryCombustionFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(stationaryCombustionDataLabel);
        gridButtonLayout.addMember(newStationaryCombustionButton);
//        gridButtonLayout.addMember(editStationaryCombustionButton);
//        gridButtonLayout.addMember(removeStationaryCombustionButton);
        stationaryCombustionTabLayout.addMember(gridButtonLayout);
        stationaryCombustionTabLayout.addMember(stationaryCombustionDataGrid);

//--Initialize stuff for Emissions Summary

// For now commented below just to see how dynamic content management will work for one module at a time display.
//--Defining Stationary Combustion tab
        final Tab stationaryCombustionTab = new Tab("Stationary Combustion");
        stationaryCombustionTab.setPane(stationaryCombustionTabLayout);

//---Adding Stationary Combustion tab to topTab
        stationaryCombustionTabSet.addTab(stationaryCombustionTab);
        stationaryCombustionLayout.addMember(stationaryCombustionTabSet);

}
private void initStationaryCombustionEditForm_OLD() {

    stationaryCombustionForm.setCellPadding(5);
    stationaryCombustionForm.setWidth("100%");
    //stationaryCombustionForm.setTitle("Please enter stationary combustion Source information:");

//-- setValidators for the forms for common types.
    initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    /*
    TextItem fuelSourceDescription = new TextItem("fuelSourceDescription");
    fuelSourceDescription.setTitle("Fuel Source Description");
    fuelSourceDescription.setSelectOnFocus(true);
    fuelSourceDescription.setWrapTitle(false);
    fuelSourceDescription.setDefaultValue("[Enter Source Description]");
    fuelSourceDescription.setRequired(Boolean.TRUE);
    //fuelSourceDescription
    */

    FuelSourceDescriptionItem fuelSourceDescription = new FuelSourceDescriptionItem();
    fuelSourceDescription.setName("fuelSourceDescription");
    final SelectItem fuelTypeItem = new SelectItem();
    fuelTypeItem.setName("fuelType");
    //fuelTypeItem.setPickListWidth(310);
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(eF_StationaryCombustion_EPADS);
    fuelTypeItem.setRequired(Boolean.TRUE);

    fuelTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            //stationaryCombustionForm.clearValue("fuelUnit");
            Record selectedFuelTypeRecord = fuelTypeItem.getSelectedRecord();
            stationaryCombustionForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
        }
    });

    final StaticTextItem fuelUnitItem = new StaticTextItem("fuelUnit");

    /*
    final SelectItem fuelUnitItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String fuelType = (String) fuelTypeItem.getValue();
            Criteria criteria = new Criteria("fuelType", fuelType);
            return criteria;
        }
    };
    */

    //fuelUnitItem.setName("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");
    //fuelUnitItem.setPickListWidth(250);
    //fuelUnitItem.setOptionDataSource(eF_StationaryCombustion_EPADS);

    //TextItem testTextItem = new TextItem("fuelUnit2");
///*

 //*/

    /*
    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");
    fuelQuantityItem.setValidators(floatValidator);
    fuelQuantityItem.setValidateOnExit(Boolean.TRUE);
    fuelQuantityItem.setValidateOnChange(Boolean.TRUE);
    fuelQuantityItem.setRequired(Boolean.TRUE);
    */

    FuelQuantityItem fuelQuantityItem = new FuelQuantityItem();

/*
    RelativeDateItem fuelUsedBeginDateItem = new RelativeDateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setValue(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setOperator(OperatorId.GREATER_OR_EQUAL);
    fuelUsedBeginDateItem.setDateFormatter(DateDisplayFormat.valueOf("MMM d, yyyy"));

    RelativeDateItem fuelUsedEndDateItem = new RelativeDateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setValue(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setOperator(OperatorId.LESS_OR_EQUAL);
    fuelUsedEndDateItem.setDateFormatter(DateDisplayFormat.valueOf("MMM d, yyyy"));
*/

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    //fuelUsedBeginDateItem.setCriteriaField("emissionPeriodDate");
    //fuelUsedBeginDateItem.setOperator(OperatorId.GREATER_OR_EQUAL);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    //fuelUsedEndDateItem.setCriteriaField("emissionPeriodDate");
    //fuelUsedEndDateItem.setOperator(OperatorId.LESS_OR_EQUAL);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);

/*
    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setWidth("*");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    //fuelUsedBeginDateItem.setAttribute("dateFormatter", displayDateFormatter);
    fuelUsedBeginDateItem.setValidators(validateDateRange);

    fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedBeginDateItem.setRequired(Boolean.TRUE);
    //fuelUsedBeginDateItem.setUseTextField(Boolean.TRUE);
    //fuelUsedBeginDateItem.setValue(currentInventoryBeginDateMin);
    //fuelUsedBeginDateItem.setDefaultValue(currentInventoryBeginDateMin);
*/
    /*
    FuelUsedDateItem fuelUsedBeginDateItem = new FuelUsedDateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    */
    /*
    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setWidth("*");
    //fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    //fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    //fuelUsedEndDateItem.setAttribute("dateFormatter", displayDateFormatter);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    //fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedEndDateItem.setRequired(Boolean.TRUE);
    //fuelUsedEndDateItem.setValue(currentInventoryEndDateMax);
    */
/*
    FuelUsedDateItem fuelUsedEndDateItem = new FuelUsedDateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
*/
    //SC.say("currentInventoryEndDateMax : "+currentInventoryEndDateMax);
    //Record organizationSelectFormRecord = UserOrganizationHeader.organizationSelectForm.getValuesAsRecord();
    //Integer currentOrganizationId = organizationSelectFormRecord.getAttributeAsInt("id");

    //IntegerItem organizationId = new IntegerItem();
    //organizationId.setName("organizationId");
    //SC.say("currentOrganizationId " + currentOrganizationId);
    //organizationId.setValue(currentOrganizationId);

//    organizationId.setValue(currentOrganizationId);
//    Integer currentOrganizationId = (Integer)organizationForm.getItem("id").getValue();

    stationaryCombustionForm.setItems(fuelSourceDescription, fuelTypeItem, fuelQuantityItem, fuelUnitItem, fuelUsedBeginDateItem, fuelUsedEndDateItem);
/*
    HashMap formDefaultValue = new HashMap();
    formDefaultValue.put("fuelUsedBeginDate", currentInventoryBeginDateMin);
    stationaryCombustionForm.setValues(formDefaultValue);
*/
    final IButton stationaryCombustionCancelButton = new IButton();
    final IButton stationaryCombustionSaveButton = new IButton();

    stationaryCombustionSaveButton.setTitle("SAVE");
    stationaryCombustionSaveButton.setTooltip("Save this Stationary Combustion Source");
    stationaryCombustionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if ((!stationaryCombustionForm.getErrors().isEmpty()) || !stationaryCombustionForm.validate()){
            SC.say("Please clear errors before submitting this information!");
        }
        else {
            Record stationaryCombustionFormRecord = stationaryCombustionForm.getValuesAsRecord();
            Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
            //String organizationNameValue = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
            stationaryCombustionFormRecord.setAttribute("organizationId", organizationIdValue);
            stationaryCombustionDataGrid.updateData(stationaryCombustionFormRecord);
            //stationaryCombustionForm.clearValues();
            stationaryCombustionForm.markForRedraw();
            stationaryCombustionFormWindow.hide();
        }
      }
    });

    stationaryCombustionCancelButton.setTitle("CANCEL");
    stationaryCombustionCancelButton.setTooltip("Cancel");
    stationaryCombustionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        stationaryCombustionForm.clearValues();
        stationaryCombustionFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(stationaryCombustionCancelButton);
    buttons.addMember(stationaryCombustionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(stationaryCombustionForm);
    dialog.addMember(buttons);
    stationaryCombustionFormWindow.setShowShadow(true);
    //stationaryCombustionFormWindow.setShowTitle(false);
    stationaryCombustionFormWindow.setIsModal(true);
    stationaryCombustionFormWindow.setPadding(20);
    stationaryCombustionFormWindow.setWidth(500);
    stationaryCombustionFormWindow.setHeight(260);
    stationaryCombustionFormWindow.setShowMinimizeButton(false);
    stationaryCombustionFormWindow.setShowCloseButton(true);
    stationaryCombustionFormWindow.setShowModalMask(true);
    stationaryCombustionFormWindow.centerInPage();
    stationaryCombustionFormWindow.setTitle("Please enter stationary combustion source information:");
    //stationaryCombustionFormWindow.setStyleName("labels");
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //stationaryCombustionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    stationaryCombustionFormWindow.addItem(dialog);
}
private void stationaryCombustionTab_2() {
    /*
        stationaryCombustionLayout.setWidth100();
        stationaryCombustionLayout.setHeight100();

        VLayout stationaryCombustionTabLayout = new VLayout(15);

        stationaryCombustionTabLayout.setWidth100();
        stationaryCombustionTabLayout.setHeight100();

        Label stationaryCombustionDataLabel = new Label("Current stationary combustion sources");
        stationaryCombustionDataLabel.setHeight(15);
        stationaryCombustionDataLabel.setWidth100();
        stationaryCombustionDataLabel.setAlign(Alignment.LEFT);
        stationaryCombustionDataLabel.setStyleName("labels");
*/
        //stationaryCombustionTabLayout.addMember(stationaryCombustionDataLabel);
/*
//--ListGrid setup
        stationaryCombustionDataGrid.setWidth100();
        //stationaryCombustionDataGrid.setHeight(200);
        stationaryCombustionDataGrid.setHeight100();
        stationaryCombustionDataGrid.setShowRecordComponents(true);
        stationaryCombustionDataGrid.setShowRecordComponentsByCell(true);
        //stationaryCombustionDataGrid.setCanRemoveRecords(true);
        //stationaryCombustionDataGrid.setShowAllRecords(true);
        stationaryCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        stationaryCombustionDataGrid.setDataSource(stationaryCombustionInfoDS);
        //stationaryCombustionDataGrid.setHeaderBackgroundColor("#4096EE");
*/
        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);

        FloatListGridField fuelQuantityField = new FloatListGridField("fuelQuantity", "Fuel Quantity");
        //fuelQuantityField.setType(ListGridFieldType.FLOAT);
        fuelQuantityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        
        fuelQuantityField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        fuelUnitField.setType(ListGridFieldType.TEXT);
        fuelUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);
        //fuelUsedBeginDateField.setAttribute("dateFormatter", displayDateFormatter);

        //fuelUsedBeginDateField.setAlign(Alignment.LEFT);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);
        //fuelUsedEndDateField.setAlign(Alignment.LEFT);
        //fuelUsedEndDateField.setAttribute("dateFormatter", displayDateFormatter);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        //editButtonField.setAlign(Alignment.CENTER);
        editButtonField.setWidth(EDIT_BUTTON_FIELD_WIDTH);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        removeButtonField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);
        //removeButtonField.setWidth(100);

        //stationaryCombustionDataGrid.setFields(sourceDescriptionField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField,editButtonField, removeButtonField);

        //stationaryCombustionTabLayout.addMember(stationaryCombustionDataGrid);
        stationaryCombustionListGridFields.add(sourceDescriptionField);
        stationaryCombustionListGridFields.add(organizationIdField);
        stationaryCombustionListGridFields.add(fuelTypeField);
        stationaryCombustionListGridFields.add(fuelQuantityField);
        stationaryCombustionListGridFields.add(fuelUnitField);
        stationaryCombustionListGridFields.add(fuelUsedBeginDateField);
        stationaryCombustionListGridFields.add(fuelUsedEndDateField);
        //stationaryCombustionListGridFields.add(editButtonField);
        //stationaryCombustionListGridFields.add(removeButtonField);
/*
        IButton newStationaryCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newStationaryCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        newStationaryCombustionButton.setIcon(ADD_ICON_IMAGE);

        newStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                stationaryCombustionForm.editNewRecord();
                stationaryCombustionForm.setValues(getInitialValues());
                stationaryCombustionFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(stationaryCombustionDataLabel);
        gridButtonLayout.addMember(newStationaryCombustionButton);
//        gridButtonLayout.addMember(editStationaryCombustionButton);
//        gridButtonLayout.addMember(removeStationaryCombustionButton);
        stationaryCombustionTabLayout.addMember(gridButtonLayout);
        stationaryCombustionTabLayout.addMember(stationaryCombustionDataGrid);

//--Initialize stuff for Emissions Summary

// For now commented below just to see how dynamic content management will work for one module at a time display.
//--Defining Stationary Combustion tab
        final Tab stationaryCombustionTab = new Tab("Stationary Combustion");
        stationaryCombustionTab.setPane(stationaryCombustionTabLayout);

//---Adding Stationary Combustion tab to topTab
        stationaryCombustionTabSet.addTab(stationaryCombustionTab);
        stationaryCombustionLayout.addMember(stationaryCombustionTabSet);
*/
}

private void refridgerationAirConditioningTab_OLD() {

        VLayout refridgerationAirConditioningLayout_1 = new VLayout(15);
        VLayout refridgerationAirConditioningLayout_2 = new VLayout(15);
        VLayout refridgerationAirConditioningLayout_3 = new VLayout(15);

        refridgerationAirConditioningLayout_1.setWidth100();
        refridgerationAirConditioningLayout_1.setHeight100();
        refridgerationAirConditioningLayout_2.setWidth100();
        refridgerationAirConditioningLayout_2.setHeight100();
        refridgerationAirConditioningLayout_3.setWidth100();
        refridgerationAirConditioningLayout_3.setHeight100();

	refridgerationAirConditioningLayout.setWidth100();
	refridgerationAirConditioningLayout.setHeight100();

        /*
        Label refridgerationAirConditioningDataLabel = new Label("Current Refridgeration Air Conditioning sources");
        refridgerationAirConditioningDataLabel.setHeight(LABEL_HEIGHT);
        refridgerationAirConditioningLayout.addMember(refridgerationAirConditioningDataLabel);
        */

        final SectionStack refridgerationAirConditioningSectionStack = new SectionStack();
        refridgerationAirConditioningSectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        refridgerationAirConditioningSectionStack.setWidth100();
        refridgerationAirConditioningSectionStack.setHeight100();

//--ListGrid setup for Material balance method
        refridgerationAirConditioningDataGrid_1.setWidth("100%");
        //refridgerationAirConditioningDataGrid_1.setHeight(200);
        refridgerationAirConditioningDataGrid_1.setHeight("100%");
        refridgerationAirConditioningDataGrid_1.setShowRecordComponents(true);
        refridgerationAirConditioningDataGrid_1.setShowRecordComponentsByCell(true);
        //refridgerationAirConditioningDataGrid_1.setCanRemoveRecords(true);
        //refridgerationAirConditioningDataGrid_1.setShowAllRecords(true);
        refridgerationAirConditioningDataGrid_1.setAutoFetchData(Boolean.FALSE);
        refridgerationAirConditioningDataGrid_1.setDataSource(refridgerationAirConditioningInfoDS);

//--ListGrid setup for Simplified Material balance method
        refridgerationAirConditioningDataGrid_2.setWidth("100%");
        //refridgerationAirConditioningDataGrid_2.setHeight(200);
        refridgerationAirConditioningDataGrid_2.setHeight("100%");
        refridgerationAirConditioningDataGrid_2.setShowRecordComponents(true);
        refridgerationAirConditioningDataGrid_2.setShowRecordComponentsByCell(true);
        //refridgerationAirConditioningDataGrid_2.setCanRemoveRecords(true);
        //refridgerationAirConditioningDataGrid_2.setShowAllRecords(true);
        refridgerationAirConditioningDataGrid_2.setAutoFetchData(Boolean.FALSE);
        refridgerationAirConditioningDataGrid_2.setDataSource(refridgerationAirConditioningInfoDS);

//--ListGrid setup for Screening method
        refridgerationAirConditioningDataGrid_3.setWidth("100%");
        //refridgerationAirConditioningDataGrid_3.setHeight(200);
        refridgerationAirConditioningDataGrid_3.setHeight("100%");
        refridgerationAirConditioningDataGrid_3.setShowRecordComponents(true);
        refridgerationAirConditioningDataGrid_3.setShowRecordComponentsByCell(true);
        //refridgerationAirConditioningDataGrid_3.setCanRemoveRecords(true);
        //refridgerationAirConditioningDataGrid_3.setShowAllRecords(true);
        refridgerationAirConditioningDataGrid_3.setAutoFetchData(Boolean.FALSE);
        refridgerationAirConditioningDataGrid_3.setDataSource(refridgerationAirConditioningInfoDS);

//--Only fetch data that is related to THE organization
        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        //ListGridField idField = new ListGridField("Id", "Id");
        //idField.setType(ListGridFieldType.INTEGER);

        ListGridField gasTypeField = new ListGridField("gasType", "Gas Type");
        gasTypeField.setType(ListGridFieldType.TEXT);
        gasTypeField.setWidth(GAS_TYPE_FIELD_WIDTH);

//-- Material Balance fields
        FloatListGridField inventoryChangeField = new FloatListGridField("inventoryChange", "Inventory Change");
        inventoryChangeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField transferredAmountField = new FloatListGridField("transferredAmount", "Transferred Amount");
        transferredAmountField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField capacityChangeField = new FloatListGridField("capacityChange", "Capacity Change");
        capacityChangeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

//--Simplified Material Balance fields
        FloatListGridField newUnitsChargeField = new FloatListGridField("newUnitsCharge", "New Units Charge");
        newUnitsChargeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField newUnitsCapacityField = new FloatListGridField("newUnitsCapacity", "New Units Capacity");
        newUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField existingUnitsRechargeField = new FloatListGridField("existingUnitsRecharge", "Existing Units Recharge");
        existingUnitsRechargeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField disposedUnitsCapacityField = new FloatListGridField("disposedUnitsCapacity", "Disposed Units Capacity");
        disposedUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField disposedUnitsRecoveredField = new FloatListGridField("disposedUnitsRecovered", "Disposed Units Recovered");
        disposedUnitsRecoveredField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

 //--Screening Method fields
        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField typeOfEquipmentField = new ListGridField("typeOfEquipment", "Type  Of Equipment");
        typeOfEquipmentField.setType(ListGridFieldType.TEXT);
        typeOfEquipmentField.setWidth(EQUIPMENT_TYPE_FIELD_WIDTH);

        FloatListGridField sourceNewUnitsChargeField = new FloatListGridField("sourceNewUnitsCharge", "Source New Units Charge");
        sourceNewUnitsChargeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField operatingUnitsCapacityField = new FloatListGridField("operatingUnitsCapacity", "Operating Units Capacity");
        operatingUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField sourceDisposedUnitsCapacityField = new FloatListGridField("sourceDisposedUnitsCapacity", "Source Disposed Units Capacity");
        sourceDisposedUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField timeInYearsUsedField = new FloatListGridField("timeInYearsUsed", "Time In Years Used");
        timeInYearsUsedField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField methodTypeField = new ListGridField("methodType", "Method Type");
        methodTypeField.setType(ListGridFieldType.TEXT);
        methodTypeField.setHidden(true);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);
        editButtonField.setWidth(EDIT_BUTTON_FIELD_WIDTH);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        removeButtonField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);

        //removeButtonField.setWidth(100);

//-- Material Balance Method
        refridgerationAirConditioningDataGrid_1.setFields(methodTypeField, organizationIdField, sourceDescriptionField, gasTypeField,inventoryChangeField, transferredAmountField, capacityChangeField, fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

        Label materialBalanceMethodLabel = new Label("Current refridgeration & air conditioning sources");
        materialBalanceMethodLabel.setHeight(LABEL_HEIGHT);
        materialBalanceMethodLabel.setWidth100();
        materialBalanceMethodLabel.setAlign(Alignment.LEFT);
        materialBalanceMethodLabel.setStyleName("labels");

        //refridgerationAirConditioningLayout.addMember(materialBalanceMethodLabel);

        IButton newRefridgerationAirConditioningButton_1 = new IButton(ADD_NEW_SOURCE_TEXT);
        newRefridgerationAirConditioningButton_1.setWidth(ADD_BUTTON_WIDTH);
        newRefridgerationAirConditioningButton_1.setIcon(ADD_ICON_IMAGE);

        newRefridgerationAirConditioningButton_1.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                refridgerationAirConditioningForm_1.editNewRecord();
                refridgerationAirConditioningForm_1.setValues(getInitialValues());
                refridgerationAirConditioningFormWindow_1.show();
            }
        });

        HLayout gridButtonLayout_1 = new HLayout(10);
        gridButtonLayout_1.addMember(materialBalanceMethodLabel);
        gridButtonLayout_1.addMember(newRefridgerationAirConditioningButton_1);
        refridgerationAirConditioningLayout_1.addMember(gridButtonLayout_1);
        refridgerationAirConditioningLayout_1.addMember(refridgerationAirConditioningDataGrid_1);
        /*
        SectionStackSection materialBalanceSection = new SectionStackSection("materialBalance");
        materialBalanceSection.addItem(gridButtonLayout_1);
        materialBalanceSection.addItem(refridgerationAirConditioningDataGrid_1);
        */
//-- Simplified Material Balance Method
        refridgerationAirConditioningDataGrid_2.setFields(methodTypeField, organizationIdField, sourceDescriptionField, gasTypeField,newUnitsChargeField, newUnitsCapacityField, inventoryChangeField, existingUnitsRechargeField, disposedUnitsCapacityField, disposedUnitsRecoveredField,  fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

        Label simplifiedMaterialBalanceMethodLabel = new Label("Current refridgeration & air conditioning sources");
        simplifiedMaterialBalanceMethodLabel.setHeight(LABEL_HEIGHT);
        simplifiedMaterialBalanceMethodLabel.setWidth100();
        simplifiedMaterialBalanceMethodLabel.setAlign(Alignment.LEFT);
        simplifiedMaterialBalanceMethodLabel.setStyleName("labels");
        //refridgerationAirConditioningLayout.addMember(simplifiedMaterialBalanceMethodLabel);

        IButton newRefridgerationAirConditioningButton_2 = new IButton(ADD_NEW_SOURCE_TEXT);
        newRefridgerationAirConditioningButton_2.setWidth(ADD_BUTTON_WIDTH);
        newRefridgerationAirConditioningButton_2.setIcon(ADD_ICON_IMAGE);

        newRefridgerationAirConditioningButton_2.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                refridgerationAirConditioningForm_2.editNewRecord();
                refridgerationAirConditioningForm_2.setValues(getInitialValues());
                refridgerationAirConditioningFormWindow_2.show();
            }
        });

        HLayout gridButtonLayout_2 = new HLayout(10);
        gridButtonLayout_2.addMember(simplifiedMaterialBalanceMethodLabel);
        gridButtonLayout_2.addMember(newRefridgerationAirConditioningButton_2);
        refridgerationAirConditioningLayout_2.addMember(gridButtonLayout_2);
        refridgerationAirConditioningLayout_2.addMember(refridgerationAirConditioningDataGrid_2);

        /*
        SectionStackSection simplifiedMaterialBalanceSection = new SectionStackSection("simplifiedMaterialBalance");
        simplifiedMaterialBalanceSection.addItem(gridButtonLayout_2);
        simplifiedMaterialBalanceSection.addItem(refridgerationAirConditioningDataGrid_2);
        */
//-- Screening Method
        refridgerationAirConditioningDataGrid_3.setFields(methodTypeField, organizationIdField, sourceDescriptionField, gasTypeField, typeOfEquipmentField, sourceNewUnitsChargeField,operatingUnitsCapacityField, sourceDisposedUnitsCapacityField, timeInYearsUsedField, fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

        Label screeningMethodLabel = new Label("Current refridgeration & air conditioning sources");
        screeningMethodLabel.setHeight(LABEL_HEIGHT);
        screeningMethodLabel.setWidth100();
        screeningMethodLabel.setAlign(Alignment.LEFT);
        screeningMethodLabel.setStyleName("labels");

        //refridgerationAirConditioningLayout.addMember(screeningMethodLabel);

        IButton newRefridgerationAirConditioningButton_3 = new IButton(ADD_NEW_SOURCE_TEXT);
        newRefridgerationAirConditioningButton_3.setWidth(ADD_BUTTON_WIDTH);
        newRefridgerationAirConditioningButton_3.setIcon(ADD_ICON_IMAGE);

        newRefridgerationAirConditioningButton_3.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                refridgerationAirConditioningForm_3.editNewRecord();
                refridgerationAirConditioningForm_3.setValues(getInitialValues());
                refridgerationAirConditioningFormWindow_3.show();
            }
        });

        HLayout gridButtonLayout_3 = new HLayout(10);
        gridButtonLayout_3.addMember(screeningMethodLabel);
        gridButtonLayout_3.addMember(newRefridgerationAirConditioningButton_3);
        refridgerationAirConditioningLayout_3.addMember(gridButtonLayout_3);
        refridgerationAirConditioningLayout_3.addMember(refridgerationAirConditioningDataGrid_3);

        /*
        SectionStackSection screeningSourceSection = new SectionStackSection("screeningSource");
        screeningSourceSection.addItem(gridButtonLayout_3);
        screeningSourceSection.addItem(refridgerationAirConditioningDataGrid_3);

//--add Sections
        refridgerationAirConditioningSectionStack.addSection(materialBalanceSection);
        refridgerationAirConditioningSectionStack.addSection(simplifiedMaterialBalanceSection);
        refridgerationAirConditioningSectionStack.addSection(screeningSourceSection);
        refridgerationAirConditioningLayout.addMember(refridgerationAirConditioningSectionStack);
        */

//--Defining Refridgeration Air Conditioning Combustion tab
        final Tab refridgerationAirConditioningTab_1 = new Tab("Company-Wide Material Balance Method");
        refridgerationAirConditioningTab_1.setPane(refridgerationAirConditioningLayout_1);

        final Tab refridgerationAirConditioningTab_2 = new Tab("Company-Wide Simplified Material Balance Method");
        refridgerationAirConditioningTab_2.setPane(refridgerationAirConditioningLayout_2);

        final Tab refridgerationAirConditioningTab_3 = new Tab("Source Level Screening Method");
        refridgerationAirConditioningTab_3.setPane(refridgerationAirConditioningLayout_3);

//---Adding Refridgeration Air Conditioning Combustion tabs to refridgerationAirConditioningTabSet
        refridgerationAirConditioningTabSet.addTab(refridgerationAirConditioningTab_1);
        refridgerationAirConditioningTabSet.addTab(refridgerationAirConditioningTab_2);
        refridgerationAirConditioningTabSet.addTab(refridgerationAirConditioningTab_3);

        refridgerationAirConditioningLayout.addMember(refridgerationAirConditioningTabSet);

 }
private void initRefridgerationAirConditioningEditForm_1() {

    refridgerationAirConditioningForm_1.setCellPadding(5);
    refridgerationAirConditioningForm_1.setWidth("100%");

    //-- setValidators for the forms for common types.
    initializeValidators();

//-- Form_1 fields  -------------------------------------------------------------------

    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);
    gasTypeItem.setRequired(Boolean.TRUE);

    FloatItem inventoryChangeItem = new FloatItem();
    inventoryChangeItem.setName("inventoryChange");
    inventoryChangeItem.setValidators(floatValidator);
    inventoryChangeItem.setValidateOnExit(Boolean.TRUE);
    inventoryChangeItem.setValidateOnChange(Boolean.TRUE);
    inventoryChangeItem.setRequired(Boolean.TRUE);

    FloatItem transferredAmountItem = new FloatItem();
    transferredAmountItem.setName("transferredAmount");
    transferredAmountItem.setValidators(floatValidator);
    transferredAmountItem.setValidateOnExit(Boolean.TRUE);
    transferredAmountItem.setValidateOnChange(Boolean.TRUE);
    transferredAmountItem.setRequired(Boolean.TRUE);


    FloatItem capacityChangeItem = new FloatItem();
    capacityChangeItem.setName("capacityChange");
    capacityChangeItem.setValidators(floatValidator);
    capacityChangeItem.setValidateOnExit(Boolean.TRUE);
    capacityChangeItem.setValidateOnChange(Boolean.TRUE);
    capacityChangeItem.setRequired(Boolean.TRUE);

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setValue("Refridgeration Air Conditioning - Company-Wide Material Balance Method");
    methodTypeItem.setTitle("Method Type");

    refridgerationAirConditioningForm_1.setItems(gasTypeItem ,inventoryChangeItem, transferredAmountItem, capacityChangeItem, fuelUsedBeginDateItem, fuelUsedEndDateItem);

    final IButton refridgerationAirConditioningCancelButton = new IButton();
    final IButton refridgerationAirConditioningSaveButton = new IButton();

    refridgerationAirConditioningSaveButton.setTitle("SAVE");
    refridgerationAirConditioningSaveButton.setTooltip("Save this Combustion Source");
    refridgerationAirConditioningSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record refridgerationAirConditioningFormRecord = refridgerationAirConditioningForm_1.getValuesAsRecord();
        refridgerationAirConditioningDataGrid_1.updateData(refridgerationAirConditioningFormRecord);
        refridgerationAirConditioningFormWindow_1.hide();
      }
    });

    refridgerationAirConditioningCancelButton.setTitle("CANCEL");
    refridgerationAirConditioningCancelButton.setTooltip("Cancel");
    refridgerationAirConditioningCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        refridgerationAirConditioningFormWindow_1.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(refridgerationAirConditioningCancelButton);
    buttons.addMember(refridgerationAirConditioningSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(refridgerationAirConditioningForm_1);
    dialog.addMember(buttons);
    refridgerationAirConditioningFormWindow_1.setShowShadow(true);
    refridgerationAirConditioningFormWindow_1.setShowTitle(false);
    refridgerationAirConditioningFormWindow_1.setIsModal(true);
    refridgerationAirConditioningFormWindow_1.setPadding(20);
    refridgerationAirConditioningFormWindow_1.setWidth(500);
    refridgerationAirConditioningFormWindow_1.setHeight(350);
    refridgerationAirConditioningFormWindow_1.setShowMinimizeButton(false);
    refridgerationAirConditioningFormWindow_1.setShowCloseButton(true);
    refridgerationAirConditioningFormWindow_1.setShowModalMask(true);
    refridgerationAirConditioningFormWindow_1.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //refridgerationAirConditioningFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    refridgerationAirConditioningFormWindow_1.addItem(dialog);

 }
private void initRefridgerationAirConditioningEditForm_2() {


    refridgerationAirConditioningForm_2.setCellPadding(5);
    refridgerationAirConditioningForm_2.setWidth("100%");

//-- Form_2 fields  -------------------------------------------------------------------

    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);

    FloatItem newUnitsChargeItem = new FloatItem();
    newUnitsChargeItem.setName("newUnitsCharge");

    FloatItem newUnitsCapacityItem = new FloatItem();
    newUnitsCapacityItem.setName("newUnitsCapacity");

    FloatItem existingUnitsRechargeItem = new FloatItem();
    existingUnitsRechargeItem.setName("existingUnitsRecharge");

    FloatItem disposedUnitsCapacityItem = new FloatItem();
    disposedUnitsCapacityItem.setName("disposedUnitsCapacity");

    FloatItem disposedUnitsRecoveredItem = new FloatItem();
    disposedUnitsRecoveredItem.setName("disposedUnitsRecovered");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setValue("Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method");
    methodTypeItem.setTitle("Method Type");

    refridgerationAirConditioningForm_2.setItems(organizationId,methodTypeItem, gasTypeItem ,newUnitsChargeItem, newUnitsCapacityItem,existingUnitsRechargeItem, disposedUnitsCapacityItem,disposedUnitsRecoveredItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton refridgerationAirConditioningCancelButton = new IButton();
    final IButton refridgerationAirConditioningSaveButton = new IButton();

    refridgerationAirConditioningSaveButton.setTitle("SAVE");
    refridgerationAirConditioningSaveButton.setTooltip("Save this Mobile Combustion Source");
    refridgerationAirConditioningSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record refridgerationAirConditioningFormRecord = refridgerationAirConditioningForm_2.getValuesAsRecord();
        refridgerationAirConditioningDataGrid_2.updateData(refridgerationAirConditioningFormRecord);
        refridgerationAirConditioningFormWindow_2.hide();
      }
    });

    refridgerationAirConditioningCancelButton.setTitle("CANCEL");
    refridgerationAirConditioningCancelButton.setTooltip("Cancel");
    refridgerationAirConditioningCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        refridgerationAirConditioningFormWindow_2.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(refridgerationAirConditioningCancelButton);
    buttons.addMember(refridgerationAirConditioningSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(refridgerationAirConditioningForm_2);
    dialog.addMember(buttons);
    refridgerationAirConditioningFormWindow_2.setShowShadow(true);
    refridgerationAirConditioningFormWindow_2.setShowTitle(false);
    refridgerationAirConditioningFormWindow_2.setIsModal(true);
    refridgerationAirConditioningFormWindow_2.setPadding(20);
    refridgerationAirConditioningFormWindow_2.setWidth(500);
    refridgerationAirConditioningFormWindow_2.setHeight(350);
    refridgerationAirConditioningFormWindow_2.setShowMinimizeButton(false);
    refridgerationAirConditioningFormWindow_2.setShowCloseButton(true);
    refridgerationAirConditioningFormWindow_2.setShowModalMask(true);
    refridgerationAirConditioningFormWindow_2.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //refridgerationAirConditioningFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    refridgerationAirConditioningFormWindow_2.addItem(dialog);
 }
private void initRefridgerationAirConditioningEditForm_3() {


    refridgerationAirConditioningForm_3.setCellPadding(5);
    refridgerationAirConditioningForm_3.setWidth("100%");

//-- Form_3 fields  -------------------------------------------------------------------

    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setValue("Source Description");
    sourceDescriptionItem.setTitle("Source Description");

    final SelectItem typeOfEquipmentItem = new SelectItem();
    typeOfEquipmentItem.setName("typeOfEquipment");
    typeOfEquipmentItem.setTitle("Type Of Equipment");
    typeOfEquipmentItem.setOptionDataSource(equipmentCapacityRange_EPADS);

    FloatItem sourceNewUnitsChargeItem = new FloatItem();
    sourceNewUnitsChargeItem.setName("sourceNewUnitsCharge");

    FloatItem operatingUnitsCapacityItem = new FloatItem();
    operatingUnitsCapacityItem.setName("operatingUnitsCapacity");

    FloatItem sourceDisposedUnitsCapacityItem = new FloatItem();
    sourceDisposedUnitsCapacityItem.setName("sourceDisposedUnitsCapacity");

    FloatItem timeInYearsUsedItem = new FloatItem();
    timeInYearsUsedItem.setName("timeInYearsUsed");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setValue("Refridgeration Air Conditioning - Source Level Screening Method");
    methodTypeItem.setTitle("Method Type");

    refridgerationAirConditioningForm_3.setItems(organizationId,methodTypeItem, gasTypeItem ,sourceDescriptionItem, typeOfEquipmentItem, sourceNewUnitsChargeItem, operatingUnitsCapacityItem, sourceDisposedUnitsCapacityItem, timeInYearsUsedItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton refridgerationAirConditioningCancelButton = new IButton();
    final IButton refridgerationAirConditioningSaveButton = new IButton();

    refridgerationAirConditioningSaveButton.setTitle("SAVE");
    refridgerationAirConditioningSaveButton.setTooltip("Save this Mobile Combustion Source");
    refridgerationAirConditioningSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record refridgerationAirConditioningFormRecord = refridgerationAirConditioningForm_3.getValuesAsRecord();
        refridgerationAirConditioningDataGrid_3.updateData(refridgerationAirConditioningFormRecord);
        refridgerationAirConditioningFormWindow_3.hide();
      }
    });

    refridgerationAirConditioningCancelButton.setTitle("CANCEL");
    refridgerationAirConditioningCancelButton.setTooltip("Cancel");
    refridgerationAirConditioningCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        refridgerationAirConditioningFormWindow_3.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(refridgerationAirConditioningCancelButton);
    buttons.addMember(refridgerationAirConditioningSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(refridgerationAirConditioningForm_3);
    dialog.addMember(buttons);
    refridgerationAirConditioningFormWindow_3.setShowShadow(true);
    refridgerationAirConditioningFormWindow_3.setShowTitle(false);
    refridgerationAirConditioningFormWindow_3.setIsModal(true);
    refridgerationAirConditioningFormWindow_3.setPadding(20);
    refridgerationAirConditioningFormWindow_3.setWidth(500);
    refridgerationAirConditioningFormWindow_3.setHeight(350);
    refridgerationAirConditioningFormWindow_3.setShowMinimizeButton(false);
    refridgerationAirConditioningFormWindow_3.setShowCloseButton(true);
    refridgerationAirConditioningFormWindow_3.setShowModalMask(true);
    refridgerationAirConditioningFormWindow_3.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //refridgerationAirConditioningFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    refridgerationAirConditioningFormWindow_3.addItem(dialog);
 }

private void fireSuppressionTab_OLD() {

        VLayout fireSuppressionLayout_1 = new VLayout(15);
        VLayout fireSuppressionLayout_2 = new VLayout(15);
        VLayout fireSuppressionLayout_3 = new VLayout(15);

        fireSuppressionLayout.setWidth100();
        fireSuppressionLayout.setHeight100();
        /*
        Label fireSuppressionDataLabel = new Label("Current Fire Suppression sources");
        fireSuppressionDataLabel.setHeight(LABEL_HEIGHT);
        fireSuppressionLayout.addMember(fireSuppressionDataLabel);
        */
        final SectionStack fireSuppressionSectionStack = new SectionStack();
        fireSuppressionSectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        fireSuppressionSectionStack.setWidth100();
        fireSuppressionSectionStack.setHeight100();

//--ListGrid setup for Material balance method
        fireSuppressionDataGrid_1.setWidth("100%");
        //fireSuppressionDataGrid_1.setHeight(200);
        fireSuppressionDataGrid_1.setHeight("100%");
        fireSuppressionDataGrid_1.setShowRecordComponents(true);
        fireSuppressionDataGrid_1.setShowRecordComponentsByCell(true);
        //fireSuppressionDataGrid_1.setCanRemoveRecords(true);
        //fireSuppressionDataGrid_1.setShowAllRecords(true);
        fireSuppressionDataGrid_1.setAutoFetchData(Boolean.FALSE);
        fireSuppressionDataGrid_1.setDataSource(refridgerationAirConditioningInfoDS);

//--ListGrid setup for Simplified Material balance method
        fireSuppressionDataGrid_2.setWidth("100%");
        //fireSuppressionDataGrid_2.setHeight(200);
        fireSuppressionDataGrid_2.setHeight("100%");
        fireSuppressionDataGrid_2.setShowRecordComponents(true);
        fireSuppressionDataGrid_2.setShowRecordComponentsByCell(true);
        //fireSuppressionDataGrid_2.setCanRemoveRecords(true);
        //fireSuppressionDataGrid_2.setShowAllRecords(true);
        fireSuppressionDataGrid_2.setAutoFetchData(Boolean.FALSE);
        fireSuppressionDataGrid_2.setDataSource(refridgerationAirConditioningInfoDS);

//--ListGrid setup for Screening method
        fireSuppressionDataGrid_3.setWidth("100%");
        //fireSuppressionDataGrid_3.setHeight(200);
        fireSuppressionDataGrid_3.setHeight("100%");
        fireSuppressionDataGrid_3.setShowRecordComponents(true);
        fireSuppressionDataGrid_3.setShowRecordComponentsByCell(true);
        //fireSuppressionDataGrid_3.setCanRemoveRecords(true);
        //fireSuppressionDataGrid_3.setShowAllRecords(true);
        fireSuppressionDataGrid_3.setAutoFetchData(Boolean.FALSE);
        fireSuppressionDataGrid_3.setDataSource(refridgerationAirConditioningInfoDS);

//--Only fetch data that is related to THE organization
        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        //ListGridField idField = new ListGridField("Id", "Id");
        //idField.setType(ListGridFieldType.INTEGER);

        ListGridField gasTypeField = new ListGridField("gasType", "Gas Type");
        gasTypeField.setType(ListGridFieldType.TEXT);

//-- Material Balance fields
        FloatListGridField inventoryChangeField = new FloatListGridField("inventoryChange", "Inventory Change");
        //inventoryChangeField.setType(ListGridFieldType.FLOAT);

        FloatListGridField transferredAmountField = new FloatListGridField("transferredAmount", "Transferred Amount");
        //transferredAmountField.setType(ListGridFieldType.FLOAT);

        FloatListGridField capacityChangeField = new FloatListGridField("capacityChange", "Capacity Change");
        //capacityChangeField.setType(ListGridFieldType.FLOAT);

//--Simplified Material Balance fields
        FloatListGridField newUnitsChargeField = new FloatListGridField("newUnitsCharge", "New Units Charge");
        //newUnitsChargeField.setType(ListGridFieldType.FLOAT);

        FloatListGridField newUnitsCapacityField = new FloatListGridField("newUnitsCapacity", "New Units Capacity");
        //newUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        FloatListGridField existingUnitsRechargeField = new FloatListGridField("existingUnitsRecharge", "Existing Units Recharge");
        //existingUnitsRechargeField.setType(ListGridFieldType.FLOAT);

        FloatListGridField disposedUnitsCapacityField = new FloatListGridField("disposedUnitsCapacity", "Disposed Units Capacity");
        //disposedUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        FloatListGridField disposedUnitsRecoveredField = new FloatListGridField("disposedUnitsRecovered", "Disposed Units Recovered");
        //disposedUnitsRecoveredField.setType(ListGridFieldType.FLOAT);

//--Screening Method fields
        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField typeOfEquipmentField = new ListGridField("typeOfEquipment", "Type  Of Equipment");
        typeOfEquipmentField.setType(ListGridFieldType.TEXT);

        FloatListGridField sourceNewUnitsChargeField = new FloatListGridField("sourceNewUnitsCharge", "Source New Units Charge");
        //sourceNewUnitsChargeField.setType(ListGridFieldType.FLOAT);

        FloatListGridField operatingUnitsCapacityField = new FloatListGridField("operatingUnitsCapacity", "Operating Units Capacity");
        //operatingUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField methodTypeField = new ListGridField("methodType", "Method Type");
        methodTypeField.setType(ListGridFieldType.TEXT);
        methodTypeField.setHidden(true);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

//-- Material Balance Method
        fireSuppressionDataGrid_1.setFields(methodTypeField, organizationIdField, gasTypeField,inventoryChangeField, transferredAmountField, capacityChangeField, fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

        Label materialBalanceMethodLabel = new Label("Current Fire Suppression sources");
        materialBalanceMethodLabel.setHeight(LABEL_HEIGHT);
        materialBalanceMethodLabel.setWidth100();
        materialBalanceMethodLabel.setAlign(Alignment.LEFT);
        materialBalanceMethodLabel.setStyleName("labels");

        //fireSuppressionLayout.addMember(materialBalanceMethodLabel);

        IButton newFireSuppressionButton_1 = new IButton(ADD_NEW_SOURCE_TEXT);
        newFireSuppressionButton_1.setWidth(ADD_BUTTON_WIDTH);
        newFireSuppressionButton_1.setIcon(ADD_ICON_IMAGE);

        newFireSuppressionButton_1.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                fireSuppressionForm_1.editNewRecord();
                fireSuppressionForm_1.setValues(getInitialValues());
                fireSuppressionFormWindow_1.show();
            }
        });

        HLayout gridButtonLayout_1 = new HLayout(10);
        gridButtonLayout_1.addMember(materialBalanceMethodLabel);
        gridButtonLayout_1.addMember(newFireSuppressionButton_1);
        fireSuppressionLayout_1.addMember(gridButtonLayout_1);
        fireSuppressionLayout_1.addMember(fireSuppressionDataGrid_1);
        /*
        SectionStackSection materialBalanceSection = new SectionStackSection("materialBalance");
        materialBalanceSection.addItem(gridButtonLayout_1);
        materialBalanceSection.addItem(fireSuppressionDataGrid_1);
        */
//-- Simplified Material Balance Method
        fireSuppressionDataGrid_2.setFields(methodTypeField, organizationIdField, gasTypeField,newUnitsChargeField, newUnitsCapacityField, inventoryChangeField, existingUnitsRechargeField, disposedUnitsCapacityField, disposedUnitsRecoveredField,  fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

        Label simplifiedMaterialBalanceMethodLabel = new Label("Current Fire Suppression sources");
        simplifiedMaterialBalanceMethodLabel.setHeight(LABEL_HEIGHT);
        simplifiedMaterialBalanceMethodLabel.setWidth100();
        simplifiedMaterialBalanceMethodLabel.setAlign(Alignment.LEFT);
        simplifiedMaterialBalanceMethodLabel.setStyleName("labels");
        //fireSuppressionLayout.addMember(simplifiedMaterialBalanceMethodLabel);

        IButton newFireSuppressionButton_2 = new IButton(ADD_NEW_SOURCE_TEXT);
        newFireSuppressionButton_2.setWidth(ADD_BUTTON_WIDTH);
        newFireSuppressionButton_2.setIcon(ADD_ICON_IMAGE);

        newFireSuppressionButton_2.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                fireSuppressionForm_2.editNewRecord();
                fireSuppressionForm_2.setValues(getInitialValues());
                fireSuppressionFormWindow_2.show();
            }
        });

        HLayout gridButtonLayout_2 = new HLayout(10);
        gridButtonLayout_2.addMember(simplifiedMaterialBalanceMethodLabel);
        gridButtonLayout_2.addMember(newFireSuppressionButton_2);
        fireSuppressionLayout_2.addMember(gridButtonLayout_2);
        fireSuppressionLayout_2.addMember(fireSuppressionDataGrid_2);
        /*
        SectionStackSection simplifiedMaterialBalanceSection = new SectionStackSection("simplifiedMaterialBalance");
        simplifiedMaterialBalanceSection.addItem(gridButtonLayout_2);
        simplifiedMaterialBalanceSection.addItem(fireSuppressionDataGrid_2);
         */
//-- Screening Method
        fireSuppressionDataGrid_3.setFields(methodTypeField, organizationIdField, gasTypeField,sourceDescriptionField, typeOfEquipmentField, sourceNewUnitsChargeField,operatingUnitsCapacityField, fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

        Label screeningMethodLabel = new Label("Current Fire Suppression sources");
        screeningMethodLabel.setHeight(LABEL_HEIGHT);
        screeningMethodLabel.setWidth100();
        screeningMethodLabel.setAlign(Alignment.LEFT);
        screeningMethodLabel.setStyleName("labels");

        //fireSuppressionLayout.addMember(screeningMethodLabel);

        IButton newFireSuppressionButton_3 = new IButton(ADD_NEW_SOURCE_TEXT);
        newFireSuppressionButton_3.setWidth(ADD_BUTTON_WIDTH);
        newFireSuppressionButton_3.setIcon(ADD_ICON_IMAGE);

        newFireSuppressionButton_3.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                fireSuppressionForm_3.editNewRecord();
                fireSuppressionForm_3.setValues(getInitialValues());
                fireSuppressionFormWindow_3.show();
            }
        });

        HLayout gridButtonLayout_3 = new HLayout(10);
        gridButtonLayout_3.addMember(screeningMethodLabel);
        gridButtonLayout_3.addMember(newFireSuppressionButton_3);
        fireSuppressionLayout_3.addMember(gridButtonLayout_3);
        fireSuppressionLayout_3.addMember(fireSuppressionDataGrid_3);

        /*
        SectionStackSection screeningSourceSection = new SectionStackSection("screeningSource");
        screeningSourceSection.addItem(gridButtonLayout_3);
        screeningSourceSection.addItem(fireSuppressionDataGrid_3);

//--add Sections
        fireSuppressionSectionStack.addSection(materialBalanceSection);
        fireSuppressionSectionStack.addSection(simplifiedMaterialBalanceSection);
        fireSuppressionSectionStack.addSection(screeningSourceSection);

        fireSuppressionLayout.addMember(fireSuppressionSectionStack);
        */

        final Tab fireSuppressionTab_1 = new Tab("Company-Wide Material Balance Method");
        fireSuppressionTab_1.setPane(fireSuppressionLayout_1);

        final Tab fireSuppressionTab_2 = new Tab("Company-Wide Simplified Material Balance Method");
        fireSuppressionTab_2.setPane(fireSuppressionLayout_2);

        final Tab fireSuppressionTab_3 = new Tab("Source Level Screening Method");
        fireSuppressionTab_3.setPane(fireSuppressionLayout_3);

//---Adding Fire Suppression tab to tabSet
        fireSuppressionTabSet.addTab(fireSuppressionTab_1);
        fireSuppressionTabSet.addTab(fireSuppressionTab_2);
        fireSuppressionTabSet.addTab(fireSuppressionTab_3);

        fireSuppressionLayout.addMember(fireSuppressionTabSet);
//--Defining Fire Suppression tab
        //final Tab fireSuppressionTab = new Tab("Fire Suppression");
        //fireSuppressionTab.setPane(fireSuppressionLayout);

//---Adding Fire Suppression tab to tabSet
        //tabSet.addTab(fireSuppressionTab);
 }
private void initFireSuppressionEditForm_1() {

    fireSuppressionForm_1.setCellPadding(5);
    fireSuppressionForm_1.setWidth("100%");

//-- Form_1 fields  -------------------------------------------------------------------

    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);

    FloatItem inventoryChangeItem = new FloatItem();
    inventoryChangeItem.setName("inventoryChange");

    FloatItem transferredAmountItem = new FloatItem();
    transferredAmountItem.setName("transferredAmount");

    FloatItem capacityChangeItem = new FloatItem();
    capacityChangeItem.setName("capacityChange");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setValue("Fire Suppression - Company-Wide Material Balance Method");
    methodTypeItem.setTitle("Method Type");

    fireSuppressionForm_1.setItems(organizationId,methodTypeItem, gasTypeItem ,inventoryChangeItem, transferredAmountItem, capacityChangeItem, fuelUsedBeginDateItem, fuelUsedEndDateItem);

    final IButton fireSuppressionCancelButton = new IButton();
    final IButton fireSuppressionSaveButton = new IButton();

    fireSuppressionSaveButton.setTitle("SAVE");
    fireSuppressionSaveButton.setTooltip("Save this Fire Suppression Source");
    fireSuppressionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record fireSuppressionFormRecord = fireSuppressionForm_1.getValuesAsRecord();
        fireSuppressionDataGrid_1.updateData(fireSuppressionFormRecord);
        fireSuppressionFormWindow_1.hide();
      }
    });

    fireSuppressionCancelButton.setTitle("CANCEL");
    fireSuppressionCancelButton.setTooltip("Cancel");
    fireSuppressionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        fireSuppressionFormWindow_1.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(fireSuppressionCancelButton);
    buttons.addMember(fireSuppressionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(fireSuppressionForm_1);
    dialog.addMember(buttons);
    fireSuppressionFormWindow_1.setShowShadow(true);
    fireSuppressionFormWindow_1.setShowTitle(false);
    fireSuppressionFormWindow_1.setIsModal(true);
    fireSuppressionFormWindow_1.setPadding(20);
    fireSuppressionFormWindow_1.setWidth(500);
    fireSuppressionFormWindow_1.setHeight(350);
    fireSuppressionFormWindow_1.setShowMinimizeButton(false);
    fireSuppressionFormWindow_1.setShowCloseButton(true);
    fireSuppressionFormWindow_1.setShowModalMask(true);
    fireSuppressionFormWindow_1.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //fireSuppressionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    fireSuppressionFormWindow_1.addItem(dialog);
 }
private void initFireSuppressionEditForm_2() {


    fireSuppressionForm_2.setCellPadding(5);
    fireSuppressionForm_2.setWidth("100%");

//-- Form_2 fields  -------------------------------------------------------------------

    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);

    FloatItem newUnitsChargeItem = new FloatItem();
    newUnitsChargeItem.setName("newUnitsCharge");

    FloatItem newUnitsCapacityItem = new FloatItem();
    newUnitsCapacityItem.setName("newUnitsCapacity");

    FloatItem existingUnitsRechargeItem = new FloatItem();
    existingUnitsRechargeItem.setName("existingUnitsRecharge");

    FloatItem disposedUnitsCapacityItem = new FloatItem();
    disposedUnitsCapacityItem.setName("disposedUnitsCapacity");

    FloatItem disposedUnitsRecoveredItem = new FloatItem();
    disposedUnitsRecoveredItem.setName("disposedUnitsRecovered");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setValue("Fire Suppression - Company-Wide Simplified Material Balance Method");
    methodTypeItem.setTitle("Method Type");

    fireSuppressionForm_2.setItems(organizationId,methodTypeItem, gasTypeItem ,newUnitsChargeItem, newUnitsCapacityItem,existingUnitsRechargeItem, disposedUnitsCapacityItem,disposedUnitsRecoveredItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton fireSuppressionCancelButton = new IButton();
    final IButton fireSuppressionSaveButton = new IButton();

    fireSuppressionSaveButton.setTitle("SAVE");
    fireSuppressionSaveButton.setTooltip("Save this Fire Suppression Source");
    fireSuppressionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record fireSuppressionFormRecord = fireSuppressionForm_2.getValuesAsRecord();
        fireSuppressionDataGrid_2.updateData(fireSuppressionFormRecord);
        fireSuppressionFormWindow_2.hide();
      }
    });

    fireSuppressionCancelButton.setTitle("CANCEL");
    fireSuppressionCancelButton.setTooltip("Cancel");
    fireSuppressionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        fireSuppressionFormWindow_2.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(fireSuppressionCancelButton);
    buttons.addMember(fireSuppressionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(fireSuppressionForm_2);
    dialog.addMember(buttons);
    fireSuppressionFormWindow_2.setShowShadow(true);
    fireSuppressionFormWindow_2.setShowTitle(false);
    fireSuppressionFormWindow_2.setIsModal(true);
    fireSuppressionFormWindow_2.setPadding(20);
    fireSuppressionFormWindow_2.setWidth(500);
    fireSuppressionFormWindow_2.setHeight(350);
    fireSuppressionFormWindow_2.setShowMinimizeButton(false);
    fireSuppressionFormWindow_2.setShowCloseButton(true);
    fireSuppressionFormWindow_2.setShowModalMask(true);
    fireSuppressionFormWindow_2.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //fireSuppressionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    fireSuppressionFormWindow_2.addItem(dialog);
 }
private void initFireSuppressionEditForm_3() {


    fireSuppressionForm_3.setCellPadding(5);
    fireSuppressionForm_3.setWidth("100%");

//-- Form_3 fields  -------------------------------------------------------------------

    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setValue("Source Description");
    sourceDescriptionItem.setTitle("Source Description");

    final SelectItem typeOfEquipmentItem = new SelectItem();
    typeOfEquipmentItem.setName("typeOfEquipment");
    typeOfEquipmentItem.setTitle("Type Of Equipment");
    typeOfEquipmentItem.setValueMap("Fixed","Portable");

    FloatItem sourceNewUnitsChargeItem = new FloatItem();
    sourceNewUnitsChargeItem.setName("sourceNewUnitsCharge");

    FloatItem operatingUnitsCapacityItem = new FloatItem();
    operatingUnitsCapacityItem.setName("operatingUnitsCapacity");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setValue("Fire Suppression - Source Level Screening Method");
    methodTypeItem.setTitle("Method Type");

    fireSuppressionForm_3.setItems(organizationId,methodTypeItem, gasTypeItem ,sourceDescriptionItem, typeOfEquipmentItem, sourceNewUnitsChargeItem, operatingUnitsCapacityItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton fireSuppressionCancelButton = new IButton();
    final IButton fireSuppressionSaveButton = new IButton();

    fireSuppressionSaveButton.setTitle("SAVE");
    fireSuppressionSaveButton.setTooltip("Save this Fire Suppression Source");
    fireSuppressionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record fireSuppressionFormRecord = fireSuppressionForm_3.getValuesAsRecord();
        fireSuppressionDataGrid_3.updateData(fireSuppressionFormRecord);
        fireSuppressionFormWindow_3.hide();
      }
    });

    fireSuppressionCancelButton.setTitle("CANCEL");
    fireSuppressionCancelButton.setTooltip("Cancel");
    fireSuppressionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        fireSuppressionFormWindow_3.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(fireSuppressionCancelButton);
    buttons.addMember(fireSuppressionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(fireSuppressionForm_3);
    dialog.addMember(buttons);
    fireSuppressionFormWindow_3.setShowShadow(true);
    fireSuppressionFormWindow_3.setShowTitle(false);
    fireSuppressionFormWindow_3.setIsModal(true);
    fireSuppressionFormWindow_3.setPadding(20);
    fireSuppressionFormWindow_3.setWidth(500);
    fireSuppressionFormWindow_3.setHeight(350);
    fireSuppressionFormWindow_3.setShowMinimizeButton(false);
    fireSuppressionFormWindow_3.setShowCloseButton(true);
    fireSuppressionFormWindow_3.setShowModalMask(true);
    fireSuppressionFormWindow_3.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //fireSuppressionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    fireSuppressionFormWindow_3.addItem(dialog);
 }

private void purchasedElectricityTab_OLD() {

        purchasedElectricityLayout.setWidth100();
        purchasedElectricityLayout.setHeight100();

        VLayout purchasedElectricityDetailsLayout = new VLayout(15);
        purchasedElectricityDetailsLayout.setWidth100();
        purchasedElectricityDetailsLayout.setHeight100();

        Label purchasedElectricityDataLabel = new Label("Current purchased electricity sources");
        purchasedElectricityDataLabel.setHeight(LABEL_HEIGHT);
        purchasedElectricityDataLabel.setWidth100();
        purchasedElectricityDataLabel.setAlign(Alignment.LEFT);
        purchasedElectricityDataLabel.setStyleName("labels");

        //purchasedElectricityDetailsLayout.addMember(purchasedElectricityDataLabel);

//--ListGrid setup
        purchasedElectricityDataGrid.setWidth100();
        //purchasedElectricityDataGrid.setHeight(200);
        purchasedElectricityDataGrid.setHeight100();
        purchasedElectricityDataGrid.setShowRecordComponents(true);
        purchasedElectricityDataGrid.setShowRecordComponentsByCell(true);
        //purchasedElectricityDataGrid.setCanRemoveRecords(true);
        //purchasedElectricityDataGrid.setShowAllRecords(true);
        purchasedElectricityDataGrid.setAutoFetchData(Boolean.FALSE);
        purchasedElectricityDataGrid.setDataSource(purchasedElectricityInfoDS);

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField eGRIDSubregionField = new ListGridField("eGRIDSubregion", "eGRID Subregion");
        eGRIDSubregionField.setType(ListGridFieldType.TEXT);

        FloatListGridField purchasedElectricityField = new FloatListGridField("purchasedElectricity", "Purchased Electricity");
        //purchasedElectricityField.setType(ListGridFieldType.FLOAT);

        ListGridField purchasedElectricityUnitField = new ListGridField("purchasedElectricityUnit", "Purchased Electricity Unit");
        purchasedElectricityUnitField.setType(ListGridFieldType.TEXT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        purchasedElectricityDataGrid.setFields(organizationIdField, sourceDescriptionField, eGRIDSubregionField, purchasedElectricityField, purchasedElectricityUnitField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //purchasedElectricityDetailsLayout.addMember(purchasedElectricityDataGrid);

        IButton newPurchasedElectricityButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newPurchasedElectricityButton.setWidth(ADD_BUTTON_WIDTH);
        newPurchasedElectricityButton.setIcon(ADD_ICON_IMAGE);

        newPurchasedElectricityButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                purchasedElectricityForm.editNewRecord();
                purchasedElectricityForm.setValues(getInitialValues());
                purchasedElectricityFormWindow.show();

            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(purchasedElectricityDataLabel);
        gridButtonLayout.addMember(newPurchasedElectricityButton);
        purchasedElectricityDetailsLayout.addMember(gridButtonLayout);
        purchasedElectricityDetailsLayout.addMember(purchasedElectricityDataGrid);

//--Defining Purchased Electricity tab
        final Tab purchasedElectricityTab = new Tab("Purchased Electricity");
        purchasedElectricityTab.setPane(purchasedElectricityDetailsLayout);

//---Adding Purchased Electricity tab to tabSet
        purchasedElectricityTabSet.addTab(purchasedElectricityTab);
        purchasedElectricityLayout.addMember(purchasedElectricityTabSet);
}
private void initPurchasedElectricityEditForm_OLD() {

    purchasedElectricityForm.setCellPadding(5);
    purchasedElectricityForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem eGRIDSubregionItem = new SelectItem();
    eGRIDSubregionItem.setName("eGRIDSubregion");
    eGRIDSubregionItem.setTitle("eGRID Subregion");
    eGRIDSubregionItem.setOptionDataSource(theEF_PurchasedElectricity_EPADS);

    FloatItem purchasedElectricityItem = new FloatItem();
    purchasedElectricityItem.setName("purchasedElectricity");

    TextItem purchasedElectricityUnitItem = new TextItem("purchasedElectricityUnit");
    purchasedElectricityUnitItem.setTitle("Purchased Electricity Unit");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    purchasedElectricityForm.setItems(organizationId, sourceDescriptionItem ,eGRIDSubregionItem, purchasedElectricityItem, purchasedElectricityUnitItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton purchasedElectricityCancelButton = new IButton();
    final IButton purchasedElectricitySaveButton = new IButton();

    purchasedElectricitySaveButton.setTitle("SAVE");
    purchasedElectricitySaveButton.setTooltip("Save this Purchased Electricity Source");
    purchasedElectricitySaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record purchasedElectricityFormRecord = purchasedElectricityForm.getValuesAsRecord();
        purchasedElectricityDataGrid.updateData(purchasedElectricityFormRecord);
        purchasedElectricityFormWindow.hide();
      }
    });

    purchasedElectricityCancelButton.setTitle("CANCEL");
    purchasedElectricityCancelButton.setTooltip("Cancel");
    purchasedElectricityCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        purchasedElectricityFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(purchasedElectricityCancelButton);
    buttons.addMember(purchasedElectricitySaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(purchasedElectricityForm);
    dialog.addMember(buttons);
    purchasedElectricityFormWindow.setShowShadow(true);
    purchasedElectricityFormWindow.setShowTitle(false);
    purchasedElectricityFormWindow.setIsModal(true);
    purchasedElectricityFormWindow.setPadding(20);
    purchasedElectricityFormWindow.setWidth(500);
    purchasedElectricityFormWindow.setHeight(350);
    purchasedElectricityFormWindow.setShowMinimizeButton(false);
    purchasedElectricityFormWindow.setShowCloseButton(true);
    purchasedElectricityFormWindow.setShowModalMask(true);
    purchasedElectricityFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //purchasedElectricityFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    purchasedElectricityFormWindow.addItem(dialog);
 }

private void purchasedSteamTab_OLD() {

        purchasedSteamLayout.setWidth100();
        purchasedSteamLayout.setHeight100();

        VLayout purchasedSteamDetailsLayout = new VLayout(15);
        purchasedSteamDetailsLayout.setWidth100();
        purchasedSteamDetailsLayout.setHeight100();

        Label purchasedSteamDataLabel = new Label("Current purchased Steam Sources");
        purchasedSteamDataLabel.setHeight(LABEL_HEIGHT);
        purchasedSteamDataLabel.setWidth100();
        purchasedSteamDataLabel.setAlign(Alignment.LEFT);
        purchasedSteamDataLabel.setStyleName("labels");

        //purchasedSteamDetailsLayout.addMember(purchasedSteamDataLabel);

//--ListGrid setup
        purchasedSteamDataGrid.setWidth100();
        //purchasedSteamDataGrid.setHeight(200);
        purchasedSteamDataGrid.setHeight100();
        purchasedSteamDataGrid.setShowRecordComponents(true);
        purchasedSteamDataGrid.setShowRecordComponentsByCell(true);
        //purchasedSteamDataGrid.setCanRemoveRecords(true);
        //purchasedSteamDataGrid.setShowAllRecords(true);
        purchasedSteamDataGrid.setAutoFetchData(Boolean.FALSE);
        purchasedSteamDataGrid.setDataSource(purchasedSteamInfoDS);

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);

        FloatListGridField boilerEfficiencyPercentField = new FloatListGridField("boilerEfficiencyPercent", "boilerEfficiency Percent");
        //boilerEfficiencyPercentField.setType(ListGridFieldType.FLOAT);
        boilerEfficiencyPercentField.setDefaultValue(80);
        //boilerEfficiencyPercentField.setEdit(Boolean.FALSE);
        /*
        boilerEfficiencyPercentField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        FloatListGridField purchasedSteamField = new FloatListGridField("purchasedSteam", "Purchased Steam");
        /*
        purchasedSteamField.setType(ListGridFieldType.FLOAT);
        purchasedSteamField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        ListGridField purchasedSteamUnitField = new ListGridField("purchasedSteamUnit", "Purchased Steam Unit");
        purchasedSteamUnitField.setType(ListGridFieldType.TEXT);
        purchasedSteamUnitField.setDefaultValue("mmBtu");

        FloatDetailViewerField supplierCO2MultiplierField = new FloatDetailViewerField("supplierCO2Multiplier", "Supplier CO2 Emission Factor");
        /*
        //supplierCO2MultiplierField.setType(ListGridFieldType.FLOAT);
        supplierCO2MultiplierField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        DetailViewerField supplierCO2MultiplierUnitField = new DetailViewerField("supplierCO2MultiplierUnit", "Supplier CO2 Emission Factor Unit");
        //supplierCO2MultiplierUnitField.setType(ListGridFieldType.TEXT);
        supplierCO2MultiplierUnitField.setValue("lb/mmBtu");

        FloatDetailViewerField supplierCH4MultiplierField = new FloatDetailViewerField("supplierCH4Multiplier", "Supplier CH4 Emission Factor");
        /*
        //supplierCH4MultiplierField.setType(ListGridFieldType.FLOAT);
        supplierCH4MultiplierField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        DetailViewerField supplierCH4MultiplierUnitField = new DetailViewerField("supplierCH4MultiplierUnit", "Supplier CH4 Emission Factor Unit");
        //supplierCH4MultiplierUnitField.setType(ListGridFieldType.TEXT);
        supplierCH4MultiplierUnitField.setValue("lb/mmBtu");

        FloatDetailViewerField supplierN2OMultiplierField = new FloatDetailViewerField("supplierN2OMultiplier", "Supplier N2O Emission Factor");
        /*
        //supplierN2OMultiplierField.setType(ListGridFieldType.FLOAT);
        supplierN2OMultiplierField.setDetailFormatter(new DetailFormatter() {
            public String format(Object value, Record record, DetailViewerField field) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        DetailViewerField supplierN2OMultiplierUnitField = new DetailViewerField("supplierN2OMultiplierUnit", "Supplier N2O Emission Factor Unit");
        //supplierN2OMultiplierUnitField.setType(ListGridFieldType.TEXT);
        supplierN2OMultiplierUnitField.setValue("lb/mmBtu");

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);
        /*
        purchasedSteamDataGrid.setFields(organizationIdField, sourceDescriptionField, fuelTypeField,boilerEfficiencyPercentField,
                                         purchasedSteamField,purchasedSteamUnitField,supplierCO2MultiplierField,
                                         supplierCO2MultiplierUnitField,supplierCH4MultiplierField,supplierCH4MultiplierUnitField,
                                         supplierN2OMultiplierField,supplierN2OMultiplierUnitField, fuelUsedBeginDateField,
                                         fuelUsedEndDateField ,editButtonField, removeButtonField);
        */

        purchasedSteamDataGrid.setFields(organizationIdField, sourceDescriptionField, fuelTypeField, boilerEfficiencyPercentField,
                                         purchasedSteamField,purchasedSteamUnitField, fuelUsedBeginDateField,
                                         fuelUsedEndDateField ,editButtonField, removeButtonField);

        //purchasedSteamDetailsLayout.addMember(purchasedSteamDataGrid);

        List<DetailViewerField> items = new ArrayList<DetailViewerField>();
        items.add(supplierCO2MultiplierField);
        items.add(supplierCO2MultiplierUnitField);
        items.add(supplierCH4MultiplierField);
        items.add(supplierCH4MultiplierUnitField);

        items.add(supplierN2OMultiplierField);
        items.add(supplierN2OMultiplierUnitField);

        final DetailViewerField[] fitems = new DetailViewerField[items.size()];
        items.toArray(fitems);

        purchasedSteamDataGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                //emissionsSummaryDetailViewer.reset();
                //--remove existing child from middleMiddleHLayout
                displayDetailInfo(purchasedSteamDataGrid, fitems);

            }
        });

        IButton newPurchasedSteamButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newPurchasedSteamButton.setWidth(ADD_BUTTON_WIDTH);
        newPurchasedSteamButton.setIcon(ADD_ICON_IMAGE);

        newPurchasedSteamButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                purchasedSteamForm.editNewRecord();
                purchasedSteamForm.setValues(getInitialValues());
                purchasedSteamFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(purchasedSteamDataLabel);
        gridButtonLayout.addMember(newPurchasedSteamButton);
        purchasedSteamDetailsLayout.addMember(gridButtonLayout);
        purchasedSteamDetailsLayout.addMember(purchasedSteamDataGrid);

//--Defining Purchased Steam tab
        final Tab purchasedSteamTab = new Tab("Purchased Steam");
        purchasedSteamTab.setPane(purchasedSteamDetailsLayout);

//---Adding Purchased Steam tab to tabSet
        purchasedSteamTabSet.addTab(purchasedSteamTab);
        purchasedSteamLayout.addMember(purchasedSteamTabSet);
}
private void initPurchasedSteamEditForm_OLD() {

    purchasedSteamForm.setCellPadding(5);
    purchasedSteamForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem fuelTypeItem = new SelectItem();
    fuelTypeItem.setName("fuelType");
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(theEF_PurchasedSteam_EPADS);

    FloatItem boilerEfficiencyPercentItem = new FloatItem();
    boilerEfficiencyPercentItem.setName("boilerEfficiencyPercent");

    FloatItem purchasedSteamItem = new FloatItem();
    purchasedSteamItem.setName("purchasedSteam");

    TextItem purchasedSteamUnitItem = new TextItem("purchasedSteamUnit");
    purchasedSteamUnitItem.setTitle("Purchased Steam Unit");

    FloatItem supplierCO2MultiplierItem = new FloatItem();
    supplierCO2MultiplierItem.setName("supplierCO2Multiplier");

    TextItem supplierCO2MultiplierUnitItem = new TextItem("supplierCO2MultiplierUnit");
    supplierCO2MultiplierUnitItem.setTitle("supplierCO2MultiplierUnit");

    FloatItem supplierCH4MultiplierItem = new FloatItem();
    supplierCH4MultiplierItem.setName("supplierCH4Multiplier");

    TextItem supplierCH4MultiplierUnitItem = new TextItem("supplierCH4MultiplierUnit");
    supplierCH4MultiplierUnitItem.setTitle("supplierCH4MultiplierUnit");

    FloatItem supplierN2OMultiplierItem = new FloatItem();
    supplierN2OMultiplierItem.setName("supplierN2OMultiplier");

    TextItem supplierN2OMultiplierUnitItem = new TextItem("supplierN2OMultiplierUnit");
    supplierN2OMultiplierUnitItem.setTitle("supplierN2OMultiplierUnit");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    purchasedSteamForm.setItems(organizationId, sourceDescriptionItem ,
                                fuelTypeItem,boilerEfficiencyPercentItem,purchasedSteamItem,purchasedSteamUnitItem,
                                supplierCO2MultiplierItem,supplierCO2MultiplierUnitItem,supplierCH4MultiplierItem,
                                supplierCH4MultiplierUnitItem,supplierN2OMultiplierItem,supplierN2OMultiplierUnitItem,
                                fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton purchasedSteamCancelButton = new IButton();
    final IButton purchasedSteamSaveButton = new IButton();

    purchasedSteamSaveButton.setTitle("SAVE");
    purchasedSteamSaveButton.setTooltip("Save this Purchased Steam Source");
    purchasedSteamSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record purchasedSteamFormRecord = purchasedSteamForm.getValuesAsRecord();
        purchasedSteamDataGrid.updateData(purchasedSteamFormRecord);
        purchasedSteamFormWindow.hide();
      }
    });

    purchasedSteamCancelButton.setTitle("CANCEL");
    purchasedSteamCancelButton.setTooltip("Cancel");
    purchasedSteamCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        purchasedSteamFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(purchasedSteamCancelButton);
    buttons.addMember(purchasedSteamSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(purchasedSteamForm);
    dialog.addMember(buttons);
    purchasedSteamFormWindow.setShowShadow(true);
    purchasedSteamFormWindow.setShowTitle(false);
    purchasedSteamFormWindow.setIsModal(true);
    purchasedSteamFormWindow.setPadding(20);
    purchasedSteamFormWindow.setWidth(500);
    purchasedSteamFormWindow.setHeight(350);
    purchasedSteamFormWindow.setShowMinimizeButton(false);
    purchasedSteamFormWindow.setShowCloseButton(true);
    purchasedSteamFormWindow.setShowModalMask(true);
    purchasedSteamFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //purchasedSteamFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    purchasedSteamFormWindow.addItem(dialog);
 }

public static ListGridField[] getMobileCombustionListGridFields() {

    //-Define all the Mobile Combustion List Grid Fields
        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);
        vehicleTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);
                
        FloatListGridField fuelQuantityField = new FloatListGridField("fuelQuantity", "Fuel Quantity");
        //fuelQuantityField.setType(ListGridFieldType.FLOAT);
        fuelQuantityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

/*
        fuelQuantityField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
*/
        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        fuelUnitField.setType(ListGridFieldType.TEXT);
        fuelUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(vehicleTypeField);
        listGridFieldList.add(fuelTypeField);
        listGridFieldList.add(fuelQuantityField);
        listGridFieldList.add(fuelUnitField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        //return mobileCombustionListGridFields;

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;

}
public static ListGridField[] getStationaryCombustionListGridFields(){

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);
                

        FloatListGridField fuelQuantityField = new FloatListGridField("fuelQuantity", "Fuel Quantity");
        //fuelQuantityField.setType(ListGridFieldType.FLOAT);
        fuelQuantityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        /*
        fuelQuantityField.setCellFormatter(new CellFormatter() {
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (value == null) return null;
                try {
                    NumberFormat nf = NumberFormat.getFormat("#,##0.00");
                    return nf.format(((Number) value).doubleValue());
                } catch (Exception e) {
                    return value.toString();
                }
            }
        });
        */

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        fuelUnitField.setType(ListGridFieldType.TEXT);
        fuelUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);
        //fuelUsedBeginDateField.setAttribute("dateFormatter", displayDateFormatter);

        //fuelUsedBeginDateField.setAlign(Alignment.LEFT);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);
        //fuelUsedEndDateField.setAlign(Alignment.LEFT);
        //fuelUsedEndDateField.setAttribute("dateFormatter", displayDateFormatter);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(organizationIdField);
        listGridFieldList.add(fuelTypeField);
        listGridFieldList.add(fuelQuantityField);
        listGridFieldList.add(fuelUnitField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getRefridgerationAirConditioningListGridFields_1() {

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField gasTypeField = new ListGridField("gasType", "Gas Type");
        //gasTypeField.setType(ListGridFieldType.TEXT);
        gasTypeField.setWidth(GAS_TYPE_FIELD_WIDTH);

        FloatListGridField inventoryChangeField = new FloatListGridField("inventoryChange", "Inventory Change");
        inventoryChangeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField transferredAmountField = new FloatListGridField("transferredAmount", "Transferred Amount");
        transferredAmountField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField capacityChangeField = new FloatListGridField("capacityChange", "Capacity Change");
        capacityChangeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(gasTypeField);
        listGridFieldList.add(inventoryChangeField);
        listGridFieldList.add(transferredAmountField);
        listGridFieldList.add(capacityChangeField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;

}
public static ListGridField[] getRefridgerationAirConditioningListGridFields_2() {

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField gasTypeField = new ListGridField("gasType", "Gas Type");
        //gasTypeField.setType(ListGridFieldType.TEXT);
        gasTypeField.setWidth(GAS_TYPE_FIELD_WIDTH);

        FloatListGridField newUnitsChargeField = new FloatListGridField("newUnitsCharge", "New Units Charge");
        newUnitsChargeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField newUnitsCapacityField = new FloatListGridField("newUnitsCapacity", "New Units Capacity");
        newUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField existingUnitsRechargeField = new FloatListGridField("existingUnitsRecharge", "Existing Units Recharge");
        existingUnitsRechargeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField disposedUnitsCapacityField = new FloatListGridField("disposedUnitsCapacity", "Disposed Units Capacity");
        disposedUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField disposedUnitsRecoveredField = new FloatListGridField("disposedUnitsRecovered", "Disposed Units Recovered");
        disposedUnitsRecoveredField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(gasTypeField);
        listGridFieldList.add(newUnitsChargeField);
        listGridFieldList.add(newUnitsCapacityField);
        listGridFieldList.add(existingUnitsRechargeField);
        listGridFieldList.add(disposedUnitsCapacityField);
        listGridFieldList.add(disposedUnitsRecoveredField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;

}
public static ListGridField[] getRefridgerationAirConditioningListGridFields_3(String typeOfData) {

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField gasTypeField = new ListGridField("gasType", "Gas Type");
        //gasTypeField.setType(ListGridFieldType.TEXT);
        gasTypeField.setWidth(GAS_TYPE_FIELD_WIDTH);

        ListGridField typeOfEquipmentField = new ListGridField("typeOfEquipment", "Type  Of Equipment");
        typeOfEquipmentField.setType(ListGridFieldType.TEXT);
        typeOfEquipmentField.setWidth(EQUIPMENT_TYPE_FIELD_WIDTH);

        FloatListGridField sourceNewUnitsChargeField = new FloatListGridField("sourceNewUnitsCharge", "Source New Units Charge");
        sourceNewUnitsChargeField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField operatingUnitsCapacityField = new FloatListGridField("operatingUnitsCapacity", "Operating Units Capacity");
        operatingUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField sourceDisposedUnitsCapacityField = new FloatListGridField("sourceDisposedUnitsCapacity", "Source Disposed Units Capacity");
        sourceDisposedUnitsCapacityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        FloatListGridField timeInYearsUsedField = new FloatListGridField("timeInYearsUsed", "Time In Years Used");
        timeInYearsUsedField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(gasTypeField);
        listGridFieldList.add(typeOfEquipmentField);
        listGridFieldList.add(sourceNewUnitsChargeField);
        listGridFieldList.add(operatingUnitsCapacityField);
        
        if (typeOfData.equalsIgnoreCase("RefridgerationAirConditioning")) {
            listGridFieldList.add(sourceDisposedUnitsCapacityField);
            listGridFieldList.add(timeInYearsUsedField);
        }
        
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;

}
public static ListGridField[] getPurchasedElecricityListGridFields() {

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField eGRIDSubregionField = new ListGridField("eGRIDSubregion", "eGRID Subregion");
        eGRIDSubregionField.setType(ListGridFieldType.TEXT);
        eGRIDSubregionField.setWidth(eGRIDSubregion_FIELD_WIDTH);

        FloatListGridField purchasedElectricityField = new FloatListGridField("purchasedElectricity", "Purchased Electricity");
        purchasedElectricityField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField purchasedElectricityUnitField = new ListGridField("purchasedElectricityUnit", "Purchased Electricity Unit");
        purchasedElectricityUnitField.setWidth(purchasedElectricityUnit_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(eGRIDSubregionField);
        listGridFieldList.add(purchasedElectricityField);
        listGridFieldList.add(purchasedElectricityUnitField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;

}
public static ListGridField[] getPurchasedSteamListGridFields() {
        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);
        fuelTypeField.setWidth(FUEL_TYPE_FIELD_WIDTH);

        FloatListGridField boilerEfficiencyPercentField = new FloatListGridField("boilerEfficiencyPercent", "boilerEfficiency Percent");
        //boilerEfficiencyPercentField.setType(ListGridFieldType.FLOAT);
        boilerEfficiencyPercentField.setDefaultValue(80);
        boilerEfficiencyPercentField.setWidth(boilerEfficiencyPercent_WIDTH);

        FloatListGridField purchasedSteamField = new FloatListGridField("purchasedSteam", "Purchased Steam");
        purchasedSteamField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField purchasedSteamUnitField = new ListGridField("purchasedSteamUnit", "Purchased Steam Unit");
        purchasedSteamUnitField.setType(ListGridFieldType.TEXT);
        purchasedSteamUnitField.setDefaultValue("mmBtu");
        purchasedSteamUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(fuelTypeField);
        listGridFieldList.add(boilerEfficiencyPercentField);
        listGridFieldList.add(purchasedSteamField);
	listGridFieldList.add(purchasedSteamUnitField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getOptionalSourceListGridFields(String typeOfData) {

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);
        vehicleTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        FloatListGridField passengerMilesField = new FloatListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField railTypeField = new ListGridField("railType", "Rail Type");
        railTypeField.setType(ListGridFieldType.TEXT);
        railTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        ListGridField busTypeField = new ListGridField("busType", "Bus Type");
        busTypeField.setType(ListGridFieldType.TEXT);
        busTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        ListGridField airTravelTypeField = new ListGridField("airTravelType", "Air Travel Type");
        airTravelTypeField.setType(ListGridFieldType.TEXT);
        airTravelTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        ListGridField transportTypeField = new ListGridField("transportType", "Transport Type");
        transportTypeField.setType(ListGridFieldType.TEXT);
        transportTypeField.setWidth(VEHICLE_TYPE_FIELD_WIDTH);

        FloatListGridField tonMilesField = new FloatListGridField("tonMiles", "Ton Miles");
        tonMilesField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(optionalSourceTypeField);
        listGridFieldList.add(sourceDescriptionField);
        
        if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByVehicle")){
            listGridFieldList.add(vehicleTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByRail")){
            listGridFieldList.add(railTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByBus")){
            listGridFieldList.add(busTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByAir")){
            listGridFieldList.add(airTravelTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("employeeCommutingByVehicle")){
            listGridFieldList.add(vehicleTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("employeeCommutingByRail")){
            listGridFieldList.add(railTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("employeeCommutingByBus")){
            listGridFieldList.add(busTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("productTransportByVehicle")){
            listGridFieldList.add(vehicleTypeField);
            listGridFieldList.add(passengerMilesField);
        } else if (typeOfData.equalsIgnoreCase("productTransportByHeavyDutyTrucks")){
            listGridFieldList.add(transportTypeField);
            listGridFieldList.add(tonMilesField);
        } else if (typeOfData.equalsIgnoreCase("productTransportByRail")){
            listGridFieldList.add(transportTypeField);
            listGridFieldList.add(tonMilesField);
        } else if (typeOfData.equalsIgnoreCase("productTransportByWaterAir")){
            listGridFieldList.add(transportTypeField);
            listGridFieldList.add(tonMilesField);
        }

        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;

}
public static ListGridField[] getWasteStreamCombustionListGridFields() {
    
        ListGridField sourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

	FloatListGridField amountOfWasterStreamGasCombustedField =
            new FloatListGridField("amountOfWasterStreamGasCombusted", "Amount Of Waster Stream Gas Combusted");
        amountOfWasterStreamGasCombustedField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

	ListGridField amountOfWasterStreamGasCombustedUnitField =
            new ListGridField("amountOfWasterStreamGasCombustedUnit", "Amount Of Waster Stream Gas Combusted Unit");
        amountOfWasterStreamGasCombustedUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

	FloatListGridField totalNumberOfMolesPerUnitVolumentField =
            new FloatListGridField("totalNumberOfMolesPerUnitVolument", "Total Number Of Moles Per Unit Volument");
        totalNumberOfMolesPerUnitVolumentField.setWidth(FUEL_QUANTITY_FIELD_WIDTH);

	ListGridField totalNumberOfMolesPerUnitVolumentUnitField =
            new ListGridField("totalNumberOfMolesPerUnitVolumentUnit", "Amount Of Waster Stream Gas Combusted Unit");
        totalNumberOfMolesPerUnitVolumentUnitField.setWidth(FUEL_UNIT_FIELD_WIDTH);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);
        fuelUsedEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(amountOfWasterStreamGasCombustedField);
        listGridFieldList.add(amountOfWasterStreamGasCombustedUnitField);
        listGridFieldList.add(totalNumberOfMolesPerUnitVolumentField);
	listGridFieldList.add(totalNumberOfMolesPerUnitVolumentUnitField);
        listGridFieldList.add(fuelUsedBeginDateField);
        listGridFieldList.add(fuelUsedEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public void setIbLFormWindow(String windowTitleString, DynamicForm df, Window dfw, ListGrid dg) {

        final DynamicForm dataForm = df;
        final Window dataFormWindow = dfw;
        final ListGrid dataGrid = dg;
        
        dataForm.setCellPadding(5);
        dataForm.setWidth("100%");

        final IButton cancelButton = new IButton();
        final IButton saveButton = new IButton();

        saveButton.setTitle("SAVE");
        //saveButton.setTooltip("Save this Stationary Combustion Source");
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
                dataFormWindow.hide();
            }
          }
        });

        cancelButton.setTitle("CANCEL");
        //cancelButton.setTooltip("Cancel");
        cancelButton.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent clickEvent) {
            dataForm.clearValues();
            dataFormWindow.hide();
          }
        });

        HLayout buttons = new HLayout(10);
        buttons.setAlign(Alignment.CENTER);
        buttons.addMember(cancelButton);
        buttons.addMember(saveButton);

        VLayout dialog = new VLayout(10);
        dialog.setPadding(25);
        dialog.addMember(dataForm);
        dialog.addMember(buttons);
        //dialog.setAlign(Alignment.CENTER);

        dataFormWindow.setShowShadow(true);
        dataFormWindow.setIsModal(true);
        dataFormWindow.setPadding(25);
        //dataFormWindow.setMinWidth(500);
        //dataFormWindow.setMinHeight(260);
        //dataFormWindow.setOverflow(Overflow.VISIBLE);
        //dataFormWindow.setAutoWidth();
        //dataFormWindow.setAutoHeight();
        dataFormWindow.setMinWidth(200);
        dataFormWindow.setMinHeight(200);
        dataFormWindow.setAutoSize(Boolean.TRUE);
        dataFormWindow.setShowMinimizeButton(false);
        dataFormWindow.setShowCloseButton(true);
        dataFormWindow.setShowModalMask(true);
        //dataFormWindow.centerInPage();
        dataFormWindow.setAutoCenter(Boolean.TRUE);
        dataFormWindow.setTitle(windowTitleString);
        dataFormWindow.setStyleName("labels");
        dataFormWindow.addItem(dialog);
        dataFormWindow.setDefaultLayoutAlign(Alignment.CENTER);
  }

public FormItem[] getMobileCombustionFormFields() {

    //-Define all the Mobile Combustion Form Fields

//-- setValidators for the forms for common types.
    initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    TextItem fuelSourceDescriptionItem = new TextItem("fuelSourceDescription");
    fuelSourceDescriptionItem.setTitle("Fuel Source Description");
    fuelSourceDescriptionItem.setSelectOnFocus(true);
    fuelSourceDescriptionItem.setWrapTitle(false);
    fuelSourceDescriptionItem.setDefaultValue("Source");
    fuelSourceDescriptionItem.setRequired(Boolean.TRUE);
    //TextItem vehicleTypeItem = new TextItem("vehicleType");
    //TextItem vehicleYearItem = new TextItem("vehicleYear");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    //vehicleTypeItem.setPickListWidth(310);
    vehicleTypeItem.setTitle("Vehicle Type");
    vehicleTypeItem.setOptionDataSource(vehicleType_EPADS);
    vehicleTypeItem.setRequired(Boolean.TRUE);
    //vehicleTypeItem.fetchData();
    //vehicleTypeItem.setDefaultToFirstOption(Boolean.TRUE);

    vehicleTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            mobileCombustionForm.clearValue("vehicleYear");
            mobileCombustionForm.clearValue("fuelType");
            Record selectedFuelTypeRecord = vehicleTypeItem.getSelectedRecord();
            mobileCombustionForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
        }
    });

    final SelectItem vehicleYearItem = new SelectItem(){
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };

    vehicleYearItem.setName("vehicleYear");
    //vehicleYearItem.setPickListWidth(310);
    vehicleYearItem.setTitle("Vehicle Year");
    vehicleYearItem.setOptionDataSource(eF_CH4N2O_MobileCombustion_EPADS);

    //final SelectItem fuelTypeItem = new SelectItem();

    final SelectItem fuelTypeItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };

    fuelTypeItem.setName("fuelType");
    //fuelTypeItem.setPickListWidth(310);
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(vehicleType_EPADS);

    final StaticTextItem fuelUnitItem = new StaticTextItem();
    fuelUnitItem.setName("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");
    //fuelUnitItem.setPickListWidth(250);
    //fuelUnitItem.setOptionDataSource(vehicleType_EPADS);

    IblFloatItem fuelQuantityItem = new IblFloatItem("fuelQuantity");
    IblFloatItem milesTravelledItem = new IblFloatItem("milesTravelled");

    /*
    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");
    fuelQuantityItem.setValidators(floatValidator);
    fuelQuantityItem.setValidateOnExit(Boolean.TRUE);
    fuelQuantityItem.setValidateOnChange(Boolean.TRUE);
    fuelQuantityItem.setRequired(Boolean.TRUE);

    FloatItem milesTravelledItem = new FloatItem();
    milesTravelledItem.setName("milesTravelled");
    milesTravelledItem.setValidators(floatValidator);
    milesTravelledItem.setValidateOnExit(Boolean.TRUE);
    milesTravelledItem.setValidateOnChange(Boolean.TRUE);
    milesTravelledItem.setRequired(Boolean.TRUE);
    */
    
    RequiredIfValidator ifBiofuelPctRequiredValidator = new RequiredIfValidator();
    ifBiofuelPctRequiredValidator.setExpression(new RequiredIfFunction() {
        public boolean execute(FormItem formItem, Object value) {
            String valueStr = (String) mobileCombustionForm.getItem("fuelType").getValue();
            //String valueStr = (String) dataForm.getItem("fuelType").getValue();
            if (valueStr !=  null){
                if (valueStr.contains("Biodiesel")){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    });
    ifBiofuelPctRequiredValidator.setErrorMessage("Please provide biofuel percent Value");

    IblFloatItem bioFuelPercentItem = new IblFloatItem("bioFuelPercent");
    /*
    FloatItem bioFuelPercentItem = new FloatItem();
    bioFuelPercentItem.setName("bioFuelPercent");
    bioFuelPercentItem.setValidators(floatValidator);
    bioFuelPercentItem.setValidateOnExit(Boolean.TRUE);
    bioFuelPercentItem.setValidateOnChange(Boolean.TRUE);
    bioFuelPercentItem.setValidators(ifBiofuelPctRequiredValidator);
    */

    RequiredIfValidator ifEthanolPctRequiredValidator = new RequiredIfValidator();
    ifEthanolPctRequiredValidator.setExpression(new RequiredIfFunction() {
        public boolean execute(FormItem formItem, Object value) {
            String valueStr = (String) mobileCombustionForm.getItem("fuelType").getValue();
            //String valueStr = (String) dataForm.getItem("fuelType").getValue();
            if (valueStr !=  null){
                if (valueStr.contains("Ethanol")){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    });
    ifEthanolPctRequiredValidator.setErrorMessage("Please provide ethanol percent Value");

    IblFloatItem ethanolPercentItem = new IblFloatItem("ethanolPercent");

    /*
    FloatItem ethanolPercentItem = new FloatItem();
    ethanolPercentItem.setName("ethanolPercent");
    ethanolPercentItem.setValidators(floatValidator);
    ethanolPercentItem.setValidateOnExit(Boolean.TRUE);
    ethanolPercentItem.setValidateOnChange(Boolean.TRUE);
    ethanolPercentItem.setValidators(ifEthanolPctRequiredValidator);
    */

    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

/*
    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setDateFormatter(DateDisplayFormat.TOSTRING);
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
*/
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(vehicleTypeItem);
    formItemList.add(vehicleYearItem);
    formItemList.add(fuelTypeItem);
    formItemList.add(fuelQuantityItem);
    formItemList.add(fuelUnitItem);
    formItemList.add(milesTravelledItem);
    formItemList.add(bioFuelPercentItem);
    formItemList.add(ethanolPercentItem);
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getStationaryCombustionFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("fuelSourceDescription");

    final SelectItem fuelTypeItem = new SelectItem();
    fuelTypeItem.setName("fuelType");
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(eF_StationaryCombustion_EPADS);
    fuelTypeItem.setRequired(Boolean.TRUE);

    fuelTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            //stationaryCombustionForm.clearValue("fuelUnit");
            Record selectedFuelTypeRecord = fuelTypeItem.getSelectedRecord();
            stationaryCombustionForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
        }
    });

    final StaticTextItem fuelUnitItem = new StaticTextItem("fuelUnit");
    fuelUnitItem.setTitle("Fuel Unit");

    //FuelQuantityItem fuelQuantityItem = new FuelQuantityItem();
    IblFloatItem fuelQuantityItem = new IblFloatItem("fuelQuantity");
    
    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);   
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    //fuelUsedEndDateItem.setValidOperators(validOperators);

/*
    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
*/
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(fuelTypeItem);
    formItemList.add(fuelQuantityItem);
    formItemList.add(fuelUnitItem);
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getRefridgerationAirConditioningFormFields_1(String typeOfData) {
//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("sourceDescription");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);
    gasTypeItem.setPickListCriteria(new Criteria("fetchType", typeOfData));
    gasTypeItem.setRequired(Boolean.TRUE);

    IblFloatItem inventoryChangeItem = new IblFloatItem("inventoryChange");
    IblFloatItem transferredAmountItem = new IblFloatItem("transferredAmount");
    IblFloatItem capacityChangeItem = new IblFloatItem("capacityChange");

    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);


    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setTitle("Method Type");

    if (typeOfData.equalsIgnoreCase("RefridgerationAirConditioning")) {
        methodTypeItem.setDefaultValue("Refridgeration Air Conditioning - Company-Wide Material Balance Method");
    } else if (typeOfData.equalsIgnoreCase("FireSuppression")){
        methodTypeItem.setDefaultValue("Fire Suppression - Company-Wide Material Balance Method");
    }

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(gasTypeItem);
    formItemList.add(inventoryChangeItem);
    formItemList.add(transferredAmountItem);
    formItemList.add(capacityChangeItem);
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);
    formItemList.add(methodTypeItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
    
}
public FormItem[] getRefridgerationAirConditioningFormFields_2(String typeOfData) {
//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("sourceDescription");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);
    gasTypeItem.setPickListCriteria(new Criteria("fetchType", typeOfData));
    gasTypeItem.setRequired(Boolean.TRUE);

    IblFloatItem newUnitsChargeItem = new IblFloatItem("newUnitsCharge");
    IblFloatItem newUnitsCapacityItem = new IblFloatItem("newUnitsCapacity");
    IblFloatItem existingUnitsRechargeItem = new IblFloatItem("existingUnitsRecharge");
    IblFloatItem disposedUnitsCapacityItem = new IblFloatItem("disposedUnitsCapacity");
    IblFloatItem disposedUnitsRecoveredItem = new IblFloatItem("disposedUnitsRecovered");

    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setTitle("Method Type");
    if (typeOfData.equalsIgnoreCase("RefridgerationAirConditioning")) {
        methodTypeItem.setDefaultValue("Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method");
    } else if (typeOfData.equalsIgnoreCase("FireSuppression")){
        methodTypeItem.setDefaultValue("Fire Suppression - Company-Wide Simplified Material Balance Method");
    }

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(gasTypeItem);
    formItemList.add(newUnitsChargeItem);
    formItemList.add(newUnitsCapacityItem);
    formItemList.add(existingUnitsRechargeItem);
    formItemList.add(disposedUnitsCapacityItem);
    formItemList.add(disposedUnitsRecoveredItem);
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);
    formItemList.add(methodTypeItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getRefridgerationAirConditioningFormFields_3(String typeOfData) {
//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("sourceDescription");

    final SelectItem gasTypeItem = new SelectItem();
    gasTypeItem.setName("gasType");
    gasTypeItem.setTitle("Gas Type");
    gasTypeItem.setOptionDataSource(gWP_RefridgerationAirConditioning_EPADS);
    gasTypeItem.setPickListCriteria(new Criteria("fetchType", typeOfData));
    gasTypeItem.setRequired(Boolean.TRUE);

    final SelectItem typeOfEquipmentItem = new SelectItem();
    typeOfEquipmentItem.setName("typeOfEquipment");
    typeOfEquipmentItem.setTitle("Type Of Equipment");

    if (typeOfData.equalsIgnoreCase("RefridgerationAirConditioning")) {
        typeOfEquipmentItem.setOptionDataSource(equipmentCapacityRange_EPADS);
    } else if (typeOfData.equalsIgnoreCase("FireSuppression")){
        typeOfEquipmentItem.setValueMap("Fixed","Portable");
    }

    IblFloatItem sourceNewUnitsChargeItem = new IblFloatItem("sourceNewUnitsCharge");
    IblFloatItem operatingUnitsCapacityItem = new IblFloatItem("operatingUnitsCapacity");
    IblFloatItem sourceDisposedUnitsCapacityItem = new IblFloatItem("sourceDisposedUnitsCapacity");
    IblFloatItem timeInYearsUsedItem = new IblFloatItem("timeInYearsUsed");

    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    TextItem methodTypeItem = new TextItem("methodType");
    methodTypeItem.setTitle("Method Type");
    if (typeOfData.equalsIgnoreCase("RefridgerationAirConditioning")) {
        methodTypeItem.setDefaultValue("Refridgeration Air Conditioning - Source Level Screening Method");
    } else if (typeOfData.equalsIgnoreCase("FireSuppression")){
        methodTypeItem.setDefaultValue("Fire Suppression - Source Level Screening Method");
    }

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(gasTypeItem);
    formItemList.add(typeOfEquipmentItem);
    formItemList.add(sourceNewUnitsChargeItem);
    formItemList.add(operatingUnitsCapacityItem);

    if (typeOfData.equalsIgnoreCase("RefridgerationAirConditioning")){
        formItemList.add(sourceDisposedUnitsCapacityItem);
        formItemList.add(timeInYearsUsedItem);
    }

    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);
    formItemList.add(methodTypeItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedElectricityFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("sourceDescription");

    final SelectItem eGRIDSubregionItem = new SelectItem();
    eGRIDSubregionItem.setName("eGRIDSubregion");
    eGRIDSubregionItem.setTitle("eGRID Subregion");
    eGRIDSubregionItem.setOptionDataSource(theEF_PurchasedElectricity_EPADS);


    IblFloatItem purchasedElectricityItem = new IblFloatItem("purchasedElectricity");

    StaticTextItem purchasedElectricityUnitItem = new StaticTextItem("purchasedElectricityUnit");
    purchasedElectricityUnitItem.setTitle("Purchased Electricity Unit");
    purchasedElectricityUnitItem.setDefaultValue("kWh");

    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(eGRIDSubregionItem);
    formItemList.add(purchasedElectricityItem);
    formItemList.add(purchasedElectricityUnitItem);
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedSteamFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("sourceDescription");

    final SelectItem fuelTypeItem = new SelectItem();
    fuelTypeItem.setName("fuelType");
    fuelTypeItem.setTitle("Fuel Type");
    fuelTypeItem.setOptionDataSource(theEF_PurchasedSteam_EPADS);

    IblFloatItem boilerEfficiencyPercentItem = new IblFloatItem("boilerEfficiencyPercent");

    IblFloatItem purchasedSteamItem = new IblFloatItem("purchasedSteam");
    StaticTextItem purchasedSteamUnitItem = new StaticTextItem("purchasedSteamUnit");
    purchasedSteamUnitItem.setTitle("Purchased Steam Unit");
    purchasedSteamUnitItem.setDefaultValue("mmBtu");

    IblFloatItem supplierCO2MultiplierItem = new IblFloatItem("supplierCO2Multiplier");
    StaticTextItem supplierCO2MultiplierUnitItem = new StaticTextItem("supplierCO2MultiplierUnit");
    supplierCO2MultiplierUnitItem.setTitle("Supplier CO2 Multiplier Unit");
    supplierCO2MultiplierUnitItem.setDefaultValue("lb/mmBtu");

    IblFloatItem supplierCH4MultiplierItem = new IblFloatItem("supplierCH4Multiplier");
    StaticTextItem supplierCH4MultiplierUnitItem = new StaticTextItem("supplierCH4MultiplierUnit");
    supplierCH4MultiplierUnitItem.setTitle("Supplier CH4 Multiplier Unit");
    supplierCH4MultiplierUnitItem.setDefaultValue("lb/mmBtu");

    IblFloatItem supplierN2OMultiplierItem = new IblFloatItem("supplierN2OMultiplier");
    StaticTextItem supplierN2OMultiplierUnitItem = new StaticTextItem("supplierN2OMultiplierUnit");
    supplierN2OMultiplierUnitItem.setTitle("Supplier N2O Multiplier Unit");
    supplierN2OMultiplierUnitItem.setDefaultValue("lb/mmBtu");


    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(fuelTypeItem);
    formItemList.add(boilerEfficiencyPercentItem);
    formItemList.add(purchasedSteamItem);
    formItemList.add(purchasedSteamUnitItem);
    formItemList.add(supplierCO2MultiplierItem);
    formItemList.add(supplierCO2MultiplierUnitItem);
    formItemList.add(supplierCH4MultiplierItem);
    formItemList.add(supplierCH4MultiplierUnitItem);
    formItemList.add(supplierN2OMultiplierItem);
    formItemList.add(supplierN2OMultiplierUnitItem);
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getOptionalSourceFormFields(String typeOfData) {
//-- setValidators for the forms for common types.
    initializeValidators();

    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("sourceDescription");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    vehicleTypeItem.setTitle("vehicleType");
    

    final SelectItem railTypeItem = new SelectItem();
    railTypeItem.setName("railType");
    railTypeItem.setTitle("railType");
    railTypeItem.setOptionDataSource(theEF_RailType_EPADS);

    final SelectItem busTypeItem = new SelectItem();
    busTypeItem.setName("busType");
    busTypeItem.setTitle("busType");
    busTypeItem.setOptionDataSource(theEF_BusType_EPADS);

    final SelectItem airTravelTypeItem = new SelectItem();
    airTravelTypeItem.setName("airTravelType");
    airTravelTypeItem.setTitle("airTravelType");
    airTravelTypeItem.setOptionDataSource(theEF_AirTravelType_EPADS);

    IblFloatItem passengerMilesItem = new IblFloatItem("passengerMiles");

    final SelectItem transportTypeItem = new SelectItem();
    transportTypeItem.setName("transportType");
    transportTypeItem.setTitle("transportType");
    transportTypeItem.setOptionDataSource(theEF_ProductTransportType_EPADS);

    FloatItem tonMilesItem = new FloatItem();
    tonMilesItem.setName("tonMiles");

    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByVehicle")){
         vehicleTypeItem.setOptionDataSource(theEF_VehicleType_EPADS);
         formItemList.add(vehicleTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Business Travel - By Vehicle");
    } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByRail")){
         formItemList.add(railTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Business Travel - By Rail");
    } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByBus")){
         formItemList.add(busTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Business Travel - By Bus");
    } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelByAir")){
         formItemList.add(airTravelTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Business Travel - By Air");
    } else if (typeOfData.equalsIgnoreCase("employeeCommutingByVehicle")){
         vehicleTypeItem.setOptionDataSource(theEF_VehicleType_EPADS);
         formItemList.add(vehicleTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Commuting - By Vehicle");
    } else if (typeOfData.equalsIgnoreCase("employeeCommutingByRail")){
         formItemList.add(railTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Commuting - By Rail");
    } else if (typeOfData.equalsIgnoreCase("employeeCommutingByBus")){
         formItemList.add(busTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Employee Commuting - By Bus");
    } else if (typeOfData.equalsIgnoreCase("productTransportByVehicle")){
         vehicleTypeItem.setOptionDataSource(theEF_ProductTransport_VehicleType_EPADS);
         formItemList.add(vehicleTypeItem);
         formItemList.add(passengerMilesItem);
         optionalSourceTypeItem.setDefaultValue("Product Transport - By Vehicle");
    } else if (typeOfData.equalsIgnoreCase("productTransportByHeavyDutyTrucks")){
         formItemList.add(transportTypeItem);
         formItemList.add(tonMilesItem);
         optionalSourceTypeItem.setDefaultValue("Product Transport - By Heavy Duty Trucks");
    } else if (typeOfData.equalsIgnoreCase("productTransportByRail")){
         formItemList.add(transportTypeItem);
         formItemList.add(tonMilesItem);
         optionalSourceTypeItem.setDefaultValue("Product Transport - By Rail");
    } else if (typeOfData.equalsIgnoreCase("productTransportByWaterAir")){
         formItemList.add(transportTypeItem);
         formItemList.add(tonMilesItem);
         optionalSourceTypeItem.setDefaultValue("Product Transport - By Water or Air");
    }

    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);
    formItemList.add(optionalSourceTypeItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getWasteStreamCombustionFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();
    
    FuelSourceDescriptionItem fuelSourceDescriptionItem = new FuelSourceDescriptionItem();
    fuelSourceDescriptionItem.setName("fuelSourceDescription");

    IblFloatItem amountOfWasterStreamGasCombustedItem = new IblFloatItem("amountOfWasterStreamGasCombusted");
    StaticTextItem amountOfWasterStreamGasCombustedUnitItem = new StaticTextItem("amountOfWasterStreamGasCombustedUnit");
    amountOfWasterStreamGasCombustedUnitItem.setDefaultValue("scf");

    IblFloatItem totalNumberOfMolesPerUnitVolumentItem = new IblFloatItem("totalNumberOfMolesPerUnitVolument");
    StaticTextItem totalNumberOfMolesPerUnitVolumentUnitItem = new StaticTextItem("totalNumberOfMolesPerUnitVolumentUnit");
    totalNumberOfMolesPerUnitVolumentUnitItem.setDefaultValue("lbmole/ft3");

    IblFloatItem carbonMonoxideMolarFractionPercentItem = new IblFloatItem("carbonMonoxideMolarFractionPercent");
    IblFloatItem carbonDioxideMolarFractionPercentItem = new IblFloatItem("carbonDioxideMolarFractionPercent");
    IblFloatItem methaneMolarFractionPercentItem = new IblFloatItem("methaneMolarFractionPercent");
    IblFloatItem cetyleneMolarFractionPercentItem = new IblFloatItem("cetyleneMolarFractionPercent");
    IblFloatItem ethyleneMolarFractionPercentItem = new IblFloatItem("ethyleneMolarFractionPercent");
    IblFloatItem ethaneMolarFractionPercentItem = new IblFloatItem("ethaneMolarFractionPercent");
    IblFloatItem propyleneMolarFractionPercentItem = new IblFloatItem("propyleneMolarFractionPercent");
    IblFloatItem propaneMolarFractionPercentItem = new IblFloatItem("propaneMolarFractionPercent");
    IblFloatItem n_ButaneMolarFractionPercentItem = new IblFloatItem("n_ButaneMolarFractionPercent");
    IblFloatItem benzeneMolarFractionPercentItem = new IblFloatItem("benzeneMolarFractionPercent");
    IblFloatItem bexaneMolarFractionPercentItem = new IblFloatItem("bexaneMolarFractionPercent");
    IblFloatItem tolueneMolarFractionPercentItem = new IblFloatItem("tolueneMolarFractionPercent");
    IblFloatItem octaneMolarFractionPercentItem = new IblFloatItem("octaneMolarFractionPercent");
    IblFloatItem ethanolMolarFractionPercentItem = new IblFloatItem("ethanolMolarFractionPercent");
    IblFloatItem acetoneMolarFractionPercentItem = new IblFloatItem("acetoneMolarFractionPercent");
    IblFloatItem tetrahydrofuranMolarFractionPercentItem = new IblFloatItem("tetrahydrofuranMolarFractionPercent");
    IblFloatItem otherNon_CMolarFractionPercentItem = new IblFloatItem("otherNon_CMolarFractionPercent");
    IblFloatItem oxidationFactorPercentItem = new IblFloatItem("oxidationFactorPercent");
    
    IblDateItem fuelUsedBeginDateItem = new IblDateItem("fuelUsedBeginDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);
    IblDateItem fuelUsedEndDateItem = new IblDateItem("fuelUsedEndDate", currentInventoryBeginDateMin,
                                                        currentInventoryEndDateMax, validateDateRange);

    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(fuelSourceDescriptionItem);
    formItemList.add(amountOfWasterStreamGasCombustedItem);
    formItemList.add(amountOfWasterStreamGasCombustedUnitItem);
    formItemList.add(totalNumberOfMolesPerUnitVolumentItem);
    formItemList.add(totalNumberOfMolesPerUnitVolumentUnitItem);
    formItemList.add(carbonMonoxideMolarFractionPercentItem);
    formItemList.add(carbonDioxideMolarFractionPercentItem);
    formItemList.add(methaneMolarFractionPercentItem);
    formItemList.add(cetyleneMolarFractionPercentItem);
    formItemList.add(ethyleneMolarFractionPercentItem);
    formItemList.add(ethaneMolarFractionPercentItem);
    formItemList.add(propyleneMolarFractionPercentItem);
    formItemList.add(propaneMolarFractionPercentItem);
    formItemList.add(n_ButaneMolarFractionPercentItem);
    formItemList.add(benzeneMolarFractionPercentItem);
    formItemList.add(bexaneMolarFractionPercentItem);
    formItemList.add(tolueneMolarFractionPercentItem);
    formItemList.add(octaneMolarFractionPercentItem);
    formItemList.add(ethanolMolarFractionPercentItem);
    formItemList.add(acetoneMolarFractionPercentItem);
    formItemList.add(tetrahydrofuranMolarFractionPercentItem);
    formItemList.add(otherNon_CMolarFractionPercentItem);
    formItemList.add(oxidationFactorPercentItem);
    
    formItemList.add(fuelUsedBeginDateItem);
    formItemList.add(fuelUsedEndDateItem);

    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

}


