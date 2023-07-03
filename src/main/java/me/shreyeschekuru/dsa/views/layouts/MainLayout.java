package me.shreyeschekuru.dsa.views.layouts;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import me.shreyeschekuru.dsa.data.entity.Role;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.repository.UserRepository;
import me.shreyeschekuru.dsa.data.service.UserService;
import me.shreyeschekuru.dsa.views.register.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "dashboard")
@Secured({"ROLE_STUDENT", "ROLE_JUDGE", "ROLE_ORGANIZER", "ROLE_ADMIN", "ROLE_GUEST"})
public class MainLayout extends AppLayout implements RouterLayout {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private UserService userService;

    private Div mainContent;

    private UserRepository userRepository;

    public MainLayout(UserRepository userRepository)
    {
        this.userRepository = userRepository;
        // Retrieve the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Username: " + authentication.toString());
        User user = userRepository.findByUsername(username);

        setPrimarySection(AppLayout.Section.NAVBAR);
        addToNavbar(new HeaderLayout());

        // Add the appropriate tabs or navigation links based on the user's role
        if (user != null) {
            String userRole = user.getAssignedRole();
            System.out.println("Main Layout - User role (userRole) : " + userRole.toString());
            System.out.println("Main Layout - user.getRole() : " + user.getRole().toString());
            if (userRole.equalsIgnoreCase(Role.STUDENT.toString())) {
                addToDrawer(new SidebarLayout(Role.STUDENT));
                setDrawerOpened(true);
            } else if (userRole.equalsIgnoreCase(Role.JUDGE.toString())) {
                addToDrawer(new SidebarLayout(Role.JUDGE));
                setDrawerOpened(true);
            } else if (userRole.equalsIgnoreCase(Role.ORGANIZER.toString())) {
                addToDrawer(new SidebarLayout(Role.ORGANIZER));
                setDrawerOpened(true);
            } else if (userRole.equalsIgnoreCase(Role.ADMIN.toString())) {
                addToDrawer(new SidebarLayout(Role.ADMIN));
                setDrawerOpened(true);
            } else {
               System.out.println("Setting up no-sidebar for Guest user");
            }
        }

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

    private Role determineUserRole(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Retrieve the user's role from the database using the UserRepository or your preferred database access mechanism
            User retrievedUser = userRepository.findByUsername(username);
            if (retrievedUser != null) {
                return retrievedUser.getRole();
            }
        }

        // Default to a guest role or handle the case when the user is not authenticated
        return Role.GUEST;
    }
}