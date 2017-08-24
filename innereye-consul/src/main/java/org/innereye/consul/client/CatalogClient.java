package org.innereye.consul.client;

import java.util.List;

import org.innereye.consul.model.health.Node;
import org.innereye.consul.util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * https://www.consul.io/api/catalog.html
 *
 * @author zhangbaohao
 * @date 2017年8月23日
 * @version 1.0
 */
public class CatalogClient {

    private final Api api;

    public CatalogClient(Retrofit retrofit){
        this.api = retrofit.create(Api.class);
    }

    public List<String> getDatacenters() {
        return RetrofitUtil.extract(api.getDatacenters());
    }

    public List<Node> getNodes() {
        return RetrofitUtil.extract(api.getNodes());
    }

    interface Api {

        @GET("catalog/datacenters")
        Call<List<String>> getDatacenters();

        @GET("catalog/nodes")
        Call<List<Node>> getNodes();
    }
}
