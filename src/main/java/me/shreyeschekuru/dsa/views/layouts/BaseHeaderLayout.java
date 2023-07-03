package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.entity.UserSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@CssImport(value = "./styles/header.css")
@PermitAll
public class BaseHeaderLayout extends HorizontalLayout {

    private UserSession userSession;
    private static final String LOGOUT_SUCCESS_URL = "/";

    private String loggedUserFirstName;

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

