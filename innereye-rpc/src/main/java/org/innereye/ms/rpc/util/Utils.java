package org.innereye.ms.rpc.util;

import java.io.File;
import java.net.URL;
import java.util.List;

public class Utils {

    /**
     * rpc扫描基包[递归]
     *
     * @param rpc_base_backage
     */
    public static void scanRpcBasePackage(String rpc_base_backage, List<String> list) {
        URL url = Utils.class.getClassLoader().getResource("");
        String baseBackagePath = rpc_base_backage.replaceAll("\\.", "/");
        File baseBackage = new File(url.getPath() + baseBackagePath);
        if (baseBackage.exists()) {
            // 是目录
            String[] childrens = baseBackage.list();
            if (childrens != null && childrens.length > 0) {
                for (String children : childrens) {
                    File childrenFile = new File(baseBackage, children);
                    if (childrenFile.isDirectory()) {
                        scanRpcBasePackage(rpc_base_backage + "." + children, list);
                    }
                    else {
                        if (childrenFile.getName().endsWith(".class")) {
                            String fileName = rpc_base_backage + "." + childrenFile.getName();
                            fileName = fileName.substring(0, fileName.lastIndexOf("."));
                            list.add(fileName);
                        }
                    }
                }
            }
        }
        else {
            throw new RuntimeException("rpc初始化异常...基包" + baseBackage + "不存在");
        }
    }
}
