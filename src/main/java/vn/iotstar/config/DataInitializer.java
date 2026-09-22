package vn.iotstar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${ADMIN_EMAIL:admin@gmail.com}")
            String adminEmail,
            @Value("${ADMIN_PASSWORD:123456}")
            String adminPassword) {

        return args -> {
            roleRepository.findByNameIgnoreCase("USER")
                .orElseGet(() ->
                    roleRepository.save(new Role("USER"))
                );

            Role adminRole =
                roleRepository.findByNameIgnoreCase("ADMIN")
                    .orElseGet(() ->
                        roleRepository.save(new Role("ADMIN"))
                    );

            if (!userRepository.existsByEmailIgnoreCase(adminEmail)) {
                User admin = new User();
                admin.setEmail(adminEmail.toLowerCase());
                admin.setFullName("System Administrator");
                admin.setPassword(
                    passwordEncoder.encode(adminPassword)
                );
                admin.setEnabled(true);
                admin.setRole(adminRole);

                userRepository.save(admin);
            }
        };
    }
}