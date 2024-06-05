/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services;

import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.SignUpForm;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author pcran
 */
@Service
public class UserService {

    private final HttpGraphQlClient graphqlClient;

    public UserService() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8080/graphql").build();
        graphqlClient = HttpGraphQlClient.builder(client).build();
    }

    public Mono<List<User>> getUsers(HttpServletRequest request) {
        String document = """
                query MyQuery {
                  getUsers {
                    _id
                    address
                    age
                    bank
                    dob
                    docNumber
                    document
                    email
                    gender
                    id
                    name
                    password
                    phoneNumber
                    profile
                    roles
                  }
                }
                """;
        Cookie[] cookies = request.getCookies();
        String jwtToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }
        if (!jwtToken.isEmpty()) {
            return graphqlClient.mutate().header("Authorization", "Bearer " + jwtToken).build().document(document).retrieve("getUsers").toEntityList(User.class);
        } else {
            throw new RuntimeException("User Not Logged In!");
        }
    }

    public Mono<String> login(String username, String password, HttpServletResponse httpResponse) {
        String document = """
                          mutation MyMutation($email: String!, $password: String!) {
                            login(email: $email, password: $password) {
                              jwtToken
                              username
                            }
                          }
                          """;
        Mono<ClientGraphQlResponse> res = graphqlClient.document(document).variable("email", username).variable("password", password).execute();
        return res.doOnNext(resp -> {
            LinkedHashMap<String, Object> response = resp.getData();
            LinkedHashMap<String, Object> login = (LinkedHashMap<String, Object>) response.getOrDefault("data", response);
            LinkedHashMap<String, Object> main = (LinkedHashMap<String, Object>) login.getOrDefault("login", response);
            String token = (String) main.get("jwtToken");
            String uname = (String) main.get("username");
            if (token != null && uname != null) {
                Cookie jwtCookie = new Cookie("jwtToken", token);
                Cookie usernameCookie = new Cookie("username", uname);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setPath("/");
                usernameCookie.setHttpOnly(true);
                usernameCookie.setPath("/");
                httpResponse.addCookie(jwtCookie);
                httpResponse.addCookie(usernameCookie);
            }
        }).thenReturn("redirect:/");
    }

    public Mono<String> signUp(SignUpForm signUpForm, ArrayList<String> selectedRoles, Model model) {
        System.err.println(selectedRoles);
        System.err.println(signUpForm.getProfile());
        return Mono.empty().thenReturn("redirect:/login");
    }
}
