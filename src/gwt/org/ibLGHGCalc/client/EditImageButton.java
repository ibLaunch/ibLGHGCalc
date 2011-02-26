package org.ibLGHGCalc.client;

import com.google.gwt.layout.client.Layout.Alignment;
import com.smartgwt.client.widgets.ImgButton;

public class EditImageButton extends ImgButton {


  public EditImageButton() {
    super();

    this.setShowDown(false);
    this.setShowRollOver(false);
    //this.setLayoutAlign(Alignment.CENTER);
    this.setSrc("/ibLGHGCalc/images/editIcon.gif");
    this.setPrompt("Edit Source");
    this.setDefaultHeight(20);
    this.setDefaultWidth(40);
    this.setMaxWidth(40);
    this.setMaxHeight(20);
    
  }
}
