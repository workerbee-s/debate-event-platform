package me.shreyeschekuru.dsa.views.students;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.views.layouts.HeaderLayout;
import me.shreyeschekuru.dsa.views.layouts.SidebarLayout;
import me.shreyeschekuru.dsa.views.sidebars.StudentSidebar;

@AnonymousAllowed
@PageTitle("Debate Event Platform")
@Route(value = "students")
@RouteAlias(value = "")
@CssImport(value = "./styles/students-view.css")
@CssImport(value = "./styles/sidebar.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/dsa-tabs.css", themeFor = "vaadin-tabs")
public class StudentMainLayout extends AppLayout {
    public StudentMainLayout()
    {
        addToNavbar(new HeaderLayout());
        addToDrawer(new SidebarLayout());
    }
}
