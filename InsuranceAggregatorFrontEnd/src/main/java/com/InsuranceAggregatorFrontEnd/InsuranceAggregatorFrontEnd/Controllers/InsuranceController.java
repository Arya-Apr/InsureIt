/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.Controllers;

import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.LoginForm;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.Policy;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.SignUpForm;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.StripeRequest;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.User;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services.InsuranceProviderService;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services.PaymentService;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services.PolicyService;
import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

/**
 *
 * @author pcran
 */
@Controller
@Slf4j
public class InsuranceController {

    private UserService userService;
    private PaymentService paymentService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private InsuranceProviderService providerService;

    public InsuranceController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @GetMapping({"/", "/index"})
    public Mono<String> showHome(HttpServletRequest request, Model model) {
        try {
            model.addAttribute("hi", "This is from the controller using a variable");
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        if (!cookie.getValue().isEmpty()) {
                            model.addAttribute("token", true);
                            break;
                        } else {
                            model.addAttribute("token", false);
                            break;
                        }
                    } else {
                        model.addAttribute("token", false);
                    }
                }
            }
            Mono<List<Policy>> response = policyService.getPolicies();
            return response.doOnNext(resp -> {
                model.addAttribute("policies", resp);
            }).thenReturn("index");
        } catch (Exception e) {
            return Mono.empty().thenReturn("index");
        }
    }

    @GetMapping({"/policy/{id}"})
    public Mono<String> getPolicyView(@PathVariable(name = "id") String id, HttpServletRequest request, Model model) {
        Mono<List<Policy>> response = policyService.getPolicies();
        Mono<Policy> res = policyService.getPolicyById(id);
        return Mono.zip(response, res, (policies, policy) -> {
            model.addAttribute("policies", policies);
            model.addAttribute("policy", policy);
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        if (!cookie.getValue().isEmpty()) {
                            model.addAttribute("token", true);
                            break;
                        } else {
                            model.addAttribute("token", false);
                            break;
                        }
                    } else {
                        model.addAttribute("token", false);
                    }
                }
            }
            return "policy";
            // Don't need the final thenReturn("policy") here
        });
    }

    @GetMapping({"/insurances"})
    public String showInsurances(Model model) {
        return "insurances";
    }

    @GetMapping({"/login"})
    public Mono<String> showLogin(HttpServletRequest request, Model model) {
        try {
            model.addAttribute("loginForm", new LoginForm());
            Mono<List<Policy>> response = policyService.getPolicies();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        if (!cookie.getValue().isEmpty()) {
                            model.addAttribute("token", true);
                            break;
                        } else {
                            model.addAttribute("token", false);
                            break;
                        }
                    } else {
                        model.addAttribute("token", false);
                    }
                }
            }
            return response.doOnNext(resp -> {
                model.addAttribute("policies", resp);
            }).thenReturn("login");
        } catch (Exception e) {
            return Mono.empty().thenReturn("index");
        }
    }

    @GetMapping({"/signUp"})
    public Mono<String> getSignUp(HttpServletRequest request, Model model) {
        try {
            model.addAttribute("signupForm", new SignUpForm());
            model.addAttribute("selectedRoles", new ArrayList<String>());
            Mono<List<Policy>> response = policyService.getPolicies();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        if (!cookie.getValue().isEmpty()) {
                            model.addAttribute("token", true);
                            break;
                        } else {
                            model.addAttribute("token", false);
                            break;
                        }
                    } else {
                        model.addAttribute("token", false);
                    }
                }
            }
            return response.doOnNext(resp -> {
                model.addAttribute("policies", resp);
            }).thenReturn("signup");
        } catch (Exception e) {
            return Mono.empty().thenReturn("index");
        }
    }

    @GetMapping({"/logout"})
    public String logout(HttpServletResponse response
    ) {
        Cookie cookie = new Cookie("jwtToken", null);
        Cookie cookie1 = new Cookie("username", null);
        response.addCookie(cookie);
        response.addCookie(cookie1);
        return "redirect:/";
    }

    @PostMapping({"/login"})
    public Mono<String> handleLogin(@ModelAttribute LoginForm loginForm, HttpServletResponse response,
            Model model
    ) {
        return userService.login(loginForm.getUsername(), loginForm.getPassword(), response);
    }

    @PostMapping({"/signup"})
    public Mono<String> handleSignUp(@ModelAttribute SignUpForm signUpForm, @ModelAttribute("selectedRoles") ArrayList<String> selectedRoles, Model model) {
        return userService.signUp(signUpForm, selectedRoles, model);
    }

    @GetMapping({"/users"})
    public Mono<String> user(HttpServletRequest request, Model model
    ) {
        try {
            Mono<List<User>> users = userService.getUsers(request);
            return users.doOnNext(response -> {
                model.addAttribute("members", response);
            }).thenReturn("users");
        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
            Map<String, Object> errorModel = new HashMap<>();
            errorModel.put("errorMessage", e.getMessage());
            errorModel.put("hi", "a hi message on error page");
            return Mono.empty().thenReturn("redirect:/error");
        }
    }

    @GetMapping({"/error"})
    public String handleError(Model model
    ) {
        System.err.println("Error View Was Called...............");
        String errorMessage = (String) model.asMap().get("errorMessage");
        String hiMessage = (String) model.asMap().get("hi");
        System.err.println(hiMessage);
        System.err.println(errorMessage);
        //the error handler is currently not working, need to fix this
        // Use these attributes to populate the error template
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("hi", hiMessage);
        model.addAttribute("hi1", "hi1 message");
        return "error";
    }

    @GetMapping({"/payment"})
    public String payment(Model model
    ) {
        model.addAttribute("request", new StripeRequest());
        //payment will have a form to input the stripe request data
        return "payment";
    }

    @PostMapping({"/payment"})
    public Mono<String> showCard(@ModelAttribute StripeRequest stripeRequest, BindingResult bindingResult,
            HttpServletRequest request, Model model
    ) {
        if (bindingResult.hasErrors()) {
            return Mono.empty().thenReturn("payment");
        }
        return this.paymentService.handlePayment(stripeRequest, request, model);
    }
}
