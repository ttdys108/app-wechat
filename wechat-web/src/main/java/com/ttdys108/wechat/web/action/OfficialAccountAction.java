package com.ttdys108.wechat.web.action;

import com.ttdys108.commons.utils.SHAUtil;
import com.ttdys108.commons.utils.XMLUtil;
import com.ttdys108.wechat.msg.dto.OfficialAccountMessage;
import com.ttdys108.wechat.msg.service.OfficialAccountMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 微信公众号相关接口
 */
@Slf4j
@RestController
public class OfficialAccountAction {

    @Value("${wechat.official-account-token}")
    private String token;

    @Autowired
    private OfficialAccountMessageService messageService;

    /**
     * 接收微信公众号消息
     * @param signature 签名
     * @param echostr 仅在设置接口时会用到
     * @param timestamp 时间
     * @param nonce nonce
     * @param body 消息
     * @return 消息回复
     */
    @RequestMapping("/api/wx/msg")
    public String wx(@RequestParam String signature,
                     String echostr,
                     @RequestParam String timestamp,
                     @RequestParam String nonce, @RequestBody String body) {
        log.info("signature:{}, echostr:{}, timestamp:{}, nonce:{}, body:{}", signature, echostr, timestamp, nonce, body);
        String[] params = {token, timestamp, nonce};
        String encode = SHAUtil.shaEncode(Arrays.asList(params));

        if(encode.equals(signature)) {
            log.info("signature verified");
            try {
                OfficialAccountMessage message = XMLUtil.unmarshal(body, OfficialAccountMessage.class);
                OfficialAccountMessage reply = messageService.reply(message);
                return XMLUtil.marshal(reply);

            } catch (Exception e) {
                log.error("error occur when handling msg:{}", body, e);
                throw new RuntimeException(e);
            }
            //return echostr; //仅公众号设置时需要
        } else {
            log.info("signature:{} verify failed", signature);
            return "error";
        }
    }




}
