package me.shreyeschekuru.dsa.views.register;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.data.entity.Role;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.service.UserService;
import me.shreyeschekuru.dsa.notification.NotificationUtil;
import me.shreyeschekuru.dsa.utilities.UIUtility;
import me.shreyeschekuru.dsa.views.layouts.BaseLayout;
import me.shreyeschekuru.dsa.views.layouts.MainLayout;
import me.shreyeschekuru.dsa.views.login.LoginView;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Debate Event Platform - Select User Type")
@Route(value = "user-select", layout = BaseLayout.class)
@RouteAlias(value = "user-select")
@CssImport("./styles/usertype-selection-view.css")
@AnonymousAllowed
public class UserTypeSelectionView extends VerticalLayout {

    private Button selectedCategory;
    private UserService userService;


    public UserTypeSelectionView(UserService userService)
    {
        this.userService = userService;
        setSizeFull();
        addClassName("usertype-selection-view");

        Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();

        User loggedInUser = (User) oldAuthentication.getPrincipal();

        System.out.println("Authentication is not empty: " + oldAuthentication.toString());

        //######################### MAIN LAYOUT #########################

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidthFull();
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Span mainText = new Span("To help us customize your unique experience on the platform and ensure you get the most of it, please make a selection as which user type you would like to sign-up as.");
        mainText.addClassName("helpful-text");
        mainLayout.add(mainText);


        HorizontalLayout userTypeSelectionBtnLayout = new HorizontalLayout();
        userTypeSelectionBtnLayout.setWidth("100%");
        userTypeSelectionBtnLayout.addClassName("usrBtnHLayout");
        userTypeSelectionBtnLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        userTypeSelectionBtnLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Image tickImage = new Image("images/tick.png", "Tick");
        tickImage.addClassName("tick-image");

        Button saveButton = new Button("Complete Registration");
        saveButton.getStyle().set("width", "25%");
        saveButton.setEnabled(false);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // ########################### Event Organizer ##################
        Button eventButton = new Button();
        eventButton.addClassName("square-button");

        Image evntImg = new Image("images/event-organizer.png", "Event Organizer");
        evntImg.addClassName("small-img");

        Span evntOrgTxt = new Span("Event Organizer");
        evntOrgTxt.addClassName("button-text");

        VerticalLayout eventContentLayout = new VerticalLayout(evntImg, evntOrgTxt);
        eventButton.getElement().appendChild(eventContentLayout.getElement());

        eventButton.addClickListener(event -> {

            if (eventButton == selectedCategory) {
                selectedCategory = null;
                eventButton.removeClassName("selected");
                evntOrgTxt.removeClassName("selected");
                eventContentLayout.remove(tickImage);
            } else {
                if (selectedCategory != null) {
                    selectedCategory.removeClassName("selected");
                }
                selectedCategory = eventButton;
                eventContentLayout.add(tickImage);
                eventButton.addClassName("selected");
                evntOrgTxt.addClassName("selected");

                GrantedAuthority newAuthority = new SimpleGrantedAuthority("ROLE_ORGANIZER");
                List<GrantedAuthority> authorities = new ArrayList<>(oldAuthentication.getAuthorities());;
                authorities.add(newAuthority);

                // Create a new Authentication object with the updated authorities
                Authentication newAuthentication = new UsernamePasswordAuthenticationToken(oldAuthentication.getPrincipal(), oldAuthentication.getCredentials(), authorities);

                System.out.println("Authentication before setting : " + SecurityContextHolder.getContext().getAuthentication());
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                System.out.println("Authentication post setting : " + SecurityContextHolder.getContext().getAuthentication());


                boolean success = userService.updateRole(loggedInUser.getEmail(), Role.ORGANIZER.toString());
                if (success) {
                    UI.getCurrent().navigate(UserTypeSelectionView.class);
                } else {
                    Notification.show("Failed to update role");
                }
            }
        });

        // ########################### Judge ##################
        Button judgeButton = new Button();
        judgeButton.addClassName("square-button");

        Image judgeImg = new Image("images/judge.png", "Judge");
        judgeImg.addClassName("small-img");

        Span judgeTxt = new Span("Judge");
        judgeTxt.addClassName("button-text");

        VerticalLayout judgeContentLayout = new VerticalLayout(judgeImg, judgeTxt);
        judgeButton.getElement().appendChild(judgeContentLayout.getElement());

        judgeButton.addClickListener(event -> {

            if (judgeButton == selectedCategory) {
                selectedCategory = null;
                judgeButton.removeClassName("selected");
                judgeTxt.removeClassName("selected");
                judgeContentLayout.remove(tickImage);
            } else {
                if (selectedCategory != null) {
                    selectedCategory.removeClassName("selected");
                }
                selectedCategory = judgeButton;
                judgeContentLayout.add(tickImage);
                judgeButton.addClassName("selected");
                judgeTxt.addClassName("selected");
                GrantedAuthority newAuthority = new SimpleGrantedAuthority("ROLE_JUDGE");
                List<GrantedAuthority> authorities = new ArrayList<>(oldAuthentication.getAuthorities());;
                authorities.add(newAuthority);

                // Create a new Authentication object with the updated authorities
                Authentication newAuthentication = new UsernamePasswordAuthenticationToken(oldAuthentication.getPrincipal(), oldAuthentication.getCredentials(), authorities);

                System.out.println("Authentication before setting : " + SecurityContextHolder.getContext().getAuthentication());
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                System.out.println("Authentication post setting : " + SecurityContextHolder.getContext().getAuthentication());

                boolean success = userService.updateRole(loggedInUser.getEmail(), Role.JUDGE.toString());
                if (success) {
                    UI.getCurrent().navigate(UserTypeSelectionView.class);
                  //  NotificationUtil.showNotification("Congratulations! " + loggedInUser.getFirstName() + ". Successfully updated your profile.", "Success");
                } else {
                    Notification.show("Failed to update role");

                }
            }

        });


        // ########################### Student ##################
        Button studentButton = new Button();
        studentButton.addClassName("square-button");

        Image studentImg = new Image("images/student.png", "Student");
        studentImg.addClassName("small-img");

        Span studentTxt = new Span("Student");
        studentTxt.addClassName("button-text");

        VerticalLayout studentContentLayout = new VerticalLayout(studentImg, studentTxt);
        studentButton.getElement().appendChild(studentContentLayout.getElement());

        studentButton.addClickListener(event -> {

            if (studentButton == selectedCategory) {
                selectedCategory = null;
                studentButton.removeClassName("selected");
                studentTxt.removeClassName("selected");
                studentContentLayout.remove(tickImage);
            } else {
                if (selectedCategory != null) {
                    selectedCategory.removeClassName("selected");
                }
                selectedCategory = studentButton;
                studentContentLayout.add(tickImage);
                studentButton.addClassName("selected");
                studentTxt.addClassName("selected");

                GrantedAuthority newAuthority = new SimpleGrantedAuthority("ROLE_STUDENT");
                List<GrantedAuthority> authorities = new ArrayList<>(oldAuthentication.getAuthorities());;
                authorities.add(newAuthority);

                // Create a new Authentication object with the updated authorities
                Authentication newAuthentication = new UsernamePasswordAuthenticationToken(oldAuthentication.getPrincipal(), oldAuthentication.getCredentials(), authorities);

                System.out.println("Authentication before setting : " + SecurityContextHolder.getContext().getAuthentication());
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                System.out.println("Authentication post setting : " + SecurityContextHolder.getContext().getAuthentication());

              //  loggedInUser.setRole(Role.STUDENT);

                boolean success = userService.updateRole(loggedInUser.getEmail(), Role.STUDENT.toString());
                if (success) {
                    UI.getCurrent().navigate(UserTypeSelectionView.class);
                   // NotificationUtil.showNotification("Congratulations! " + loggedInUser.getFirstName() + ". Successfully updated your profile.", "Success");
                } else {
                    Notification.show("Failed to update role");
                }
            }
        });

        // Create a click listener for the buttons
        ComponentEventListener<ClickEvent<Button>> buttonClickListener = event -> {
            boolean anyButtonSelected = eventButton.isEnabled() || judgeButton.isEnabled() || studentButton.isEnabled();
            saveButton.setEnabled(anyButtonSelected);
        };

        eventButton.addClickListener(buttonClickListener);
        judgeButton.addClickListener(buttonClickListener);
        studentButton.addClickListener(buttonClickListener);


        saveButton.addClickListener(event ->{
            System.out.println("$$$$$$$$$$$$$$$");
            UIUtility.printGrantedAuthorities(SecurityContextHolder.getContext().getAuthentication());
            System.out.println("$$$$$$$$$$$$$$$");
            UI.getCurrent().navigate(SignUpThankYouView.class);
        });

        userTypeSelectionBtnLayout.add(eventButton);
        userTypeSelectionBtnLayout.add(judgeButton);
       // userTypeSelectionBtnLayout.add(participantButton);
        userTypeSelectionBtnLayout.add(studentButton);
       // userTypeSelectionBtnLayout.add(parentButton);

        mainLayout.add(userTypeSelectionBtnLayout);

        Span addSpace = new Span("");
        addSpace.addClassName("add-space");

        mainLayout.add(addSpace);
        mainLayout.add(addSpace);

        mainLayout.add(saveButton);



        Span infoText = new Span("A student can benefit from our debate app by engaging in meaningful discussions, developing critical thinking skills, " +
                "improving communication abilities, and gaining exposure to diverse perspectives. Our platform provides a safe and structured environment " +
                "to practice debating and refine public speaking skills, which can be beneficial in academic and professional settings. Additionally, " +
                "students can learn to construct persuasive arguments, research and analyze complex topics, and collaborate with peers, all " +
                "while having fun and building their confidence.");
        infoText.addClassName("info-text");


        mainLayout.add(addSpace);
        mainLayout.add(infoText);

        add(mainLayout);

    }

}
