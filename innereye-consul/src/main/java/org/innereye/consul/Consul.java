package org.innereye.consul;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.innereye.consul.client.CatalogClient;
import org.innereye.consul.util.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HostAndPort;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Client for interacting with the Consul HTTP API.
 */
public class Consul {

    /**
     * Default Consul HTTP API host.
     */
    public static final String DEFAULT_HTTP_HOST = "localhost";

    /**
     * Default Consul HTTP API port.
     */
    public static final int DEFAULT_HTTP_PORT = 8500;

    private final ExecutorService executorService;

    private final CatalogClient catalogClient;

    private Consul(CatalogClient catalogClient, ExecutorService executorService){
        this.catalogClient = catalogClient;
        this.executorService = executorService;
    }

    public void destroy() {
        this.executorService.shutdownNow();
    }

    public CatalogClient catalogClient() {
        return catalogClient;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private URL url;

        private ExecutorService executorService;
        {
            try {
                url = new URL("http", DEFAULT_HTTP_HOST, DEFAULT_HTTP_PORT, "");
            }
            catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        Builder(){
        }

        private String buildUrl(URL url) {
            return url.toExternalForm().replaceAll("/$", "") + "/v1/";
        }

        public Builder withHostAndPort(HostAndPort hostAndPort) {
            try {
                this.url = new URL("http", hostAndPort.getHost(), hostAndPort.getPort(), "");
            }
            catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Consul build() {
            final Retrofit retrofit;
            ExecutorService executorService = this.executorService;
            if (executorService == null) {
                executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", true));
            }
            try {
                retrofit = createRetrofit(buildUrl(this.url), Jackson.MAPPER, executorService);
            }
            catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            CatalogClient catalogClient = new CatalogClient(retrofit);
            return new Consul(catalogClient, executorService);
        }

        private Retrofit createRetrofit(String url, ObjectMapper mapper, ExecutorService executorService)
                throws MalformedURLException {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            Dispatcher dispatcher = new Dispatcher(executorService);
            dispatcher.setMaxRequests(Integer.MAX_VALUE);
            dispatcher.setMaxRequestsPerHost(Integer.MAX_VALUE);
            builder.dispatcher(dispatcher);
            final URL consulUrl = new URL(url);
            return new Retrofit.Builder().baseUrl(new URL(consulUrl.getProtocol(), consulUrl.getHost(), consulUrl.getPort(), consulUrl.getFile()).toExternalForm()).addConverterFactory(JacksonConverterFactory.create(mapper)).client(builder.build()).build();
        }
    }
}
