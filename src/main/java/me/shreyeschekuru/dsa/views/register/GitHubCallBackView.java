package me.shreyeschekuru.dsa.views.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import me.shreyeschekuru.dsa.data.entity.Role;
import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.entity.UserSession;
import me.shreyeschekuru.dsa.data.service.EmailService;
import me.shreyeschekuru.dsa.data.service.UserService;
import me.shreyeschekuru.dsa.notification.NotificationUtil;
import me.shreyeschekuru.dsa.views.DefaultDashboardView;
import me.shreyeschekuru.dsa.views.admin.AdminDashboardView;
import me.shreyeschekuru.dsa.views.judge.JudgeDashboardView;
import me.shreyeschekuru.dsa.views.layouts.BaseLayout;
import me.shreyeschekuru.dsa.views.login.LoginView;
import me.shreyeschekuru.dsa.views.organizer.OrganizerDashboardView;
import me.shreyeschekuru.dsa.views.students.StudentDashboardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Route(value = "oauth2/callback/github")
@AnonymousAllowed
public class GitHubCallBackView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private GitHubAuthService gitHubAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public GitHubCallBackView(GitHubAuthService gitHubAuthService, UserService userService) {
        this.gitHubAuthService = gitHubAuthService;
        this.userService = userService;

    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        System.out.println("Entering the beforeEvent of sub class GitHubCallBackView 2.0 ");

        Location location = beforeEnterEvent.getLocation();
        String path = location.getPath();

        QueryParameters queryParameters = location.getQueryParameters();
        String code = queryParameters.getParameters().getOrDefault("code", Collections.emptyList()).stream().findFirst().orElse(null);

        System.out.println("Code : " + code + " Location Path contains : " + path.contains("google") + " or " + path.contains("github"));

        if (code != null) {
            String accessToken = gitHubAuthService.exchangeCodeForAccessToken(code);
            if (accessToken != null) {
                User userDto = gitHubAuthService.getUserInfoFromToken(code, accessToken);
                handleUserRegistration(userDto, "github");
            } else {
                System.out.println("GitHub Token Failure");
            }
        } else {
            System.out.println("Callback Failure");
        }

    }

    private void handleUserRegistration(User userDto, String provider) {
        if (userDto != null) {
            String email = userDto.getEmail();
            userDto.setAssignedRole(Role.GUEST.toString());
            userDto.setEmailVerified(true);
            userDto.setPasswordApplicable(false);
            userDto.setoAuthProvider(provider);

            User existingUser = userService.findUserByEmail(email);
            if (existingUser == null) {
                boolean success = userService.registerUser(userDto);
                if (success) {
                    emailService.sendRegistrationEmail(userDto.getEmail(), userDto.getFirstName() + " " + userDto.getLastName());

                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    UI.getCurrent().access(() -> {
                        UI.getCurrent().navigate(UserTypeSelectionView.class);
                    });
                    NotificationUtil.showNotification("Congratulations! " + userDto.getFirstName() + ". You have successfully registered with our platform. Welcome aboard!", "Success");
                } else {
                    NotificationUtil.showNotification("Failed to register user", "Error");
                }
            } else {
                // User already exists, perform desired actions or display a message
                System.out.println("User already exists: " + existingUser.toString());
                System.out.println("User already exists (existingUser.getAssignedRole): " + existingUser.getAssignedRole());

                if (!existingUser.getoAuthProvider().equalsIgnoreCase("github"))
                {
                    NotificationUtil.showNotification( "Your email is already associated in the system with a different provider, please try to login with another account.", "INFO");
                    UI.getCurrent().access(() -> {
                        UI.getCurrent().navigate(LoginView.class);
                    });
                }
                else {

                    UI.getCurrent().access(() -> {
                        if (existingUser.getAssignedRole().equalsIgnoreCase(Role.ADMIN.toString()))
                            UI.getCurrent().navigate(DefaultDashboardView.class);
                        else if (existingUser.getAssignedRole().equalsIgnoreCase(Role.STUDENT.toString()))
                            UI.getCurrent().navigate(DefaultDashboardView.class);
                        else if (existingUser.getAssignedRole().equalsIgnoreCase(Role.JUDGE.toString()))
                            UI.getCurrent().navigate(DefaultDashboardView.class);
                        else if (existingUser.getAssignedRole().equalsIgnoreCase(Role.ORGANIZER.toString()))
                            UI.getCurrent().navigate(DefaultDashboardView.class);
                        else {
                            System.out.println("No role identified, launching default guest");
                            Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST")));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            UI.getCurrent().navigate(DefaultDashboardView.class);
                        }
                    });
                }
            }
        }
    }
}