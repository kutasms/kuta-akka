package com.kuta.base.util.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.kuta.common.config.utils.PropertyUtils;

public class AliyunEmailUtil {
	
	
	// 设置鉴权参数，初始化客户端
    private DefaultProfile profile = DefaultProfile.getProfile(
    		PropertyUtils.getProperty("email", "aliyun.area"),// 地域ID
    		PropertyUtils.getProperty("email", "aliyun.access.key.id"),// 您的AccessKey ID
    		PropertyUtils.getProperty("email", "aliyun.access.key.secret"));// 您的AccessKey Secret
    private IAcsClient client = new DefaultAcsClient(profile);
    
    /**
     * 添加短信模板
     */
    private String addSmsTemplate(String name, String type,String templateStr,String remark) throws ClientException {
        CommonRequest addSmsTemplateRequest = new CommonRequest();
        addSmsTemplateRequest.setSysDomain("dysmsapi.aliyuncs.com");
        addSmsTemplateRequest.setSysAction("AddSmsTemplate");
        addSmsTemplateRequest.setSysVersion("2017-05-25");
        // 短信类型。0：验证码；1：短信通知；2：推广短信；3：国际/港澳台消息
        addSmsTemplateRequest.putQueryParameter("TemplateType", type);
        // 模板名称，长度为1~30个字符
        addSmsTemplateRequest.putQueryParameter("TemplateName", name);
        // 模板内容，长度为1~500个字符
        String tempStr = null;
        switch(type) {
        case "0":
        	tempStr = PropertyUtils.getProperty("email", "aliyun.template.checkcode");
        	break;
        case "1":
        	tempStr = PropertyUtils.getProperty("email", "aliyun.template.notice");
        	break;
        case "2":
        	tempStr = templateStr;
        }
        addSmsTemplateRequest.putQueryParameter("TemplateContent", tempStr);
        // 短信模板申请说明
        addSmsTemplateRequest.putQueryParameter("Remark", remark);
        CommonResponse addSmsTemplateResponse = client.getCommonResponse(addSmsTemplateRequest);
        String data = addSmsTemplateResponse.getData();
        // 消除返回文本中的反转义字符
        String sData = data.replaceAll("'\'", "");
        Gson gson = new Gson();
        // 将字符串转换为Map类型，取TemplateCode字段值
        Map<?, ?> map = gson.fromJson(sData, Map.class);
        Object templateCode = map.get("TemplateCode");
        return templateCode.toString();
    }
    
    public JSONObject send(String phoneNum) throws ClientException {
    	Random random = new Random();
    	Integer code = random.nextInt(899999) + 100000;
    	return send(code.toString(),phoneNum);
    }
    
    public JSONObject send(String phoneNum, Integer type) throws ClientException {
    	Random random = new Random();
    	Integer code = random.nextInt(899999) + 100000;
    	return send(code.toString(),phoneNum, type);
    }
    
    /**
     * 发送短信验证码
     * @throws ClientException 
     * */
    public JSONObject send(String code,String phoneNum) throws ClientException {
    	String templateCode = PropertyUtils.getProperty("email", "aliyun.template.checkcode.id");
    	if(templateCode == null || templateCode.equals("")) {
    		templateCode = addSmsTemplate("短信验证码", 
    				"0",
    				PropertyUtils.getProperty("email", "aliyun.template.checkcode"),
    				"登陆短信验证码");
    		
    		PropertyUtils.updateProperty("email", "aliyun.template.checkcode.id", templateCode);
    	}
    	JSONObject param = new JSONObject();
    	param.put("code", code);
    	return sendSms(templateCode, phoneNum, param);
    }
    /**
     * 发送短信验证码
     * @throws ClientException 
     * */
    public JSONObject send(String code,String phoneNum, Integer type) throws ClientException {
    	String templateCode = null;
    	switch (type) {
		case 0:
			templateCode = PropertyUtils.getProperty("email", "aliyun.template.checkcode.id");
			break;
		case 1:
			templateCode = PropertyUtils.getProperty("email", "aliyun.template.signup.checkcode.id");
			break;
		default:
			break;
		}
    	
    	if(templateCode == null || templateCode.equals("")) {
    		templateCode = addSmsTemplate("短信验证码", 
    				"0",
    				PropertyUtils.getProperty("email", "aliyun.template.checkcode"),
    				"登陆短信验证码");
    		
    		PropertyUtils.updateProperty("email", "aliyun.template.checkcode.id", templateCode);
    	}
    	JSONObject param = new JSONObject();
    	param.put("code", code);
    	return sendSms(templateCode, phoneNum, param);
    }
    /**
     * 发送短信
     */
    private JSONObject sendSms(String templateCode,String phoneNum,JSONObject param) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", phoneNum);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        String signName = PropertyUtils.getProperty("email", "aliyun.sign.name");
        request.putQueryParameter("SignName", signName);
        // 短信模板ID
        request.putQueryParameter("TemplateCode", templateCode);
        // 短信模板变量对应的实际值，JSON格式。
        
        request.putQueryParameter("TemplateParam", param.toJSONString());
        
        CommonResponse commonResponse = client.getCommonResponse(request);
        String data = commonResponse.getData();
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(JSONObject.parseObject(data));
        jsonObject.put("TemplateCode", templateCode);
        jsonObject.put("PhoneNumber", phoneNum);
        jsonObject.putAll(param);
        return jsonObject;
    }
    
    private JSONObject querySendDetails(String bizId,String phoneNum,Date sendDate) throws ClientException {
    	return querySendDetails(bizId,phoneNum,sendDate, "1");
    }
    /**
     * 查询发送详情
     */
    private JSONObject querySendDetails(String bizId,String phoneNum,Date sendDate,String currentPage) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("QuerySendDetails");
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumber", phoneNum);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 短信发送日期，支持查询最近30天的记录。格式为yyyyMMdd，例如20191010。
        request.putQueryParameter("SendDate", formatter.format(sendDate));
        // 分页记录数量
        request.putQueryParameter("PageSize", PropertyUtils.getProperty("email", "aliyun.query.pagesize"));
        // 分页当前页码
        request.putQueryParameter("CurrentPage", currentPage);
        // 发送回执ID，即发送流水号。
        request.putQueryParameter("BizId", bizId);
        CommonResponse response = client.getCommonResponse(request);
        return JSONObject.parseObject(response.getData());
    }
}
