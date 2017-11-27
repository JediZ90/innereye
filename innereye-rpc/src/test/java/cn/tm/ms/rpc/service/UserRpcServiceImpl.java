package cn.tm.ms.rpc.service;

import java.util.ArrayList;
import java.util.List;

import org.innereye.ms.rpc.annotion.RpcService;

import cn.tm.ms.rpc.entity.User;

@RpcService
public class UserRpcServiceImpl implements UserRpcService {

    public List<User> select() {
        List<User> list = new ArrayList<User>();
        User user = null;
        for (int i = 0;i < 10;i++) {
            user = new User("小明", 12 + i, 1 % 2);
            list.add(user);
        }
        return list;
    }
}
