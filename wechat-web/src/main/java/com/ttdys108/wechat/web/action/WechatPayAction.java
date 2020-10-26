package com.ttdys108.wechat.web.action;

import com.ttdys108.commons.http.Response;
import com.ttdys108.wechat.core.service.WechatPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WechatPayAction {

    @Autowired
    private WechatPayService wechatPayService;

    @PostMapping("/wxpay/test")
    public Response<String> testPay() {
        return wechatPayService.createOrder(1);
    }


}
