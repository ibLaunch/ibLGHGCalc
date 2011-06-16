package org.ibLGHGCalc.client;

import com.google.gwt.layout.client.Layout.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;

public class RemoveIButton extends ImgButton {

  public RemoveIButton() {
    super();

    this.setShowDown(false);
    this.setShowRollOver(false);
    //this.setLayoutAlign(Alignment.CENTER);
    //this.setIcon("/ibLGHGCalc/images/deleteIcon.png");
    this.setSrc("/ibLGHGCalc/images/deleteIcon.png");
    //this.setIcon("/ibLGHGCalc/images/editIcon.png");
    this.setPrompt("Remove");
    this.setDefaultHeight(16);
    this.setDefaultWidth(40);
    this.setMaxWidth(40);
    this.setMaxHeight(16);
    this.setSnapTo("BL");
  }
}
