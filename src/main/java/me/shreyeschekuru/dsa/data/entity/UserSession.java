package me.shreyeschekuru.dsa.data.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@SessionScope
public class UserSession implements Serializable {

    private OAuth2AuthorizedClientService authorizedClientService;
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

        return new User(principal.getAttribute("given_name"), principal.getAttribute("family_name"), principal.getAttribute("email"),
                principal.getAttribute("picture"));
    }

    public void assignUserRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
            if (authorizedClient != null) {
                Collection<? extends GrantedAuthority> authorities = oauthToken.getAuthorities();
                Set<String> roles = new HashSet<>(authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
                roles.add(role);
                Set<GrantedAuthority> updatedAuthorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

                Authentication newAuthentication = new OAuth2AuthenticationToken(
                        oauthToken.getPrincipal(), updatedAuthorities, oauthToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            }
        }
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }
}
