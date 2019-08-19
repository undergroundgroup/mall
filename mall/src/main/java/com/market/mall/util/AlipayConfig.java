package com.market.mall.util;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100200643695";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYZnnmbozQaomYJZrPwUFKdbskE4R6hL7MiaB6ODHKDRCR8mPWRWd1bJdUhZQyd9pnaj6Z2EHzXK4JYiw03QshZnu/NPnHWipAzfqOrWUiH8x1RNygMKmUHC1rN3dVkT9XKpKfxia5sNgXbLBDhbTbIaava/NDi4a7qpDqrsFvS30uIHEXmTp8tMbOlMYE2vnpNrmrtaNuAc56avGcarmwbFYaDeIvaY5nLW5FDUhqpboWOVmI/MNk7RBLyGwuVXd8rs85F+rqtR+M/mydCCZ3ALhiB+MHbxXC74n70Wou3LctR6IQRT4W7BqnhUQV4NDHVkhtotuz2zJo1CQgXSO9AgMBAAECggEADLXvUNLDl9Zq52+re8xof6dWFmoyUG2l5DVOVyM8s9dnHGUVApgDSv3lmT0S4Sqx4pIv6nDo/z0pnsq7WSVeekir5YSrmH7iq0kCrsezeachTF6T8CBe5GV9PEYCA/b7S2/Xjs/eBvrC6yE7ikYqkWQ7HMJqJaYLdLeDIq+0nm2RVk0Mkkqg8oNE83Q90WIoxK17/uXAwiPIxvI+9KCb9igoouFUVR2Suw3Se2P69bS8EHorCgEf6qbFaewuXYiukrs59kIszffINZ0sMjk2ZLOUgkl/d4BsvUjjx4PuanNY24ypm2n0sWyOU0kZKx8eEpNwdT0jc8NmwUu2eDMlgQKBgQD/aY53/dxANzCYrzmq9QSS08/jWj/UiBi1xKF08hdmMTbl2iUvbuiHp6yd6ojyngKwmQCl2G4ocQcxJhrO2qp3UNNfaDNSoGA1aG9f6ooUazjXor3t6qcoJZkcwRhTBBAGP5L1IhiY8nfyXvufHsEjanJjAsEIJhsnuxhM1CsUUQKBgQCYwD5I8C+qUU8AMLj/Qmucn6WQhCW8izZsyqCmxqJbJFffMyvlgBBBQy1gLIDaW2d5RjnxOQbJnGDDe2e5KP/EV/wwVPYPtn42+v+OXV/JRLu84LqPbwc833xoJvjiCda/B9y2SzWJ6dn9A+eS+Gv2WJqxBjSxInVE0KaWw1yZrQKBgQCC5GOg1rZzKlV7SgFxvVN0s5wMVYn0rzKKpJRhzFd1ha8uqFb6Mgp0ytdJrpI0VAno2uBcHciGbTZUV8TRUXqe3wsUD+CNbacgd77sFtRglnpw9/QB44l6p5oKBGMk2CYmLZpwieK1Lz2NNmQZLbepILoyR40JUg4ElHtllC3noQKBgFjVDCPc0suI55mjvCJunSiDy4RE5bFFgIw6Nr1mrQq2EwgxuSiJlmptx9izaOgT2hQ0HARF9sWO1NM6VDLhPgUQahAtiL/QkIwm4jIH6WmiOE/6nWRG3H2xNqdXaswBjsqfpuK40FT5E5XKDIVzRU4Jb0Td6AzPjkVL1mW9DYAtAoGBAOSTY9EKzKbDTwhqmO63ug/kYCHdFsDk/K5WN4xOA4mkTZA/o9gCWUuSCDz/vikaSthjv4goPIBoGMzqC9bbE/cVK6wLCuPmPSnh+EgFnrEi+CuJPJo67gaGspgqNZINalHUrt9EnlTenyk0SweB1ZC1LqFotBlwCILhTURG5q1M";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmGZ55m6M0GqJmCWaz8FBSnW7JBOEeoS+zImgejgxyg0QkfJj1kVndWyXVIWUMnfaZ2o+mdhB81yuCWIsNN0LIWZ7vzT5x1oqQM36jq1lIh/MdUTcoDCplBwtazd3VZE/VyqSn8YmubDYF2ywQ4W02yGmr2vzQ4uGu6qQ6q7Bb0t9LiBxF5k6fLTGzpTGBNr56Ta5q7WjbgHOemrxnGq5sGxWGg3iL2mOZy1uRQ1IaqW6FjlZiPzDZO0QS8hsLlV3fK7PORfq6rUfjP5snQgmdwC4YgfjB28Vwu+J+9FqLty3LUeiEEU+Fuwap4VEFeDQx1ZIbaLbs9syaNQkIF0jvQIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

