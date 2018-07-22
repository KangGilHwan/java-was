package web;

import annotation.Controller;
import annotation.RequestMapping;
import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.ModelAndView;

@Controller
@RequestMapping("/user/list")
public class ListUserController{

    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @RequestMapping("")
    public void showUsers(HttpRequest request, HttpResponse response){
        String cookie = request.getHeader("Cookie");
        log.debug("Cookies : {}", cookie);
        if (!cookie.contains("logined=true")) {
            response.sendRedirect("/login.html");
            return;
        }
        ModelAndView modelAndView = new ModelAndView("/user/list.html");
        modelAndView.setAttribute("user", DataBase.findAll());
        response.modelAndViewResponse(modelAndView);
    }

    public void doPost(HttpRequest request, HttpResponse response){}
}
