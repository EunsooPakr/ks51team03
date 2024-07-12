package ks51team03.funeral.payment.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.funeral.payment.dto.PaymentDto;
import ks51team03.funeral.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("funeral/funeral_confirm_payment")
    public String confirmPayment(HttpSession session, Model model, PaymentDto paymentDto) {
        String memberId = (String) session.getAttribute("SID");
        log.info("memberId: {}", memberId);

        if(memberId == null) {
            return "redirect:/member/member_main";
        }

        List<PaymentDto> paymentDtoList = paymentService.confirmPayment(paymentDto);

        log.info("paymentDtoList: {}", paymentDtoList);
        model.addAttribute("paymentDtoList", paymentDtoList);

        return "funeral/funeral_confirm_payment";
    }

    @GetMapping("funeral/funeral_confirm_payment_detail")
    public String confirmPaymentDetail() {

        return "funeral/funeral_confirm_payment_detail";
    }
}
