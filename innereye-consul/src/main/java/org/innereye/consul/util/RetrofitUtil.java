package org.innereye.consul.util;

import java.io.IOException;
import java.math.BigInteger;

import org.innereye.consul.exception.ConsulException;
import org.innereye.consul.model.ConsulResponse;

import com.google.common.collect.Sets;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitUtil {

    public static boolean isSuccessful(Response<?> response, Integer... okCodes) {
        return response.isSuccessful() || Sets.newHashSet(okCodes).contains(response.code());
    }

    public static <T> T extract(Call<T> call, Integer... okCodes) {
        Response<T> response;
        try {
            response = call.execute();
        }
        catch (IOException e) {
            throw new ConsulException(e);
        }
        if (isSuccessful(response, okCodes)) {
            return response.body();
        }
        else {
            throw new ConsulException(response);
        }
    }

    public static <T> ConsulResponse<T> extractConsulResponse(Call<T> call, Integer... okCodes) {
        Response<T> response;
        try {
            response = call.execute();
        }
        catch (IOException e) {
            throw new ConsulException(e);
        }
        if (!isSuccessful(response, okCodes)) {
            throw new ConsulException(response);
        }
        return consulResponse(response);
    }

    private static <T> ConsulResponse<T> consulResponse(Response<T> response) {
        Headers headers = response.headers();
        String indexHeaderValue = headers.get("X-Consul-Index");
        String lastContactHeaderValue = headers.get("X-Consul-Lastcontact");
        String knownLeaderHeaderValue = headers.get("X-Consul-Knownleader");
        BigInteger index = indexHeaderValue == null ? new BigInteger("0") : new BigInteger(indexHeaderValue);
        long lastContact = lastContactHeaderValue == null ? 0 : Long.valueOf(lastContactHeaderValue);
        boolean knownLeader = knownLeaderHeaderValue == null ? false : Boolean.valueOf(knownLeaderHeaderValue);
        ConsulResponse<T> consulResponse = new ConsulResponse<>(response.body(), lastContact, knownLeader, index);
        return consulResponse;
    }
}
