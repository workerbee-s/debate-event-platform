package me.shreyeschekuru.dsa.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import me.shreyeschekuru.dsa.components.appnav.AppNav;



@CssImport("./styles/sidebar.css")
public class SidebarView extends VerticalLayout {

    public SidebarView() {
        addClassName("sidebar");


        AppNav nav = new AppNav();

//        nav.addItem(new AppNavItem("Dashboard", DashboardView.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));
//        nav.addItem(new AppNavItem("Events", DashboardView.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));
//        nav.addItem(new AppNavItem("Library", DashboardView.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));
//        nav.addItem(new AppNavItem("Settings", DashboardView.class, LineAwesomeIcon.CHART_AREA_SOLID.create()));

        add(nav);

    }
}
