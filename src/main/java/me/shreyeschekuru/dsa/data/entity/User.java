package me.shreyeschekuru.dsa.data.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Entity
@Table(name = "app_user")
public class User implements UserDetails{

    public User(String firstName, String lastName, String email, String profilePicture, boolean emailVerified, boolean passwordApplicable, String oAuthProvider){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePicture = profilePicture;
        this.emailVerified = emailVerified;
        this.passwordApplicable = passwordApplicable;
        this.oAuthProvider = oAuthProvider;
    }

    public User(String firstName, String lastName, String email, String profilePicture){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePicture = profilePicture;
    }
    public User(){}

    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;

    @NotNull(message = "First Name cannot be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last Name cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email address")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Username cannot be empty")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "mobile_verified")
    private boolean mobileVerified;

    @Column(name = "oauth_provider")
    private String oAuthProvider;

    @Column(name = "password_applicable")
    private boolean passwordApplicable;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "assigned_role")
    private String assignedRole;

    @Column(name = "locked")
    private Boolean locked = false;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getoAuthProvider() {
        return oAuthProvider;
    }

    public void setoAuthProvider(String oAuthProvider) {
        this.oAuthProvider = oAuthProvider;
    }

    public boolean isPasswordApplicable() {
        return passwordApplicable;
    }

    public void setPasswordApplicable(boolean passwordApplicable) {
        this.passwordApplicable = passwordApplicable;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getAssignedRole() {
        return assignedRole;
    }

    public void setAssignedRole(String assignedRole) {
        this.assignedRole = assignedRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return emailVerified == user.emailVerified && mobileVerified == user.mobileVerified && passwordApplicable == user.passwordApplicable && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(mobile, user.mobile) && Objects.equals(oAuthProvider, user.oAuthProvider) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && role == user.role && Objects.equals(assignedRole, user.assignedRole) && Objects.equals(locked, user.locked) && Objects.equals(enabled, user.enabled) && Objects.equals(profilePicture, user.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, username, password, mobile, emailVerified, mobileVerified, oAuthProvider, passwordApplicable, createdAt, updatedAt, role, assignedRole, locked, enabled, profilePicture);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", emailVerified=" + emailVerified +
                ", mobileVerified=" + mobileVerified +
                ", oAuthProvider='" + oAuthProvider + '\'' +
                ", passwordApplicable=" + passwordApplicable +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
                ", assignedRole='" + assignedRole + '\'' +
                ", locked=" + locked +
                ", enabled=" + enabled +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
