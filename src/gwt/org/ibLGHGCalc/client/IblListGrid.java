package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;


  public class IblListGrid extends ListGrid {

  private RestDataSource dataSource;
  private final DynamicForm dataForm;
  private final Window dataFormWindow;

  public IblListGrid(RestDataSource ds, DynamicForm df, Window dfw) {
    super();

    GWT.log("init IblListGrid ()...", null);
    this.dataSource = ds;
    this.dataForm = df;
    this.dataFormWindow = dfw;

    this.setWidth100();
    //this.setHeight(200);
    this.setHeight100();
    this.setShowRecordComponents(true);
    this.setShowRecordComponentsByCell(true);
    this.setCanRemoveRecords(true);
    //this.set
    //this.setShowAllRecords(true);
    this.setAutoFetchData(Boolean.TRUE);
    //this.setCanEdit(Boolean.TRUE);
    this.setDataSource(dataSource);
    this.setCanHover(true);
    this.setShowHover(true);
    this.setShowHoverComponents(true);
    this.setCanExpandRecords(true);
    this.setExpansionMode(ExpansionMode.DETAILS);
    this.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
        public void onRecordDoubleClick(RecordDoubleClickEvent event) {
            ibLUsers.initializeValidators();
            dataForm.editRecord(event.getRecord());
            dataFormWindow.show();
        }
    });
  }

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



}