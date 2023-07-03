package me.shreyeschekuru.dsa.views.register;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.data.entity.Role;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.service.EmailService;
import me.shreyeschekuru.dsa.data.service.UserService;
import me.shreyeschekuru.dsa.notification.NotificationUtil;
import me.shreyeschekuru.dsa.utilities.UIUtility;
import me.shreyeschekuru.dsa.views.login.LoginView;
import me.shreyeschekuru.dsa.views.students.StudentDashboardView;
import me.shreyeschekuru.dsa.views.students.StudentEventsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;

@PageTitle("Registration")
@Route(value = "register")
@RouteAlias(value = "")
@AnonymousAllowed
@CssImport("./styles/main-layout.css")
public class RegisterView extends Div {

    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String OAUTH_URL = "/oauth2/authorization/google";

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private GitHubAuthService gitHubAuthService;
    @Autowired
    private EmailService emailService;

    public RegisterView(UserService userService, GoogleAuthService googleAuthService, GitHubAuthService gitHubAuthService) {

        this.userService = userService;
        this.googleAuthService = googleAuthService;
        this.gitHubAuthService = gitHubAuthService;

        setSizeFull();
        addClassName("register-view");
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
        Image slider1 = new Image("images/db-event.png", "DSA");
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
       // rightSection.getStyle().set("background-color", "#F5F5F5");


        H1 mainHeading = new H1("Welcome to Debate Platform!");
        mainHeading.getStyle().set("color", "#5A456C");
        mainHeading.getStyle().set("font-size","2.5rem");
        rightSection.add(mainHeading);

        H5 cmtText = new H5("We're excited to have you join our community. Sign up for free and enjoy the experience.");
        rightSection.add(cmtText);

//        H5 subText = new H5("Let's create your new account for free and enjoy the experience");
//        subText.getStyle().set("color", "#d1d5db");
//        rightSection.add(subText);

        HorizontalLayout socialSignUpBtnsLayout = new HorizontalLayout();
        socialSignUpBtnsLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        socialSignUpBtnsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        socialSignUpBtnsLayout.setWidth("100%");

        Button googleSignUpBtn = new Button("Sign up with Google");
        googleSignUpBtn.addClassName("google-button");

        googleSignUpBtn.addClickListener(event -> {
            VaadinSession session = VaadinSession.getCurrent();
            session.setAttribute("registrationView", this);

            String authorizationUrl = googleAuthService.getAuthorizationUrl();
            getUI().ifPresent(ui -> ui.getPage().setLocation(authorizationUrl));
           System.out.println("Completed the google workflow");
        });

        Button githubSignUpBtn = new Button("Sign up with Github");
        githubSignUpBtn.addClassName("github-button");

        githubSignUpBtn.addClickListener(event -> {
            VaadinSession session = VaadinSession.getCurrent();
            session.setAttribute("registrationView", this);

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

        TextField firstName = new TextField("First Name");
        firstName.addClassName("form-label");
        firstName.setRequiredIndicatorVisible(true);

        TextField lastName = new TextField("Last Name");
        lastName.addClassName("form-label");
        lastName.setRequiredIndicatorVisible(true);

        HorizontalLayout nameLayout = new HorizontalLayout();
        nameLayout.setWidthFull();
        nameLayout.add(firstName, lastName);


        TextField emailField = new TextField("Email");
        emailField.setRequiredIndicatorVisible(true);
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setRequiredIndicatorVisible(true);

        Checkbox acceptTermsCheckbox = new Checkbox();
        acceptTermsCheckbox.setLabel("I agree to the terms and conditions of the registration");
        acceptTermsCheckbox.setRequiredIndicatorVisible(true);

        Button signUpButton = new Button("Sign Up");
        signUpButton.addClassName("form-control-sign-btn");
        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        signUpButton.setEnabled(false); // Initially disable the button

        acceptTermsCheckbox.addValueChangeListener(event -> {
            signUpButton.setEnabled(event.getValue());
        });

        signUpButton.addClickListener(buttonClickEvent -> {
            User user = new User();
            user.setFirstName(firstName.getValue());
            user.setLastName(lastName.getValue());
            user.setEmail(emailField.getValue());
            user.setUsername(emailField.getValue());
         //   String encryptedPassword = passwordEncoder.encode(passwordField.getValue());
            String encryptedPassword = UIUtility.hashPassword(passwordField.getValue());
            user.setPassword(encryptedPassword);
            user.setEmailVerified(false);
            user.setPasswordApplicable(true);
            user.setoAuthProvider("system");


            String firstLetter = firstName.getValue().substring(0, 1).toUpperCase();
            String profilePictureUrl = "/images/letters/" + firstLetter + ".png";

            user.setProfilePicture(profilePictureUrl);

            boolean success = userService.registerUser(user);
            if (success) {
                emailService.sendRegistrationEmail(user.getEmail(), user.getFirstName() + " " + user.getLastName());

                System.out.println("User Role defined in DB: " + user.getAssignedRole());
                    user.setAssignedRole(Role.GUEST.toString());
                    user.setRole(Role.GUEST);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    NotificationUtil.showNotification("Congratulations! " + user.getFirstName() + ". You have successfully registered with our platform. Welcome aboard!", "Success");
                    UI.getCurrent().navigate(UserTypeSelectionView.class);

            } else {
                NotificationUtil.showNotification(user.getFirstName() + ". Looks like you already have an account with us, please login using your existing account.", "INFO");
                UI.getCurrent().navigate(LoginView.class);
            }

        });

        formLayout.add(nameLayout, emailField, passwordField, acceptTermsCheckbox, signUpButton);

        rightSection.add(formLayout);

        Anchor loginLink = new Anchor("login", String.valueOf(LoginView.class));
        loginLink.setText("Already have an account with us? - Login here");
        loginLink.getStyle().set("color","#4285F4");

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


}
