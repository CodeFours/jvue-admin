package net.ccfish.jvue.service.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.ccfish.jvue.model.JvueRole;
import net.ccfish.jvue.model.User;
import net.ccfish.jvue.repository.UserRepository;
import net.ccfish.jvue.security.JwtTalkUser;

/**
 * 用户验证方法
 * 
 * @author 袁贵
 * @version 1.0
 * @since 1.0
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public JwtUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("No user found with username '%s'.", username));
        } else {
            
            Collection<GrantedAuthority> authorities = loadGrantedRoles(user.getRoles());
            
            return new JwtTalkUser(user.getUsername(), user.getPassword(), authorities);
        }
    }

    /**
     * @param roles
     * @return
     * @since  1.0
     */
    private Collection<GrantedAuthority> loadGrantedRoles(Set<JvueRole> roles) {
        // TODO Auto-generated method stub
        return null;
    }

}
