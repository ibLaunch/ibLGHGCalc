package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
//import sun.util.calendar.LocalGregorianCalendar.Date;

public class UserOrganizationHeader extends HLayout {

  private static final int USER_ORGANIZATION_HEADER_HEIGHT = 35;

  private final OrganizationDS organizationDS = OrganizationDS.getInstance();
  public static final DynamicForm organizationSelectForm = new DynamicForm();
  public static final SelectItem programTypeSelectItem = new SelectItem();
  public static final DateItem currentInventoryBeginDateInHeader = new DateItem("currentInventoryBeginDate");
  public static final DateItem currentInventoryEndDateInHeader = new DateItem("currentInventoryEndDate");
  public static final StaticTextItem organizationName = new StaticTextItem("organizationName");
  public static final IntegerItem organizationId = new IntegerItem();
  
  /*
  public static StaticTextItem currentInventoryEndDateString = new StaticTextItem("currentInventoryEndDateString");
  public static StaticTextItem currentInventoryBeginDateString = new StaticTextItem("currentInventoryBeginDateString");
  */
  public UserOrganizationHeader() {
    super();

    GWT.log("init UserOrganizationHeader ()...", null);

    // initialise the layout container
    this.setHeight(USER_ORGANIZATION_HEADER_HEIGHT);
    this.setBackgroundColor("#EFFBFB");

    //-- Organization selection Form
    organizationSelectForm.setTitleOrientation(TitleOrientation.TOP);
    organizationSelectForm.setNumCols(4);
    //organizationSelectForm.setTitleOrientation(TitleOrientation.LEFT);
    //organizationSelectForm.setShowEdges(true);
    organizationSelectForm.setWidth("36%");
    organizationSelectForm.setBorder("1px double orange");
    
    organizationSelectForm.setColWidths("25%");
    organizationSelectForm.setEdgeBackgroundColor("CC3");
    organizationSelectForm.setDataSource(organizationDS);
    //organizationSelectForm.setIsGroup(Boolean.TRUE);
    
    //final SelectItem organizationNameSelectItem = new SelectItem("organizationName");
    //organizationNameSelectItem.setTitle("Select Organization Name");
    //organizationNameSelectItem.setOptionDataSource(organizationDS);
    //organizationNameSelectItem.setValueMap("ibL0","ibL1","ibL2");
    //DateItem inventoryYearBeginDate = new DateItem("inventoryYearBeginDate");

    organizationId.setName("id");
    organizationName.setTitle("Organization");

    currentInventoryBeginDateInHeader.setTitle("Inventory Begin Date");
    currentInventoryBeginDateInHeader.setDisabled(Boolean.TRUE);
    currentInventoryBeginDateInHeader.setUseTextField(Boolean.TRUE);

    currentInventoryEndDateInHeader.setTitle("Inventory End Date");
    currentInventoryEndDateInHeader.setDisabled(Boolean.TRUE);
    currentInventoryEndDateInHeader.setUseTextField(Boolean.TRUE);

    //final SelectItem programTypeSelectItem = new SelectItem();
    programTypeSelectItem.setName("programType");
    programTypeSelectItem.setTitle("Program Type");
    programTypeSelectItem.setValueMap("EPA Climate Leaders", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");
    programTypeSelectItem.setDefaultToFirstOption(Boolean.TRUE);
    programTypeSelectItem.setDisabled(Boolean.TRUE);
    
    organizationSelectForm.setFields(organizationId, organizationName, currentInventoryBeginDateInHeader, currentInventoryEndDateInHeader, programTypeSelectItem);
    organizationSelectForm.hideItem("id");
    //organizationSelectForm.hideItem("currentInventoryBeginDate");
    //organizationSelectForm.hideItem("currentInventoryEndDate");

    organizationSelectForm.fetchData();
    //organizationSelectForm.setAlign(Alignment.CENTER);
    //organizationSelectForm.setOverflow(Overflow.HIDDEN);
    /*
    currentInventoryBeginDateString.setTitle("Inventory Begin Date");
    currentInventoryEndDateString.setTitle("Inventory End Date");
    currentInventoryBeginDateString.setValue(currentInventoryBeginDate.getValue().toString());
    currentInventoryEndDateString.setValue(currentInventoryEndDate.getValue().toString());
    */
    // add the Application menu to the nested layout container
    this.addMember(new ApplicationMenu());
    this.addMember(organizationSelectForm);
    this.setShowEdges(true);
    //this.setAlign(Alignment.CENTER);
  }
}