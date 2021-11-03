package com.study.webserver;

public class Starter {
    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(3000);
        server.setWebappPath("src/main/resources/webapp");
        server.start();

        // GET /index.html HTTP/1.1
        // path to resource = webappPath + URI =>
        // src/main/resources/webapp/index.html

        // // GET /css/styles.css HTTP/1.1
        // path to resource = webappPath + URI =>
        // src/main/resources/webapp/css/styles.css
    }
}
