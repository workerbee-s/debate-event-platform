package me.shreyeschekuru.dsa.views.judge;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.views.layouts.MainLayout;

@AnonymousAllowed
@PageTitle("Debate Event Platform - Admin")
@Route(value = "judges-dashboard", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "JUDGE"})
public class JudgeDashboardView extends VerticalLayout {

    public JudgeDashboardView()
    {
        add(new Button("Iam in Judge Dashboard area"));
    }
}
