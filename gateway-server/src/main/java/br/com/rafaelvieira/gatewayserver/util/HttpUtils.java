package br.com.rafaelvieira.gatewayserver.util;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author rafae
 */
public class HttpUtils {

    public static String getRemoteIp(ServerWebExchange exchange){
        var remoteAddress = exchange.getRequest().getRemoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getAddress().getHostAddress();
        } else {
            return null;
        }
    }

    public static String getHost(ServerWebExchange exchange){
        return exchange.getRequest().getURI().getHost();
    }

    private HttpUtils() {
    }
}
