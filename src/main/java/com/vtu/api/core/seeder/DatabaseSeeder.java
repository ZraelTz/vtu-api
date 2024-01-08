package com.vtu.api.core.seeder;

import com.github.javafaker.Faker;
import com.vtu.api.model.entity.User;
import com.vtu.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        initializeFakeData();
        alreadySetup = true;
    }

    private void initializeFakeData() {
        String myEmail = "zraelwalker@gmail.com";
        if (userRepository.existsByEmail(myEmail)) {
            return;
        }

        Faker faker = Faker.instance(Locale.getDefault());

        //create users
        int userCount = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            User user = User.builder()
                    .email(faker.name().firstName() + (i + 1) + "@example.com")
                    .password(passwordEncoder.encode("$Password123"))
                    .build();

            if (users.stream().noneMatch(persistedUser -> persistedUser.getEmail().equals(myEmail))) {
                user.setEmail(myEmail);
            }

            users.add(userRepository.save(user));
        }

    }


}
