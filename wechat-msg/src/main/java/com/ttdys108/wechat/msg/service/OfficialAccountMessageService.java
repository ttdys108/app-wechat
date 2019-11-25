package com.ttdys108.wechat.msg.service;

import com.ttdys108.wechat.msg.dto.OfficialAccountMessage;
import org.springframework.stereotype.Component;

@Component
public class OfficialAccountMessageService {

    /**
     * 回复公众号消息
     * @param message 接收的消息
     * @return 回复
     */
    public OfficialAccountMessage reply(OfficialAccountMessage message) {
        OfficialAccountMessage replyMsg = new OfficialAccountMessage();
        replyMsg.setMsgType(message.getMsgType());
        replyMsg.setContent(message.getContent());
        replyMsg.setCreateTime(System.currentTimeMillis()/1000);
        replyMsg.setFromUserName(message.getToUserName());
        replyMsg.setToUserName(message.getFromUserName());
        return replyMsg;
    }

}
