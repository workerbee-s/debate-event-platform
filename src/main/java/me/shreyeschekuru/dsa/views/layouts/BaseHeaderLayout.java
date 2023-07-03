package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.security.PermitAll;

@CssImport(value = "./styles/header.css")
@PermitAll
public class BaseHeaderLayout extends HorizontalLayout {


    public BaseHeaderLayout() {

        setWidthFull();
//        DrawerToggle toggle = new DrawerToggle();
//        toggle.addClassName("toggle-button");

        // Create the logo
        Image logo = new Image("images/dsa-logo-white.png", "DSA");
        logo.addClassName("dashboard-logo");

        // Create a horizontal layout to hold the logo and toggle button
        HorizontalLayout logoLayout = new HorizontalLayout(logo);
        logoLayout.setSpacing(false);
        logoLayout.setAlignItems(Alignment.CENTER);
        add(logoLayout);

    }
}

