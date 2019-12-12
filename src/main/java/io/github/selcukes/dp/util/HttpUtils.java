package io.github.selcukes.dp.util;

import io.github.selcukes.dp.exception.DriverPoolException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;

/**
 * The type Http utils.
 */
public final class HttpUtils {

    private static Function<String, HttpURLConnection> connection = (endpoint) -> {
        HttpURLConnection.setFollowRedirects(false);

        final HttpURLConnection connection;

        try {
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            return connection;
        } catch (Exception e) {
            throw new DriverPoolException(e);
        } finally {
            HttpURLConnection.setFollowRedirects(true);
        }
    };

    /**
     * Gets location.
     *
     * @param endpoint the endpoint
     * @return the location
     */
    public static String getLocation(String endpoint) {
        return extract(connection.apply(endpoint), t -> t.getHeaderField("Location"));
    }

    /**
     * Gets response input stream.
     *
     * @param endpoint the endpoint
     * @return the response input stream
     */
    public static InputStream getResponseInputStream(String endpoint) {
        return extract(connection.apply(endpoint), t -> {
            try {
                return t.getInputStream();
            } catch (IOException e) {
                throw new DriverPoolException(e);
            }
        });
    }

    private static <T> T extract(HttpURLConnection connection, Function<HttpURLConnection, T> condition) {
        return condition.apply(connection);
    }
}