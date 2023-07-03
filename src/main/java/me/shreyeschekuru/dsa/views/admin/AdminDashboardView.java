package me.shreyeschekuru.dsa.views.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.views.layouts.MainLayout;


@PageTitle("Debate Event Platform - Admin")
@Route(value = "admin-dashboard", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminDashboardView extends VerticalLayout {

    public AdminDashboardView()
    {
        add(new Button("Iam in Admin Dashboard area"));
    }
}
