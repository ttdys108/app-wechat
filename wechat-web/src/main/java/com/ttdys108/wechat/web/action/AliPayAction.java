package com.ttdys108.wechat.web.action;

import com.ttdys108.commons.http.Response;
import com.ttdys108.wechat.core.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AliPayAction {

    @Autowired
    private AliPayService aliPayService;

    @PostMapping("/alipay/test")
    public Response<String> test() {

        return aliPayService.createOrder();
    }

}
