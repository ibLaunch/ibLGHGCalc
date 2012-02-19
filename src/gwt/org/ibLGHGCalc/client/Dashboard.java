package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
//import sun.util.calendar.LocalGregorianCalendar.Date;

public class Dashboard extends VLayout {

  //private static final int USER_ORGANIZATION_HEADER_HEIGHT = 60;

  private String LISTGRID_STYLE_NAME="dashboardListGrid";
  private String FORM_STYLE_NAME="dashboardForm";
  private final OrganizationDS organizationDS = OrganizationDS.getInstance();
  private static final DynamicForm organizationSelectForm = new DynamicForm();
  
  //private final ListGrid emissionsSummaryDashboardListGrid = new ListGrid();
private final ListGrid emissionsSummaryDashboardListGrid = new ListGrid()
        {
            @Override
            protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
                final String fieldName = this.getFieldName(colNum);

                //SC.say("I am in createRecord Overide");
                if (fieldName.equals("updateSummaryButton")) {
                    IButton updateSummaryButton = new IButton();
                    updateSummaryButton.setTitle("Recalculate");
                    updateSummaryButton.addClickHandler(new ClickHandler() {
                        public void onClick(ClickEvent event) {
                            
	                    String organizationName = (String)UserOrganizationHeader.organizationSelectForm.getField("organizationName").getValue();
	                    /*
                            String programType = (String)record.getAttribute("programType");                            
                            Date emissionsBeginDate = (Date)record.getAttribute("emissionsBeginDate");
                            Date emissionsEndDate = (Date)record.getAttribute("emissionsEndDate");
			    			    
			    //--create a record with attributes
                            Record dashboardCriteriaRecord = new Record();
			    dashboardCriteriaRecord.setAttribute("organizationId",orgId);
			    dashboardCriteriaRecord.setAttribute("programType",programType);
			    dashboardCriteriaRecord.setAttribute("emissionsBeginDate",emissionsBeginDate);
			    dashboardCriteriaRecord.setAttribute("emissionsEndDate",emissionsEndDate);
			    */
			    record.setAttribute("organizationName",organizationName);
                            
                            
			    emissionsSummaryDashboardListGrid.addData(record, new DSCallback() {
				    public void execute(DSResponse response, Object rawData, DSRequest request) {
					//SC.say("Check if data is updated!");
                                        Criteria emissionsSummaryFetchCriteria = organizationSelectForm.getValuesAsCriteria();
                                        emissionsSummaryDashboardListGrid.filterData(emissionsSummaryFetchCriteria);
                                        //emissionsSummaryDashboardListGrid.refreshRow(rowNum);
				    }
				});
                            
                        }
                    });
                    return updateSummaryButton;
                } else {
                    return null;
                }

            }
        };
      
  private final EmissionsSummaryDS emissionsSummaryDS = EmissionsSummaryDS.getInstance();

  private static final String BEGIN_DATE_FIELD_WIDTH = "15%";
  private static final String END_DATE_FIELD_WIDTH = "15%";
  private static final String LAST_UPDATE_DATETIME_FIELD_WIDTH = "20%";

  public Dashboard() {
    super();

    GWT.log("init UserOrganizationHeader ()...", null);

    // initialise the layout container
    this.setHeight("80%");
    this.setWidth("100%");
    this.setAlign(Alignment.LEFT);
    this.setMargin(30);
    this.setMembersMargin(15);

    emissionsSummaryDashboardListGrid.setShowRecordComponents(true);
    emissionsSummaryDashboardListGrid.setShowRecordComponentsByCell(true);
    //emissionsSummaryDashboardListGrid.setAutoFetchData(Boolean.TRUE);
    //this.setBackgroundImage("sun.gif");

    //organizationId.setName("id");
    StaticTextItem organizationName = new StaticTextItem("organizationName");
    StaticTextItem pointOfContact = new StaticTextItem("pointOfContact");
    //organizationName.setCellStyle("userOrganizationHeaderFormItem");
    //pointOfContact.setCellStyle("userOrganizationHeaderFormItem");

    organizationSelectForm.setDataSource(organizationDS);
    //organizationSelectForm.setBorder("5px outset #286ea0");
    organizationSelectForm.setStyleName(FORM_STYLE_NAME);

    organizationSelectForm.setHeight("20%");
    organizationSelectForm.setWidth("100%");
    organizationSelectForm.setAlign(Alignment.LEFT);
    //organizationSelectForm.setTitleOrientation(TitleOrientation.LEFT);
    //organizationSelectForm.setEdgeSize(15);
    //organizationSelectForm.setEdgeBackgroundColor("CC3");
    //organizationSelectForm.setNumCols(2);
    //organizationSelectForm.setWidth("15");
    organizationSelectForm.setFields(organizationName,pointOfContact);

    FloatListGridField totalEmissions = new FloatListGridField("totalEmissions", "Total Emissions(MT CO2-e)");
    FloatListGridField totalOptionalEmissions = new FloatListGridField("totalOptionalEmissions", "Optional Emissions(MT CO2-e)");
    ListGridField totalNumberOfSources = new ListGridField("totalNumberOfSources");
    ListGridField emissionsBeginDate = new ListGridField("emissionsBeginDate");
    emissionsBeginDate.setWidth(BEGIN_DATE_FIELD_WIDTH);
    ListGridField emissionsEndDate = new ListGridField("emissionsEndDate");
    emissionsEndDate.setWidth(END_DATE_FIELD_WIDTH);
    ListGridField programType = new ListGridField("programType");
    ListGridField lastUpdated = new ListGridField("lastUpdated");
    lastUpdated.setWidth(LAST_UPDATE_DATETIME_FIELD_WIDTH);
    //set date and time format
    lastUpdated.setDateFormatter(DateDisplayFormat.TOSTRING);    

    ListGridField updateSummaryButton = new ListGridField("updateSummaryButton", "Recalculate");
    emissionsSummaryDashboardListGrid.setDataSource(emissionsSummaryDS);
    emissionsSummaryDashboardListGrid.setFields(programType,emissionsBeginDate, emissionsEndDate, totalNumberOfSources, totalEmissions,totalOptionalEmissions,lastUpdated, updateSummaryButton);
    emissionsSummaryDashboardListGrid.sort("lastUpdated", SortDirection.DESCENDING);    
    //emissionsSummaryDashboardListGrid.hideField("lastUpdated");
    
    //emissionsSummaryDashboardListGrid.setBorder("5px outset #286ea0");
    emissionsSummaryDashboardListGrid.setStyleName(LISTGRID_STYLE_NAME);

    //emissionsSummaryDashboardListGrid.setAutoFitFieldWidths(Boolean.TRUE);
    //emissionsSummaryDashboardListGrid.setBackgroundImage("sun.gif");
    
    organizationSelectForm.fetchData(null, new DSCallback(){
         public void execute(DSResponse response, Object rawData, DSRequest request) {
                Criteria emissionsSummaryFetchCriteria = organizationSelectForm.getValuesAsCriteria();
                //SC.say(emissionsSummaryFetchCriteria.toString());
                emissionsSummaryDashboardListGrid.filterData(emissionsSummaryFetchCriteria);
             }
        });
    /*
    HLayout northLayout = new HLayout();
    northLayout.setHeight("50%");
    northLayout.setWidth100();
    northLayout.addMember(emissionsSummaryDashboardListGrid);
    northLayout.addMember(organizationSelectForm);
    this.addMember(northLayout);
     *
     */
    Label emissionsSummaryLabel = new Label("Emissions Summary");
    emissionsSummaryLabel.setHeight(15);
    emissionsSummaryLabel.setWidth100();
    emissionsSummaryLabel.setAlign(Alignment.LEFT);
    emissionsSummaryLabel.setStyleName("labels");

    VLayout eSummaryVLayout = new VLayout();
    eSummaryVLayout.addMember(emissionsSummaryLabel);
    eSummaryVLayout.addMember(emissionsSummaryDashboardListGrid);
    
    this.addMember(organizationSelectForm);
    this.addMember(eSummaryVLayout);
  }
}
