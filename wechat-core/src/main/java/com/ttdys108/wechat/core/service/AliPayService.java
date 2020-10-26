package com.ttdys108.wechat.core.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.ttdys108.commons.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;


import static com.alipay.api.AlipayConstants.APP_ID;

@Slf4j
@Service
public class AliPayService {

    private String appId = "2019031063530035";
    private String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1lLq8lqRbsiMRs0+P3j41rasrWJ3u6tFzAA3c7Ms7SjjwzY/MnP0F5estG5iieTBimRJ8SPPnMM1HGhPHsvEb9/pURL8LM967EvX7ziiI8FjLL9qJfBbkd4ylK6gGUpvcMAYO1ZcX92rWiJZdzV8MFt9GbiUoU0r9SMagsyjkU9PIcOQBrEsj5dpOjrOOL1scvUMrbJkTlPBpj9e7WGA/Z4CZaa3YISextwWnj3hiurxNb58zww2mziXhSJ/hvwpP4WQSyPAEPILgiDUqJFJcxCXmT3oJkYj5DM1vrMfOkCnuSYw+R5RmGSbXunov0tkx41YVLo3DBhW6agudNsHcwIDAQAB";
    private String priKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDWUuryWpFuyIxGzT4/ePjWtqytYne7q0XMADdzsyztKOPDNj8yc/QXl6y0bmKJ5MGKZEnxI8+cwzUcaE8ey8Rv3+lREvwsz3rsS9fvOKIjwWMsv2ol8FuR3jKUrqAZSm9wwBg7Vlxf3ataIll3NXwwW30ZuJShTSv1IxqCzKORT08hw5AGsSyPl2k6Os44vWxy9QytsmROU8GmP17tYYD9ngJlprdghJ7G3BaePeGK6vE1vnzPDDabOJeFIn+G/Ck/hZBLI8AQ8guCINSokUlzEJeZPegmRiPkMzW+sx86QKe5JjD5HlGYZJte6ei/S2THjVhUujcMGFbpqC502wdzAgMBAAECggEAaLHMj2LqlCFnVNbBx6PCerB1naTyNDywNb0NWXl3QsqsqBmBuZWm8TdoaCNcA1w5pu7ip+cEbeF1ulDCBZsodDX+9Vm+0ezwcSmDbBpQKgM3/Q3sPmgxcwoFz1eWYYRY98z+iU3ghfv0zmzlIzR/W+f1LnFaOYlHJdJmCusII9yizFJBNjVALBZzQp9uZWSxIR//dSjZt9/3faLMTGPd9Tq3lmLjVkfw1wDFFXpq/BFNlVYcoJYkOUnqaMt4t1WALxMwkPyv3aXAJ+pMluFhkmO7wYBiZ9QV5/fnEo+YBDFRn+aJbBsQJApfbOdP9nWkuumNOw45+X8EfF05l09BgQKBgQD5t7vtq4FBUrk1lk3mWpE4T+we/atE88fuTR67qM40xlfbzeltx1qYsTL9bor6QZPs7OXVKR537gtTeptJUF8Q2g94mBaT0ciFBUZ5Vy7SKBpDU3qd3+2sMU1v0fo7iVaQYrJOn+Pq4oUGBHz3p6JmpUGs0kuxEeL5m1iU79XekwKBgQDbtzxRosSmmeDYhQV6lMGO4fNJB8dqUrZO3YPoJ/6bpPbyZhPLvXEq7MkZhStgA4c02aY+xsWtBlYxvUyFd90F75DeI6HWRALbF+bHfDK5J2IjCUKcdgx8p/3qDJdIk57cjb07xrp0iRrXfXXXyxa6KMU/ZFZVbMImqapP9qjfoQKBgQDX64mgcOobbIbeke8bnfXxRhH5ngdoYI3gPGi1hLmAInw3f602EekBHD7lqZ6b0vYwJ4p3LM2+j2LVtK7uzoLU1GgKMx9ag0sp0azlIIBIkQpcaqMcPVKJ0DuP03TV4+Ooht58Y340S9cPo/PWQ+mFbaEC6PcvzTJ/91W5ukMn5wKBgA7TMEi52Nb87wTtBM+elejUKhw1YCk2Z4Kuw6W4fR93ZAo7BP7sS2SAEZmdOK4Dzu2r+eGme9QO52X3xeADlHBAF+BFOzg/4olx9WpkYO/JTn/ashnohTMsCp/uNY76Fu/+yFPuNsqCLcN4fakP1Pq+oYCDfqfI7Ni6/fjjRcXBAoGAfSIliN9D/22iE8AcoovdexMP871HgOMtJoZggONlUHnxyXP/+32AFswZkC9mo9BWVQeWNg+3EUDdWIFgew1KqK6lW2EW0SEhwIQYoxTxW3Hf9/AACrD8QROGbQW+Mk3ejimqwPVoRLWgusR2EM4So3+eKhyZx1+XBEMItwD6uX8=";
    private String partnerId = "2088431121415815";
    private String callbackUrl = "http://106.12.139.252:8090/wechatpay/callback";
    private String returnUrl = "http://106.12.139.252:8090/wechatpay/return";
    private String quitUrl = "http://106.12.139.252:8090/wechatpay/return";

    public Response<String> createOrder() {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                appId, priKey, "json", "UTF-8", pubKey, "RSA2"); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(callbackUrl);//在公共参数中设置回跳和通知地址
        String orderNo = RandomStringUtils.randomAlphanumeric(32);
        log.info("创建订单<{}>", orderNo);
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\""+orderNo+"\"," +
                " \"total_amount\":0.01," +
                " \"subject\":\"Iphone6 16G\"," +
                " \"quit_url\":\""+quitUrl+"\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.info("创建订单<{}>成功，返回<{}>", orderNo, form);
        return Response.success(form);


    }




}
