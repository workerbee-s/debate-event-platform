package me.shreyeschekuru.dsa.data;

public enum Role {
    USER("User"),
    ADMIN("Admin"),
    JUDGE("Judge"),
    ORGANIZER("Organizer"),
    STUDENT("Student"),
    PARTICIPANT("Participant");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}