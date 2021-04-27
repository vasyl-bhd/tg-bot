package connectors;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;

@Slf4j
public class DebuggingConnection extends HttpConnection {

    @Override
    public Connection.Response execute() throws IOException {
        log(this.request());
        Connection.Response response = super.execute();
        log(response);

        return response;
    }

    public static Connection connect(String url) {
        Connection connection = new DebuggingConnection();
        connection.url(url);
        return connection;
    }

    private static void log(Connection.Request request) {
        log.info("========================================");
        log.info("[url] {}", request.url());
        log.info("== REQUEST ==");
        logBase(request);
        log.info("[method] {}", request.method());
        log.info("[data] {}", request.data());
        log.info("[request body] {}", request.requestBody());
    }

    private static void log(Connection.Response response) {
        log.info("== RESPONSE ==");
        logBase(response);
        log.info("[code] {}", response.statusCode());
        log.info("[status msg] {}", response.statusMessage());
        log.info("[body] {}", response.body());
        log.info("========================================");
    }

    private static void logBase(Connection.Base<?> base) {
        log.info("[headers] {}", base.headers());
        log.info("[cookies] {}", base.cookies());
    }
}
