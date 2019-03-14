package com.csthink.bbs.servlet;

import com.csthink.bbs.entity.Message;
import com.csthink.bbs.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page"); // 获取当前页码
        int total = messageService.countMessages(); // 统计留言总数
        int pageSize = 5; // 每页显示记录数
        int lastPage = (total % pageSize == 0) ? (total / pageSize) : (total / pageSize + 1); // 计算出最后一页的页码
        int page = 1;
        if (null != pageStr && !"".equals(pageStr.trim())) {
            try {
                page = Integer.parseInt(pageStr);
//                page = Integer.valueOf(pageStr);
                page = page > lastPage ? lastPage : ((page <= 0) ? 1 : page);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // 获取消息记录列表
        List<Message> messageList = messageService.getMessages(page, pageSize);

        // 分页
        List<Integer> pageList = new ArrayList<Integer>();
        for (int i = 1; i <= lastPage; i++) {
            pageList.add(i);
        }

        req.setAttribute("messageList", messageList);
        req.setAttribute("page", page);
        req.setAttribute("pageList", pageList);
        req.setAttribute("lastPage", lastPage);
        req.setAttribute("total", total);
        req.getRequestDispatcher("/WEB-INF/views/biz/message_list.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        messageService = null;
    }
}
