package ks51team03.funeral.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("funeral/funeral_confirm_payment")
    public String confirmPayment() {

        return "funeral/funeral_confirm_payment";
    }

    @GetMapping("funeral/funeral_confirm_payment_detail")
    public String confirmPaymentDetail() {

        return "funeral/funeral_confirm_payment_detail";
    }
}
