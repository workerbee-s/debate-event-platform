package me.shreyeschekuru.dsa.views.students;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.views.layouts.MainLayout;


@PageTitle("Debate Event Platform - Students")
@Route(value = "students/dashboard", layout = MainLayout.class)
@RolesAllowed({"ROLE_STUDENT", "STUDENT", "GUEST"})
public class StudentDashboardView extends VerticalLayout {
    public StudentDashboardView() {
        add(new Button("Iam in dashboard area"));
    }
}
