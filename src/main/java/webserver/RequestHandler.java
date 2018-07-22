package webserver;

import annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.ForwardController;
import web.ScanClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpFactory.init(in);
            HttpResponse response = new HttpResponse(out);
//            DispatcherServlet dispatcherServlet = new DispatcherServlet();

            Object controller = ScanClass.getController(request.getUrl());
            if (controller == null) {
                ForwardController.forward(request, response);
                return;
            }
            log.debug("Controller : {}", controller.getClass().getName());
            String path = controller.getClass().getAnnotation(RequestMapping.class).value();
            Method[] methods = controller.getClass().getMethods();
            log.debug("RequestMapping path : {}", path);

            for (Method method : methods){
                if (method.isAnnotationPresent(RequestMapping.class)){
                    if (request.urlCollect(path + method.getAnnotation(RequestMapping.class).value())){
                        method.invoke(controller, request, response);
                    return;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
