package me.shreyeschekuru.dsa.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.views.layouts.BaseLayout;
import me.shreyeschekuru.dsa.views.layouts.MainLayout;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "welcome", layout = BaseLayout.class)
@AnonymousAllowed
public class DefaultDashboardView extends VerticalLayout {
    // Add components and functionality specific to the organizer role
    public DefaultDashboardView(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                // role contains the role of the current user
                System.out.println("Current user role: " + role);
            }
        }

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("52%");
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setAlignItems(Alignment.CENTER);

        Span addSpace = new Span("");
        addSpace.addClassName("addSpace");

        Span welcomeText = new Span("Love it!, We are equally excited as you are, but you may need to wait a little longer. We are still under construction and we plan to launch it on August 19th, 2023. So Stay tuned as we complete our work.");
        welcomeText.getStyle().set("font-size", "25px").set("color", "#5A456C");

        layout.add(addSpace);
        layout.add(welcomeText);
        layout.add(addSpace);
        layout.add(addSpace);

        Image loadingImage = new Image("https://miro.medium.com/v2/resize:fit:1400/1*z1GtdIU39OpomatEOIrREA.gif", "Loading Site");
        layout.add(loadingImage);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        add(layout);
    }
}
