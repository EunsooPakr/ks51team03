package ks51team03.funeral.payment.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.funeral.payment.dto.PaymentDto;
import ks51team03.funeral.payment.service.PaymentService;
import ks51team03.funeral.serviceList.dto.ServiceListDto;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final MemberServiceImpl memberService;


    @GetMapping("funeral/funeral_confirm_payment")
    public String confirmPayment(HttpSession session, Model model, PaymentDto paymentDto, RedirectAttributes redirectAttributes) {
        String memberId = (String) session.getAttribute("SID");
        log.info("memberId: {}", memberId);

        if(memberId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요한 서비스입니다");
            return "redirect:/";
        }

        paymentDto.setReserveId(memberId);


        List<PaymentDto> paymentDtoList = paymentService.confirmPayment(paymentDto);

        log.info("paymentDtoList: {}", paymentDtoList);
        model.addAttribute("paymentDtoList", paymentDtoList);

        return "funeral/funeral_confirm_payment";
    }

    @GetMapping("funeral/funeral_confirm_payment_detail")
    public String confirmPaymentDetail(@RequestParam(value="fpcode") String fpcode, HttpSession session, Model model, PaymentDto paymentDto) {

        String memberId = (String) session.getAttribute("SID");
        log.info("memberId: {}", memberId);

        if(memberId == null) {
            return "redirect:/";
        }

        PaymentDto paymentDetail = paymentService.getPaymentDetail(fpcode);

        log.info("fpcode: {}", fpcode);

        model.addAttribute("paymentDetail", paymentDetail);

        return "funeral/funeral_confirm_payment_detail";
    }

    @GetMapping("funeral/funeral_Paymented_Service_List_Company")
    public String paymentServiceListCompany(HttpSession session, Model model, PaymentDto paymentDto) {
        String memberId = (String) session.getAttribute("SID");
        String ccode = (String) session.getAttribute("CCODE");
        log.info("memberId: {}", memberId);

        if(memberId == null) {
            return "redirect:/";
        }

        paymentDto.setCcode(ccode);

        List<PaymentDto> getPaymentServiceCompnayList = paymentService.getPaymentServiceCompnay(paymentDto);

        log.info("getPaymentServiceCompnayList: {}", getPaymentServiceCompnayList);
        model.addAttribute("getPaymentServiceCompnayList", getPaymentServiceCompnayList);

        return "/funeral/funeral_Paymented_Service_List_Company";
    }


}
