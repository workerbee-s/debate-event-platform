package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import me.shreyeschekuru.dsa.data.entity.Role;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@PageTitle("Debate Event Platform")
@CssImport(value = "./styles/students-view.css")
@CssImport(value = "./styles/sidebar.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/dsa-tabs.css", themeFor = "vaadin-tabs")
public class StudentMainLayout extends AppLayout implements RouterLayout {

    private Div mainContent;

    private UserRepository userRepository;

    public StudentMainLayout(UserRepository userRepository)
    {
        this.userRepository = userRepository;
        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Username: " + username);
        User user = userRepository.findByUsername(username);

        setPrimarySection(Section.NAVBAR);
        addToNavbar(new HeaderLayout());

        // Add the appropriate tabs or navigation links based on the user's role
        if (user != null) {
            Role userRole = user.getRole();
            if (userRole == Role.STUDENT) {
                addToDrawer(new SidebarLayout(Role.STUDENT));
            } else if (userRole == Role.JUDGE) {
                addToDrawer(new SidebarLayout(Role.JUDGE));
            } else if (userRole == Role.ORGANIZER) {
                addToDrawer(new SidebarLayout(Role.ORGANIZER));
            } else if (userRole == Role.ADMIN) {
                addToDrawer(new SidebarLayout(Role.ADMIN));
            } else {
                addToDrawer(new SidebarLayout(Role.GUEST));
            }
        }
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
