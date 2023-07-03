package me.shreyeschekuru.dsa.views.students;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import me.shreyeschekuru.dsa.views.layouts.StudentMainLayout;

@PageTitle("Debate Event Platform - Students")
@Route(value = "students/mock", layout = StudentMainLayout.class)
@PermitAll
@RolesAllowed({"ADMIN", "STUDENT"})
public class StudentMockView extends VerticalLayout {
    public StudentMockView() {
        add(new Button("Iam in Mock Simulator area"));
    }

}
