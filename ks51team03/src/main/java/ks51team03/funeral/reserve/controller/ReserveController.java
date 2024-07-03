package ks51team03.funeral.reserve.controller;

import ks51team03.funeral.reserve.dto.ReserveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReserveController {

    @PostMapping("/funeral/funeral_info")
    public String reserveInfo(ReserveDto.Funeral_reserve reserve){

        log.info("장례예약 reserve:{}", reserve);


        return "funeral/funeral_reserve_payment";
    }



    @GetMapping("funeral/funeral_reserve_info")
    public String reserveInfo() {

        return "funeral/funeral_reserve_info";
    }

    @GetMapping("funeral/funeral_reserve_payment")
    public String reservePayment() {

        return "funeral/funeral_reserve_payment";
    }

    @GetMapping("funeral/funeral_reserve_confirm")
    public String reserveConfirm() {

        return "funeral/funeral_reserve_confirm";
    }


}
