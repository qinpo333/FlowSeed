package com.cmcc.seed.mall_cp;

import com.cmcc.seed.codec.RSAPKCS1Sign;
import com.cmcc.seed.codec.RSAX509Verify;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CPClient {
    // 你的商户ID
    public int cpid = 1013;
//    public int cpid = 1000001;
    // 你的赠送私钥
    public String priKey = "MIICXAIBAAKBgQDHt6x+y3VtnXHKsFyw1EtQIEh46KGC+Mh7EhG/lWJ81uabCSkFmZ1PhxtKdHdIt0PWmxVJIUMGlaiX6iZL7CwVHkRwOTUUxXY8tMS1oLJ4xdkkcZDZbMjjRlqyXqvR/chHNkTqIDUstHUkF2q2V8kcwngyHZ4oWCTjD8RpYCWSxQIDAQABAoGAJu430rOIXWiucMs2FkYbtC/G0MLQEP673bmk+gp9m0Ysx5XeiDfsgT8d48KWKfeHYsLyQEIalaMArTQ4aN5NrSwDJbvckl0wLSLfqCRlf/gVgccsc35nJpKdofonIoHDpffg7PrRWMln96LlAvk2JxkWlZW84eop+eWPIVQWwtUCQQDlAjAclhndffMg10qEaB8VySQ/2W+UJDOUMGE19qhq2j3YYHtRKq2+gsXQgApElTdWMjsJuhWiaXrvjQwWgYYbAkEA30Gxxgu6XWiPacoMi3RnuTX6Sf/SUC8TvGvVSAbPBOOpY4aDIjJm28z0UBTls/6Gl8jeCtgJWcZOi5C9wG5YnwJBAL0//KBzDrby0cwO/p7DKQUo7xLwznufGxzYik09JFsBNeCtgttFqMVqbqzqUxHwOgW6hv130U8hWh3C8v1aVocCQAI4mRgrTbNYMvUOeYl5ov6ItvC9hs4dacjaGckZqD2YNjszP2K7taJCK7wQOSjNQ3GHIg5hj9ZAzs88pWagsmkCQHZz3OMCfqjsPtrpswVOQMhdn611ceylrDzSTT2NZCYJqWUQSuJH6NYVumANfUBQGDTSqKlvnWdaEHK5fIaYEHE=";
//    public String priKey = "MIICXQIBAAKBgQDO8GEWndiXQYnvwVMMRJGdYwDco3lOKpSs4ASN+260g5D2x64Kg9UodNeLFVh83s01fQMTqNzf6HBEQeVYlOObPZI9yNkOI2sNh/Ud16EdPsH0olA52zZs/F1aRmE+XvSyaQ6oI7DKoaglNUJ5j08Med3gxLBqE/HfHe5fMzA3fQIDAQABAoGBAIdKRE8i9x08Lc/fVkoo8gIxXbjh70aZNAYplxC0FtZRRY7xUOly8wK2EMzPMcDBcOvNRRuvRGfmcK9PXX+iJ4KDs8A0Ia63txqY5W5tNWC0TjoFpNRXwsn2xDIsTCOZa7WmjYEGO1DHPLwUpCJM/UTn35FzdQq2cX/ZIqWpSxfBAkEA5siMdKrn7xMy/Agd3c+6NuUPneiocH0S7TNfvUmPkUkFJZuk352VuzaWdAktL2x+bFw9fNFMmwt3bh7jR9ettQJBAOWM22F7JgRRb08vVk8j9dXoQcKaoSa2CxkIldsQ63yqz13W5DjUCLsCUj6u5wKGIs8EqL9j5mYaDNpNCRszP6kCQGrNGrsPMKCiOPEL7I5vCJVI8i44smLEbxLYuJDHzyoEKd6apfVaXyWxgHUYnhGZWvRYx5fQ4GvMJawPnUztEhUCQQCe8diZ+A6L4ONk/g8SP4Eiu26FVFKPnm+yxsoU5PNZ4GAzxu9CFjdxXhsawjq8+aoylDRQSdkoC5OouLy8c9D5AkANpkmoZHnV+kYfAGPoRYh7ujA8WWgFXbV5/AexgoKablj2uq9y4zbPC0L8Ayzmjd6KPbxfRq6/RlGYGqzUp2rC";
    // 充值平台的公钥
    public String paltPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4iWJH4szBhbDyxRsxvFSyTcbUgwvgoHIfBnZr6O+5suPdD9A86bdfR5Zn9zEXUZZ9T72SHHjebpUm9yTP8x85wxcrAHvN7tUq4YymwqcQAdsuqT403UnEfpimZeFdjoTraF/3gu4zWL+OHKr2nqrb1qqC56G7gvFFZFW/w9OL3QIDAQAB";
//    public String paltPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDO8GEWndiXQYnvwVMMRJGdYwDco3lOKpSs4ASN+260g5D2x64Kg9UodNeLFVh83s01fQMTqNzf6HBEQeVYlOObPZI9yNkOI2sNh/Ud16EdPsH0olA52zZs/F1aRmE+XvSyaQ6oI7DKoaglNUJ5j08Med3gxLBqE/HfHe5fMzA3fQIDAQAB";

    protected String serverUrl="http://life.10085.cn:20230";
//    protected String serverUrl="http://10085.test.happyapk.com:54100";


    /**
     * 发起赠送
     * 
     * @param req
     * @return
     * @throws IOException
     * @throws ServiceException
     */
    public long present(PresentReq req) throws IOException, ServiceException {
        if (!req.isValid()) {
            throw new ServiceException("2001", "参数错误");
        }
        Map<String, Long> r = this.call(serverUrl + "/mall/serv/present", req,
                new TypeToken<Map<String, Long>>() {
                }.getType());
        return r.get("rechrNo");
    }

    /**
     * 查询充值订单
     * 
     * @param rechrNos
     * @return
     * @throws IOException
     * @throws ServiceException
     */
    public Map<Long, RechrOrder> query(Collection<Long> rechrNos)
            throws IOException, ServiceException {
        if (rechrNos == null || rechrNos.isEmpty()) {
            return Collections.emptyMap();
        }
        return this.call(serverUrl + "/mall/serv/rinfos", rechrNos,
                new TypeToken<Map<Long, RechrOrder>>() {
                }.getType());

    }

    protected String sign(String data) throws IOException {
        try {
            return new RSAPKCS1Sign(priKey).sign(data, "utf-8");
        } catch (Exception e) {
            throw new IOException("when do Sign", e);
        }
    }

    protected boolean verify(String data, String sign) throws IOException {
        try {
            return new RSAX509Verify(paltPubKey).verify(data, "utf-8", sign);
        } catch (Exception e) {
            throw new IOException("when do Verify", e);
        }
    }

    protected <T> T call(String url, Object odata, Type resultType)
            throws IOException {
        String data = this.toJsonStr(odata);
        String sign = this.sign(data);
        Map<String, Object> q = new LinkedHashMap<String, Object>();
        q.put("mer", this.cpid);
        q.put("data", data);
        q.put("sign", sign);
        data = this.toJsonStr(q);

        System.out.println(data);

        String resp = this.post(url, data);
        System.out.println("充值请求响应：" + resp);
        Map<String, String> r = fromJsonStr(resp,
                new TypeToken<Map<String, String>>() {
                }.getType());

        String code = String.valueOf(r.get("code"));
        if (!"0".equals(code)) {
            throw new ServiceException(code, r.get("err"));
        }
        String rdata = r.get("data");
        String rsign = r.get("sign");
        if (!this.verify(rdata, rsign)) {
            throw new IOException("verify err: req=" + data + ", resp=" + resp);
        }
        return this.fromJsonStr(rdata, resultType);
    }

    protected String post(String url, String data) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(data, "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return resp;
    }

    private static final Gson gson = new Gson();

    protected String toJsonStr(Object o) {
        return gson.toJson(o);
    }

    protected <T> T fromJsonStr(String json, Type c) {
        return gson.fromJson(json, c);
    }

    public static void main(String[] args) throws ServiceException, IOException {
        CPClient c = new CPClient();
        // System.out.println(c.query(Arrays.asList(1l, 2l, 3l)));

        PresentReq r = new PresentReq().setAsCP(1000001).setGid(10001)
                .setTel("18867103382").setTransNo(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(c.present(r));
    }
}
