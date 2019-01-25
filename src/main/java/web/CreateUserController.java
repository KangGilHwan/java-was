package web;

import annotation.Autowired;
import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestParam;
import db.DataBase;
import dto.UserDto;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

@Controller
@RequestMapping("/user/create")
public class CreateUserController {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Autowired
    private DataBase dataBase;

    @RequestMapping("")
    public String createUser(UserDto userDto) {
        log.debug("UserId : {}", userDto);
        dataBase.addUser(userDto.toUser());
        return "redirect:/index.html";
    }
}
