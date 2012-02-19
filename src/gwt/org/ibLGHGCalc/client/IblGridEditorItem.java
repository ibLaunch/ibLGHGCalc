package org.ibLGHGCalc.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.DoubleClickEvent;
import com.smartgwt.client.widgets.form.fields.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;

public class IblGridEditorItem extends CanvasItem {
        IblGridEditorItem (String name) {
            super(name);
            
            setWidth("*");
            setHeight("*");
            setColSpan("*");
            setEndRow(true);
            setStartRow(true);
            
            // this is going to be an editable data item
            setShouldSaveValue(true);
            
            addShowValueHandler(new ShowValueHandler() {  
                @Override  
                public void onShowValue(ShowValueEvent event) {  
                    CanvasItem item = (CanvasItem) event.getSource();  
                      
                    ListGrid grid = (ListGrid)item.getCanvas();  
                    if (grid==null) return;  
                      
                    grid.deselectAllRecords();  
                    String value = (String) event.getDisplayValue();  
                    if (value==null) return;  
                      
                    RecordList recordList = grid.getDataAsRecordList();  
                    int index = recordList.findIndex(item.getFieldName(), value);  
                    grid.selectRecord(index);  
                    //SC.say("I am in new add Show Handler");
                }  
            });  
            
            setInitHandler(new FormItemInitHandler() {
                @Override
                public void onInit(FormItem item) {
                    ListGrid grid = new ListGrid();
                    
                    grid.setCanEdit(Boolean.TRUE); 
                    //grid.setModalEditing(Boolean.TRUE);
                    grid.setEditEvent(ListGridEditEvent.CLICK);
                    grid.setListEndEditAction(RowEndEditAction.NEXT);
                    grid.setCanRemoveRecords(true);  
                    //grid.setDataPath("materialUsedBy_T1S_Info");
                    
                    /*
                    grid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {  
                        public void onRecordDoubleClick(RecordDoubleClickEvent event) {  
                            //CountryRecord record = (CountryRecord) event.getRecord();  
                            SC.say("I am in listgrid: Name is");  
                        }  
                    });  
                    */
                   
                    //grid.setAutoFetchData(Boolean.TRUE);

                    // dataSource and fields to use, provided to a listGridItem as
                    // listGridItem.gridDataSource and optional gridFields
                    grid.setDataSource(getGridDataSource());
                                        
                    if (gridFields != null) grid.setFields(getGridFields());
                    //if (gridSortField != null) grid.setSortField(getGridSortField());
                    //grid.setSortDirection(SortDirection.ASCENDING);
                    
                    // The form item's data is set to a list of records
                    RecordList value = (RecordList) item.getValue();
                    if (value != null) grid.setData(value);
                                        
                    grid.setSaveLocally(true);
                    
                    grid.addCellSavedHandler(new CellSavedHandler() {
                        @Override
                        public void onCellSaved(CellSavedEvent event) {
                            ListGrid grid = (ListGrid) event.getSource();
                            IblGridEditorItem item = (IblGridEditorItem) grid.getCanvasItem();
                            //RecordList data = grid.getDataAsRecordList();
                            //item.storeValue(data);
                            item.storeValue(new RecordList(grid.getRecords()));
                            if (item.getGridSortField() == null) return;
                            //grid.sort(item.getGridSortField(), SortDirection.ASCENDING);
                        }
                    });
                    
                    ((CanvasItem) item).setCanvas(grid);
                    /*
                    addShowValueHandler(new ShowValueHandler() {
                        @Override
                        public void onShowValue(ShowValueEvent event) {
                            ListGrid grid = (ListGrid)((CanvasItem)event.getSource()).getCanvas();
                            RecordList records = event.getDataValueAsRecordList();
                            if (records != null && grid != null) grid.setData(records);
                            //SC.say("I am showing Value");
                        }
                    });
                    */
                    
                    addDoubleClickHandler(new DoubleClickHandler() {  
                        public void onDoubleClick(DoubleClickEvent event) {  
                            //CountryRecord record = (CountryRecord) event.getRecord(); 
                            //ListGrid grid = (ListGrid) event.getSource();
                            //rec.setAttribute(gridSortField, null)                           
                            ibLUsers.initializeValidators();
                            ListGrid grid = (ListGrid)((CanvasItem)event.getSource()).getCanvas();
                            grid.startEditingNew();
                            //ListGridRecord rec = new ListGridRecord();                             
                            //grid.addData(rec); 
                            //SC.say("I am in Double Click even on Listgrid");  
                        }  
                    });                                                              
                }
            });
        }
        
        private RestDataSource gridDataSource;
        public RestDataSource getGridDataSource() {
            return this.gridDataSource;
        }
        
        public void setGridDataSource(RestDataSource gridDataSource) {
            this.gridDataSource = gridDataSource;
        }
        
        private ListGridField[] gridFields;
        public ListGridField[] getGridFields() {
            return gridFields;
        }
        
        public void setGridFields(ListGridField... gridFields) {
            this.gridFields = gridFields;
        }
        
        private String gridSortField;
        public String getGridSortField() {
            return gridSortField;
        }
        
        public void setGridSortField(String gridSortField) {
            this.gridSortField = gridSortField;
        }
    };
    