package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private HttpUrl url;
    private HttpMethod method;
    private HttpHeader httpHeader;
    private HttpParameter parameters;

    public HttpRequest(String url, HttpMethod method, HttpHeader header, BufferedReader br) throws IOException {
        this.method = method;
        this.httpHeader = header;

        List<String> urlAndQueryString = Arrays.asList(url.split("\\?"));
        this.url = new HttpUrl(urlAndQueryString.get(0).trim());
        this.parameters = new HttpParameter(existParameter(urlAndQueryString, br));

        log.debug("Url : {} , Method : {}", getUrl(), getMethod());
    }

    public String getUrl() {
        return url.getUrl();
    }

    public String getMethod() {
        return method.getMethod();
    }

    public String getHeader(String key) {
        return httpHeader.findHeader(key);
    }

    public String getParameter(String key) {
        return parameters.findParameter(key);
    }

    public String existParameter(List<String> urlAndQueryString, BufferedReader br) throws IOException {
        if (!method.isGetMethod()) {
            return IOUtils.readData(br, httpHeader.getContentLength());
        }
        return parseQueryString(urlAndQueryString);
    }

    public String parseQueryString(List<String> urlAndQueryString) {
        if (urlAndQueryString.size() != 2) {
            return null;
        }
        return urlAndQueryString.get(1).trim();
    }

    public boolean isGetMethod() {
        return method.isGetMethod();
    }

    public boolean urlCollect(String mappingUrl) {
        return url.getUrl().equals(mappingUrl);
    }
}
