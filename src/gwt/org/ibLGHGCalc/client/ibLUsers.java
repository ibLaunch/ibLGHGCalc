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
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.DateDisplayFormatter;
import com.smartgwt.client.util.DateInputFormatter;
import com.smartgwt.client.widgets.form.validator.DateRangeValidator;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.IsFloatValidator;
import com.smartgwt.client.widgets.form.validator.Validator;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ibLUsers implements EntryPoint {
    /**
     * This is the entry point method.
     */

    private static final int NORTH_HEIGHT = 107; // MASTHEAD_HEIGHT + APPLICATION_MENU_HEIGHT + USER_ORGANIZATION_HEADER_HEIGHT
    private VLayout mainVLayout; // This is main Layout
    private HLayout northHLayout;  // This is for header purpose only

    private static final int LEFT_SECTION_BUTTON_WIDTH = 180;
    private static final int LEFT_SECTION_BUTTON_HEIGHT = 25;
    
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
    
//- Data upload Options
    private String[] epaDataLoadOptions = {
				"Stationary Combustion Info",
				"Data - EPA Purchased Electricity",
				"Data - EPA Purchased Steam",
				"Data - EPA Employee Business Travel - By Vehicle",
				"Data - EPA Employee Business Travel - By Vehicle",
				"Data - EPA Employee Business Travel - By Bus",
				"Data - EPA Employee Business Travel - By Rail",
				"Data - EPA Employee Business Travel - By Air",
				"Data - EPA Employee Commuting - By Vehicle",
				"Data - EPA Employee Commuting - By Rail",
				"Data - EPA Employee Commuting - By Bus",
				"Data - EPA Product Transport - By Vehicle",
				"Data - EPA Product Transport - By Heavy Duty Trucks",
				"Data - EPA Product Transport - By Rail",
				"Data - EPA Product Transport - By Water or Air",
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
    
//-- Validators
    private Date currentInventoryBeginDateMin;
    private Date currentInventoryEndDateMax;

    private final DateRangeValidator validateDateRange = new DateRangeValidator();
    private final IsFloatValidator floatValidator = new IsFloatValidator();
    private final FloatPrecisionValidator floatPrecisionValidator = new FloatPrecisionValidator();

    private final TabSet tabSet = new TabSet();
    //private final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd-MM-yyyy"+" "+"h:mm:a" );
    private final DateTimeFormat displayDateFormatter = DateTimeFormat.getFormat("MMM d, yyyy");
    private final DateTimeFormat inputDateFormatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'hh:mm:ss");

    private final String eFUploadFormSubmitAction = "EF_StationaryCombustion_EPA";
    private final DynamicForm uploadForm = new DynamicForm();

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

//-- refridgerationAirConditioning For Material balance method
    private final Window refridgerationAirConditioningFormWindow_1 = new Window();
    private final DynamicForm refridgerationAirConditioningForm_1 = new DynamicForm();
    private final ListGrid refridgerationAirConditioningDataGrid_1 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            refridgerationAirConditioningForm_1.editRecord(record);
                            refridgerationAirConditioningFormWindow_1.show();
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
                                               refridgerationAirConditioningDataGrid_1.removeData(record);
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
//-- For refridgerationAirConditioning Simplified Material balance method
    private final Window refridgerationAirConditioningFormWindow_2 = new Window();
    private final DynamicForm refridgerationAirConditioningForm_2 = new DynamicForm();
    private final ListGrid refridgerationAirConditioningDataGrid_2 = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            refridgerationAirConditioningForm_2.editRecord(record);
                            refridgerationAirConditioningFormWindow_2.show();
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
                                           refridgerationAirConditioningDataGrid_2.removeData(record);
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

//-- For refridgerationAirConditioning Screening method
    private final Window refridgerationAirConditioningFormWindow_3 = new Window();
    private final DynamicForm refridgerationAirConditioningForm_3 = new DynamicForm();
    private final ListGrid refridgerationAirConditioningDataGrid_3 = new ListGrid(){
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
    };

//-- fireSuppression
    private final TabSet fireSuppressionTabSet = new TabSet();
    private final VLayout fireSuppressionLayout = new VLayout(15);

//-- fireSuppression For Material balance method
    private final Window fireSuppressionFormWindow_1 = new Window();
    private final DynamicForm fireSuppressionForm_1 = new DynamicForm();
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
//-- For fireSuppression Simplified Material balance method
    private final Window fireSuppressionFormWindow_2 = new Window();
    private final DynamicForm fireSuppressionForm_2 = new DynamicForm();
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

//-- For fireSuppression Screening method    
    private final Window fireSuppressionFormWindow_3 = new Window();
    private final DynamicForm fireSuppressionForm_3 = new DynamicForm();
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

    private final RefridgerationAirConditioningInfoDS refridgerationAirConditioningInfoDS = RefridgerationAirConditioningInfoDS.getInstance();
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

    //private final HLayout emissionsSummaryLayout = new HLayout();
    private final DynamicForm emissionsSummaryInputForm = new DynamicForm();
    private final ListGrid emissionsSummaryDataGrid = new ListGrid();
    private final DetailViewer emissionsSummaryDetailViewer = new DetailViewer();
    private final EmissionsSummaryDS emissionsSummaryDS = EmissionsSummaryDS.getInstance();
    private final VLayout emissionsSummaryInputVLayout = new VLayout();
    private final VLayout emissionsSummaryVLayout = new VLayout();
    private final VLayout detailViewerVLayout = new VLayout();
    private final Label emissionsSummaryDetailViewerLabel = new Label("Emission Details:");

    private final ListGrid stationaryCombustionDataGrid = new ListGrid(){
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

    private final ListGrid mobileCombustionDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //mobileCombustionForm.editSelectedData(mobileCombustionDataGrid);
                            mobileCombustionForm.editRecord(record);
                            mobileCombustionFormWindow.show();
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

//--Purchased Electricity
    private final TabSet purchasedElectricityTabSet = new TabSet();
    private final VLayout purchasedElectricityLayout = new VLayout(15);

    private final Window purchasedElectricityFormWindow = new Window();
    private final DynamicForm purchasedElectricityForm = new DynamicForm();
    private final PurchasedElectricityInfoDS purchasedElectricityInfoDS = PurchasedElectricityInfoDS.getInstance();
    private final EF_PurchasedElectricity_EPADS theEF_PurchasedElectricity_EPADS = EF_PurchasedElectricity_EPADS.getInstance();

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

//--Purchased Steam
    private final TabSet purchasedSteamTabSet = new TabSet();
    private final VLayout purchasedSteamLayout = new VLayout(15);

    private final Window purchasedSteamFormWindow = new Window();
    private final DynamicForm purchasedSteamForm = new DynamicForm();
    private final PurchasedSteamInfoDS purchasedSteamInfoDS = PurchasedSteamInfoDS.getInstance();
    private final EF_PurchasedSteam_EPADS theEF_PurchasedSteam_EPADS = EF_PurchasedSteam_EPADS.getInstance();

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

//--Optinal Source DS
    private final OptionalSourceInfoDS optionalSourceInfoDS = OptionalSourceInfoDS.getInstance();

//--employee Business Travel
    private final TabSet employeeBusinessTravelTabSet = new TabSet();
    private final VLayout employeeBusinessTravelLayout = new VLayout(15);
//--employeeBusinessTravelByVehicle
    private final Window employeeBusinessTravelByVehicleFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByVehicleForm = new DynamicForm();
    private final EF_VehicleType_EPADS theEF_VehicleType_EPADS = EF_VehicleType_EPADS.getInstance();
    private final ListGrid employeeBusinessTravelByVehicleDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeBusinessTravelByVehicleForm.editSelectedData(employeeBusinessTravelByVehicleDataGrid);
                            employeeBusinessTravelByVehicleForm.editRecord(record);
                            employeeBusinessTravelByVehicleFormWindow.show();
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
					   					    //employeeBusinessTravelByVehicleDataGrid.removeSelectedData();
                       			             employeeBusinessTravelByVehicleDataGrid.removeData(record);
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

//--employeeBusinessTravelByRail
    private final Window employeeBusinessTravelByRailFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByRailForm = new DynamicForm();
    private final EF_RailType_EPADS theEF_RailType_EPADS = EF_RailType_EPADS.getInstance();
    private final ListGrid employeeBusinessTravelByRailDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeBusinessTravelByRailForm.editSelectedData(employeeBusinessTravelByRailDataGrid);
                            employeeBusinessTravelByRailForm.editRecord(record);
                            employeeBusinessTravelByRailFormWindow.show();
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
					   					    //employeeBusinessTravelByRailDataGrid.removeSelectedData();
                       			             employeeBusinessTravelByRailDataGrid.removeData(record);
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

//--employeeBusinessTravelByBus
    private final Window employeeBusinessTravelByBusFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByBusForm = new DynamicForm();
    private final EF_BusType_EPADS theEF_BusType_EPADS = EF_BusType_EPADS.getInstance();
    private final ListGrid employeeBusinessTravelByBusDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeBusinessTravelByBusForm.editSelectedData(employeeBusinessTravelByBusDataGrid);
                            employeeBusinessTravelByBusForm.editRecord(record);
                            employeeBusinessTravelByBusFormWindow.show();
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
					   					    //employeeBusinessTravelByBusDataGrid.removeSelectedData();
                       			             employeeBusinessTravelByBusDataGrid.removeData(record);
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

//--employeeBusinessTravelByAir
    private final Window employeeBusinessTravelByAirFormWindow = new Window();
    private final DynamicForm employeeBusinessTravelByAirForm = new DynamicForm();
    private final EF_AirTravelType_EPADS theEF_AirTravelType_EPADS = EF_AirTravelType_EPADS.getInstance();
    private final ListGrid employeeBusinessTravelByAirDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeBusinessTravelByAirForm.editSelectedData(employeeBusinessTravelByAirDataGrid);
                            employeeBusinessTravelByAirForm.editRecord(record);
                            employeeBusinessTravelByAirFormWindow.show();
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
					   					    //employeeBusinessTravelByAirDataGrid.removeSelectedData();
                       			             employeeBusinessTravelByAirDataGrid.removeData(record);
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

//--employeeCommuting
    private final TabSet employeeCommutingTabSet = new TabSet();
    private final VLayout employeeCommutingLayout = new VLayout(15);

//--employeeCommutingByVehicle
    private final Window employeeCommutingByVehicleFormWindow = new Window();
    private final DynamicForm employeeCommutingByVehicleForm = new DynamicForm();
    //private final EF_VType_EPADS theEF_VehicleTypeType_EPADS = EF_VehicleTypeType_EPADS.getInstance();
    private final ListGrid employeeCommutingByVehicleDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeCommutingByVehicleForm.editSelectedData(employeeCommutingByVehicleDataGrid);
                            employeeCommutingByVehicleForm.editRecord(record);
                            employeeCommutingByVehicleFormWindow.show();
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
					   					    //employeeCommutingByVehicleDataGrid.removeSelectedData();
				                       			             employeeCommutingByVehicleDataGrid.removeData(record);
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

//--employeeCommutingByRail
    private final Window employeeCommutingByRailFormWindow = new Window();
    private final DynamicForm employeeCommutingByRailForm = new DynamicForm();
    //private final EF_VehicleType_EPADS theEF_RailType_EPADS = EF_RailType_EPADS.getInstance();
    private final ListGrid employeeCommutingByRailDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeCommutingByRailForm.editSelectedData(employeeCommutingByRailDataGrid);
                            employeeCommutingByRailForm.editRecord(record);
                            employeeCommutingByRailFormWindow.show();
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
					   					    //employeeCommutingByRailDataGrid.removeSelectedData();
				                       			             employeeCommutingByRailDataGrid.removeData(record);
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

//--employeeCommutingByBus
    private final Window employeeCommutingByBusFormWindow = new Window();
    private final DynamicForm employeeCommutingByBusForm = new DynamicForm();
    //private final EF_VehicleType_EPADS theEF_BusType_EPADS = EF_BusType_EPADS.getInstance();
    private final ListGrid employeeCommutingByBusDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //employeeCommutingByBusForm.editSelectedData(employeeCommutingByBusDataGrid);
                            employeeCommutingByBusForm.editRecord(record);
                            employeeCommutingByBusFormWindow.show();
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
					   					    //employeeCommutingByBusDataGrid.removeSelectedData();
				                       			             employeeCommutingByBusDataGrid.removeData(record);
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

//--product Transport
    private final TabSet productTransportTabSet = new TabSet();
    private final VLayout productTransportLayout = new VLayout(15);

//--productTransportByVehicle
    private final Window productTransportByVehicleFormWindow = new Window();
    private final DynamicForm productTransportByVehicleForm = new DynamicForm();
    private final EF_ProductTransport_VehicleType_EPADS theEF_ProductTransport_VehicleType_EPADS = EF_ProductTransport_VehicleType_EPADS.getInstance();
    private final ListGrid productTransportByVehicleDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //productTransportByVehicleForm.editSelectedData(productTransportByVehicleDataGrid);
                            productTransportByVehicleForm.editRecord(record);
                            productTransportByVehicleFormWindow.show();
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
					   					    //productTransportByVehicleDataGrid.removeSelectedData();
				                       			             productTransportByVehicleDataGrid.removeData(record);
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

//--productTransportByHeavyDutyTrucks
    private final Window productTransportByHeavyDutyTrucksFormWindow = new Window();
    private final DynamicForm productTransportByHeavyDutyTrucksForm = new DynamicForm();
    private final EF_ProductTransportType_EPADS theEF_ProductTransportType_EPADS= EF_ProductTransportType_EPADS.getInstance();
    private final ListGrid productTransportByHeavyDutyTrucksDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //productTransportByHeavyDutyTrucksForm.editSelectedData(productTransportByHeavyDutyTrucksDataGrid);
                            productTransportByHeavyDutyTrucksForm.editRecord(record);
                            productTransportByHeavyDutyTrucksFormWindow.show();
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
					   					    //productTransportByHeavyDutyTrucksDataGrid.removeSelectedData();
				                       			             productTransportByHeavyDutyTrucksDataGrid.removeData(record);
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

//--productTransportByRail
    private final Window productTransportByRailFormWindow = new Window();
    private final DynamicForm productTransportByRailForm = new DynamicForm();
    //private final EF_ProductTransportType_EPADS theEF_ProductTransportType_EPADS= EF_ProductTransportType_EPADS.getInstance();
    private final ListGrid productTransportByRailDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //productTransportByRailForm.editSelectedData(productTransportByRailDataGrid);
                            productTransportByRailForm.editRecord(record);
                            productTransportByRailFormWindow.show();
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
					   					    //productTransportByRailDataGrid.removeSelectedData();
				                       			             productTransportByRailDataGrid.removeData(record);
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

//--productTransportByWaterAir
    private final Window productTransportByWaterAirFormWindow = new Window();
    private final DynamicForm productTransportByWaterAirForm = new DynamicForm();
    //private final EF_ProductTransportType_EPADS theEF_ProductTransportType_EPADS= EF_ProductTransportType_EPADS.getInstance();
    private final ListGrid productTransportByWaterAirDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //productTransportByWaterAirForm.editSelectedData(productTransportByWaterAirDataGrid);
                            productTransportByWaterAirForm.editRecord(record);
                            productTransportByWaterAirFormWindow.show();
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
                                                        //productTransportByWaterAirDataGrid.removeSelectedData();
                                                         productTransportByWaterAirDataGrid.removeData(record);
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

//--wasteStreamCombustion
    private final VLayout wasteStreamCombustionLayout = new VLayout(15);
    private final TabSet wasteStreamCombustionTabSet = new TabSet();

    private final Window wasteStreamCombustionFormWindow = new Window();
    private final DynamicForm wasteStreamCombustionForm = new DynamicForm();
    private final WasteStreamCombustionInfoDS wasteStreamCombustionInfoDS= WasteStreamCombustionInfoDS.getInstance();
    private final ListGrid wasteStreamCombustionDataGrid = new ListGrid(){
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

                String fieldName = this.getFieldName(colNum);

                if (fieldName.equals("editButtonField")) {
                    EditImageButton editImg = new EditImageButton();
                    editImg.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            //wasteStreamCombustionForm.editSelectedData(wasteStreamCombustionDataGrid);
                            wasteStreamCombustionForm.editRecord(record);
                            wasteStreamCombustionFormWindow.show();
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
                                            //wasteStreamCombustionDataGrid.removeSelectedData();
                                             wasteStreamCombustionDataGrid.removeData(record);
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

public void onModuleLoad() {


// - New code added below for layout management
      GWT.log("init OnLoadModule()...", null);

      // get rid of scroll bars, and clear out the window's built-in margin,
      // because we want to take advantage of the entire client area
      //Window.enableScrolling(false);
      //Window.setMargin("0px");

      // initialise the main layout container
      mainVLayout = new VLayout();
      mainVLayout.setWidth100();
      mainVLayout.setHeight100();

      // initialise the North layout container
      northHLayout = new HLayout();
      northHLayout.setHeight(NORTH_HEIGHT);

      VLayout vLayout = new VLayout();
      // add the Masthead to the nested layout container
      vLayout.addMember(new Masthead());
      // add the UserOrganizationHeader to the nested layout container
      vLayout.addMember(new UserOrganizationHeader());
      // add the Application menu to the nested layout container
      //vLayout.addMember(new ApplicationMenu());

      // add the nested layout container to the  North layout container
      northHLayout.addMember(vLayout);

      middleBottomHLayout.setOverflow(Overflow.AUTO);
      middleMiddleHLayout.setOverflow(Overflow.AUTO);
      
//-- setValidators for the forms for common types.
//      setValidators();

   //stationaryCombustionDataGrid.setDateFormatter(dateFormatter);

   DateUtil.setDefaultDisplayTimezone("+00:00");

   DateUtil.setShortDateDisplayFormatter(new DateDisplayFormatter() {
    public String format(Date date) {
        if(date == null) {
            return null;
        }
        // set the date format as per the WWF date preference
        //DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd-MM-yyyy"+" "+"h:mm:a" );
        return displayDateFormatter.format(date, TimeZone.createTimeZone(0));
        /*
        final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("MMM d, yyyy");
        String format = dateFormatter.format(date);
        return format;
         *
         */
     }
   });

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
   programTypeSelectItem.setValueMap("EPA Climate Leaders", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");

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
    middleVLayout.setWidth100();
    middleVLayout.setHeight100();
    //middleVLayout.setAnimateMembers(true);
    
    final Label middleMiddleHLayoutLable = new Label("This middleMiddleHLayout");
    final Label middleBottomHLayoutLable = new Label("This middleBottomHLayout");

    middleMiddleHLayout.addChild(middleMiddleHLayoutLable);
    middleBottomHLayout.addChild(middleBottomHLayoutLable);
    middleBottomHLayout.animateShow(AnimationEffect.SLIDE);    
    middleBottomHLayout.hide();
    
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
    leftSectionStack.setBackgroundColor("#A9F5F2");
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
     programTypeItem.setValueMap("EPA Climate Leaders", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");

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

    Label emissionsSummaryLabel = getSectionLink("Emissions Summary", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Emissions Summary");
       }
    });
    reportSection.addItem(emissionsSummaryLabel);

    Label emissionsReportLabel = getSectionLink("Emissions Report", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Emissions Report");
       }
    });
    reportSection.addItem(emissionsReportLabel);
    leftSectionStack.addSection(reportSection);

//--Direct Emissions Sources

    SectionStackSection directEmissionsSourcesSection = new SectionStackSection("Direct Emission Sources");
    directEmissionsSourcesSection.setID("directEmissionsSources");
    //directEmissionsSourcesSection.setExpanded(true);
    directEmissionsSourcesSection.setResizeable(Boolean.FALSE);

    Label getStationaryCombustionInfoLabel = getSectionLink("Stationary", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Stationary Combustions Sources");
       }
    });
    directEmissionsSourcesSection.addItem(getStationaryCombustionInfoLabel);

    Label getMobileCombustionInfoLabel = getSectionLink("Mobile", new ClickHandler() {
       @Override
       public void onClick(ClickEvent event) {
          displayEmissionSourceInfo("Mobile Combustions Sources");
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

//--Calling form to Add New Record to Stationary Combustion Source
    initStationaryCombustionEditForm();

//--Defining Mobile Combustion tab layout
    mobileCombustionTab();

//--Calling form to Add New Record to Mobile Combustion Source
    initMobileCombustionEditForm();

//--Defining Refridgeration Air Conditioning tab layout
    refridgerationAirConditioningTab ();

//--Initializing forms to Add New Record to Mobile Combustion Source
    initRefridgerationAirConditioningEditForm_1();
    initRefridgerationAirConditioningEditForm_2();
    initRefridgerationAirConditioningEditForm_3();

//--Defining Fire Suppression tab layout
    fireSuppressionTab ();

//--Initializing forms to Add New Record to FireSuppression Source
    initFireSuppressionEditForm_1();
    initFireSuppressionEditForm_2();
    initFireSuppressionEditForm_3();

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
private void fileUploadTab(){

    uploadForm.setEncoding(Encoding.MULTIPART);
    uploadForm.setAction(eFUploadFormSubmitAction);

    final UploadItem fileUpload = new UploadItem("fileUpload");
    fileUpload.setTitle("File");
    fileUpload.setWidth(300);

    //final SelectItem fileTypeItem = new SelectItem("fileType");
    fileTypeItem.setTitle("Select File to upload");
    /*
    fileTypeItem.setValueMap("EF - EPA Stationary Combustion", "Stationary Combustion Info",
                             "EF - EPA Purchased Electricity",
                             "Data - EPA Purchased Electricity",
                             "EF - EPA Purchased Steam",
                             "Data - EPA Purchased Steam",
                             "EF - EPA Waste Stream Combustion",
                             "EF - EPA Vehicle Type",
                             "Data - EPA Employee Business Travel - By Vehicle",
                             "EF - EPA Rail Type",
                             "EF - EPA Bus Type",
                             "EF - EPA Air Travel Type",
                             "EF - EPA Product Transport Vehicle Type",
                             "EF - EPA Product Transport Type",
                             "Data - EPA Employee Business Travel - By Vehicle",
                             "Data - EPA Employee Business Travel - By Bus",
                             "Data - EPA Employee Business Travel - By Rail",
                             "Data - EPA Employee Business Travel - By Air",
                             "Data - EPA Employee Commuting - By Vehicle",
                             "Data - EPA Employee Commuting - By Rail",
                             "Data - EPA Employee Commuting - By Bus",
                             "Data - EPA Product Transport - By Vehicle",
                             "Data - EPA Product Transport - By Heavy Duty Trucks",
                             "Data - EPA Product Transport - By Rail",
                             "Data - EPA Product Transport - By Water or Air",
                             "Mobile Combustion Info - EF_CO2_MobileCombustion_EPA",
                             "Mobile Combustion Info - VehicleType_EPA",
                             "Mobile Combustion Info - EF_CH4N2O_MobileCombustionGasoline_EPA",
                             "Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonGasoline_EPA",
                             "Mobile Combustion Info - EF_CH4N2O_MobileCombustionNonHighway_EPA",
                             "Mobile Combustion Info - EF_CH4N2O_MobileCombustion_EPA",
                             "Mobile Combustion Info",
                             "RefridgerationAirConditioning - GWP_RefridgerationAirConditioning_EPA",
                             "RefridgerationAirConditioning - EquipmentCapacityRange_EPA",
                             "Refridgeration Air Conditioning - Company-Wide Material Balance Method",
                             "Refridgeration Air Conditioning - Company-Wide Simplified Material Balance Method",
                             "Refridgeration Air Conditioning - Source Level Screening Method",
                             "Fire Suppression - Company-Wide Material Balance Method",
                             "Fire Suppression - Company-Wide Simplified Material Balance Method",
                             "Fire Suppression - Source Level Screening Method"
                             );
                  */
    fileTypeItem.setValueMap(epaDataLoadOptions);

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
    items.add(fileTypeItem);
    items.add(fileUpload);

    FormItem[] fitems = new FormItem[items.size()];
    items.toArray(fitems);
    uploadForm.setItems(fitems);

//-Load form and button on Vstack
    //VStack fileUploadLayout = new VStack();
    fileUploadLayout.setWidth100();
    fileUploadLayout.setMembersMargin(10);
    fileUploadLayout.setDefaultLayoutAlign(Alignment.LEFT);

    fileUploadLayout.addMember(uploadForm);
    fileUploadLayout.addMember(uploadButton);

//---Adding Stationary Combustion tab to topTab
/*    final Tab fileUploadTab = new Tab("Emission Factor File Upload");
    fileUploadTab.setPane(fileUploadLayout);
    tabSet.addTab(fileUploadTab);
*/
    /*
    //--Add fileUpload in left section
    SectionStackSection fileUploadSection = new SectionStackSection("fileUpload");
    fileUploadSection.addItem(fileUploadLayout);
    leftSectionStack.addSection(fileUploadSection);
    */
    }

private void initializeValidators(){

    currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
    currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
    
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
private void stationaryCombustionTab() {
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

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

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
        //removeButtonField.setWidth(100);

        stationaryCombustionDataGrid.setFields(sourceDescriptionField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, fuelUsedBeginDateField, fuelUsedEndDateField,editButtonField, removeButtonField);

        //stationaryCombustionTabLayout.addMember(stationaryCombustionDataGrid);

        IButton newStationaryCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newStationaryCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        newStationaryCombustionButton.setIcon(ADD_ICON_IMAGE);

        newStationaryCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //-- setValidators for the forms for common types.
                initializeValidators();
                stationaryCombustionForm.editNewRecord();
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
private void initStationaryCombustionEditForm() {

    stationaryCombustionForm.setCellPadding(5);
    stationaryCombustionForm.setWidth("100%");
    //stationaryCombustionForm.setTitle("Please enter stationary combustion Source information:");

//-- setValidators for the forms for common types.
      initializeValidators();

//-- Form fields  -------------------------------------------------------------------
    TextItem fuelSourceDescription = new TextItem("fuelSourceDescription");
    fuelSourceDescription.setTitle("Fuel Source Description");
    fuelSourceDescription.setSelectOnFocus(true);
    fuelSourceDescription.setWrapTitle(false);
    fuelSourceDescription.setDefaultValue("[Enter Source Description]");
    fuelSourceDescription.setRequired(Boolean.TRUE);
    //fuelSourceDescription

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

    FloatItem fuelQuantityItem = new FloatItem();
    fuelQuantityItem.setName("fuelQuantity");
    fuelQuantityItem.setValidators(floatValidator);
    fuelQuantityItem.setValidateOnExit(Boolean.TRUE);
    fuelQuantityItem.setValidateOnChange(Boolean.TRUE);
    fuelQuantityItem.setRequired(Boolean.TRUE);

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setWidth("*");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    //fuelUsedBeginDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedBeginDateItem.setRequired(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setWidth("*");
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    //fuelUsedEndDateItem.setValidateOnExit(Boolean.TRUE);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedEndDateItem.setRequired(Boolean.TRUE);

    //Record organizationSelectFormRecord = UserOrganizationHeader.organizationSelectForm.getValuesAsRecord();
    //Integer currentOrganizationId = organizationSelectFormRecord.getAttributeAsInt("id");

    //IntegerItem organizationId = new IntegerItem();
    //organizationId.setName("organizationId");
    //SC.say("currentOrganizationId " + currentOrganizationId);
    //organizationId.setValue(currentOrganizationId);
    
//    organizationId.setValue(currentOrganizationId);
//    Integer currentOrganizationId = (Integer)organizationForm.getItem("id").getValue();

    stationaryCombustionForm.setItems(fuelSourceDescription, fuelTypeItem, fuelQuantityItem, fuelUnitItem, fuelUsedBeginDateItem, fuelUsedEndDateItem);
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
            stationaryCombustionForm.clearValues();
            stationaryCombustionForm.markForRedraw();
            stationaryCombustionFormWindow.hide();
        }
      }
    });

    stationaryCombustionCancelButton.setTitle("CANCEL");
    stationaryCombustionCancelButton.setTooltip("Cancel");
    stationaryCombustionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        //stationaryCombustionForm.saveData();
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

private void mobileCombustionTab() {
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

//--ListGrid setup
        mobileCombustionDataGrid.setWidth100();
        //mobileCombustionDataGrid.setHeight(200);
        mobileCombustionDataGrid.setHeight100();
        mobileCombustionDataGrid.setShowRecordComponents(true);
        mobileCombustionDataGrid.setShowRecordComponentsByCell(true);
        //mobileCombustionDataGrid.setCanRemoveRecords(true);
        //mobileCombustionDataGrid.setShowAllRecords(true);
        mobileCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        mobileCombustionDataGrid.setDataSource(mobileCombustionInfoDS);
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

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);

        ListGridField vehicleYearField = new ListGridField("vehicleYear", "Vehicle Year");
        vehicleYearField.setType(ListGridFieldType.TEXT);

        ListGridField fuelTypeField = new ListGridField("fuelType", "Fuel Type");
        fuelTypeField.setType(ListGridFieldType.TEXT);

        ListGridField fuelQuantityField = new ListGridField("fuelQuantity", "Fuel Quantity");
        fuelQuantityField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUnitField = new ListGridField("fuelUnit", "Fuel Unit");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField milesTravelledField = new ListGridField("milesTravelled", "Miles Travelled");
        milesTravelledField.setType(ListGridFieldType.FLOAT);

        ListGridField bioFuelPercentField = new ListGridField("bioFuelPercent", "Biofuel Percent");
        bioFuelPercentField.setType(ListGridFieldType.FLOAT);

        ListGridField ethanolPercentField = new ListGridField("ethanolPercent", "Ethanol Percent");
        ethanolPercentField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        mobileCombustionDataGrid.setFields(sourceDescriptionField, vehicleTypeField,vehicleYearField, organizationIdField, fuelTypeField, fuelQuantityField, fuelUnitField, milesTravelledField, bioFuelPercentField, ethanolPercentField,fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);
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
private void initMobileCombustionEditForm() {

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

    vehicleTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            mobileCombustionForm.clearValue("vehicleYear");
            mobileCombustionForm.clearValue("fuelType");            
            //mobileCombustionForm.clearValue("fuelUnit");
            Record selectedFuelTypeRecord = vehicleTypeItem.getSelectedRecord();
            mobileCombustionForm.setValue("fuelUnit", selectedFuelTypeRecord.getAttributeAsString("fuelUnit"));
        }
    });
   // final SelectItem vehicleYearItem = new SelectItem();
 
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

/*
    fuelTypeItem.addChangedHandler(new ChangedHandler() {
        public void onChanged(ChangedEvent event) {
            mobileCombustionForm.clearValue("fuelUnit");
        }
    });
*/
    /*
    final SelectItem fuelUnitItem = new SelectItem() {
        @Override
        protected Criteria getPickListFilterCriteria() {
            String vehicleType = (String) vehicleTypeItem.getValue();
            Criteria criteria = new Criteria("vehicleType", vehicleType);
            return criteria;
        }
    };
     *
     */
    
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

    FloatItem bioFuelPercentItem = new FloatItem();
    bioFuelPercentItem.setName("bioFuelPercent");
    bioFuelPercentItem.setValidators(floatValidator);
    bioFuelPercentItem.setValidateOnExit(Boolean.TRUE);
    bioFuelPercentItem.setValidateOnChange(Boolean.TRUE);

    FloatItem ethanolPercentItem = new FloatItem();
    ethanolPercentItem.setName("ethanolPercent");
    ethanolPercentItem.setValidators(floatValidator);
    ethanolPercentItem.setValidateOnExit(Boolean.TRUE);
    ethanolPercentItem.setValidateOnChange(Boolean.TRUE);

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");
    fuelUsedBeginDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedBeginDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedBeginDateItem.setValidators(validateDateRange);
    fuelUsedBeginDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedBeginDateItem.setRequired(Boolean.TRUE);

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");
    fuelUsedEndDateItem.setStartDate(currentInventoryBeginDateMin);
    fuelUsedEndDateItem.setEndDate(currentInventoryEndDateMax);
    fuelUsedEndDateItem.setValidators(validateDateRange);
    fuelUsedEndDateItem.setValidateOnChange(Boolean.TRUE);
    fuelUsedEndDateItem.setRequired(Boolean.TRUE);

    //IntegerItem organizationId = new IntegerItem();
    //organizationId.setName("organizationId");

    mobileCombustionForm.setItems(fuelSourceDescriptionItem ,vehicleTypeItem, vehicleYearItem, fuelTypeItem, fuelQuantityItem, fuelUnitItem, milesTravelledItem, bioFuelPercentItem,ethanolPercentItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton mobileCombustionCancelButton = new IButton();
    final IButton mobileCombustionSaveButton = new IButton();

    mobileCombustionSaveButton.setTitle("SAVE");
    mobileCombustionSaveButton.setTooltip("Save this Mobile Combustion Source");
    mobileCombustionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record mobileCombustionFormRecord = mobileCombustionForm.getValuesAsRecord();
        Integer organizationIdValue = (Integer)UserOrganizationHeader.organizationSelectForm.getItem("id").getValue();
        mobileCombustionFormRecord.setAttribute("organizationId", organizationIdValue);
        mobileCombustionDataGrid.updateData(mobileCombustionFormRecord);
        mobileCombustionForm.clearValues();
        mobileCombustionForm.markForRedraw();
        mobileCombustionFormWindow.hide();
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
 }

private void emissionsSummaryTab() {

        emissionsSummaryVLayout.setWidth100();
        emissionsSummaryVLayout.setHeight100();
        
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

        //final VLayout emissionsSummaryVLayout = new VLayout();
        Label emissionSummaryDataLabel = new Label("Current Emissions Summary");
        emissionSummaryDataLabel.setHeight(20);
        emissionsSummaryVLayout.addMember(emissionSummaryDataLabel);

        emissionsSummaryDataGrid.setWidth100();
        emissionsSummaryDataGrid.setHeight100();
        emissionsSummaryDataGrid.setAutoFetchData(Boolean.FALSE);
        emissionsSummaryDataGrid.setDataSource(emissionsSummaryDS);

        ListGridField organizationIdField2 = new ListGridField("organizationId", "Organization Id");
        organizationIdField2.setType(ListGridFieldType.INTEGER);

        DetailViewerField directEmissionsField = new DetailViewerField("directEmissions", "Direct Emissions");
        //directEmissionsField.
        //DetailViewerField.setType(ListGridFieldType.FLOAT);

        DetailViewerField stationaryCombustionEmissionsField = new DetailViewerField("stationaryCombustionEmissions", "Stationary Combustion Emissions");
        //stationaryCombustionEmissionsField.setType(DetailViewerField.FLOAT);

        DetailViewerField mobileCombustionEmissionsField = new DetailViewerField("mobileCombustionEmissions", "Mobile Source Emissions");
        //mobileCombustionEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField refridgerationAirConditioningEmissionsField = new DetailViewerField("refridgerationAirConditioningEmissions", "Refridgeration And Ac Emissions");
        //refridgerationAirConditioningEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField fireSuppressantEmissionsField = new DetailViewerField("fireSuppressantEmissions", "Fire Suppressant Emissions");
        //fireSuppressantEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField wasteStreamCombustionEmissionsField = new DetailViewerField("wasteStreamCombustionEmissions", "WasteStream Combustion Emissions");
        //wasteStreamCombustionEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField  purchasedElectricityEmissionsField = new DetailViewerField("purchasedElectricityEmissions", "Purchased Electricity Emissions");
        //purchasedElectricityEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField purchasedSteamEmissionsField = new DetailViewerField("purchasedSteamEmissions", "Purchased Steam Emissions");
       //purchasedSteamEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField employeeBusinessTravelByVehicleEmissionsField = new DetailViewerField("employeeBusinessTravelByVehicleEmissions", "Employee Business Travel By Vehicle Emissions");
        //employeeBusinessTravelByVehicleEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField employeeBusinessTravelByRailEmissionsField = new DetailViewerField("employeeBusinessTravelByRailEmissions", "Employee Business Travel By Rail Emissions");
        //employeeBusinessTravelByRailEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField employeeBusinessTravelByBusEmissionsField = new DetailViewerField("employeeBusinessTravelByBusEmissions", "Employee Business Travel By Bus Emissions");
        //employeeBusinessTravelByBusEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField employeeBusinessTravelByAirEmissionsField = new DetailViewerField("employeeBusinessTravelByAirEmissions", "Employee Business Travel By Air Emissions");
        //employeeBusinessTravelByAirEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField employeeCommutingByVehicleEmissionsField = new DetailViewerField("employeeCommutingByVehicleEmissions", "Employee Commuting By Vehicle Emissions");
        //employeeCommutingByVehicleEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField employeeCommutingByBusEmissionsField = new DetailViewerField("employeeCommutingByBusEmissions", "Employee Commuting By Bus Emissions");
        //employeeCommutingByBusEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField productTransportByVehicleEmissionsField = new DetailViewerField("productTransportByVehicleEmissions", "Product Transport By Vehicle Emissions");
        //productTransportByVehicleEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField productTransportByHeavyDutyTrucksEmissionsField = new DetailViewerField("productTransportByHeavyDutyTrucksEmissions", "Product Transport By Heavy Duty Trucks Emissions");
        //productTransportByHeavyDutyTrucksEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField productTransportByRailEmissionsField = new DetailViewerField("productTransportByRailEmissions", "Product Transport By Rail Emissions");
        //productTransportByRailEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField productTransportByWaterAirEmissionsField = new DetailViewerField("productTransportByWaterAirEmissions", "Product Transport By Water/Air Emissions");
        //productTransportByWaterAirEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField biomassStationaryCombustionEmissionsField = new DetailViewerField("biomassStationaryCombustionEmissions", "Biomass Stationary Combustion Emissions");
        //biomassStationaryCombustionEmissionsField.setType(ListGridFieldType.FLOAT);

        DetailViewerField biomassMobileCombustionEmissionsField = new DetailViewerField("biomassMobileCombustionEmissions", "Biomass Mobile Source Emissions");
        //biomassMobileCombustionEmissionsField.setType(ListGridFieldType.FLOAT);

        ListGridField totalEmissionsField = new ListGridField("totalEmissions", "Total Emissions");
        totalEmissionsField.setType(ListGridFieldType.FLOAT);

        ListGridField programTypeField = new ListGridField("programType", "Program Type");
        programTypeField.setType(ListGridFieldType.TEXT);

        ListGridField emissionsBeginDateField = new ListGridField("emissionsBeginDate", "Emissions Begin Date");
        emissionsBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField emissionsEndDateField = new ListGridField("emissionsEndDate", "Emissions End Date");
        emissionsEndDateField.setType(ListGridFieldType.DATE);
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
        emissionsSummaryDataGrid.setFields(programTypeField, emissionsBeginDateField, emissionsEndDateField,totalEmissionsField);
        //emissionsSummaryDataGrid.setOverflow(Overflow.VISIBLE);
        //emissionsSummaryDataGrid.setAutoWidth();

        emissionsSummaryVLayout.addMember(emissionsSummaryDataGrid);

        //emissionsSummaryDetailViewer.setFields(stationaryCombustionEmissionsField);
        
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

        emissionsSummaryDataGrid.addRecordClickHandler(new RecordClickHandler() {
            public void onRecordClick(RecordClickEvent event) {
                //emissionsSummaryDetailViewer.reset();
                //--remove existing child from middleMiddleHLayout
                
                Canvas[] children2 = detailViewerVLayout.getChildren();
                if (children2.length>0){
                    for (int i=0; i < children2.length; i++){
                        detailViewerVLayout.removeChild(children2[i]);
                    }
                }                

                emissionsSummaryDetailViewer.viewSelectedData(emissionsSummaryDataGrid);
                detailViewerVLayout.addMember(emissionsSummaryDetailViewerLabel);
                detailViewerVLayout.addMember(emissionsSummaryDetailViewer);
                middleBottomHLayout.addMember(detailViewerVLayout);
                //middleBottomHLayout.addMember(panel);
                middleBottomHLayout.show();

            }
        });

        final Tab emissionsSummaryTab = new Tab("Emissions Summary");
        emissionsSummaryTab.setPane(emissionsSummaryVLayout);

//---Adding Mobile Combustion tab to tabSet
        tabSet.addTab(emissionsSummaryTab);


 }

private void refridgerationAirConditioningTab() {

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

//-- Material Balance fields
        ListGridField inventoryChangeField = new ListGridField("inventoryChange", "Inventory Change");
        inventoryChangeField.setType(ListGridFieldType.FLOAT);

        ListGridField transferredAmountField = new ListGridField("transferredAmount", "Transferred Amount");
        transferredAmountField.setType(ListGridFieldType.FLOAT);

        ListGridField capacityChangeField = new ListGridField("capacityChange", "Capacity Change");
        capacityChangeField.setType(ListGridFieldType.FLOAT);

//--Simplified Material Balance fields
        ListGridField newUnitsChargeField = new ListGridField("newUnitsCharge", "New Units Charge");
        newUnitsChargeField.setType(ListGridFieldType.FLOAT);

        ListGridField newUnitsCapacityField = new ListGridField("newUnitsCapacity", "New Units Capacity");
        newUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField existingUnitsRechargeField = new ListGridField("existingUnitsRecharge", "Existing Units Recharge");
        existingUnitsRechargeField.setType(ListGridFieldType.FLOAT);

        ListGridField disposedUnitsCapacityField = new ListGridField("disposedUnitsCapacity", "Disposed Units Capacity");
        disposedUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField disposedUnitsRecoveredField = new ListGridField("disposedUnitsRecovered", "Disposed Units Recovered");
        disposedUnitsRecoveredField.setType(ListGridFieldType.FLOAT);

//--Screening Method fields
        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField typeOfEquipmentField = new ListGridField("typeOfEquipment", "Type  Of Equipment");
        typeOfEquipmentField.setType(ListGridFieldType.TEXT);

        ListGridField sourceNewUnitsChargeField = new ListGridField("sourceNewUnitsCharge", "Source New Units Charge");
        sourceNewUnitsChargeField.setType(ListGridFieldType.FLOAT);

        ListGridField operatingUnitsCapacityField = new ListGridField("operatingUnitsCapacity", "Operating Units Capacity");
        operatingUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField sourceDisposedUnitsCapacityField = new ListGridField("sourceDisposedUnitsCapacity", "Source Disposed Units Capacity");
        sourceDisposedUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField timeInYearsUsedField = new ListGridField("timeInYearsUsed", "Time In Years Used");
        timeInYearsUsedField.setType(ListGridFieldType.FLOAT);

        ListGridField methodTypeField = new ListGridField("methodType", "Method Type");
        methodTypeField.setType(ListGridFieldType.TEXT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

//-- Material Balance Method
        refridgerationAirConditioningDataGrid_1.setFields(methodTypeField, organizationIdField, gasTypeField,inventoryChangeField, transferredAmountField, capacityChangeField, fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

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
                refridgerationAirConditioningForm_1.editNewRecord();
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
        refridgerationAirConditioningDataGrid_2.setFields(methodTypeField, organizationIdField, gasTypeField,newUnitsChargeField, newUnitsCapacityField, inventoryChangeField, existingUnitsRechargeField, disposedUnitsCapacityField, disposedUnitsRecoveredField,  fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

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
                refridgerationAirConditioningForm_2.editNewRecord();
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
        refridgerationAirConditioningDataGrid_3.setFields(methodTypeField, organizationIdField, gasTypeField,sourceDescriptionField, typeOfEquipmentField, sourceNewUnitsChargeField,operatingUnitsCapacityField, sourceDisposedUnitsCapacityField, timeInYearsUsedField, fuelUsedBeginDateField, fuelUsedEndDateField, editButtonField, removeButtonField);

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
                refridgerationAirConditioningForm_3.editNewRecord();
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
    refridgerationAirConditioningSaveButton.setTooltip("Save this Mobile Combustion Source");
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

private void fireSuppressionTab() {

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
        ListGridField inventoryChangeField = new ListGridField("inventoryChange", "Inventory Change");
        inventoryChangeField.setType(ListGridFieldType.FLOAT);

        ListGridField transferredAmountField = new ListGridField("transferredAmount", "Transferred Amount");
        transferredAmountField.setType(ListGridFieldType.FLOAT);

        ListGridField capacityChangeField = new ListGridField("capacityChange", "Capacity Change");
        capacityChangeField.setType(ListGridFieldType.FLOAT);

//--Simplified Material Balance fields
        ListGridField newUnitsChargeField = new ListGridField("newUnitsCharge", "New Units Charge");
        newUnitsChargeField.setType(ListGridFieldType.FLOAT);

        ListGridField newUnitsCapacityField = new ListGridField("newUnitsCapacity", "New Units Capacity");
        newUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField existingUnitsRechargeField = new ListGridField("existingUnitsRecharge", "Existing Units Recharge");
        existingUnitsRechargeField.setType(ListGridFieldType.FLOAT);

        ListGridField disposedUnitsCapacityField = new ListGridField("disposedUnitsCapacity", "Disposed Units Capacity");
        disposedUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField disposedUnitsRecoveredField = new ListGridField("disposedUnitsRecovered", "Disposed Units Recovered");
        disposedUnitsRecoveredField.setType(ListGridFieldType.FLOAT);

//--Screening Method fields
        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField typeOfEquipmentField = new ListGridField("typeOfEquipment", "Type  Of Equipment");
        typeOfEquipmentField.setType(ListGridFieldType.TEXT);

        ListGridField sourceNewUnitsChargeField = new ListGridField("sourceNewUnitsCharge", "Source New Units Charge");
        sourceNewUnitsChargeField.setType(ListGridFieldType.FLOAT);

        ListGridField operatingUnitsCapacityField = new ListGridField("operatingUnitsCapacity", "Operating Units Capacity");
        operatingUnitsCapacityField.setType(ListGridFieldType.FLOAT);

        ListGridField methodTypeField = new ListGridField("methodType", "Method Type");
        methodTypeField.setType(ListGridFieldType.TEXT);

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
                fireSuppressionForm_1.editNewRecord();
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
                fireSuppressionForm_2.editNewRecord();
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
                fireSuppressionForm_3.editNewRecord();
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

private void purchasedElectricityTab() {

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

        ListGridField purchasedElectricityField = new ListGridField("purchasedElectricity", "Purchased Electricity");
        purchasedElectricityField.setType(ListGridFieldType.FLOAT);

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

        purchasedElectricityDataGrid.setFields(organizationIdField, sourceDescriptionField, eGRIDSubregionField, purchasedElectricityUnitField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //purchasedElectricityDetailsLayout.addMember(purchasedElectricityDataGrid);

        IButton newPurchasedElectricityButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newPurchasedElectricityButton.setWidth(ADD_BUTTON_WIDTH);
        newPurchasedElectricityButton.setIcon(ADD_ICON_IMAGE);

        newPurchasedElectricityButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                purchasedElectricityForm.editNewRecord();
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
private void initPurchasedElectricityEditForm() {

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

private void purchasedSteamTab() {

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

        ListGridField boilerEfficiencyPercentField = new ListGridField("boilerEfficiencyPercent", "boilerEfficiency Percent");
        boilerEfficiencyPercentField.setType(ListGridFieldType.FLOAT);
        boilerEfficiencyPercentField.setDefaultValue(80);
        //boilerEfficiencyPercentField.setEdit(Boolean.FALSE);

        ListGridField purchasedSteamField = new ListGridField("purchasedSteam", "Purchased Steam");
        purchasedSteamField.setType(ListGridFieldType.FLOAT);

        ListGridField purchasedSteamUnitField = new ListGridField("purchasedSteamUnit", "Purchased Steam Unit");
        purchasedSteamUnitField.setType(ListGridFieldType.TEXT);
        purchasedSteamUnitField.setDefaultValue("mmBtu");

        ListGridField supplierCO2MultiplierField = new ListGridField("supplierCO2Multiplier", "Supplier CO2 Emission Factor");
        supplierCO2MultiplierField.setType(ListGridFieldType.FLOAT);

        ListGridField supplierCO2MultiplierUnitField = new ListGridField("supplierCO2MultiplierUnit", "Supplier CO2 Emission Factor Unit");
        supplierCO2MultiplierUnitField.setType(ListGridFieldType.TEXT);
        supplierCO2MultiplierUnitField.setDefaultValue("lb/mmBtu");

        ListGridField supplierCH4MultiplierField = new ListGridField("supplierCH4Multiplier", "Supplier CH4 Emission Factor");
        supplierCH4MultiplierField.setType(ListGridFieldType.FLOAT);

        ListGridField supplierCH4MultiplierUnitField = new ListGridField("supplierCH4MultiplierUnit", "Supplier CH4 Emission Factor Unit");
        supplierCH4MultiplierUnitField.setType(ListGridFieldType.TEXT);
        supplierCH4MultiplierUnitField.setDefaultValue("lb/mmBtu");

        ListGridField supplierN2OMultiplierField = new ListGridField("supplierN2OMultiplier", "Supplier N2O Emission Factor");
        supplierN2OMultiplierField.setType(ListGridFieldType.FLOAT);

        ListGridField supplierN2OMultiplierUnitField = new ListGridField("supplierN2OMultiplierUnit", "Supplier N2O Emission Factor Unit");
        supplierN2OMultiplierUnitField.setType(ListGridFieldType.TEXT);
        supplierN2OMultiplierUnitField.setDefaultValue("lb/mmBtu");

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        purchasedSteamDataGrid.setFields(organizationIdField, sourceDescriptionField, fuelTypeField,boilerEfficiencyPercentField,
                                         purchasedSteamField,purchasedSteamUnitField,supplierCO2MultiplierField,
                                         supplierCO2MultiplierUnitField,supplierCH4MultiplierField,supplierCH4MultiplierUnitField,
                                         supplierN2OMultiplierField,supplierN2OMultiplierUnitField, fuelUsedBeginDateField,
                                         fuelUsedEndDateField ,editButtonField, removeButtonField);

        //purchasedSteamDetailsLayout.addMember(purchasedSteamDataGrid);

        IButton newPurchasedSteamButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newPurchasedSteamButton.setWidth(ADD_BUTTON_WIDTH);
        newPurchasedSteamButton.setIcon(ADD_ICON_IMAGE);

        newPurchasedSteamButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                purchasedSteamForm.editNewRecord();
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
private void initPurchasedSteamEditForm() {

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

//--employeeBusinessTravelByVehicle
private void employeeBusinessTravelByVehicleTab() {

        VLayout employeeBusinessTravelByVehicleLayout = new VLayout(15);

        Label employeeBusinessTravelByVehicleDataLabel = new Label("Current Sources of Employee Business Travel By Vehicle");
        employeeBusinessTravelByVehicleDataLabel.setHeight(LABEL_HEIGHT);
        employeeBusinessTravelByVehicleDataLabel.setWidth100();
        employeeBusinessTravelByVehicleDataLabel.setAlign(Alignment.LEFT);
        employeeBusinessTravelByVehicleDataLabel.setStyleName("labels");
        //employeeBusinessTravelByVehicleLayout.addMember(employeeBusinessTravelByVehicleDataLabel);

//--ListGrid setup
        employeeBusinessTravelByVehicleDataGrid.setWidth100();
        //employeeBusinessTravelByVehicleDataGrid.setHeight(200);
        employeeBusinessTravelByVehicleDataGrid.setHeight100();
        employeeBusinessTravelByVehicleDataGrid.setShowRecordComponents(true);
        employeeBusinessTravelByVehicleDataGrid.setShowRecordComponentsByCell(true);
        //employeeBusinessTravelByVehicleDataGrid.setCanRemoveRecords(true);
        //employeeBusinessTravelByVehicleDataGrid.setShowAllRecords(true);
        employeeBusinessTravelByVehicleDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeBusinessTravelByVehicleDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        employeeBusinessTravelByVehicleDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, vehicleTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeBusinessTravelByVehicleLayout.addMember(employeeBusinessTravelByVehicleDataGrid);

        IButton newEmployeeBusinessTravelByVehicleButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeBusinessTravelByVehicleButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeBusinessTravelByVehicleButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeBusinessTravelByVehicleButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeBusinessTravelByVehicleForm.editNewRecord();
                employeeBusinessTravelByVehicleFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(employeeBusinessTravelByVehicleDataLabel);
        gridButtonLayout.addMember(newEmployeeBusinessTravelByVehicleButton);
        employeeBusinessTravelByVehicleLayout.addMember(gridButtonLayout);
        employeeBusinessTravelByVehicleLayout.addMember(employeeBusinessTravelByVehicleDataGrid);

//--Defining employeeBusinessTravel By Vehicle
        final Tab employeeBusinessTravelByVehicleTab = new Tab("Employee Business Travel By Vehicle");
        employeeBusinessTravelByVehicleTab.setPane(employeeBusinessTravelByVehicleLayout);

//---Adding employeeBusinessTravel By Vehicle tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByVehicleTab);
            //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByVehicleTab);
}
private void initEmployeeBusinessTravelByVehicleEditForm() {

    employeeBusinessTravelByVehicleForm.setCellPadding(5);
    employeeBusinessTravelByVehicleForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    vehicleTypeItem.setTitle("vehicleType");
    vehicleTypeItem.setOptionDataSource(theEF_VehicleType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeBusinessTravelByVehicleForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,vehicleTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeBusinessTravelByVehicleCancelButton = new IButton();
    final IButton employeeBusinessTravelByVehicleSaveButton = new IButton();

    employeeBusinessTravelByVehicleSaveButton.setTitle("SAVE");
    employeeBusinessTravelByVehicleSaveButton.setTooltip("Save this Source");
    employeeBusinessTravelByVehicleSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeBusinessTravelByVehicleFormRecord = employeeBusinessTravelByVehicleForm.getValuesAsRecord();
        employeeBusinessTravelByVehicleDataGrid.updateData(employeeBusinessTravelByVehicleFormRecord);
        employeeBusinessTravelByVehicleFormWindow.hide();
      }
    });

    employeeBusinessTravelByVehicleCancelButton.setTitle("CANCEL");
    employeeBusinessTravelByVehicleCancelButton.setTooltip("Cancel");
    employeeBusinessTravelByVehicleCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeBusinessTravelByVehicleFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeBusinessTravelByVehicleCancelButton);
    buttons.addMember(employeeBusinessTravelByVehicleSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeBusinessTravelByVehicleForm);
    dialog.addMember(buttons);
    employeeBusinessTravelByVehicleFormWindow.setShowShadow(true);
    employeeBusinessTravelByVehicleFormWindow.setShowTitle(false);
    employeeBusinessTravelByVehicleFormWindow.setIsModal(true);
    employeeBusinessTravelByVehicleFormWindow.setPadding(20);
    employeeBusinessTravelByVehicleFormWindow.setWidth(500);
    employeeBusinessTravelByVehicleFormWindow.setHeight(350);
    employeeBusinessTravelByVehicleFormWindow.setShowMinimizeButton(false);
    employeeBusinessTravelByVehicleFormWindow.setShowCloseButton(true);
    employeeBusinessTravelByVehicleFormWindow.setShowModalMask(true);
    employeeBusinessTravelByVehicleFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeBusinessTravelByVehicleFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeBusinessTravelByVehicleFormWindow.addItem(dialog);
 }

//--employeeBusinessTravelByRail
private void employeeBusinessTravelByRailTab() {

        VLayout employeeBusinessTravelByRailLayout = new VLayout(15);

        Label employeeBusinessTravelByRailDataLabel = new Label("Current Sources of Employee Business Travel By Rail");
        employeeBusinessTravelByRailDataLabel.setHeight(LABEL_HEIGHT);
        employeeBusinessTravelByRailDataLabel.setWidth100();
        employeeBusinessTravelByRailDataLabel.setAlign(Alignment.LEFT);
        employeeBusinessTravelByRailDataLabel.setStyleName("labels");

        //employeeBusinessTravelByRailLayout.addMember(employeeBusinessTravelByRailDataLabel);

//--ListGrid setup
        employeeBusinessTravelByRailDataGrid.setWidth100();
        //employeeBusinessTravelByRailDataGrid.setHeight(200);
        employeeBusinessTravelByRailDataGrid.setHeight100();
        employeeBusinessTravelByRailDataGrid.setShowRecordComponents(true);
        employeeBusinessTravelByRailDataGrid.setShowRecordComponentsByCell(true);
        //employeeBusinessTravelByRailDataGrid.setCanRemoveRecords(true);
        //employeeBusinessTravelByRailDataGrid.setShowAllRecords(true);
        employeeBusinessTravelByRailDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeBusinessTravelByRailDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField railTypeField = new ListGridField("railType", "Rail Type");
        railTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        employeeBusinessTravelByRailDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, railTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeBusinessTravelByRailLayout.addMember(employeeBusinessTravelByRailDataGrid);

        IButton newEmployeeBusinessTravelByRailButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeBusinessTravelByRailButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeBusinessTravelByRailButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeBusinessTravelByRailButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeBusinessTravelByRailForm.editNewRecord();
                employeeBusinessTravelByRailFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(employeeBusinessTravelByRailDataLabel);
        gridButtonLayout.addMember(newEmployeeBusinessTravelByRailButton);
        employeeBusinessTravelByRailLayout.addMember(gridButtonLayout);
        employeeBusinessTravelByRailLayout.addMember(employeeBusinessTravelByRailDataGrid);
        
//--Defining employeeBusinessTravel By Rail
        final Tab employeeBusinessTravelByRailTab = new Tab("Employee Business Travel By Rail");
        employeeBusinessTravelByRailTab.setPane(employeeBusinessTravelByRailLayout);

//---Adding employeeBusinessTravel By Rail tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByRailTab);
        //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByRailTab);
}
private void initEmployeeBusinessTravelByRailEditForm() {

    employeeBusinessTravelByRailForm.setCellPadding(5);
    employeeBusinessTravelByRailForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem railTypeItem = new SelectItem();
    railTypeItem.setName("railType");
    railTypeItem.setTitle("railType");
    railTypeItem.setOptionDataSource(theEF_RailType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeBusinessTravelByRailForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,railTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeBusinessTravelByRailCancelButton = new IButton();
    final IButton employeeBusinessTravelByRailSaveButton = new IButton();

    employeeBusinessTravelByRailSaveButton.setTitle("SAVE");
    employeeBusinessTravelByRailSaveButton.setTooltip("Save this Source");
    employeeBusinessTravelByRailSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeBusinessTravelByRailFormRecord = employeeBusinessTravelByRailForm.getValuesAsRecord();
        employeeBusinessTravelByRailDataGrid.updateData(employeeBusinessTravelByRailFormRecord);
        employeeBusinessTravelByRailFormWindow.hide();
      }
    });

    employeeBusinessTravelByRailCancelButton.setTitle("CANCEL");
    employeeBusinessTravelByRailCancelButton.setTooltip("Cancel");
    employeeBusinessTravelByRailCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeBusinessTravelByRailFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeBusinessTravelByRailCancelButton);
    buttons.addMember(employeeBusinessTravelByRailSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeBusinessTravelByRailForm);
    dialog.addMember(buttons);
    employeeBusinessTravelByRailFormWindow.setShowShadow(true);
    employeeBusinessTravelByRailFormWindow.setShowTitle(false);
    employeeBusinessTravelByRailFormWindow.setIsModal(true);
    employeeBusinessTravelByRailFormWindow.setPadding(20);
    employeeBusinessTravelByRailFormWindow.setWidth(500);
    employeeBusinessTravelByRailFormWindow.setHeight(350);
    employeeBusinessTravelByRailFormWindow.setShowMinimizeButton(false);
    employeeBusinessTravelByRailFormWindow.setShowCloseButton(true);
    employeeBusinessTravelByRailFormWindow.setShowModalMask(true);
    employeeBusinessTravelByRailFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeBusinessTravelByRailFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeBusinessTravelByRailFormWindow.addItem(dialog);
 }

//--employeeBusinessTravelByBus
private void employeeBusinessTravelByBusTab() {

        VLayout employeeBusinessTravelByBusLayout = new VLayout(15);

        Label employeeBusinessTravelByBusDataLabel = new Label("Current Sources of Employee Business Travel By Bus");
        employeeBusinessTravelByBusDataLabel.setHeight(LABEL_HEIGHT);
        employeeBusinessTravelByBusDataLabel.setWidth100();
        employeeBusinessTravelByBusDataLabel.setAlign(Alignment.LEFT);
        employeeBusinessTravelByBusDataLabel.setStyleName("labels");

        //employeeBusinessTravelByBusLayout.addMember(employeeBusinessTravelByBusDataLabel);

//--ListGrid setup
        employeeBusinessTravelByBusDataGrid.setWidth100();
        //employeeBusinessTravelByBusDataGrid.setHeight(200);
        employeeBusinessTravelByBusDataGrid.setHeight100();
        employeeBusinessTravelByBusDataGrid.setShowRecordComponents(true);
        employeeBusinessTravelByBusDataGrid.setShowRecordComponentsByCell(true);
        //employeeBusinessTravelByBusDataGrid.setCanRemoveRecords(true);
        //employeeBusinessTravelByBusDataGrid.setShowAllRecords(true);
        employeeBusinessTravelByBusDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeBusinessTravelByBusDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField busTypeField = new ListGridField("busType", "Bus Type");
        busTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        employeeBusinessTravelByBusDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, busTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeBusinessTravelByBusLayout.addMember(employeeBusinessTravelByBusDataGrid);

        IButton newEmployeeBusinessTravelByBusButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeBusinessTravelByBusButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeBusinessTravelByBusButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeBusinessTravelByBusButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeBusinessTravelByBusForm.editNewRecord();
                employeeBusinessTravelByBusFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);


        gridButtonLayout.addMember(employeeBusinessTravelByBusDataLabel);
        gridButtonLayout.addMember(newEmployeeBusinessTravelByBusButton);
        employeeBusinessTravelByBusLayout.addMember(gridButtonLayout);
        employeeBusinessTravelByBusLayout.addMember(employeeBusinessTravelByBusDataGrid);

//--Defining employeeBusinessTravel By Bus
        final Tab employeeBusinessTravelByBusTab = new Tab("Employee Business Travel By Bus");
        employeeBusinessTravelByBusTab.setPane(employeeBusinessTravelByBusLayout);

//---Adding employeeBusinessTravel By Bus tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByBusTab);
        //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByBusTab);
}
private void initEmployeeBusinessTravelByBusEditForm() {

    employeeBusinessTravelByBusForm.setCellPadding(5);
    employeeBusinessTravelByBusForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem busTypeItem = new SelectItem();
    busTypeItem.setName("busType");
    busTypeItem.setTitle("busType");
    busTypeItem.setOptionDataSource(theEF_BusType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeBusinessTravelByBusForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,busTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeBusinessTravelByBusCancelButton = new IButton();
    final IButton employeeBusinessTravelByBusSaveButton = new IButton();

    employeeBusinessTravelByBusSaveButton.setTitle("SAVE");
    employeeBusinessTravelByBusSaveButton.setTooltip("Save this Source");
    employeeBusinessTravelByBusSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeBusinessTravelByBusFormRecord = employeeBusinessTravelByBusForm.getValuesAsRecord();
        employeeBusinessTravelByBusDataGrid.updateData(employeeBusinessTravelByBusFormRecord);
        employeeBusinessTravelByBusFormWindow.hide();
      }
    });

    employeeBusinessTravelByBusCancelButton.setTitle("CANCEL");
    employeeBusinessTravelByBusCancelButton.setTooltip("Cancel");
    employeeBusinessTravelByBusCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeBusinessTravelByBusFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeBusinessTravelByBusCancelButton);
    buttons.addMember(employeeBusinessTravelByBusSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeBusinessTravelByBusForm);
    dialog.addMember(buttons);
    employeeBusinessTravelByBusFormWindow.setShowShadow(true);
    employeeBusinessTravelByBusFormWindow.setShowTitle(false);
    employeeBusinessTravelByBusFormWindow.setIsModal(true);
    employeeBusinessTravelByBusFormWindow.setPadding(20);
    employeeBusinessTravelByBusFormWindow.setWidth(500);
    employeeBusinessTravelByBusFormWindow.setHeight(350);
    employeeBusinessTravelByBusFormWindow.setShowMinimizeButton(false);
    employeeBusinessTravelByBusFormWindow.setShowCloseButton(true);
    employeeBusinessTravelByBusFormWindow.setShowModalMask(true);
    employeeBusinessTravelByBusFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeBusinessTravelByBusFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeBusinessTravelByBusFormWindow.addItem(dialog);
 }

//--employeeBusinessTravelByAir
private void employeeBusinessTravelByAirTab() {

        VLayout employeeBusinessTravelByAirLayout = new VLayout(15);

        Label employeeBusinessTravelByAirDataLabel = new Label("Current Sources of Employee Business Travel By Air");
        employeeBusinessTravelByAirDataLabel.setHeight(LABEL_HEIGHT);
        employeeBusinessTravelByAirDataLabel.setWidth100();
        employeeBusinessTravelByAirDataLabel.setAlign(Alignment.LEFT);
        employeeBusinessTravelByAirDataLabel.setStyleName("labels");

        //employeeBusinessTravelByAirLayout.addMember(employeeBusinessTravelByAirDataLabel);

//--ListGrid setup
        employeeBusinessTravelByAirDataGrid.setWidth100();
        //employeeBusinessTravelByAirDataGrid.setHeight(200);
        employeeBusinessTravelByAirDataGrid.setHeight100();
        employeeBusinessTravelByAirDataGrid.setShowRecordComponents(true);
        employeeBusinessTravelByAirDataGrid.setShowRecordComponentsByCell(true);
        //employeeBusinessTravelByAirDataGrid.setCanRemoveRecords(true);
        //employeeBusinessTravelByAirDataGrid.setShowAllRecords(true);
        employeeBusinessTravelByAirDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeBusinessTravelByAirDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        //optionalSourceTypeField.setDefaultValue("Employee Business Travel - By Air");
        //optionalSourceTypeField.Edit(Boolean.FALSE);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField airTravelTypeField = new ListGridField("airTravelType", "Air Travel Type");
        airTravelTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        employeeBusinessTravelByAirDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, airTravelTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeBusinessTravelByAirLayout.addMember(employeeBusinessTravelByAirDataGrid);

        IButton newEmployeeBusinessTravelByAirButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeBusinessTravelByAirButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeBusinessTravelByAirButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeBusinessTravelByAirButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeBusinessTravelByAirForm.editNewRecord();
                employeeBusinessTravelByAirFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(employeeBusinessTravelByAirDataLabel);
        gridButtonLayout.addMember(newEmployeeBusinessTravelByAirButton);
        employeeBusinessTravelByAirLayout.addMember(gridButtonLayout);
        employeeBusinessTravelByAirLayout.addMember(employeeBusinessTravelByAirDataGrid);

//--Defining employeeBusinessTravel By Air
        final Tab employeeBusinessTravelByAirTab = new Tab("Employee Business Travel By Air");
        employeeBusinessTravelByAirTab.setPane(employeeBusinessTravelByAirLayout);

//---Adding employeeBusinessTravel By Air tab to tabSet
        employeeBusinessTravelTabSet.addTab(employeeBusinessTravelByAirTab);
        //tabSet.addTab(employeeBusinessTravelTabSet);
        //tabSet.addTab(employeeBusinessTravelByAirTab);
}
private void initEmployeeBusinessTravelByAirEditForm() {

    employeeBusinessTravelByAirForm.setCellPadding(5);
    employeeBusinessTravelByAirForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem airTravelTypeItem = new SelectItem();
    airTravelTypeItem.setName("airTravelType");
    airTravelTypeItem.setTitle("airTravelType");
    airTravelTypeItem.setOptionDataSource(theEF_AirTravelType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeBusinessTravelByAirForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,airTravelTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeBusinessTravelByAirCancelButton = new IButton();
    final IButton employeeBusinessTravelByAirSaveButton = new IButton();

    employeeBusinessTravelByAirSaveButton.setTitle("SAVE");
    employeeBusinessTravelByAirSaveButton.setTooltip("Save this Source");
    employeeBusinessTravelByAirSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeBusinessTravelByAirFormRecord = employeeBusinessTravelByAirForm.getValuesAsRecord();
        employeeBusinessTravelByAirDataGrid.updateData(employeeBusinessTravelByAirFormRecord);
        employeeBusinessTravelByAirFormWindow.hide();
      }
    });

    employeeBusinessTravelByAirCancelButton.setTitle("CANCEL");
    employeeBusinessTravelByAirCancelButton.setTooltip("Cancel");
    employeeBusinessTravelByAirCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeBusinessTravelByAirFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeBusinessTravelByAirCancelButton);
    buttons.addMember(employeeBusinessTravelByAirSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeBusinessTravelByAirForm);
    dialog.addMember(buttons);
    employeeBusinessTravelByAirFormWindow.setShowShadow(true);
    employeeBusinessTravelByAirFormWindow.setShowTitle(false);
    employeeBusinessTravelByAirFormWindow.setIsModal(true);
    employeeBusinessTravelByAirFormWindow.setPadding(20);
    employeeBusinessTravelByAirFormWindow.setWidth(500);
    employeeBusinessTravelByAirFormWindow.setHeight(350);
    employeeBusinessTravelByAirFormWindow.setShowMinimizeButton(false);
    employeeBusinessTravelByAirFormWindow.setShowCloseButton(true);
    employeeBusinessTravelByAirFormWindow.setShowModalMask(true);
    employeeBusinessTravelByAirFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeBusinessTravelByAirFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeBusinessTravelByAirFormWindow.addItem(dialog);
 }

//--employeeCommutingByVehicle
private void employeeCommutingByVehicleTab() {

        VLayout employeeCommutingByVehicleLayout = new VLayout(15);

        Label employeeCommutingByVehicleDataLabel = new Label("Current Sources of Employee Commuting By Vehicle");
        employeeCommutingByVehicleDataLabel.setHeight(LABEL_HEIGHT);
        employeeCommutingByVehicleDataLabel.setWidth100();
        employeeCommutingByVehicleDataLabel.setAlign(Alignment.LEFT);
        employeeCommutingByVehicleDataLabel.setStyleName("labels");

        //employeeCommutingByVehicleLayout.addMember(employeeCommutingByVehicleDataLabel);

//--ListGrid setup
        employeeCommutingByVehicleDataGrid.setWidth100();
        //employeeCommutingByVehicleDataGrid.setHeight(200);
        employeeCommutingByVehicleDataGrid.setHeight100();
        employeeCommutingByVehicleDataGrid.setShowRecordComponents(true);
        employeeCommutingByVehicleDataGrid.setShowRecordComponentsByCell(true);
        //employeeCommutingByVehicleDataGrid.setCanRemoveRecords(true);
        //employeeCommutingByVehicleDataGrid.setShowAllRecords(true);
        employeeCommutingByVehicleDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeCommutingByVehicleDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);
        employeeCommutingByVehicleDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, vehicleTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeCommutingByVehicleLayout.addMember(employeeCommutingByVehicleDataGrid);

        IButton newEmployeeCommutingByVehicleButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeCommutingByVehicleButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeCommutingByVehicleButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeCommutingByVehicleButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeCommutingByVehicleForm.editNewRecord();
                employeeCommutingByVehicleFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(employeeCommutingByVehicleDataLabel);
        gridButtonLayout.addMember(newEmployeeCommutingByVehicleButton);
        employeeCommutingByVehicleLayout.addMember(gridButtonLayout);
        employeeCommutingByVehicleLayout.addMember(employeeCommutingByVehicleDataGrid);
//--Defining employeeCommutingByVehicle
        final Tab employeeCommutingByVehicleTab = new Tab("Employee Commuting By Vehicle");
        employeeCommutingByVehicleTab.setPane(employeeCommutingByVehicleLayout);

//---Adding employeeCommutingByVehicle tab to tabSet
        employeeCommutingTabSet.addTab(employeeCommutingByVehicleTab);
        //tabSet.addTab(employeeCommutingTabSet);
        //tabSet.addTab(employeeCommutingByVehicleTab);
}
private void initEmployeeCommutingByVehicleEditForm() {

    employeeCommutingByVehicleForm.setCellPadding(5);
    employeeCommutingByVehicleForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    vehicleTypeItem.setTitle("vehicleType");
    vehicleTypeItem.setOptionDataSource(theEF_VehicleType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeCommutingByVehicleForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,vehicleTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeCommutingByVehicleCancelButton = new IButton();
    final IButton employeeCommutingByVehicleSaveButton = new IButton();

    employeeCommutingByVehicleSaveButton.setTitle("SAVE");
    employeeCommutingByVehicleSaveButton.setTooltip("Save this Source");
    employeeCommutingByVehicleSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeCommutingByVehicleFormRecord = employeeCommutingByVehicleForm.getValuesAsRecord();
        employeeCommutingByVehicleDataGrid.updateData(employeeCommutingByVehicleFormRecord);
        employeeCommutingByVehicleFormWindow.hide();
      }
    });

    employeeCommutingByVehicleCancelButton.setTitle("CANCEL");
    employeeCommutingByVehicleCancelButton.setTooltip("Cancel");
    employeeCommutingByVehicleCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeCommutingByVehicleFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeCommutingByVehicleCancelButton);
    buttons.addMember(employeeCommutingByVehicleSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeCommutingByVehicleForm);
    dialog.addMember(buttons);
    employeeCommutingByVehicleFormWindow.setShowShadow(true);
    employeeCommutingByVehicleFormWindow.setShowTitle(false);
    employeeCommutingByVehicleFormWindow.setIsModal(true);
    employeeCommutingByVehicleFormWindow.setPadding(20);
    employeeCommutingByVehicleFormWindow.setWidth(500);
    employeeCommutingByVehicleFormWindow.setHeight(350);
    employeeCommutingByVehicleFormWindow.setShowMinimizeButton(false);
    employeeCommutingByVehicleFormWindow.setShowCloseButton(true);
    employeeCommutingByVehicleFormWindow.setShowModalMask(true);
    employeeCommutingByVehicleFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeCommutingByVehicleFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeCommutingByVehicleFormWindow.addItem(dialog);
 }

//--employeeCommutingByRail
private void employeeCommutingByRailTab() {

        VLayout employeeCommutingByRailLayout = new VLayout(15);

        Label employeeCommutingByRailDataLabel = new Label("Current Sources of Employee Commuting By Rail");
        employeeCommutingByRailDataLabel.setHeight(LABEL_HEIGHT);
        employeeCommutingByRailDataLabel.setWidth100();
        employeeCommutingByRailDataLabel.setAlign(Alignment.LEFT);
        employeeCommutingByRailDataLabel.setStyleName("labels");

        //employeeCommutingByRailLayout.addMember(employeeCommutingByRailDataLabel);

//--ListGrid setup
        employeeCommutingByRailDataGrid.setWidth100();
        //employeeCommutingByRailDataGrid.setHeight(200);
        employeeCommutingByRailDataGrid.setHeight100();
        employeeCommutingByRailDataGrid.setShowRecordComponents(true);
        employeeCommutingByRailDataGrid.setShowRecordComponentsByCell(true);
        //employeeCommutingByRailDataGrid.setCanRemoveRecords(true);
        //employeeCommutingByRailDataGrid.setShowAllRecords(true);
        employeeCommutingByRailDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeCommutingByRailDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField railTypeField = new ListGridField("railType", "Rail Type");
        railTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        employeeCommutingByRailDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, railTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeCommutingByRailLayout.addMember(employeeCommutingByRailDataGrid);

        IButton newEmployeeCommutingByRailButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeCommutingByRailButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeCommutingByRailButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeCommutingByRailButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeCommutingByRailForm.editNewRecord();
                employeeCommutingByRailFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(employeeCommutingByRailDataLabel);
        gridButtonLayout.addMember(newEmployeeCommutingByRailButton);
        employeeCommutingByRailLayout.addMember(gridButtonLayout);
        employeeCommutingByRailLayout.addMember(employeeCommutingByRailDataGrid);

//--Defining employeeCommutingByRail
        final Tab employeeCommutingByRailTab = new Tab("Employee Commuting By Rail");
        employeeCommutingByRailTab.setPane(employeeCommutingByRailLayout);

//---Adding employeeCommutingByRail tab to tabSet
       employeeCommutingTabSet.addTab(employeeCommutingByRailTab);
        //tabSet.addTab(employeeCommutingTabSet);
        //tabSet.addTab(employeeCommutingByRailTab);
}
private void initEmployeeCommutingByRailEditForm() {

    employeeCommutingByRailForm.setCellPadding(5);
    employeeCommutingByRailForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem railTypeItem = new SelectItem();
    railTypeItem.setName("railType");
    railTypeItem.setTitle("Rail Type");
    railTypeItem.setOptionDataSource(theEF_RailType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeCommutingByRailForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,railTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeCommutingByRailCancelButton = new IButton();
    final IButton employeeCommutingByRailSaveButton = new IButton();

    employeeCommutingByRailSaveButton.setTitle("SAVE");
    employeeCommutingByRailSaveButton.setTooltip("Save this Source");
    employeeCommutingByRailSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeCommutingByRailFormRecord = employeeCommutingByRailForm.getValuesAsRecord();
        employeeCommutingByRailDataGrid.updateData(employeeCommutingByRailFormRecord);
        employeeCommutingByRailFormWindow.hide();
      }
    });

    employeeCommutingByRailCancelButton.setTitle("CANCEL");
    employeeCommutingByRailCancelButton.setTooltip("Cancel");
    employeeCommutingByRailCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeCommutingByRailFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeCommutingByRailCancelButton);
    buttons.addMember(employeeCommutingByRailSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeCommutingByRailForm);
    dialog.addMember(buttons);
    employeeCommutingByRailFormWindow.setShowShadow(true);
    employeeCommutingByRailFormWindow.setShowTitle(false);
    employeeCommutingByRailFormWindow.setIsModal(true);
    employeeCommutingByRailFormWindow.setPadding(20);
    employeeCommutingByRailFormWindow.setWidth(500);
    employeeCommutingByRailFormWindow.setHeight(350);
    employeeCommutingByRailFormWindow.setShowMinimizeButton(false);
    employeeCommutingByRailFormWindow.setShowCloseButton(true);
    employeeCommutingByRailFormWindow.setShowModalMask(true);
    employeeCommutingByRailFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeCommutingByRailFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeCommutingByRailFormWindow.addItem(dialog);
 }

//--employeeCommutingByBus
private void employeeCommutingByBusTab() {

        VLayout employeeCommutingByBusLayout = new VLayout(15);

        Label employeeCommutingByBusDataLabel = new Label("Current Sources of Employee Commuting By Bus");
        employeeCommutingByBusDataLabel.setHeight(LABEL_HEIGHT);
        employeeCommutingByBusDataLabel.setWidth100();
        employeeCommutingByBusDataLabel.setAlign(Alignment.LEFT);
        employeeCommutingByBusDataLabel.setStyleName("labels");

        //employeeCommutingByBusLayout.addMember(employeeCommutingByBusDataLabel);

//--ListGrid setup
        employeeCommutingByBusDataGrid.setWidth100();
        //employeeCommutingByBusDataGrid.setHeight(200);
        employeeCommutingByBusDataGrid.setHeight100();
        employeeCommutingByBusDataGrid.setShowRecordComponents(true);
        employeeCommutingByBusDataGrid.setShowRecordComponentsByCell(true);
        //employeeCommutingByBusDataGrid.setCanRemoveRecords(true);
        //employeeCommutingByBusDataGrid.setShowAllRecords(true);
        employeeCommutingByBusDataGrid.setAutoFetchData(Boolean.FALSE);
        employeeCommutingByBusDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField busTypeField = new ListGridField("busType", "Bus Type");
        busTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        employeeCommutingByBusDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, busTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //employeeCommutingByBusLayout.addMember(employeeCommutingByBusDataGrid);

        IButton newEmployeeCommutingByBusButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newEmployeeCommutingByBusButton.setWidth(ADD_BUTTON_WIDTH);
        newEmployeeCommutingByBusButton.setIcon(ADD_ICON_IMAGE);

        newEmployeeCommutingByBusButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                employeeCommutingByBusForm.editNewRecord();
                employeeCommutingByBusFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(employeeCommutingByBusDataLabel);
        gridButtonLayout.addMember(newEmployeeCommutingByBusButton);
        employeeCommutingByBusLayout.addMember(gridButtonLayout);
        employeeCommutingByBusLayout.addMember(employeeCommutingByBusDataGrid);

//--Defining employeeCommutingByBus
        final Tab employeeCommutingByBusTab = new Tab("Employee Commuting By Bus");
        employeeCommutingByBusTab.setPane(employeeCommutingByBusLayout);

//---Adding employeeCommutingByBus tab to tabSet
        employeeCommutingTabSet.addTab(employeeCommutingByBusTab);
        //tabSet.addTab(employeeCommutingTabSet);
        //tabSet.addTab(employeeCommutingByBusTab);
}
private void initEmployeeCommutingByBusEditForm() {

    employeeCommutingByBusForm.setCellPadding(5);
    employeeCommutingByBusForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem busTypeItem = new SelectItem();
    busTypeItem.setName("busType");
    busTypeItem.setTitle("busType");
    busTypeItem.setOptionDataSource(theEF_BusType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    employeeCommutingByBusForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,busTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton employeeCommutingByBusCancelButton = new IButton();
    final IButton employeeCommutingByBusSaveButton = new IButton();

    employeeCommutingByBusSaveButton.setTitle("SAVE");
    employeeCommutingByBusSaveButton.setTooltip("Save this Source");
    employeeCommutingByBusSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record employeeCommutingByBusFormRecord = employeeCommutingByBusForm.getValuesAsRecord();
        employeeCommutingByBusDataGrid.updateData(employeeCommutingByBusFormRecord);
        employeeCommutingByBusFormWindow.hide();
      }
    });

    employeeCommutingByBusCancelButton.setTitle("CANCEL");
    employeeCommutingByBusCancelButton.setTooltip("Cancel");
    employeeCommutingByBusCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        employeeCommutingByBusFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(employeeCommutingByBusCancelButton);
    buttons.addMember(employeeCommutingByBusSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(employeeCommutingByBusForm);
    dialog.addMember(buttons);
    employeeCommutingByBusFormWindow.setShowShadow(true);
    employeeCommutingByBusFormWindow.setShowTitle(false);
    employeeCommutingByBusFormWindow.setIsModal(true);
    employeeCommutingByBusFormWindow.setPadding(20);
    employeeCommutingByBusFormWindow.setWidth(500);
    employeeCommutingByBusFormWindow.setHeight(350);
    employeeCommutingByBusFormWindow.setShowMinimizeButton(false);
    employeeCommutingByBusFormWindow.setShowCloseButton(true);
    employeeCommutingByBusFormWindow.setShowModalMask(true);
    employeeCommutingByBusFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //employeeCommutingByBusFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    employeeCommutingByBusFormWindow.addItem(dialog);
 }

//--productTransportByVehicle
private void productTransportByVehicleTab() {

        VLayout productTransportByVehicleLayout = new VLayout(15);

        Label productTransportByVehicleDataLabel = new Label("Current Sources of Product Transport - By Vehicle");
        productTransportByVehicleDataLabel.setHeight(LABEL_HEIGHT);
        productTransportByVehicleDataLabel.setWidth100();
        productTransportByVehicleDataLabel.setAlign(Alignment.LEFT);
        productTransportByVehicleDataLabel.setStyleName("labels");

        //productTransportByVehicleLayout.addMember(productTransportByVehicleDataLabel);

//--ListGrid setup
        productTransportByVehicleDataGrid.setWidth100();
        //productTransportByVehicleDataGrid.setHeight(200);
        productTransportByVehicleDataGrid.setHeight100();
        productTransportByVehicleDataGrid.setShowRecordComponents(true);
        productTransportByVehicleDataGrid.setShowRecordComponentsByCell(true);
        //productTransportByVehicleDataGrid.setCanRemoveRecords(true);
        //productTransportByVehicleDataGrid.setShowAllRecords(true);
        productTransportByVehicleDataGrid.setAutoFetchData(Boolean.FALSE);
        productTransportByVehicleDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField vehicleTypeField = new ListGridField("vehicleType", "Vehicle Type");
        vehicleTypeField.setType(ListGridFieldType.TEXT);

        ListGridField passengerMilesField = new ListGridField("passengerMiles", "Passenger Miles");
        passengerMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        productTransportByVehicleDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, vehicleTypeField,passengerMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //productTransportByVehicleLayout.addMember(productTransportByVehicleDataGrid);

        IButton newProductTransportByVehicleButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newProductTransportByVehicleButton.setWidth(ADD_BUTTON_WIDTH);
        newProductTransportByVehicleButton.setIcon(ADD_ICON_IMAGE);

        newProductTransportByVehicleButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                productTransportByVehicleForm.editNewRecord();
                productTransportByVehicleFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(productTransportByVehicleDataLabel);
        gridButtonLayout.addMember(newProductTransportByVehicleButton);
        productTransportByVehicleLayout.addMember(gridButtonLayout);
        productTransportByVehicleLayout.addMember(productTransportByVehicleDataGrid);

//--Defining productTransportByVehicle
        final Tab productTransportByVehicleTab = new Tab("Product Transport - By Vehicle");
        productTransportByVehicleTab.setPane(productTransportByVehicleLayout);

//---Adding productTransportByVehicle tab to tabSet
        productTransportTabSet.addTab(productTransportByVehicleTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByVehicleTab);
}
private void initProductTransportByVehicleEditForm() {

    productTransportByVehicleForm.setCellPadding(5);
    productTransportByVehicleForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem vehicleTypeItem = new SelectItem();
    vehicleTypeItem.setName("vehicleType");
    vehicleTypeItem.setTitle("vehicleType");
    vehicleTypeItem.setOptionDataSource(theEF_ProductTransport_VehicleType_EPADS);

    FloatItem passengerMilesItem = new FloatItem();
    passengerMilesItem.setName("passengerMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    productTransportByVehicleForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,vehicleTypeItem, passengerMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton productTransportByVehicleCancelButton = new IButton();
    final IButton productTransportByVehicleSaveButton = new IButton();

    productTransportByVehicleSaveButton.setTitle("SAVE");
    productTransportByVehicleSaveButton.setTooltip("Save this Source");
    productTransportByVehicleSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record productTransportByVehicleFormRecord = productTransportByVehicleForm.getValuesAsRecord();
        productTransportByVehicleDataGrid.updateData(productTransportByVehicleFormRecord);
        productTransportByVehicleFormWindow.hide();
      }
    });

    productTransportByVehicleCancelButton.setTitle("CANCEL");
    productTransportByVehicleCancelButton.setTooltip("Cancel");
    productTransportByVehicleCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        productTransportByVehicleFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(productTransportByVehicleCancelButton);
    buttons.addMember(productTransportByVehicleSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(productTransportByVehicleForm);
    dialog.addMember(buttons);
    productTransportByVehicleFormWindow.setShowShadow(true);
    productTransportByVehicleFormWindow.setShowTitle(false);
    productTransportByVehicleFormWindow.setIsModal(true);
    productTransportByVehicleFormWindow.setPadding(20);
    productTransportByVehicleFormWindow.setWidth(500);
    productTransportByVehicleFormWindow.setHeight(350);
    productTransportByVehicleFormWindow.setShowMinimizeButton(false);
    productTransportByVehicleFormWindow.setShowCloseButton(true);
    productTransportByVehicleFormWindow.setShowModalMask(true);
    productTransportByVehicleFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //productTransportByVehicleFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    productTransportByVehicleFormWindow.addItem(dialog);
 }

//--productTransportByHeavyDutyTrucks
private void productTransportByHeavyDutyTrucksTab() {

        VLayout productTransportByHeavyDutyTrucksLayout = new VLayout(15);

        Label productTransportByHeavyDutyTrucksDataLabel = new Label("Current Sources of Product Transport - By Heavy Duty Trucks");
        productTransportByHeavyDutyTrucksDataLabel.setHeight(LABEL_HEIGHT);
        productTransportByHeavyDutyTrucksDataLabel.setWidth100();
        productTransportByHeavyDutyTrucksDataLabel.setAlign(Alignment.LEFT);
        productTransportByHeavyDutyTrucksDataLabel.setStyleName("labels");
        //productTransportByHeavyDutyTrucksLayout.addMember(productTransportByHeavyDutyTrucksDataLabel);

//--ListGrid setup
        productTransportByHeavyDutyTrucksDataGrid.setWidth100();
        //productTransportByHeavyDutyTrucksDataGrid.setHeight(200);
        productTransportByHeavyDutyTrucksDataGrid.setHeight100();
        productTransportByHeavyDutyTrucksDataGrid.setShowRecordComponents(true);
        productTransportByHeavyDutyTrucksDataGrid.setShowRecordComponentsByCell(true);
        //productTransportByHeavyDutyTrucksDataGrid.setCanRemoveRecords(true);
        //productTransportByHeavyDutyTrucksDataGrid.setShowAllRecords(true);
        productTransportByHeavyDutyTrucksDataGrid.setAutoFetchData(Boolean.FALSE);
        productTransportByHeavyDutyTrucksDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField transportTypeField = new ListGridField("transportType", "Transport Type");
        transportTypeField.setType(ListGridFieldType.TEXT);

        ListGridField tonMilesField = new ListGridField("tonMiles", "Ton Miles");
        tonMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        productTransportByHeavyDutyTrucksDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, transportTypeField,tonMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //productTransportByHeavyDutyTrucksLayout.addMember(productTransportByHeavyDutyTrucksDataGrid);

        IButton newProductTransportByHeavyDutyTrucksButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newProductTransportByHeavyDutyTrucksButton.setWidth(ADD_BUTTON_WIDTH);
        newProductTransportByHeavyDutyTrucksButton.setIcon(ADD_ICON_IMAGE);

        newProductTransportByHeavyDutyTrucksButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                productTransportByHeavyDutyTrucksForm.editNewRecord();
                productTransportByHeavyDutyTrucksFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(productTransportByHeavyDutyTrucksDataLabel);
        gridButtonLayout.addMember(newProductTransportByHeavyDutyTrucksButton);
        productTransportByHeavyDutyTrucksLayout.addMember(gridButtonLayout);
        productTransportByHeavyDutyTrucksLayout.addMember(productTransportByHeavyDutyTrucksDataGrid);

//--Defining productTransportByHeavyDutyTrucks
        final Tab productTransportByHeavyDutyTrucksTab = new Tab("Product Transport - By Heavy Duty Trucks");
        productTransportByHeavyDutyTrucksTab.setPane(productTransportByHeavyDutyTrucksLayout);

//---Adding productTransportByHeavyDutyTrucks tab to tabSet
        productTransportTabSet.addTab(productTransportByHeavyDutyTrucksTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByHeavyDutyTrucksTab);
}
private void initProductTransportByHeavyDutyTrucksEditForm() {

    productTransportByHeavyDutyTrucksForm.setCellPadding(5);
    productTransportByHeavyDutyTrucksForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem transportTypeItem = new SelectItem();
    transportTypeItem.setName("transportType");
    transportTypeItem.setTitle("transportType");
    transportTypeItem.setOptionDataSource(theEF_ProductTransportType_EPADS);

    FloatItem tonMilesItem = new FloatItem();
    tonMilesItem.setName("tonMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    productTransportByHeavyDutyTrucksForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,transportTypeItem, tonMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton productTransportByHeavyDutyTrucksCancelButton = new IButton();
    final IButton productTransportByHeavyDutyTrucksSaveButton = new IButton();

    productTransportByHeavyDutyTrucksSaveButton.setTitle("SAVE");
    productTransportByHeavyDutyTrucksSaveButton.setTooltip("Save this Source");
    productTransportByHeavyDutyTrucksSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record productTransportByHeavyDutyTrucksFormRecord = productTransportByHeavyDutyTrucksForm.getValuesAsRecord();
        productTransportByHeavyDutyTrucksDataGrid.updateData(productTransportByHeavyDutyTrucksFormRecord);
        productTransportByHeavyDutyTrucksFormWindow.hide();
      }
    });

    productTransportByHeavyDutyTrucksCancelButton.setTitle("CANCEL");
    productTransportByHeavyDutyTrucksCancelButton.setTooltip("Cancel");
    productTransportByHeavyDutyTrucksCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        productTransportByHeavyDutyTrucksFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(productTransportByHeavyDutyTrucksCancelButton);
    buttons.addMember(productTransportByHeavyDutyTrucksSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(productTransportByHeavyDutyTrucksForm);
    dialog.addMember(buttons);
    productTransportByHeavyDutyTrucksFormWindow.setShowShadow(true);
    productTransportByHeavyDutyTrucksFormWindow.setShowTitle(false);
    productTransportByHeavyDutyTrucksFormWindow.setIsModal(true);
    productTransportByHeavyDutyTrucksFormWindow.setPadding(20);
    productTransportByHeavyDutyTrucksFormWindow.setWidth(500);
    productTransportByHeavyDutyTrucksFormWindow.setHeight(350);
    productTransportByHeavyDutyTrucksFormWindow.setShowMinimizeButton(false);
    productTransportByHeavyDutyTrucksFormWindow.setShowCloseButton(true);
    productTransportByHeavyDutyTrucksFormWindow.setShowModalMask(true);
    productTransportByHeavyDutyTrucksFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //productTransportByHeavyDutyTrucksFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    productTransportByHeavyDutyTrucksFormWindow.addItem(dialog);
 }

//--productTransportByRail
private void productTransportByRailTab() {

        VLayout productTransportByRailLayout = new VLayout(15);

        Label productTransportByRailDataLabel = new Label("Current Sources of Product Transport - By Rail");
        productTransportByRailDataLabel.setHeight(LABEL_HEIGHT);
        productTransportByRailDataLabel.setWidth100();
        productTransportByRailDataLabel.setAlign(Alignment.LEFT);
        productTransportByRailDataLabel.setStyleName("labels");

        //productTransportByRailLayout.addMember(productTransportByRailDataLabel);

//--ListGrid setup
        productTransportByRailDataGrid.setWidth100();
        //productTransportByRailDataGrid.setHeight(200);
        productTransportByRailDataGrid.setHeight100();
        productTransportByRailDataGrid.setShowRecordComponents(true);
        productTransportByRailDataGrid.setShowRecordComponentsByCell(true);
        //productTransportByRailDataGrid.setCanRemoveRecords(true);
        //productTransportByRailDataGrid.setShowAllRecords(true);
        productTransportByRailDataGrid.setAutoFetchData(Boolean.FALSE);
        productTransportByRailDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField transportTypeField = new ListGridField("transportType", "Transport Type");
        transportTypeField.setType(ListGridFieldType.TEXT);

        ListGridField tonMilesField = new ListGridField("tonMiles", "Ton Miles");
        tonMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        productTransportByRailDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, transportTypeField,tonMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //productTransportByRailLayout.addMember(productTransportByRailDataGrid);

        IButton newProductTransportByRailButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newProductTransportByRailButton.setWidth(ADD_BUTTON_WIDTH);
        newProductTransportByRailButton.setIcon(ADD_ICON_IMAGE);

        newProductTransportByRailButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                productTransportByRailForm.editNewRecord();
                productTransportByRailFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(productTransportByRailDataLabel);
        gridButtonLayout.addMember(newProductTransportByRailButton);
        productTransportByRailLayout.addMember(gridButtonLayout);
        productTransportByRailLayout.addMember(productTransportByRailDataGrid);


//--Defining productTransportByRail
        final Tab productTransportByRailTab = new Tab("Product Transport - By Rail");
        productTransportByRailTab.setPane(productTransportByRailLayout);

//---Adding productTransportByRail tab to tabSet
        productTransportTabSet.addTab(productTransportByRailTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByRailTab);
}
private void initProductTransportByRailEditForm() {

    productTransportByRailForm.setCellPadding(5);
    productTransportByRailForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem transportTypeItem = new SelectItem();
    transportTypeItem.setName("transportType");
    transportTypeItem.setTitle("transportType");
    transportTypeItem.setOptionDataSource(theEF_ProductTransportType_EPADS);

    FloatItem tonMilesItem = new FloatItem();
    tonMilesItem.setName("tonMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    productTransportByRailForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,transportTypeItem, tonMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton productTransportByRailCancelButton = new IButton();
    final IButton productTransportByRailSaveButton = new IButton();

    productTransportByRailSaveButton.setTitle("SAVE");
    productTransportByRailSaveButton.setTooltip("Save this Source");
    productTransportByRailSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record productTransportByRailFormRecord = productTransportByRailForm.getValuesAsRecord();
        productTransportByRailDataGrid.updateData(productTransportByRailFormRecord);
        productTransportByRailFormWindow.hide();
      }
    });

    productTransportByRailCancelButton.setTitle("CANCEL");
    productTransportByRailCancelButton.setTooltip("Cancel");
    productTransportByRailCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        productTransportByRailFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(productTransportByRailCancelButton);
    buttons.addMember(productTransportByRailSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(productTransportByRailForm);
    dialog.addMember(buttons);
    productTransportByRailFormWindow.setShowShadow(true);
    productTransportByRailFormWindow.setShowTitle(false);
    productTransportByRailFormWindow.setIsModal(true);
    productTransportByRailFormWindow.setPadding(20);
    productTransportByRailFormWindow.setWidth(500);
    productTransportByRailFormWindow.setHeight(350);
    productTransportByRailFormWindow.setShowMinimizeButton(false);
    productTransportByRailFormWindow.setShowCloseButton(true);
    productTransportByRailFormWindow.setShowModalMask(true);
    productTransportByRailFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //productTransportByRailFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    productTransportByRailFormWindow.addItem(dialog);
 }

//--productTransportByWaterAir
private void productTransportByWaterAirTab() {

        VLayout productTransportByWaterAirLayout = new VLayout(15);

        Label productTransportByWaterAirDataLabel = new Label("Current Sources of Product Transport - By Water or Air");
        productTransportByWaterAirDataLabel.setHeight(LABEL_HEIGHT);
        productTransportByWaterAirDataLabel.setWidth100();
        productTransportByWaterAirDataLabel.setAlign(Alignment.LEFT);
        productTransportByWaterAirDataLabel.setStyleName("labels");

        //productTransportByWaterAirLayout.addMember(productTransportByWaterAirDataLabel);

//--ListGrid setup
        productTransportByWaterAirDataGrid.setWidth100();
        //productTransportByWaterAirDataGrid.setHeight(200);
        productTransportByWaterAirDataGrid.setHeight100();
        productTransportByWaterAirDataGrid.setShowRecordComponents(true);
        productTransportByWaterAirDataGrid.setShowRecordComponentsByCell(true);
        //productTransportByWaterAirDataGrid.setCanRemoveRecords(true);
        //productTransportByWaterAirDataGrid.setShowAllRecords(true);
        productTransportByWaterAirDataGrid.setAutoFetchData(Boolean.FALSE);
        productTransportByWaterAirDataGrid.setDataSource(optionalSourceInfoDS);


        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField optionalSourceTypeField = new ListGridField("optionalSourceType", "Optional Source Type");
        optionalSourceTypeField.setType(ListGridFieldType.TEXT);
        optionalSourceTypeField.setHidden(true);

        ListGridField sourceDescriptionField = new ListGridField("sourceDescription", "Source Description");
        sourceDescriptionField.setType(ListGridFieldType.TEXT);

        ListGridField transportTypeField = new ListGridField("transportType", "Transport Type");
        transportTypeField.setType(ListGridFieldType.TEXT);

        ListGridField tonMilesField = new ListGridField("tonMiles", "Ton Miles");
        tonMilesField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        productTransportByWaterAirDataGrid.setFields(organizationIdField,optionalSourceTypeField, sourceDescriptionField, transportTypeField,tonMilesField, fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //productTransportByWaterAirLayout.addMember(productTransportByWaterAirDataGrid);

        IButton newProductTransportByWaterAirButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newProductTransportByWaterAirButton.setWidth(ADD_BUTTON_WIDTH);
        newProductTransportByWaterAirButton.setIcon(ADD_ICON_IMAGE);

        newProductTransportByWaterAirButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                productTransportByWaterAirForm.editNewRecord();
                productTransportByWaterAirFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);

        gridButtonLayout.addMember(productTransportByWaterAirDataLabel);
        gridButtonLayout.addMember(newProductTransportByWaterAirButton);
        productTransportByWaterAirLayout.addMember(gridButtonLayout);
        productTransportByWaterAirLayout.addMember(productTransportByWaterAirDataGrid);

//--Defining productTransportByWaterAir
        final Tab productTransportByWaterAirTab = new Tab("Product Transport - By Water or Air");
        productTransportByWaterAirTab.setPane(productTransportByWaterAirLayout);

//---Adding productTransportByWaterAir tab to tabSet
        productTransportTabSet.addTab(productTransportByWaterAirTab);
        //tabSet.addTab(productTransportTabSet);
        //tabSet.addTab(productTransportByWaterAirTab);
}
private void initProductTransportByWaterAirEditForm() {

    productTransportByWaterAirForm.setCellPadding(5);
    productTransportByWaterAirForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem optionalSourceTypeItem = new TextItem("optionalSourceType");
    optionalSourceTypeItem.setTitle("Optional Source Type");
    optionalSourceTypeItem.setSelectOnFocus(true);
    optionalSourceTypeItem.setWrapTitle(false);
    //optionalSourceTypeItem.setDefaultValue("Source");

    TextItem sourceDescriptionItem = new TextItem("sourceDescription");
    sourceDescriptionItem.setTitle("Source Description");
    sourceDescriptionItem.setSelectOnFocus(true);
    sourceDescriptionItem.setWrapTitle(false);
    sourceDescriptionItem.setDefaultValue("Source");

    final SelectItem transportTypeItem = new SelectItem();
    transportTypeItem.setName("transportType");
    transportTypeItem.setTitle("transportType");
    transportTypeItem.setOptionDataSource(theEF_ProductTransportType_EPADS);

    FloatItem tonMilesItem = new FloatItem();
    tonMilesItem.setName("tonMiles");

    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    productTransportByWaterAirForm.setItems(organizationId,optionalSourceTypeItem, sourceDescriptionItem ,transportTypeItem, tonMilesItem, fuelUsedBeginDateItem,fuelUsedEndDateItem);

    final IButton productTransportByWaterAirCancelButton = new IButton();
    final IButton productTransportByWaterAirSaveButton = new IButton();

    productTransportByWaterAirSaveButton.setTitle("SAVE");
    productTransportByWaterAirSaveButton.setTooltip("Save this Source");
    productTransportByWaterAirSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record productTransportByWaterAirFormRecord = productTransportByWaterAirForm.getValuesAsRecord();
        productTransportByWaterAirDataGrid.updateData(productTransportByWaterAirFormRecord);
        productTransportByWaterAirFormWindow.hide();
      }
    });

    productTransportByWaterAirCancelButton.setTitle("CANCEL");
    productTransportByWaterAirCancelButton.setTooltip("Cancel");
    productTransportByWaterAirCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        productTransportByWaterAirFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(productTransportByWaterAirCancelButton);
    buttons.addMember(productTransportByWaterAirSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(productTransportByWaterAirForm);
    dialog.addMember(buttons);
    productTransportByWaterAirFormWindow.setShowShadow(true);
    productTransportByWaterAirFormWindow.setShowTitle(false);
    productTransportByWaterAirFormWindow.setIsModal(true);
    productTransportByWaterAirFormWindow.setPadding(20);
    productTransportByWaterAirFormWindow.setWidth(500);
    productTransportByWaterAirFormWindow.setHeight(350);
    productTransportByWaterAirFormWindow.setShowMinimizeButton(false);
    productTransportByWaterAirFormWindow.setShowCloseButton(true);
    productTransportByWaterAirFormWindow.setShowModalMask(true);
    productTransportByWaterAirFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //productTransportByWaterAirFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    productTransportByWaterAirFormWindow.addItem(dialog);
 }

//--wasteStreamCombustion
private void wasteStreamCombustionTab() {

        wasteStreamCombustionLayout.setWidth100();
        wasteStreamCombustionLayout.setHeight100();

        VLayout wasteStreamCombustionDetailsLayout = new VLayout(15);

        Label wasteStreamCombustionDataLabel = new Label("Current Sources of Product Waste Stream Combustion");
        wasteStreamCombustionDataLabel.setHeight(LABEL_HEIGHT);
        wasteStreamCombustionDataLabel.setWidth100();
        wasteStreamCombustionDataLabel.setAlign(Alignment.LEFT);
        wasteStreamCombustionDataLabel.setStyleName("labels");

        //wasteStreamCombustionDetailsLayout.addMember(wasteStreamCombustionDataLabel);

//--ListGrid setup
        wasteStreamCombustionDataGrid.setWidth100();
        //wasteStreamCombustionDataGrid.setHeight(200);
        wasteStreamCombustionDataGrid.setHeight100();
        wasteStreamCombustionDataGrid.setShowRecordComponents(true);
        wasteStreamCombustionDataGrid.setShowRecordComponentsByCell(true);
        //wasteStreamCombustionDataGrid.setCanRemoveRecords(true);
        //wasteStreamCombustionDataGrid.setShowAllRecords(true);
        wasteStreamCombustionDataGrid.setAutoFetchData(Boolean.FALSE);
        wasteStreamCombustionDataGrid.setDataSource(wasteStreamCombustionInfoDS);

        ListGridField organizationIdField = new ListGridField("organizationId", "Organization Id");
        organizationIdField.setType(ListGridFieldType.INTEGER);
        organizationIdField.setHidden(true);

        ListGridField fuelSourceDescriptionField = new ListGridField("fuelSourceDescription", "Fuel Source Description");
        fuelSourceDescriptionField.setType(ListGridFieldType.TEXT);

	ListGridField amountOfWasterStreamGasCombustedField =
	new ListGridField("amountOfWasterStreamGasCombusted", "Amount Of Waster Stream Gas Combusted");
	amountOfWasterStreamGasCombustedField.setType(ListGridFieldType.FLOAT);

	ListGridField amountOfWasterStreamGasCombustedUnitField =
	new ListGridField("amountOfWasterStreamGasCombustedUnit", "Amount Of Waster Stream Gas Combusted Unit");
	amountOfWasterStreamGasCombustedUnitField.setType(ListGridFieldType.TEXT);

	ListGridField totalNumberOfMolesPerUnitVolumentField =
	new ListGridField("totalNumberOfMolesPerUnitVolument", "Total Number Of Moles Per Unit Volument");
	totalNumberOfMolesPerUnitVolumentField.setType(ListGridFieldType.FLOAT);

	ListGridField totalNumberOfMolesPerUnitVolumentUnitField =
	new ListGridField("totalNumberOfMolesPerUnitVolumentUnit", "Amount Of Waster Stream Gas Combusted Unit");
	totalNumberOfMolesPerUnitVolumentUnitField.setType(ListGridFieldType.TEXT);

	ListGridField carbonMonoxideMolarFractionPercentField =
	new ListGridField("carbonMonoxideMolarFractionPercent", "Carbon Monoxide Molar Fraction Percent");
	carbonMonoxideMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField carbonDioxideMolarFractionPercentField =
	new ListGridField("carbonDioxideMolarFractionPercent", "Carbon Dioxide Molar Fraction Percent");
	carbonDioxideMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField methaneMolarFractionPercentField =
	new ListGridField("methaneMolarFractionPercent", "methaneMolarFractionPercent");
	methaneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField cetyleneMolarFractionPercentField =
	new ListGridField("cetyleneMolarFractionPercent", "Cetylene Molar Fraction Percent");
	cetyleneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField ethyleneMolarFractionPercentField =
	new ListGridField("ethyleneMolarFractionPercent", "Ethylene Molar Fraction Percent");
	ethyleneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField ethaneMolarFractionPercentField =
	new ListGridField("ethaneMolarFractionPercent", "Ethane Molar Fraction Percent");
	ethaneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField propyleneMolarFractionPercentField =
	new ListGridField("propyleneMolarFractionPercent", "Propylene Molar Fraction Percent");
	propyleneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);


	ListGridField propaneMolarFractionPercentField =
	new ListGridField("propaneMolarFractionPercent", "Propane Molar Fraction Percent");
	propaneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);


	ListGridField n_ButaneMolarFractionPercentField =
	new ListGridField("n_ButaneMolarFractionPercent", "n_Butane Molar Fraction Percent");
	n_ButaneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField benzeneMolarFractionPercentField =
	new ListGridField("benzeneMolarFractionPercent", "Benzene Molar Fraction Percent");
	benzeneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField bexaneMolarFractionPercentField =
	new ListGridField("bexaneMolarFractionPercent", "Bexane Molar Fraction Percent");
	bexaneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField tolueneMolarFractionPercentField =
	new ListGridField("tolueneMolarFractionPercent", "Toluene Molar Fraction Percent");
	tolueneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField octaneMolarFractionPercentField =
	new ListGridField("octaneMolarFractionPercent", "Octane Molar Fraction Percent");
	octaneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField ethanolMolarFractionPercentField =
	new ListGridField("ethanolMolarFractionPercent", "Ethanol Molar Fraction Percent");
	ethanolMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField acetoneMolarFractionPercentField =
	new ListGridField("acetoneMolarFractionPercent", "Acetone Molar Fraction Percent");
	acetoneMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField tetrahydrofuranMolarFractionPercentField =
	new ListGridField("tetrahydrofuranMolarFractionPercent", "Tetrahydrofuran Molar Fraction Percent");
	tetrahydrofuranMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField otherNon_CMolarFractionPercentField =
	new ListGridField("otherNon_CMolarFractionPercent", "Other Non_C Molar Fraction Percent");
	otherNon_CMolarFractionPercentField.setType(ListGridFieldType.FLOAT);

	ListGridField oxidationFactorPercentField =
	new ListGridField("oxidationFactorPercent", "Oxidation Factor Percent");
	oxidationFactorPercentField.setType(ListGridFieldType.FLOAT);

        ListGridField fuelUsedBeginDateField = new ListGridField("fuelUsedBeginDate", "Begin Date");
        fuelUsedBeginDateField.setType(ListGridFieldType.DATE);

        ListGridField fuelUsedEndDateField = new ListGridField("fuelUsedEndDate", "End Date");
        fuelUsedEndDateField.setType(ListGridFieldType.DATE);

        ListGridField editButtonField = new ListGridField("editButtonField", "Edit");
        editButtonField.setAlign(Alignment.CENTER);

        ListGridField removeButtonField = new ListGridField("removeButtonField", "Remove");
        //removeButtonField.setWidth(100);

        wasteStreamCombustionDataGrid.setFields(organizationIdField,fuelSourceDescriptionField,
						amountOfWasterStreamGasCombustedField,
						amountOfWasterStreamGasCombustedUnitField,
						totalNumberOfMolesPerUnitVolumentField,
						totalNumberOfMolesPerUnitVolumentUnitField,
						carbonMonoxideMolarFractionPercentField,
						carbonDioxideMolarFractionPercentField,
						methaneMolarFractionPercentField,
						cetyleneMolarFractionPercentField,
						ethyleneMolarFractionPercentField,
						ethaneMolarFractionPercentField,
						propyleneMolarFractionPercentField,
						propaneMolarFractionPercentField,
						n_ButaneMolarFractionPercentField,
						benzeneMolarFractionPercentField,
						bexaneMolarFractionPercentField,
						tolueneMolarFractionPercentField,
						octaneMolarFractionPercentField,
						ethanolMolarFractionPercentField,
						acetoneMolarFractionPercentField,
						tetrahydrofuranMolarFractionPercentField,
						otherNon_CMolarFractionPercentField,
                                                oxidationFactorPercentField,
        					fuelUsedBeginDateField, fuelUsedEndDateField ,editButtonField, removeButtonField);

        //wasteStreamCombustionDetailsLayout.addMember(wasteStreamCombustionDataGrid);

        IButton newWasteStreamCombustionButton = new IButton(ADD_NEW_SOURCE_TEXT);
        newWasteStreamCombustionButton.setWidth(ADD_BUTTON_WIDTH);
        newWasteStreamCombustionButton.setIcon(ADD_ICON_IMAGE);

        newWasteStreamCombustionButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                wasteStreamCombustionForm.editNewRecord();
                wasteStreamCombustionFormWindow.show();
            }
        });

        HLayout gridButtonLayout = new HLayout(10);
        gridButtonLayout.addMember(wasteStreamCombustionDataLabel);
        gridButtonLayout.addMember(newWasteStreamCombustionButton);
        wasteStreamCombustionDetailsLayout.addMember(gridButtonLayout);
        wasteStreamCombustionDetailsLayout.addMember(wasteStreamCombustionDataGrid);

//--Defining wasteStreamCombustion
        final Tab wasteStreamCombustionTab = new Tab("Waste Stream Combustion");
        wasteStreamCombustionTab.setPane(wasteStreamCombustionDetailsLayout);

//---Adding wasteStreamCombustion tab to tabSet
        wasteStreamCombustionTabSet.addTab(wasteStreamCombustionTab);
        wasteStreamCombustionLayout.addMember(wasteStreamCombustionTabSet);

}
private void initWasteStreamCombustionEditForm() {

    wasteStreamCombustionForm.setCellPadding(5);
    wasteStreamCombustionForm.setWidth("100%");

//-- Form fields  -------------------------------------------------------------------
    IntegerItem organizationId = new IntegerItem();
    organizationId.setName("organizationId");

    TextItem fuelSourceDescriptionItem = new TextItem();
    fuelSourceDescriptionItem.setName("fuelSourceDescription");

    FloatItem amountOfWasterStreamGasCombustedItem = new FloatItem();
    amountOfWasterStreamGasCombustedItem.setName("amountOfWasterStreamGasCombusted");

    TextItem amountOfWasterStreamGasCombustedUnitItem = new TextItem();
    amountOfWasterStreamGasCombustedUnitItem.setName("amountOfWasterStreamGasCombustedUnit");

	FloatItem totalNumberOfMolesPerUnitVolumentItem = new FloatItem();
	totalNumberOfMolesPerUnitVolumentItem.setName("totalNumberOfMolesPerUnitVolument");

	TextItem totalNumberOfMolesPerUnitVolumentUnitItem = new TextItem();
	totalNumberOfMolesPerUnitVolumentUnitItem.setName("totalNumberOfMolesPerUnitVolumentUnit");

	FloatItem carbonMonoxideMolarFractionPercentItem = new FloatItem();
	carbonMonoxideMolarFractionPercentItem.setName("carbonMonoxideMolarFractionPercent");

	FloatItem carbonDioxideMolarFractionPercentItem = new FloatItem();
	carbonDioxideMolarFractionPercentItem.setName("carbonDioxideMolarFractionPercent");

	FloatItem methaneMolarFractionPercentItem = new FloatItem();
	methaneMolarFractionPercentItem.setName("methaneMolarFractionPercent");

	FloatItem cetyleneMolarFractionPercentItem = new FloatItem();
	cetyleneMolarFractionPercentItem.setName("cetyleneMolarFractionPercent");

	FloatItem ethyleneMolarFractionPercentItem = new FloatItem();
	ethyleneMolarFractionPercentItem.setName("ethyleneMolarFractionPercent");

	FloatItem ethaneMolarFractionPercentItem = new FloatItem();
	ethaneMolarFractionPercentItem.setName("ethaneMolarFractionPercent");

	FloatItem propyleneMolarFractionPercentItem = new FloatItem();
	propyleneMolarFractionPercentItem.setName("propyleneMolarFractionPercent");

	FloatItem propaneMolarFractionPercentItem = new FloatItem();
	propaneMolarFractionPercentItem.setName("propaneMolarFractionPercent");

	FloatItem n_ButaneMolarFractionPercentItem = new FloatItem();
	n_ButaneMolarFractionPercentItem.setName("n_ButaneMolarFractionPercent");

	FloatItem benzeneMolarFractionPercentItem = new FloatItem();
	benzeneMolarFractionPercentItem.setName("benzeneMolarFractionPercent");

	FloatItem bexaneMolarFractionPercentItem = new FloatItem();
	bexaneMolarFractionPercentItem.setName("bexaneMolarFractionPercent");

	FloatItem tolueneMolarFractionPercentItem = new FloatItem();
	tolueneMolarFractionPercentItem.setName("tolueneMolarFractionPercent");

	FloatItem octaneMolarFractionPercentItem = new FloatItem();
	octaneMolarFractionPercentItem.setName("octaneMolarFractionPercent");

	FloatItem ethanolMolarFractionPercentItem = new FloatItem();
	ethanolMolarFractionPercentItem.setName("ethanolMolarFractionPercent");

	FloatItem acetoneMolarFractionPercentItem = new FloatItem();
	acetoneMolarFractionPercentItem.setName("acetoneMolarFractionPercent");

	FloatItem tetrahydrofuranMolarFractionPercentItem = new FloatItem();
	tetrahydrofuranMolarFractionPercentItem.setName("tetrahydrofuranMolarFractionPercent");

	FloatItem otherNon_CMolarFractionPercentItem = new FloatItem();
	otherNon_CMolarFractionPercentItem.setName("otherNon_CMolarFractionPercent");

      	FloatItem oxidationFactorPercentItem = new FloatItem();
	oxidationFactorPercentItem.setName("oxidationFactorPercent");


    DateItem fuelUsedBeginDateItem = new DateItem();
    fuelUsedBeginDateItem.setName("fuelUsedBeginDate");

    DateItem fuelUsedEndDateItem = new DateItem();
    fuelUsedEndDateItem.setName("fuelUsedEndDate");

    wasteStreamCombustionForm.setItems(organizationId,fuelSourceDescriptionItem,
					    amountOfWasterStreamGasCombustedItem,
					    amountOfWasterStreamGasCombustedUnitItem,
					    totalNumberOfMolesPerUnitVolumentItem,
					    totalNumberOfMolesPerUnitVolumentUnitItem,
					    carbonMonoxideMolarFractionPercentItem,
					    carbonDioxideMolarFractionPercentItem,
					    methaneMolarFractionPercentItem,
					    cetyleneMolarFractionPercentItem,
					    ethyleneMolarFractionPercentItem,
					    ethaneMolarFractionPercentItem,
					    propyleneMolarFractionPercentItem,
					    propaneMolarFractionPercentItem,
					    n_ButaneMolarFractionPercentItem,
					    benzeneMolarFractionPercentItem,
					    bexaneMolarFractionPercentItem,
					    tolueneMolarFractionPercentItem,
					    octaneMolarFractionPercentItem,
					    ethanolMolarFractionPercentItem,
					    acetoneMolarFractionPercentItem,
					    tetrahydrofuranMolarFractionPercentItem,
					    otherNon_CMolarFractionPercentItem,
                                            oxidationFactorPercentItem,
    					    fuelUsedBeginDateItem,fuelUsedEndDateItem
    					);

    final IButton wasteStreamCombustionCancelButton = new IButton();
    final IButton wasteStreamCombustionSaveButton = new IButton();

    wasteStreamCombustionSaveButton.setTitle("SAVE");
    wasteStreamCombustionSaveButton.setTooltip("Save this Source");
    wasteStreamCombustionSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        Record wasteStreamCombustionFormRecord = wasteStreamCombustionForm.getValuesAsRecord();
        wasteStreamCombustionDataGrid.updateData(wasteStreamCombustionFormRecord);
        wasteStreamCombustionFormWindow.hide();
      }
    });

    wasteStreamCombustionCancelButton.setTitle("CANCEL");
    wasteStreamCombustionCancelButton.setTooltip("Cancel");
    wasteStreamCombustionCancelButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        wasteStreamCombustionFormWindow.hide();
      }
    });

    HLayout buttons = new HLayout(10);
    buttons.setAlign(Alignment.CENTER);
    buttons.addMember(wasteStreamCombustionCancelButton);
    buttons.addMember(wasteStreamCombustionSaveButton);

    VLayout dialog = new VLayout(10);
    dialog.setPadding(10);
    dialog.addMember(wasteStreamCombustionForm);
    dialog.addMember(buttons);
    wasteStreamCombustionFormWindow.setShowShadow(true);
    wasteStreamCombustionFormWindow.setShowTitle(false);
    wasteStreamCombustionFormWindow.setIsModal(true);
    wasteStreamCombustionFormWindow.setPadding(20);
    wasteStreamCombustionFormWindow.setWidth(500);
    wasteStreamCombustionFormWindow.setHeight(350);
    wasteStreamCombustionFormWindow.setShowMinimizeButton(false);
    wasteStreamCombustionFormWindow.setShowCloseButton(true);
    wasteStreamCombustionFormWindow.setShowModalMask(true);
    wasteStreamCombustionFormWindow.centerInPage();
    //HeaderControl closeControl = new HeaderControl(HeaderControl.CLOSE, this);
    //wasteStreamCombustionFormWindow.setHeaderControls(HeaderControls.HEADER_LABEL, closeControl);
    wasteStreamCombustionFormWindow.addItem(dialog);
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
    //organizationNameItem.setWidth(100);

    TextItem organizationStreetAddress2Item = new TextItem("organizationStreetAddress2");
    //organizationNameItem.setWidth(200);

    TextItem organizationCityItem = new TextItem("organizationCity");
    //organizationNameItem.setWidth(50);

    TextItem organizationStateItem = new TextItem("organizationState");
    //organizationNameItem.setWidth(20);

    TextItem organizationZipCodeItem = new TextItem("organizationZipCode");
    organizationNameItem.setWidth(40);

    TextItem organizationCountryItem = new TextItem("organizationCountry");
    organizationNameItem.setWidth(200);

    TextItem organizationWebsiteItem = new TextItem("organizationWebsite");
    organizationNameItem.setWidth(200);

    TextItem organizationHQItem = new TextItem("organizationHQ");
    organizationNameItem.setWidth(20);

    TextItem pointOfContactItem = new TextItem("pointOfContact");
    organizationNameItem.setWidth(200);

    //organizationProfileForm.setIsGroup(Boolean.TRUE);
    //organizationProfileForm.setGroupTitle("Update your organization profile");
    //organizationProfileForm.setRedrawOnResize(true);

    organizationProfileForm.setItems(organizationNameItem, 
                                        organizationStreetAddress1Item,organizationStreetAddress2Item,
                                        organizationCityItem,organizationStateItem,organizationZipCodeItem,
                                        organizationCountryItem,organizationWebsiteItem,organizationHQItem,pointOfContactItem);

    final IButton organizationProfileCancelButton = new IButton();
    final IButton organizationProfileSaveButton = new IButton();

    organizationProfileSaveButton.setTitle("SAVE");
    organizationProfileSaveButton.setTooltip("Save");
    organizationProfileSaveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        if (!organizationProfileForm.getErrors().isEmpty()){
            SC.say("Please clear errors before submitting this information!");
        }
        else {
            //Record organizationRecord = organizationProfileForm.getValuesAsRecord();
            organizationProfileForm.saveData();
            //organizationProfileForm.clearValues();
            //organizationProfileForm.markForRedraw();
            //organizationProfileVLayout.hide();
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

//-- Form fields  -------------------------------------------------------------------
     DateItem emissionsBeginDate = new DateItem("emissionsBeginDate");
     emissionsBeginDate.setTitle("Emissions Begin Date");

     DateItem emissionsEndDate = new DateItem("emissionsEndDate");
     emissionsEndDate.setTitle("Emissions End Date");

     final SelectItem programTypeItem = new SelectItem();
     programTypeItem.setName("programType");
     programTypeItem.setTitle("Program Type");
     programTypeItem.setValueMap("EPA Climate Leaders", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");
     programTypeItem.setDefaultToFirstOption(Boolean.TRUE);
     programTypeItem.setDisabled(Boolean.TRUE);

    emissionsSummaryInputForm.setItems(emissionsBeginDate, emissionsEndDate, programTypeItem);

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
        	Record emissionsSummaryInputRecord = emissionsSummaryInputForm.getValuesAsRecord();
                String orgName = (String) UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
                emissionsSummaryInputRecord.setAttribute("organizationName", orgName);
		emissionsSummaryDS.performCustomOperation("calculateEmissionsSummary", emissionsSummaryInputRecord);
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

public void displayEmissionSourceInfo(String emissionSourceChoice) {
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
            } else if (emissionSourceChoice.equals("Emissions Report")){
               //emissionsSummaryInputForm.fetchData(fetchCriteria);
               middleMiddleHLayout.addChild(emissionsSummaryInputVLayout);            
            } else if (emissionSourceChoice.equals("Emissions Summary")){
               //emissionsSummaryInputForm.fetchData(fetchCriteria);
               emissionsSummaryDataGrid.filterData(fetchCriteria);
               middleMiddleHLayout.addChild(emissionsSummaryVLayout);
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
            } else if (emissionSourceChoice.equals("Stationary Combustions Sources")){
               //--Display stationary Combustion Data
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

               Criteria fireSuppressionSimplifiedMaterialBalanceFetchCriteria = new Criteria();
               fireSuppressionSimplifiedMaterialBalanceFetchCriteria.addCriteria("methodType", "Fire Suppression - Company-Wide Simplified Material Balance Method");
               fireSuppressionSimplifiedMaterialBalanceFetchCriteria.addCriteria(fetchCriteria);
               fireSuppressionDataGrid_2.filterData(fireSuppressionSimplifiedMaterialBalanceFetchCriteria);

               Criteria fireSuppressionACScreeningFetchCriteria = new Criteria();
               fireSuppressionACScreeningFetchCriteria.addCriteria("methodType", "Fire Suppression - Source Level Screening Method");
               fireSuppressionACScreeningFetchCriteria.addCriteria(fetchCriteria);
               fireSuppressionDataGrid_3.filterData(fireSuppressionACScreeningFetchCriteria);

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

public static Label getSectionLink(String message, ClickHandler handler) {
   Label link = new Label();
   link = new Label(message);
   link.addStyleName("sectionLink");
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

/*
  private AbstractDataTable createTable() {
    DataTable data = DataTable.create();
    data.addColumn(ColumnType.STRING, "Task");
    data.addColumn(ColumnType.NUMBER, "Hours per Day");
    data.addRows(2);
    data.setValue(0, 0, "Work");
    data.setValue(0, 1, 14);
    data.setValue(1, 0, "Sleep");
    data.setValue(1, 1, 10);
    return data;
  }

    private Options createOptions() {
    Options options = Options.create();
    options.setWidth(400);
    options.setHeight(240);
    //options.set3D(true);
    options.setTitle("My Daily Activities");
    return options;
  }
*/

}

