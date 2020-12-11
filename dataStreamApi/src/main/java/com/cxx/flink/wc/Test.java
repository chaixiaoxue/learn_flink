package com.cxx.flink.wc;

import com.nextbreakpoint.flinkclient.api.ApiClient;
import com.nextbreakpoint.flinkclient.api.ApiException;
import com.nextbreakpoint.flinkclient.api.FlinkApi;
import com.nextbreakpoint.flinkclient.model.DashboardConfiguration;
import com.nextbreakpoint.flinkclient.model.JobDetailsInfo;
import com.nextbreakpoint.flinkclient.model.SavepointTriggerRequestBody;

/**
 * @author chaixiaoxue
 * @version 1.0
 * @date 2020/12/11 15:35
 */
public class Test {

    public static void main(String[] args) throws ApiException {
        FlinkApi api = new FlinkApi();
        api.getApiClient().setBasePath("http://192.168.3.100:8081");
        //DashboardConfiguration dashboardConfiguration = api.showConfig();
        JobDetailsInfo details = api.getJobDetails("0514ab0e27c3a4062c59a8efd1178a1c");
        //api.terminateJob("f370f5421e5254eed8d6fc6673829c83", "cancel");
        SavepointTriggerRequestBody savepointTriggerRequestBody = new SavepointTriggerRequestBody();
        savepointTriggerRequestBody.setCancelJob(true);
        savepointTriggerRequestBody.setTargetDirectory("hdfs://192.168.3.101:8020/user/flink/flink-savepoints/plink");
        api.createJobSavepoint(savepointTriggerRequestBody,"0514ab0e27c3a4062c59a8efd1178a1c");
        System.out.println(details);
    }
}
