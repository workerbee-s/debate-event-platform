package me.shreyeschekuru.dsa.utilities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class UIUtility {

    public static Button getButtonByName(Tabs tabs, String buttonName) {
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

    public static void printGrantedAuthorities(Authentication authentication){
      //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                // role contains the role of the current user
                System.out.println("Current user role: " + role);
            }
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Password hashing failed");
        }
    }
}
