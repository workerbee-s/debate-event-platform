package me.shreyeschekuru.dsa.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.service.UserService;
import me.shreyeschekuru.dsa.notification.NotificationUtil;
import me.shreyeschekuru.dsa.views.DefaultDashboardView;
import me.shreyeschekuru.dsa.views.register.GitHubAuthService;
import me.shreyeschekuru.dsa.views.register.GoogleAuthService;
import me.shreyeschekuru.dsa.views.register.RegisterView;
import me.shreyeschekuru.dsa.views.register.UserTypeSelectionView;
import org.springframework.beans.factory.annotation.Autowired;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
@RouteAlias(value = "login")
public class LoginView extends Div {//implements HasErrorParameter<NotFoundException> {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private GitHubAuthService gitHubAuthService;

    @Autowired
    private UserService userService;

    public LoginView(UserService userService, GoogleAuthService googleAuthService, GitHubAuthService gitHubAuthService) {

        this.userService = userService;
        this.googleAuthService = googleAuthService;
        this.gitHubAuthService = gitHubAuthService;

//        this.authenticatedUser = authenticatedUser;
//        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
//
//        LoginI18n i18n = LoginI18n.createDefault();
//        i18n.setHeader(new LoginI18n.Header());
//        i18n.getHeader().setTitle("Debate Event Platform");
//        i18n.getHeader().setDescription("Login using user/user or admin/admin");
//        i18n.setAdditionalInformation(null);
//        setI18n(i18n);
//
//        setForgotPasswordButtonVisible(false);
//        setOpened(true);

        setSizeFull();
        addClassName("login-view");
        //######################### LEFT SECTION #########################
        VerticalLayout leftSection = new VerticalLayout();
        leftSection.addClassName("splash-screen-left");

        VerticalLayout logoLayout = new VerticalLayout();
        logoLayout.setHeight("10%");
        Image logo = new Image("images/dsa-logo-white.png", "DSA");
        logo.setHeight("100%");
        logoLayout.add(logo);

        leftSection.add(logoLayout);

        VerticalLayout sliderLayout = new VerticalLayout();
        sliderLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        sliderLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        sliderLayout.getStyle().set("margin-top","5%");
        sliderLayout.setHeight("60%");
        Image slider1 = new Image("images/img-slider-2.png", "DSA");
        slider1.setWidth("70%");

        H2 slide1Text = new H2("Let's engage in respectful and constructive debates");
        slide1Text.addClassName("slide-text");

        sliderLayout.add(slider1);
        sliderLayout.add(slide1Text);

        leftSection.add(sliderLayout);

        //######################### RIGHT SECTION #########################
        VerticalLayout rightSection = new VerticalLayout();
        rightSection.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        rightSection.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightSection.setWidth("50%");

        H1 mainHeading = new H1("Welcome to Debate Platform - We missed you!");
        mainHeading.getStyle().set("color", "#5A456C");
        mainHeading.getStyle().set("font-size","1.8rem");
        rightSection.add(mainHeading);

        H5 cmtText = new H5("Learn, Inspire and Be Inspired - Platform is designed for greater learning experience.");
        cmtText.getStyle().set("color", "#3A3F4C");
        rightSection.add(cmtText);

//        H5 subText = new H5("Let's create your new account for free and enjoy the experience");
//        subText.getStyle().set("color", "#d1d5db");
//        rightSection.add(subText);

        HorizontalLayout socialSignUpBtnsLayout = new HorizontalLayout();
        socialSignUpBtnsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        socialSignUpBtnsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        socialSignUpBtnsLayout.setWidth("100%");

        Button googleSignUpBtn = new Button("Login with Google");
        googleSignUpBtn.addClassName("google-button");

        googleSignUpBtn.addClickListener(event -> {
            VaadinSession session = VaadinSession.getCurrent();
            session.setAttribute("LoginView", this);

            String authorizationUrl = googleAuthService.getAuthorizationUrl();
            getUI().ifPresent(ui -> ui.getPage().setLocation(authorizationUrl));
            System.out.println("Completed the google workflow");
        });

        Button githubSignUpBtn = new Button("Login with Github");
        githubSignUpBtn.addClassName("github-button");

        githubSignUpBtn.addClickListener(event -> {
            VaadinSession session = VaadinSession.getCurrent();
            session.setAttribute("LoginView", this);

            String authorizationUrl = gitHubAuthService.getAuthorizationUrl();
            getUI().ifPresent(ui -> ui.getPage().setLocation(authorizationUrl));
            System.out.println("Completed the github workflow");
        });

        socialSignUpBtnsLayout.add(googleSignUpBtn);
        socialSignUpBtnsLayout.add(githubSignUpBtn);
        rightSection.add(socialSignUpBtnsLayout);

        H1 orText = new H1("(Or)");
        orText.getStyle().set("color", "#402755DC");
        orText.getStyle().set("font-size", "24px");
        orText.getStyle().set("margin-left", "auto");
        orText.getStyle().set("margin-right", "auto");

        rightSection.add(orText);

        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("form-control");

        formLayout.setMaxWidth("500px"); // Set the maximum width of the form

        TextField emailField = new TextField("Email");
        emailField.setRequiredIndicatorVisible(true);
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setRequiredIndicatorVisible(true);

        Button loginButton = new Button("Login");
        loginButton.addClassName("form-control-sign-btn");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        loginButton.addClickListener(event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();
            if (userService.authenticateUser(email, password)) {
                UI.getCurrent().access(() -> {
                    UI.getCurrent().navigate(DefaultDashboardView.class);
                });
            } else {
                // User authentication failed
                NotificationUtil.showNotification("Invalid Login Credentials", "Error");
            }
        });

        formLayout.add(emailField, passwordField, loginButton);
        rightSection.add(formLayout);

        Anchor loginLink = new Anchor("register", String.valueOf(RegisterView.class));
        loginLink.setText("Don't have an account with us? - Create one here");

        rightSection.add(loginLink);

        VerticalLayout footerLayout = new VerticalLayout();
        footerLayout.setWidth("50%");
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        footerLayout.addClassName("footer");

        Footer footer = new Footer();
        Paragraph copyright = new Paragraph("Shreyes Chekuru 2023-2024 © All rights reserved");
        footer.add(copyright);
        footerLayout.add(footer);

        rightSection.add(footerLayout);

        //######################### ADD LAYOUTS #########################

        // Create the split screen layout
        HorizontalLayout splitLayout = new HorizontalLayout();
        splitLayout.setSizeFull();
        splitLayout.add(leftSection, rightSection);
        splitLayout.setFlexGrow(1, leftSection);
        splitLayout.setFlexGrow(1, rightSection);

        add(splitLayout);
    }


//
//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        if (authenticatedUser.get().isPresent()) {
//            // Already logged in
//          //  setOpened(false);
//            event.forwardTo("");
//        }
//
//      //  setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
//    }

//    @Override
//    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
//        // Implement your custom logic for handling the "RouteNotFoundError" for "/login"
//        // For example, display a message or redirect to a specific page
//        getElement().setText("Page not found: " + event.getLocation().getPath());
//        return 404; // Return the appropriate HTTP status code
//    }
}
