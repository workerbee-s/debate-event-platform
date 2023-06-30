package me.shreyeschekuru.dsa.views;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class SidebarMenuButton extends HorizontalLayout {
    public SidebarMenuButton(String caption, String imagePath) {
        Image image = new Image(imagePath, "Icon");
        image.setHeight("24px");
        image.setWidth("24px");

        Label label = new Label(caption);

        add(image, label);
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
    }
}
