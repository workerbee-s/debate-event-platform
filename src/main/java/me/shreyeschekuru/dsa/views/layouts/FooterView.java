package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/footer.css")
public class FooterView extends Footer {

    public FooterView() {
        addClassName("full-width-footer");

        VerticalLayout footerLayout = new VerticalLayout();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        footerLayout.addClassName("footer-full");

        Footer footer = new Footer();
        Paragraph copyright = new Paragraph("Shreyes Chekuru 2023-2024 © All rights reserved");
        footer.add(copyright);
        footerLayout.add(footer);

        add(footerLayout);
    }
}
