package io.submanager.controller;

import io.submanager.model.PaymentSchedulerTestRequest;
import io.submanager.schedule.PaymentNotificationScheduler;
import io.submanager.schedule.PaymentScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private PaymentScheduler paymentScheduler;

    @Autowired
    private PaymentNotificationScheduler paymentNotificationScheduler;

    @GetMapping(path = "/public")
    public String testPublicAccess() {
        return "Hello World from public endpoint!";
    }

    @PostMapping(path = "/public/payment")
    public void testPaymentScheduler(@RequestBody PaymentSchedulerTestRequest request) {
        paymentScheduler.updatePayments(request.getPeriodicity(), request.getPeriod());
    }

    @PostMapping(path = "/public/payment/notification")
    public void testPaymentNotificationSchedule() {
        paymentNotificationScheduler.createPaymentNotifications();
    }

    @GetMapping(path = "/private")
    public String testPrivateAccess(Principal principal) {
        String email = principal.getName();
        return "Hello World from private endpoint! " +email;
    }
}
