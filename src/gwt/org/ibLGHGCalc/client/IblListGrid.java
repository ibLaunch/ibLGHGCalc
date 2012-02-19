package org.ibLGHGCalc.client;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import java.util.Date;


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
    this.setHeight100();
    this.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);    
    this.setShowRecordComponents(true);
    this.setShowRecordComponentsByCell(true);
    this.setCanRemoveRecords(true);    
    //this.set
    //this.setShowAllRecords(true);
    //this.setAutoFetchData(Boolean.TRUE);
    //this.setCanEdit(Boolean.TRUE);
    this.setDataSource(dataSource);
    this.setCanHover(true);
    this.setShowHover(true);
    this.setShowHoverComponents(true);
    this.setCanExpandRecords(true);
    this.setExpansionMode(ExpansionMode.DETAILS);
    //this.setExpansionMode(ExpansionMode.RELATED);
    this.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
        public void onRecordDoubleClick(RecordDoubleClickEvent event) {            
            ibLUsers.initializeValidators();                                                
            dataForm.editRecord(event.getRecord());
            dataFormWindow.show();                                              
            
            /*
            Date currentInventoryBeginDateMin = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryBeginDate").getValue();
            Date currentInventoryEndDateMax = (Date)UserOrganizationHeader.organizationSelectForm.getField("currentInventoryEndDate").getValue();
            
            Record rec = event.getRecord();  
            Integer purchasedProductInfoId = (Integer) rec.getAttributeAsInt("id");
            
            Criteria criteria = new Criteria();
            criteria.addCriteria("inventoryYearBeginDate", currentInventoryBeginDateMin);
            criteria.addCriteria("inventoryYearEndDate", currentInventoryEndDateMax);            
            criteria.addCriteria("purchasedProductInfoId", purchasedProductInfoId);            
            
            MaterialUsedBy_T1S_InfoDS materialUsedBy_T1S_InfoDS = MaterialUsedBy_T1S_InfoDS.getInstance();         
            //materialUsedBy_T1S_InfoDS.fetchData(criteria,null);                        

            final ListGrid testThisItem = (ListGrid)((IblGridEditorItem) dataForm.getItem("materialUsedBy_T1S_Info")).getCanvas();                        
            testThisItem.setDataSource(materialUsedBy_T1S_InfoDS);            
            //testThisItem.fetchData(criteria);
            
            materialUsedBy_T1S_InfoDS.fetchData(criteria, new DSCallback(){
               public void execute(DSResponse response, Object rawData, DSRequest request) {
                  testThisItem.setData(response.getData());
               }
            });
            
            dataForm.editRecord(rec);
            dataFormWindow.show();                         
            */
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