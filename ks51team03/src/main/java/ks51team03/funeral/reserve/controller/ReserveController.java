package ks51team03.funeral.reserve.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.funeral.payment.dto.PaymentDto;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveInfoDto;
import ks51team03.funeral.reserve.dto.ReservePaymentDto;
import ks51team03.funeral.reserve.dto.ReserveServiceInfoDto;
import ks51team03.funeral.reserve.service.ReserveService;
import ks51team03.funeral.serviceList.service.ServiceListService;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReserveController {

    private final ServiceListService serviceListService;
    private final ReserveService reserveService;
    private final MemberServiceImpl memberService;

    // 결제 콜백 처리
    @PostMapping("/reserve/callback")
    public ResponseEntity<?> callbackReceive(@RequestBody ReservePaymentDto reservePaymentDto) {

        log.info("Received payment callBack: {}", reservePaymentDto);

        try {
            String txId = reservePaymentDto.getTxId();
            String code = reservePaymentDto.getCode();
            String message = reservePaymentDto.getMessage();

            log.info("--- after payment receive ---");
            log.info("txId: {}", txId);
            log.info("error_code: {}", code);
            log.info("error_message: {}", message);

            // 결제 조회 API를 통해 가맹점이 의도한 금액과 결과 금액이 같은지 검증단계
            reserveService.handlePaymentCallback(reservePaymentDto);

            log.info("여기는 handlePaymentCallback 받는 곳");
            
            log.info("reserveService.handlePaymentCallback(reservePaymentDto): {}", reservePaymentDto);

            // 정상 처리 응답
            Map<String, String> response = new HashMap<>();
            response.put("status", "성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Exception during payment callback processing", e);

            // 예외 처리 응답
            Map<String, String> response = new HashMap<>();
            response.put("status", "실패");
            response.put("message", "처리 실패 : 송중기에게 문의해 주세요");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("funeral/funeral_reserve_info")
    public String reserveInfo(Model model, HttpSession session, ReserveInfoDto reserveInfoDto, ReserveServiceInfoDto reserveServiceInfoDto) {
            String memberId = (String) session.getAttribute("SID");

        log.info("장례 예약한 회원 아이디 memberId = {}", memberId);

        log.info("여기는 멤버아이디 나오는 곳");

        //회원 정보 조회
        Member member = memberService.getMemberInfoById(memberId);
        String reservePhone = member.getMemberPhone();

        reserveInfoDto.setReserveId(memberId);
        reserveInfoDto.setReservePhone(reservePhone);

        reserveServiceInfoDto.setReserveId(memberId);

        List<ReserveInfoDto> reserveInfoList = reserveService.funeralReserveInfo(reserveInfoDto);
        List<ReserveServiceInfoDto> reserveServiceInfoDtoList = reserveService.funeralReserveServiceInfo(reserveServiceInfoDto);

        log.info("reserveInfoList = {}", reserveInfoList);
        log.info("reserveServiceInfoDtoList = {}", reserveServiceInfoDtoList);
        model.addAttribute("reserveInfoList", reserveInfoList);
        model.addAttribute("reserveServiceInfoDtoList", reserveServiceInfoDtoList);

        return "funeral/funeral_reserve_info";
    }

    @GetMapping("funeral/funeral_reserve_payment")
    public String reservePayment(Model model, HttpSession session, ReserveInfoDto reserveInfoDto, ReserveServiceInfoDto reserveServiceInfoDto) {



        String memberId = (String) session.getAttribute("SID");

        if(memberId == null) {
            return "redirect:/member/member_login";
        }

        log.info("장례 예약한 회원 아이디 memberId = {}", memberId);

        log.info("여기는 멤버아이디 나오는 곳");

        //회원 정보 조회
        Member member = memberService.getMemberInfoById(memberId);
        String reservePhone = member.getMemberPhone();

        reserveInfoDto.setReserveId(memberId);
        reserveInfoDto.setReservePhone(reservePhone);

        reserveServiceInfoDto.setReserveId(memberId);

        List<ReserveInfoDto> reserveInfoList = reserveService.funeralReserveInfo(reserveInfoDto);
        List<ReserveServiceInfoDto> reserveServiceInfoDtoList = reserveService.funeralReserveServiceInfo(reserveServiceInfoDto);

        log.info("reserveInfoList = {}", reserveInfoList);
        log.info("reserveServiceInfoDtoList = {}", reserveServiceInfoDtoList);
        model.addAttribute("reserveInfoList", reserveInfoList);
        model.addAttribute("reserveServiceInfoDtoList", reserveServiceInfoDtoList);

        // fpcode 생성
        String fpcode = reserveService.generateFpcode();
        model.addAttribute("fpcode", fpcode);

        log.info("fpcode = {}", fpcode);

        // 기타 필요한 데이터 설정
        model.addAttribute("memberId", memberId);
        model.addAttribute("reservePhone", reservePhone);

        return "funeral/funeral_reserve_payment";
    }

    @GetMapping("funeral/funeral_reserve_confirm")
    public String reserveConfirm() {

        return "funeral/funeral_reserve_confirm";
    }


}
