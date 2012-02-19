package org.ibLGHGCalc.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;   
import com.smartgwt.client.types.Alignment;   
import com.smartgwt.client.widgets.IButton;   
import com.smartgwt.client.widgets.events.ClickEvent;   
import com.smartgwt.client.widgets.events.ClickHandler;   
import com.smartgwt.client.widgets.form.DynamicForm;   
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGrid;   
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;   
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;   
import com.smartgwt.client.widgets.layout.HLayout;   
import com.smartgwt.client.widgets.layout.VLayout;   
import java.util.Map;

public class IblCompoundEditor extends VLayout {   
    private DataSource datasource;   
    private DynamicForm form;   
    private ListGrid grid;   
    private IButton saveButton;     
    private IButton newButton;
    private IButton removeButton;
    private IButton closeButton;   		
    private static Criteria criteria;
    private String parentKey;
    private Integer parentKeyValue;
    //private Integer purchasedProductId;
    
    private FormItem[] formItems;
    
    public IblCompoundEditor(DataSource datasource, Criteria criteria) {   
        this.datasource = datasource;
        this.criteria = criteria;   			
    }   
    
    @Override    
    protected void onInit() {   
        super.onInit();   
        this.form = new DynamicForm();   
        //form.setItems(formItems);   
        //form.setDataSource(datasource);   
        form.setAutoFocus(true);
        form.disable();
        form.setNumCols(6);
        
        saveButton = new IButton("Save");   
        //saveButton.setLayoutAlign(Alignment.CENTER);   
        saveButton.addClickHandler(new ClickHandler() {   
            public void onClick(ClickEvent event) {
                //Map map = criteria.getValues();
                //form.setValues(map);
                form.saveData(new DSCallback() {   
                        public void execute(DSResponse response, Object rawData, DSRequest request) {   
                              form.editNewRecord();
                              saveButton.disable(); 
                        }   
                    });   
                form.reset();
                form.disable();
            }   
        });   

        newButton = new IButton("New");   
        newButton.setLayoutAlign(Alignment.CENTER);   
        newButton.addClickHandler(new ClickHandler() {   
            public void onClick(ClickEvent event) {   
                form.editNewRecord();
                //form.setValue(parentKey, parentKeyValue);
                Map map = criteria.getValues();
                /*
                Set keys = map.keySet();    
                for (Iterator i = keys.iterator(); i.hasNext();) {
                  String key = (String) i.next();
                  String value = map.get(key).toString();   
                  form.setValue(key, value);
                }
                */
                //Get initial values and set it
                map.putAll(ibLUsers.getInitialValues());
                
                form.setValues(map);
                form.enable();
                form.setAutoFocus(true);
                saveButton.enable(); 
            }   
        });   

        removeButton = new IButton("Remove");   
        //removeButton.setLayoutAlign(Alignment.CENTER);   
        removeButton.addClickHandler(new ClickHandler() {   
            public void onClick(ClickEvent event) {   
                grid.removeSelectedData(new DSCallback() {   
                        public void execute(DSResponse response, Object rawData, DSRequest request) {   
                              form.editNewRecord();
                              form.disable();
                              saveButton.disable(); 
                        }   
                    },null);
            }   
        });   

        VLayout editorLayout = new VLayout(5);   
        editorLayout.addMember(form);   
        editorLayout.addMember(saveButton);
        
        HLayout buttonLayout = new HLayout(15);        
        buttonLayout.addMember(newButton);   
        buttonLayout.addMember(removeButton);   			
        //buttonLayout.setWidth("280");   
        
        //editorLayout.addMember(buttonLayout);   
        
        grid = new ListGrid();   
        //grid.setAlign(Alignment.CENTER);
        grid.setWidth("700");   
        grid.setHeight("200");   
        grid.setDataSource(datasource);   
        //grid.setAutoFetchData(true);   
        grid.addRecordClickHandler(new RecordClickHandler() {   
            public void onRecordClick(RecordClickEvent event) {   
                form.clearErrors(true);   
                form.editRecord(event.getRecord());   
                form.enable();
                form.setAutoFocus(true);
                saveButton.enable();   
            }   
        });   
        
        VLayout gridLayout = new VLayout();
        gridLayout.addMember(grid);
        gridLayout.addMember(buttonLayout);
        
        addMember(gridLayout);
        addMember(editorLayout);   
    }   

    public DataSource getDatasource() {   
        return datasource;   
    }   

    public static Criteria getCriteria() {   
        return criteria;   
    }   
    
    public void setDatasourceAndCriteria(DataSource datasource, Criteria criteria) {   
        this.datasource = datasource;   
        this.criteria = criteria;   
        grid.setDataSource(datasource);   
        form.setDataSource(datasource);   
        saveButton.disable();   
        grid.fetchData(criteria);           
    }   

    public void setDatasourceAndCriteria(DataSource datasource, Criteria criteria,  FormItem[] formItems) {   
        this.datasource = datasource;   
        this.criteria = criteria; 
        this.formItems = formItems;
        
        ibLUsers.initializeValidators();
        form.setDataSource(datasource);   
        form.setFields(formItems);                        
        
        grid.setDataSource(datasource);   
        grid.fetchData(criteria);        
        
        saveButton.disable();   
    }   
    
    public void setDatasourceAndCriteria(DataSource datasource, Criteria criteria, String parentKey, Integer parentKeyValue) {   
        this.datasource = datasource;   
        this.criteria = criteria;  
        grid.setDataSource(datasource);   
        form.setDataSource(datasource);   
        saveButton.disable();   
        grid.fetchData(criteria);   
        this.parentKey=parentKey;
        this.parentKeyValue=parentKeyValue;
    }   
    
    public void setDatasourceAndCriteria(DataSource datasource, Criteria criteria, String parentKey, Integer parentKeyValue, FormItem[] formItems) {   
        this.datasource = datasource;   
        this.criteria = criteria;  
        grid.setDataSource(datasource);   
        form.setDataSource(datasource);   
        saveButton.disable();   
        grid.fetchData(criteria);   
        this.parentKey=parentKey;
        this.parentKeyValue=parentKeyValue;
        form.setFields(formItems);
    }   
    
}   
