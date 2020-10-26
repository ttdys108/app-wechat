package com.ttdys108.wechat.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

@Service
public class WxUtils {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 执行 POST 方法的 HTTP 请求
     *
     * @param url
     * @param parameters
     * @return
     * @throws IOException
     */
    public static String executeHttpPost(String url, SortedMap<String, Object> parameters) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-type", "application/xml");
        request.setHeader("Accept", "application/xml");
        request.setEntity(new StringEntity(XmlUtils.toXmlString(parameters, "xml"), "UTF-8"));
        HttpResponse response = client.execute(request);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }


    /**
     * 第一次签名
     *
     * @param parameters 数据为服务器生成，下单时必须的字段排序签名
     * @param key
     * @return
     */
    public static String createSign(SortedMap<String, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

//    /**
//     * 第二次签名
//     *
//     * @param result 数据为微信返回给服务器的数据（XML 的 String），再次签名后传回给客户端（APP）使用
//     * @param key    密钥
//     * @return
//     * @throws IOException
//     */
//    public Map createSign2(String result, String key) throws IOException {
//        SortedMap<String, Object> map = new TreeMap<>(transferXmlToMap(result));
//        if (!map.get("return_code").equals("SUCCESS")) {
//            logger.error("createSign2 error:" + map.get("return_msg"));
//            return null;
//        }
//        Map app = new HashMap();
//        app.put("appid", map.get("appid"));
//        app.put("partnerid", map.get("mch_id"));
//        app.put("prepayid", map.get("prepay_id"));
//        app.put("package", "Sign=WXPay");                   // 固定字段，保留，不可修改
//        app.put("noncestr", map.get("nonce_str"));
//        app.put("timestamp", new Date().getTime() / 1000);  // 时间为秒，JDK 生成的是毫秒，故除以 1000
//        app.put("sign", createSign(new TreeMap<>(app), key));
//        return app;
//    }

    /**
     * 验证签名是否正确
     *
     * @return boolean
     * @throws Exception
     */
    public boolean checkSign(SortedMap<String, Object> parameters, String key) throws Exception {
        String signWx = parameters.get("sign").toString();
        if (signWx == null) return false;
        parameters.remove("sign"); // 需要去掉原 map 中包含的 sign 字段再进行签名
        String signMe = createSign(parameters, key);
        return signWx.equals(signMe);
    }



    public static void main(String[] args) {
        String s = "123";
        System.out.println(DigestUtils.md5Hex(s));
    }
}
