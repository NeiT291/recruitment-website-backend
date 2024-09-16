package org.neit.backend.configuration;

import org.neit.backend.entity.Role;
import org.neit.backend.entity.User;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ApplicationInitConfig {
    private final String[] DEFAULT_ROLES = {"ADMIN","HR", "USER"};
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);
    PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            for(String roles : DEFAULT_ROLES){
                if(!roleRepository.existsByName(roles)){
                    Role role = new Role();
                    role.setName(roles);
                    role.setDescription(roles + " role");
                    roleRepository.save(role);
                    log.warn("Role: " + roles + " created");
                }
            }
            if(!userRepository.existsByUsername("admin")){

                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));


                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByName("ADMIN"));

                user.setRoles(roles);
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
            }
        };
    }

}
