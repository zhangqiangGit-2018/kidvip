package com.boco.springboot_kidvip.provider;

import com.alibaba.fastjson.JSON;
import com.boco.springboot_kidvip.dto.AccessTokenDTO;
import com.boco.springboot_kidvip.dto.GitHubUser;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component //初始化到spring的上下文
public class GitHubProvider {
    Logger logger= LoggerFactory.getLogger(GitHubProvider.class);


    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            try {
                String string= response.body().string();
                logger.info("return string:"+string);
                String tokenStr=string.split("&")[0].split("=")[1];


                return tokenStr;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getUser(String accessToken){
        logger.info("input accessToken:"+accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)

                .build();


        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();

            logger.info("user:"+string);
           GitHubUser gitHubUser= JSON.parseObject(string,GitHubUser.class);
           return  gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;

    }

}
