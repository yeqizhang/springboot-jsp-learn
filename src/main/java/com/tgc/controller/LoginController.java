package com.tgc.controller;

//@Controller
//@RequestMapping("/login")
//public class LoginController {
//
//    private static Logger log = LoggerFactory.getLogger(LoginController.class);
//
//    @Autowired
//    UserService userService;
//
//
//    @RequestMapping("/to_login")
//    public String toLogin() {
//    	System.out.println("==> login/to_login");
//        return "login";
//    }
//
//    @RequestMapping("/do_login")
//    @ResponseBody
//    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {//加入JSR303参数校验
////    	System.out.println("$$$$$$$$$login/do_login$$$$$$$$$$$$$$$$$");
////    	System.out.println("记录校验：");
//        log.info(loginVo.toString());
//        String token = userService.login(response, loginVo);
//        if(!StringUtils.isEmpty(token)){
//        	System.out.println("登陆成功~~");
//        }else{
//        	System.out.println("登陆失败~~");
//        }
////        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
//        return Result.success(token);
//    }
//
//}