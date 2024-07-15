package ks51team03.funeral.reserve.entity;

import lombok.Data;

@Data
public class Payment {

    private String txId;
    private String paymentId;
    private String code;
    private String message;
}
