package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import java.util.HashMap;

  public class IblReportListGrid extends ListGrid {
      public IblReportListGrid(RestDataSource ds) {
        super();

        GWT.log("init IblReportListGrid ()...", null);

        this.setWidth100();
        this.setHeight100();
        this.setShowRecordComponents(true);
        this.setShowRecordComponentsByCell(true);
        this.setCanRemoveRecords(true);
        this.setAutoFetchData(Boolean.TRUE);
        this.setCanExpandRecords(true);
        this.setExpansionMode(ExpansionMode.DETAILS);
        this.setDataSource(ds);
    /*
        //this.setCanEdit(Boolean.TRUE);
    //    
    //    this.setCanHover(true);
    //    this.setShowHover(true);
    //    this.setShowHoverComponents(true);
        this.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
            public void onRecordDoubleClick(RecordDoubleClickEvent event) {
                ibLUsers.initializeValidators();
                dataForm.editRecord(event.getRecord());
                dataFormWindow.show();
            }
        });
     *
     */
      }

    @Override
    protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
        final String fieldName = this.getFieldName(colNum);

        //SC.say("I am in createRecord Overide");
        if (fieldName.equals("viewReportButtonField")) {
            //Button viewReportButton = new Button();
            IButton viewReportButton = new IButton();
            viewReportButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    Integer orgId = (Integer)UserOrganizationHeader.organizationSelectForm.getField("id").getValue();
                    //String fileName = record.getAttribute("reportFileName");
                    String emissionsSummaryReportId = (String)record.getAttribute("id");
                    //SC.say("I am in createRecord Overide");

                    HTMLPane htmlPane = new HTMLPane();
                    htmlPane.setHeight100();
                    htmlPane.setWidth100();
                    htmlPane.setContentsURL("/ibLGHGCalc/reports");

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
/*
  @Override
  protected Canvas getCellHoverComponent(Record record, Integer rowNum, Integer colNum) {
    DetailViewer detailViewer = new DetailViewer();
    detailViewer.setWidth(400);
    detailViewer.setUseAllDataSourceFields(true);    
    detailViewer.setDataSource(dataSource);
    Criteria criteria = new Criteria();
    criteria.addCriteria("id", record.getAttribute("id"));
    detailViewer.fetchData(criteria);
    return detailViewer;
  }
*/
}