package com.example.airlines_tickets_reservation.user;

import com.example.airlines_tickets_reservation.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//creating a new user is implemented in auth package
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    public User findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("user not found with this id"));

        if (isAdmin()) {
            return user;
        }

        if (doesUsernameMatchTheToken(user)) {
            return user;
        } else {
            throw new AccessDeniedException("you can't access other users information");
        }
    }

    public User alterUser(Integer id, RegisterRequest changedUser) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("username not found"));

        if (isAdmin()) {
            return updateUserInfo(user, changedUser);
        }

        if (doesUsernameMatchTheToken(user)) {
            return updateUserInfo(user, changedUser);
        } else {
            throw new AccessDeniedException("you can't alter other users information");
        }
    }

    private User updateUserInfo(User user, RegisterRequest changedUser) {
        user.setFullname(changedUser.getFullname());
        if(!userRepository.existsByEmail(changedUser.getEmail())){
            user.setEmail(changedUser.getEmail());
        }else{
            throw new IllegalArgumentException("email is already taken");
        }
        user.setPassword(changedUser.getPassword());

        userRepository.save(user);
        return user;
    }

    public void deleteById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("username not found"));

        if (isAdmin()) {
            userRepository.deleteById(id);
            return;
        }

        if (doesUsernameMatchTheToken(user)) {
            userRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("you can't delete another user");
        }
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
    }

    public boolean doesUsernameMatchTheToken(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName().equals(user.getUsername());
    }
}
