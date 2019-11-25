package com.ttdys108.wechat.web.action;

import com.ttdys108.commons.exception.ServiceException;
import com.ttdys108.commons.http.Response;
import com.ttdys108.wechat.core.dto.AccessToken;
import com.ttdys108.wechat.core.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainAction {

    @Autowired
    private AccessTokenService tokenService;

    @GetMapping("/api/wx/token")
    public Response<AccessToken> getAccessToken() {
        try {
            AccessToken token = tokenService.getAccessToken();
            return Response.success(token);
        } catch (ServiceException e) {
            return new Response<>(e.getCode(), e.getMsg());
        }
    }



}
