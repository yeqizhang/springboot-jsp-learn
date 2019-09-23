package com.tgc.oauth.impl;
import org.springframework.stereotype.Service;

import com.tgc.entity.ThirdAppBind;
import com.tgc.oauth.ThirdAppBindService;

@Service
public class ThirdAppBindServiceImpl implements ThirdAppBindService {

	@Override
	public ThirdAppBind findByAppTypeAndOpenId(String bindType, String openid) {
		// TODO Auto-generated method stub
		return null;
	}
	
}