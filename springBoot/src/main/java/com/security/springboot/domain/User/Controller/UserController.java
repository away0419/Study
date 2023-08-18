package com.security.springboot.domain.User.Controller;

import com.security.springboot.domain.User.Model.UserVO;
import com.security.springboot.domain.User.Service.UserService;
import com.security.springboot.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody UserVO userVO){
        String token = TokenUtil.generateJwtToken(userVO);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String loginPage(Model model){
        log.debug("login");
        return "pages/login/loginPage";
    }
// insert into users(IS_ENABLE, CREATED_AT , USER_EMAIL , USER_PW , role) values (true, current_timestamp(), 'admin@c.com', '123', 'ADMIN');
}
