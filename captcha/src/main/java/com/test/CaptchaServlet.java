package com.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置字符集为utf-8
		req.setCharacterEncoding("UTF-8");
		// 禁止页面缓存
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "No-cache");
		resp.setDateHeader("Expires", 0);
		// 设置响应正文的MIME类型为图片
		resp.setContentType("image/jpeg");
		int width = 60, height = 20;//
		// 创建一个位于缓存中的图像，宽度为60，高度为20
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();// 获取用于处理图形上下文的对象，相当于画笔
		Random random = new Random();// 创建生成随机数的对象
		g.setColor(getRandomColor(150, 255));// 设置图像的背景色
		g.fillRect(0, 0, width, height);// 画一个矩形，坐标（0，0），宽度为60，高度为20
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));// 设定字体格式

		String strCode = "";
		// 将验证码依次画到图像上
		for (int i = 0; i < 4; i++) {
			String strNumber = String.valueOf(random.nextInt(10));
			strCode = strCode + strNumber;
			//设置字体的颜色
			g.setColor(getRandomColor(20, 150));
			g.drawString(strNumber, 13 * i + 6, 16);
		}
		req.getSession().setAttribute("captchaCode", strCode);// 把验证码保存到Session中
		for (int i = 0; i < 20; i++) {// 产生干扰线
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(20);
			int y1 = random.nextInt(20);
			g.setColor(getRandomColor(10, 255));
			//在图像的坐标x,y和坐标x+x1,y+y1之间画干扰线
			g.drawLine(x, y, x + x1, y + y1);
		}
		// 释放此图像的上下文以及它使用的所有系统资源
		g.dispose();
		// 输出JPEG格式的图像
		ImageIO.write(image, "JPEG", resp.getOutputStream());
		resp.getOutputStream().flush();
		resp.getOutputStream().close();

	}

	// 生成范围内的随机颜色
	public Color getRandomColor(int startRange, int endRange) {
		Random random = new Random();
		// 设置0~255之间的随机数颜色值
		if (startRange > 255)
			startRange = 255;
		if (endRange > 255)
			endRange = 255;
		int r = startRange + random.nextInt(endRange - startRange);
		int g = startRange + random.nextInt(endRange - startRange);
		int b = startRange + random.nextInt(endRange - startRange);
		return new Color(r, g, b);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}