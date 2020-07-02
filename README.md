# lf-pay-hfb-hx
汇付宝及华夏银行转账对接

# 1.test目录：
    
    1.test目录下，分别是各类基初数据的准备，文件存在于lf-pay-hfb-hx\src\main\resources\data
    2.需要分开执行将基础数据导入
    
# 2.汇付宝测试用例：

## 2.1汇付宝-支付：
    
    url：http://localhost:8888/websocket/hfb_pay    
    param：
        {
            "batch_no": "2018101010101044",
            "batch_amt":"0.01",
            "batch_num":"1",
            "detail_data":[
                {
                    "child_batch_no":"A1234596",
                    "bank_no":"3",
                    "type":"0",
                    "account":"6228484018396408XXX",
                    "name":"李四",
                    "money":"0.01",
                    "reason":"代理佣金",
                    "province":"甘肃省",
                    "city":"金昌市",
                    "bank_name":"中国农业银行股份有限公司金昌金川支行"
                }
            ],
            "ext_param1":"ccode"
        }
    return:
        {
            "code": "0",
            "msg": null,
            "data": {
                "sign": "c00cbe178e28c53e02fa821e39c1b169",
                "ret_msg": "detail_data 参数，商户未设置相应付款理由",
                "ret_code": "E104"
            }
        }
## 2.2汇付宝-查询：
    
    url：http://localhost:8888/websocket/hfb_query?batch_no=2018101010101044
    return：
        {
            "code": "0",
            "msg": null,
            "data": {
                "ext_param1": "ccode",
                "batch_no": "2018101010101044",
                "agent_id": "1664502",
                "sign": "b9e8d892b1397f82248453d4518e7cdd",
                "hy_bill_no": "11973269",
                "ret_msg": "",
                "batch_num": "0",
                "ret_code": "0000",
                "batch_amt": "0.00",
                "detail_data": [
                    {
                        "child_batch_no": "A1234596",
                        "account": "6217000010100164865",
                        "name": "张三",
                        "money": "0.0100",
                        "code": "F",
                        "reason": "交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配"
                    },
                    {
                        "child_batch_no": "A12345610",
                        "account": "6217000010100164865",
                        "name": "张三",
                        "money": "0.0100",
                        "code": "F",
                        "reason": "交易失败: YBLA0431WL01提现不成功,客户户名与预留的户名不匹配"
                    }
                ]
            }
        }
# 3.华夏银行测试用例：
## 3.1 公对私转账：
    
    url:http://localhost:8888/websocket/sendMessage
    param:
        {
            "message": "xhj1001#1153285#201310230099112#10250001002171482#500#1#对私#1|6230200011348377|个人网银12|500|j|#@@@@"
        }
    return:
        {
            "code": "000000",
            "msg": "交易成功",
            "data": {
                "returnError": {
                    "id": 29179,
                    "errorCode": "000000",
                    "errorDesc": "交易成功",
                    "errorOwner": 0,
                    "errorType": 1
                },
                "otherData": "#201310230099112##"
            }
        }
 
 ## 3.2结果查询：
    
    视文档而定

# 4.注意：
    
    lf-pay-hfb-hx\src\main\java\com\ziniu\pay\conf\HFBConf.java
    上面的配置项，可以提到application.properties中，需要修改
