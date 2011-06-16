package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
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
import com.smartgwt.client.widgets.layout.VLayout;
//import sun.util.calendar.LocalGregorianCalendar.Date;

public class UserOrganizationHeader extends VLayout {

  private static final int USER_ORGANIZATION_HEADER_HEIGHT = 60;

  private final OrganizationDS organizationDS = OrganizationDS.getInstance();
  public static final DynamicForm organizationSelectForm = new DynamicForm();
  //public static final SelectItem programTypeSelectItem = new SelectItem();
  //public static final DateItem currentInventoryBeginDateInHeader = new DateItem("currentInventoryBeginDate");
  //public static final DateItem currentInventoryEndDateInHeader = new DateItem("currentInventoryEndDate");
  public static final StaticTextItem organizationName = new StaticTextItem("organizationName");
  public static final IntegerItem organizationId = new IntegerItem();
  
  public static final StaticTextItem programType = new StaticTextItem("programType");
  public static StaticTextItem currentInventoryBeginDateInHeader = new StaticTextItem("currentInventoryBeginDate");
  public static StaticTextItem currentInventoryEndDateInHeader = new StaticTextItem("currentInventoryEndDate");
  
  public UserOrganizationHeader() {
    super();

    GWT.log("init UserOrganizationHeader ()...", null);

    // initialise the layout container
    this.setHeight(USER_ORGANIZATION_HEADER_HEIGHT);
    this.setHeight100();
    this.setWidth100();
    //this.setBackgroundColor("#EFFBFB");
    //this.setShowEdges(true);
    //this.setAlign(VerticalAlignment.BOTTOM);
    this.setAlign(Alignment.CENTER);
    /*
    Img headerImg = new Img(GWT.getHostPageBaseURL()+"images/sun.gif", 1000,60);
    headerImg.setImageType(ImageStyle.TILE);
    */
    this.setBackgroundImage("sun.gif");
    organizationId.setName("id");
    organizationSelectForm.setDataSource(organizationDS);
    organizationSelectForm.setNumCols(10);
    //organizationSelectForm.setAlign(Alignment.CENTER);
    //organizationSelectForm.setBorder("1px double orange");
    //organizationSelectForm.setOverflow(Overflow.HIDDEN);
    
    //--set the style for all the formItem
    setFormItemStyle();
    
    //organizationSelectForm.setColWidths("10%","10%","10%","10%","10%","10%","10%","10%","10%","10%");
    //organizationSelectForm.setEdgeBackgroundColor("CC3");
    
    //currentInventoryBeginDateInHeader.setDisabled(Boolean.TRUE);
    //currentInventoryBeginDateInHeader.setUseTextField(Boolean.TRUE);
    //currentInventoryBeginDateInHeader.setWidth("*");

    //currentInventoryEndDateInHeader.setDisabled(Boolean.TRUE);
    //currentInventoryEndDateInHeader.setUseTextField(Boolean.TRUE);
    //currentInventoryEndDateInHeader.setWidth("*");
/*
    //final SelectItem programTypeSelectItem = new SelectItem();
    programTypeSelectItem.setName("programType");
    programTypeSelectItem.setTitle("Program Type");
    programTypeSelectItem.setValueMap("EPA Climate Leaders");
    //programTypeSelectItem.setValueMap("EPA Climate Leaders", "California ARB 32", "WCI", "India", "China", "Russia","Brazil");
    programTypeSelectItem.setDefaultToFirstOption(Boolean.TRUE);
    programTypeSelectItem.setDisabled(Boolean.TRUE);
    //programTypeSelectItem.setWidth("*");
*/

    organizationSelectForm.setFields(organizationId, organizationName,programType, currentInventoryBeginDateInHeader, currentInventoryEndDateInHeader);
    organizationSelectForm.hideItem("id");
    organizationSelectForm.fetchData();

    // add the Application menu to the nested layout container
    //this.addMember(new ApplicationMenu());
    this.addMember(organizationSelectForm);
  }

  private void setFormItemStyle(){

    organizationSelectForm.setTitleOrientation(TitleOrientation.LEFT);

    organizationName.setTitle("Organization");
    //organizationName.setTitleStyle("userOrganizationHeaderFormItemTitle");
    organizationName.setCellStyle("userOrganizationHeaderFormItem");


    currentInventoryBeginDateInHeader.setTitle("Inventory From");
    //currentInventoryBeginDateInHeader.setTitleStyle("userOrganizationHeaderFormItemTitle");
    currentInventoryBeginDateInHeader.setCellStyle("userOrganizationHeaderFormItem");
    currentInventoryBeginDateInHeader.setDisplayFormat(DateDisplayFormat.TOUSSHORTDATE);

    currentInventoryEndDateInHeader.setTitle("Inventory Till");
    //currentInventoryEndDateInHeader.setTitleStyle("userOrganizationHeaderFormItemTitle");
    currentInventoryEndDateInHeader.setCellStyle("userOrganizationHeaderFormItem");
    currentInventoryEndDateInHeader.setDisplayFormat(DateDisplayFormat.TOUSSHORTDATE);

    programType.setTitle("Program Type");
    //programType.setTitleStyle("userOrganizationHeaderFormItemTitle");
    programType.setCellStyle("userOrganizationHeaderFormItem");
    

  }
}