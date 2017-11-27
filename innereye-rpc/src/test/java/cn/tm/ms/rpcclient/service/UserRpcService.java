package cn.tm.ms.rpcclient.service;

import java.util.List;

import cn.tm.ms.rpc.entity.User;

public interface UserRpcService {

    List<User> select();
}
