package com.weii.pay.controller.message;

import com.alibaba.dubbo.config.annotation.Reference;
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
 * @author mumu
 * @version V1.0
 * @date Created in 17:10 2018/5/12
 * @Description:
 */
@RestController
public class MessageController {

    private static final Log log = LogFactory.getLog(MessageController.class);
    @Reference(version = "1.0.0")
    private TransactionMessageService transactionMessageService;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, PageParam pageParam, TransactionMessage message, Model model) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("areadlyDead", message.getAreadlyDead());
        paramMap.put("messageId", message.getMessageId());
        paramMap.put("consumerQueue", message.getConsumerQueue());
        paramMap.put("status", message.getStatus());

        PageBean pageBean = transactionMessageService.listPage(pageParam, paramMap);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("messageStatus", MessageStatusEnum.toList());
        model.addAttribute("queues", NotifyDestinationNameEnum.toList());
        return "message/list";
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
