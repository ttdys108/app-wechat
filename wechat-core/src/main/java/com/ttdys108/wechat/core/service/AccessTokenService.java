package com.ttdys108.wechat.core.service;

import com.alibaba.fastjson.JSONObject;
import com.ttdys108.commons.exception.ServiceException;
import com.ttdys108.wechat.core.dto.AccessToken;
import com.ttdys108.wechat.core.dto.AccessTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AccessTokenService {

    @Value("${wechat.app-id}")
    private String appId;
    @Value("${wechat.app-secret}")
    private String appSecret;
    @Value("${wechat.access-token-gateway}")
    private String gateway;
    @Value("${redis-key.access-token}")
    private String redisTokenKey;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String grant_type = "client_credential";


    public AccessToken getAccessToken() throws ServiceException {
        String preToken = redisTemplate.opsForValue().get(redisTokenKey);
        if(preToken != null) {
            log.info("get token from cache:{}", preToken);
            return JSONObject.parseObject(preToken, AccessToken.class);
        }

        try {
            HttpClient client = HttpClients.createDefault();
            String url = gateway + "?grant_type=" + grant_type + "&appid=" + appId + "&secret=" + appSecret;
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String data = EntityUtils.toString(response.getEntity());
            log.info("acquire access token resp:{}", data);
            AccessTokenResponse tokenResponse = JSONObject.parseObject(data, AccessTokenResponse.class);
            if(StringUtils.isEmpty(tokenResponse.getAccess_token()))
                throw new ServiceException(tokenResponse.getErrcode(), tokenResponse.getErrmsg());

            AccessToken accessToken = new AccessToken();
            accessToken.setToken(tokenResponse.getAccess_token());
            accessToken.setExpires(System.currentTimeMillis()/1000 + tokenResponse.getExpires_in() - 5);
            //存入redis
            redisTemplate.opsForValue().set(redisTokenKey, JSONObject.toJSONString(accessToken), tokenResponse.getExpires_in() - 5, TimeUnit.SECONDS);
            return accessToken;
        } catch (IOException e) {
            log.error("error occur when get access token", e);
            throw ServiceException.sysError(e);
        }
    }

}
