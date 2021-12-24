package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import cn.edu.swu.ffdy.springboot.utils.ValidateCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/validate")
public class ValidateCodeHandler {
    @GetMapping
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/png");

        // 获取session
        HttpSession session = request.getSession(true);

        final String Code = ValidateCode.getCodeNum();
        session.setAttribute(SessionContents.USER_VALIDATE_CODE, Code); // 将验证码存入session
        try (OutputStream out = response.getOutputStream()) {
            // 将对应的验证码图片发送给客户端
            ImageIO.write(ValidateCode.getCodePic(Code), "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
