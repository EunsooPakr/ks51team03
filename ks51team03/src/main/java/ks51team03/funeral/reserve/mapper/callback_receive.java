package ks51team03.funeral.reserve.mapper;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class callback_receive {

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public <Payment> ResponseEntity<?> callback_receive(@RequestBody Payment entity){

        // 응답 header 생성
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        JSONPObject responseObj = new JSONPObject("callback_receive", entity);

        try {
            String txId = entity.getTxId();
            String paymentId = entity.getPaymentId();
            String code = entity.getCode();
            String message = entity.getMessage();

            System.out.println("--- after payment receive---");
            System.out.println("----------------------------");
            System.out.println("txId: " + txId);
            System.out.println("paymentId: " + paymentId);
            System.out.println("error_code: " + code);
            System.out.println("error_message: " + message);

            //결제조회 api 를 통해 가맹점이 의도한 금액과 결과금액이 맞는지 검증하는 로직 구현

            String status = doResult(entity);
            responseObj.put("status", status);
        } catch (Exception e){
            e.printStackTrace();
            responseObj.put("status", "처리실패 : 관리자에게 문의해 주세요");
        }

        return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpHeaders.OK)!
    }


}
