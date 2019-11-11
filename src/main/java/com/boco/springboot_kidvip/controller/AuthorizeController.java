package com.boco.springboot_kidvip.controller;

import com.boco.springboot_kidvip.dto.AccessTokenDTO;
import com.boco.springboot_kidvip.dto.GitHubUser;
import com.boco.springboot_kidvip.provider.GitHubProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    private Logger logger= LoggerFactory.getLogger(AuthorizeController.class);
    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id("35ecf8f398f2ff03a520");
        accessTokenDTO.setClient_secret("45d6f78d0aa798dd69f266d0c08ab7fa42d3c93d");
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");

        accessTokenDTO.setState(state);
       String accessToken= gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user=gitHubProvider.getUser(accessToken);
        logger.info("user:"+user.toString());
        return "index";
    }
}
