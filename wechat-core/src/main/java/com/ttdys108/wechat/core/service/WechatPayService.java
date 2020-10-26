package com.ttdys108.wechat.core.service;

import com.ttdys108.commons.http.Response;
import com.ttdys108.wechat.core.dto.WechatPayResponse;
import com.ttdys108.wechat.core.utils.WxUtils;
import com.ttdys108.wechat.core.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@Service
public class WechatPayService {


    private String appId = "wxf4b6bcc9cf36662d";
    private String merchantId = "1524561601";
    private String appKey = "xuerenfulixuerenfulixuerenfuliok";
    private String callbackUrl = "http://106.12.139.252:8090/wechatpay/callback";
    private static final String tradeType_H5 = "MWEB";
    private String wechatPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 创建微信支付订单
     * @param amount 单位：分
     * @return
     */
    public Response<String> createOrder(int amount) {
        String orderId = RandomStringUtils.randomAlphanumeric(16);
        log.info("创建订单号<{}>", orderId);
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", appId);
        parameters.put("mch_id", merchantId);
        parameters.put("device_info", tradeType_H5);
        parameters.put("body", "TYUIOP-充值");
        parameters.put("nonce_str", RandomStringUtils.randomNumeric(10)); // 32 位随机字符串
        parameters.put("notify_url", callbackUrl);
        parameters.put("out_trade_no", orderId);
        parameters.put("total_fee", amount);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        parameters.put("trade_type", tradeType_H5);
        parameters.put("sign", WxUtils.createSign(parameters, appKey)); // sign 必须在最后
        try {
            String result = WxUtils.executeHttpPost(wechatPayUrl, parameters); // 执行 HTTP 请求，获取接收的字符串（一段 XML）
            log.info("微信订单<{}>下单成功, 返回<{}>", orderId, result);
            WechatPayResponse resp = XmlUtils.parseXml(result, WechatPayResponse.class);
            return Response.success(result);
        } catch (IOException e) {
            log.error("微信订单<{}>处理失败", orderId, orderId);
            return Response.error();
        }

    }



}
