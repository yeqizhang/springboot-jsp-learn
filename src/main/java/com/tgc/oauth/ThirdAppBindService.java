package com.tgc.oauth;
import com.tgc.entity.ThirdAppBind;

public interface ThirdAppBindService {
    /**
     * 根据code获得Token
     *
     * @param code code
     * @return token
     */
	ThirdAppBind findByAppTypeAndOpenId(String bindType, String openid);
}