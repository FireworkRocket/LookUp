package org.fireworkrocket.lookup.kernel.process;

import javafx.collections.ObservableList;
import org.fireworkrocket.lookup.kernel.config.DatabaseUtil;

import java.util.Map;

import static org.fireworkrocket.lookup.kernel.process.net.util.URLUtil.*;

public class ApiParamHandler {
    //TODO 修复修改参数BUG，修复点击应用按钮后删除按钮失效问题
    public static void editParam(String selectedParam, String newParam, String apiUrl, ObservableList<String> apiObservableList) throws Exception {
        String[] keyValue = newParam.split("=");
        if (keyValue.length != 2) {
            throw new Exception("参数格式错误");
        }

        String newUrl = replaceURLParam(keyValue[0], apiUrl, keyValue[0], keyValue[1]);
        apiObservableList.remove(apiUrl);
        apiObservableList.add(newUrl);
        DatabaseUtil.replaceItem(apiUrl, newUrl);
    }

    public static void deleteParam(String selectedParam, String apiUrl, ObservableList<String> apiObservableList) {
        String newUrl = removeURLParam(apiUrl, selectedParam.split("=")[0]);
        apiObservableList.remove(apiUrl);
        apiObservableList.add(newUrl);
        DatabaseUtil.replaceItem(apiUrl, newUrl);
    }

    public static void addParam(String newParam, String apiUrl, ObservableList<String> apiObservableList) throws Exception {
        String[] keyValue = newParam.split("=");
        if (keyValue.length == 2) {
            String key = keyValue[0];
            String value = keyValue[1];
            Map<String, String> params = parseURLParams(apiUrl);
            if (!params.containsKey(key)) {
                String newUrl = addURLParam(apiUrl, key, value);
                apiObservableList.remove(apiUrl);
                apiObservableList.add(newUrl);
                DatabaseUtil.replaceItem(apiUrl, newUrl);
            } else {
                throw new Exception("参数已存在");
            }
        } else {
            throw new Exception("参数格式错误");
        }
    }
}