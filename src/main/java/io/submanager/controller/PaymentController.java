package io.submanager.controller;

import io.submanager.converter.PaymentConverter;
import io.submanager.converter.PaymentNotificationConverter;
import io.submanager.model.PaymentNotificationResponse;
import io.submanager.model.PaymentResponse;
import io.submanager.model.UpdatePaymentRequest;
import io.submanager.model.entity.Payment;
import io.submanager.model.entity.PaymentNotification;
import io.submanager.model.entity.User;
import io.submanager.service.PaymentNotificationService;
import io.submanager.service.PaymentService;
import io.submanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentNotificationService paymentNotificationService;

    @Autowired
    private PaymentConverter paymentConverter;

    @Autowired
    private PaymentNotificationConverter paymentNotificationConverter;

    @PutMapping
    public ResponseEntity<Payment> updatePayment(Principal principal, @RequestBody UpdatePaymentRequest request) {

        User user = userService.getUser(principal);
        Optional<Payment> maybePayment = paymentService.getByIdAndOwnerId(request.getPaymentId(), user.getId());

        if (maybePayment.isEmpty()) {
            // TODO defined exceptions
            throw new RuntimeException("Payment does not exists");
        }

        Payment payment = maybePayment.get();
        payment.setStatus(request.getStatus());
        payment = paymentService.update(payment);

        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getDuePayments(Principal principal) {
        User user = userService.getUser(principal);
        List<Payment> payments = paymentService.getAllDueBySubscriberUserId(user.getId());
        List<PaymentResponse> paymentResponses = payments.stream()
                .map(paymentConverter::fromPayment)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentResponses);
    }

    @GetMapping("/notification")
    public ResponseEntity<List<PaymentNotificationResponse>> getPaymentNotifications(Principal principal) {
        User user = userService.getUser(principal);
        List<PaymentNotification> notifications = paymentNotificationService.getAllByUserId(user.getId());

        List<PaymentNotificationResponse> notificationResponses = notifications.stream()
                .map(paymentNotificationConverter::fromPaymentNotification)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notificationResponses);
    }
}
