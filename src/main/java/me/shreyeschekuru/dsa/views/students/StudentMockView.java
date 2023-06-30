package me.shreyeschekuru.dsa.views.students;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.views.layouts.SidebarLayout;

@AnonymousAllowed
@PageTitle("Debate Event Platform - Students")
@Route(value = "students")
@RouteAlias(value = "")
@CssImport(value = "./styles/students-view.css")
@CssImport(value = "./styles/sidebar.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/dsa-tabs.css", themeFor = "vaadin-tabs")
public class StudentDashboardView extends StudentMainLayout{
    public StudentDashboardView() {
        super();
        // Get the sidebar layout
        SidebarLayout sidebarLayout = new SidebarLayout().getSidebarLayout();

        // Get the menu tabs
        Tabs menu = sidebarLayout.getMenuTabs();

        // Get the Dashboard button by name
        Button dashboardButton = getButtonByName(menu, "Dashboard");

        if (dashboardButton != null) {
            dashboardButton.getElement().setAttribute("selected", "true");
            dashboardButton.addClassName("selected");
        }
    }
    private Button getButtonByName(Tabs tabs, String buttonName) {
        return tabs.getChildren()
                .filter(tab -> tab instanceof Tab)
                .map(tab -> (Tab) tab)
                .filter(tab -> tab.getElement().getComponent().isPresent())
                .map(tab -> tab.getElement().getComponent().get())
                .filter(component -> component instanceof Button)
                .map(component -> (Button) component)
                .filter(button -> button.getText().equals(buttonName))
                .findFirst()
                .orElse(null);
    }
}
