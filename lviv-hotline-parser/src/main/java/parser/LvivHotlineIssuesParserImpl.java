package parser;

import converter.ElementConverter;
import lombok.SneakyThrows;
import model.Action;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static model.Constants.*;

public class LvivHotlineIssuesParserImpl implements LvivHotlineIssuesParser {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");

    @Override
    @SneakyThrows
    public List<Action> parse(LocalDate from, LocalDate to) {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        return Jsoup.connect(LVIV_HOTLINE_URL)
                .sslSocketFactory(socketFactory())
                .data("data", getRequestData(from, to))
                .data("rn", "0")
                .data("all", "1")
                .data("isFrame", "")
                .data("hr", "1")
                .data("frameJeoId", "0")
                .post()
                .select(ROW_CLASS)
                .stream()
                .map(ElementConverter::toAction)
                .collect(Collectors.toList());
    }

    static private SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }

    private String getRequestData(LocalDate from, LocalDate to) {
        return String.format(
                model.Constants.REQUEST_FILTER_STRING_FORMAT,
                from.format(dateTimeFormatter),
                to.format(dateTimeFormatter),
                GROOVE_STREET_SWEET_HOME);
    }
}
