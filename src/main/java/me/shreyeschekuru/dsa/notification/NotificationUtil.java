package me.shreyeschekuru.dsa.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

@CssImport("./styles/notification.css")
public final class NotificationUtil {

    public static void showNotification(String message, String messageType){

        Notification notification = new Notification();
        notification.add(new Text(message));


        switch (messageType.toUpperCase()) {
            case "ERROR":
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                break;
            case "SUCCESS":
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;
            default:
                notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                break;
        }

        notification.setPosition(Notification.Position.TOP_CENTER);


// Set the transition effect
        notification.setDuration(3000); // Duration in milliseconds
        notification.setOpened(true);

        notification.addClassName("centered-notification-text");
        notification.open();
    }
}
