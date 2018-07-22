package web;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DispatcherServlet {

//    private Map<String, Controller2> mapper;
//
//    public DispatcherServlet() {
//        mapper = new HashMap<>();
//        init();
//    }
//
//    private void init() {
//        mapper.put("/user/create", new CreateUserController());
//        mapper.put("/user/login", new LoginController());
//        mapper.put("/user/list", new ListUserController());
//        mapper.put("/", new ForwardController());
//    }
//
//    public Controller2 match(String url) {
//        Optional<Controller2> maybeController = Optional.ofNullable(mapper.get(url));
//        if (!maybeController.isPresent()) {
//            return mapper.get("/");
//        }
//        return maybeController.get();
//    }
}
