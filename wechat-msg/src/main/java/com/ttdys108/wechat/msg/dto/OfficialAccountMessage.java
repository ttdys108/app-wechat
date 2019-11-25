package com.ttdys108.wechat.msg.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  公众号消息xml
 */
@Getter
@Setter
@ToString
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfficialAccountMessage {

    @XmlElement(name = "ToUserName")
    private String toUserName;
    @XmlElement(name = "FromUserName")
    private String fromUserName;
    @XmlElement(name = "Content")
    private String content;
    @XmlElement(name = "CreateTime")
    private Long createTime;
    @XmlElement(name = "MsgType")
    private String msgType;
    @XmlElement(name = "MsgId")
    private String msgId;


}
