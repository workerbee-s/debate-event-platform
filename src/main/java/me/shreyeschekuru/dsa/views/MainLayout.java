package me.shreyeschekuru.dsa.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import me.shreyeschekuru.dsa.security.AuthenticatedUser;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout()
    {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }
    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        HorizontalLayout rightContent = new HorizontalLayout();
        rightContent.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightContent.setSpacing(true);
        rightContent.addClassName("right-content-header");

        // Create a label for "Welcome user"
        Label welcomeLabel = new Label("Welcome user");
        rightContent.add(welcomeLabel);

        // Create a button for "Logout"
        Button logoutButton = new Button("Logout");
        logoutButton.addClickListener(e -> {
            // Logout logic here
        });
        rightContent.add(logoutButton);

        addToNavbar(true, toggle, rightContent);
    }

    private void addDrawerContent() {
        addClassName("drawer");
        Image logo = new Image("images/dsa-logo-white.png", "Logo");
        logo.addClassNames("header-logo");

        VerticalLayout headerContent = new VerticalLayout(logo);
        headerContent.setWidthFull();
        headerContent.setAlignItems(FlexComponent.Alignment.CENTER);
        headerContent.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Header header = new Header(headerContent);
        header.getStyle().set("background-color", "#233348");

   //     Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header,createFooter());//, scroller, createFooter());
    }

    private Footer createFooter() {
        return new FooterView();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        //viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
