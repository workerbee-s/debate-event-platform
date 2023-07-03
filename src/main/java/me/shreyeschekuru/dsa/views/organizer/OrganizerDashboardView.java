package me.shreyeschekuru.dsa.views.organizer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.views.layouts.MainLayout;

@AnonymousAllowed
@PageTitle("Debate Event Platform - Organizers")
@Route(value = "organizers-dashboard", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "ORGANIZER"})
public class OrganizerDashboardView extends VerticalLayout {

    public OrganizerDashboardView(){
        add(new Button("Iam in Organizer Dashboard area"));
    }
}
