package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.Controller;
import web.DispatcherServlet;
import web.ForwardController;
import web.ScanClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

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
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            List<Class<?>> classes = ScanClass.getClasses();
//            for (Class<?> name : classes){
//                log.debug("Class List : {}", name.getName());
//            }

            Controller controller = dispatcherServlet.match(request.getUrl());
            controller.service(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
