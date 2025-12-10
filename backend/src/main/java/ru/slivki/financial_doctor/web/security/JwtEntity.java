package ru.slivki.financial_doctor.web.security;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtEntity implements UserDetails {
    final String email;
    final String name;
    final String password;
    final Collection<? extends GrantedAuthority> authorities;
    Long id;

    public JwtEntity(Long id, String email, String name, String password, List<GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = grantedAuthorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}