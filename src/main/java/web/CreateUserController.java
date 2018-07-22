package web;

import annotation.Controller;
import annotation.RequestMapping;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

@Controller
@RequestMapping("/user/create")
public class CreateUserController{

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @RequestMapping("")
    public void createUser(HttpRequest request, HttpResponse response){
        User user = new User(request.getParams("userId"), request.getParams("password"), request.getParams("name"), request.getParams("email"));
        DataBase.addUser(user);
        log.debug("User : {}", user);
        response.sendRedirect("/index.html");
    }

    public void doGet(HttpRequest request, HttpResponse response){}
}
