package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import me.shreyeschekuru.dsa.data.entity.UserSession;


@PageTitle("Debate Event Platform")
@CssImport(value = "./styles/students-view.css")
@CssImport(value = "./styles/sidebar.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/dsa-tabs.css", themeFor = "vaadin-tabs")
public class BaseLayout extends AppLayout implements RouterLayout {

    private Div mainContent;

    public BaseLayout(UserSession userSession)
    {

        setPrimarySection(Section.NAVBAR);
        addToNavbar(new BaseHeaderLayout());

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
