package com.ziniu.pay.web;

import com.ziniu.pay.conf.HFBConf;
import com.ziniu.pay.entity.PayLogs;
import com.ziniu.pay.entity.ReturnError;
import com.ziniu.pay.entity.hfb.PayInfo;
import com.ziniu.pay.entity.hfb.PayInfoDetail;
import com.ziniu.pay.entity.hfb.PayInfoDetailToSee;
import com.ziniu.pay.service.JDBCService;
import com.ziniu.pay.service.WebSocketService;
import com.ziniu.pay.util.AnalysisDOM4J;
import com.ziniu.pay.util.DealStringUtils;
import com.ziniu.pay.util.Result;
import com.ziniu.pay.util.hfb.Des.Des;
import com.ziniu.pay.util.hfb.HttpUtil;
import com.ziniu.pay.util.hfb.SmallTools;
import com.ziniu.pay.vo.ResponseDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 测试后台websocket客户端
 */
@Slf4j
@RestController
@RequestMapping("/websocket")
public class IndexController {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private JDBCService jdbcService;


    @Resource
    HttpServletRequest request;

    @Resource
    HttpServletResponse response;

    /**
     * http://localhost:8080/websocket/sendMessage
     *
     * @param message
     * @return
     */
    @PostMapping("/sendMessage")
    @ResponseBody
    public Result sendMessage(@RequestBody Map<String, String> message) {
        String sendMsg = message.get("message");
        String[] split = sendMsg.split("#");
        boolean isLog = true;
        //查询不加日志
        if (split[0].equals("xhj5002") || split[0].equals("xhj5001")) {
            isLog = false;
        }
        String reveiveMsg = webSocketService.groupSending(sendMsg);
        if (reveiveMsg == null && isLog) {
            log.error("请求返回null值..." + sendMsg);
            String resPonse = "";
            String resData = null;
            ResponseDataVo responseDataVo = new ResponseDataVo();
            responseDataVo.setReturnError(null);
            responseDataVo.setOtherData(resData);
            addLog(sendMsg, reveiveMsg, resPonse, "1");
            throw new IllegalStateException("request fail!");
//            Result result = new Result(resPonse, null, responseDataVo);
//            return result;
        } else {
            String resPonse = DealStringUtils.getResPonse(reveiveMsg);
            String resData = DealStringUtils.getResData(reveiveMsg);
            ReturnError returnError = jdbcService.selectByCode(resPonse);
            ResponseDataVo responseDataVo = new ResponseDataVo();
            responseDataVo.setReturnError(returnError);
            responseDataVo.setOtherData(resData);
            if (isLog) {
                addLog(sendMsg, reveiveMsg, resPonse, "1");
            }
            Result result = new Result(resPonse, returnError == null ? null : returnError.getErrorDesc(), responseDataVo);
            return result;
        }
    }

    private void addLog(String sendMsg, String reveiveMsg, String resPonse, String s) {
        PayLogs payLogs = new PayLogs();
        payLogs.setCreateTime(new Date());
        payLogs.setReqContent(sendMsg);
        payLogs.setReqType(s);
        payLogs.setResCode(resPonse);
        payLogs.setResResult(reveiveMsg);
        jdbcService.insertLogs(payLogs);
    }

    /**
     * http://localhost:8080/websocket/sendMessage
     *
     * @param payInfo
     * @return
     */
    @PostMapping("/hfb_pay")
    @ResponseBody
    public Result payHFB(@RequestBody PayInfo payInfo) {
        String s = payInfo.toBuildParamter();
        String amt = payInfo.getBatch_amt();
        String url = HFBConf.URL_SMALL;
        BigDecimal b1 = new BigDecimal(amt);
        BigDecimal b2 = new BigDecimal("50000");
        int c = b1.compareTo(b2);
        //bi大于等于b2，走大额通道
        if (c >= 0) {
            url = HFBConf.URL_BIG;
        }
        String retu = HttpUtil.sendPost(url, s);
        Map<String, Object> map = AnalysisDOM4J.parserDOM4ForStrXml(retu);
        addLog(s, retu, (String) map.get("ret_code"), "2");
        Result result = new Result("0", null, map);
        return result;
    }

    @GetMapping("/hfb_notice")
    @ResponseBody
    public void noticeHFB(PayInfo payInfo) {
        String str = request.getQueryString();
        System.out.println("接收到的参数：" + str);
        //result=1&agent_id=1234567&jnet_bill_no=B20100225132210&agent_bill_id=20100225132210&
        //接收异步通知参数
        String ret_code = request.getParameter("ret_code");//返回码值0000 表示查询成功
        String ret_msg = request.getParameter("ret_msg");//返回码信息提示
        String agent_id = request.getParameter("agent_id");//商户ID
        String hy_bill_no = request.getParameter("hy_bill_no");//汇付宝订单号
        String status = request.getParameter("status");//-1=无效，0=未处理，1=成功
        String batch_no = request.getParameter("batch_no");//商户系统订单号
        String batch_amt = request.getParameter("batch_amt");//成功付款金额
        String batch_num = request.getParameter("batch_num");//成功付款数量
        String detail_data = request.getParameter("detail_data");//付款明细
        String ext_param1 = request.getParameter("ext_param1");//商户自定义参数，透传参数
        String sign = request.getParameter("sign");//签名

        String key = HFBConf.MD5;
        String DesKey = HFBConf.DES_KEY;
        //解密detail_data
        detail_data = Des.Decrypt3Des(detail_data, DesKey, "ToHex16");
        //验签
        String b_sign = "ret_code=" + ret_code +
                "&ret_msg=" + ret_msg +
                "&agent_id=" + agent_id +
                "&hy_bill_no=" + hy_bill_no +
                "&status=" + status +
                "&batch_no=" + batch_no +
                "&batch_amt=" + batch_amt +
                "&batch_num=" + batch_num +
                "&detail_data=" + detail_data +
                "&ext_param1=" + ext_param1 +
                "&key=" + key;
        b_sign = SmallTools.MD5en(b_sign);
        PayLogs payLogs = new PayLogs();
        payLogs.setCreateTime(new Date());
        payLogs.setReqContent(str);
        payLogs.setReqType("3");
        try (
                PrintWriter out = response.getWriter();
        ) {
            if (b_sign.equals(sign)) {
                payLogs.setResCode("0");
                payLogs.setResResult("签名验证成功");
                jdbcService.insertLogs(payLogs);
                System.out.println("签名验证成功");
                out.print("ok");
                out.flush();
                out.close();
            } else {
                payLogs.setResCode("1");
                payLogs.setResResult("签名验证失败");
                jdbcService.insertLogs(payLogs);
                System.out.println("签名验证失败");
                out.print("error");
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 汇付宝查询 - 暂不加日志
     *
     * @param payInfo
     * @return
     */
    @GetMapping("/hfb_query")
    @ResponseBody
    public Result queryHFB(PayInfo payInfo) {
        String s = payInfo.toSelect();
        String retu = HttpUtil.sendPost(HFBConf.QUERY_URL, s);
        log.info("retu:|", retu, "|");
        if (retu == null || retu.trim().equals("")) {
            Result result = new Result("0", null, null);
            return result;
        } else {
            Map<String, Object> map = AnalysisDOM4J.parserDOM4ForStrXml(retu);
            String detailData = (String) map.get("detail_data");
            if (!StringUtils.isEmpty(detailData)) {
                detailData = Des.Decrypt3Des(detailData, HFBConf.DES_KEY, "ToHex16");
                log.info("detailData:" + detailData);
                List<PayInfoDetailToSee> payInfoDetailToSees = PayInfoDetailToSee.analyseDetail(detailData);
                payInfoDetailToSees.forEach((item) -> log.info("item:" + item.toString()));
                map.put("detail_data", payInfoDetailToSees);
            }
            Result result = new Result("0", null, map);
            return result;
        }
    }

    /**
     * 汇付宝查询 - 暂不加日志
     * @return
     */
    @GetMapping("/test")
    @ResponseBody
    public Result test() {
        Result result = new Result("0", null, null);
        return result;
    }
}
