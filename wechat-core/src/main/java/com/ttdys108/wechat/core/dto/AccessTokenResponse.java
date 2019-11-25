package com.ttdys108.wechat.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenResponse {

    private String access_token;
    private Long expires_in;
    private String errcode;
    private String errmsg;

}
