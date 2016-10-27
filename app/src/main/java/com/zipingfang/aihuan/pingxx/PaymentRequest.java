package com.zipingfang.aihuan.pingxx;

/**
 * Created by zipingfang on 16/10/20.
 */

public class PaymentRequest {
    String channel;
    String  amount;//ping++金额单位是分 所以需要乘以1oo,用户输入多少就充值多少
    String order_no;
    public PaymentRequest(String channel, String amount,String order_no) {
        this.channel = channel;
        this.amount = amount;
        this.order_no=order_no;
    }
}
