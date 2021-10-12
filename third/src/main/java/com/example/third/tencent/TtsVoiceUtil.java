package com.example.third.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
// 导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
// 导入对应VMS模块的client
import com.tencentcloudapi.vms.v20200902.VmsClient;
// 导入要请求接口对应的request response类
import com.tencentcloudapi.vms.v20200902.models.SendTtsVoiceRequest;
import com.tencentcloudapi.vms.v20200902.models.SendTtsVoiceResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: freaxjj
 * @Desc: https://cloud.tencent.com/document/product/1128/51558
 * @Date: 9/23/21 1:57 PM
 */
@Slf4j
public class TtsVoiceUtil
{
    private static final String phonePrefix = "+86";
    // todo 取配置
    private static final String voiceSdkAppid = "";
    private static final String secretId = "";
    private static final String secretKey = "";

    public static void sendVoice(String templateId,String[] params, String sendTo){
        try {
            /* CAM密匙查询: https://console.cloud.tencent.com/cam/capi*/
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理
            // httpProfile.setProxyHost("host");
            // httpProfile.setProxyPort(port);
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果您访问的是金融区的服务
             * 则必须手动指定域名，例如vms的上海金融区域名： vms.ap-shanghai-fsi.tencentcloudapi.com */
            //httpProfile.setEndpoint("vms.tencentcloudapi.com");
            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK默认用TC3-HMAC-SHA256进行签名*/
            clientProfile.setSignMethod("TC3-HMAC-SHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以vms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
            VmsClient client = new VmsClient(cred, "ap-guangzhou", clientProfile);

            SendTtsVoiceRequest req = new SendTtsVoiceRequest();
            //应用id
            req.setVoiceSdkAppid(voiceSdkAppid);
            req.setTemplateId(templateId);
            // 模板参数，若模板没有参数，请提供为空数组
            req.setTemplateParamSet(params);
            /* 被叫手机号码，采用 e.164 标准，格式为+[国家或地区码][用户号码]*/
            if(!sendTo.startsWith(phonePrefix)){
                sendTo = phonePrefix.concat(sendTo);
            }
            req.setCalledNumber(sendTo);
            // 播放次数，可选，最多3次，默认2次
            Long playTimes = 2L;
            req.setPlayTimes(playTimes);
            // 用户的 session 内容，腾讯 server 回包中会原样返回
            //req.setSessionContext("xxxx");

            SendTtsVoiceResponse res = client.SendTtsVoice(req);
            log.info("反送语音消息成功：{}", SendTtsVoiceResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String[] params = {"freaxjj", "测试报警"};
        sendVoice("381236", params, "17681822729");
    }
}
