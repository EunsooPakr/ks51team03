package ks51team03.funeral.reserve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReserveController {

    @GetMapping("funeral/funeral_reserve_detail")
    public String reserveDetail() {

        return "funeral/funeral_reserve_detail";
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
