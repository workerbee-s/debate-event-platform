package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.entity.UserSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@CssImport(value = "./styles/header.css")
@PermitAll
public class HeaderLayout extends HorizontalLayout {

    private UserSession userSession;
    private static final String LOGOUT_SUCCESS_URL = "/";

    private String loggedUserFirstName;

    public HeaderLayout() {


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

        // Get the current authenticated user from the UserService
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "Guest User";
        if(authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User loggedInUser = (User) principal;
                loggedUserFirstName = loggedInUser.getFirstName();
                String lastName = loggedInUser.getLastName();
                System.out.println("First Name: " + username);
                System.out.println("Last Name: " + lastName);

                System.out.println("loggedInUser (authentication.getName()): " + authentication.getName());
            }else {
                loggedUserFirstName = "Guest User";
            }
            System.out.println("loggedUserFirstName : " + loggedUserFirstName);
        }

        Span userInfo = new Span();
        userInfo.setText(loggedUserFirstName);
        userInfo.getStyle().set("color", "#D19967");
        rightContentLayout.add(userInfo);

        // Create the user's image placeholder

        Image placeholderImage = new Image();//userSession.getUser().getProfilePicture(), "User Image");
        placeholderImage.setSrc("images/placeholder.png");
        placeholderImage.setWidth("40px");
        placeholderImage.setHeight("40px");
        rightContentLayout.add(placeholderImage);

        // Create the logout button
        Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClassName("logout-button");

        logoutButton.addClickListener(e -> {
            UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(
                    (HttpServletRequest) VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                    null);
        });
        rightContentLayout.add(logoutButton);
        add(rightContentLayout);
    }
}

