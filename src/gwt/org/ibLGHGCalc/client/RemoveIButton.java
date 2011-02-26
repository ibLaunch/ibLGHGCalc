package org.ibLGHGCalc.client;

import com.google.gwt.layout.client.Layout.Alignment;
import com.smartgwt.client.widgets.IButton;

public class RemoveIButton extends IButton {

  public RemoveIButton() {
    super();

    this.setShowDown(false);
    this.setShowRollOver(false);
    //this.setLayoutAlign(Alignment.CENTER);
    this.setIcon("/ibLGHGCalc/images/deleteIcon.png");
    this.setPrompt("Remove");
    this.setDefaultHeight(20);
    this.setDefaultWidth(40);
    this.setMaxWidth(40);
    this.setMaxHeight(20);
    
  }
}
