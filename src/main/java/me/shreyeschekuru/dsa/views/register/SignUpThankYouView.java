package me.shreyeschekuru.dsa.views.register;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.notification.NotificationUtil;
import me.shreyeschekuru.dsa.views.layouts.BaseLayout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@PageTitle("Thank you for registering with us")
@Route(value = "signup-thank-you", layout = BaseLayout.class)
@CssImport("./styles/signup-thank-you.css")
@AnonymousAllowed
public class SignUpThankYouView extends VerticalLayout {

    private Button selectedCategory;
    private String loggedUserFirstName;

    public SignUpThankYouView()
    {
        super();

        addClassName("signup-thank-you");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User loggedInUser = (User) principal;
                loggedUserFirstName = loggedInUser.getFirstName();
                String lastName = loggedInUser.getLastName();
                System.out.println("First Name: " + loggedUserFirstName);
                System.out.println("Last Name: " + lastName);

                System.out.println("loggedInUser (authentication.getName()): " + authentication.getName());
            }else {
                loggedUserFirstName = "Guest User";
            }
            System.out.println("loggedUserFirstName : " + loggedUserFirstName);
        }


        NotificationUtil.showNotification("Congratulations!. Your profile has been correctly updated on the system. See you on Launch day!", "Success");

        //######################### MAIN LAYOUT #########################

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidthFull();
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        mainLayout.addClassName("main-layout");

        HorizontalLayout pictureLayout = new HorizontalLayout();
        pictureLayout.setAlignItems(FlexComponent.Alignment.START);
        pictureLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        pictureLayout.addClassName("picture-layout");

        VerticalLayout picVLayout = new VerticalLayout();
        picVLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        picVLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        picVLayout.addClassName("pic-layout");

        Image shreyesPic = new Image("images/shreyes.png", "Shreyes Chekuru");
        shreyesPic.getStyle().set("width","385").set("height","65");
        Span shereyesTxt = new Span("I'm Shreyes Chekuru");
        picVLayout.add(shreyesPic);
        picVLayout.add(shereyesTxt);

        pictureLayout.add(picVLayout);


        VerticalLayout thankYouLayout = new VerticalLayout();
        thankYouLayout.setWidth("60%");
        thankYouLayout.getStyle().set("margin-left", "-20%").set("margin-right", "20%");
        thankYouLayout.setAlignItems(FlexComponent.Alignment.START);
        thankYouLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        thankYouLayout.addClassName("thank-you-layout");

        Span infoText = new Span("Thank you for registering with our DSA platform! We developed this platform with students, parents, and judges in mind, to provide a standardized and streamlined platform for effective communication and feedback. Our user-friendly interface makes it easy for anyone to participate and provide constructive criticism. We hope that our platform will help you hone your critical thinking and communication skills, and we look forward to supporting you in your debate journey. If you have any questions or concerns, please do not hesitate to reach out to us. Thank you again for joining our community!\n");
        infoText.getStyle().set("text-align", "justify");

        HtmlComponent specialTxt = new HtmlComponent("span");

        specialTxt.getElement().setProperty("innerHTML", "Special Launch & LIVE in-person event planned on August 19th (Saturday) <br><br>Stone Hill Middle School,<br>23415 Evergreen Ridge Dr, <br>Ashburn, VA 20148. <br><br>We would like to see you there.");
        specialTxt.getStyle().set("color", "#F05480").set("font-size", "bold").set("font-size", "16px");


        HorizontalLayout launchLayout = new HorizontalLayout();
        launchLayout.setAlignItems(FlexComponent.Alignment.START);
        launchLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        Image launchImg=new Image("images/launch.png", "Launch Event");
        launchImg.getStyle().set("width", "200px").set("height","200px");

        Span addSpace = new Span("");
        addSpace.addClassName("addSpace");

        thankYouLayout.add(infoText);
        thankYouLayout.add(addSpace);

        launchLayout.add(launchImg);
        launchLayout.add(addSpace);
        launchLayout.add(specialTxt);
        launchLayout.add(addSpace);
        thankYouLayout.add(launchLayout);

//        Button countMeIn = new Button("Love it, Count me in!");
//        countMeIn.addClassName("count-me-in");
//        countMeIn.getStyle().set("background-color","#8c59c2").set("color","#F5F5F5").set("width", "100%").set("font-size", "24px").set("height","50px");
//        thankYouLayout.add(countMeIn);
//
//        Button skipMe = new Button("It's Ok, skip me out");
//        skipMe.addClassName("skip-me-in");
//        skipMe.getStyle().set("background-color","#D8D8D8").set("color","#333333").set("width", "100%");
//        thankYouLayout.add(skipMe);

        pictureLayout.add(thankYouLayout);
        mainLayout.add(pictureLayout);

        mainLayout.add(addSpace);

       add(mainLayout);
    }

}
