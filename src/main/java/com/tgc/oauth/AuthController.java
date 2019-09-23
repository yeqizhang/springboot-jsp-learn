package com.tgc.oauth;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tgc.entity.ThirdAppBind;
import com.tgc.entity.User;
import com.tgc.service.UserService;
import com.tgc.utils.ServerResponse;
/**
 * @author 言曌
 * @date 2018/5/9 下午2:59
 */
@Controller
//@Slf4j
public class AuthController {
    @Autowired
    private QQAuthService qqAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private ThirdAppBindService thirdAppBindService;
//    @Autowired
//    private LogService logService;
    
    /**
     * 第三方授权后会回调此方法，并将code传过来
     *
     * @param code code
     * @return
     */
    @RequestMapping("/oauth/qq/callback")
    public String oauthByQQ(@RequestParam(value = "code") String code, HttpServletRequest request) {
        ServerResponse<String> tokenServerResponse = qqAuthService.getAccessToken(code);
        if (tokenServerResponse.isSuccess()) {
            ServerResponse<String> openidServerResponse = qqAuthService.getOpenId(tokenServerResponse.getData());
            if (openidServerResponse.isSuccess()) {
                //根据openId去找关联的用户
                //ThirdAppBind bind = thirdAppBindService.findByAppTypeAndOpenId(BindTypeEnum.QQ.getValue(), openidServerResponse.getData());
                ThirdAppBind bind = thirdAppBindService.findByAppTypeAndOpenId("qq", openidServerResponse.getData());
                if (bind != null && bind.getUserId() != null) {
                    //执行Login操作
                    User user = userService.findById(bind.getUserId());
                    if (user != null) {
                    		//设置session
//                        request.getSession().setAttribute(SensConst.USER_SESSION_KEY, user);
                    		//登陆业务日志记录（保存到表）
//                        logService.saveByLog(new Log(LogsRecord.LOGIN, LogsRecord.LOGIN_SUCCESS+"(QQ登录)", ServletUtil.getClientIP(request), DateUtil.date()));
                    		//程序日志
//                        log.info("用户[{}]登录成功(QQ登录)。", user.getUserDisplayName());
                        return "redirect:/admin";
                    }
                }
            }
        }
        return "redirect:/admin/login";
    }
    
}