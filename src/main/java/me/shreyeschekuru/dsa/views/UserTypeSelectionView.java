package me.shreyeschekuru.dsa.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("UserType Selection")
@Route(value = "utselect")
@RouteAlias(value = "user")
public class UserTypeSelectionView extends Div {

    private Button selectedCategory;

    public UserTypeSelectionView()
    {
        setSizeFull();
        addClassName("usertype-selection-view");

        //######################### HEADER #########################

        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.addClassName("header");

        HorizontalLayout logoLayout = new HorizontalLayout();

        Image logo = new Image("images/dsa-logo.png", "Logo");
        logo.addClassName("header-logo");

        logoLayout.add(logo);

        HorizontalLayout userInfoLayout = new HorizontalLayout();
        userInfoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        userInfoLayout.setAlignItems(FlexComponent.Alignment.END);
        userInfoLayout.addClassName("user-layout");

        Span helloText = new Span("Hello");
        userInfoLayout.add(helloText);

        Span userInfoText = new Span("Srinivasa,");
        userInfoText.getStyle().set("color", "#D19967").set("margin-left", "-8px");
        userInfoLayout.add(userInfoText);

        Span welcomeText = new Span("Welcome to DSA!");
        welcomeText.getStyle().set("margin-left", "-8px");
        userInfoLayout.add(welcomeText);

        headerLayout.add(logoLayout);
        headerLayout.add(userInfoLayout);

        add(headerLayout);



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
            }

        });

        // ########################### Participant ##################
        Button participantButton = new Button();
        participantButton.addClassName("square-button");

        Image participantImg = new Image("images/participant.png", "Participant");
        participantImg.addClassName("small-img");

        Span participantTxt = new Span("Participant");
        participantTxt.addClassName("button-text");

        VerticalLayout participantContentLayout = new VerticalLayout(participantImg, participantTxt);
        participantButton.getElement().appendChild(participantContentLayout.getElement());

        participantButton.addClickListener(event -> {

            if (participantButton == selectedCategory) {
                selectedCategory = null;
                participantButton.removeClassName("selected");
                participantTxt.removeClassName("selected");
                participantContentLayout.remove(tickImage);
            } else {
                if (selectedCategory != null) {
                    selectedCategory.removeClassName("selected");
                }
                selectedCategory = participantButton;
                participantContentLayout.add(tickImage);
                participantButton.addClassName("selected");
                participantTxt.addClassName("selected");
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
            }

        });

        // ########################### Parent ##################
        Button parentButton = new Button();
        parentButton.addClassName("square-button");

        Image parentImg = new Image("images/parent.png", "Parents");
        parentImg.addClassName("small-img");

        Span parentTxt = new Span("Parent");
        parentTxt.addClassName("button-text");

        VerticalLayout parentContentLayout = new VerticalLayout(parentImg, parentTxt);
        parentButton.getElement().appendChild(parentContentLayout.getElement());

        parentButton.addClickListener(event -> {

            if (parentButton == selectedCategory) {
                selectedCategory = null;
                parentButton.removeClassName("selected");
                parentTxt.removeClassName("selected");
                parentContentLayout.remove(tickImage);
            } else {
                if (selectedCategory != null) {
                    selectedCategory.removeClassName("selected");
                }
                selectedCategory = parentButton;
                parentContentLayout.add(tickImage);
                parentButton.addClassName("selected");
                parentTxt.addClassName("selected");
            }

        });


        userTypeSelectionBtnLayout.add(eventButton);
        userTypeSelectionBtnLayout.add(judgeButton);
        userTypeSelectionBtnLayout.add(participantButton);
        userTypeSelectionBtnLayout.add(studentButton);
        userTypeSelectionBtnLayout.add(parentButton);

        mainLayout.add(userTypeSelectionBtnLayout);

        Span addSpace = new Span("");
        addSpace.addClassName("add-space");

        Span infoText = new Span("A student can benefit from our debate app by engaging in meaningful discussions, developing critical thinking skills, " +
                "improving communication abilities, and gaining exposure to diverse perspectives. Our platform provides a safe and structured environment " +
                "to practice debating and refine public speaking skills, which can be beneficial in academic and professional settings. Additionally, " +
                "students can learn to construct persuasive arguments, research and analyze complex topics, and collaborate with peers, all " +
                "while having fun and building their confidence.");
        infoText.addClassName("info-text");


        mainLayout.add(addSpace);
        mainLayout.add(infoText);

        add(mainLayout);

        //######################### FOOTER #########################

        VerticalLayout footerLayout = new VerticalLayout();
        footerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        footerLayout.addClassName("footer");

        Footer footer = new Footer();
        Paragraph copyright = new Paragraph("Shreyes Chekuru 2023-2024 © All rights reserved");
        footer.add(copyright);
        footerLayout.add(footer);

        add(footerLayout);
    }

    private VerticalLayout addHeaderLayout() {
        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.addClassName("header");

        Image logo = new Image("images/dsa-logo.png", "Logo");
        logo.addClassName("header-logo");

        headerLayout.add(logo);
        return headerLayout;
    }
    private void addFooterLayout() {

    }

    private void addMainContent() {

    }
}
