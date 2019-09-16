package com.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("name");
		String password = req.getParameter("password");
		String code = req.getParameter("code");
		if("admin".equals(user) && "admin".equals("admin") && code.equals(req.getSession().getAttribute("captchaCode"))) {
			req.getRequestDispatcher("/success.jsp").forward(req, resp);
		}else {
			req.getRequestDispatcher("/fail.jsp").forward(req, resp);
		}
	}

}
