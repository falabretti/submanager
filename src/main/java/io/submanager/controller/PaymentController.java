package io.submanager.controller;

import io.submanager.model.UpdatePaymentRequest;
import io.submanager.model.entity.Payment;
import io.submanager.model.entity.User;
import io.submanager.service.PaymentService;
import io.submanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

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
}
