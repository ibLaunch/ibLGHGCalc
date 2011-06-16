package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
//import sun.util.calendar.LocalGregorianCalendar.Date;

public class Dashboard extends VLayout {

  //private static final int USER_ORGANIZATION_HEADER_HEIGHT = 60;

  private final OrganizationDS organizationDS = OrganizationDS.getInstance();
  private static final DynamicForm organizationSelectForm = new DynamicForm();
  
  private final ListGrid emissionsSummaryDashboardListGrid = new ListGrid();
  private final EmissionsSummaryDS emissionsSummaryDS = EmissionsSummaryDS.getInstance();

  private static final String BEGIN_DATE_FIELD_WIDTH = "15%";
  private static final String END_DATE_FIELD_WIDTH = "15%";

  public Dashboard() {
    super();

    GWT.log("init UserOrganizationHeader ()...", null);

    // initialise the layout container
    this.setHeight("80%");
    this.setWidth("100%");
    this.setAlign(Alignment.LEFT);
    this.setMargin(30);
    this.setMembersMargin(15);

    //this.setBackgroundImage("sun.gif");

    //organizationId.setName("id");
    StaticTextItem organizationName = new StaticTextItem("organizationName");
    StaticTextItem pointOfContact = new StaticTextItem("pointOfContact");
    //organizationName.setCellStyle("userOrganizationHeaderFormItem");
    //pointOfContact.setCellStyle("userOrganizationHeaderFormItem");

    organizationSelectForm.setDataSource(organizationDS);
    organizationSelectForm.setBorder("5px outset #286ea0");
    organizationSelectForm.setHeight("20%");
    organizationSelectForm.setWidth("100%");
    organizationSelectForm.setAlign(Alignment.LEFT);
    //organizationSelectForm.setTitleOrientation(TitleOrientation.LEFT);
    //organizationSelectForm.setEdgeSize(15);
    //organizationSelectForm.setEdgeBackgroundColor("CC3");
    //organizationSelectForm.setNumCols(2);
    //organizationSelectForm.setWidth("15");
    organizationSelectForm.setFields(organizationName,pointOfContact);

    FloatListGridField totalEmissions = new FloatListGridField("totalEmissions", "Total Emissions");
    ListGridField totalNumberOfSources = new ListGridField("totalNumberOfSources");
    ListGridField emissionsBeginDate = new ListGridField("emissionsBeginDate");
    emissionsBeginDate.setWidth(BEGIN_DATE_FIELD_WIDTH);
    ListGridField emissionsEndDate = new ListGridField("emissionsEndDate");
    emissionsEndDate.setWidth(END_DATE_FIELD_WIDTH);
    ListGridField programType = new ListGridField("programType");

    emissionsSummaryDashboardListGrid.setDataSource(emissionsSummaryDS);
    emissionsSummaryDashboardListGrid.setFields(programType,emissionsBeginDate, emissionsEndDate, totalNumberOfSources, totalEmissions);
    emissionsSummaryDashboardListGrid.setBorder("5px outset #286ea0");
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
    this.addMember(organizationSelectForm);
    this.addMember(emissionsSummaryDashboardListGrid);
  }
}
