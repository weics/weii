package com.weii.admin.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.common.pojo.WeiiResult;
import com.weii.pay.common.core.enums.NotifyDestinationNameEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.message.api.TransactionMessageService;
import com.weii.pay.service.message.entity.TransactionMessage;
import com.weii.pay.service.message.enums.MessageStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 21:49 2018/8/23
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("message")
public class MessageController {


    private static final Log log = LogFactory.getLog(MessageController.class);
    @Reference(version = "1.0.0")
    private TransactionMessageService transactionMessageService;

    @RequestMapping(value = "/list")
    public Object list(HttpServletRequest request, PageParam pageParam, TransactionMessage message) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("areadlyDead", message.getAreadlyDead());
        paramMap.put("messageId", message.getMessageId());
        paramMap.put("consumerQueue", message.getConsumerQueue());
        paramMap.put("status", message.getStatus());

        PageBean pageBean = transactionMessageService.listPage(pageParam, paramMap);

        Map<String,Object> map = new HashMap<>(3);
        map.put("pageBean", pageBean);
        map.put("messageStatus", MessageStatusEnum.toList());
        map.put("queues", NotifyDestinationNameEnum.toList());
        return WeiiResult.ok(map);
    }

    @RequestMapping(value = "/sendMessage")
    public String sendMessage(String messageId, Model model) {

        transactionMessageService.reSendMessageByMessageId(messageId);
//        DwzAjax dwz = new DwzAjax();
//        try {
//            transactionMessageService.reSendMessageByMessageId(messageId);
//            dwz.setStatusCode(DWZ.SUCCESS);
//            dwz.setNavTabId("xxlb");
//            dwz.setMessage("操作成功");
//            model.addAttribute("dwz", dwz);
//            return DWZ.AJAX_DONE;
//
//        } catch (Exception e) {
//            dwz.setStatusCode(DWZ.ERROR);
//            dwz.setMessage("退出系统时系统出现异常，请通知系统管理员！");
//            model.addAttribute("dwz", dwz);
//            return DWZ.AJAX_DONE;
//        }
        return null;
    }

    /**
     * 一键触发发送某个消息队列全部已死亡的消息
     *
     * @param queueName
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendAllMessage")
    public String sendAllMessage(String queueName, Model model) {
//        DwzAjax dwz = new DwzAjax();
//        try {
//            if (StringUtil.isEmpty(queueName)) {
//                dwz.setStatusCode(DWZ.ERROR);
//                dwz.setMessage("请选择相应的队列名称");
//                model.addAttribute("dwz", dwz);
//                return DWZ.AJAX_DONE;
//            }
        transactionMessageService.reSendAllDeadMessageByQueueName(queueName, 2000);
//            dwz.setStatusCode(DWZ.SUCCESS);
//            dwz.setNavTabId("xxlb");
//            dwz.setMessage("操作成功");
//            model.addAttribute("dwz", dwz);
//            return DWZ.AJAX_DONE;
//
//        } catch (Exception e) {
//            dwz.setStatusCode(DWZ.ERROR);
//            dwz.setMessage("退出系统时系统出现异常，请通知系统管理员！");
//            model.addAttribute("dwz", dwz);
//            return DWZ.AJAX_DONE;
//        }

        return null;
    }

}
