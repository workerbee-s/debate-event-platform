package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport(value = "./styles/header.css")
public class HeaderView extends HorizontalLayout {

    public HeaderView()
    {
        setWidthFull();
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("toggle-button");

        // Create the logo
        Image logo = new Image("images/dsa-logo-white.png", "DSA");
        logo.addClassName("dashboard-logo");

        // Create a horizontal layout to hold the logo and toggle button
        HorizontalLayout logoLayout = new HorizontalLayout(toggle, logo);
        logoLayout.setSpacing(false);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(logoLayout);

        HorizontalLayout rightContentLayout = new HorizontalLayout();
        rightContentLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightContentLayout.setSpacing(true);
        rightContentLayout.addClassName("right-content-header");

        // Create the "Welcome User" message
        Span welcomeMessage = new Span("Welcome back!");
        rightContentLayout.add(welcomeMessage);

        Span userInfo = new Span("Srinivasa");
        userInfo.getStyle().set("color","#D19967");
        rightContentLayout.add(userInfo);

        // Create the logout button
        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClassName("logout-button");

        logoutButton.addClickListener(e -> {
            // Logout logic
        });
        rightContentLayout.add(logoutButton);
        add(rightContentLayout);
    }
}
