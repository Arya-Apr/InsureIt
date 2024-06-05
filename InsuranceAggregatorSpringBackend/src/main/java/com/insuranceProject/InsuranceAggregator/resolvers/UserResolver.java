/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.insuranceProject.InsuranceAggregator.resolvers;

import com.insuranceProject.InsuranceAggregator.exceptionHandlers.BadCredsException;
import com.insuranceProject.InsuranceAggregator.exceptionHandlers.UserNotFoundException;
import com.insuranceProject.InsuranceAggregator.inputTypes.CreateUserInput;
import com.insuranceProject.InsuranceAggregator.inputTypes.JwtResponse;
import com.insuranceProject.InsuranceAggregator.inputTypes.UpdateUserInput;
import com.insuranceProject.InsuranceAggregator.models.User;
import com.insuranceProject.InsuranceAggregator.repositories.UserRepository;
import com.insuranceProject.InsuranceAggregator.security.JwtHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author pcran
 */
@Controller
public class UserResolver {

    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, List<User>> kafkaTemplate;

    @Autowired
    public UserResolver(PasswordEncoder passwordEncoder, KafkaTemplate<String, List<User>> kafkaTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserDetailsService userDetailsService;

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Inavlid Username or Password!!");
        }
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public List<User> getUsers() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @MutationMapping
    public JwtResponse login(@Argument String email, @Argument String password) {
        try {
            this.doAuthenticate(email, password);
        } catch (Exception e) {
            throw new BadCredsException("Invalid Username or Password!");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = this.helper.generateToken(userDetails);
        return JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
    }

    @MutationMapping
    public User createUser(@Argument CreateUserInput createUserInput) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(createUserInput.name);
        user.setPassword(passwordEncoder.encode(createUserInput.password));
        user.setRoles(createUserInput.roles);
        user.setAddress(createUserInput.address);
        user.setBank(new ArrayList<>());
        user.setDocument(new ArrayList<>());
        user.setEmail(createUserInput.email);
        user.setProfile(createUserInput.profile);
        user.setAge(createUserInput.age);
        user.setDob(createUserInput.dob);
        user.setGender(createUserInput.gender);
        user.setKyc(Boolean.FALSE);
        user.setDocNumber(createUserInput.docNumber);
        user.setPhoneNumber(createUserInput.phoneNumber);
        return userRepo.save(user);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public User updateUser(@Argument UpdateUserInput updateUserInput) {
        User user = userRepo.findById(updateUserInput.getId()).orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        user.setName(updateUserInput.getName());
        user.setPassword(passwordEncoder.encode(updateUserInput.getPassword()));
        user.setRoles(updateUserInput.getRoles());
        user.setAddress(updateUserInput.getAddress());
        user.setAge(updateUserInput.getAge());
        user.setBank(updateUserInput.getBank());
        user.setDocument(updateUserInput.getDocument());
        user.setDob(updateUserInput.getDob());
        user.setDocNumber(updateUserInput.getDocNumber());
        user.setEmail(updateUserInput.getEmail());
        user.setGender(updateUserInput.getGender());
        user.setProfile(updateUserInput.getProfile());
        user.setPhoneNumber(updateUserInput.getPhoneNumber());
        return userRepo.save(user);
    }
}
