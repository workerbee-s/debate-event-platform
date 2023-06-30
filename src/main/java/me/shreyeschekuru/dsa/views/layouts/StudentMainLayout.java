package me.shreyeschekuru.dsa.views.students;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.views.layouts.HeaderLayout;
import me.shreyeschekuru.dsa.views.layouts.SidebarLayout;

@AnonymousAllowed
@PageTitle("Debate Event Platform")
@CssImport(value = "./styles/students-view.css")
@CssImport(value = "./styles/sidebar.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/dsa-tabs.css", themeFor = "vaadin-tabs")
public class StudentMainLayout extends AppLayout implements RouterLayout {

    private Div mainContent;
    public StudentMainLayout()
    {
        setPrimarySection(Section.NAVBAR);
        addToNavbar(new HeaderLayout());
        addToDrawer(new SidebarLayout());
        setDrawerOpened(true);


        // Create the main content area
        mainContent = new Div();
        mainContent.setSizeFull();
        setContent(mainContent);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        mainContent.removeAll();
        mainContent.getElement().appendChild(content.getElement());
    }
    public Div getMainContent() {
        return mainContent;
    }

}
