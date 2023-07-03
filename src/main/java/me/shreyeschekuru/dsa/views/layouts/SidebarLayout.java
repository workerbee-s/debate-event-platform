package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import me.shreyeschekuru.dsa.data.entity.Role;
import me.shreyeschekuru.dsa.views.DefaultDashboardView;
import me.shreyeschekuru.dsa.views.admin.AdminDashboardView;
import me.shreyeschekuru.dsa.views.judge.JudgeDashboardView;
import me.shreyeschekuru.dsa.views.organizer.OrganizerDashboardView;
import me.shreyeschekuru.dsa.views.students.*;

@CssImport("./styles/sidebar.css")
public class SidebarLayout extends VerticalLayout {

    private Role role;
    private Tabs menu;
    public SidebarLayout(Role role) {
        this.role = role;
        setClassName("sidebar-layout");

        menu = new Tabs();
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.getStyle().set("flex-grow", "1");

        if(role == Role.STUDENT)
        {
            menu.add(createMenuButton("Dashboard", VaadinIcon.DASHBOARD, StudentDashboardView.class));
            menu.add(createMenuButton("Events", VaadinIcon.CALENDAR, StudentEventsView.class));
            menu.add(createMenuButton("Library", VaadinIcon.BOOK, StudentLibraryView.class));
            menu.add(createMenuButton("Mock Simulator", VaadinIcon.ACADEMY_CAP, StudentMockView.class));
            menu.add(createMenuButton("FAQs", VaadinIcon.QUESTION_CIRCLE_O, StudentFAQsView.class));
            menu.add(createMenuButton("Settings", VaadinIcon.COGS, StudentSettingsView.class));
        } else if (role == Role.JUDGE) {
            menu.add(createMenuButton("Judge Dashboard", VaadinIcon.DASHBOARD, JudgeDashboardView.class));
        } else if (role == Role.ORGANIZER) {
            menu.add(createMenuButton("Organizer Dashboard", VaadinIcon.DASHBOARD, OrganizerDashboardView.class));
        } else if (role == Role.ADMIN) {
            menu.add(createMenuButton("Admin Dashboard", VaadinIcon.DASHBOARD, AdminDashboardView.class));
        } else if (role == Role.GUEST) {
           // menu.add(createMenuButton("Default Dashboard", VaadinIcon.DASHBOARD, DefaultDashboardView.class));
            menu.add(createMenuButton("Settings", VaadinIcon.COGS, StudentSettingsView.class));
        }

        menu.addSelectedChangeListener(event -> {
            RouterLink selectedLink = (RouterLink) event.getSelectedTab().getElement().getComponent().get();
            UI.getCurrent().navigate(selectedLink.getHref());
        });

        add(menu);
    }

    private Tab createMenuButton(String text, VaadinIcon icon, Class<? extends Component> viewClass) {
        Icon menuIcon = icon.create();
        Button button = new Button(text, menuIcon);
        button.addClassName("sidebar-button");
        button.setIconAfterText(false);

        RouterLink link = new RouterLink();
        link.setRoute(viewClass);

        FlexLayout contentLayout = new FlexLayout();
        contentLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        contentLayout.add(menuIcon, new Text(text));
        contentLayout.addClassName("menu-item-layout");

        link.add(contentLayout);

        Tab tab = new Tab(link);
        tab.addClassName("menu-item");
        tab.getElement().addEventListener("click", event -> button.getUI().ifPresent(ui -> ui.navigate(viewClass)));
        tab.setSelected(true);
        return tab;
    }
}