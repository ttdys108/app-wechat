package com.ttdys108.wechat.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {
    private String token;
    /** 有效时间 unix时间戳 */
    private Long expires;

    public AccessToken() {}

    public AccessToken(String token, Long expires) {
        this.token = token;
        this.expires = expires;
    }

}
