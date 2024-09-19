package org.neit.backend.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import com.nimbusds.jose.shaded.gson.stream.JsonReader;
import org.neit.backend.entity.City;
import org.neit.backend.entity.Role;
import org.neit.backend.entity.User;
import org.neit.backend.repository.CityRepository;
import org.neit.backend.repository.RoleRepository;
import org.neit.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class ApplicationInitConfig {
    private final String[] DEFAULT_ROLES = {"ADMIN","HR", "USER"};
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);
    private final ObjectMapper objectMapper;
    private final String CITY_DATA_JSON = "/data/data-city.json";
    private final PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, CityRepository cityRepository){
        return args -> {
            if(cityRepository.findAll().isEmpty()){
                Type CITY_TYPE = new TypeToken<List<City>>() {
                }.getType();
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/data/data-city.json"));
                List<City> cities = gson.fromJson(reader, CITY_TYPE);
                cityRepository.saveAll(cities);
                log.info("import cities data from json success");
            }

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
