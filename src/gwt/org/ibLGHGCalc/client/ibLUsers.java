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
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.SimpleType;
import com.smartgwt.client.data.SimpleTypeFormatter;
import com.smartgwt.client.data.SimpleTypeParser;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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

    private static final String NAME_FIELD_WIDTH = "15%";
    private static final String TYPE_FIELD_WIDTH = "15%";
    private static final String QUANTITY_FIELD_WIDTH = "5%";
    private static final String UNIT_FIELD_WIDTH = "5%";
    private static final String EF_UNIT_FIELD_WIDTH = "10%";
    private static final String EF_FIELD_WIDTH = "5%";
    
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

//--employeeBusinessHotelStay
    private final Window employeeBusinessTravelHotelStayFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelHotelStayForm = new DynamicForm();
    //private final BusinessTravelInfo_HotelStay_DS businessTravelInfo_HotelStay_DS = BusinessTravelInfo_HotelStay_DS.getInstance();    
    private final IblListGrid employeeBusinessTravelHotelStayDataGrid = new IblListGrid(optionalSourceInfoDS, employeeBusinessTravelHotelStayForm, employeeBusinessTravelHotelStayFormWindow);
    
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

//--Purchased Product
    private final TabSet purchasedProductTabSet = new TabSet();
    private final VLayout purchasedProductLayout = new VLayout(15);
        
//-- Purchased Product For Product Level method
    private final Window purchasedProductFormWindow_1 = new Window();
    private final DynamicForm purchasedProductForm_1 = new DynamicForm();
    private final PurchasedProductInfoDS_1 purchasedProductInfoDS_1 = PurchasedProductInfoDS_1.getInstance();
    private final IblListGrid purchasedProductDataGrid_1 = new IblListGrid(purchasedProductInfoDS_1, 
                                                                                        purchasedProductForm_1,
                                                                                        purchasedProductFormWindow_1);

//-- Purchased Product For Supplier Specific Level method
    private final Window purchasedProductFormWindow_2 = new Window();
    private final Window purchasedProductFormWindow_2_1 = new Window();
    private final DynamicForm purchasedProductForm_2 = new DynamicForm();
    private final PurchasedProductInfoDS_2 purchasedProductInfoDS_2 = PurchasedProductInfoDS_2.getInstance();
    /*
    private final IblListGrid purchasedProductDataGrid_2 = new IblListGrid(purchasedProductInfoDS_2, 
                                                                                        purchasedProductForm_2,
                                                                                        purchasedProductFormWindow_2);
     * 
     */
    private final MaterialUsedBy_T1S_InfoDS materialUsedBy_T1S_InfoDS = MaterialUsedBy_T1S_InfoDS.getInstance();    
    private final MaterialTransportTo_T1S_InfoDS materialTransportTo_T1S_InfoDS = MaterialTransportTo_T1S_InfoDS.getInstance();        
    private final WasteOutputFrom_T1S_InfoDS wasteOutputFrom_T1S_InfoDS = WasteOutputFrom_T1S_InfoDS.getInstance();        
    
    private final Criteria purchasedProductCriteria = new Criteria();
    private final IblCompoundEditor cEditor = new IblCompoundEditor(materialUsedBy_T1S_InfoDS, purchasedProductCriteria);    
    
    private final IblListGrid purchasedProductDataGrid_2 = new IblListGrid(purchasedProductInfoDS_2, 
                                                                                    purchasedProductForm_2,
                                                                                    purchasedProductFormWindow_2){
        @Override
        protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

            String fieldName = this.getFieldName(colNum);

            if (fieldName.equals("materialUsedBy_T1S")) {
                EditImageButton editImg = new EditImageButton();
                editImg.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
                        Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
                        Integer purchasedProductInfoId = (Integer) record.getAttributeAsInt("id");
                        String purchasedProductType = (String)record.getAttribute("purchasedProductType");

                        Criteria criteria = new Criteria();
                        criteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDateMin);
                        criteria.addCriteria("inventoryYearEndDate", currentInventoryEndDateMax);            
                        criteria.addCriteria("purchasedProductInfoId", purchasedProductInfoId);            
                        criteria.addCriteria("purchasedProductType", purchasedProductType);            

                        //Map<String, Integer> parentKeyMap = new HashMap<String, Integer>();
                        //parentKeyMap.put("purchasedProductInfoId",purchasedProductInfoId);
                        //String parentKey  = "purchasedProductInfoId";
                        //Integer parentKeyValue= purchasedProductInfoId;
                        //final IblCompoundEditor c2Editor = new IblCompoundEditor(materialUsedBy_T1S_InfoDS, criteria,getMaterialUsedBy_T1S_FormFields());
                        //FormItem[] formFields = getMaterialUsedBy_T1S_FormFields();
                        cEditor.setDatasourceAndCriteria(materialUsedBy_T1S_InfoDS, criteria, getMaterialUsedBy_T1S_FormFields());
                        purchasedProductFormWindow_2_1.show();
                    }
                });

                return editImg;
            } else if (fieldName.equals("materialTransportTo_T1S")) {
                //RemoveIButton button = new RemoveIButton();
                EditImageButton editImg = new EditImageButton();
                editImg.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
                        Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
                        Integer purchasedProductInfoId = (Integer) record.getAttributeAsInt("id");
                        String purchasedProductType = (String)record.getAttribute("purchasedProductType");
                        
                        Criteria criteria = new Criteria();
                        criteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDateMin);
                        criteria.addCriteria("inventoryYearEndDate", currentInventoryEndDateMax);            
                        criteria.addCriteria("purchasedProductInfoId", purchasedProductInfoId);            
                        criteria.addCriteria("purchasedProductType", purchasedProductType);            
                        
                        cEditor.setDatasourceAndCriteria(materialTransportTo_T1S_InfoDS, criteria,getMaterialTransportTo_T1S_FormFields());
                        purchasedProductFormWindow_2_1.show();
                    }
                });
                return editImg;
            } else if (fieldName.equals("wasteOutputFrom_T1S")) {
                //RemoveIButton button = new RemoveIButton();
                EditImageButton editImg = new EditImageButton();
                editImg.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
                        Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
                        Integer purchasedProductInfoId = (Integer) record.getAttributeAsInt("id");
                        String purchasedProductType = (String)record.getAttribute("purchasedProductType");
                        
                        Criteria criteria = new Criteria();
                        criteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDateMin);
                        criteria.addCriteria("inventoryYearEndDate", currentInventoryEndDateMax);            
                        criteria.addCriteria("purchasedProductInfoId", purchasedProductInfoId);            
                        criteria.addCriteria("purchasedProductType", purchasedProductType);            
                        
                        cEditor.setDatasourceAndCriteria(wasteOutputFrom_T1S_InfoDS, criteria,getWasteOutputFrom_T1S_FormFields());
                        purchasedProductFormWindow_2_1.show();
                    }
                });
                return editImg;
            } else {
                return null;
            }
        }
    };
         
//-- Purchased Product For Material or Spend-Based Calculation Method
    private final Window purchasedProductFormWindow_3 = new Window();
    private final DynamicForm purchasedProductForm_3 = new DynamicForm();
    private final PurchasedProductInfoDS_3 purchasedProductInfoDS_3 = PurchasedProductInfoDS_3.getInstance();
    private final IblListGrid purchasedProductDataGrid_3 = new IblListGrid(purchasedProductInfoDS_3, 
                                                                                        purchasedProductForm_3,
                                                                                        purchasedProductFormWindow_3);
    
    
    
//--PurchasedEnergyInfo
    private final TabSet purchasedEnergyTabSet = new TabSet();
    private final VLayout purchasedEnergyLayout = new VLayout(15);
/*
    private final PurchasedEnergyInfoDS purchasedEnergyInfoDS = PurchasedEnergyInfoDS.getInstance();
  
    private final Window purchasedEnergyFormWindow = new Window();
    private final DynamicForm purchasedEnergyForm = new DynamicForm();
    private final IblListGrid purchasedEnergyDataGrid = new IblListGrid(purchasedEnergyInfoDS, 
                                                                                        purchasedEnergyForm,
                                                                                        purchasedEnergyFormWindow);
*/    

    private final PurchasedEnergyInfoDS_1 purchasedEnergyInfoDS_1 = PurchasedEnergyInfoDS_1.getInstance();    
    private final Window purchasedEnergyFormWindow_1 = new Window();
    private final DynamicForm purchasedEnergyForm_1 = new DynamicForm();
    private final IblListGrid purchasedEnergyDataGrid_1 = new IblListGrid(purchasedEnergyInfoDS_1, 
                                                                                        purchasedEnergyForm_1,
                                                                                        purchasedEnergyFormWindow_1);

    private final PurchasedEnergyInfoDS_2 purchasedEnergyInfoDS_2 = PurchasedEnergyInfoDS_2.getInstance();    
    private final Window purchasedEnergyFormWindow_2 = new Window();
    private final DynamicForm purchasedEnergyForm_2 = new DynamicForm();
    private final IblListGrid purchasedEnergyDataGrid_2 = new IblListGrid(purchasedEnergyInfoDS_2, 
                                                                                        purchasedEnergyForm_2,
                                                                                        purchasedEnergyFormWindow_2);

    private final PurchasedEnergyInfoDS_3 purchasedEnergyInfoDS_3 = PurchasedEnergyInfoDS_3.getInstance();    
    private final Window purchasedEnergyFormWindow_3 = new Window();
    private final DynamicForm purchasedEnergyForm_3 = new DynamicForm();
    private final IblListGrid purchasedEnergyDataGrid_3 = new IblListGrid(purchasedEnergyInfoDS_3, 
                                                                                        purchasedEnergyForm_3,
                                                                                        purchasedEnergyFormWindow_3);

    private final PurchasedEnergyInfoDS_4 purchasedEnergyInfoDS_4 = PurchasedEnergyInfoDS_4.getInstance();        
    private final Window purchasedEnergyFormWindow_4 = new Window();
    private final DynamicForm purchasedEnergyForm_4 = new DynamicForm();
    private final IblListGrid purchasedEnergyDataGrid_4 = new IblListGrid(purchasedEnergyInfoDS_4, 
                                                                                        purchasedEnergyForm_4,
                                                                                        purchasedEnergyFormWindow_4);
    
    
//--Transportation
    private final TabSet transportationTabSet = new TabSet();
    private final VLayout transportationLayout = new VLayout(15);
        
//--Transportation For Fuel Based Method -  transport emission		
    private final Window transportationFormWindow_1 = new Window();
    private final DynamicForm transportationForm_1 = new DynamicForm();
    private final TransportationInfoDS_1 transportationInfoDS_1 = TransportationInfoDS_1.getInstance();
    private final IblListGrid transportationDataGrid_1 = new IblListGrid(transportationInfoDS_1, 
                                                                                        transportationForm_1,
                                                                                        transportationFormWindow_1);
    
//--Transportation For Fuel Based Method -  transport emission		
    private final Window transportationFormWindow_2 = new Window();
    private final DynamicForm transportationForm_2 = new DynamicForm();
    private final TransportationInfoDS_2 transportationInfoDS_2 = TransportationInfoDS_2.getInstance();
    private final IblListGrid transportationDataGrid_2 = new IblListGrid(transportationInfoDS_2, 
                                                                                        transportationForm_2,
                                                                                        transportationFormWindow_2);
//--Distribution
    private final TabSet distributionTabSet = new TabSet();
    private final VLayout distributionLayout = new VLayout(15);
        
//-- Distribution For Site Specific Method - distribution emission								
    private final Window distributionFormWindow_1 = new Window();
    private final DynamicForm distributionForm_1 = new DynamicForm();
    private final DistributionInfoDS_1 distributionInfoDS_1 = DistributionInfoDS_1.getInstance();
    private final IblListGrid distributionDataGrid_1 = new IblListGrid(distributionInfoDS_1, 
                                                                                        distributionForm_1,
                                                                                        distributionFormWindow_1);    
//-- Distribution For Site Specific Method - distribution emission								
    private final Window distributionFormWindow_2 = new Window();
    private final DynamicForm distributionForm_2 = new DynamicForm();
    private final DistributionInfoDS_2 distributionInfoDS_2 = DistributionInfoDS_2.getInstance();
    private final IblListGrid distributionDataGrid_2 = new IblListGrid(distributionInfoDS_2, 
                                                                                        distributionForm_2,
                                                                                        distributionFormWindow_2);

//--WasteGeneratedInOperationsInfo
    private final TabSet wasteGeneratedInOperationsTabSet = new TabSet();
    private final VLayout wasteGeneratedInOperationsLayout = new VLayout(15);

    private final WasteGeneratedInOperationsInfoDS_1 wasteGeneratedInOperationsInfoDS_1 = WasteGeneratedInOperationsInfoDS_1.getInstance();    
    private final Window wasteGeneratedInOperationsFormWindow_1 = new Window();
    private final DynamicForm wasteGeneratedInOperationsForm_1 = new DynamicForm();
    private final IblListGrid wasteGeneratedInOperationsDataGrid_1 = new IblListGrid(wasteGeneratedInOperationsInfoDS_1, 
                                                                                        wasteGeneratedInOperationsForm_1,
                                                                                        wasteGeneratedInOperationsFormWindow_1);

    private final WasteGeneratedInOperationsInfoDS_2 wasteGeneratedInOperationsInfoDS_2 = WasteGeneratedInOperationsInfoDS_2.getInstance();    
    private final Window wasteGeneratedInOperationsFormWindow_2 = new Window();
    private final DynamicForm wasteGeneratedInOperationsForm_2 = new DynamicForm();
    private final IblListGrid wasteGeneratedInOperationsDataGrid_2 = new IblListGrid(wasteGeneratedInOperationsInfoDS_2, 
                                                                                        wasteGeneratedInOperationsForm_2,
                                                                                        wasteGeneratedInOperationsFormWindow_2);
  //--Business Travel Info
    private final TabSet businessTravelTabSet = new TabSet();
    private final VLayout businessTravelLayout = new VLayout(15);

    private final BusinessTravelInfoDS businessTravelInfoDS = BusinessTravelInfoDS.getInstance();    
    private final Window businessTravelFormWindow = new Window();
    private final DynamicForm businessTravelForm = new DynamicForm();
    private final IblListGrid businessTravelDataGrid = new IblListGrid(businessTravelInfoDS, 
                                                                                        businessTravelForm,
                                                                                        businessTravelFormWindow);

    //Leased Assets Info
    private final TabSet leasedAssetsTabSet = new TabSet();
    private final VLayout leasedAssetsLayout = new VLayout(15);
    
    private final LeasedAssetsInfoDS_1 leasedAssetsInfoDS_1 = LeasedAssetsInfoDS_1.getInstance();    
    private final Window leasedAssetsFormWindow_1 = new Window();
    private final DynamicForm leasedAssetsForm_1 = new DynamicForm();
    private final IblListGrid leasedAssetsDataGrid_1 = new IblListGrid(leasedAssetsInfoDS_1, 
                                                                                        leasedAssetsForm_1,
                                                                                        leasedAssetsFormWindow_1);

    private final LeasedAssetsInfoDS_2 leasedAssetsInfoDS_2 = LeasedAssetsInfoDS_2.getInstance();    
    private final Window leasedAssetsFormWindow_2 = new Window();
    private final DynamicForm leasedAssetsForm_2 = new DynamicForm();
    private final IblListGrid leasedAssetsDataGrid_2 = new IblListGrid(leasedAssetsInfoDS_2, 
                                                                                        leasedAssetsForm_2,
                                                                                        leasedAssetsFormWindow_2);    

    
    //Processing Of Sold Products Info
    private final TabSet processingOfSoldProductsTabSet = new TabSet();
    private final VLayout processingOfSoldProductsLayout = new VLayout(15);
    
    private final ProcessingOfSoldProductsInfoDS_1 processingOfSoldProductsInfoDS_1 = ProcessingOfSoldProductsInfoDS_1.getInstance();    
    private final Window processingOfSoldProductsFormWindow_1 = new Window();
    private final DynamicForm processingOfSoldProductsForm_1 = new DynamicForm();
    private final IblListGrid processingOfSoldProductsDataGrid_1 = new IblListGrid(processingOfSoldProductsInfoDS_1, 
                                                                                        processingOfSoldProductsForm_1,
                                                                                        processingOfSoldProductsFormWindow_1);

    private final ProcessingOfSoldProductsInfoDS_2 processingOfSoldProductsInfoDS_2 = ProcessingOfSoldProductsInfoDS_2.getInstance();    
    private final Window processingOfSoldProductsFormWindow_2 = new Window();
    private final DynamicForm processingOfSoldProductsForm_2 = new DynamicForm();
    private final IblListGrid processingOfSoldProductsDataGrid_2 = new IblListGrid(processingOfSoldProductsInfoDS_2, 
                                                                                        processingOfSoldProductsForm_2,
                                                                                        processingOfSoldProductsFormWindow_2);    
    
    

    //DirectUsePhaseEmissions Info
    private final TabSet directUsePhaseEmissionsTabSet = new TabSet();
    private final VLayout directUsePhaseEmissionsLayout = new VLayout(15);
    
    private final DirectUsePhaseEmissionsInfoDS_1 directUsePhaseEmissionsInfoDS_1 = DirectUsePhaseEmissionsInfoDS_1.getInstance();    
    private final Window directUsePhaseEmissionsFormWindow_1 = new Window();
    private final DynamicForm directUsePhaseEmissionsForm_1 = new DynamicForm();
    private final IblListGrid directUsePhaseEmissionsDataGrid_1 = new IblListGrid(directUsePhaseEmissionsInfoDS_1, 
                                                                                        directUsePhaseEmissionsForm_1,
                                                                                        directUsePhaseEmissionsFormWindow_1);

    private final DirectUsePhaseEmissionsInfoDS_2 directUsePhaseEmissionsInfoDS_2 = DirectUsePhaseEmissionsInfoDS_2.getInstance();    
    private final Window directUsePhaseEmissionsFormWindow_2 = new Window();
    private final DynamicForm directUsePhaseEmissionsForm_2 = new DynamicForm();
    private final IblListGrid directUsePhaseEmissionsDataGrid_2 = new IblListGrid(directUsePhaseEmissionsInfoDS_2, 
                                                                                        directUsePhaseEmissionsForm_2,
                                                                                        directUsePhaseEmissionsFormWindow_2);    
																						
    private final DirectUsePhaseEmissionsInfoDS_3 directUsePhaseEmissionsInfoDS_3 = DirectUsePhaseEmissionsInfoDS_3.getInstance();    
    private final Window directUsePhaseEmissionsFormWindow_3 = new Window();
    private final DynamicForm directUsePhaseEmissionsForm_3 = new DynamicForm();
    private final IblListGrid directUsePhaseEmissionsDataGrid_3 = new IblListGrid(directUsePhaseEmissionsInfoDS_3, 
                                                                                        directUsePhaseEmissionsForm_3,
                                                                                        directUsePhaseEmissionsFormWindow_3);    


    //InDirectUsePhaseEmissions Info
    private final TabSet inDirectUsePhaseEmissionsTabSet = new TabSet();
    private final VLayout inDirectUsePhaseEmissionsLayout = new VLayout(15);
    
    private final InDirectUsePhaseEmissionsInfoDS_1 inDirectUsePhaseEmissionsInfoDS_1 = InDirectUsePhaseEmissionsInfoDS_1.getInstance();    
    private final Window inDirectUsePhaseEmissionsFormWindow_1 = new Window();
    private final DynamicForm inDirectUsePhaseEmissionsForm_1 = new DynamicForm();
    private final IblListGrid inDirectUsePhaseEmissionsDataGrid_1 = new IblListGrid(inDirectUsePhaseEmissionsInfoDS_1, 
                                                                                        inDirectUsePhaseEmissionsForm_1,
                                                                                        inDirectUsePhaseEmissionsFormWindow_1);

    private final InDirectUsePhaseEmissionsInfoDS_2 inDirectUsePhaseEmissionsInfoDS_2 = InDirectUsePhaseEmissionsInfoDS_2.getInstance();    
    private final Window inDirectUsePhaseEmissionsFormWindow_2 = new Window();
    private final DynamicForm inDirectUsePhaseEmissionsForm_2 = new DynamicForm();
    private final IblListGrid inDirectUsePhaseEmissionsDataGrid_2 = new IblListGrid(inDirectUsePhaseEmissionsInfoDS_2, 
                                                                                        inDirectUsePhaseEmissionsForm_2,
                                                                                        inDirectUsePhaseEmissionsFormWindow_2);    


    //endOfLifeTreatmentOfSoldProductsInfo
    private final TabSet endOfLifeTreatmentOfSoldProductsTabSet = new TabSet();
    private final VLayout endOfLifeTreatmentOfSoldProductsLayout = new VLayout(15);
    
    private final EndOfLifeTreatmentOfSoldProductsInfoDS endOfLifeTreatmentOfSoldProductsInfoDS = EndOfLifeTreatmentOfSoldProductsInfoDS.getInstance();    
    private final Window endOfLifeTreatmentOfSoldProductsFormWindow = new Window();
    private final DynamicForm endOfLifeTreatmentOfSoldProductsForm = new DynamicForm();
    private final IblListGrid endOfLifeTreatmentOfSoldProductsDataGrid = new IblListGrid(endOfLifeTreatmentOfSoldProductsInfoDS, 
                                                                                        endOfLifeTreatmentOfSoldProductsForm,
                                                                                        endOfLifeTreatmentOfSoldProductsFormWindow);


//--FranchisesInfo
    private final TabSet franchisesTabSet = new TabSet();
    private final VLayout franchisesLayout = new VLayout(15);

    private final FranchisesInfoDS_1 franchisesInfoDS_1 = FranchisesInfoDS_1.getInstance();    
    private final Window franchisesFormWindow_1 = new Window();
    private final DynamicForm franchisesForm_1 = new DynamicForm();
    private final IblListGrid franchisesDataGrid_1 = new IblListGrid(franchisesInfoDS_1, 
                                                                                        franchisesForm_1,
                                                                                        franchisesFormWindow_1);

    private final FranchisesInfoDS_2 franchisesInfoDS_2 = FranchisesInfoDS_2.getInstance();    
    private final Window franchisesFormWindow_2 = new Window();
    private final DynamicForm franchisesForm_2 = new DynamicForm();
    private final IblListGrid franchisesDataGrid_2 = new IblListGrid(franchisesInfoDS_2, 
                                                                                        franchisesForm_2,
                                                                                        franchisesFormWindow_2);

//--InvestmentsInfo
    private final TabSet investmentsTabSet = new TabSet();
    private final VLayout investmentsLayout = new VLayout(15);

    private final InvestmentsInfoDS_1 investmentsInfoDS_1 = InvestmentsInfoDS_1.getInstance();    
    private final Window investmentsFormWindow_1 = new Window();
    private final DynamicForm investmentsForm_1 = new DynamicForm();
    private final IblListGrid investmentsDataGrid_1 = new IblListGrid(investmentsInfoDS_1, 
                                                                                        investmentsForm_1,
                                                                                        investmentsFormWindow_1);

    private final InvestmentsInfoDS_2 investmentsInfoDS_2 = InvestmentsInfoDS_2.getInstance();    
    private final Window investmentsFormWindow_2 = new Window();
    private final DynamicForm investmentsForm_2 = new DynamicForm();
    private final IblListGrid investmentsDataGrid_2 = new IblListGrid(investmentsInfoDS_2, 
                                                                                        investmentsForm_2,
                                                                                        investmentsFormWindow_2);
    
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
        //return inputDateFormatter.format(date);
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
                    //if (value == null) return "";                    
                    //if (value == "") return "";
                    String valueString = value.toString().replaceAll("&nbsp;","");
                    if (valueString.isEmpty()) return null;
                    //if (value.toString().replaceAll("&nbsp;","").equals("")) return "";
                    //return floatSimpleFormat.format(Double.valueOf(value.toString().replaceAll("&nbsp;","")));
                    return floatSimpleFormat.format(Double.valueOf(valueString));
            }
    });

/*    
//-Below is not working as of 08/04/2011    
    floatSimpleType.setEditParser(new SimpleTypeParser() {
        @Override
        public Object parseInput(String value, DataClass field,
                DataBoundComponent component, Record record) {
            if (value==null) value = "0";
            try {
                // Remove anything but digits and the decimal point
                String strippedValue = value.toString().replaceAll("[^0-9\\.]", "");
                Double valueDouble = Double.valueOf(strippedValue.length()==0 ? "0" : strippedValue);
               // if ("0".equals(IPGui.homeCurrencyDecimals))
                //    return valueDouble/100d;
                //else
                    return valueDouble;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    });    
  
* 
*/
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

    /*
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
*/
    
    Label purchasedGoodsAndServicesLabel = getSectionLink("Purchased Goods and Services", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Purchased Goods and Services");
       }
    });
    optionalEmissionsSourcesSection.addItem(purchasedGoodsAndServicesLabel);
/*    
//------------------------------------------------
    Label capitalGoodsLabel = getSectionLink("Capital Goods", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Capital Goods");
       }
    });
    optionalEmissionsSourcesSection.addItem(capitalGoodsLabel);	
*/
//------------------------------------------------
    Label purchasedEnergyLabel = getSectionLink("Fuel and Energy Related Activities", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Purchased Energy");
       }
    });
    optionalEmissionsSourcesSection.addItem(purchasedEnergyLabel);
/*        
//------------------------------------------------
    Label upstreamEmissionsFromPurchasedFuelLabel = getSectionLink("Upstream Emissions From Purchased Fuel", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Upstream Emissions From Purchased Fuel");
       }
    });
    optionalEmissionsSourcesSection.addItem(upstreamEmissionsFromPurchasedFuelLabel);	

	
//------------------------------------------------
    Label upstreamEmissionsFromPurchasedElectricityLabel = getSectionLink("Upstream Emissions From Purchased Electricity", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Upstream Emissions From Purchased Electricity");
       }
    });
    optionalEmissionsSourcesSection.addItem(upstreamEmissionsFromPurchasedElectricityLabel);	

//------------------------------------------------
    Label emissionsFromTransAndDistLossesLabel = getSectionLink("Emissions From Transmission And Distribution Losses", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Emissions From Transmission And Distribution Losses");
       }
    });
    optionalEmissionsSourcesSection.addItem(emissionsFromTransAndDistLossesLabel);	

//------------------------------------------------

    Label emissionsFromPowerPurchasedAndSoldLabel = getSectionLink("Emissions From Power Purchased And Sold", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Emissions From Power Purchased And Sold");
       }
    });
    optionalEmissionsSourcesSection.addItem(emissionsFromPowerPurchasedAndSoldLabel);	
*/
//------------------------------------------------
    Label transportationLabel = getSectionLink("Transportation", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Transportation");
       }
    });
    optionalEmissionsSourcesSection.addItem(transportationLabel);	

//------------------------------------------------
    Label distributionLabel = getSectionLink("Distribution", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Distribution");
       }
    });
    optionalEmissionsSourcesSection.addItem(distributionLabel);	

//------------------------------------------------
    Label wasteGeneratedInOperationsLabel = getSectionLink("Waste Generated In Operations", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Waste Generated In Operations");
       }
    });
    optionalEmissionsSourcesSection.addItem(wasteGeneratedInOperationsLabel);	

//------------------------------------------------
/*
    Label businessTravelLabel = getSectionLink("Business Travel", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Business Travel");
       }
    });
    optionalEmissionsSourcesSection.addItem(businessTravelLabel);	
*/
    Label getEmployeeBusinessTravelInfoLabel = getSectionLink("Employee Business Travel", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Employee Business Travel");
       }
    });
    optionalEmissionsSourcesSection.addItem(getEmployeeBusinessTravelInfoLabel);
    
//------------------------------------------------
    Label employeeCommutingLabel = getSectionLink("Employee Commuting", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Employee Commuting");
       }
    });
    optionalEmissionsSourcesSection.addItem(employeeCommutingLabel);	

//------------------------------------------------
    Label leasedAssetsLabel = getSectionLink("Leased Assets", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Leased Assets");
       }
    });
    optionalEmissionsSourcesSection.addItem(leasedAssetsLabel);	


//------------------------------------------------
    Label processingOfSoldProductsLabel = getSectionLink("Processing Of Sold Products", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Processing Of Sold Products");
       }
    });
    optionalEmissionsSourcesSection.addItem(processingOfSoldProductsLabel);	

//------------------------------------------------
    Label directUsePhaseEmissionsForSoldProductsLabel = getSectionLink("Direct Use Phase Emissions For Sold Products", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Direct Use Phase Emissions For Sold Products");
       }
    });
    optionalEmissionsSourcesSection.addItem(directUsePhaseEmissionsForSoldProductsLabel);	

//------------------------------------------------
    Label inDirectUsePhaseEmissionsForSoldProductsLabel = getSectionLink("In-Direct Use Phase Emissions For Sold Products", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("In-Direct Use Phase Emissions For Sold Products");
       }
    });
    optionalEmissionsSourcesSection.addItem(inDirectUsePhaseEmissionsForSoldProductsLabel);	

//------------------------------------------------

    Label endOfLifeTreatmentOfSoldProductsLabel = getSectionLink("End Of Life Treatment Of Sold Products", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("End Of Life Treatment Of Sold Products");
       }
    });
    optionalEmissionsSourcesSection.addItem(endOfLifeTreatmentOfSoldProductsLabel);	


//------------------------------------------------
    Label franchisesLabel = getSectionLink("Franchises", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Franchises");
       }
    });
    optionalEmissionsSourcesSection.addItem(franchisesLabel);	

//------------------------------------------------

    Label investmentsLabel = getSectionLink("Investments", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Investments");
       }
    });
    optionalEmissionsSourcesSection.addItem(investmentsLabel);	
           
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

//-Set the ID for dynamic Form
    
    purchasedProductForm_2.setID("purchasedProductForm_2");    
    
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

    //--employeeBusinessTravelHotelStay
    employeeBusinessTravelHotelStayTab();
    initEmployeeBusinessTravelHotelStayEditForm();
    
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
    
    //--purchasedProduct - Goods, Service, Capital Goods
    purchasedProductTab();
    initPurchasedProductEditForm();

    //Purchased energy Info    
    purchasedEnergyInfoTab();
    initPurchasedEnergyEditForm();
    
    //Transportation Emissions
    transportationInfoTab();
    initTransportationEditForm();

    //Distribution Emissions
    distributionInfoTab();
    initDistributionEditForm();
 
    //wasteGeneratedInOperationsInfo
    wasteGeneratedInOperationsInfoTab();
    initWasteGeneratedInOperationsEditForm();
    
    //businessTravelInfo
    businessTravelInfoTab();
    initBusinessTravelEditForm();
    
    //leasedAssetsInfo
    leasedAssetsInfoTab();
    initLeasedAssetsEditForm();
    
    //processingOfSoldProductsInfo
    processingOfSoldProductsInfoTab();
    initProcessingOfSoldProductsEditForm();

    //directUsePhaseEmissions
    directUsePhaseEmissionsInfoTab();
    initDirectUsePhaseEmissionsEditForm();

    //franchises
    inDirectUsePhaseEmissionsInfoTab();
    initInDirectUsePhaseEmissionsEditForm();
    
    
    //endOfLifeTreatmentOfSoldProducts
    endOfLifeTreatmentOfSoldProductsInfoTab();
    initEndOfLifeTreatmentOfSoldProductsEditForm();

    //franchisesInfo
    franchisesInfoTab();
    initFranchisesEditForm();

    //investmentsInfo
    investmentsInfoTab();
    initInvestmentsEditForm();    
    
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
                //if (value == null) return null;
                try {
                    return "Click to download Report";
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
                        
                        String fullContextPath = GWT.getHostPageBaseURL();
                        String[] contextPathArray = fullContextPath.split("/");
                        String contextPath= contextPathArray[contextPathArray.length - 1];
                        System.out.println("Context Path is: "+contextPath);
                        //SC.say("contextPath is: " + contextPath);
                        //String contextPath= fullContextPath.substring(fullContextPath.lastIndexOf('/') + 1);
                        //HttpServletRequest request = this.getThreadLocalRequest();
                        //String contextPath = ServletContext.getContextPath();
                        
                        
                        //String url = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                        //String url = "/"+contextPath+"/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                        
                        String reportUrl;
                        if (contextPath.contains("igreenlaunch")) {
                            reportUrl = "/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
                        } else {
                            reportUrl = "/"+contextPath+"/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;        
                        }                        
                        com.google.gwt.user.client.Window.open(reportUrl,null,null);
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
    //sfloatValidator.setErrorMessage("Not a valid float value");

    FloatPrecisionValidator floatPrecisionValidator = new FloatPrecisionValidator();
    floatPrecisionValidator.setErrorMessage("Need to have 2 decimal places");

}
public static HashMap getInitialValues() {
    HashMap formDefaultValue = new HashMap();
    formDefaultValue.put("fuelUsedBeginDate", currentInventoryBeginDateMin);
    formDefaultValue.put("fuelUsedEndDate", currentInventoryEndDateMax);

    formDefaultValue.put("dataBeginDate", currentInventoryBeginDateMin);
    formDefaultValue.put("dataEndDate", currentInventoryEndDateMax);
    
    return formDefaultValue;
}

public static Criteria getCurrentInventoryYear() {

    Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
    Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
    //Integer organizationId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();

    Criteria criteria = new Criteria();
    criteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDateMin);
    criteria.addCriteria("inventoryYearEndDate", currentInventoryEndDateMax);            
    //criteria.addCriteria("organizationId", organizationId);            
    
    return criteria;
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

//--employeeBusinessTravelHotelStay
private void employeeBusinessTravelHotelStayTab() {

	//VLayout employeeBusinessTravelHotelStayLayout = new VLayout(15);
	final ListGridField[] listGridFields = getOptionalSourceListGridFields("employeeBusinessTravelHotelStay");
        //final ListGridField[] listGridFields = getBusinessTravelHotelStayListGridFields();
	employeeBusinessTravelHotelStayDataGrid.setFields(listGridFields);

	TabLayout employeeBusinessTravelHotelStayLayout = new TabLayout("Current Sources of Employee Business Travel Hotel Stay", employeeBusinessTravelHotelStayForm,
	                                                     employeeBusinessTravelHotelStayFormWindow, employeeBusinessTravelHotelStayDataGrid);
//--Defining employeeBusinessTravel By Vehicle
	final Tab employeeBusinessTravelHotelStayTab = new Tab("Employee Business Travel Hotel Stay");
	employeeBusinessTravelHotelStayTab.setPane(employeeBusinessTravelHotelStayLayout);

//---Adding employeeBusinessTravel By Vehicle tab to tabSet
	employeeBusinessTravelTabSet.addTab(employeeBusinessTravelHotelStayTab);
}
private void initEmployeeBusinessTravelHotelStayEditForm() {
    FormItem[] formItemFields = getOptionalSourceFormFields("employeeBusinessTravelHotelStay");
    //FormItem[] formItemFields = getBusinessTravelHotelStayFormFields();
    employeeBusinessTravelHotelStayForm.setItems(formItemFields);
    employeeBusinessTravelHotelStayForm.hideItem("optionalSourceType");
    //employeeBusinessTravelHotelStayForm.hideItem("activityType");
    setIbLFormWindow("Please enter employee business travel hotel stay information:", employeeBusinessTravelHotelStayForm,
                          employeeBusinessTravelHotelStayFormWindow, employeeBusinessTravelHotelStayDataGrid );
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

//purchasedProduct
private void purchasedProductTab() {

        purchasedProductLayout.setWidth100();
        purchasedProductLayout.setHeight100();

	//--Product Level Method fields		
        final ListGridField[] listGridFields_1 = getPurchasedProductListGridFields_1();
        purchasedProductDataGrid_1.setFields(listGridFields_1);

	TabLayout purchasedProductTabLayout_1 = new TabLayout("Current purchased products(Goods, Services, Capital Goods) sources", purchasedProductForm_1,
	                                                     purchasedProductFormWindow_1, purchasedProductDataGrid_1);

 	//--Supplier Specific Level Method fields		
        final ListGridField[] listGridFields_2 = getPurchasedProductListGridFields_2_0();
        purchasedProductDataGrid_2.setFields(listGridFields_2);
        
	TabLayout purchasedProductTabLayout_2 = new TabLayout("Current purchased products(Goods, Services, Capital Goods) sources", purchasedProductForm_2,
	                                                     purchasedProductFormWindow_2, purchasedProductDataGrid_2);

//---Temporary setting        
        purchasedProductFormWindow_2_1.setShowShadow(true);
        purchasedProductFormWindow_2_1.setIsModal(true);
        purchasedProductFormWindow_2_1.setMinWidth(200);
        purchasedProductFormWindow_2_1.setMinHeight(200);
        purchasedProductFormWindow_2_1.setAutoSize(Boolean.TRUE);
        purchasedProductFormWindow_2_1.setShowMinimizeButton(true);
        purchasedProductFormWindow_2_1.setShowCloseButton(true);
        purchasedProductFormWindow_2_1.setShowModalMask(true);
        //purchasedProductFormWindow_2_1.centerInPage();
        purchasedProductFormWindow_2_1.setAutoCenter(Boolean.TRUE);
        //purchasedProductFormWindow_2_1.setTitle(windowTitleString);
        //purchasedProductFormWindow_2_1.setStyleName("labels");
        //purchasedProductFormWindow_2_1.addItem(dialog);
        purchasedProductFormWindow_2_1.setDefaultLayoutAlign(Alignment.CENTER);
        purchasedProductFormWindow_2_1.setTitle("Please eneter following values");
        purchasedProductFormWindow_2_1.addItem(cEditor);  
        
        
        
        
 	//--Material or Spend-Based Calculation Method fields		
        final ListGridField[] listGridFields_3 = getPurchasedProductListGridFields_3();
        purchasedProductDataGrid_3.setFields(listGridFields_3);

	TabLayout purchasedProductTabLayout_3 = new TabLayout("Current purchased products(Goods, Services, Capital Goods) sources", purchasedProductForm_3,
	                                                     purchasedProductFormWindow_3, purchasedProductDataGrid_3);
														 
//--Defining Purchased Product tab
        final Tab purchasedProductTab_1 = new Tab("Product Level Method");
        purchasedProductTab_1.setPane(purchasedProductTabLayout_1);
        
	final Tab purchasedProductTab_2 = new Tab("Supplier Specific Level Method");		
        purchasedProductTab_2.setPane(purchasedProductTabLayout_2);
		
        final Tab purchasedProductTab_3 = new Tab("Material or Spend-Based Calculation Method");		
        purchasedProductTab_3.setPane(purchasedProductTabLayout_3);

//---Adding Purchased Product tab to tabSet
        purchasedProductTabSet.addTab(purchasedProductTab_1);
	purchasedProductTabSet.addTab(purchasedProductTab_2);
	purchasedProductTabSet.addTab(purchasedProductTab_3);
        purchasedProductLayout.addMember(purchasedProductTabSet);
}
private void initPurchasedProductEditForm() {
//-- Form_1 fields  -------------------------------------------------------------------

    FormItem[] formItemFields_1 = getPurchasedProductFormFields_1();
    purchasedProductForm_1.setItems(formItemFields_1);
    purchasedProductForm_1.setDataSource(purchasedProductInfoDS_1);
    purchasedProductForm_1.hideItem("methodType");
    
    setIbLFormWindow("Please enter purchased products(Goods, Services, Capital Goods) information:", purchasedProductForm_1,
                          purchasedProductFormWindow_1, purchasedProductDataGrid_1 );

//-- Form_2 fields  -------------------------------------------------------------------
    
    FormItem[] formItemFields_2 = getPurchasedProductFormFields_2();
    purchasedProductForm_2.setItems(formItemFields_2);
    purchasedProductForm_2.setDataSource(purchasedProductInfoDS_2);       
    purchasedProductForm_2.hideItem("methodType");
    purchasedProductForm_2.setNumCols(6);

/*
    ListGrid materialUsedBy_T1S_InfoGrid = new ListGrid();    
    materialUsedBy_T1S_InfoGrid.setID("materialUsedBy_T1S_Grid");
    materialUsedBy_T1S_InfoGrid.setFields(getPurchasedProductListGridFields_2_2());
    materialUsedBy_T1S_InfoGrid.setDataSource(materialUsedBy_T1S_InfoDS);
    materialUsedBy_T1S_InfoGrid.setCanEdit(Boolean.TRUE); 
    materialUsedBy_T1S_InfoGrid.setModalEditing(Boolean.TRUE);
    materialUsedBy_T1S_InfoGrid.setEditEvent(ListGridEditEvent.CLICK);
    materialUsedBy_T1S_InfoGrid.setListEndEditAction(RowEndEditAction.NEXT);
    materialUsedBy_T1S_InfoGrid.setCanRemoveRecords(true);  
    materialUsedBy_T1S_InfoGrid.setHeight(190);
    materialUsedBy_T1S_InfoGrid.setWidth(190);
    
    setIbLFormWindow2("Please enter purchased products(Goods, Services, Capital Goods) information:", purchasedProductForm_2,
                          purchasedProductFormWindow_2, purchasedProductDataGrid_2,materialUsedBy_T1S_InfoGrid  );          
*/
    
    setIbLFormWindow("Please enter purchased products(Goods, Services, Capital Goods) information:", purchasedProductForm_2,
                          purchasedProductFormWindow_2, purchasedProductDataGrid_2);          
    
//-- Form_3 fields  -------------------------------------------------------------------
    FormItem[] formItemFields_3 = getPurchasedProductFormFields_3();
    purchasedProductForm_3.setItems(formItemFields_3);
    purchasedProductForm_3.setDataSource(purchasedProductInfoDS_3);        
    purchasedProductForm_3.hideItem("methodType");
    
    setIbLFormWindow("Please enter purchased products(Goods, Services, Capital Goods) information:", purchasedProductForm_3,
                          purchasedProductFormWindow_3, purchasedProductDataGrid_3 );
						  
 }

//purchasedEnergy
private void purchasedEnergyInfoTab() {

        purchasedEnergyLayout.setWidth100();
        purchasedEnergyLayout.setHeight100();
/*
	//--Energy Level Method fields		
        final ListGridField[] listGridFields = getPurchasedEnergyListGridFields();
        purchasedEnergyDataGrid.setFields(listGridFields);

      	TabLayout purchasedEnergyTabLayout = new TabLayout("Fuel and Energy Related Activities Not Included in Scope 1 and 2", purchasedEnergyForm,
	                                                     purchasedEnergyFormWindow, purchasedEnergyDataGrid);
											 
        final Tab purchasedEnergyTab = new Tab("Fuel and Energy Related Activities Included in Scope 1 and 2");
        purchasedEnergyTab.setPane(purchasedEnergyTabLayout);

//---Adding tab to topTab
        purchasedEnergyTabSet.addTab(purchasedEnergyTab);                 
        purchasedEnergyLayout.addMember(purchasedEnergyTabSet);
*/
        
	//--Upstream Emissions Of Purchased Fuels
        final ListGridField[] listGridFields_1 = getPurchasedEnergyListGridFields_1();
        purchasedEnergyDataGrid_1.setFields(listGridFields_1);

	TabLayout purchasedEnergyTabLayout_1 = new TabLayout("Upstream Emissions Of Purchased Fuels", purchasedEnergyForm_1,
	                                                     purchasedEnergyFormWindow_1, purchasedEnergyDataGrid_1);

 	//--Upstream Emissions Of Purchased Energy
        final ListGridField[] listGridFields_2 = getPurchasedEnergyListGridFields_2();
        purchasedEnergyDataGrid_2.setFields(listGridFields_2);
        
	TabLayout purchasedEnergyTabLayout_2 = new TabLayout("Upstream Emissions Of Purchased Energy", purchasedEnergyForm_2,
	                                                     purchasedEnergyFormWindow_2, purchasedEnergyDataGrid_2);

 	//--Transmission & Distribution Losses
        final ListGridField[] listGridFields_3 = getPurchasedEnergyListGridFields_3();
        purchasedEnergyDataGrid_3.setFields(listGridFields_3);

	TabLayout purchasedEnergyTabLayout_3 = new TabLayout("Transmission & Distribution Losses", purchasedEnergyForm_3,
	                                                     purchasedEnergyFormWindow_3, purchasedEnergyDataGrid_3);

 	//--Generation Of Purchased Electricity That Is Sold To End Users
        final ListGridField[] listGridFields_4 = getPurchasedEnergyListGridFields_4();
        purchasedEnergyDataGrid_4.setFields(listGridFields_4);

	TabLayout purchasedEnergyTabLayout_4 = new TabLayout("Generation Of Purchased Energy That Is Sold To End Users", purchasedEnergyForm_4,
	                                                     purchasedEnergyFormWindow_4, purchasedEnergyDataGrid_4);        
//--Defining Purchased Energy tab
        final Tab purchasedEnergyTab_1 = new Tab("Upstream Emissions Of Purchased Fuels");
        purchasedEnergyTab_1.setPane(purchasedEnergyTabLayout_1);
        
	final Tab purchasedEnergyTab_2 = new Tab("Upstream Emissions Of Purchased Energy");		
        purchasedEnergyTab_2.setPane(purchasedEnergyTabLayout_2);
		
        final Tab purchasedEnergyTab_3 = new Tab("Transmission & Distribution Losses");		
        purchasedEnergyTab_3.setPane(purchasedEnergyTabLayout_3);

        final Tab purchasedEnergyTab_4 = new Tab("Generation Of Purchased Energy That Is Sold To End Users");
        purchasedEnergyTab_4.setPane(purchasedEnergyTabLayout_4);
        
        
//---Adding Purchased Product tab to tabSet
        purchasedEnergyTabSet.addTab(purchasedEnergyTab_1);
	purchasedEnergyTabSet.addTab(purchasedEnergyTab_2);
	purchasedEnergyTabSet.addTab(purchasedEnergyTab_3);
        purchasedEnergyTabSet.addTab(purchasedEnergyTab_4);        
        purchasedEnergyLayout.addMember(purchasedEnergyTabSet);
        
}
private void initPurchasedEnergyEditForm() {
/*
    FormItem[] formItemFields = getPurchasedEnergyFormFields();
    purchasedEnergyForm.setItems(formItemFields);
    purchasedEnergyForm.setDataSource(purchasedEnergyInfoDS);
    purchasedEnergyForm.hideItem("methodType");
    
    setIbLFormWindow("Please enter Fuel and Energy Related Activities Not Included in Scope 1 and 2:", purchasedEnergyForm,
                          purchasedEnergyFormWindow, purchasedEnergyDataGrid );

*/

    FormItem[] formItemFields_1 = getPurchasedEnergyFormFields_1();
    purchasedEnergyForm_1.setItems(formItemFields_1);
    purchasedEnergyForm_1.setDataSource(purchasedEnergyInfoDS_1);
    purchasedEnergyForm_1.hideItem("methodType");
    purchasedEnergyForm_1.hideItem("activityType");
    
    setIbLFormWindow("Please enter purchased fuel information:", purchasedEnergyForm_1,
                          purchasedEnergyFormWindow_1, purchasedEnergyDataGrid_1 );

    FormItem[] formItemFields_2 = getPurchasedEnergyFormFields_2();
    purchasedEnergyForm_2.setItems(formItemFields_2);
    purchasedEnergyForm_2.setDataSource(purchasedEnergyInfoDS_2);
    purchasedEnergyForm_2.hideItem("methodType");
    purchasedEnergyForm_2.hideItem("activityType");
    
    setIbLFormWindow("Please enter purchased energy information:", purchasedEnergyForm_2,
                          purchasedEnergyFormWindow_2, purchasedEnergyDataGrid_2 );

    FormItem[] formItemFields_3 = getPurchasedEnergyFormFields_3();
    purchasedEnergyForm_3.setItems(formItemFields_3);
    purchasedEnergyForm_3.setDataSource(purchasedEnergyInfoDS_3);
    purchasedEnergyForm_3.hideItem("methodType");
    purchasedEnergyForm_3.hideItem("activityType");
    
    setIbLFormWindow("Please enter transmission and distribution loss information:", purchasedEnergyForm_3,
                          purchasedEnergyFormWindow_3, purchasedEnergyDataGrid_3 );


    FormItem[] formItemFields_4 = getPurchasedEnergyFormFields_4();
    purchasedEnergyForm_4.setItems(formItemFields_4);
    purchasedEnergyForm_4.setDataSource(purchasedEnergyInfoDS_4);
    purchasedEnergyForm_4.hideItem("methodType");
    purchasedEnergyForm_4.hideItem("activityType");
    
    setIbLFormWindow("Please enter purchased energy that is sold to end users:", purchasedEnergyForm_4,
                          purchasedEnergyFormWindow_4, purchasedEnergyDataGrid_4 );

    
 }

//transportationInfo
private void transportationInfoTab() {

	transportationLayout.setWidth100();
	transportationLayout.setHeight100();
        
	//--Upstream Emissions Of Purchased Fuels
	final ListGridField[] listGridFields_1 = getTransportationListGridFields_1();
	transportationDataGrid_1.setFields(listGridFields_1);

	TabLayout transportationTabLayout_1 = new TabLayout("Fuel Based Method - Transportation Emissions", transportationForm_1,
	                                                     transportationFormWindow_1, transportationDataGrid_1);

 	//--Upstream Emissions Of Transportation
	final ListGridField[] listGridFields_2 = getTransportationListGridFields_2();
	transportationDataGrid_2.setFields(listGridFields_2);
        
	TabLayout transportationTabLayout_2 = new TabLayout("Distance Based Method - Transportation Emissions", transportationForm_2,
	                                                     transportationFormWindow_2, transportationDataGrid_2);

//--Defining Trasnportation tab
	final Tab transportationTab_1 = new Tab("Fuel Based Method");
	transportationTab_1.setPane(transportationTabLayout_1);
        
	final Tab transportationTab_2 = new Tab("Distance Based Method");		
	transportationTab_2.setPane(transportationTabLayout_2);		        
        
//---Adding Trasnportation tab to tabSet
	transportationTabSet.addTab(transportationTab_1);
	transportationTabSet.addTab(transportationTab_2);
	transportationLayout.addMember(transportationTabSet);
        
}
private void initTransportationEditForm() {

    FormItem[] formItemFields_1 = getTransportationFormFields_1();
    transportationForm_1.setItems(formItemFields_1);
    transportationForm_1.setDataSource(transportationInfoDS_1);
    transportationForm_1.hideItem("methodType");
    transportationForm_1.setNumCols(6);
    //transportationForm_1.hideItem("streamType");
    
    setIbLFormWindow("Please enter transportation information:", transportationForm_1,
                          transportationFormWindow_1, transportationDataGrid_1 );

    FormItem[] formItemFields_2 = getTransportationFormFields_2();
    transportationForm_2.setItems(formItemFields_2);
    transportationForm_2.setDataSource(transportationInfoDS_2);
    transportationForm_2.hideItem("methodType");
    //transportationForm_2.hideItem("streamType");
    
    setIbLFormWindow("Please enter transportation information:", transportationForm_2,
                          transportationFormWindow_2, transportationDataGrid_2 );
    
 }

//distributionInfo
private void distributionInfoTab() {

	distributionLayout.setWidth100();
	distributionLayout.setHeight100();
        
	//--Upstream Emissions Of Purchased Fuels
	final ListGridField[] listGridFields_1 = getDistributionListGridFields_1();
	distributionDataGrid_1.setFields(listGridFields_1);

	TabLayout distributionTabLayout_1 = new TabLayout("Site Specific Method - Distribution Emissions", distributionForm_1,
	                                                     distributionFormWindow_1, distributionDataGrid_1);

 	//--Upstream Emissions Of Distribution
	final ListGridField[] listGridFields_2 = getDistributionListGridFields_2();
	distributionDataGrid_2.setFields(listGridFields_2);
        
	TabLayout distributionTabLayout_2 = new TabLayout("Average Data Method - Distribution Emissions", distributionForm_2,
	                                                     distributionFormWindow_2, distributionDataGrid_2);

//--Defining Trasnportation tab
	final Tab distributionTab_1 = new Tab("Site Specific Method");
	distributionTab_1.setPane(distributionTabLayout_1);
        
	final Tab distributionTab_2 = new Tab("Average Data Method");		
	distributionTab_2.setPane(distributionTabLayout_2);		        
        
//---Adding Trasnportation tab to tabSet
	distributionTabSet.addTab(distributionTab_1);
	distributionTabSet.addTab(distributionTab_2);
	distributionLayout.addMember(distributionTabSet);
        
}
private void initDistributionEditForm() {

    FormItem[] formItemFields_1 = getDistributionFormFields_1();
    distributionForm_1.setItems(formItemFields_1);
    distributionForm_1.setDataSource(distributionInfoDS_1);
    distributionForm_1.hideItem("methodType");
    //distributionForm_1.hideItem("streamType");
    //distributionForm_1.setNumCols(8);
    
    setIbLFormWindow("Please enter distribution information:", distributionForm_1,
                          distributionFormWindow_1, distributionDataGrid_1 );

    FormItem[] formItemFields_2 = getDistributionFormFields_2();
    distributionForm_2.setItems(formItemFields_2);
    distributionForm_2.setDataSource(distributionInfoDS_2);
    distributionForm_2.hideItem("methodType");
    //distributionForm_2.hideItem("streamType");
    
    setIbLFormWindow("Please enter distribution information:", distributionForm_2,
                          distributionFormWindow_2, distributionDataGrid_2 );
    
 }

//wasteGeneratedInOperationsInfo
private void wasteGeneratedInOperationsInfoTab() {

	wasteGeneratedInOperationsLayout.setWidth100();
	wasteGeneratedInOperationsLayout.setHeight100();
        
	//--Waste Type Specific Method - Waste Generated In Operations
	final ListGridField[] listGridFields_1 = getWasteGeneratedInOperationsListGridFields_1();
	wasteGeneratedInOperationsDataGrid_1.setFields(listGridFields_1);

	TabLayout wasteGeneratedInOperationsTabLayout_1 = new TabLayout("Waste Type Specific Method - Waste Generated In Operations", wasteGeneratedInOperationsForm_1,
	                                                     wasteGeneratedInOperationsFormWindow_1, wasteGeneratedInOperationsDataGrid_1);

 	//--Average Data Method - Waste Generated In Operations
	final ListGridField[] listGridFields_2 = getWasteGeneratedInOperationsListGridFields_2();
	wasteGeneratedInOperationsDataGrid_2.setFields(listGridFields_2);
        
	TabLayout wasteGeneratedInOperationsTabLayout_2 = new TabLayout("Average Data Method - Waste Generated In Operations", wasteGeneratedInOperationsForm_2,
	                                                     wasteGeneratedInOperationsFormWindow_2, wasteGeneratedInOperationsDataGrid_2);

//--Defining wasteGeneratedInOperations tab
	final Tab wasteGeneratedInOperationsTab_1 = new Tab("Site Specific Method");
	wasteGeneratedInOperationsTab_1.setPane(wasteGeneratedInOperationsTabLayout_1);
        
	final Tab wasteGeneratedInOperationsTab_2 = new Tab("Average Data Method");		
	wasteGeneratedInOperationsTab_2.setPane(wasteGeneratedInOperationsTabLayout_2);		        
        
//---Adding wasteGeneratedInOperations tab to tabSet
	wasteGeneratedInOperationsTabSet.addTab(wasteGeneratedInOperationsTab_1);
	wasteGeneratedInOperationsTabSet.addTab(wasteGeneratedInOperationsTab_2);
	wasteGeneratedInOperationsLayout.addMember(wasteGeneratedInOperationsTabSet);
        
}
private void initWasteGeneratedInOperationsEditForm() {

    FormItem[] formItemFields_1 = getWasteGeneratedInOperationsFormFields_1();
    wasteGeneratedInOperationsForm_1.setItems(formItemFields_1);
    wasteGeneratedInOperationsForm_1.setDataSource(wasteGeneratedInOperationsInfoDS_1);
    wasteGeneratedInOperationsForm_1.hideItem("methodType");
    //wasteGeneratedInOperationsForm_1.hideItem("streamType");
    //wasteGeneratedInOperationsForm_1.setNumCols(8);
    
    setIbLFormWindow("Please enter Waste Generated In Operations information:", wasteGeneratedInOperationsForm_1,
                          wasteGeneratedInOperationsFormWindow_1, wasteGeneratedInOperationsDataGrid_1 );

    FormItem[] formItemFields_2 = getWasteGeneratedInOperationsFormFields_2();
    wasteGeneratedInOperationsForm_2.setItems(formItemFields_2);
    wasteGeneratedInOperationsForm_2.setDataSource(wasteGeneratedInOperationsInfoDS_2);
    wasteGeneratedInOperationsForm_2.hideItem("methodType");
    //wasteGeneratedInOperationsForm_2.hideItem("streamType");
    
    setIbLFormWindow("Please enter WasteGenerated In Operations information:", wasteGeneratedInOperationsForm_2,
                          wasteGeneratedInOperationsFormWindow_2, wasteGeneratedInOperationsDataGrid_2 );
    
 }

//businessTravelInfo
private void businessTravelInfoTab() {

	businessTravelLayout.setWidth100();
	businessTravelLayout.setHeight100();
        
	//--Distance Based Method
	final ListGridField[] listGridFields = getBusinessTravelListGridFields();
	businessTravelDataGrid.setFields(listGridFields);

	TabLayout businessTravelTabLayout = new TabLayout("Business Travel", businessTravelForm,
	                                                     businessTravelFormWindow, businessTravelDataGrid);

//--Defining businessTravel tab
	final Tab businessTravelTab = new Tab("Distance Based Method");
	businessTravelTab.setPane(businessTravelTabLayout);
        
        
//---Adding businessTravel tab to tabSet
	businessTravelTabSet.addTab(businessTravelTab);
	businessTravelLayout.addMember(businessTravelTabSet);
        
}
private void initBusinessTravelEditForm() {

    FormItem[] formItemFields = getBusinessTravelFormFields();
    businessTravelForm.setItems(formItemFields);
    businessTravelForm.setDataSource(businessTravelInfoDS);
    businessTravelForm.hideItem("methodType");
    
    setIbLFormWindow("Please enter business travel information:", businessTravelForm,
                          businessTravelFormWindow, businessTravelDataGrid );
    
 }

//leasedAssetsInfo
private void leasedAssetsInfoTab() {

	leasedAssetsLayout.setWidth100();
	leasedAssetsLayout.setHeight100();
        
	//--Upstream Emissions Of Leased Assets 
	final ListGridField[] listGridFields_1 = getLeasedAssetsListGridFields_1();
	leasedAssetsDataGrid_1.setFields(listGridFields_1);

	TabLayout leasedAssetsTabLayout_1 = new TabLayout("Site Specific Method - Leased Assets Emissions", leasedAssetsForm_1,
	                                                     leasedAssetsFormWindow_1, leasedAssetsDataGrid_1);

 	//--Upstream Emissions Of Leased Assets
	final ListGridField[] listGridFields_2 = getLeasedAssetsListGridFields_2();
	leasedAssetsDataGrid_2.setFields(listGridFields_2);
        
	TabLayout leasedAssetsTabLayout_2 = new TabLayout("Average Data Method - Leased Assets Emissions", leasedAssetsForm_2,
	                                                     leasedAssetsFormWindow_2, leasedAssetsDataGrid_2);

//--Defining Leased Assets tab
	final Tab leasedAssetsTab_1 = new Tab("Site Specific Method");
	leasedAssetsTab_1.setPane(leasedAssetsTabLayout_1);
        
	final Tab leasedAssetsTab_2 = new Tab("Average Data Method");		
	leasedAssetsTab_2.setPane(leasedAssetsTabLayout_2);		        
        
//---Adding Leased Assets tab to tabSet
	leasedAssetsTabSet.addTab(leasedAssetsTab_1);
	leasedAssetsTabSet.addTab(leasedAssetsTab_2);
	leasedAssetsLayout.addMember(leasedAssetsTabSet);
        
}
private void initLeasedAssetsEditForm() {

    FormItem[] formItemFields_1 = getLeasedAssetsFormFields_1();
    leasedAssetsForm_1.setItems(formItemFields_1);
    leasedAssetsForm_1.setDataSource(leasedAssetsInfoDS_1);
    leasedAssetsForm_1.hideItem("methodType");
    //leasedAssetsForm_1.hideItem("streamType");
    //leasedAssetsForm_1.setNumCols(8);
    
    setIbLFormWindow("Please enter Leased Assets information:", leasedAssetsForm_1,
                          leasedAssetsFormWindow_1, leasedAssetsDataGrid_1 );

    FormItem[] formItemFields_2 = getLeasedAssetsFormFields_2();
    leasedAssetsForm_2.setItems(formItemFields_2);
    leasedAssetsForm_2.setDataSource(leasedAssetsInfoDS_2);
    leasedAssetsForm_2.hideItem("methodType");
    //leasedAssetsForm_2.hideItem("streamType");
    
    setIbLFormWindow("Please enter Leased Assets information:", leasedAssetsForm_2,
                          leasedAssetsFormWindow_2, leasedAssetsDataGrid_2 );
    
 }

//processingOfSoldProductsInfo
private void processingOfSoldProductsInfoTab() {

	processingOfSoldProductsLayout.setWidth100();
	processingOfSoldProductsLayout.setHeight100();
        
	//--Downstream Emissions Of Purchased Fuels
	final ListGridField[] listGridFields_1 = getProcessingOfSoldProductsListGridFields_1();
	processingOfSoldProductsDataGrid_1.setFields(listGridFields_1);

	TabLayout processingOfSoldProductsTabLayout_1 = new TabLayout("Site Specific Method - Processing Of Sold Products Emissions", processingOfSoldProductsForm_1,
	                                                     processingOfSoldProductsFormWindow_1, processingOfSoldProductsDataGrid_1);

 	//--Downstream Emissions Of Processing Of Sold Products
	final ListGridField[] listGridFields_2 = getProcessingOfSoldProductsListGridFields_2();
	processingOfSoldProductsDataGrid_2.setFields(listGridFields_2);
        
	TabLayout processingOfSoldProductsTabLayout_2 = new TabLayout("Average Data Method - Processing Of Sold Products Emissions", processingOfSoldProductsForm_2,
	                                                     processingOfSoldProductsFormWindow_2, processingOfSoldProductsDataGrid_2);

//--Defining Processing Of Sold Products tab
	final Tab processingOfSoldProductsTab_1 = new Tab("Site Specific Method");
	processingOfSoldProductsTab_1.setPane(processingOfSoldProductsTabLayout_1);
        
	final Tab processingOfSoldProductsTab_2 = new Tab("Average Data Method");		
	processingOfSoldProductsTab_2.setPane(processingOfSoldProductsTabLayout_2);		        
        
//---Adding Processing Of Sold Products tab to tabSet
	processingOfSoldProductsTabSet.addTab(processingOfSoldProductsTab_1);
	processingOfSoldProductsTabSet.addTab(processingOfSoldProductsTab_2);
	processingOfSoldProductsLayout.addMember(processingOfSoldProductsTabSet);
        
}
private void initProcessingOfSoldProductsEditForm() {

    FormItem[] formItemFields_1 = getProcessingOfSoldProductsFormFields_1();
    processingOfSoldProductsForm_1.setItems(formItemFields_1);
    processingOfSoldProductsForm_1.setDataSource(processingOfSoldProductsInfoDS_1);
    processingOfSoldProductsForm_1.hideItem("methodType");
    //processingOfSoldProductsForm_1.hideItem("streamType");
    //processingOfSoldProductsForm_1.setNumCols(8);
    
    setIbLFormWindow("Please enter processing of sold products information:", processingOfSoldProductsForm_1,
                          processingOfSoldProductsFormWindow_1, processingOfSoldProductsDataGrid_1 );

    FormItem[] formItemFields_2 = getProcessingOfSoldProductsFormFields_2();
    processingOfSoldProductsForm_2.setItems(formItemFields_2);
    processingOfSoldProductsForm_2.setDataSource(processingOfSoldProductsInfoDS_2);
    processingOfSoldProductsForm_2.hideItem("methodType");
    //processingOfSoldProductsForm_2.hideItem("streamType");
    
    setIbLFormWindow("Please enter processing of sold products information:", processingOfSoldProductsForm_2,
                          processingOfSoldProductsFormWindow_2, processingOfSoldProductsDataGrid_2 );
    
 }

//directUsePhaseEmissions
private void directUsePhaseEmissionsInfoTab() {

        directUsePhaseEmissionsLayout.setWidth100();
        directUsePhaseEmissionsLayout.setHeight100();
        
	//--Upstream Emissions Of Purchased Fuels
        final ListGridField[] listGridFields_1 = getDirectUsePhaseEmissionsListGridFields_1();
        directUsePhaseEmissionsDataGrid_1.setFields(listGridFields_1);

	TabLayout directUsePhaseEmissionsTabLayout_1 = new TabLayout("Lifetime Uses Method – Energy Using Products", directUsePhaseEmissionsForm_1,
	                                                     directUsePhaseEmissionsFormWindow_1, directUsePhaseEmissionsDataGrid_1);

 	//--Upstream Emissions Of Purchased Energy
        final ListGridField[] listGridFields_2 = getDirectUsePhaseEmissionsListGridFields_2();
        directUsePhaseEmissionsDataGrid_2.setFields(listGridFields_2);
        
	TabLayout directUsePhaseEmissionsTabLayout_2 = new TabLayout("Combustion Method - Direct Use Phase Emissions – Fuels and Feedstocks", directUsePhaseEmissionsForm_2,
	                                                     directUsePhaseEmissionsFormWindow_2, directUsePhaseEmissionsDataGrid_2);

 	//--Transmission & Distribution Losses
        final ListGridField[] listGridFields_3 = getDirectUsePhaseEmissionsListGridFields_3();
        directUsePhaseEmissionsDataGrid_3.setFields(listGridFields_3);

	TabLayout directUsePhaseEmissionsTabLayout_3 = new TabLayout("GHG released Method - Product Containing GHGs that are Emitted During Use", directUsePhaseEmissionsForm_3,
	                                                     directUsePhaseEmissionsFormWindow_3, directUsePhaseEmissionsDataGrid_3);
//--Defining Purchased Energy tab
        final Tab directUsePhaseEmissionsTab_1 = new Tab("Energy Using Products");
        directUsePhaseEmissionsTab_1.setPane(directUsePhaseEmissionsTabLayout_1);
        
	final Tab directUsePhaseEmissionsTab_2 = new Tab("Fuels and Feedstocks");		
        directUsePhaseEmissionsTab_2.setPane(directUsePhaseEmissionsTabLayout_2);
		
        final Tab directUsePhaseEmissionsTab_3 = new Tab("Product Containing GHGs that are Emitted During Use");		
        directUsePhaseEmissionsTab_3.setPane(directUsePhaseEmissionsTabLayout_3);
                
//---Adding Purchased Product tab to tabSet
        directUsePhaseEmissionsTabSet.addTab(directUsePhaseEmissionsTab_1);
		directUsePhaseEmissionsTabSet.addTab(directUsePhaseEmissionsTab_2);
		directUsePhaseEmissionsTabSet.addTab(directUsePhaseEmissionsTab_3);        
        directUsePhaseEmissionsLayout.addMember(directUsePhaseEmissionsTabSet);
        
}
private void initDirectUsePhaseEmissionsEditForm() {

    FormItem[] formItemFields_1 = getDirectUsePhaseEmissionsFormFields_1();
    directUsePhaseEmissionsForm_1.setItems(formItemFields_1);
    directUsePhaseEmissionsForm_1.setDataSource(directUsePhaseEmissionsInfoDS_1);
    directUsePhaseEmissionsForm_1.hideItem("methodType");
    //directUsePhaseEmissionsForm_1.hideItem("activityType");
    //directUsePhaseEmissionsForm_1.setNumCols(6);
    setIbLFormWindow("Please enter direct use phase emissions for sold products information:", directUsePhaseEmissionsForm_1,
                          directUsePhaseEmissionsFormWindow_1, directUsePhaseEmissionsDataGrid_1 );

    FormItem[] formItemFields_2 = getDirectUsePhaseEmissionsFormFields_2();
    directUsePhaseEmissionsForm_2.setItems(formItemFields_2);
    directUsePhaseEmissionsForm_2.setDataSource(directUsePhaseEmissionsInfoDS_2);
    directUsePhaseEmissionsForm_2.hideItem("methodType");
    //directUsePhaseEmissionsForm_2.hideItem("activityType");
    
    setIbLFormWindow("Please enter direct use phase emissions for sold products information:", directUsePhaseEmissionsForm_2,
                          directUsePhaseEmissionsFormWindow_2, directUsePhaseEmissionsDataGrid_2 );

    FormItem[] formItemFields_3 = getDirectUsePhaseEmissionsFormFields_3();
    directUsePhaseEmissionsForm_3.setItems(formItemFields_3);
    directUsePhaseEmissionsForm_3.setDataSource(directUsePhaseEmissionsInfoDS_3);
    directUsePhaseEmissionsForm_3.hideItem("methodType");
    //directUsePhaseEmissionsForm_3.hideItem("activityType");
    
    setIbLFormWindow("Please enter direct use phase emissions for sold products information:", directUsePhaseEmissionsForm_3,
                          directUsePhaseEmissionsFormWindow_3, directUsePhaseEmissionsDataGrid_3 );

    
 }

//inDirectUsePhaseEmissions
private void inDirectUsePhaseEmissionsInfoTab() {

        inDirectUsePhaseEmissionsLayout.setWidth100();
        inDirectUsePhaseEmissionsLayout.setHeight100();
        
	//--Typical Use Phase Profile Method - In-Direct Use Phase Emissions
        final ListGridField[] listGridFields_1 = getInDirectUsePhaseEmissionsListGridFields_1();
        inDirectUsePhaseEmissionsDataGrid_1.setFields(listGridFields_1);

	TabLayout inDirectUsePhaseEmissionsTabLayout_1 = new TabLayout("Typical Use Phase Profile Method", inDirectUsePhaseEmissionsForm_1,
	                                                     inDirectUsePhaseEmissionsFormWindow_1, inDirectUsePhaseEmissionsDataGrid_1);

 	//--Functional Unit Method - Emissions Intensity Metrics - In-Direct Use Phase Emissions
        final ListGridField[] listGridFields_2 = getInDirectUsePhaseEmissionsListGridFields_2();
        inDirectUsePhaseEmissionsDataGrid_2.setFields(listGridFields_2);
        
	TabLayout inDirectUsePhaseEmissionsTabLayout_2 = new TabLayout("Functional Unit Method - Emissions Intensity Metrics", inDirectUsePhaseEmissionsForm_2,
	                                                     inDirectUsePhaseEmissionsFormWindow_2, inDirectUsePhaseEmissionsDataGrid_2);

//--Defining  tab
        final Tab inDirectUsePhaseEmissionsTab_1 = new Tab("Typical Use Phase Profile Method");
        inDirectUsePhaseEmissionsTab_1.setPane(inDirectUsePhaseEmissionsTabLayout_1);
        
	final Tab inDirectUsePhaseEmissionsTab_2 = new Tab("Functional Unit Method - Emissions Intensity Metrics");		
        inDirectUsePhaseEmissionsTab_2.setPane(inDirectUsePhaseEmissionsTabLayout_2);
		
                
//---Adding Purchased Product tab to tabSet
        inDirectUsePhaseEmissionsTabSet.addTab(inDirectUsePhaseEmissionsTab_1);
		inDirectUsePhaseEmissionsTabSet.addTab(inDirectUsePhaseEmissionsTab_2);
        inDirectUsePhaseEmissionsLayout.addMember(inDirectUsePhaseEmissionsTabSet);
        
}
private void initInDirectUsePhaseEmissionsEditForm() {

    FormItem[] formItemFields_1 = getInDirectUsePhaseEmissionsFormFields_1();
    inDirectUsePhaseEmissionsForm_1.setItems(formItemFields_1);
    inDirectUsePhaseEmissionsForm_1.setDataSource(inDirectUsePhaseEmissionsInfoDS_1);
    inDirectUsePhaseEmissionsForm_1.hideItem("methodType");
    inDirectUsePhaseEmissionsForm_1.hideItem("activityType");
    
    setIbLFormWindow("Please enter in-direct use phase emissions for sold products information:", inDirectUsePhaseEmissionsForm_1,
                          inDirectUsePhaseEmissionsFormWindow_1, inDirectUsePhaseEmissionsDataGrid_1 );

    FormItem[] formItemFields_2 = getInDirectUsePhaseEmissionsFormFields_2();
    inDirectUsePhaseEmissionsForm_2.setItems(formItemFields_2);
    inDirectUsePhaseEmissionsForm_2.setDataSource(inDirectUsePhaseEmissionsInfoDS_2);
    inDirectUsePhaseEmissionsForm_2.hideItem("methodType");
    inDirectUsePhaseEmissionsForm_2.hideItem("activityType");
    
    setIbLFormWindow("Please enter in-direct use phase emissions for sold products information:", inDirectUsePhaseEmissionsForm_2,
                          inDirectUsePhaseEmissionsFormWindow_2, inDirectUsePhaseEmissionsDataGrid_2 );


    
 }


//endOfLifeTreatmentOfSoldProducts
private void endOfLifeTreatmentOfSoldProductsInfoTab() {

        endOfLifeTreatmentOfSoldProductsLayout.setWidth100();
        endOfLifeTreatmentOfSoldProductsLayout.setHeight100();
        
	//--Typical Use Phase Profile Method - In-Direct Use Phase Emissions
        final ListGridField[] listGridFields = getEndOfLifeTreatmentOfSoldProductsListGridFields();
        endOfLifeTreatmentOfSoldProductsDataGrid.setFields(listGridFields);

	TabLayout endOfLifeTreatmentOfSoldProductsTabLayout = new TabLayout("End Of Life Treatment Of Sold Products", endOfLifeTreatmentOfSoldProductsForm,
	                                                     endOfLifeTreatmentOfSoldProductsFormWindow, endOfLifeTreatmentOfSoldProductsDataGrid);

//--Defining  tab
        final Tab endOfLifeTreatmentOfSoldProductsTab = new Tab("Calculation Method");
        endOfLifeTreatmentOfSoldProductsTab.setPane(endOfLifeTreatmentOfSoldProductsTabLayout);
        		                
//---Adding  Product tab to tabSet
        endOfLifeTreatmentOfSoldProductsTabSet.addTab(endOfLifeTreatmentOfSoldProductsTab);
        endOfLifeTreatmentOfSoldProductsLayout.addMember(endOfLifeTreatmentOfSoldProductsTabSet);
        
}
private void initEndOfLifeTreatmentOfSoldProductsEditForm() {

    FormItem[] formItemFields = getEndOfLifeTreatmentOfSoldProductsFormFields();
    endOfLifeTreatmentOfSoldProductsForm.setItems(formItemFields);
    endOfLifeTreatmentOfSoldProductsForm.setDataSource(endOfLifeTreatmentOfSoldProductsInfoDS);
    endOfLifeTreatmentOfSoldProductsForm.hideItem("methodType");
    
    setIbLFormWindow("Please enter end of life treatment of sold products information:", endOfLifeTreatmentOfSoldProductsForm,
                          endOfLifeTreatmentOfSoldProductsFormWindow, endOfLifeTreatmentOfSoldProductsDataGrid );
    
    
 }

//franchisesInfo
private void franchisesInfoTab() {

	franchisesLayout.setWidth100();
	franchisesLayout.setHeight100();
        
	//--
	final ListGridField[] listGridFields_1 = getFranchisesListGridFields_1();
	franchisesDataGrid_1.setFields(listGridFields_1);

	TabLayout franchisesTabLayout_1 = new TabLayout("Franchise Specific Method", franchisesForm_1,
	                                                     franchisesFormWindow_1, franchisesDataGrid_1);

 	//--
	final ListGridField[] listGridFields_2 = getFranchisesListGridFields_2();
	franchisesDataGrid_2.setFields(listGridFields_2);
        
	TabLayout franchisesTabLayout_2 = new TabLayout("Average Data Method", franchisesForm_2,
	                                                     franchisesFormWindow_2, franchisesDataGrid_2);

//--Defining  tab
	final Tab franchisesTab_1 = new Tab("Franchise Specific Method");
	franchisesTab_1.setPane(franchisesTabLayout_1);
        
	final Tab franchisesTab_2 = new Tab("Average Data Method");		
	franchisesTab_2.setPane(franchisesTabLayout_2);		        
        
//---Adding  tab to tabSet
	franchisesTabSet.addTab(franchisesTab_1);
	franchisesTabSet.addTab(franchisesTab_2);
	franchisesLayout.addMember(franchisesTabSet);
        
}
private void initFranchisesEditForm() {

    FormItem[] formItemFields_1 = getFranchisesFormFields_1();
    franchisesForm_1.setItems(formItemFields_1);
    franchisesForm_1.setDataSource(franchisesInfoDS_1);
    franchisesForm_1.hideItem("methodType");
    
    setIbLFormWindow("Please enter franchise information:", franchisesForm_1,
                          franchisesFormWindow_1, franchisesDataGrid_1 );

    FormItem[] formItemFields_2 = getFranchisesFormFields_2();
    franchisesForm_2.setItems(formItemFields_2);
    franchisesForm_2.setDataSource(franchisesInfoDS_2);
    franchisesForm_2.hideItem("methodType");
    
    setIbLFormWindow("Please enter franchise information:", franchisesForm_2,
                          franchisesFormWindow_2, franchisesDataGrid_2 );
    
 }

//investmentsInfo
private void investmentsInfoTab() {

	investmentsLayout.setWidth100();
	investmentsLayout.setHeight100();
        
	//--
	final ListGridField[] listGridFields_1 = getInvestmentsListGridFields_1();
	investmentsDataGrid_1.setFields(listGridFields_1);

	TabLayout investmentsTabLayout_1 = new TabLayout("Investment Specific Method", investmentsForm_1,
	                                                     investmentsFormWindow_1, investmentsDataGrid_1);

 	//--
	final ListGridField[] listGridFields_2 = getInvestmentsListGridFields_2();
	investmentsDataGrid_2.setFields(listGridFields_2);
        
	TabLayout investmentsTabLayout_2 = new TabLayout("Economic Data Method", investmentsForm_2,
	                                                     investmentsFormWindow_2, investmentsDataGrid_2);

//--Defining  tab
	final Tab investmentsTab_1 = new Tab("Investments Specific Method");
	investmentsTab_1.setPane(investmentsTabLayout_1);
        
	final Tab investmentsTab_2 = new Tab("Economic Data Method");		
	investmentsTab_2.setPane(investmentsTabLayout_2);		        
        
//---Adding  tab to tabSet
	investmentsTabSet.addTab(investmentsTab_1);
	investmentsTabSet.addTab(investmentsTab_2);
	investmentsLayout.addMember(investmentsTabSet);
        
}
private void initInvestmentsEditForm() {

    FormItem[] formItemFields_1 = getInvestmentsFormFields_1();
    investmentsForm_1.setItems(formItemFields_1);
    investmentsForm_1.setDataSource(investmentsInfoDS_1);
    investmentsForm_1.hideItem("methodType");
    
    setIbLFormWindow("Please enter investment information:", investmentsForm_1,
                          investmentsFormWindow_1, investmentsDataGrid_1 );

    FormItem[] formItemFields_2 = getInvestmentsFormFields_2();
    investmentsForm_2.setItems(formItemFields_2);
    investmentsForm_2.setDataSource(investmentsInfoDS_2);
    investmentsForm_2.hideItem("methodType");
    
    setIbLFormWindow("Please enter investment information:", investmentsForm_2,
                          investmentsFormWindow_2, investmentsDataGrid_2 );
    
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

    SelectItem consolidationApproach= new SelectItem("consolidationApproach");
    consolidationApproach.setValueMap("Equity share", "Financial control", "Operational control");
    consolidationApproach.setDefaultValue("Equity share");
    
    //organizationProfileForm.setIsGroup(Boolean.TRUE);
    //organizationProfileForm.setGroupTitle("Update your organization profile");
    //organizationProfileForm.setRedrawOnResize(true);

    organizationProfileForm.setItems(organizationNameItem, 
                                        organizationStreetAddress1Item,
                                        organizationCityItem,organizationStateItem,organizationZipCodeItem,
                                        organizationCountryItem,organizationWebsiteItem,pointOfContactItem, consolidationApproach);

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
    //String reportUrl = "/ibLGHGCalc/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
    String fullContextPath = GWT.getHostPageBaseURL();
    String[] contextPathArray = fullContextPath.split("/");
    String contextPath= contextPathArray[contextPathArray.length - 1];
    //System.out.println("Context Path is: "+contextPath);
    
    String reportUrl;
    if (contextPath.contains("igreenlaunch")) {
        reportUrl = "/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;
    } else {
        reportUrl = "/"+contextPath+"/reports?emissionsSummaryReportId="+emissionsSummaryReportId+"&organizationId="+orgId;        
    }
    
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

                Criteria employeeBusinessTravelHotelStayFetchCriteria = new Criteria();
                employeeBusinessTravelHotelStayFetchCriteria.addCriteria(fetchCriteria);
                employeeBusinessTravelHotelStayFetchCriteria.addCriteria("methodType", "Employee Business Travel - Hotel Stay");
                
                employeeBusinessTravelByVehicleDataGrid.filterData(employeeBusinessTravelByVehicleFetchCriteria);
                employeeBusinessTravelByRailDataGrid.filterData(employeeBusinessTravelByRailFetchCriteria);
                employeeBusinessTravelByBusDataGrid.filterData(employeeBusinessTravelByBusFetchCriteria);
                employeeBusinessTravelByAirDataGrid.filterData(employeeBusinessTravelByAirFetchCriteria);
                employeeBusinessTravelHotelStayDataGrid.filterData(employeeBusinessTravelHotelStayFetchCriteria);

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
            } else if (emissionSourceChoice.equals("Purchased Goods and Services")){
               //--Display Purchased Goods and Services
               Criteria purchasedProductLevelFetchCriteria = new Criteria();
               purchasedProductLevelFetchCriteria.addCriteria("methodType", "Purchased Goods and Services - Product Level Method");
               purchasedProductLevelFetchCriteria.addCriteria(fetchCriteria);
               purchasedProductDataGrid_1.filterData(purchasedProductLevelFetchCriteria);               

               Criteria purchasedProductSupplierSpecificFetchCriteria = new Criteria();
               purchasedProductSupplierSpecificFetchCriteria.addCriteria("methodType", "Purchased Goods and Services - Supplier Specific Method");
               purchasedProductSupplierSpecificFetchCriteria.addCriteria(fetchCriteria);
               purchasedProductDataGrid_2.filterData(purchasedProductSupplierSpecificFetchCriteria);               

               Criteria purchasedProductMaterialOrSpendBasedFetchCriteria = new Criteria();
               purchasedProductMaterialOrSpendBasedFetchCriteria.addCriteria("methodType", "Purchased Goods and Services - Material Or Spend Based Method");
               purchasedProductMaterialOrSpendBasedFetchCriteria.addCriteria(fetchCriteria);
               purchasedProductDataGrid_3.filterData(purchasedProductMaterialOrSpendBasedFetchCriteria);               
               
               middleMiddleHLayout.addChild(purchasedProductLayout);
            } else if (emissionSourceChoice.equals("Purchased Energy")){
               //--Display Purchased Energy
               /*
               Criteria purchasedEnergyLevelFetchCriteria = new Criteria();
               purchasedEnergyLevelFetchCriteria.addCriteria("methodType", "Fuel and Energy Related Activities");
               purchasedEnergyLevelFetchCriteria.addCriteria(fetchCriteria);
               purchasedEnergyDataGrid.filterData(purchasedEnergyLevelFetchCriteria);               
               */
               Criteria purchasedEnergyFetchCriteria_1 = new Criteria();
               purchasedEnergyFetchCriteria_1.addCriteria("activityType", "Upstream Emissions Of Purchased Fuels");
               purchasedEnergyFetchCriteria_1.addCriteria(fetchCriteria);
               purchasedEnergyDataGrid_1.filterData(purchasedEnergyFetchCriteria_1);               
                

               Criteria purchasedEnergyFetchCriteria_2 = new Criteria();
               purchasedEnergyFetchCriteria_2.addCriteria("activityType", "Upstream Emissions Of Purchased Energy");
               purchasedEnergyFetchCriteria_2.addCriteria(fetchCriteria);
               purchasedEnergyDataGrid_2.filterData(purchasedEnergyFetchCriteria_2);               

               
               Criteria purchasedEnergyFetchCriteria_3 = new Criteria();
               purchasedEnergyFetchCriteria_3.addCriteria("activityType", "Transmission & Distribution Losses");
               purchasedEnergyFetchCriteria_3.addCriteria(fetchCriteria);
               purchasedEnergyDataGrid_3.filterData(purchasedEnergyFetchCriteria_3);               

               Criteria purchasedEnergyFetchCriteria_4 = new Criteria();
               purchasedEnergyFetchCriteria_4.addCriteria("activityType", "Generation Of Purchased Energy That Is Sold To End Users");
               purchasedEnergyFetchCriteria_4.addCriteria(fetchCriteria);
               purchasedEnergyDataGrid_4.filterData(purchasedEnergyFetchCriteria_4);               
               
               middleMiddleHLayout.addChild(purchasedEnergyLayout);
            } else if (emissionSourceChoice.equals("Transportation")){
                
               Criteria transportationFetchCriteria_1 = new Criteria();
               transportationFetchCriteria_1.addCriteria("methodType", "Fuel Based Method - Transportation Emissions");
               transportationFetchCriteria_1.addCriteria(fetchCriteria);
               transportationDataGrid_1.filterData(transportationFetchCriteria_1);               
                
               Criteria transportationFetchCriteria_2 = new Criteria();
               transportationFetchCriteria_2.addCriteria("methodType", "Distance Based Method - Transportation Emissions");
               transportationFetchCriteria_2.addCriteria(fetchCriteria);
               transportationDataGrid_2.filterData(transportationFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(transportationLayout);
            }else if (emissionSourceChoice.equals("Distribution")){
                
               Criteria distributionFetchCriteria_1 = new Criteria();
               distributionFetchCriteria_1.addCriteria("methodType", "Site Specific Method - Distribution Emissions");
               distributionFetchCriteria_1.addCriteria(fetchCriteria);
               distributionDataGrid_1.filterData(distributionFetchCriteria_1);               
                
               Criteria distributionFetchCriteria_2 = new Criteria();
               distributionFetchCriteria_2.addCriteria("methodType", "Average Data Method - Distribution Emissions");
               distributionFetchCriteria_2.addCriteria(fetchCriteria);
               distributionDataGrid_2.filterData(distributionFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(distributionLayout);
            }else if (emissionSourceChoice.equals("Waste Generated In Operations")){
                
               Criteria wasteGeneratedInOperationsFetchCriteria_1 = new Criteria();
               wasteGeneratedInOperationsFetchCriteria_1.addCriteria("methodType", "Waste Type Specific Method - Waste Generated In Operations");
               wasteGeneratedInOperationsFetchCriteria_1.addCriteria(fetchCriteria);
               wasteGeneratedInOperationsDataGrid_1.filterData(wasteGeneratedInOperationsFetchCriteria_1);               
                
               Criteria wasteGeneratedInOperationsFetchCriteria_2 = new Criteria();
               wasteGeneratedInOperationsFetchCriteria_2.addCriteria("methodType", "Average Data Method - Waste Generated In Operations");
               wasteGeneratedInOperationsFetchCriteria_2.addCriteria(fetchCriteria);
               wasteGeneratedInOperationsDataGrid_2.filterData(wasteGeneratedInOperationsFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(wasteGeneratedInOperationsLayout);
            }else if (emissionSourceChoice.equals("Business Travel")){
                
               Criteria businessTravelFetchCriteria = new Criteria();
               businessTravelFetchCriteria.addCriteria("methodType", "Distance Based Method - Business Travel Emissions");
               businessTravelFetchCriteria.addCriteria(fetchCriteria);
               businessTravelDataGrid.filterData(businessTravelFetchCriteria);               
                              
               middleMiddleHLayout.addChild(businessTravelLayout);
            }else if (emissionSourceChoice.equals("Leased Assets")){
                
               Criteria leasedAssetsFetchCriteria_1 = new Criteria();
               leasedAssetsFetchCriteria_1.addCriteria("methodType", "Site Specific Method - Leased Assets");
               leasedAssetsFetchCriteria_1.addCriteria(fetchCriteria);
               leasedAssetsDataGrid_1.filterData(leasedAssetsFetchCriteria_1);               
                
               Criteria leasedAssetsFetchCriteria_2 = new Criteria();
               leasedAssetsFetchCriteria_2.addCriteria("methodType", "Average Data Method - Leased Assets");
               leasedAssetsFetchCriteria_2.addCriteria(fetchCriteria);
               leasedAssetsDataGrid_2.filterData(leasedAssetsFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(leasedAssetsLayout);
            }else if (emissionSourceChoice.equals("Processing Of Sold Products")){
                
               Criteria processingOfSoldProductsFetchCriteria_1 = new Criteria();
               processingOfSoldProductsFetchCriteria_1.addCriteria("methodType", "Site Specific Method - Processing Of Sold Products Emissions");
               processingOfSoldProductsFetchCriteria_1.addCriteria(fetchCriteria);
               processingOfSoldProductsDataGrid_1.filterData(processingOfSoldProductsFetchCriteria_1);               
                
               Criteria processingOfSoldProductsFetchCriteria_2 = new Criteria();
               processingOfSoldProductsFetchCriteria_2.addCriteria("methodType", "Average Data Method - Processing Of Sold Products Emissions");
               processingOfSoldProductsFetchCriteria_2.addCriteria(fetchCriteria);
               processingOfSoldProductsDataGrid_2.filterData(processingOfSoldProductsFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(processingOfSoldProductsLayout);
            }else if (emissionSourceChoice.equals("Direct Use Phase Emissions For Sold Products")){
                
               Criteria directUsePhaseEmissionsFetchCriteria_1 = new Criteria();
               directUsePhaseEmissionsFetchCriteria_1.addCriteria("methodType", "Lifetime Uses Method");
                                                                                
               directUsePhaseEmissionsFetchCriteria_1.addCriteria(fetchCriteria);
               directUsePhaseEmissionsDataGrid_1.filterData(directUsePhaseEmissionsFetchCriteria_1);               
                
               Criteria directUsePhaseEmissionsFetchCriteria_2 = new Criteria();
               directUsePhaseEmissionsFetchCriteria_2.addCriteria("methodType", "Combustion Method");
               directUsePhaseEmissionsFetchCriteria_2.addCriteria(fetchCriteria);
               directUsePhaseEmissionsDataGrid_2.filterData(directUsePhaseEmissionsFetchCriteria_2);               

               Criteria directUsePhaseEmissionsFetchCriteria_3 = new Criteria();
               directUsePhaseEmissionsFetchCriteria_3.addCriteria("methodType", "GHG released Method");
               directUsePhaseEmissionsFetchCriteria_3.addCriteria(fetchCriteria);
               directUsePhaseEmissionsDataGrid_3.filterData(directUsePhaseEmissionsFetchCriteria_3);               
               
               middleMiddleHLayout.addChild(directUsePhaseEmissionsLayout);
            }else if (emissionSourceChoice.equals("In-Direct Use Phase Emissions For Sold Products")){
                
               Criteria inDirectUsePhaseEmissionsFetchCriteria_1 = new Criteria();
               inDirectUsePhaseEmissionsFetchCriteria_1.addCriteria("methodType", "Typical Use Phase Profile Method");
               inDirectUsePhaseEmissionsFetchCriteria_1.addCriteria(fetchCriteria);
               inDirectUsePhaseEmissionsDataGrid_1.filterData(inDirectUsePhaseEmissionsFetchCriteria_1);               
                
               Criteria inDirectUsePhaseEmissionsFetchCriteria_2 = new Criteria();
               inDirectUsePhaseEmissionsFetchCriteria_2.addCriteria("methodType", "Functional Unit Method");
               inDirectUsePhaseEmissionsFetchCriteria_2.addCriteria(fetchCriteria);
               inDirectUsePhaseEmissionsDataGrid_2.filterData(inDirectUsePhaseEmissionsFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(inDirectUsePhaseEmissionsLayout);
            }else if (emissionSourceChoice.equals("End Of Life Treatment Of Sold Products")){
                
               Criteria endOfLifeTreatmentOfSoldProductsFetchCriteria = new Criteria();
               endOfLifeTreatmentOfSoldProductsFetchCriteria.addCriteria("methodType", "Calculation Method");
               endOfLifeTreatmentOfSoldProductsFetchCriteria.addCriteria(fetchCriteria);
               endOfLifeTreatmentOfSoldProductsDataGrid.filterData(endOfLifeTreatmentOfSoldProductsFetchCriteria);               
                                              
               middleMiddleHLayout.addChild(endOfLifeTreatmentOfSoldProductsLayout);
            }else if (emissionSourceChoice.equals("Franchises")){
                
               Criteria franchisesFetchCriteria_1 = new Criteria();
               franchisesFetchCriteria_1.addCriteria("methodType", "Franchise Specific Method");
               franchisesFetchCriteria_1.addCriteria(fetchCriteria);
               franchisesDataGrid_1.filterData(franchisesFetchCriteria_1);               
                
               Criteria franchisesFetchCriteria_2 = new Criteria();
               franchisesFetchCriteria_2.addCriteria("methodType", "Average Data Method");
               franchisesFetchCriteria_2.addCriteria(fetchCriteria);
               franchisesDataGrid_2.filterData(franchisesFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(franchisesLayout);
            } else if (emissionSourceChoice.equals("Investments")){
                
               Criteria investmentsFetchCriteria_1 = new Criteria();
               investmentsFetchCriteria_1.addCriteria("methodType", "Investment Specific Method");
               investmentsFetchCriteria_1.addCriteria(fetchCriteria);
               investmentsDataGrid_1.filterData(investmentsFetchCriteria_1);               
                
               Criteria investmentsFetchCriteria_2 = new Criteria();
               investmentsFetchCriteria_2.addCriteria("methodType", "Economic Data Method");
               investmentsFetchCriteria_2.addCriteria(fetchCriteria);
               investmentsDataGrid_2.filterData(investmentsFetchCriteria_2);               
                              
               middleMiddleHLayout.addChild(investmentsLayout);
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
        //ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);
        fuelUsedBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        //ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate");
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

        FloatListGridField annualNumberOfHotelNights = new FloatListGridField("annualNumberOfHotelNights");        
        FloatListGridField EF_Hotel = new FloatListGridField("EF_Hotel");
        ListGridField EF_Hotel_Unit = new ListGridField("EF_Hotel_Unit");
        
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
        } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelHotelStay")){
            listGridFieldList.add(annualNumberOfHotelNights);
            listGridFieldList.add(EF_Hotel);
            listGridFieldList.add(EF_Hotel_Unit);
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

public static ListGridField[] getPurchasedProductListGridFields_1() {
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");
        ListGridField purchasedProductTypeField = new ListGridField("purchasedProductType");
        ListGridField purchasedProductNameField = new ListGridField("purchasedProductName");				
        FloatListGridField quantityOfPurchasedProductField = new FloatListGridField("quantityOfPurchasedProduct");
        ListGridField quantityOfPurchasedProduct_UnitField = new ListGridField("quantityOfPurchasedProduct_Unit");
        //quantityOfPurchasedProduct_UnitField.setWidth(UNIT_FIELD_WIDTH);

        FloatListGridField supplierSpecific_EF_ForPurchasedProductField = new FloatListGridField("supplierSpecific_EF_ForPurchasedProduct");        
        ListGridField supplierSpecific_EF_ForPurchasedProduct_UnitField = new ListGridField("supplierSpecific_EF_ForPurchasedProduct_Unit");
        //supplierSpecific_EF_ForPurchasedProduct_UnitField.setWidth(EF_UNIT_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(purchasedProductTypeField);
        listGridFieldList.add(purchasedProductNameField);
        listGridFieldList.add(quantityOfPurchasedProductField);
        listGridFieldList.add(quantityOfPurchasedProduct_UnitField);
        listGridFieldList.add(supplierSpecific_EF_ForPurchasedProductField);
	listGridFieldList.add(supplierSpecific_EF_ForPurchasedProduct_UnitField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedProductListGridFields_2() {
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");
        ListGridField purchasedProductTypeField = new ListGridField("purchasedProductType");
        ListGridField purchasedProductNameField = new ListGridField("purchasedProductName");				
        FloatListGridField quantityOfPurchasedProductField = new FloatListGridField("quantityOfPurchasedProduct");
        ListGridField quantityOfPurchasedProduct_UnitField = new ListGridField("quantityOfPurchasedProduct_Unit");
        //quantityOfPurchasedProduct_UnitField.setWidth(UNIT_FIELD_WIDTH);

        FloatListGridField supplierSpecific_EF_ForPurchasedProductField = new FloatListGridField("supplierSpecific_EF_ForPurchasedProduct");        
        ListGridField supplierSpecific_EF_ForPurchasedProduct_UnitField = new ListGridField("supplierSpecific_EF_ForPurchasedProduct_Unit");
        //supplierSpecific_EF_ForPurchasedProduct_UnitField.setWidth(EF_UNIT_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(purchasedProductTypeField);
        listGridFieldList.add(purchasedProductNameField);
        listGridFieldList.add(quantityOfPurchasedProductField);
        listGridFieldList.add(quantityOfPurchasedProduct_UnitField);
        listGridFieldList.add(supplierSpecific_EF_ForPurchasedProductField);
	listGridFieldList.add(supplierSpecific_EF_ForPurchasedProduct_UnitField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedProductListGridFields_2_0() {
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");
        ListGridField purchasedProductTypeField = new ListGridField("purchasedProductType");
        ListGridField purchasedProductNameField = new ListGridField("purchasedProductName");				
        FloatListGridField quantityOfPurchasedProductField = new FloatListGridField("quantityOfPurchasedProduct");
        ListGridField quantityOfPurchasedProduct_UnitField = new ListGridField("quantityOfPurchasedProduct_Unit");
        //quantityOfPurchasedProduct_UnitField.setWidth(UNIT_FIELD_WIDTH);

        ListGridField materialUsedBy_T1SField = new ListGridField("materialUsedBy_T1S", "Material Used");
        //materialUsedBy_T1SField.setAlign(Alignment.CENTER);
        //materialUsedBy_T1SField.setWidth(EDIT_BUTTON_FIELD_WIDTH);

        ListGridField materialTransportTo_T1SField = new ListGridField("materialTransportTo_T1S", "Transport of Material");
        //transportOfMaterialInputs_T1SField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);

        ListGridField wasteOutputFrom_T1SField = new ListGridField("wasteOutputFrom_T1S", "Waste Output");
        //wasteOutputFrom_T1SField.setWidth(REMOVE_BUTTON_FIELD_WIDTH);
        
        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(purchasedProductTypeField);
        listGridFieldList.add(purchasedProductNameField);
        listGridFieldList.add(quantityOfPurchasedProductField);
        listGridFieldList.add(quantityOfPurchasedProduct_UnitField);
	listGridFieldList.add(materialUsedBy_T1SField);
	listGridFieldList.add(materialTransportTo_T1SField);
        listGridFieldList.add(wasteOutputFrom_T1SField);                

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedProductListGridFields_2_2() {
    
        ListGridField materialUsedBy_T1S_ForPurchasedProduct = new TextListGridField("materialUsedBy_T1S_ForPurchasedProduct");
        //sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);
			
        FloatListGridField amountOfMaterialUsedBy_T1S_ForPurchasedProduct = new FloatListGridField("amountOfMaterialUsedBy_T1S_ForPurchasedProduct","Quantity");
        //quantityOfPurchasedProductField.setWidth(QUANTITY_FIELD_WIDTH);

        ListGridField amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit = new ListGridField("amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit", "Unit");
        //quantityOfPurchasedProduct_UnitField.setDefaultValue("Kg");
        //quantityOfPurchasedProduct_UnitField.setWidth(UNIT_FIELD_WIDTH);

        FloatListGridField EF_Material = new FloatListGridField("EF_Material", "Emission Factor For Material");
        //supplierSpecific_EF_ForPurchasedProductField.setWidth(EF_FIELD_WIDTH);

        ListGridField EF_Material_Unit = new ListGridField("EF_Material_Unit", "Unit");
        //supplierSpecific_EF_ForPurchasedProduct_UnitField.setDefaultValue("Kg CO2e/Kg");
        //supplierSpecific_EF_ForPurchasedProduct_UnitField.setWidth(UNIT_FIELD_WIDTH);
		
        ListGridField dataBeginDateField = new ListGridField("dataBeginDate", "Purchase Begin Date");
        dataBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField dataEndDateField = new ListGridField("dataEndDate", "Purchase End Date");
        dataEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(materialUsedBy_T1S_ForPurchasedProduct);
        listGridFieldList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct);
        listGridFieldList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit);
        listGridFieldList.add(EF_Material);
        listGridFieldList.add(EF_Material_Unit);
        //listGridFieldList.add(dataBeginDateField);
        //listGridFieldList.add(dataEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedProductListGridFields_3() {
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");
        sourceDescriptionField.setWidth(SOURCE_DESCRIPTION_WIDTH);

        ListGridField supplierNameField = new ListGridField("supplierName");
        supplierNameField.setWidth(NAME_FIELD_WIDTH);

        ListGridField supplierContactField = new ListGridField("supplierContact");
        supplierContactField.setWidth(NAME_FIELD_WIDTH);
		
        ListGridField purchasedProductTypeField = new ListGridField("purchasedProductType");
        purchasedProductTypeField.setWidth(TYPE_FIELD_WIDTH);

        ListGridField purchasedProductNameField = new ListGridField("purchasedProductName");
        purchasedProductNameField.setWidth(NAME_FIELD_WIDTH);
				
        FloatListGridField quantityOfPurchasedProductField = new FloatListGridField("quantityOfPurchasedProduct","Quantity");
        quantityOfPurchasedProductField.setWidth(QUANTITY_FIELD_WIDTH);

        ListGridField quantityOfPurchasedProduct_UnitField = new ListGridField("quantityOfPurchasedProduct_Unit", "Purchased Product Unit");
        quantityOfPurchasedProduct_UnitField.setDefaultValue("Kg");
        quantityOfPurchasedProduct_UnitField.setWidth(UNIT_FIELD_WIDTH);

        FloatListGridField secondary_EF_Field = new FloatListGridField("secondary_EF", "Secondary Emission Factor");
        secondary_EF_Field.setWidth(EF_FIELD_WIDTH);

        ListGridField secondary_EF_Unit_Field = new ListGridField("secondary_EF_Unit", "Unit");
        secondary_EF_Unit_Field.setDefaultValue("Kg CO2e/Kg");
        secondary_EF_Unit_Field.setWidth(UNIT_FIELD_WIDTH);
		
        ListGridField dataBeginDateField = new ListGridField("dataBeginDate", "Purchase Begin Date");
        dataBeginDateField.setWidth(BEGIN_DATE_FIELD_WIDTH);

        ListGridField dataEndDateField = new ListGridField("dataEndDate", "Purchase End Date");
        dataEndDateField.setWidth(END_DATE_FIELD_WIDTH);

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(sourceDescriptionField);
        //listGridFieldList.add(supplierNameField);
        //listGridFieldList.add(supplierContactField);
        listGridFieldList.add(purchasedProductTypeField);
        listGridFieldList.add(purchasedProductNameField);
        listGridFieldList.add(quantityOfPurchasedProductField);
        listGridFieldList.add(quantityOfPurchasedProduct_UnitField);
        listGridFieldList.add(secondary_EF_Field);
	listGridFieldList.add(secondary_EF_Unit_Field);
        //listGridFieldList.add(dataBeginDateField);
        //listGridFieldList.add(dataEndDateField);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getPurchasedEnergyListGridFields() {
    
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        ListGridField activityType = new TextListGridField("activityType");
        ListGridField energyType = new TextListGridField("energyType");        
        //ListGridField supplierName = new ListGridField("supplierName");
        //ListGridField supplierContact = new ListGridField("supplierContact");				
        //ListGridField supplierRegionCountry = new ListGridField("supplierRegionCountry");	        
        
        FloatListGridField energyPurchased = new FloatListGridField("energyPurchased");
        ListGridField energyPurchased_Unit = new ListGridField("energyPurchased_Unit");

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(activityType);
        listGridFieldList.add(energyType);        
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(energyPurchased);
        listGridFieldList.add(energyPurchased_Unit);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedEnergyListGridFields_1() {
    
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
		ListGridField fuelType = new TextListGridField("fuelType");       
        FloatListGridField energyPurchased = new FloatListGridField("energyPurchased");
		energyPurchased.setTitle("Fuel Purchased");
        ListGridField energyPurchased_Unit = new ListGridField("energyPurchased_Unit");

        
		List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
		listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(fuelType);
        listGridFieldList.add(energyPurchased);
        listGridFieldList.add(energyPurchased_Unit);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedEnergyListGridFields_2() {
    
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        ListGridField energyType = new TextListGridField("energyType");                
        FloatListGridField energyPurchased = new FloatListGridField("energyPurchased");
        ListGridField energyPurchased_Unit = new ListGridField("energyPurchased_Unit");

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(energyType);        
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(energyPurchased);
        listGridFieldList.add(energyPurchased_Unit);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedEnergyListGridFields_3() {
    
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        ListGridField energyType = new TextListGridField("energyType");                
        FloatListGridField energyPurchased = new FloatListGridField("energyPurchased");
        ListGridField energyPurchased_Unit = new ListGridField("energyPurchased_Unit");

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(energyType);        
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(energyPurchased);
        listGridFieldList.add(energyPurchased_Unit);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getPurchasedEnergyListGridFields_4() {
    
        ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        ListGridField energyType = new TextListGridField("energyType");                
        FloatListGridField energyPurchased = new FloatListGridField("energyPurchased");
        ListGridField energyPurchased_Unit = new ListGridField("energyPurchased_Unit");

        List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
        listGridFieldList.add(energyType);        
        listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(energyPurchased);
        listGridFieldList.add(energyPurchased_Unit);

        ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
        listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getTransportationListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField streamType = new TextListGridField("streamType");
	
	ListGridField fuelType = new TextListGridField("fuelType");                
	FloatListGridField fuelConsumed = new FloatListGridField("fuelConsumed");
	ListGridField fuelConsumed_Unit = new ListGridField("fuelConsumed_Unit");
	FloatListGridField EF_Fuel = new FloatListGridField("EF_Fuel");
	ListGridField EF_Fuel_Unit = new ListGridField("EF_Fuel_Unit");

	ListGridField refrigerantType = new TextListGridField("refrigerantType");                
	FloatListGridField refrigerantLeakage = new FloatListGridField("refrigerantLeakage");
	ListGridField refrigerantLeakage_Unit = new ListGridField("refrigerantLeakage_Unit");
	FloatListGridField EF_Refrigerant = new FloatListGridField("EF_Refrigerant");
	ListGridField EF_Refrigerant_Unit = new ListGridField("EF_Refrigerant_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(streamType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(fuelType);
	listGridFieldList.add(fuelConsumed);
	listGridFieldList.add(fuelConsumed_Unit);
	listGridFieldList.add(EF_Fuel);
	listGridFieldList.add(EF_Fuel_Unit);

	listGridFieldList.add(refrigerantType);
	listGridFieldList.add(refrigerantLeakage);
	listGridFieldList.add(refrigerantLeakage_Unit);
	listGridFieldList.add(EF_Refrigerant);
	listGridFieldList.add(EF_Refrigerant_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getTransportationListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField streamType = new TextListGridField("streamType");
	
	ListGridField transportModeOrVehicleType = new TextListGridField("transportModeOrVehicleType");                
	FloatListGridField massOfGoodsPurchased = new FloatListGridField("massOfGoodsPurchased");
	ListGridField massOfGoodsPurchased_Unit = new ListGridField("massOfGoodsPurchased_Unit");
	FloatListGridField distanceTraveledInTransportLeg = new FloatListGridField("distanceTraveledInTransportLeg");
	ListGridField distanceTraveledInTransportLeg_Unit = new ListGridField("distanceTraveledInTransportLeg_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(streamType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(transportModeOrVehicleType);
	listGridFieldList.add(massOfGoodsPurchased);
	listGridFieldList.add(massOfGoodsPurchased_Unit);
	listGridFieldList.add(distanceTraveledInTransportLeg);
	listGridFieldList.add(distanceTraveledInTransportLeg_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getDistributionListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField streamType = new TextListGridField("streamType");
	
	FloatListGridField volumeOfReportingCompanysPurchasedGoods = new FloatListGridField("volumeOfReportingCompanysPurchasedGoods");                
	ListGridField volumeOfReportingCompanysPurchasedGoods_Unit = new ListGridField("volumeOfReportingCompanysPurchasedGoods_Unit");
	FloatListGridField totalVolumeOfGoodsInStorageFacility = new FloatListGridField("totalVolumeOfGoodsInStorageFacility");
	ListGridField totalVolumeOfGoodsInStorageFacility_Unit = new ListGridField("totalVolumeOfGoodsInStorageFacility_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(streamType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(volumeOfReportingCompanysPurchasedGoods);
	listGridFieldList.add(volumeOfReportingCompanysPurchasedGoods_Unit);
	listGridFieldList.add(totalVolumeOfGoodsInStorageFacility);
	listGridFieldList.add(totalVolumeOfGoodsInStorageFacility_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getDistributionListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField streamType = new TextListGridField("streamType");
	
	FloatListGridField storedGoodsInReportingYear = new FloatListGridField("storedGoodsInReportingYear");                
	ListGridField storedGoodsInReportingYear_Unit = new ListGridField("storedGoodsInReportingYear_Unit");
	FloatListGridField EF_StorageFacility = new FloatListGridField("EF_StorageFacility");
	ListGridField EF_StorageFacility_Unit = new ListGridField("EF_StorageFacility_Unit");

	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(streamType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(storedGoodsInReportingYear);
	listGridFieldList.add(storedGoodsInReportingYear_Unit);
	listGridFieldList.add(EF_StorageFacility);
	listGridFieldList.add(EF_StorageFacility_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getWasteGeneratedInOperationsListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField wasteType = new TextListGridField("wasteType");
	
	ListGridField wasteTreatmentType = new TextListGridField("wasteTreatmentType");                
	FloatListGridField wasteProduced = new FloatListGridField("wasteProduced");
	ListGridField wasteProduced_Unit = new ListGridField("wasteProduced_Unit");
	FloatListGridField EF_WasteTypeAndWasteTreatment = new FloatListGridField("EF_WasteTypeAndWasteTreatment");
        ListGridField EF_WasteTypeAndWasteTreatment_Unit = new ListGridField("EF_WasteTypeAndWasteTreatment_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);        
	listGridFieldList.add(wasteType);     
	listGridFieldList.add(wasteTreatmentType);
	listGridFieldList.add(wasteProduced);
	listGridFieldList.add(wasteProduced_Unit);
	listGridFieldList.add(EF_WasteTypeAndWasteTreatment);
	listGridFieldList.add(EF_WasteTypeAndWasteTreatment_Unit);
        
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getWasteGeneratedInOperationsListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField wasteType = new TextListGridField("wasteType");
	
	ListGridField wasteTreatmentType = new TextListGridField("wasteTreatmentType");                
	FloatListGridField wasteProduced = new FloatListGridField("wasteProduced");
	ListGridField wasteProduced_Unit = new ListGridField("wasteProduced_Unit");
	FloatListGridField percentOfWasteTreatedByWasteTreatmentMethod = new FloatListGridField("percentOfWasteTreatedByWasteTreatmentMethod");        
	ListGridField EF_WasteTreatmentMethod = new ListGridField("EF_WasteTreatmentMethod");
        ListGridField EF_WasteTreatmentMethod_Unit = new ListGridField("EF_WasteTreatmentMethod_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);        
	listGridFieldList.add(wasteType);     
	listGridFieldList.add(wasteTreatmentType);
	listGridFieldList.add(wasteProduced);
	listGridFieldList.add(wasteProduced_Unit);
        listGridFieldList.add(percentOfWasteTreatedByWasteTreatmentMethod);
	listGridFieldList.add(EF_WasteTreatmentMethod);
	listGridFieldList.add(EF_WasteTreatmentMethod_Unit);
        
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getBusinessTravelListGridFields() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField activityType = new TextListGridField("activityType");
	
	ListGridField vehicleType = new TextListGridField("vehicleType");                
	ListGridField distanceTravelledByVehicleType = new ListGridField("distanceTravelledByVehicleType");	
	ListGridField distanceTravelledByVehicleType_Unit = new ListGridField("distanceTravelledByVehicleType_Unit");
        FloatListGridField EF_VehicleType = new FloatListGridField("EF_VehicleType");
        ListGridField EF_VehicleType_Unit = new ListGridField("EF_VehicleType_Unit");

        FloatListGridField annualNumberOfHotelNights = new FloatListGridField("annualNumberOfHotelNights");        
        FloatListGridField EF_Hotel = new FloatListGridField("EF_Hotel");
        ListGridField EF_Hotel_Unit = new ListGridField("EF_Hotel_Unit");
        
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(activityType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(vehicleType);
	listGridFieldList.add(distanceTravelledByVehicleType);
	listGridFieldList.add(distanceTravelledByVehicleType_Unit);
	listGridFieldList.add(EF_VehicleType);
	listGridFieldList.add(EF_VehicleType_Unit);
	listGridFieldList.add(annualNumberOfHotelNights);
	listGridFieldList.add(EF_Hotel);
	listGridFieldList.add(EF_Hotel_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getBusinessTravelHotelStayListGridFields() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        
        FloatListGridField annualNumberOfHotelNights = new FloatListGridField("annualNumberOfHotelNights");        
        FloatListGridField EF_Hotel = new FloatListGridField("EF_Hotel");
        ListGridField EF_Hotel_Unit = new ListGridField("EF_Hotel_Unit");
        
        
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();

	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(annualNumberOfHotelNights);
	listGridFieldList.add(EF_Hotel);
	listGridFieldList.add(EF_Hotel_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getLeasedAssetsListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField streamType = new TextListGridField("streamType");
	
	ListGridField typeOfLeasingArrangement = new TextListGridField("typeOfLeasingArrangement");                
	ListGridField lessorOrLessee = new ListGridField("lessorOrLessee");
	ListGridField leasedAssetName = new ListGridField("leasedAssetName");
	//FloatListGridField totalVolumeOfGoodsInStorageFacility_Unit = new FloatListGridField("totalVolumeOfGoodsInStorageFacility_Unit");

	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(streamType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(typeOfLeasingArrangement);
	listGridFieldList.add(lessorOrLessee);
	listGridFieldList.add(leasedAssetName);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getLeasedAssetsListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField streamType = new TextListGridField("streamType");
	
	ListGridField typeOfLeasingArrangement = new TextListGridField("typeOfLeasingArrangement");                
	ListGridField lessorOrLessee = new ListGridField("lessorOrLessee");
	ListGridField leasedAssetName = new ListGridField("leasedAssetName");
	//FloatListGridField totalVolumeOfGoodsInStorageFacility_Unit = new FloatListGridField("totalVolumeOfGoodsInStorageFacility_Unit");

	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(streamType);     
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(typeOfLeasingArrangement);
	listGridFieldList.add(lessorOrLessee);
	listGridFieldList.add(leasedAssetName);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getProcessingOfSoldProductsListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField soldProductName = new TextListGridField("soldProductName");
	ListGridField productSoldTo = new TextListGridField("productSoldTo");	
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();

	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(soldProductName);
	listGridFieldList.add(productSoldTo);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getProcessingOfSoldProductsListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField soldProductName = new TextListGridField("soldProductName");
	ListGridField productSoldTo = new TextListGridField("productSoldTo");	
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();

	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(soldProductName);
	listGridFieldList.add(productSoldTo);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getDirectUsePhaseEmissionsListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField productType = new TextListGridField("productType");
	ListGridField productName = new TextListGridField("productName");
	
	FloatListGridField totalLifetimeExpectedUsesOfProduct = new FloatListGridField("totalLifetimeExpectedUsesOfProduct");                
	FloatListGridField numberSoldInReportingPeriod = new FloatListGridField("numberSoldInReportingPeriod");                
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(productType);
	listGridFieldList.add(productName);
	listGridFieldList.add(totalLifetimeExpectedUsesOfProduct);
	listGridFieldList.add(numberSoldInReportingPeriod);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getDirectUsePhaseEmissionsListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField productType = new TextListGridField("productType");
	ListGridField productName = new TextListGridField("productName");
	
	FloatListGridField totalQuantityOfFuelOrFeedstockSold = new FloatListGridField("totalQuantityOfFuelOrFeedstockSold"); 
	ListGridField totalQuantityOfFuelOrfeedstockSold_Unit = new TextListGridField("totalQuantityOfFuelOrfeedstockSold_Unit");
	
	FloatListGridField combustion_EF_ForFuelOrFeedstock = new FloatListGridField("combustion_EF_ForFuelOrFeedstock");                
	ListGridField combustion_EF_ForFuelOrFeedstock_Unit = new TextListGridField("combustion_EF_ForFuelOrFeedstock_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(productType);
	listGridFieldList.add(productName);
	listGridFieldList.add(totalQuantityOfFuelOrFeedstockSold);
	listGridFieldList.add(totalQuantityOfFuelOrfeedstockSold_Unit);
	listGridFieldList.add(combustion_EF_ForFuelOrFeedstock);
	listGridFieldList.add(combustion_EF_ForFuelOrFeedstock_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getDirectUsePhaseEmissionsListGridFields_3() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField productType = new TextListGridField("productType");
	ListGridField productName = new TextListGridField("productName");
	
	ListGridField GHG_Name = new TextListGridField("GHG_Name");	
	FloatListGridField numberSoldInReportingPeriod = new FloatListGridField("numberSoldInReportingPeriod"); 	
	FloatListGridField GHG_PerProduct = new FloatListGridField("GHG_PerProduct"); 
	
	FloatListGridField percentOfGHGReleasedDuringLifetimeUseOfProduct = new FloatListGridField("percentOfGHGReleasedDuringLifetimeUseOfProduct");
	FloatListGridField GWP_GHG = new FloatListGridField("GWP_GHG");
		
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(productType);
	listGridFieldList.add(productName);
	listGridFieldList.add(GHG_Name);
	listGridFieldList.add(numberSoldInReportingPeriod);
	listGridFieldList.add(GHG_PerProduct);
	listGridFieldList.add(percentOfGHGReleasedDuringLifetimeUseOfProduct);
	listGridFieldList.add(GWP_GHG);
        
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getInDirectUsePhaseEmissionsListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField productType = new TextListGridField("productType");
	ListGridField productName = new TextListGridField("productName");
	
	FloatListGridField totalLifetimeExpectedUsesOfProduct = new FloatListGridField("totalLifetimeExpectedUsesOfProduct"); 
	FloatListGridField numberSoldInReportingPeriod = new FloatListGridField("numberSoldInReportingPeriod"); 	
			
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(productType);
	listGridFieldList.add(productName);
	listGridFieldList.add(totalLifetimeExpectedUsesOfProduct);
	listGridFieldList.add(numberSoldInReportingPeriod);
        
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getInDirectUsePhaseEmissionsListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField productType = new TextListGridField("productType");
	ListGridField productName = new TextListGridField("productName");
	
	FloatListGridField functionalUnitsPerformedPerProduct = new FloatListGridField("functionalUnitsPerformedPerProduct"); 
	FloatListGridField numberSoldInReportingPeriod = new FloatListGridField("numberSoldInReportingPeriod"); 	

	FloatListGridField emissionsPerFunctionalUnitOfProduct = new FloatListGridField("emissionsPerFunctionalUnitOfProduct"); 	
	ListGridField emissionsPerFunctionalUnitOfProduct_Unit = new TextListGridField("emissionsPerFunctionalUnitOfProduct_Unit");

	FloatListGridField totalLifetimeEmissions = new FloatListGridField("totalLifetimeEmissions"); 	
	ListGridField totalLifetimeEmissions_Unit = new TextListGridField("totalLifetimeEmissions_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);
	listGridFieldList.add(productType);
	listGridFieldList.add(productName);
	listGridFieldList.add(functionalUnitsPerformedPerProduct);
	listGridFieldList.add(numberSoldInReportingPeriod);

	listGridFieldList.add(emissionsPerFunctionalUnitOfProduct);
	listGridFieldList.add(emissionsPerFunctionalUnitOfProduct_Unit);
	listGridFieldList.add(totalLifetimeEmissions);
	listGridFieldList.add(totalLifetimeEmissions_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getEndOfLifeTreatmentOfSoldProductsListGridFields() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        ListGridField soldProductName = new TextListGridField("soldProductName");    
	FloatListGridField massOfSoldProductsAfterConsumerUse = new FloatListGridField("massOfSoldProductsAfterConsumerUse");
	ListGridField massOfSoldProductsAfterConsumerUse_Unit = new ListGridField("massOfSoldProductsAfterConsumerUse_Unit");
	FloatListGridField percentOfWasteTreatedByWasteTreatmentMethod = new FloatListGridField("percentOfWasteTreatedByWasteTreatmentMethod");        
                            
	ListGridField wasteType = new TextListGridField("wasteType");
	ListGridField wasteTreatmentType = new TextListGridField("wasteTreatmentType");                
	ListGridField EF_WasteTreatmentMethod = new ListGridField("EF_WasteTreatmentMethod");
        ListGridField EF_WasteTreatmentMethod_Unit = new ListGridField("EF_WasteTreatmentMethod_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);        
        listGridFieldList.add(soldProductName);   
	listGridFieldList.add(massOfSoldProductsAfterConsumerUse);
	listGridFieldList.add(massOfSoldProductsAfterConsumerUse_Unit);
        listGridFieldList.add(percentOfWasteTreatedByWasteTreatmentMethod);        
	listGridFieldList.add(wasteType);     
	listGridFieldList.add(wasteTreatmentType);
	listGridFieldList.add(EF_WasteTreatmentMethod);
	listGridFieldList.add(EF_WasteTreatmentMethod_Unit);
        
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getFranchisesListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField franchiseName = new TextListGridField("franchiseName");
	
        FloatListGridField scope1EmissionsOfFranchise = new FloatListGridField("scope1EmissionsOfFranchise");
	ListGridField scope1EmissionsOfFranchise_Unit = new TextListGridField("scope1EmissionsOfFranchise_Unit");                
        FloatListGridField scope2EmissionsOfFranchise = new FloatListGridField("scope2EmissionsOfFranchise");
	ListGridField scope2EmissionsOfFranchise_Unit = new TextListGridField("scope2EmissionsOfFranchise_Unit");                	
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);
        listGridFieldList.add(franchiseName);     
	listGridFieldList.add(scope1EmissionsOfFranchise);
	listGridFieldList.add(scope1EmissionsOfFranchise_Unit);
	listGridFieldList.add(scope2EmissionsOfFranchise);
	listGridFieldList.add(scope2EmissionsOfFranchise_Unit);
        
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}
public static ListGridField[] getFranchisesListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField franchiseName = new TextListGridField("franchiseName");
	
	FloatListGridField floorSpace = new FloatListGridField("floorSpace");                
	ListGridField floorSpace_Unit = new ListGridField("floorSpace_Unit");
	FloatListGridField average_EF = new FloatListGridField("average_EF");                
	ListGridField average_EF_Unit = new ListGridField("average_EF_Unit");
        
	ListGridField buildingOrAssetName = new ListGridField("buildingOrAssetName");
	FloatListGridField numberOfbuildingsOrAssetTypes = new FloatListGridField("numberOfbuildingsOrAssetTypes");                
	FloatListGridField averageEmissionsPerBuildingOrAssetType = new FloatListGridField("averageEmissionsPerBuildingOrAssetType");                
	ListGridField averageEmissionsPerBuildingOrAssetType_Unit = new ListGridField("averageEmissionsPerBuildingOrAssetType_Unit");
	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);     
	listGridFieldList.add(franchiseName);
	listGridFieldList.add(floorSpace);
	listGridFieldList.add(floorSpace_Unit);
	listGridFieldList.add(average_EF);
	listGridFieldList.add(average_EF_Unit);
	listGridFieldList.add(buildingOrAssetName);
	listGridFieldList.add(numberOfbuildingsOrAssetTypes);
	listGridFieldList.add(averageEmissionsPerBuildingOrAssetType);
	listGridFieldList.add(averageEmissionsPerBuildingOrAssetType_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getInvestmentsListGridFields_1() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
	ListGridField investmentType = new TextListGridField("investmentType");
	
	FloatListGridField scope1Emissions = new FloatListGridField("scope1Emissions");                
	ListGridField scope1Emissions_Unit = new ListGridField("scope1Emissions_Unit");
	FloatListGridField scope2Emissions = new FloatListGridField("scope2Emissions");                
	ListGridField scope2Emissions_Unit = new ListGridField("scope2Emissions_Unit");
        	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);     
	listGridFieldList.add(investmentType);
	listGridFieldList.add(scope1Emissions);
	listGridFieldList.add(scope1Emissions_Unit);
	listGridFieldList.add(scope2Emissions);
	listGridFieldList.add(scope2Emissions_Unit);
	
	ListGridField[] listGridFieldArray = new ListGridField[listGridFieldList.size()];
	listGridFieldList.toArray(listGridFieldArray);

	return listGridFieldArray;
}

public static ListGridField[] getInvestmentsListGridFields_2() {
    
	ListGridField sourceDescriptionField = new TextListGridField("sourceDescription");    
        ListGridField investmentType = new TextListGridField("investmentType");
	ListGridField investmentSector = new TextListGridField("investmentSector");
	
	FloatListGridField investmentAmount = new FloatListGridField("investmentAmount");                
	ListGridField investmentAmount_Unit = new ListGridField("investmentAmount_Unit");
	FloatListGridField EF_SectorSpecific = new FloatListGridField("EF_SectorSpecific");                
	ListGridField EF_SectorSpecific_Unit = new ListGridField("EF_SectorSpecific_Unit");
        	
	List<ListGridField> listGridFieldList = new ArrayList<ListGridField>();
	listGridFieldList.add(sourceDescriptionField);     
	listGridFieldList.add(investmentType);
        listGridFieldList.add(investmentSector);
	listGridFieldList.add(investmentAmount);
	listGridFieldList.add(investmentAmount_Unit);
	listGridFieldList.add(EF_SectorSpecific);
	listGridFieldList.add(EF_SectorSpecific_Unit);
	
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
        //dataForm.setLayoutAlign(Alignment.CENTER);//lign(VerticalAlignment.CENTER);
        
        
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
                
                /*
                Record formRecord = dataForm.getValuesAsRecord();                
                Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
                formRecord.setAttribute("organizationId", organizationIdValue);
                //ListGrid testThisItem = (ListGrid)((IblGridEditorItem) dataForm.getItem("materialUsedBy_T1S_Info")).getCanvas();
                //RecordList records = testThisItem.getDataAsRecordList();
                //Map records = testThisItem.getValues();
                //formRecord.setAttribute("materialUsedBy_T1S_InfoGrid", records.toArray());                                
                //dataGrid.updateData(formRecord);               
                
                
                dataGrid.updateData(formRecord, new DSCallback(){
                    @Override
                    public void execute(DSResponse response, Object rawData, DSRequest request) {  
                        
                        ListGrid testThisItem = (ListGrid)((IblGridEditorItem) dataForm.getItem("materialUsedBy_T1S_Info")).getCanvas();
                        testThisItem.saveAllEdits();                        
                    }
                });     
                
                
                
                dataForm.setValues((Map)formRecord);
                //dataForm.setValues((formRecord.getAtt);
                dataForm.saveData();
                //testThisItem.saveAllEdits();
                
                dataForm.markForRedraw();
                dataFormWindow.hide();    
                */
            };
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
        dialog.setWidth("100%");
        //dialog.setPadding(25);
        dialog.addMember(dataForm);
        dialog.addMember(buttons);
        //dialog.setAlign(Alignment.CENTER);

        dataFormWindow.setShowShadow(true);
        dataFormWindow.setIsModal(true);
        //dataFormWindow.setPadding(25);
        
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

    IblFloatItem annualNumberOfHotelNights = new IblFloatItem("annualNumberOfHotelNights");
    IblFloatItem EF_Hotel = new IblFloatItem("EF_Hotel");
    EF_Hotel.setStartRow(Boolean.TRUE);
    
    SelectItem EF_Hotel_Unit = new SelectItem("EF_Hotel_Unit");
    EF_Hotel_Unit.setValueMap("Kg CO2e");
    EF_Hotel_Unit.setDefaultValue("Kg CO2e");
    EF_Hotel_Unit.setWidth("*");    
    
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
    } else if (typeOfData.equalsIgnoreCase("employeeBusinessTravelHotelStay")){
         formItemList.add(annualNumberOfHotelNights);
         formItemList.add(EF_Hotel);
         formItemList.add(EF_Hotel_Unit);
         optionalSourceTypeItem.setDefaultValue("Employee Business Travel - Hotel Stay");
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

public FormItem[] getPurchasedProductFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
    IblFloatItem supplierSpecific_EF_ForPurchasedProductItem = new IblFloatItem("supplierSpecific_EF_ForPurchasedProduct");    
    SelectItem_EFUnit supplierSpecific_EF_ForPurchasedProduct_UnitItem = new SelectItem_EFUnit("supplierSpecific_EF_ForPurchasedProduct_Unit");    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Product Level Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
    formItemList.add(supplierSpecific_EF_ForPurchasedProductItem);
    formItemList.add(supplierSpecific_EF_ForPurchasedProduct_UnitItem);
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedProductFormFields_2() {
//-- setValidators for the forms for common types.
    initializeValidators();
   
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
       
    IblFloatItem scope1EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope1EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope1EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope1EmissionsOf_T1S_ForPurchasedProduct_Unit");
    IblFloatItem scope2EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope2EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope2EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope2EmissionsOf_T1S_ForPurchasedProduct_Unit");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
        
    final List<FormItem> formItemList = new ArrayList<FormItem>();
        
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
        
    //formItemList.add(scope1EmissionsOf_T1S_sectionItem);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct_Unit);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct_Unit);
    
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    
    formItemList.add(methodTypeItem);    
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_2_OLD() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SectionItem purchasedProduct_sectionItem = new SectionItem();    
    purchasedProduct_sectionItem.setName("purchasedProduct_sectionItem");
    purchasedProduct_sectionItem.setDefaultValue("Purchased Product from Tier One Supplier");
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
    
    //-- Section for Scope 1 and 2 Emissions
    /*
    SectionItem scope1EmissionsOf_T1S_sectionItem = new SectionItem();    
    scope1EmissionsOf_T1S_sectionItem.setName("scope1EmissionsOf_T1S_sectionItem");
    scope1EmissionsOf_T1S_sectionItem.setDefaultValue("Scope 1 & 2 Emissions Of Tier One Supplier");
    */
    
    IblFloatItem scope1EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope1EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope1EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope1EmissionsOf_T1S_ForPurchasedProduct_Unit");
    IblFloatItem scope2EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope2EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope2EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope2EmissionsOf_T1S_ForPurchasedProduct_Unit");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");

    purchasedProduct_sectionItem.setItemIds("sourceDescription",
                                            "supplierName",
                                            "supplierContact",
                                            "purchasedProductType",
                                            "purchasedProductName",
                                            "quantityOfPurchasedProduct",
                                            "quantityOfPurchasedProduct_Unit",            
                                                 "scope1EmissionsOf_T1S_ForPurchasedProduct",
                                                 "scope1EmissionsOf_T1S_ForPurchasedProduct_Unit",
                                                 "scope2EmissionsOf_T1S_ForPurchasedProduct",
                                                 "scope2EmissionsOf_T1S_ForPurchasedProduct_Unit",
                                                 "userNotesOnData",
                                                 "dataBeginDate",
                                                 "dataEndDate"
                                                );
    purchasedProduct_sectionItem.setSectionExpanded(true);   
    
    
    //-- Section for Material Inputs for Purchased Product    
    SectionItem materialUsedBy_T1S_sectionItem = new SectionItem();    
    materialUsedBy_T1S_sectionItem.setName("scope1EmissionsOf_T1S_sectionItem");
    materialUsedBy_T1S_sectionItem.setDefaultValue("Materials Used By Tier One Suppler for purchased product");


    //IblGridEditorItem materialUsedBy_T1S_InfoItems = new IblGridEditorItem("materialUsedBy_T1S_Grid",purchasedProductForm_2 );    
    IblGridEditorItem materialUsedBy_T1S_InfoItems = new IblGridEditorItem("materialUsedBy_T1S_Info");
    materialUsedBy_T1S_InfoItems.setGridFields(getPurchasedProductListGridFields_2_2());    
    materialUsedBy_T1S_InfoItems.setShowTitle(false);
    materialUsedBy_T1S_InfoItems.setGridDataSource(materialUsedBy_T1S_InfoDS);
    materialUsedBy_T1S_InfoItems.setStartRow(Boolean.TRUE);

    
    /*
    Criteria productCriteria = new Criteria();    
    //Integer purchasedProductInfoId = (Integer) purchasedProductForm_2.getValue("id");
    //Record purchasedProductForm_2Record = (Record)purchasedProductForm_2.getValuesAsRecord();
    //Integer purchasedProductInfoId = (Integer) purchasedProductForm_2Record.getAttributeAsInt("id");
    Integer purchasedProductInfoId=25;
    //materialUsedBy_T1S_InfoItems.setCriteriaField("id");
    
    productCriteria.addCriteria("purchasedProductInfoId", purchasedProductInfoId);
    
    //materialUsedBy_T1S_InfoDS.setCriteria(productCriteria);
    materialUsedBy_T1S_InfoDS.fetchData(productCriteria, new DSCallback() {
        @Override
        public void execute(DSResponse response, Object rawData, DSRequest request) {
            DynamicForm exampleForm = (DynamicForm) Canvas.getById("purchasedProductForm_2");
            exampleForm.editRecord(response.getData()[0]);
            SC.say("I am in execute" + response.getData()[0]);
        }
    });
   */
    
    
/*    
    IblTextItem materialUsedBy_T1S_ForPurchasedProduct = new IblTextItem("materialUsedBy_T1S_ForPurchasedProduct","");    
    IblFloatItem amountOfMaterialUsedBy_T1S_ForPurchasedProduct = new IblFloatItem("amountOfMaterialUsedBy_T1S_ForPurchasedProduct");    
    SelectItem_Unit amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_Material = new TextItem("EF_Material");        
    SelectItem_EFUnit EF_Material_Unit = new SelectItem_EFUnit("EF_Material_Unit");    
    materialUsedBy_T1S_sectionItem.setItemIds("materialUsedBy_T1S_ForPurchasedProduct",
                                                 "amountOfMaterialUsedBy_T1S_ForPurchasedProduct",
                                                 "amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit",
                                                 "EF_Material",
                                                 "EF_Material_Unit"
                                                );
 * 
 */
    materialUsedBy_T1S_sectionItem.setItemIds("materialUsedBy_T1S_Info");
    materialUsedBy_T1S_sectionItem.setSectionExpanded(false);   
        
    //-- Section for Transport of material inputs to tier 1 supplier
    SectionItem transportOfMaterialTo_T1S_sectionItem = new SectionItem();    
    transportOfMaterialTo_T1S_sectionItem.setName("transportOfMaterialTo_T1S_sectionItem");
    transportOfMaterialTo_T1S_sectionItem.setDefaultValue("Transport of Materials Used By Tier One Suppler for purchased product");
    
    IblFloatItem distanceOfTransportOfMaterialInputsTo_T1S = new IblFloatItem("distanceOfTransportOfMaterialInputsTo_T1S");    
    SelectItem_Unit distanceOfTransportOfMaterialInputsTo_T1S_Unit = new SelectItem_Unit("distanceOfTransportOfMaterialInputsTo_T1S_Unit");    
    IblFloatItem massOfMateriaInput = new IblFloatItem("massOfMateriaInput");    
    SelectItem_Unit massOfMateriaInput_Unit = new SelectItem_Unit("massOfMateriaInput_Unit");    
    TextItem vehicleType = new TextItem("vehicleType");        
    TextItem EF_VehicleType = new TextItem("EF_VehicleType");        
    SelectItem_EFUnit EF_VehicleType_Unit = new SelectItem_EFUnit("EF_VehicleType_Unit");                
    transportOfMaterialTo_T1S_sectionItem.setItemIds("distanceOfTransportOfMaterialInputsTo_T1S",
                                                 "distanceOfTransportOfMaterialInputsTo_T1S_Unit",
                                                 "massOfMateriaInput",
                                                 "massOfMateriaInput_Unit",
                                                 "vehicleType",
                                                 "EF_VehicleType",
                                                 "EF_VehicleType_Unit"
                                                );
    
    transportOfMaterialTo_T1S_sectionItem.setSectionExpanded(false);   
    
    //-- Section for Mass Of Waste From T1S for purchased product
    SectionItem massOfWasteFrom_T1S_sectionItem = new SectionItem();    
    massOfWasteFrom_T1S_sectionItem.setName("massOfWasteFrom_T1S_sectionItem");
    massOfWasteFrom_T1S_sectionItem.setDefaultValue("Mass of Waste  By Tier One Suppler for purchased product");
    //massOfWasteFrom_T1S_sectionItem.setColSpan(4);
    //massOfWasteFrom_T1S_sectionItem.setWidth("100%");
    
    IblFloatItem massOfWasteFrom_T1S_ForPurchasedProduct = new IblFloatItem("massOfWasteFrom_T1S_ForPurchasedProduct");    
    SelectItem_Unit massOfWasteFrom_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("massOfWasteFrom_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_WasteActivity = new TextItem("EF_WasteActivity");        
    SelectItem_EFUnit EF_WasteActivity_Unit = new SelectItem_EFUnit("EF_WasteActivity_Unit");    
    
    massOfWasteFrom_T1S_sectionItem.setItemIds("massOfWasteFrom_T1S_ForPurchasedProduct",
                                                 "massOfWasteFrom_T1S_ForPurchasedProduct_Unit",
                                                 "EF_WasteActivity",
                                                 "EF_WasteActivity_Unit"
                                                );       
    
    massOfWasteFrom_T1S_sectionItem.setSectionExpanded(false);   
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(purchasedProduct_sectionItem);
    
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
        
    //formItemList.add(scope1EmissionsOf_T1S_sectionItem);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct_Unit);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct_Unit);

    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    
    formItemList.add(materialUsedBy_T1S_sectionItem);    
    formItemList.add(materialUsedBy_T1S_InfoItems);    
    
    /*
    formItemList.add(materialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_Material);
    formItemList.add(EF_Material_Unit);
     * 
     */

    formItemList.add(transportOfMaterialTo_T1S_sectionItem);    
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S);
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S_Unit);
    formItemList.add(massOfMateriaInput);
    formItemList.add(massOfMateriaInput_Unit);
    formItemList.add(vehicleType);
    formItemList.add(EF_VehicleType);
    formItemList.add(EF_VehicleType_Unit);

    formItemList.add(massOfWasteFrom_T1S_sectionItem);    
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct);
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_WasteActivity);
    formItemList.add(EF_WasteActivity_Unit);	
        
    formItemList.add(methodTypeItem);    
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_2_TAB() {

//-- setValidators for the forms for common types.
    initializeValidators();

    Tab purchasedProduct_sectionItem = new Tab();    
    purchasedProduct_sectionItem.setID("purchasedProduct_sectionItem");
    purchasedProduct_sectionItem.setTitle("Purchased Product from Tier One Supplier");
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
    
    //-- Section for Scope 1 and 2 Emissions
    /*
    SectionItem scope1EmissionsOf_T1S_sectionItem = new SectionItem();    
    scope1EmissionsOf_T1S_sectionItem.setName("scope1EmissionsOf_T1S_sectionItem");
    scope1EmissionsOf_T1S_sectionItem.setDefaultValue("Scope 1 & 2 Emissions Of Tier One Supplier");
    */
    
    IblFloatItem scope1EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope1EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope1EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope1EmissionsOf_T1S_ForPurchasedProduct_Unit");
    IblFloatItem scope2EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope2EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope2EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope2EmissionsOf_T1S_ForPurchasedProduct_Unit");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");

    DynamicForm purchaseProductForm_2_2 = new DynamicForm();
    
    purchaseProductForm_2_2.setFields(sourceDescriptionItem,
                                            supplierNameItem,
                                            supplierContactItem,
                                            purchasedProductTypeItem,
                                            purchasedProductNameItem,
                                            quantityOfPurchasedProductItem,
                                            quantityOfPurchasedProduct_UnitItem,            
                                                 scope1EmissionsOf_T1S_ForPurchasedProduct,
                                                 scope1EmissionsOf_T1S_ForPurchasedProduct_Unit,
                                                 scope2EmissionsOf_T1S_ForPurchasedProduct,
                                                 scope2EmissionsOf_T1S_ForPurchasedProduct_Unit,
                                                 userNotesOnDataItem,
                                                 dataBeginDateItem,
                                                 dataEndDateItem
                                                );
    
    purchasedProduct_sectionItem.setPane(purchaseProductForm_2_2);
    
    //-- Section for Material Inputs for Purchased Product    
    Tab materialUsedBy_T1S_sectionItem = new Tab();    
    materialUsedBy_T1S_sectionItem.setID("materialUsedBy_T1S_InfoTab");
    materialUsedBy_T1S_sectionItem.setTitle("Materials Used By Tier One Suppler for purchased product");

    //IblGridEditorItem materialUsedBy_T1S_InfoItems = new IblGridEditorItem("materialUsedBy_T1S_Grid",purchasedProductForm_2 );    
    ListGrid materialUsedBy_T1S_InfoGrid = new ListGrid();    
    materialUsedBy_T1S_InfoGrid.setFields(getPurchasedProductListGridFields_2_2());
    materialUsedBy_T1S_InfoGrid.setDataSource(materialUsedBy_T1S_InfoDS);
    materialUsedBy_T1S_InfoGrid.setCanEdit(Boolean.TRUE); 
    materialUsedBy_T1S_InfoGrid.setModalEditing(Boolean.TRUE);
    materialUsedBy_T1S_InfoGrid.setEditEvent(ListGridEditEvent.CLICK);
    materialUsedBy_T1S_InfoGrid.setListEndEditAction(RowEndEditAction.NEXT);
    materialUsedBy_T1S_InfoGrid.setCanRemoveRecords(true);  
    
    materialUsedBy_T1S_sectionItem.setPane(materialUsedBy_T1S_InfoGrid);

/*    
    //-- Section for Transport of material inputs to tier 1 supplier
    SectionItem transportOfMaterialTo_T1S_sectionItem = new SectionItem();    
    transportOfMaterialTo_T1S_sectionItem.setName("transportOfMaterialTo_T1S_sectionItem");
    transportOfMaterialTo_T1S_sectionItem.setDefaultValue("Transport of Materials Used By Tier One Suppler for purchased product");
    
    IblFloatItem distanceOfTransportOfMaterialInputsTo_T1S = new IblFloatItem("distanceOfTransportOfMaterialInputsTo_T1S");    
    SelectItem_Unit distanceOfTransportOfMaterialInputsTo_T1S_Unit = new SelectItem_Unit("distanceOfTransportOfMaterialInputsTo_T1S_Unit");    
    IblFloatItem massOfMateriaInput = new IblFloatItem("massOfMateriaInput");    
    SelectItem_Unit massOfMateriaInput_Unit = new SelectItem_Unit("massOfMateriaInput_Unit");    
    TextItem vehicleType = new TextItem("vehicleType");        
    TextItem EF_VehicleType = new TextItem("EF_VehicleType");        
    SelectItem_EFUnit EF_VehicleType_Unit = new SelectItem_EFUnit("EF_VehicleType_Unit");                
    transportOfMaterialTo_T1S_sectionItem.setItemIds("distanceOfTransportOfMaterialInputsTo_T1S",
                                                 "distanceOfTransportOfMaterialInputsTo_T1S_Unit",
                                                 "massOfMateriaInput",
                                                 "massOfMateriaInput_Unit",
                                                 "vehicleType",
                                                 "EF_VehicleType",
                                                 "EF_VehicleType_Unit"
                                                );
    
    transportOfMaterialTo_T1S_sectionItem.setSectionExpanded(false);   
    
    //-- Section for Mass Of Waste From T1S for purchased product
    SectionItem massOfWasteFrom_T1S_sectionItem = new SectionItem();    
    massOfWasteFrom_T1S_sectionItem.setName("massOfWasteFrom_T1S_sectionItem");
    massOfWasteFrom_T1S_sectionItem.setDefaultValue("Mass of Waste  By Tier One Suppler for purchased product");
    //massOfWasteFrom_T1S_sectionItem.setColSpan(4);
    //massOfWasteFrom_T1S_sectionItem.setWidth("100%");
    
    IblFloatItem massOfWasteFrom_T1S_ForPurchasedProduct = new IblFloatItem("massOfWasteFrom_T1S_ForPurchasedProduct");    
    SelectItem_Unit massOfWasteFrom_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("massOfWasteFrom_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_WasteActivity = new TextItem("EF_WasteActivity");        
    SelectItem_EFUnit EF_WasteActivity_Unit = new SelectItem_EFUnit("EF_WasteActivity_Unit");    
    
    massOfWasteFrom_T1S_sectionItem.setItemIds("massOfWasteFrom_T1S_ForPurchasedProduct",
                                                 "massOfWasteFrom_T1S_ForPurchasedProduct_Unit",
                                                 "EF_WasteActivity",
                                                 "EF_WasteActivity_Unit"
                                                );       
    
    massOfWasteFrom_T1S_sectionItem.setSectionExpanded(false);   
*/    
    TabSet newTabSet = new TabSet();
    newTabSet.addTab(purchasedProduct_sectionItem);
    newTabSet.addTab(materialUsedBy_T1S_sectionItem);
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
/*    
    formItemList.add(purchasedProduct_sectionItem);
    
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
        
    //formItemList.add(scope1EmissionsOf_T1S_sectionItem);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct_Unit);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct_Unit);

    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    
    formItemList.add(materialUsedBy_T1S_sectionItem);    
    formItemList.add(materialUsedBy_T1S_InfoItems);    
*/    
    /*
    formItemList.add(materialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_Material);
    formItemList.add(EF_Material_Unit);
     * 
     */
/*
    formItemList.add(transportOfMaterialTo_T1S_sectionItem);    
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S);
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S_Unit);
    formItemList.add(massOfMateriaInput);
    formItemList.add(massOfMateriaInput_Unit);
    formItemList.add(vehicleType);
    formItemList.add(EF_VehicleType);
    formItemList.add(EF_VehicleType_Unit);

    formItemList.add(massOfWasteFrom_T1S_sectionItem);    
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct);
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_WasteActivity);
    formItemList.add(EF_WasteActivity_Unit);	
*/
    
    formItemList.add(methodTypeItem);    
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_2_Sections() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SectionItem purchasedProduct_sectionItem = new SectionItem();    
    purchasedProduct_sectionItem.setName("purchasedProduct_sectionItem");
    purchasedProduct_sectionItem.setDefaultValue("Purchased Product from Tier One Supplier");
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
    
    //-- Section for Scope 1 and 2 Emissions
    /*
    SectionItem scope1EmissionsOf_T1S_sectionItem = new SectionItem();    
    scope1EmissionsOf_T1S_sectionItem.setName("scope1EmissionsOf_T1S_sectionItem");
    scope1EmissionsOf_T1S_sectionItem.setDefaultValue("Scope 1 & 2 Emissions Of Tier One Supplier");
    */
    
    IblFloatItem scope1EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope1EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope1EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope1EmissionsOf_T1S_ForPurchasedProduct_Unit");
    IblFloatItem scope2EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope2EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope2EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope2EmissionsOf_T1S_ForPurchasedProduct_Unit");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");

    purchasedProduct_sectionItem.setItemIds("sourceDescription",
                                            "supplierName",
                                            "supplierContact",
                                            "purchasedProductType",
                                            "purchasedProductName",
                                            "quantityOfPurchasedProduct",
                                            "quantityOfPurchasedProduct_Unit",            
                                                 "scope1EmissionsOf_T1S_ForPurchasedProduct",
                                                 "scope1EmissionsOf_T1S_ForPurchasedProduct_Unit",
                                                 "scope2EmissionsOf_T1S_ForPurchasedProduct",
                                                 "scope2EmissionsOf_T1S_ForPurchasedProduct_Unit",
                                                 "userNotesOnData",
                                                 "dataBeginDate",
                                                 "dataEndDate"
                                                );
    purchasedProduct_sectionItem.setSectionExpanded(true);   
    
    
    //-- Section for Material Inputs for Purchased Product    
    SectionItem materialUsedBy_T1S_sectionItem = new SectionItem();    
    materialUsedBy_T1S_sectionItem.setName("scope1EmissionsOf_T1S_sectionItem");
    materialUsedBy_T1S_sectionItem.setDefaultValue("Materials Used By Tier One Suppler for purchased product");


    //IblGridEditorItem materialUsedBy_T1S_InfoItems = new IblGridEditorItem("materialUsedBy_T1S_Grid",purchasedProductForm_2 );    
    IblGridEditorItem materialUsedBy_T1S_InfoItems = new IblGridEditorItem("materialUsedBy_T1S_Info");    
    materialUsedBy_T1S_InfoItems.setShowTitle(false);
    materialUsedBy_T1S_InfoItems.setGridDataSource(materialUsedBy_T1S_InfoDS);
    materialUsedBy_T1S_InfoItems.setStartRow(Boolean.TRUE);
    materialUsedBy_T1S_InfoItems.setDataPath("materialUsedBy_T1S_Info");
    
    /*
    Criteria productCriteria = new Criteria();    
    //Integer purchasedProductInfoId = (Integer) purchasedProductForm_2.getValue("id");
    //Record purchasedProductForm_2Record = (Record)purchasedProductForm_2.getValuesAsRecord();
    //Integer purchasedProductInfoId = (Integer) purchasedProductForm_2Record.getAttributeAsInt("id");
    Integer purchasedProductInfoId=25;
    //materialUsedBy_T1S_InfoItems.setCriteriaField("id");
    
    productCriteria.addCriteria("purchasedProductInfoId", purchasedProductInfoId);
    
    //materialUsedBy_T1S_InfoDS.setCriteria(productCriteria);
    materialUsedBy_T1S_InfoDS.fetchData(productCriteria, new DSCallback() {
        @Override
        public void execute(DSResponse response, Object rawData, DSRequest request) {
            DynamicForm exampleForm = (DynamicForm) Canvas.getById("purchasedProductForm_2");
            exampleForm.editRecord(response.getData()[0]);
            SC.say("I am in execute" + response.getData()[0]);
        }
    });
   */
    
    
/*    
    IblTextItem materialUsedBy_T1S_ForPurchasedProduct = new IblTextItem("materialUsedBy_T1S_ForPurchasedProduct","");    
    IblFloatItem amountOfMaterialUsedBy_T1S_ForPurchasedProduct = new IblFloatItem("amountOfMaterialUsedBy_T1S_ForPurchasedProduct");    
    SelectItem_Unit amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_Material = new TextItem("EF_Material");        
    SelectItem_EFUnit EF_Material_Unit = new SelectItem_EFUnit("EF_Material_Unit");    
    materialUsedBy_T1S_sectionItem.setItemIds("materialUsedBy_T1S_ForPurchasedProduct",
                                                 "amountOfMaterialUsedBy_T1S_ForPurchasedProduct",
                                                 "amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit",
                                                 "EF_Material",
                                                 "EF_Material_Unit"
                                                );
 * 
 */
    materialUsedBy_T1S_sectionItem.setItemIds("materialUsedBy_T1S_Info");
    materialUsedBy_T1S_sectionItem.setSectionExpanded(false);   
        
    //-- Section for Transport of material inputs to tier 1 supplier
    SectionItem transportOfMaterialTo_T1S_sectionItem = new SectionItem();    
    transportOfMaterialTo_T1S_sectionItem.setName("transportOfMaterialTo_T1S_sectionItem");
    transportOfMaterialTo_T1S_sectionItem.setDefaultValue("Transport of Materials Used By Tier One Suppler for purchased product");
    
    IblFloatItem distanceOfTransportOfMaterialInputsTo_T1S = new IblFloatItem("distanceOfTransportOfMaterialInputsTo_T1S");    
    SelectItem_Unit distanceOfTransportOfMaterialInputsTo_T1S_Unit = new SelectItem_Unit("distanceOfTransportOfMaterialInputsTo_T1S_Unit");    
    IblFloatItem massOfMateriaInput = new IblFloatItem("massOfMateriaInput");    
    SelectItem_Unit massOfMateriaInput_Unit = new SelectItem_Unit("massOfMateriaInput_Unit");    
    TextItem vehicleType = new TextItem("vehicleType");        
    TextItem EF_VehicleType = new TextItem("EF_VehicleType");        
    SelectItem_EFUnit EF_VehicleType_Unit = new SelectItem_EFUnit("EF_VehicleType_Unit");                
    transportOfMaterialTo_T1S_sectionItem.setItemIds("distanceOfTransportOfMaterialInputsTo_T1S",
                                                 "distanceOfTransportOfMaterialInputsTo_T1S_Unit",
                                                 "massOfMateriaInput",
                                                 "massOfMateriaInput_Unit",
                                                 "vehicleType",
                                                 "EF_VehicleType",
                                                 "EF_VehicleType_Unit"
                                                );
    
    transportOfMaterialTo_T1S_sectionItem.setSectionExpanded(false);   
    
    //-- Section for Mass Of Waste From T1S for purchased product
    SectionItem massOfWasteFrom_T1S_sectionItem = new SectionItem();    
    massOfWasteFrom_T1S_sectionItem.setName("massOfWasteFrom_T1S_sectionItem");
    massOfWasteFrom_T1S_sectionItem.setDefaultValue("Mass of Waste  By Tier One Suppler for purchased product");
    //massOfWasteFrom_T1S_sectionItem.setColSpan(4);
    //massOfWasteFrom_T1S_sectionItem.setWidth("100%");
    
    IblFloatItem massOfWasteFrom_T1S_ForPurchasedProduct = new IblFloatItem("massOfWasteFrom_T1S_ForPurchasedProduct");    
    SelectItem_Unit massOfWasteFrom_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("massOfWasteFrom_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_WasteActivity = new TextItem("EF_WasteActivity");        
    SelectItem_EFUnit EF_WasteActivity_Unit = new SelectItem_EFUnit("EF_WasteActivity_Unit");    
    
    massOfWasteFrom_T1S_sectionItem.setItemIds("massOfWasteFrom_T1S_ForPurchasedProduct",
                                                 "massOfWasteFrom_T1S_ForPurchasedProduct_Unit",
                                                 "EF_WasteActivity",
                                                 "EF_WasteActivity_Unit"
                                                );       
    
    massOfWasteFrom_T1S_sectionItem.setSectionExpanded(false);   
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(purchasedProduct_sectionItem);
    
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
        
    //formItemList.add(scope1EmissionsOf_T1S_sectionItem);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct_Unit);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct_Unit);

    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    
    formItemList.add(materialUsedBy_T1S_sectionItem);    
    formItemList.add(materialUsedBy_T1S_InfoItems);    
    
    /*
    formItemList.add(materialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_Material);
    formItemList.add(EF_Material_Unit);
     * 
     */

    formItemList.add(transportOfMaterialTo_T1S_sectionItem);    
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S);
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S_Unit);
    formItemList.add(massOfMateriaInput);
    formItemList.add(massOfMateriaInput_Unit);
    formItemList.add(vehicleType);
    formItemList.add(EF_VehicleType);
    formItemList.add(EF_VehicleType_Unit);

    formItemList.add(massOfWasteFrom_T1S_sectionItem);    
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct);
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_WasteActivity);
    formItemList.add(EF_WasteActivity_Unit);	
        
    formItemList.add(methodTypeItem);    
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_2_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
    
    IblFloatItem scope1EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope1EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope1EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope1EmissionsOf_T1S_ForPurchasedProduct_Unit");
    IblFloatItem scope2EmissionsOf_T1S_ForPurchasedProduct = new IblFloatItem("scope2EmissionsOf_T1S_ForPurchasedProduct");    
    SelectItem_Unit scope2EmissionsOf_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("scope2EmissionsOf_T1S_ForPurchasedProduct_Unit");
          
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);

    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope1EmissionsOf_T1S_ForPurchasedProduct_Unit);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct);
    formItemList.add(scope2EmissionsOf_T1S_ForPurchasedProduct_Unit);
	            
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_2_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
        
    IblTextItem materialUsedBy_T1S_ForPurchasedProduct = new IblTextItem("materialUsedBy_T1S_ForPurchasedProduct","");    
    IblFloatItem amountOfMaterialUsedBy_T1S_ForPurchasedProduct = new IblFloatItem("amountOfMaterialUsedBy_T1S_ForPurchasedProduct");    
    SelectItem_Unit amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_Material = new TextItem("EF_Material");        
    SelectItem_EFUnit EF_Material_Unit = new SelectItem_EFUnit("EF_Material_Unit");    
        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
	
    formItemList.add(materialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_Material);
    formItemList.add(EF_Material_Unit);
	            
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getMaterialUsedBy_T1S_FormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();
       
    IblTextItem materialUsedBy_T1S_ForPurchasedProduct = new IblTextItem("materialUsedBy_T1S_ForPurchasedProduct","");    
    IblFloatItem amountOfMaterialUsedBy_T1S_ForPurchasedProduct = new IblFloatItem("amountOfMaterialUsedBy_T1S_ForPurchasedProduct");    
    SelectItem_Unit amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_Material = new TextItem("EF_Material");        
    SelectItem_EFUnit EF_Material_Unit = new SelectItem_EFUnit("EF_Material_Unit");    
        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    //TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
	
    formItemList.add(materialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct);
    formItemList.add(amountOfMaterialUsedBy_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_Material);
    formItemList.add(EF_Material_Unit);
	            
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    //formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_2_3() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");
    
    IblFloatItem distanceOfTransportOfMaterialInputsTo_T1S = new IblFloatItem("distanceOfTransportOfMaterialInputsTo_T1S");    
    SelectItem_Unit distanceOfTransportOfMaterialInputsTo_T1S_Unit = new SelectItem_Unit("distanceOfTransportOfMaterialInputsTo_T1S_Unit");    
    IblFloatItem massOfMateriaInput = new IblFloatItem("massOfMateriaInput");    
    SelectItem_Unit massOfMateriaInput_Unit = new SelectItem_Unit("massOfMateriaInput_Unit");    
    TextItem vehicleType = new TextItem("vehicleType");        
    TextItem EF_VehicleType = new TextItem("EF_VehicleType");        
    SelectItem_EFUnit EF_VehicleType_Unit = new SelectItem_EFUnit("EF_VehicleType_Unit");                
            
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
	
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S);
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S_Unit);
    formItemList.add(massOfMateriaInput);
    formItemList.add(massOfMateriaInput_Unit);
    formItemList.add(vehicleType);
    formItemList.add(EF_VehicleType);
    formItemList.add(EF_VehicleType_Unit);
	            
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getMaterialTransportTo_T1S_FormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();
    
    //IblTextItem materialUsedBy_T1S_ForPurchasedProduct = new IblTextItem("materialUsedBy_T1S_ForPurchasedProduct",""); 
    
    //final IntegerItem purchasedProductInfoId = new IntegerItem("purchasedProductInfoId");
    final SelectItem materialUsedBy_T1S_ForPurchasedProduct = new SelectItem("materialUsedBy_T1S_ForPurchasedProduct") {
        @Override
        protected Criteria getPickListFilterCriteria() {
            /*
            Criteria criteria = new Criteria();
            criteria = getCurrentInventoryYear();
            Integer id = (Integer) purchasedProductInfoId.getValue();
            criteria.addCriteria("purchasedProductInfoId", id);   
            SC.say("purchasedProductInfoId is: "+ purchasedProductInfoId);
            */
            Criteria criteria = IblCompoundEditor.getCriteria();
            return criteria ;
        }
    };

    materialUsedBy_T1S_ForPurchasedProduct.setName("materialUsedBy_T1S_ForPurchasedProduct");
    //fuelTypeItem.setPickListWidth(310);
    //fuelTypeItem.setTitle("Fuel Type");
    materialUsedBy_T1S_ForPurchasedProduct.setOptionDataSource(materialUsedBy_T1S_InfoDS);
    materialUsedBy_T1S_ForPurchasedProduct.setAddUnknownValues(Boolean.TRUE);
    //materialUsedBy_T1S_ForPurchasedProduct.setOtherValue("Add New material..");
    
    IblFloatItem distanceOfTransportOfMaterialInputsTo_T1S = new IblFloatItem("distanceOfTransportOfMaterialInputsTo_T1S");    
    
    //SelectItem_Unit distanceOfTransportOfMaterialInputsTo_T1S_Unit = new SelectItem_Unit("distanceOfTransportOfMaterialInputsTo_T1S_Unit");
    SelectItem_DistanceUnit distanceOfTransportOfMaterialInputsTo_T1S_Unit = new SelectItem_DistanceUnit("distanceOfTransportOfMaterialInputsTo_T1S_Unit");
    
    IblFloatItem massOfMateriaInput = new IblFloatItem("massOfMateriaInput");    
    //SelectItem_Unit massOfMateriaInput_Unit = new SelectItem_Unit("massOfMateriaInput_Unit"); 
    StaticTextItem massOfMateriaInput_Unit = new StaticTextItem("massOfMateriaInput_Unit"); 
    massOfMateriaInput_Unit.setTitle("Unit");
    massOfMateriaInput_Unit.setDefaultValue("Tonnes");    
    
    TextItem vehicleType = new TextItem("vehicleType");        
    TextItem EF_VehicleType = new TextItem("EF_VehicleType");        
    
    //SelectItem_EFUnit EF_VehicleType_Unit = new SelectItem_EFUnit("EF_VehicleType_Unit");
    final StaticTextItem EF_VehicleType_Unit = new StaticTextItem("EF_VehicleType_Unit");                
    EF_VehicleType_Unit.setTitle("Unit");
    EF_VehicleType_Unit.setDefaultValue("(Kg CO2e/Tonne)/Km");    
    
    final Map<String, String> EF_Units = new HashMap<String, String>();  
    EF_Units.put("Km", "(Kg CO2e/Tonne)/Km");  
    EF_Units.put("Mile","(Kg CO2e/Tonne)/Mile");  

    distanceOfTransportOfMaterialInputsTo_T1S_Unit.addChangeHandler(new ChangeHandler() {  
        public void onChange(ChangeEvent event) {  
            String selectedItem = (String) event.getValue();  
            EF_VehicleType_Unit.setValue(EF_Units.get(selectedItem));  
        }  
    });      
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    //TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();

    formItemList.add(materialUsedBy_T1S_ForPurchasedProduct);
    //formItemList.add(purchasedProductInfoId);
    
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S);
    formItemList.add(distanceOfTransportOfMaterialInputsTo_T1S_Unit);
    formItemList.add(massOfMateriaInput);
    formItemList.add(massOfMateriaInput_Unit);
    formItemList.add(vehicleType);
    formItemList.add(EF_VehicleType);
    formItemList.add(EF_VehicleType_Unit);
	            
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    //formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getWasteOutputFrom_T1S_FormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    
    IblFloatItem massOfWasteFrom_T1S_ForPurchasedProduct = new IblFloatItem("massOfWasteFrom_T1S_ForPurchasedProduct");    
    SelectItem_Unit massOfWasteFrom_T1S_ForPurchasedProduct_Unit = new SelectItem_Unit("massOfWasteFrom_T1S_ForPurchasedProduct_Unit");    
    TextItem EF_WasteActivity = new TextItem("EF_WasteActivity");        
    SelectItem_EFUnit EF_WasteActivity_Unit = new SelectItem_EFUnit("EF_WasteActivity_Unit");    
        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    //TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Supplier Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
	    
    formItemList.add(sourceDescriptionItem);            
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct);
    formItemList.add(massOfWasteFrom_T1S_ForPurchasedProduct_Unit);
    formItemList.add(EF_WasteActivity);
    formItemList.add(EF_WasteActivity_Unit);	
            
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    //formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
}
public FormItem[] getPurchasedProductFormFields_3() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");

    IblFloatItem secondary_EF_Item = new IblFloatItem("secondary_EF");     
    
    
    //SelectItem_EFUnit secondary_EF_UnitItem = new SelectItem_EFUnit("secondary_EF_Unit");    
    /*
    final SelectItem secondary_EF_UnitItem = new SelectItem("secondary_EF_Unit");    
    final Map<String, String[]> EF_Units = new HashMap<String, String[]>();  
    EF_Units.put("Kg", new String[]{"Kg CO2e/Kg"});  
    EF_Units.put("Piece", new String[]{"Kg CO2e/Piece"});  
    EF_Units.put("$", new String[]{"Kg CO2e/$"});  

    quantityOfPurchasedProduct_UnitItem.addChangeHandler(new ChangeHandler() {  
        public void onChange(ChangeEvent event) {  
            String selectedItem = (String) event.getValue();  
            secondary_EF_UnitItem.setValueMap(EF_Units.get(selectedItem));  
        }  
    });  
    */

    final StaticTextItem secondary_EF_UnitItem = new StaticTextItem("secondary_EF_Unit");    
    secondary_EF_UnitItem.setDefaultValue("Kg CO2e/Kg");
    
    final Map<String, String> EF_Units = new HashMap<String, String>();  
    EF_Units.put("Kg", "Kg CO2e/Kg");  
    EF_Units.put("Piece","Kg CO2e/Piece");  
    EF_Units.put("$","Kg CO2e/$");  

    quantityOfPurchasedProduct_UnitItem.addChangeHandler(new ChangeHandler() {  
        public void onChange(ChangeEvent event) {  
            String selectedItem = (String) event.getValue();  
            secondary_EF_UnitItem.setValue(EF_Units.get(selectedItem));  
        }  
    });  
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Material Or Spend Based Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);
    formItemList.add(secondary_EF_Item);
    formItemList.add(secondary_EF_UnitItem);
    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedProductFormFields_3_ORIG() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    SelectItem_ProductType purchasedProductTypeItem = new SelectItem_ProductType("purchasedProductType");    
    TextItem purchasedProductNameItem = new TextItem("purchasedProductName");	
    IblFloatItem quantityOfPurchasedProductItem = new IblFloatItem("quantityOfPurchasedProduct");
    SelectItem_Unit quantityOfPurchasedProduct_UnitItem = new SelectItem_Unit("quantityOfPurchasedProduct_Unit");

    //CanvasItem testCanvasItem = new CanvasItem("testCanvasItem");
    //testCanvasItem.
            
    IblFloatItem massOfPurchasedProduct = new IblFloatItem("massOfPurchasedProduct");    
    SelectItem_Unit massOfPurchasedProduct_Unit = new SelectItem_Unit("massOfPurchasedProduct_Unit");    
    TextItem EF_PurchasedProductPerUnitOfMass = new TextItem("EF_PurchasedProductPerUnitOfMass");        
    SelectItem_EFUnit EF_PurchasedProductPerUnitOfMass_Unit = new SelectItem_EFUnit("EF_PurchasedProductPerUnitOfMass_Unit");    

    IblFloatItem unitOfPurchasedProduct = new IblFloatItem("unitOfPurchasedProduct");    
    SelectItem_Unit unitOfPurchasedProduct_Unit = new SelectItem_Unit("unitOfPurchasedProduct_Unit");    
    TextItem EF_PurchasedProductPerReferenceUnit = new TextItem("EF_PurchasedProductPerReferenceUnit");        
    SelectItem_EFUnit EF_PurchasedProductPerReferenceUnit_Unit = new SelectItem_EFUnit("EF_PurchasedProductPerReferenceUnit_Unit");    

    IblFloatItem valueOfPurchasedProduct = new IblFloatItem("valueOfPurchasedProduct");    
    SelectItem_Unit valueOfPurchasedProduct_Unit = new SelectItem_Unit("valueOfPurchasedProduct_Unit");    
    TextItem EF_PurchasedProductPerUnitOfReferenceValue = new TextItem("EF_PurchasedProductPerUnitOfReferenceValue");        
    SelectItem_EFUnit EF_PurchasedProductPerUnitOfReferenceValue_Unit = new SelectItem_EFUnit("EF_PurchasedProductPerUnitOfReferenceValue_Unit");    
	
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Purchased Goods and Services - Material Or Spend Based Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    formItemList.add(sourceDescriptionItem);    
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(purchasedProductTypeItem);
    formItemList.add(purchasedProductNameItem);
    formItemList.add(quantityOfPurchasedProductItem);
    formItemList.add(quantityOfPurchasedProduct_UnitItem);

    formItemList.add(massOfPurchasedProduct);
    formItemList.add(massOfPurchasedProduct_Unit);
    formItemList.add(EF_PurchasedProductPerUnitOfMass);
    formItemList.add(EF_PurchasedProductPerUnitOfMass_Unit);
	
    formItemList.add(unitOfPurchasedProduct);
    formItemList.add(unitOfPurchasedProduct_Unit);
    formItemList.add(EF_PurchasedProductPerReferenceUnit);
    formItemList.add(EF_PurchasedProductPerReferenceUnit_Unit);
	
    formItemList.add(valueOfPurchasedProduct);
    formItemList.add(valueOfPurchasedProduct_Unit);
    formItemList.add(EF_PurchasedProductPerUnitOfReferenceValue);
    formItemList.add(EF_PurchasedProductPerUnitOfReferenceValue_Unit);

    formItemList.add(userNotesOnDataItem);
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;
    
}

public FormItem[] getPurchasedEnergyFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SelectItem activityType = new SelectItem("activityType");       
    activityType.setValueMap("Upstream emissions of purchased fuels",
                             "Upstream emissions of purchased electricity",
                             "Transmission & Distribution Losses",
                             "Generation of purchased electricity that is sold to end users");
    activityType.setDefaultValue("Upstream emissions of purchased fuels");
    activityType.setWidth("*");
    
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");
    //fuelType.setVisible(Boolean.FALSE);
    
    SelectItem energyType = new SelectItem("energyType");           
    energyType.setValueMap("Electricity","Steam","Heating","Cooling");
    energyType.setDefaultValue("Electricity");
    energyType.setVisible(Boolean.FALSE);
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    TextItem supplierRegionCountry = new TextItem("supplierRegionCountry");    
    
    final RadioGroupItem flag_transAndDistDataType = new RadioGroupItem();  
    flag_transAndDistDataType.setName("flag_transAndDistDataType");  
    flag_transAndDistDataType.setColSpan("*");  
    flag_transAndDistDataType.setRequired(false);  
    flag_transAndDistDataType.setVertical(false);  
    flag_transAndDistDataType.setValueMap("Energy data", "Scope2 data");  
    flag_transAndDistDataType.setRedrawOnChange(true);  
    flag_transAndDistDataType.setTitle("What type of transmission and distribution data you have?");  
    flag_transAndDistDataType.setDefaultValue("Energy data");
    flag_transAndDistDataType.setVisible(Boolean.FALSE);
    
    IblFloatItem energyPurchased = new IblFloatItem("energyPurchased");
    energyPurchased.setTitle("Fuel Purchased");
    SelectItem_Energy_Unit energyPurchased_Unit = new SelectItem_Energy_Unit("energyPurchased_Unit");
    
    CheckboxItem flag_upstreamEF = new CheckboxItem();  
    flag_upstreamEF.setName("flag_upstreamEF");  
    flag_upstreamEF.setTitle("Upstream Emission Factor?");  
    flag_upstreamEF.setRedrawOnChange(true);  
    flag_upstreamEF.setWidth(50);  
    flag_upstreamEF.setDefaultValue(false);    
    flag_upstreamEF.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem EF_UpstreamEnergy = new IblFloatItem("EF_UpstreamEnergy");    
    EF_UpstreamEnergy.setVisible(Boolean.FALSE);
    EF_UpstreamEnergy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit EF_UpstreamEnergy_Unit = new SelectItem_Energy_EFUnit("EF_UpstreamEnergy_Unit");    
    EF_UpstreamEnergy_Unit.setVisible(Boolean.FALSE);   
    EF_UpstreamEnergy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
                    
    IblFloatItem cradleToGate_EF_Energy = new IblFloatItem("cradleToGate_EF_Energy");    
    cradleToGate_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit cradleToGate_EF_Energy_Unit = new SelectItem_Energy_EFUnit("cradleToGate_EF_Energy_Unit");    
    cradleToGate_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem combustion_EF_Energy = new IblFloatItem("combustion_EF_Energy");    
    combustion_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit combustion_EF_Energy_Unit = new SelectItem_Energy_EFUnit("combustion_EF_Energy_Unit");    
    combustion_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem transAndDistLossRate = new IblFloatItem("transAndDistLossRate");
    transAndDistLossRate.setVisible(Boolean.FALSE);

    IblFloatItem scope2EmissionsOfEnergyUse = new IblFloatItem("scope2EmissionsOfEnergyUse");    
    SelectItem_EFUnit scope2EmissionsOfEnergyUse_Unit = new SelectItem_EFUnit("scope2EmissionsOfEnergyUse_Unit");    
    scope2EmissionsOfEnergyUse.setVisible(Boolean.FALSE);
    scope2EmissionsOfEnergyUse_Unit.setVisible(Boolean.FALSE);
    
    activityType.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            if ((Boolean)event.getValue().equals("Upstream emissions of purchased fuels")){
                    event.getForm().getItem("fuelType").show();
                    event.getForm().getItem("energyType").hide();
                    event.getForm().getItem("energyPurchased").setTitle("Fuel Purchased");
                    //SC.say("It changed");
            } else {
                event.getForm().getItem("fuelType").hide();
                event.getForm().getItem("energyType").show();
                event.getForm().getItem("energyPurchased").setTitle("Energy Purchased");
            }

            if ((Boolean)event.getValue().equals("Transmission & Distribution Losses")){
                    event.getForm().getItem("transAndDistLossRate").show();
                    event.getForm().getItem("flag_transAndDistDataType").show();
                    event.getForm().getItem("flag_transAndDistDataType").show();
                    
            } else {
                event.getForm().getItem("transAndDistLossRate").hide();
                event.getForm().getItem("flag_transAndDistDataType").hide();
                event.getForm().getItem("flag_transAndDistDataType").hide();
            }             
        }
    });
    
    flag_transAndDistDataType.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                if((Boolean) flag_transAndDistDataType.getValue().equals("Energy data")){
                    event.getForm().getItem("energyPurchased").show();
                    event.getForm().getItem("energyPurchased_Unit").show();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse").hide();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse_Unit").hide();
                } else {
                    event.getForm().getItem("energyPurchased").hide();
                    event.getForm().getItem("energyPurchased_Unit").hide();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse").show();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse_Unit").show();
                }
            }  
        });  
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel and Energy Related Activities");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(activityType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(fuelType);            
    formItemList.add(energyType);        
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(supplierRegionCountry);    	
    formItemList.add(flag_transAndDistDataType);    			    
    formItemList.add(energyPurchased);    		    
    formItemList.add(energyPurchased_Unit);    	
    formItemList.add(scope2EmissionsOfEnergyUse);    			
    formItemList.add(scope2EmissionsOfEnergyUse_Unit);
    formItemList.add(transAndDistLossRate);    			    
    formItemList.add(flag_upstreamEF);    		    
    formItemList.add(EF_UpstreamEnergy);    		
    formItemList.add(EF_UpstreamEnergy_Unit);    	
    formItemList.add(cradleToGate_EF_Energy);    		
    formItemList.add(cradleToGate_EF_Energy_Unit);    	
    formItemList.add(combustion_EF_Energy);    			
    formItemList.add(combustion_EF_Energy_Unit);    			
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedEnergyFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    TextItem activityType = new TextItem("activityType");       
    activityType.setDefaultValue("Upstream Emissions Of Purchased Fuels");
//  activityType.setWidth("*");
    
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");
    //fuelType.setVisible(Boolean.FALSE);
       
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    TextItem supplierRegionCountry = new TextItem("supplierRegionCountry");    
    
    IblFloatItem energyPurchased = new IblFloatItem("energyPurchased");
    energyPurchased.setTitle("Fuel Purchased");
    SelectItem_Energy_Unit energyPurchased_Unit = new SelectItem_Energy_Unit("energyPurchased_Unit");
    
    CheckboxItem flag_upstreamEF = new CheckboxItem();  
    flag_upstreamEF.setName("flag_upstreamEF");  
    flag_upstreamEF.setTitle("Upstream Emission Factor?");  
    flag_upstreamEF.setRedrawOnChange(true);  
    flag_upstreamEF.setWidth(50);  
    flag_upstreamEF.setDefaultValue(true);    
    flag_upstreamEF.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem EF_UpstreamEnergy = new IblFloatItem("EF_UpstreamEnergy");    
    EF_UpstreamEnergy.setVisible(Boolean.FALSE);
    EF_UpstreamEnergy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit EF_UpstreamEnergy_Unit = new SelectItem_Energy_EFUnit("EF_UpstreamEnergy_Unit");    
    EF_UpstreamEnergy_Unit.setVisible(Boolean.FALSE);   
    EF_UpstreamEnergy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
                    
    IblFloatItem cradleToGate_EF_Energy = new IblFloatItem("cradleToGate_EF_Energy");    
    cradleToGate_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit cradleToGate_EF_Energy_Unit = new SelectItem_Energy_EFUnit("cradleToGate_EF_Energy_Unit");    
    cradleToGate_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem combustion_EF_Energy = new IblFloatItem("combustion_EF_Energy");    
    combustion_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit combustion_EF_Energy_Unit = new SelectItem_Energy_EFUnit("combustion_EF_Energy_Unit");    
    combustion_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel and Energy Related Activities");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(activityType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(fuelType);            
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(supplierRegionCountry);    	
    formItemList.add(energyPurchased);    		    
    formItemList.add(energyPurchased_Unit);    	
    formItemList.add(flag_upstreamEF);    		    
    formItemList.add(EF_UpstreamEnergy);    		
    formItemList.add(EF_UpstreamEnergy_Unit);    	
    formItemList.add(cradleToGate_EF_Energy);    		
    formItemList.add(cradleToGate_EF_Energy_Unit);    	
    formItemList.add(combustion_EF_Energy);    			
    formItemList.add(combustion_EF_Energy_Unit);    			
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedEnergyFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    TextItem activityType = new TextItem("activityType");       
    activityType.setDefaultValue("Upstream Emissions Of Purchased Energy");
        
    SelectItem energyType = new SelectItem("energyType");           
    energyType.setValueMap("Electricity","Steam","Heating","Cooling");
    energyType.setDefaultValue("Electricity");
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    TextItem supplierRegionCountry = new TextItem("supplierRegionCountry");    
        
    IblFloatItem energyPurchased = new IblFloatItem("energyPurchased");
    energyPurchased.setTitle("Energy Purchased");
    SelectItem_Energy_Unit energyPurchased_Unit = new SelectItem_Energy_Unit("energyPurchased_Unit");
    
    CheckboxItem flag_upstreamEF = new CheckboxItem();  
    flag_upstreamEF.setName("flag_upstreamEF");  
    flag_upstreamEF.setTitle("Upstream Emission Factor?");  
    flag_upstreamEF.setRedrawOnChange(true);  
    flag_upstreamEF.setWidth(50);  
    flag_upstreamEF.setDefaultValue(true);    
    flag_upstreamEF.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem EF_UpstreamEnergy = new IblFloatItem("EF_UpstreamEnergy");    
    EF_UpstreamEnergy.setVisible(Boolean.FALSE);
    EF_UpstreamEnergy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit EF_UpstreamEnergy_Unit = new SelectItem_Energy_EFUnit("EF_UpstreamEnergy_Unit");    
    EF_UpstreamEnergy_Unit.setVisible(Boolean.FALSE);   
    EF_UpstreamEnergy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
                    
    IblFloatItem cradleToGate_EF_Energy = new IblFloatItem("cradleToGate_EF_Energy");    
    cradleToGate_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit cradleToGate_EF_Energy_Unit = new SelectItem_Energy_EFUnit("cradleToGate_EF_Energy_Unit");    
    cradleToGate_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem combustion_EF_Energy = new IblFloatItem("combustion_EF_Energy");    
    combustion_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit combustion_EF_Energy_Unit = new SelectItem_Energy_EFUnit("combustion_EF_Energy_Unit");    
    combustion_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel and Energy Related Activities");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(activityType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(energyType);        
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(supplierRegionCountry);    	
    formItemList.add(energyPurchased);    		    
    formItemList.add(energyPurchased_Unit);    	
    formItemList.add(flag_upstreamEF);    		    
    formItemList.add(EF_UpstreamEnergy);    		
    formItemList.add(EF_UpstreamEnergy_Unit);    	
    formItemList.add(cradleToGate_EF_Energy);    		
    formItemList.add(cradleToGate_EF_Energy_Unit);    	
    formItemList.add(combustion_EF_Energy);    			
    formItemList.add(combustion_EF_Energy_Unit);    			
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedEnergyFormFields_3() {

//-- setValidators for the forms for common types.
    initializeValidators();

    TextItem activityType = new TextItem("activityType");       
    activityType.setDefaultValue("Transmission & Distribution Losses");
        
    SelectItem energyType = new SelectItem("energyType");           
    energyType.setValueMap("Electricity","Steam","Heating","Cooling");
    energyType.setDefaultValue("Electricity");
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    TextItem supplierRegionCountry = new TextItem("supplierRegionCountry");    
    
    final RadioGroupItem transAndDistDataType = new RadioGroupItem();  
    transAndDistDataType.setName("transAndDistDataType");  
    transAndDistDataType.setColSpan("*");  
    transAndDistDataType.setRequired(false);  
    transAndDistDataType.setVertical(false);  
    transAndDistDataType.setValueMap("Energy data", "Scope2 data");  
    transAndDistDataType.setRedrawOnChange(true);  
    transAndDistDataType.setTitle("What type of transmission and distribution data you have?");  
    transAndDistDataType.setDefaultValue("Energy data");
    
    IblFloatItem energyPurchased = new IblFloatItem("energyPurchased");
    energyPurchased.setTitle("Energy Purchased");
    SelectItem_Energy_Unit energyPurchased_Unit = new SelectItem_Energy_Unit("energyPurchased_Unit");
    
    CheckboxItem flag_upstreamEF = new CheckboxItem();  
    flag_upstreamEF.setName("flag_upstreamEF");  
    flag_upstreamEF.setTitle("Upstream Emission Factor?");  
    flag_upstreamEF.setRedrawOnChange(true);  
    flag_upstreamEF.setWidth(50);  
    flag_upstreamEF.setDefaultValue(true);    
    flag_upstreamEF.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem EF_UpstreamEnergy = new IblFloatItem("EF_UpstreamEnergy");    
    EF_UpstreamEnergy.setVisible(Boolean.FALSE);
    EF_UpstreamEnergy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit EF_UpstreamEnergy_Unit = new SelectItem_Energy_EFUnit("EF_UpstreamEnergy_Unit");    
    EF_UpstreamEnergy_Unit.setVisible(Boolean.FALSE);   
    EF_UpstreamEnergy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
                    
    IblFloatItem cradleToGate_EF_Energy = new IblFloatItem("cradleToGate_EF_Energy");    
    cradleToGate_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit cradleToGate_EF_Energy_Unit = new SelectItem_Energy_EFUnit("cradleToGate_EF_Energy_Unit");    
    cradleToGate_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem combustion_EF_Energy = new IblFloatItem("combustion_EF_Energy");    
    combustion_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit combustion_EF_Energy_Unit = new SelectItem_Energy_EFUnit("combustion_EF_Energy_Unit");    
    combustion_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem transAndDistLossRate = new IblFloatItem("transAndDistLossRate");
    //transAndDistLossRate.setVisible(Boolean.FALSE);

    IblFloatItem scope2EmissionsOfEnergyUse = new IblFloatItem("scope2EmissionsOfEnergyUse");    
    SelectItem_EFUnit scope2EmissionsOfEnergyUse_Unit = new SelectItem_EFUnit("scope2EmissionsOfEnergyUse_Unit");    
    scope2EmissionsOfEnergyUse.setVisible(Boolean.FALSE);
    scope2EmissionsOfEnergyUse_Unit.setVisible(Boolean.FALSE);
        
    transAndDistDataType.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                if((Boolean) transAndDistDataType.getValue().equals("Energy data")){
                    event.getForm().getItem("energyPurchased").show();
                    event.getForm().getItem("energyPurchased_Unit").show();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse").hide();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse_Unit").hide();
                } else {
                    event.getForm().getItem("energyPurchased").hide();
                    event.getForm().getItem("energyPurchased_Unit").hide();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse").show();
                    event.getForm().getItem("scope2EmissionsOfEnergyUse_Unit").show();
                }
            }  
        });  
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel and Energy Related Activities");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(activityType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(energyType);        
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(supplierRegionCountry);    	
    formItemList.add(transAndDistDataType);    			    
    formItemList.add(energyPurchased);    		    
    formItemList.add(energyPurchased_Unit);    	
    formItemList.add(scope2EmissionsOfEnergyUse);    			
    formItemList.add(scope2EmissionsOfEnergyUse_Unit);
    formItemList.add(transAndDistLossRate);    			    
    formItemList.add(flag_upstreamEF);    		    
    formItemList.add(EF_UpstreamEnergy);    		
    formItemList.add(EF_UpstreamEnergy_Unit);    	
    formItemList.add(cradleToGate_EF_Energy);    		
    formItemList.add(cradleToGate_EF_Energy_Unit);    	
    formItemList.add(combustion_EF_Energy);    			
    formItemList.add(combustion_EF_Energy_Unit);    			
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getPurchasedEnergyFormFields_4() {

//-- setValidators for the forms for common types.
    initializeValidators();

    TextItem activityType = new TextItem("activityType");       
    activityType.setDefaultValue("Generation Of Purchased Energy That Is Sold To End Users");
    activityType.setWidth("*");
    
    SelectItem energyType = new SelectItem("energyType");           
    energyType.setValueMap("Electricity","Steam","Heating","Cooling");
    energyType.setDefaultValue("Electricity");
    energyType.setVisible(Boolean.FALSE);
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem supplierNameItem = new TextItem("supplierName");
    TextItem supplierContactItem = new TextItem("supplierContact");    
    TextItem supplierRegionCountry = new TextItem("supplierRegionCountry");    
        
    IblFloatItem energyPurchased = new IblFloatItem("energyPurchased");
    energyPurchased.setTitle("Energy Purchased");
    SelectItem_Energy_Unit energyPurchased_Unit = new SelectItem_Energy_Unit("energyPurchased_Unit");
    
    CheckboxItem flag_upstreamEF = new CheckboxItem();  
    flag_upstreamEF.setName("flag_upstreamEF");  
    flag_upstreamEF.setTitle("Upstream Emission Factor?");  
    flag_upstreamEF.setRedrawOnChange(true);  
    flag_upstreamEF.setWidth(50);  
    flag_upstreamEF.setDefaultValue(true);    
    flag_upstreamEF.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem EF_UpstreamEnergy = new IblFloatItem("EF_UpstreamEnergy");    
    EF_UpstreamEnergy.setVisible(Boolean.FALSE);
    EF_UpstreamEnergy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit EF_UpstreamEnergy_Unit = new SelectItem_Energy_EFUnit("EF_UpstreamEnergy_Unit");    
    EF_UpstreamEnergy_Unit.setVisible(Boolean.FALSE);   
    EF_UpstreamEnergy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
                    
    IblFloatItem cradleToGate_EF_Energy = new IblFloatItem("cradleToGate_EF_Energy");    
    cradleToGate_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit cradleToGate_EF_Energy_Unit = new SelectItem_Energy_EFUnit("cradleToGate_EF_Energy_Unit");    
    cradleToGate_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

    IblFloatItem combustion_EF_Energy = new IblFloatItem("combustion_EF_Energy");    
    combustion_EF_Energy.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  
    
    SelectItem_Energy_EFUnit combustion_EF_Energy_Unit = new SelectItem_Energy_EFUnit("combustion_EF_Energy_Unit");    
    combustion_EF_Energy_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_upstreamEF");  
        }  
    });  

        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel and Energy Related Activities");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(activityType);        
    formItemList.add(sourceDescriptionItem);                   
    formItemList.add(energyType);        
    formItemList.add(supplierNameItem);
    formItemList.add(supplierContactItem);
    formItemList.add(supplierRegionCountry);    				    
    formItemList.add(energyPurchased);    		    
    formItemList.add(energyPurchased_Unit);    	
    formItemList.add(flag_upstreamEF);    		    
    formItemList.add(EF_UpstreamEnergy);    		
    formItemList.add(EF_UpstreamEnergy_Unit);    	
    formItemList.add(cradleToGate_EF_Energy);    		
    formItemList.add(cradleToGate_EF_Energy_Unit);    	
    formItemList.add(combustion_EF_Energy);    			
    formItemList.add(combustion_EF_Energy_Unit);    			
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getTransportationFormFields_1_ORIG() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SelectItem streamType = new SelectItem("streamType");       
    streamType.setValueMap("Upstream","Downstream");
    streamType.setDefaultValue("Upstream");
    streamType.setWidth("*");
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    
	    
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");    
    fuelType.setStartRow(Boolean.TRUE);
    
    IblFloatItem fuelConsumed = new IblFloatItem("fuelConsumed");
    SelectItem_Energy_Unit fuelConsumed_Unit = new SelectItem_Energy_Unit("fuelConsumed_Unit");
    IblFloatItem EF_Fuel = new IblFloatItem("EF_Fuel");    	
	SelectItem_Energy_EFUnit EF_Fuel_Unit = new SelectItem_Energy_EFUnit("EF_Fuel_Unit");    

    SelectOtherItem refrigerantType = new SelectOtherItem("refrigerantType");       
    refrigerantType.setValueMap("Test 1", "Test 2");
    refrigerantType.setDefaultValue("Test 1");
    refrigerantType.setWidth("*");    
	
    IblFloatItem refrigerantLeakage = new IblFloatItem("refrigerantLeakage");
    SelectItem_Energy_Unit refrigerantLeakage_Unit = new SelectItem_Energy_Unit("refrigerantLeakage_Unit");
    IblFloatItem EF_Refrigerant = new IblFloatItem("EF_Refrigerant");    	
    SelectItem_Energy_EFUnit EF_Refrigerant_Unit = new SelectItem_Energy_EFUnit("EF_Refrigerant_Unit");    
	
    CheckboxItem flag_calculateFuelConsumed = new CheckboxItem();  
    flag_calculateFuelConsumed.setName("flag_calculateFuelConsumed");  
    flag_calculateFuelConsumed.setTitle("Calculate Fuel consumed from distance travelled and vehicle used?");  
    flag_calculateFuelConsumed.setRedrawOnChange(true);  
    flag_calculateFuelConsumed.setWidth(50);  
    flag_calculateFuelConsumed.setDefaultValue(false);    
    flag_calculateFuelConsumed.setColSpan(4);//EndRow(Boolean.TRUE);
	
    IblFloatItem totalDistanceTravelled = new IblFloatItem("totalDistanceTravelled");
    SelectItem_Energy_Unit totalDistanceTravelled_Unit = new SelectItem_Energy_Unit("totalDistanceTravelled_Unit");

    TextItem vehicleType = new TextItem("vehicleType");    
    IblFloatItem fuelEfficiencyOfVehicle = new IblFloatItem("fuelEfficiencyOfVehicle");
    SelectItem fuelEfficiencyOfVehicle_Unit = new SelectItem("fuelEfficiencyOfVehicle_Unit");
    fuelEfficiencyOfVehicle_Unit.setValueMap("1/Km","1/Mile");
    fuelEfficiencyOfVehicle_Unit.setDefaultValue("1/Mile");
	
    CheckboxItem flag_calculateAllocatedFuelUse = new CheckboxItem();  
    flag_calculateAllocatedFuelUse.setName("flag_calculateAllocatedFuelUse");  
    flag_calculateAllocatedFuelUse.setTitle("Calculate Allocated Fuel Use?");  
    flag_calculateAllocatedFuelUse.setRedrawOnChange(true);  
    flag_calculateAllocatedFuelUse.setWidth(50);  
    flag_calculateAllocatedFuelUse.setDefaultValue(false);    
    flag_calculateAllocatedFuelUse.setColSpan(4);//EndRow(Boolean.TRUE);
		
    IblFloatItem totalFuelConsumed = new IblFloatItem("totalFuelConsumed");
    SelectItem totalFuelConsumed_Unit = new SelectItem("totalFuelConsumed_Unit");
    totalFuelConsumed_Unit.setValueMap("Gallons","Litre");
    totalFuelConsumed_Unit.setDefaultValue("Gallons");
    totalFuelConsumed_Unit.setEndRow(Boolean.TRUE);
    
    IblFloatItem companyGoodsTransported = new IblFloatItem("companyGoodsTransported");
    final SelectItem companyGoodsTransported_Unit = new SelectItem("companyGoodsTransported_Unit");
    companyGoodsTransported_Unit.setValueMap("m3","Kg","lb");
    companyGoodsTransported_Unit.setDefaultValue("Kg");
    
    SelectItem companyGoodsTransported_MassType = new SelectItem("companyGoodsTransported_MassType");
    companyGoodsTransported_MassType.setValueMap("Actual","Chargeable","Dimensional");
    companyGoodsTransported_MassType.setDefaultValue("Actual");
	
    IblFloatItem totalGoodsTransportedByCarrier = new IblFloatItem("totalGoodsTransportedByCarrier");
    totalGoodsTransportedByCarrier.setStartRow(Boolean.TRUE);
    
    StaticTextItem totalGoodsTransportedByCarrier_Unit = new StaticTextItem("totalGoodsTransportedByCarrier_Unit");
    //totalGoodsTransportedByCarrier_Unit.setValueMap("m3","Kg","lb");
    totalGoodsTransportedByCarrier_Unit.setDefaultValue("Kg");
		
    SelectItem totalGoodsTransportedByCarrier_MassType = new SelectItem("totalGoodsTransportedByCarrier_MassType");
    totalGoodsTransportedByCarrier_MassType.setValueMap("Actual","Chargeable","Dimensional");
    totalGoodsTransportedByCarrier_MassType.setDefaultValue("Actual");
    
    CheckboxItem flag_unladenBackhaul = new CheckboxItem();  
    flag_unladenBackhaul.setName("flag_unladenBackhaul");  
    flag_unladenBackhaul.setTitle("Unladen Backhaul?");  
    flag_unladenBackhaul.setRedrawOnChange(true);  
    flag_unladenBackhaul.setWidth(50);  
    flag_unladenBackhaul.setDefaultValue(false);    
    flag_unladenBackhaul.setColSpan(4);//EndRow(Boolean.TRUE);
	       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel Based Method - Transportation Emissions");

// Change Handlers
    
    companyGoodsTransported_Unit.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                event.getForm().setValue("totalGoodsTransportedByCarrier_Unit",(String)companyGoodsTransported_Unit.getValue());
                if((Boolean) companyGoodsTransported_Unit.getValue().equals("m3")){  
                    //event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").setVisible(Boolean.TRUE);
                    //event.getForm().getItem("companyGoodsTransported_MassType").setVisible(Boolean.TRUE);                    
                    event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").show();
                    event.getForm().getItem("companyGoodsTransported_MassType").show();
                } else {                    
                    //event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").setVisible(Boolean.FALSE);
                    //event.getForm().getItem("companyGoodsTransported_MassType").setVisible(Boolean.FALSE);
                    event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").hide();
                    event.getForm().getItem("companyGoodsTransported_MassType").hide();                    
                }
            }  
    });  
    
//-setShowIfCondition
    
    fuelConsumed.setVisible(Boolean.TRUE);
    fuelConsumed.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });  
    fuelConsumed_Unit.setVisible(Boolean.TRUE);
    fuelConsumed_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });      
    totalDistanceTravelled.setVisible(Boolean.FALSE);
    totalDistanceTravelled.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });  
    totalDistanceTravelled_Unit.setVisible(Boolean.FALSE);
    totalDistanceTravelled_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });
    vehicleType.setVisible(Boolean.FALSE);
    vehicleType.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });
    fuelEfficiencyOfVehicle.setVisible(Boolean.FALSE);
    fuelEfficiencyOfVehicle.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });
    fuelEfficiencyOfVehicle_Unit.setVisible(Boolean.FALSE);
    fuelEfficiencyOfVehicle_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateFuelConsumed");  
        }  
    });  
    
    
//--
    totalFuelConsumed.setVisible(Boolean.FALSE);
    totalFuelConsumed.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    totalFuelConsumed_Unit.setVisible(Boolean.FALSE);
    totalFuelConsumed_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    companyGoodsTransported.setVisible(Boolean.FALSE);
    companyGoodsTransported.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    companyGoodsTransported_Unit.setVisible(Boolean.FALSE);
    companyGoodsTransported_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    companyGoodsTransported_MassType.setVisible(Boolean.FALSE);
    companyGoodsTransported_MassType.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    totalGoodsTransportedByCarrier.setVisible(Boolean.FALSE);
    totalGoodsTransportedByCarrier.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    totalGoodsTransportedByCarrier_Unit.setVisible(Boolean.FALSE);
    totalGoodsTransportedByCarrier_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });
    totalGoodsTransportedByCarrier_MassType.setVisible(Boolean.FALSE);
    totalGoodsTransportedByCarrier_MassType.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_calculateAllocatedFuelUse");  
        }  
    });  
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(streamType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    
    formItemList.add(flag_calculateFuelConsumed);    		    
    formItemList.add(fuelType);            
    formItemList.add(fuelConsumed);        
    formItemList.add(fuelConsumed_Unit);    	
    formItemList.add(totalDistanceTravelled);    	
    formItemList.add(totalDistanceTravelled_Unit);    		
    formItemList.add(vehicleType);    	
    formItemList.add(fuelEfficiencyOfVehicle);    			
    formItemList.add(fuelEfficiencyOfVehicle_Unit);    			
    
    formItemList.add(EF_Fuel);    			    
    formItemList.add(EF_Fuel_Unit);    		        

    formItemList.add(flag_calculateAllocatedFuelUse);    			    
    formItemList.add(totalFuelConsumed);    		    
    formItemList.add(totalFuelConsumed_Unit);    		
    formItemList.add(companyGoodsTransported);    	
    formItemList.add(companyGoodsTransported_Unit);
    formItemList.add(companyGoodsTransported_MassType);    
    formItemList.add(totalGoodsTransportedByCarrier);    			
    formItemList.add(totalGoodsTransportedByCarrier_Unit);    			
    formItemList.add(totalGoodsTransportedByCarrier_MassType);    
   
    formItemList.add(flag_unladenBackhaul);    

    formItemList.add(refrigerantType);    	
    formItemList.add(refrigerantLeakage);    			
    formItemList.add(refrigerantLeakage_Unit);
    formItemList.add(EF_Refrigerant);    			    
    formItemList.add(EF_Refrigerant_Unit);    		    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getTransportationFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    //final Map<String, String[][]> transportModeVehicle_EF_Unit = new HashMap<String, String[][]>();  
    
    SelectItem streamType = new SelectItem("streamType");       
    streamType.setValueMap("Upstream","Downstream");
    streamType.setDefaultValue("Upstream");
    streamType.setWidth("*");
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    
	    
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");    
    fuelType.setStartRow(Boolean.TRUE);
    
    IblFloatItem fuelConsumed = new IblFloatItem("fuelConsumed");
    SelectItem fuelConsumed_Unit = new SelectItem("fuelConsumed_Unit");
    fuelConsumed_Unit.setValueMap("Gallons","Litre");
    fuelConsumed_Unit.setDefaultValue("Gallons");
    fuelConsumed_Unit.setWidth("*");
    
    IblFloatItem EF_Fuel = new IblFloatItem("EF_Fuel");    	
    SelectItem EF_Fuel_Unit = new SelectItem("EF_Fuel_Unit");    
    EF_Fuel_Unit.setValueMap("Kg CO2e/Gallon","Kg CO2e/Litre");
    EF_Fuel_Unit.setDefaultValue("Kg CO2e/Gallon");
    EF_Fuel_Unit.setWidth("*");   
    
    SelectOtherItem refrigerantType = new SelectOtherItem("refrigerantType");       
    refrigerantType.setValueMap("C2F6", 
                                "C4F10",
                                "CF4", 
                                "CO2", 
                                "HFC-125",
                                "HFC-134a",
                                "HFC-143a",
                                "HFC-152a",
                                "HFC-227ea",
                                "HFC-23",
                                "HFC-236fa",
                                "HFC-32",
                                "SF6");
    refrigerantType.setDefaultValue("C2F6");
    refrigerantType.setWidth("*");    
    refrigerantType.setStartRow(Boolean.TRUE);
    refrigerantType.setEndRow(Boolean.TRUE);
    
    IblFloatItem refrigerantLeakage = new IblFloatItem("refrigerantLeakage");
    SelectItem refrigerantLeakage_Unit = new SelectItem("refrigerantLeakage_Unit");
    refrigerantLeakage_Unit.setValueMap("Kg");
    refrigerantLeakage_Unit.setDefaultValue("Kg");
    refrigerantLeakage_Unit.setWidth("*");
    
    IblFloatItem EF_Refrigerant = new IblFloatItem("EF_Refrigerant");    	
    SelectItem_Energy_EFUnit EF_Refrigerant_Unit = new SelectItem_Energy_EFUnit("EF_Refrigerant_Unit");    

/*    
    CheckboxItem flag_calculateFuelConsumed = new CheckboxItem();  
    flag_calculateFuelConsumed.setName("flag_calculateFuelConsumed");  
    flag_calculateFuelConsumed.setTitle("Calculate Fuel consumed from distance travelled and vehicle used?");  
    flag_calculateFuelConsumed.setRedrawOnChange(true);  
    flag_calculateFuelConsumed.setWidth(50);  
    flag_calculateFuelConsumed.setDefaultValue(false);    
    flag_calculateFuelConsumed.setColSpan(4);//EndRow(Boolean.TRUE);
*/	
    IblFloatItem totalDistanceTravelled = new IblFloatItem("totalDistanceTravelled");
    SelectItem totalDistanceTravelled_Unit = new SelectItem("totalDistanceTravelled_Unit");
    totalDistanceTravelled_Unit.setValueMap("Mile","Km");
    totalDistanceTravelled_Unit.setDefaultValue("Mile");
    totalDistanceTravelled_Unit.setWidth("*");    
    totalDistanceTravelled_Unit.setEndRow(Boolean.TRUE);
    
    TextItem vehicleType = new TextItem("vehicleType");    
    IblFloatItem fuelEfficiencyOfVehicle = new IblFloatItem("fuelEfficiencyOfVehicle");
    SelectItem fuelEfficiencyOfVehicle_Unit = new SelectItem("fuelEfficiencyOfVehicle_Unit");
    fuelEfficiencyOfVehicle_Unit.setValueMap("1/Km","1/Mile");
    fuelEfficiencyOfVehicle_Unit.setDefaultValue("1/Mile");
/*	
    CheckboxItem flag_calculateAllocatedFuelUse = new CheckboxItem();  
    flag_calculateAllocatedFuelUse.setName("flag_calculateAllocatedFuelUse");  
    flag_calculateAllocatedFuelUse.setTitle("Calculate Allocated Fuel Use?");  
    flag_calculateAllocatedFuelUse.setRedrawOnChange(true);  
    flag_calculateAllocatedFuelUse.setWidth(50);  
    flag_calculateAllocatedFuelUse.setDefaultValue(false);    
    flag_calculateAllocatedFuelUse.setColSpan(4);//EndRow(Boolean.TRUE);
*/		
    IblFloatItem totalFuelConsumed = new IblFloatItem("totalFuelConsumed");
    SelectItem totalFuelConsumed_Unit = new SelectItem("totalFuelConsumed_Unit");
    totalFuelConsumed_Unit.setValueMap("Gallons","Litre");
    totalFuelConsumed_Unit.setDefaultValue("Gallons");
    totalFuelConsumed_Unit.setEndRow(Boolean.TRUE);
    
    IblFloatItem companyGoodsTransported = new IblFloatItem("companyGoodsTransported");
    final SelectItem companyGoodsTransported_Unit = new SelectItem("companyGoodsTransported_Unit");
    companyGoodsTransported_Unit.setValueMap("m3","Kg","lb");
    companyGoodsTransported_Unit.setDefaultValue("Kg");
    
    SelectItem companyGoodsTransported_MassType = new SelectItem("companyGoodsTransported_MassType");
    companyGoodsTransported_MassType.setValueMap("Actual","Chargeable","Dimensional");
    companyGoodsTransported_MassType.setDefaultValue("Actual");
	
    IblFloatItem totalGoodsTransportedByCarrier = new IblFloatItem("totalGoodsTransportedByCarrier");
    totalGoodsTransportedByCarrier.setStartRow(Boolean.TRUE);
    
    StaticTextItem totalGoodsTransportedByCarrier_Unit = new StaticTextItem("totalGoodsTransportedByCarrier_Unit");
    //totalGoodsTransportedByCarrier_Unit.setValueMap("m3","Kg","lb");
    totalGoodsTransportedByCarrier_Unit.setDefaultValue("Kg");
		
    StaticTextItem totalGoodsTransportedByCarrier_MassType = new StaticTextItem("totalGoodsTransportedByCarrier_MassType");
    //totalGoodsTransportedByCarrier_MassType.setValueMap("Actual","Chargeable","Dimensional");
    //totalGoodsTransportedByCarrier_MassType.setDefaultValue("Actual");
    
    CheckboxItem flag_unladenBackhaul = new CheckboxItem();  
    flag_unladenBackhaul.setName("flag_unladenBackhaul");  
    flag_unladenBackhaul.setTitle("Unladen Backhaul?");  
    flag_unladenBackhaul.setRedrawOnChange(true);  
    flag_unladenBackhaul.setWidth(50);  
    flag_unladenBackhaul.setDefaultValue(false);    
    flag_unladenBackhaul.setColSpan(4);//EndRow(Boolean.TRUE);

    final RadioGroupItem fuelDataType = new RadioGroupItem();  
    fuelDataType.setName("fuelDataType");  
    fuelDataType.setColSpan("*");  
    fuelDataType.setRequired(false);  
    fuelDataType.setVertical(false);  
    fuelDataType.setValueMap("Fuel Consumed", "Distance/Vehicle Used", "Calculate Allocated Fuel Used");  
    fuelDataType.setRedrawOnChange(true);  
    fuelDataType.setTitle("What type of fuel related data you have?");  
    fuelDataType.setDefaultValue("Fuel Consumed");
    fuelDataType.setVisible(Boolean.TRUE);
    fuelDataType.setStartRow(Boolean.TRUE);
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Fuel Based Method - Transportation Emissions");

// Change Handlers    
    companyGoodsTransported_Unit.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                event.getForm().setValue("totalGoodsTransportedByCarrier_Unit",(String)companyGoodsTransported_Unit.getValue());
                
                if((Boolean) companyGoodsTransported_Unit.getValue().equals("m3")){  
                    event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").show();
                    event.getForm().getItem("companyGoodsTransported_MassType").show();
                } else {                    
                    event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").hide();
                    event.getForm().getItem("companyGoodsTransported_MassType").hide();                    
                }
            }  
    });  
    
    companyGoodsTransported_MassType.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                event.getForm().setValue("totalGoodsTransportedByCarrier_MassType",(String)companyGoodsTransported_Unit.getValue());                
                event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").show();                    
            }  
    });  

/*
    flag_unladenBackhaul.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                event.getForm().setValue("totalGoodsTransportedByCarrier_MassType",(String)companyGoodsTransported_Unit.getValue());                
                event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").show();                    
            }  
    });      
*/
    
    fuelConsumed.setVisible(Boolean.TRUE);
    fuelConsumed_Unit.setVisible(Boolean.TRUE);
    totalDistanceTravelled.setVisible(Boolean.FALSE);
    totalDistanceTravelled_Unit.setVisible(Boolean.FALSE);
    vehicleType.setVisible(Boolean.FALSE);
    fuelEfficiencyOfVehicle.setVisible(Boolean.FALSE);
    fuelEfficiencyOfVehicle_Unit.setVisible(Boolean.FALSE);
    totalFuelConsumed.setVisible(Boolean.FALSE);
    totalFuelConsumed_Unit.setVisible(Boolean.FALSE);
    companyGoodsTransported.setVisible(Boolean.FALSE);
    companyGoodsTransported_Unit.setVisible(Boolean.FALSE);
    companyGoodsTransported_MassType.setVisible(Boolean.FALSE);
    totalGoodsTransportedByCarrier.setVisible(Boolean.FALSE);
    totalGoodsTransportedByCarrier_Unit.setVisible(Boolean.FALSE);
    totalGoodsTransportedByCarrier_MassType.setVisible(Boolean.FALSE);
    
    fuelDataType.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if((Boolean) fuelDataType.getValue().equals("Fuel Consumed")){
                event.getForm().getItem("fuelConsumed").show();
                event.getForm().getItem("fuelConsumed_Unit").show();

                event.getForm().getItem("totalDistanceTravelled").hide();
                event.getForm().getItem("totalDistanceTravelled_Unit").hide();
                event.getForm().getItem("vehicleType").hide();
                event.getForm().getItem("fuelEfficiencyOfVehicle").hide();
                event.getForm().getItem("fuelEfficiencyOfVehicle_Unit").hide();

                event.getForm().getItem("totalDistanceTravelled").clearValue();
                event.getForm().getItem("fuelEfficiencyOfVehicle").clearValue();                
                
                event.getForm().getItem("totalFuelConsumed").hide();
                event.getForm().getItem("totalFuelConsumed_Unit").hide();
                event.getForm().getItem("companyGoodsTransported").hide();
                event.getForm().getItem("companyGoodsTransported_Unit").hide();
                event.getForm().getItem("companyGoodsTransported_MassType").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier_Unit").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").hide();

                event.getForm().getItem("totalFuelConsumed").clearValue();
                event.getForm().getItem("companyGoodsTransported").clearValue();
                event.getForm().getItem("totalGoodsTransportedByCarrier").clearValue();
                
            }if((Boolean) fuelDataType.getValue().equals("Distance/Vehicle Used")){
                event.getForm().getItem("fuelConsumed").hide();
                event.getForm().getItem("fuelConsumed_Unit").hide();
                event.getForm().getItem("fuelConsumed").clearValue();

                event.getForm().getItem("totalDistanceTravelled").show();
                event.getForm().getItem("totalDistanceTravelled_Unit").show();
                event.getForm().getItem("vehicleType").show();
                event.getForm().getItem("fuelEfficiencyOfVehicle").show();
                event.getForm().getItem("fuelEfficiencyOfVehicle_Unit").show();

                event.getForm().getItem("totalFuelConsumed").hide();
                event.getForm().getItem("totalFuelConsumed_Unit").hide();
                event.getForm().getItem("companyGoodsTransported").hide();
                event.getForm().getItem("companyGoodsTransported_Unit").hide();
                event.getForm().getItem("companyGoodsTransported_MassType").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier_Unit").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").hide();

                event.getForm().getItem("totalFuelConsumed").clearValue();
                event.getForm().getItem("companyGoodsTransported").clearValue();
                event.getForm().getItem("totalGoodsTransportedByCarrier").clearValue();
                
            } else if((Boolean) fuelDataType.getValue().equals("Calculate Allocated Fuel Used")){
                event.getForm().getItem("fuelConsumed").hide();
                event.getForm().getItem("fuelConsumed_Unit").hide();
                event.getForm().getItem("fuelConsumed").clearValue();

                event.getForm().getItem("totalDistanceTravelled").hide();
                event.getForm().getItem("totalDistanceTravelled_Unit").hide();
                event.getForm().getItem("vehicleType").hide();
                event.getForm().getItem("fuelEfficiencyOfVehicle").hide();
                event.getForm().getItem("fuelEfficiencyOfVehicle_Unit").hide();
                
                event.getForm().getItem("totalDistanceTravelled").clearValue();
                event.getForm().getItem("fuelEfficiencyOfVehicle").clearValue();                

                event.getForm().getItem("totalFuelConsumed").show();
                event.getForm().getItem("totalFuelConsumed_Unit").show();
                event.getForm().getItem("companyGoodsTransported").show();
                event.getForm().getItem("companyGoodsTransported_Unit").show();
                event.getForm().getItem("companyGoodsTransported_MassType").hide();
                event.getForm().getItem("totalGoodsTransportedByCarrier").show();
                event.getForm().getItem("totalGoodsTransportedByCarrier_Unit").show();
                event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").hide();				
            }
        }  
    });  

    flag_unladenBackhaul.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if ((Boolean)event.getForm().getValue("flag_unladenBackhaul") ){
                //fuelDataType.setValueDisabled("Distance/Vehicle Used", true);
                fuelDataType.setValueDisabled("Calculate Allocated Fuel Used", true);                
                                
                if (fuelDataType.getValue().equals("Calculate Allocated Fuel Used")) {
                    
                    fuelDataType.setValue("Fuel Consumed");
                    
                    event.getForm().getItem("fuelConsumed").show();
                    event.getForm().getItem("fuelConsumed_Unit").show();
                    
                    event.getForm().getItem("totalDistanceTravelled").hide();
                    event.getForm().getItem("totalDistanceTravelled_Unit").hide();
                    event.getForm().getItem("vehicleType").hide();
                    event.getForm().getItem("fuelEfficiencyOfVehicle").hide();
                    event.getForm().getItem("fuelEfficiencyOfVehicle_Unit").hide();
                    
                    event.getForm().getItem("totalFuelConsumed").hide();
                    event.getForm().getItem("totalFuelConsumed_Unit").hide();
                    event.getForm().getItem("companyGoodsTransported").hide();
                    event.getForm().getItem("companyGoodsTransported_Unit").hide();
                    event.getForm().getItem("companyGoodsTransported_MassType").hide();
                    event.getForm().getItem("totalGoodsTransportedByCarrier").hide();
                    event.getForm().getItem("totalGoodsTransportedByCarrier_Unit").hide();
                    event.getForm().getItem("totalGoodsTransportedByCarrier_MassType").hide();                                        
                };
                
                event.getForm().getItem("refrigerantType").hide();
                event.getForm().getItem("refrigerantLeakage").hide();
                event.getForm().getItem("refrigerantLeakage_Unit").hide();
                event.getForm().getItem("EF_Refrigerant").hide();
                event.getForm().getItem("EF_Refrigerant_Unit").hide();
                                
            } else {
                //fuelDataType.setValueDisabled("Distance/Vehicle Used", false);
                fuelDataType.setValueDisabled("Calculate Allocated Fuel Used", false);                                

                event.getForm().getItem("refrigerantType").show();
                event.getForm().getItem("refrigerantLeakage").show();
                event.getForm().getItem("refrigerantLeakage_Unit").show();
                event.getForm().getItem("EF_Refrigerant").show();
                event.getForm().getItem("EF_Refrigerant_Unit").show();                
            }
        }  
    });  

    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(streamType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    
    //formItemList.add(flag_calculateFuelConsumed);    		    
    formItemList.add(flag_unladenBackhaul);    
    formItemList.add(fuelDataType);      
    
    formItemList.add(fuelConsumed);        
    formItemList.add(fuelConsumed_Unit);    	
    
    formItemList.add(totalDistanceTravelled);    	
    formItemList.add(totalDistanceTravelled_Unit);    		
    formItemList.add(vehicleType);    	
    formItemList.add(fuelEfficiencyOfVehicle);    			
    formItemList.add(fuelEfficiencyOfVehicle_Unit);    			
    
    //formItemList.add(flag_calculateAllocatedFuelUse);    			    
    formItemList.add(totalFuelConsumed);    		    
    formItemList.add(totalFuelConsumed_Unit);    		
    formItemList.add(companyGoodsTransported);    	
    formItemList.add(companyGoodsTransported_Unit);
    formItemList.add(companyGoodsTransported_MassType);    
    formItemList.add(totalGoodsTransportedByCarrier);    			
    formItemList.add(totalGoodsTransportedByCarrier_Unit);    			
    formItemList.add(totalGoodsTransportedByCarrier_MassType);    
    
    formItemList.add(fuelType);                
    formItemList.add(EF_Fuel);    			    
    formItemList.add(EF_Fuel_Unit);    		        

 
    formItemList.add(refrigerantType);    	
    formItemList.add(refrigerantLeakage);    			
    formItemList.add(refrigerantLeakage_Unit);
    formItemList.add(EF_Refrigerant);    			    
    formItemList.add(EF_Refrigerant_Unit);    		    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getTransportationFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SelectItem streamType = new SelectItem("streamType");       
    streamType.setValueMap("Upstream","Downstream");
    streamType.setDefaultValue("Upstream");
    streamType.setWidth("*");
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    
	    
    SelectOtherItem transportModeOrVehicleType = new SelectOtherItem("transportModeOrVehicleType");       
    transportModeOrVehicleType.setValueMap("Air","Ship","Rail","Truck","Warehouse","Terminal");
    transportModeOrVehicleType.setDefaultValue("Air");
    transportModeOrVehicleType.setWidth("*");    
    transportModeOrVehicleType.setEndRow(Boolean.TRUE);
	
    IblFloatItem massOfGoodsPurchased = new IblFloatItem("massOfGoodsPurchased");
    SelectItem massOfGoodsPurchased_Unit = new SelectItem("massOfGoodsPurchased_Unit");
    massOfGoodsPurchased_Unit.setValueMap("Tonne");
    massOfGoodsPurchased_Unit.setDefaultValue("Tonne");
    
    IblFloatItem distanceTraveledInTransportLeg = new IblFloatItem("distanceTraveledInTransportLeg");    	
    SelectItem distanceTraveledInTransportLeg_Unit = new SelectItem("distanceTraveledInTransportLeg_Unit");
    distanceTraveledInTransportLeg_Unit.setValueMap("Mile");
    distanceTraveledInTransportLeg_Unit.setDefaultValue("Mile");
			
    IblFloatItem EF_TransportModeOrVehicleType = new IblFloatItem("EF_TransportModeOrVehicleType");
    SelectItem EF_TransportModeOrVehicleType_Unit = new SelectItem("EF_TransportModeOrVehicleType_Unit");
    EF_TransportModeOrVehicleType_Unit.setValueMap("Kg CO2e/Tonne-Mile");
    EF_TransportModeOrVehicleType_Unit.setDefaultValue("Kg CO2e/Tonne-Mile");
		       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Distance Based Method - Transportation Emissions");

    
    
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(streamType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    formItemList.add(transportModeOrVehicleType);            
    formItemList.add(massOfGoodsPurchased);        
    formItemList.add(massOfGoodsPurchased_Unit);    	
    formItemList.add(distanceTraveledInTransportLeg);    			    
    formItemList.add(distanceTraveledInTransportLeg_Unit);    		    
    formItemList.add(EF_TransportModeOrVehicleType);    	
    formItemList.add(EF_TransportModeOrVehicleType_Unit);    			
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getDistributionFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SelectItem streamType = new SelectItem("streamType");       
    streamType.setValueMap("Upstream","Downstream");
    streamType.setDefaultValue("Upstream");
    streamType.setWidth("*");
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    
    TextItem storageFacility = new TextItem("storageFacility");    
	

    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");    
    fuelType.setStartRow(Boolean.TRUE);
    fuelType.setEndRow(Boolean.TRUE);
    
    IblFloatItem fuelConsumed = new IblFloatItem("fuelConsumed");
    SelectItem_Energy_Unit fuelConsumed_Unit = new SelectItem_Energy_Unit("fuelConsumed_Unit");
    IblFloatItem EF_Fuel = new IblFloatItem("EF_Fuel");    	
    SelectItem_Energy_EFUnit EF_Fuel_Unit = new SelectItem_Energy_EFUnit("EF_Fuel_Unit");    

    SelectOtherItem refrigerantType = new SelectOtherItem("refrigerantType");       
    refrigerantType.setValueMap("C2F6", 
                                "C4F10",
                                "CF4", 
                                "CO2", 
                                "HFC-125",
                                "HFC-134a",
                                "HFC-143a",
                                "HFC-152a",
                                "HFC-227ea",
                                "HFC-23",
                                "HFC-236fa",
                                "HFC-32",
                                "SF6");
    refrigerantType.setDefaultValue("C2F6");
    refrigerantType.setWidth("*");    
    refrigerantType.setStartRow(Boolean.TRUE);
    refrigerantType.setEndRow(Boolean.TRUE);
	
    IblFloatItem refrigerantLeakage = new IblFloatItem("refrigerantLeakage");
    SelectItem refrigerantLeakage_Unit = new SelectItem("refrigerantLeakage_Unit");
    refrigerantLeakage_Unit.setValueMap("Kg");
    refrigerantLeakage_Unit.setDefaultValue("Kg");
    
    IblFloatItem EF_Refrigerant = new IblFloatItem("EF_Refrigerant");    	
    SelectItem EF_Refrigerant_Unit = new SelectItem("EF_Refrigerant_Unit");    
    EF_Refrigerant_Unit.setValueMap("Kg CO2e/Kg");
    EF_Refrigerant_Unit.setDefaultValue("Kg CO2e/Kg");

    IblFloatItem electricityConsumed = new IblFloatItem("electricityConsumed");
    SelectItem_Energy_Unit electricityConsumed_Unit = new SelectItem_Energy_Unit("electricityConsumed_Unit");
    IblFloatItem EF_Electricity = new IblFloatItem("EF_Electricity");    	
    SelectItem_Energy_EFUnit EF_Electricity_Unit = new SelectItem_Energy_EFUnit("EF_Electricity_Unit");    
		
    IblFloatItem volumeOfReportingCompanysPurchasedGoods = new IblFloatItem("volumeOfReportingCompanysPurchasedGoods");
    SelectItem volumeOfReportingCompanysPurchasedGoods_Unit = new SelectItem("volumeOfReportingCompanysPurchasedGoods_Unit");
    volumeOfReportingCompanysPurchasedGoods_Unit.setValueMap("m3");
    volumeOfReportingCompanysPurchasedGoods_Unit.setDefaultValue("m3");
    
    IblFloatItem totalVolumeOfGoodsInStorageFacility = new IblFloatItem("totalVolumeOfGoodsInStorageFacility");    	
    SelectItem totalVolumeOfGoodsInStorageFacility_Unit = new SelectItem("totalVolumeOfGoodsInStorageFacility_Unit");    
    totalVolumeOfGoodsInStorageFacility_Unit.setValueMap("m3");
    totalVolumeOfGoodsInStorageFacility_Unit.setDefaultValue("m3");
					       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Site Specific Method - Distribution Emissions");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(streamType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    formItemList.add(storageFacility);        

    formItemList.add(fuelType);            
    formItemList.add(fuelConsumed);        
    formItemList.add(fuelConsumed_Unit);    	
    formItemList.add(EF_Fuel);    			    
    formItemList.add(EF_Fuel_Unit);    		    
    formItemList.add(electricityConsumed);    			
    formItemList.add(electricityConsumed_Unit);
    formItemList.add(EF_Electricity);    			    
    formItemList.add(EF_Electricity_Unit);    		    
    
    formItemList.add(refrigerantType);    	
    formItemList.add(refrigerantLeakage);    			
    formItemList.add(refrigerantLeakage_Unit);
    formItemList.add(EF_Refrigerant);    			    
    formItemList.add(EF_Refrigerant_Unit);    		    
    
    formItemList.add(volumeOfReportingCompanysPurchasedGoods);            
    formItemList.add(volumeOfReportingCompanysPurchasedGoods_Unit);        
    formItemList.add(totalVolumeOfGoodsInStorageFacility);    	
    formItemList.add(totalVolumeOfGoodsInStorageFacility_Unit);    			    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getDistributionFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SelectItem streamType = new SelectItem("streamType");       
    streamType.setValueMap("Upstream","Downstream");
    streamType.setDefaultValue("Upstream");
    streamType.setWidth("*");
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    
    TextItem storageFacility = new TextItem("storageFacility");    
    storageFacility.setEndRow(Boolean.TRUE);

    IblFloatItem storedGoodsInReportingYear = new IblFloatItem("storedGoodsInReportingYear");
    final SelectItem storedGoodsInReportingYear_Unit = new SelectItem("storedGoodsInReportingYear_Unit");
    storedGoodsInReportingYear_Unit.setValueMap("m3", "Pallets");
    storedGoodsInReportingYear_Unit.setDefaultValue("m3");
    storedGoodsInReportingYear_Unit.setWidth("*");    

    IblFloatItem EF_StorageFacility = new IblFloatItem("EF_StorageFacility");  
    final StaticTextItem EF_StorageFacility_Unit = new StaticTextItem("EF_StorageFacility_Unit");
    EF_StorageFacility_Unit.setDefaultValue("m3");
/*    
    SelectItem EF_StorageFacility_Unit = new SelectItem("EF_StorageFacility_Unit");
    EF_StorageFacility_Unit.setValueMap("Kg CO2e/m3", "Kg CO2e/Pallet");
    EF_StorageFacility_Unit.setDefaultValue("Kg CO2e/m3");
    EF_StorageFacility_Unit.setWidth("*");    
*/					       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Average Data Method - Distribution Emissions");

    storedGoodsInReportingYear_Unit.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if (storedGoodsInReportingYear_Unit.getValue().equals("m3")) {                    
                EF_StorageFacility_Unit.setValue("Kg CO2e/m3");                    
            } else if (storedGoodsInReportingYear_Unit.getValue().equals("Pallets")){
                EF_StorageFacility_Unit.setValue("Kg CO2e/Pallet");                    
            }    
        }
    });  
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(streamType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    formItemList.add(storageFacility);    
    formItemList.add(storedGoodsInReportingYear);            
    formItemList.add(storedGoodsInReportingYear_Unit);        
    formItemList.add(EF_StorageFacility);    	
    formItemList.add(EF_StorageFacility_Unit);    			    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getWasteGeneratedInOperationsFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    sourceDescriptionItem.setEndRow(Boolean.TRUE);

    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    

    SelectOtherItem wasteType = new SelectOtherItem("wasteType");       
    wasteType.setValueMap("Plastic","Water Disposal");
    wasteType.setDefaultValue("Plastic");
    wasteType.setWidth("*");

    SelectOtherItem wasteTreatmentType = new SelectOtherItem("wasteTreatmentType");       
    wasteTreatmentType.setValueMap("Incinerated","Landfill","Wastewater");
    wasteTreatmentType.setDefaultValue("Incinerated");
    wasteTreatmentType.setWidth("*");
    
    IblFloatItem wasteProduced = new IblFloatItem("wasteProduced");
    final SelectItem wasteProduced_Unit = new SelectItem("wasteProduced_Unit");
    wasteProduced_Unit.setValueMap("Tonne","lb");
    wasteProduced_Unit.setDefaultValue("Tonne");
    wasteProduced_Unit.setWidth("*");    

    IblFloatItem EF_WasteTypeAndWasteTreatment = new IblFloatItem("EF_WasteTypeAndWasteTreatment");  
    final StaticTextItem EF_WasteTypeAndWasteTreatment_Unit = new StaticTextItem("EF_WasteTypeAndWasteTreatment_Unit");
    EF_WasteTypeAndWasteTreatment_Unit.setDefaultValue("Kg CO2e/Tonne");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Waste Type Specific Method - Waste Generated In Operations");

    wasteProduced_Unit.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if (wasteProduced_Unit.getValue().equals("Tonne")) {                    
                EF_WasteTypeAndWasteTreatment_Unit.setValue("Kg CO2e/Tonne");                    
            } else if (wasteProduced_Unit.getValue().equals("lb")){
                EF_WasteTypeAndWasteTreatment_Unit.setValue("Kg CO2e/lb");                    
            }    
        }
    });  
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    formItemList.add(wasteType);            
    formItemList.add(wasteTreatmentType);    
    formItemList.add(wasteProduced);            
    formItemList.add(wasteProduced_Unit);            
    formItemList.add(EF_WasteTypeAndWasteTreatment);        
    formItemList.add(EF_WasteTypeAndWasteTreatment_Unit);    	
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getWasteGeneratedInOperationsFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    sourceDescriptionItem.setEndRow(Boolean.TRUE);
    
    TextItem serviceProviderName = new TextItem("serviceProviderName");
    TextItem serviceProviderContact = new TextItem("serviceProviderContact");    

    SelectOtherItem wasteType = new SelectOtherItem("wasteType");       
    wasteType.setValueMap("Plastic","Water Disposal");
    wasteType.setDefaultValue("Plastic");
    wasteType.setWidth("*");

    SelectOtherItem wasteTreatmentType = new SelectOtherItem("wasteTreatmentType");       
    wasteTreatmentType.setValueMap("Incinerated","Landfill","Wastewater");
    wasteTreatmentType.setDefaultValue("Incinerated");
    wasteTreatmentType.setWidth("*");
    
    IblFloatItem wasteProduced = new IblFloatItem("wasteProduced");
    final SelectItem wasteProduced_Unit = new SelectItem("wasteProduced_Unit");
    wasteProduced_Unit.setValueMap("Tonne","lb");
    wasteProduced_Unit.setDefaultValue("Tonne");
    wasteProduced_Unit.setWidth("*");    

    IblFloatItem percentOfWasteTreatedByWasteTreatmentMethod = new IblFloatItem("percentOfWasteTreatedByWasteTreatmentMethod");  
    
    IblFloatItem EF_WasteTreatmentMethod = new IblFloatItem("EF_WasteTreatmentMethod");  
    final StaticTextItem EF_WasteTreatmentMethod_Unit = new StaticTextItem("EF_WasteTreatmentMethod_Unit");
    EF_WasteTreatmentMethod_Unit.setDefaultValue("Kg CO2e/Tonne");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Average Data Method - Waste Generated In Operations");

    wasteProduced_Unit.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if (wasteProduced_Unit.getValue().equals("Tonne")) {                    
                EF_WasteTreatmentMethod_Unit.setValue("Kg CO2e/Tonne");                    
            } else if (wasteProduced_Unit.getValue().equals("lb")){
                EF_WasteTreatmentMethod_Unit.setValue("Kg CO2e/lb");                    
            }    
        }
    });  
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    

    formItemList.add(sourceDescriptionItem);        
    formItemList.add(serviceProviderName);
    formItemList.add(serviceProviderContact);    
    formItemList.add(wasteType);        
    formItemList.add(wasteTreatmentType);    
    formItemList.add(wasteProduced);            
    formItemList.add(wasteProduced_Unit);            
    formItemList.add(percentOfWasteTreatedByWasteTreatmentMethod);            
    formItemList.add(EF_WasteTreatmentMethod);        
    formItemList.add(EF_WasteTreatmentMethod_Unit);    	
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
 
public FormItem[] getBusinessTravelFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();
/*
    final SelectItem activityType = new SelectItem("activityType");       
    activityType.setValueMap("Travel","Hotel");
    activityType.setRedrawOnChange(true);
    activityType.setDefaultValue("Travel");
    activityType.setWidth("*");
 */   

    final RadioGroupItem activityType = new RadioGroupItem();  
    activityType.setName("activityType");  
    activityType.setColSpan("*");  
    activityType.setRequired(false);  
    activityType.setVertical(false);  
    activityType.setValueMap("Travel","Hotel");  
    activityType.setRedrawOnChange(true);  
    activityType.setTitle("Business Travel activity?"); 
    activityType.setDefaultValue("Travel");
    activityType.setVisible(Boolean.TRUE);
    activityType.setStartRow(Boolean.TRUE);
    
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
/*
    SelectOtherItem travelMode = new SelectOtherItem("travelMode");       
    travelMode.setValueMap("Air","Train","Bus","Car");
    travelMode.setDefaultValue("Car");
    travelMode.setWidth("*");
*/
    TextItem vehicleType = new TextItem("vehicleType");  
/*    
    SelectOtherItem vehicleType = new SelectOtherItem("vehicleType");       
    vehicleType.setValueMap("Air","Train","Bus","Car");
    vehicleType.setDefaultValue("Car");
    vehicleType.setWidth("*");
*/
    
    IblFloatItem distanceTravelledByVehicleType = new IblFloatItem("distanceTravelledByVehicleType");
    distanceTravelledByVehicleType.setStartRow(Boolean.TRUE);
    
    final SelectItem distanceTravelledByVehicleType_Unit = new SelectItem("distanceTravelledByVehicleType_Unit");
    distanceTravelledByVehicleType_Unit.setValueMap("Mile", "Km");
    distanceTravelledByVehicleType_Unit.setDefaultValue("Mile");
    distanceTravelledByVehicleType_Unit.setWidth("*");    

    IblFloatItem EF_VehicleType = new IblFloatItem("EF_VehicleType");  
    final StaticTextItem EF_VehicleType_Unit = new StaticTextItem("EF_VehicleType_Unit");
    EF_VehicleType_Unit.setDefaultValue("Kg CO2e/Mile");    	

    IblFloatItem annualNumberOfHotelNights = new IblFloatItem("annualNumberOfHotelNights");
    IblFloatItem EF_Hotel = new IblFloatItem("EF_Hotel");
    EF_Hotel.setStartRow(Boolean.TRUE);
    
    final SelectItem EF_Hotel_Unit = new SelectItem("EF_Hotel_Unit");
    EF_Hotel_Unit.setValueMap("Kg CO2e");
    EF_Hotel_Unit.setDefaultValue("Kg CO2e");
    EF_Hotel_Unit.setWidth("*");    
    
    annualNumberOfHotelNights.setVisible(Boolean.FALSE);
    EF_Hotel.setVisible(Boolean.FALSE);
    EF_Hotel_Unit.setVisible(Boolean.FALSE);
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Distance Based Method - Business Travel Emissions");

    distanceTravelledByVehicleType_Unit.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if (distanceTravelledByVehicleType_Unit.getValue().equals("Mile")) {                    
                EF_VehicleType_Unit.setValue("Kg CO2e/Mile");                    
            } else if (distanceTravelledByVehicleType_Unit.getValue().equals("Km")){
                EF_VehicleType_Unit.setValue("Kg CO2e/Km");                    
            }    
        }
    });  

    activityType.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if (activityType.getValue().equals("Travel")) {
                event.getForm().getItem("vehicleType").show();
                event.getForm().getItem("distanceTravelledByVehicleType").show();
                event.getForm().getItem("distanceTravelledByVehicleType_Unit").show();
                event.getForm().getItem("EF_VehicleType").show();
                event.getForm().getItem("EF_VehicleType_Unit").show();
                
                event.getForm().getItem("annualNumberOfHotelNights").hide();
                event.getForm().getItem("EF_Hotel").hide();
                event.getForm().getItem("EF_Hotel_Unit").hide();
                
            } else if (activityType.getValue().equals("Hotel")){
                event.getForm().getItem("vehicleType").hide();
                event.getForm().getItem("distanceTravelledByVehicleType").hide();
                event.getForm().getItem("distanceTravelledByVehicleType_Unit").hide();
                event.getForm().getItem("EF_VehicleType").hide();
                event.getForm().getItem("EF_VehicleType_Unit").hide();
                
                event.getForm().getItem("annualNumberOfHotelNights").show();
                event.getForm().getItem("EF_Hotel").show();
                event.getForm().getItem("EF_Hotel_Unit").show();                
            }    
        }
    });  
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(activityType);        
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(vehicleType);
    formItemList.add(distanceTravelledByVehicleType);    
    formItemList.add(distanceTravelledByVehicleType_Unit);    
    formItemList.add(EF_VehicleType);            
    formItemList.add(EF_VehicleType_Unit);
    formItemList.add(annualNumberOfHotelNights);           
    formItemList.add(EF_Hotel);           
    formItemList.add(EF_Hotel_Unit);               
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getBusinessTravelHotelStayFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    StaticTextItem activityType = new StaticTextItem("activityType");
    activityType.setDefaultValue("Hotel");
    
    IblFloatItem annualNumberOfHotelNights = new IblFloatItem("annualNumberOfHotelNights");
    IblFloatItem EF_Hotel = new IblFloatItem("EF_Hotel");
    EF_Hotel.setStartRow(Boolean.TRUE);
    
    SelectItem EF_Hotel_Unit = new SelectItem("EF_Hotel_Unit");
    EF_Hotel_Unit.setValueMap("Kg CO2e");
    EF_Hotel_Unit.setDefaultValue("Kg CO2e");
    EF_Hotel_Unit.setWidth("*");    
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Hotel Stay Method - Business Travel Emissions");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(annualNumberOfHotelNights);           
    formItemList.add(EF_Hotel);           
    formItemList.add(EF_Hotel_Unit);               

    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    formItemList.add(activityType);
    
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getLeasedAssetsFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    SelectItem typeOfLeasingArrangement = new SelectItem("typeOfLeasingArrangement");       
    typeOfLeasingArrangement.setValueMap("Finance/Capital Lease",
					 "Operating Lease");
    typeOfLeasingArrangement.setWidth("*");

    SelectItem lessorOrLessee = new SelectItem("lessorOrLessee");       
    lessorOrLessee.setValueMap("Lessee","Lessor");
    lessorOrLessee.setWidth("*");
	
    TextItem leasedAssetName =  new TextItem("leasedAssetName");	
    
    IblFloatItem scope1EmissionsOfLeasedAsset = new IblFloatItem("scope1EmissionsOfLeasedAsset");
    scope1EmissionsOfLeasedAsset.setStartRow(Boolean.TRUE);
    
    StaticTextItem scope1EmissionsOfLeasedAsset_Unit = new StaticTextItem("scope1EmissionsOfLeasedAsset_Unit");
    scope1EmissionsOfLeasedAsset_Unit.setDefaultValue("Kg CO2e");
    
    IblFloatItem scope2EmissionsOfLeasedAsset = new IblFloatItem("scope2EmissionsOfLeasedAsset");
    StaticTextItem scope2EmissionsOfLeasedAsset_Unit = new StaticTextItem("scope2EmissionsOfLeasedAsset_Unit");
    scope2EmissionsOfLeasedAsset_Unit.setDefaultValue("Kg CO2e");            
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Site Specific Method - Leased Assets");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(typeOfLeasingArrangement);            
    formItemList.add(lessorOrLessee);        
    formItemList.add(leasedAssetName);
    formItemList.add(scope1EmissionsOfLeasedAsset);
    formItemList.add(scope1EmissionsOfLeasedAsset_Unit);    	
    formItemList.add(scope2EmissionsOfLeasedAsset);    			    
    formItemList.add(scope2EmissionsOfLeasedAsset_Unit);    		    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getLeasedAssetsFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    SelectItem typeOfLeasingArrangement = new SelectItem("typeOfLeasingArrangement");       
    typeOfLeasingArrangement.setValueMap("Finance/Capital Lease",
					 "Operating Lease");
    typeOfLeasingArrangement.setWidth("*");

    SelectItem lessorOrLessee = new SelectItem("lessorOrLessee");       
    lessorOrLessee.setValueMap("Lessee","Lessor");
    lessorOrLessee.setWidth("*");
	
    TextItem leasedAssetName =  new TextItem("leasedAssetName");	
    
    CheckboxItem flag_floorSpaceData = new CheckboxItem();  
    flag_floorSpaceData.setName("flag_floorSpaceData");  
    flag_floorSpaceData.setTitle("Floor space data us available?");  
    flag_floorSpaceData.setRedrawOnChange(true);  
    flag_floorSpaceData.setWidth(50);  
    flag_floorSpaceData.setDefaultValue(false);    
    flag_floorSpaceData.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem floorSpace = new IblFloatItem("floorSpace");
    StaticTextItem floorSpace_Unit = new StaticTextItem("floorSpace_Unit");
    floorSpace_Unit.setDefaultValue("m2");

    IblFloatItem average_EF = new IblFloatItem("average_EF");
    StaticTextItem average_EF_Unit = new StaticTextItem("average_EF_Unit");
    average_EF_Unit.setDefaultValue("Kg CO2e/m2/year");
/*
    IblFloatItem emissionsFromOtherAsset = new IblFloatItem("emissionsFromOtherAsset");
    emissionsFromOtherAsset.setVisible(Boolean.FALSE);
    TextItem emissionsFromOtherAsset_Unit = new TextItem("emissionsFromOtherAsset_Unit");
    emissionsFromOtherAsset_Unit.setVisible(Boolean.FALSE);
    //floorSpace_Unit.setDefaultValue("m2");
*/
    IblFloatItem numberOfbuildingsOrAssetTypes = new IblFloatItem("numberOfbuildingsOrAssetTypes");  
    numberOfbuildingsOrAssetTypes.setVisible(Boolean.FALSE);
    
    IblFloatItem averageEmissionsPerBuildingOrAssetType = new IblFloatItem("averageEmissionsPerBuildingOrAssetType");
    averageEmissionsPerBuildingOrAssetType.setVisible(Boolean.FALSE);
    averageEmissionsPerBuildingOrAssetType.setStartRow(Boolean.TRUE);
    
    TextItem averageEmissionsPerBuildingOrAssetType_Unit = new TextItem("averageEmissionsPerBuildingOrAssetType_Unit");
    averageEmissionsPerBuildingOrAssetType_Unit.setVisible(Boolean.FALSE);
    //floorSpace_Unit.setDefaultValue("Kg CO2e/m2/year");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Average Data Method - Leased Assets");

    floorSpace.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    floorSpace_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    average_EF.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    average_EF_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    numberOfbuildingsOrAssetTypes.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    averageEmissionsPerBuildingOrAssetType.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    averageEmissionsPerBuildingOrAssetType_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });      
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(lessorOrLessee);       
    formItemList.add(typeOfLeasingArrangement);                
    formItemList.add(leasedAssetName);
    formItemList.add(flag_floorSpaceData);
    formItemList.add(floorSpace);    	
    formItemList.add(floorSpace_Unit);    			     		    
    formItemList.add(average_EF);    			     		    
    formItemList.add(average_EF_Unit);    	
    //formItemList.add(emissionsFromOtherAsset);
    //formItemList.add(emissionsFromOtherAsset_Unit);
    formItemList.add(numberOfbuildingsOrAssetTypes);
    formItemList.add(averageEmissionsPerBuildingOrAssetType);
    formItemList.add(averageEmissionsPerBuildingOrAssetType_Unit);    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getProcessingOfSoldProductsFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();
/*
    SelectItem streamType = new SelectItem("streamType");       
    streamType.setValueMap("Upstream","Downstream");
    streamType.setDefaultValue("Upstream");
    streamType.setWidth("*");
*/	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem soldProductName = new TextItem("soldProductName");
    TextItem productSoldTo = new TextItem("productSoldTo");    
    TextItem productSoldToContact = new TextItem("productSoldToContact");    
	
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");    
    fuelType.setStartRow(Boolean.TRUE);
    fuelType.setEndRow(Boolean.TRUE);
    
    IblFloatItem fuelConsumed = new IblFloatItem("fuelConsumed");
    SelectItem_Energy_Unit fuelConsumed_Unit = new SelectItem_Energy_Unit("fuelConsumed_Unit");
    IblFloatItem EF_Fuel = new IblFloatItem("EF_Fuel");    	
    SelectItem_Energy_EFUnit EF_Fuel_Unit = new SelectItem_Energy_EFUnit("EF_Fuel_Unit");    

    SelectOtherItem refrigerantType = new SelectOtherItem("refrigerantType");       
    refrigerantType.setValueMap("C2F6", 
                                "C4F10",
                                "CF4", 
                                "CO2", 
                                "HFC-125",
                                "HFC-134a",
                                "HFC-143a",
                                "HFC-152a",
                                "HFC-227ea",
                                "HFC-23",
                                "HFC-236fa",
                                "HFC-32",
                                "SF6");
    refrigerantType.setDefaultValue("C2F6");
    refrigerantType.setWidth("*");    
    refrigerantType.setStartRow(Boolean.TRUE);
    refrigerantType.setEndRow(Boolean.TRUE);
	
    IblFloatItem refrigerantLeakage = new IblFloatItem("refrigerantLeakage");
    SelectItem refrigerantLeakage_Unit = new SelectItem("refrigerantLeakage_Unit");
    refrigerantLeakage_Unit.setValueMap("Kg");
    refrigerantLeakage_Unit.setDefaultValue("Kg");
    
    IblFloatItem EF_Refrigerant = new IblFloatItem("EF_Refrigerant");    	
    SelectItem EF_Refrigerant_Unit = new SelectItem("EF_Refrigerant_Unit");    
    EF_Refrigerant_Unit.setValueMap("Kg CO2e/Kg");
    EF_Refrigerant_Unit.setDefaultValue("Kg CO2e/Kg");

    IblFloatItem electricityConsumed = new IblFloatItem("electricityConsumed");
    SelectItem_Energy_Unit electricityConsumed_Unit = new SelectItem_Energy_Unit("electricityConsumed_Unit");
    IblFloatItem EF_Electricity = new IblFloatItem("EF_Electricity");    	
    SelectItem_Energy_EFUnit EF_Electricity_Unit = new SelectItem_Energy_EFUnit("EF_Electricity_Unit");    
		
    IblFloatItem massOfWasteOutput = new IblFloatItem("massOfWasteOutput");
    SelectItem massOfWasteOutput_Unit = new SelectItem("massOfWasteOutput_Unit");
    massOfWasteOutput_Unit.setValueMap("Kg");
    massOfWasteOutput_Unit.setDefaultValue("Kg");
    
    IblFloatItem EF_WasteActivity = new IblFloatItem("EF_WasteActivity");    	
    SelectItem EF_WasteActivity_Unit = new SelectItem("EF_WasteActivity_Unit");    
    EF_WasteActivity_Unit.setValueMap("Kg CO2e/Kg");
    EF_WasteActivity_Unit.setDefaultValue("Kg CO2e/Kg");
					       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Site Specific Method - Processing Of Sold Products Emissions");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(soldProductName);
    formItemList.add(productSoldTo);    
    formItemList.add(productSoldToContact);        

    formItemList.add(fuelType);            
    formItemList.add(fuelConsumed);        
    formItemList.add(fuelConsumed_Unit);    	
    formItemList.add(EF_Fuel);    			    
    formItemList.add(EF_Fuel_Unit);    		    
    formItemList.add(electricityConsumed);    			
    formItemList.add(electricityConsumed_Unit);
    formItemList.add(EF_Electricity);    			    
    formItemList.add(EF_Electricity_Unit);    		    
    
    formItemList.add(refrigerantType);    	
    formItemList.add(refrigerantLeakage);    			
    formItemList.add(refrigerantLeakage_Unit);
    formItemList.add(EF_Refrigerant);    			    
    formItemList.add(EF_Refrigerant_Unit);    		    
    
    formItemList.add(massOfWasteOutput);            
    formItemList.add(massOfWasteOutput_Unit);        
    formItemList.add(EF_WasteActivity);    	
    formItemList.add(EF_WasteActivity_Unit);    			    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getProcessingOfSoldProductsFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem soldProductName = new TextItem("soldProductName");
    TextItem productSoldTo = new TextItem("productSoldTo");    
    TextItem productSoldToContact = new TextItem("productSoldToContact");    
			
    IblFloatItem massOfSoldIntermediateProduct = new IblFloatItem("massOfSoldIntermediateProduct");
    massOfSoldIntermediateProduct.setStartRow(Boolean.TRUE);
    
    SelectItem massOfSoldIntermediateProduct_Unit = new SelectItem("massOfSoldIntermediateProduct_Unit");
    massOfSoldIntermediateProduct_Unit.setValueMap("Kg");
    massOfSoldIntermediateProduct_Unit.setDefaultValue("Kg");
    
    IblFloatItem EF_ProcessingOfSoldProducts = new IblFloatItem("EF_ProcessingOfSoldProducts");    	
    SelectItem EF_ProcessingOfSoldProducts_Unit = new SelectItem("EF_ProcessingOfSoldProducts_Unit");    
    EF_ProcessingOfSoldProducts_Unit.setValueMap("Kg CO2e/Kg");
    EF_ProcessingOfSoldProducts_Unit.setDefaultValue("Kg CO2e/Kg");
					       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Average Data Method - Processing Of Sold Products Emissions");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(soldProductName);
    formItemList.add(productSoldTo);    
    formItemList.add(productSoldToContact);        
   
    formItemList.add(massOfSoldIntermediateProduct);            
    formItemList.add(massOfSoldIntermediateProduct_Unit);        
    formItemList.add(EF_ProcessingOfSoldProducts);    	
    formItemList.add(EF_ProcessingOfSoldProducts_Unit);    			    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getDirectUsePhaseEmissionsFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    
    StaticTextItem productType = new StaticTextItem("productType");
    productType.setDefaultValue("Energy Using Products");
    
    TextItem productName = new TextItem("productName");    
    IblFloatItem totalLifetimeExpectedUsesOfProduct = new IblFloatItem("totalLifetimeExpectedUsesOfProduct");    	
    IblFloatItem numberSoldInReportingPeriod = new IblFloatItem("numberSoldInReportingPeriod");    	
    
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");    
    fuelType.setStartRow(Boolean.TRUE);
    fuelType.setEndRow(Boolean.TRUE);
    
    IblFloatItem fuelConsumedPerUse = new IblFloatItem("fuelConsumedPerUse");
    SelectItem_Energy_Unit fuelConsumedPerUse_Unit = new SelectItem_Energy_Unit("fuelConsumedPerUse_Unit");
    IblFloatItem EF_Fuel = new IblFloatItem("EF_Fuel");    	
    SelectItem_Energy_EFUnit EF_Fuel_Unit = new SelectItem_Energy_EFUnit("EF_Fuel_Unit");    

    SelectOtherItem refrigerantType = new SelectOtherItem("refrigerantType");       
    refrigerantType.setValueMap("C2F6", 
                                "C4F10",
                                "CF4", 
                                "CO2", 
                                "HFC-125",
                                "HFC-134a",
                                "HFC-143a",
                                "HFC-152a",
                                "HFC-227ea",
                                "HFC-23",
                                "HFC-236fa",
                                "HFC-32",
                                "SF6");
    refrigerantType.setDefaultValue("C2F6");
    refrigerantType.setWidth("*");    
    refrigerantType.setStartRow(Boolean.TRUE);
    refrigerantType.setEndRow(Boolean.TRUE);
	
    IblFloatItem refrigerantLeakagePerUse = new IblFloatItem("refrigerantLeakagePerUse");
    SelectItem refrigerantLeakagePerUse_Unit = new SelectItem("refrigerantLeakagePerUse_Unit");
    refrigerantLeakagePerUse_Unit.setValueMap("Kg");
    refrigerantLeakagePerUse_Unit.setDefaultValue("Kg");
    
    IblFloatItem EF_Refrigerant = new IblFloatItem("EF_Refrigerant");    	
    SelectItem EF_Refrigerant_Unit = new SelectItem("EF_Refrigerant_Unit");    
    EF_Refrigerant_Unit.setValueMap("Kg CO2e/Kg");
    EF_Refrigerant_Unit.setDefaultValue("Kg CO2e/Kg");

    IblFloatItem electricityConsumedPerUse = new IblFloatItem("electricityConsumedPerUse");
    SelectItem_Energy_Unit electricityConsumedPerUse_Unit = new SelectItem_Energy_Unit("electricityConsumedPerUse_Unit");
    IblFloatItem EF_Electricity = new IblFloatItem("EF_Electricity");    	
    SelectItem_Energy_EFUnit EF_Electricity_Unit = new SelectItem_Energy_EFUnit("EF_Electricity_Unit");    
							       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Lifetime Uses Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(productType);
    formItemList.add(productName);    

    formItemList.add(totalLifetimeExpectedUsesOfProduct);
    formItemList.add(numberSoldInReportingPeriod);    
    
    formItemList.add(fuelType);            
    formItemList.add(fuelConsumedPerUse);        
    formItemList.add(fuelConsumedPerUse_Unit);    	
    formItemList.add(EF_Fuel);    			    
    formItemList.add(EF_Fuel_Unit);    		    
    formItemList.add(electricityConsumedPerUse);    			
    formItemList.add(electricityConsumedPerUse_Unit);
    formItemList.add(EF_Electricity);    			    
    formItemList.add(EF_Electricity_Unit);    		    
    
    formItemList.add(refrigerantType);    	
    formItemList.add(refrigerantLeakagePerUse);    			
    formItemList.add(refrigerantLeakagePerUse_Unit);
    formItemList.add(EF_Refrigerant);    			    
    formItemList.add(EF_Refrigerant_Unit);    		    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getDirectUsePhaseEmissionsFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    StaticTextItem productType = new StaticTextItem("productType");
    productType.setDefaultValue("Fuels and Feedstocks");
    
    TextItem productName = new TextItem("productName");    
    IblFloatItem totalQuantityOfFuelOrFeedstockSold = new IblFloatItem("totalQuantityOfFuelOrFeedstockSold");    	
    SelectItem_Energy_Unit totalQuantityOfFuelOrfeedstockSold_Unit = new SelectItem_Energy_Unit("totalQuantityOfFuelOrfeedstockSold_Unit");    	

    IblFloatItem combustion_EF_ForFuelOrFeedstock = new IblFloatItem("combustion_EF_ForFuelOrFeedstock");    	
    SelectItem_Energy_EFUnit combustion_EF_ForFuelOrFeedstock_Unit = new SelectItem_Energy_EFUnit("combustion_EF_ForFuelOrFeedstock_Unit");    	        
							       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Combustion Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(productType);
    formItemList.add(productName);    

    formItemList.add(totalQuantityOfFuelOrFeedstockSold);
    formItemList.add(totalQuantityOfFuelOrfeedstockSold_Unit);    
    
    formItemList.add(combustion_EF_ForFuelOrFeedstock);            
    formItemList.add(combustion_EF_ForFuelOrFeedstock_Unit);        
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getDirectUsePhaseEmissionsFormFields_3() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    StaticTextItem productType = new StaticTextItem("productType");
    productType.setDefaultValue("Product containing GHGs that are emitted during use");
    
    TextItem productName = new TextItem("productName");    
    TextItem GHG_Name = new TextItem("GHG_Name");    
    
    IblFloatItem numberSoldInReportingPeriod = new IblFloatItem("numberSoldInReportingPeriod");    	
    IblFloatItem GHG_PerProduct = new IblFloatItem("GHG_PerProduct");    	
    
    IblFloatItem percentOfGHGReleasedDuringLifetimeUseOfProduct = new IblFloatItem("percentOfGHGReleasedDuringLifetimeUseOfProduct");    	
    IblFloatItem GWP_GHG = new IblFloatItem("GWP_GHG");    	
    							       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","GHG released Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(productType);
    formItemList.add(productName);    
    formItemList.add(GHG_Name);    
    
    formItemList.add(numberSoldInReportingPeriod);
    formItemList.add(GHG_PerProduct);        
    formItemList.add(percentOfGHGReleasedDuringLifetimeUseOfProduct);            
    formItemList.add(GWP_GHG);        
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getInDirectUsePhaseEmissionsFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    
    StaticTextItem productType = new StaticTextItem("productType");
    productType.setDefaultValue("Products that indirectly consume energy (fuels or electricity) during use");
    
    TextItem productName = new TextItem("productName");    
    IblFloatItem totalLifetimeExpectedUsesOfProduct = new IblFloatItem("totalLifetimeExpectedUsesOfProduct");        
    TextItem scenarioDescription = new TextItem("scenarioDescription");        
    IblFloatItem percentOfTotalLifetimeUsesInThisScenario = new IblFloatItem("percentOfTotalLifetimeUsesInThisScenario");    	
    IblFloatItem numberSoldInReportingPeriod = new IblFloatItem("numberSoldInReportingPeriod");    	
    
    SelectOtherItem fuelType = new SelectOtherItem("fuelType");       
    fuelType.setValueMap("Anthracite Coal",
                            "Bituminous Coal",
                            "Distillate Fuel Oil (#1, 2 & 4)",
                            "Kerosene",
                            "Landfill Gas (50%CH4, 50%CO2)",
                            "Lignite Coal",
                            "LPG",
                            "Natural Gas",
                            "Propane",
                            "Residual Fuel Oil  (#5 & 6)",
                            "Sub-bituminous Coal",
                            "Wood and Wood Waste");

    fuelType.setDefaultValue("Anthracite Coal");
    fuelType.setWidth("*");    
    fuelType.setStartRow(Boolean.TRUE);
    fuelType.setEndRow(Boolean.TRUE);
    
    IblFloatItem fuelConsumedPerUseInThisScenario = new IblFloatItem("fuelConsumedPerUseInThisScenario");
    SelectItem_Energy_Unit fuelConsumedPerUseInThisScenario_Unit = new SelectItem_Energy_Unit("fuelConsumedPerUseInThisScenario_Unit");
    IblFloatItem EF_Fuel = new IblFloatItem("EF_Fuel");    	
    SelectItem_Energy_EFUnit EF_Fuel_Unit = new SelectItem_Energy_EFUnit("EF_Fuel_Unit");    

    SelectOtherItem refrigerantType = new SelectOtherItem("refrigerantType");       
    refrigerantType.setValueMap("C2F6", 
                                "C4F10",
                                "CF4", 
                                "CO2", 
                                "HFC-125",
                                "HFC-134a",
                                "HFC-143a",
                                "HFC-152a",
                                "HFC-227ea",
                                "HFC-23",
                                "HFC-236fa",
                                "HFC-32",
                                "SF6");
    refrigerantType.setDefaultValue("C2F6");
    refrigerantType.setWidth("*");    
    refrigerantType.setStartRow(Boolean.TRUE);
    refrigerantType.setEndRow(Boolean.TRUE);
	
    IblFloatItem refrigerantLeakagePerUseInThisScenario = new IblFloatItem("refrigerantLeakagePerUseInThisScenario");
    SelectItem refrigerantLeakagePerUseInThisScenario_Unit = new SelectItem("refrigerantLeakagePerUseInThisScenario_Unit");
    refrigerantLeakagePerUseInThisScenario_Unit.setValueMap("Kg");
    refrigerantLeakagePerUseInThisScenario_Unit.setDefaultValue("Kg");
    
    IblFloatItem EF_Refrigerant = new IblFloatItem("EF_Refrigerant");    	
    SelectItem EF_Refrigerant_Unit = new SelectItem("EF_Refrigerant_Unit");    
    EF_Refrigerant_Unit.setValueMap("Kg CO2e/Kg");
    EF_Refrigerant_Unit.setDefaultValue("Kg CO2e/Kg");

    IblFloatItem electricityConsumedPerUseInThisScenario = new IblFloatItem("electricityConsumedPerUseInThisScenario");
    SelectItem_Energy_Unit electricityConsumedPerUseInThisScenario_Unit = new SelectItem_Energy_Unit("electricityConsumedPerUseInThisScenario_Unit");
    IblFloatItem EF_Electricity = new IblFloatItem("EF_Electricity");    	
    SelectItem_Energy_EFUnit EF_Electricity_Unit = new SelectItem_Energy_EFUnit("EF_Electricity_Unit");    

    IblFloatItem GHG_EmittedIndirectly = new IblFloatItem("GHG_EmittedIndirectly");
    SelectItem GHG_EmittedIndirectly_Unit = new SelectItem("GHG_EmittedIndirectly_Unit");
    GHG_EmittedIndirectly_Unit.setValueMap("Kg");
    GHG_EmittedIndirectly_Unit.setDefaultValue("Kg");

    TextItem GHG_Name = new TextItem("GHG_Name");    
    IblFloatItem GWP_GHG = new IblFloatItem("GWP_GHG");    	
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Typical Use Phase Profile Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(productType);
    formItemList.add(productName);    

    formItemList.add(totalLifetimeExpectedUsesOfProduct);
    formItemList.add(scenarioDescription);    
    formItemList.add(percentOfTotalLifetimeUsesInThisScenario);    
    formItemList.add(numberSoldInReportingPeriod);    
    
    formItemList.add(fuelType);            
    formItemList.add(fuelConsumedPerUseInThisScenario);        
    formItemList.add(fuelConsumedPerUseInThisScenario_Unit);    	
    formItemList.add(EF_Fuel);    			    
    formItemList.add(EF_Fuel_Unit);    		    
    formItemList.add(electricityConsumedPerUseInThisScenario);    			
    formItemList.add(electricityConsumedPerUseInThisScenario_Unit);
    formItemList.add(EF_Electricity);    			    
    formItemList.add(EF_Electricity_Unit);    		    
    
    formItemList.add(refrigerantType);    	
    formItemList.add(refrigerantLeakagePerUseInThisScenario);    			
    formItemList.add(refrigerantLeakagePerUseInThisScenario_Unit);
    formItemList.add(EF_Refrigerant);    			    
    formItemList.add(EF_Refrigerant_Unit);    		    

    formItemList.add(GHG_EmittedIndirectly);    			    
    formItemList.add(GHG_EmittedIndirectly_Unit);    		    
    
    formItemList.add(GHG_Name);    
    formItemList.add(GWP_GHG);    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getInDirectUsePhaseEmissionsFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    
    StaticTextItem productType = new StaticTextItem("productType");
    productType.setDefaultValue("Products that indirectly consume energy (fuels or electricity) during use");
    
    TextItem productName = new TextItem("productName");    
    IblFloatItem functionalUnitsPerformedPerProduct = new IblFloatItem("functionalUnitsPerformedPerProduct");        
    IblFloatItem numberSoldInReportingPeriod = new IblFloatItem("numberSoldInReportingPeriod");    	
    SourceDescriptionItem scenarioDescription = new SourceDescriptionItem("scenarioDescription");        
    IblFloatItem emissionsPerFunctionalUnitOfProduct = new IblFloatItem("emissionsPerFunctionalUnitOfProduct");            
    StaticTextItem emissionsPerFunctionalUnitOfProduct_Unit = new StaticTextItem("emissionsPerFunctionalUnitOfProduct_Unit");    
    emissionsPerFunctionalUnitOfProduct_Unit.setDefaultValue("Kg CO2e");
    IblFloatItem totalLifetimeEmissions = new IblFloatItem("totalLifetimeEmissions");    	
    StaticTextItem totalLifetimeEmissions_Unit = new StaticTextItem("totalLifetimeEmissions_Unit");    
    totalLifetimeEmissions_Unit.setDefaultValue("Kg CO2e");
        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Functional Unit Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(productType);
    formItemList.add(productName);    
    formItemList.add(scenarioDescription);    
    
    formItemList.add(functionalUnitsPerformedPerProduct);
    formItemList.add(numberSoldInReportingPeriod);      
    
    formItemList.add(emissionsPerFunctionalUnitOfProduct);  
    formItemList.add(emissionsPerFunctionalUnitOfProduct_Unit);    
    formItemList.add(totalLifetimeEmissions);    
    formItemList.add(totalLifetimeEmissions_Unit);    
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getEndOfLifeTreatmentOfSoldProductsFormFields() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    sourceDescriptionItem.setEndRow(Boolean.TRUE);
    
    TextItem soldProductName = new TextItem("soldProductName");

    IblFloatItem massOfSoldProductsAfterConsumerUse = new IblFloatItem("massOfSoldProductsAfterConsumerUse");
    massOfSoldProductsAfterConsumerUse.setStartRow(Boolean.TRUE);
    
    final SelectItem massOfSoldProductsAfterConsumerUse_Unit = new SelectItem("massOfSoldProductsAfterConsumerUse_Unit");
    massOfSoldProductsAfterConsumerUse_Unit.setValueMap("Tonne","lb");
    massOfSoldProductsAfterConsumerUse_Unit.setDefaultValue("Tonne");
    massOfSoldProductsAfterConsumerUse_Unit.setWidth("*");    
    
    IblFloatItem percentOfWasteTreatedByWasteTreatmentMethod = new IblFloatItem("percentOfWasteTreatedByWasteTreatmentMethod");  
    percentOfWasteTreatedByWasteTreatmentMethod.setEndRow(Boolean.TRUE);

    SelectOtherItem wasteType = new SelectOtherItem("wasteType");       
    wasteType.setValueMap("Plastic","Water Disposal");
    wasteType.setDefaultValue("Plastic");
    wasteType.setWidth("*");

    SelectOtherItem wasteTreatmentType = new SelectOtherItem("wasteTreatmentType");       
    wasteTreatmentType.setValueMap("Incinerated","Landfill","Wastewater");
    wasteTreatmentType.setDefaultValue("Incinerated");
    wasteTreatmentType.setWidth("*");
        
    IblFloatItem EF_WasteTreatmentMethod = new IblFloatItem("EF_WasteTreatmentMethod");  
    final StaticTextItem EF_WasteTreatmentMethod_Unit = new StaticTextItem("EF_WasteTreatmentMethod_Unit");
    EF_WasteTreatmentMethod_Unit.setDefaultValue("Kg CO2e/Tonne");
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Calculation Method");

    massOfSoldProductsAfterConsumerUse_Unit.addChangedHandler(new ChangedHandler() {  
        public void onChanged(ChangedEvent event) {  
            if (massOfSoldProductsAfterConsumerUse_Unit.getValue().equals("Tonne")) {                    
                EF_WasteTreatmentMethod_Unit.setValue("Kg CO2e/Tonne");                    
            } else if (massOfSoldProductsAfterConsumerUse_Unit.getValue().equals("lb")){
                EF_WasteTreatmentMethod_Unit.setValue("Kg CO2e/lb");                    
            }    
        }
    });  
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    

    formItemList.add(sourceDescriptionItem);        
    formItemList.add(soldProductName);
    formItemList.add(massOfSoldProductsAfterConsumerUse);    

    formItemList.add(massOfSoldProductsAfterConsumerUse_Unit);            
    formItemList.add(percentOfWasteTreatedByWasteTreatmentMethod);            
    
    formItemList.add(wasteType);        
    formItemList.add(wasteTreatmentType);    

    formItemList.add(EF_WasteTreatmentMethod);        
    formItemList.add(EF_WasteTreatmentMethod_Unit);    	
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

public FormItem[] getFranchisesFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
	
    TextItem franchiseName =  new TextItem("franchiseName");	
    
    IblFloatItem scope1EmissionsOfFranchise = new IblFloatItem("scope1EmissionsOfFranchise");
    scope1EmissionsOfFranchise.setStartRow(Boolean.TRUE);
    
    StaticTextItem scope1EmissionsOfFranchise_Unit = new StaticTextItem("scope1EmissionsOfFranchise_Unit");
    scope1EmissionsOfFranchise_Unit.setDefaultValue("Kg CO2e");
    
    IblFloatItem scope2EmissionsOfFranchise = new IblFloatItem("scope2EmissionsOfFranchise");
    StaticTextItem scope2EmissionsOfFranchise_Unit = new StaticTextItem("scope2EmissionsOfFranchise_Unit");
    scope2EmissionsOfFranchise_Unit.setDefaultValue("Kg CO2e");            
    
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Franchise Specific Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(franchiseName);            
    formItemList.add(scope1EmissionsOfFranchise);
    formItemList.add(scope1EmissionsOfFranchise_Unit);    	
    formItemList.add(scope2EmissionsOfFranchise);    			    
    formItemList.add(scope2EmissionsOfFranchise_Unit);    		    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getFranchisesFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();

    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       
    TextItem franchiseName =  new TextItem("franchiseName");	
    
    CheckboxItem flag_floorSpaceData = new CheckboxItem();  
    flag_floorSpaceData.setName("flag_floorSpaceData");  
    flag_floorSpaceData.setTitle("Floor space data us available?");  
    flag_floorSpaceData.setRedrawOnChange(true);  
    flag_floorSpaceData.setWidth(50);  
    flag_floorSpaceData.setDefaultValue(false);    
    flag_floorSpaceData.setColSpan(4);//EndRow(Boolean.TRUE);
    
    IblFloatItem floorSpace = new IblFloatItem("floorSpace");
    StaticTextItem floorSpace_Unit = new StaticTextItem("floorSpace_Unit");
    floorSpace_Unit.setDefaultValue("m2");

    IblFloatItem average_EF = new IblFloatItem("average_EF");
    StaticTextItem average_EF_Unit = new StaticTextItem("average_EF_Unit");
    average_EF_Unit.setDefaultValue("Kg CO2e/m2/year");
        
    TextItem buildingOrAssetName =  new TextItem("buildingOrAssetName");
    buildingOrAssetName.setVisible(Boolean.FALSE);
    
    IblFloatItem numberOfbuildingsOrAssetTypes = new IblFloatItem("numberOfbuildingsOrAssetTypes");  
    numberOfbuildingsOrAssetTypes.setVisible(Boolean.FALSE);
    
    IblFloatItem averageEmissionsPerBuildingOrAssetType = new IblFloatItem("averageEmissionsPerBuildingOrAssetType");
    averageEmissionsPerBuildingOrAssetType.setVisible(Boolean.FALSE);
    averageEmissionsPerBuildingOrAssetType.setStartRow(Boolean.TRUE);
    
    TextItem averageEmissionsPerBuildingOrAssetType_Unit = new TextItem("averageEmissionsPerBuildingOrAssetType_Unit");
    averageEmissionsPerBuildingOrAssetType_Unit.setVisible(Boolean.FALSE);
        
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Average Data Method");

    floorSpace.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    floorSpace_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    average_EF.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    average_EF_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return (Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    
    buildingOrAssetName.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });      
    numberOfbuildingsOrAssetTypes.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    averageEmissionsPerBuildingOrAssetType.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });  
    averageEmissionsPerBuildingOrAssetType_Unit.setShowIfCondition(new FormItemIfFunction() {  
        public boolean execute(FormItem item, Object value, DynamicForm form) {  
            return !(Boolean)form.getValue("flag_floorSpaceData");  
        }  
    });      
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(franchiseName);       
    formItemList.add(flag_floorSpaceData);
    formItemList.add(floorSpace);    	
    formItemList.add(floorSpace_Unit);    			     		    
    formItemList.add(average_EF);    			     		    
    formItemList.add(average_EF_Unit);    	
    formItemList.add(buildingOrAssetName);
    formItemList.add(numberOfbuildingsOrAssetTypes);
    formItemList.add(averageEmissionsPerBuildingOrAssetType);
    formItemList.add(averageEmissionsPerBuildingOrAssetType_Unit);    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}


public FormItem[] getInvestmentsFormFields_1() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       

    final SelectItem investmentType = new SelectItem("investmentType");       
    investmentType.setValueMap("Equity",
                               "Debt with know use of proceeds",
                               "Project Finance",
                               "Debt without known use of proceeds",
                               "Managed investments and client services",
                               "Other investment category");
    investmentType.setDefaultValue("Equity");
    investmentType.setWidth("*");

    IblFloatItem scope1Emissions = new IblFloatItem("scope1Emissions");
    scope1Emissions.setStartRow(Boolean.TRUE);    
    StaticTextItem scope1Emissions_Unit = new StaticTextItem("scope1Emissions_Unit");
    scope1Emissions_Unit.setDefaultValue("Kg CO2e");
    
    IblFloatItem scope2Emissions = new IblFloatItem("scope2Emissions");
    StaticTextItem scope2Emissions_Unit = new StaticTextItem("scope2Emissions_Unit");
    scope2Emissions_Unit.setDefaultValue("Kg CO2e");            
    
    IblFloatItem percentShareOfInvestment = new IblFloatItem("percentShareOfInvestment");

    IblFloatItem anticipatedLifetimeScope1Emissions = new IblFloatItem("anticipatedLifetimeScope1Emissions");
    anticipatedLifetimeScope1Emissions.setStartRow(Boolean.TRUE);    
    StaticTextItem anticipatedLifetimeScope1Emissions_Unit = new StaticTextItem("anticipatedLifetimeScope1Emissions_Unit");
    anticipatedLifetimeScope1Emissions_Unit.setDefaultValue("Kg CO2e");
    
    IblFloatItem anticipatedLifetimeScope2Emissions = new IblFloatItem("anticipatedLifetimeScope2Emissions");
    StaticTextItem anticipatedLifetimeScope2Emissions_Unit = new StaticTextItem("anticipatedLifetimeScope2Emissions_Unit");
    anticipatedLifetimeScope2Emissions_Unit.setDefaultValue("Kg CO2e");            
       
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Investment Specific Method");

// Change Handlers    
    investmentType.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                
                if((Boolean) investmentType.getValue().equals("Project Finance")){  
                    event.getForm().getItem("anticipatedLifetimeScope1Emissions").show();
                    event.getForm().getItem("anticipatedLifetimeScope1Emissions_Unit").show();
                    event.getForm().getItem("anticipatedLifetimeScope2Emissions").show();
                    event.getForm().getItem("anticipatedLifetimeScope2Emissions_Unit").show();                    
                } else {                    
                    event.getForm().getItem("anticipatedLifetimeScope1Emissions").hide();
                    event.getForm().getItem("anticipatedLifetimeScope1Emissions_Unit").hide();
                    event.getForm().getItem("anticipatedLifetimeScope2Emissions").hide();
                    event.getForm().getItem("anticipatedLifetimeScope2Emissions_Unit").hide();                    
                }
            }  
    });  
        
    anticipatedLifetimeScope1Emissions.setVisible(Boolean.FALSE);
    anticipatedLifetimeScope1Emissions_Unit.setVisible(Boolean.FALSE);
    anticipatedLifetimeScope2Emissions.setVisible(Boolean.FALSE);
    anticipatedLifetimeScope2Emissions_Unit.setVisible(Boolean.FALSE);
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(investmentType);
    
    formItemList.add(scope1Emissions);      
    formItemList.add(scope1Emissions_Unit);      
    formItemList.add(scope2Emissions);    
    formItemList.add(scope2Emissions_Unit);      
    
    formItemList.add(percentShareOfInvestment);        
    
    formItemList.add(anticipatedLifetimeScope1Emissions);    	    
    formItemList.add(anticipatedLifetimeScope1Emissions_Unit);    	
    formItemList.add(anticipatedLifetimeScope2Emissions);    		
    formItemList.add(anticipatedLifetimeScope2Emissions_Unit);    	
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}
public FormItem[] getInvestmentsFormFields_2() {

//-- setValidators for the forms for common types.
    initializeValidators();
	
    SourceDescriptionItem sourceDescriptionItem = new SourceDescriptionItem("sourceDescription");       

    SelectItem investmentType = new SelectItem("investmentType");       
    investmentType.setValueMap("Equity",
                               "Debt with know use of proceeds",
                               "Project Finance",
                               "Debt without known use of proceeds",
                               "Managed investments and client services",
                               "Other investment category");
    investmentType.setDefaultValue("Equity");
    investmentType.setWidth("*");
    
    SelectOtherItem investmentSector = new SelectOtherItem("investmentSector");       
    investmentSector.setValueMap("Food and Drink",
                               "Telecommunication",
                               "Pharmaceutical",
                               "Energy Generation");
    investmentSector.setDefaultValue("Food and Drink");
    investmentSector.setWidth("*");

    IblFloatItem investmentAmount = new IblFloatItem("investmentAmount");
    investmentAmount.setStartRow(Boolean.TRUE);    
    
    StaticTextItem investmentAmount_Unit = new StaticTextItem("investmentAmount_Unit");
    investmentAmount_Unit.setDefaultValue("US Dollar");
    
    IblFloatItem EF_SectorSpecific = new IblFloatItem("EF_SectorSpecific");
    StaticTextItem EF_SectorSpecific_Unit = new StaticTextItem("EF_SectorSpecific_Unit");
    EF_SectorSpecific_Unit.setDefaultValue("Kg CO2e/$");            
           
    IblTextAreaItem userNotesOnDataItem = new IblTextAreaItem("userNotesOnData");        
    IblDateItem dataBeginDateItem = new IblDateItem("dataBeginDate", currentInventoryBeginDateMin,currentInventoryEndDateMax, validateDateRange);
    dataBeginDateItem.setStartRow(Boolean.TRUE);
    IblDateItem dataEndDateItem = new IblDateItem("dataEndDate", currentInventoryBeginDateMin, currentInventoryEndDateMax, validateDateRange);        
    TextItem_MethodType methodTypeItem = new TextItem_MethodType("methodType","Economic Data Method");
    
    final List<FormItem> formItemList = new ArrayList<FormItem>();
    
    formItemList.add(sourceDescriptionItem);        
    formItemList.add(investmentType);
    formItemList.add(investmentSector);    
    
    formItemList.add(investmentAmount);    
    formItemList.add(investmentAmount_Unit);      
         
    formItemList.add(EF_SectorSpecific);    	    
    formItemList.add(EF_SectorSpecific_Unit);    	
    
    formItemList.add(dataBeginDateItem);
    formItemList.add(dataEndDateItem);
    formItemList.add(userNotesOnDataItem);    
    formItemList.add(methodTypeItem);
    
    FormItem[] formItemArray = new FormItem[formItemList.size()];
    formItemList.toArray(formItemArray);
    return formItemArray;

}

}


