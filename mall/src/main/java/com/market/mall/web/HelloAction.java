package com.market.mall.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.mall.bean.Cache;
import com.market.mall.bean.Product;
import com.market.mall.bean.User;
import com.market.mall.dao.DBHelper;
import com.market.mall.dao.ProductMapper;
import com.market.mall.dao.UserMapper;

@Controller
public class HelloAction {
	
	@Resource
	private ProductMapper pm;
	
	@Resource
	private UserMapper um;
	
	
	@RequestMapping("welcome")
	public String hello(){
		return "index";
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request){
		String uname = request.getParameter("uname");
		String upwd = request.getParameter("upwd");
		User user = um.login(uname,upwd);
		if (user!=null){
			request.getSession().setAttribute("user",user);
		}else{
			request.setAttribute("msg", "登录失败，请确认用户名或密码是否正确");
		}
		return "index";
	}
	
	@RequestMapping("reg")
	public String register(HttpServletRequest request,Model model){
		String uname=request.getParameter("uname");
		String email=request.getParameter("email");
		String upwd = request.getParameter("upwd");
		User u=um.selectByUname(uname);
		if(u==null){
			int result=um.register(uname, email, upwd);
			if(result>0){
				request.setAttribute("msg", "注册成功");
				return "index";
			}else{
				request.setAttribute("msg", "注册失败");
			}
		}else{
			request.setAttribute("msg", "该用户已存在");
		}
		
		return "index";
		
	}
	@RequestMapping("quit")
	public String quit(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		request.setAttribute("msg", "已退出");
		return "index";
	}
	
	@RequestMapping("shopindex")
	public String mallindex(){
		return "product";
	}
	
	
	@RequestMapping(value="confirm", method=RequestMethod.POST)
	public String submit(@RequestParam("size") int size, HttpServletRequest request){
		request.getSession().removeAttribute("cartsList");
		DBHelper.update("delete from cache");
		DBHelper.update("alter table cache AUTO_INCREMENT=1");
		for(int i = 1; i <= size; i++){
			String item = request.getParameter("item_name_"+i);
			String amount = request.getParameter("quantity_"+i);
			Product pro = pm.selectByName(item);
			System.out.println(pro);
			double a = Double.parseDouble(amount);
			double p = pro.getPrice();
			double money = p-a;
			String sql2 = "insert into cache values(?,?,?,?,?,?)";
			DBHelper.update(sql2, pro.getPname(), amount, pro.getPrice(), pro.getImage(), null, money);
		}
		List<Cache> ca =  DBHelper.select("select * from cache", Cache.class);
		request.getSession().setAttribute("cartsList", ca);
		return "redirect:tomylist";
	}
	
	@RequestMapping("tomylist")
	public String tomylist(){
		return "checkout";
	}
	
	@RequestMapping("query")
	public String query(HttpServletRequest request){
		List<Cache> ca =  DBHelper.select("select * from cache", Cache.class);
		request.setAttribute("cartsList", ca);
		return "checkout";
	}
	
	@RequestMapping("del")
	public String del(HttpServletRequest request){
		String pid = request.getParameter("id");
		String sql = "delete from cache where pid=?";
		int result = DBHelper.update(sql, pid);
		return "redirect:tomylist";
	}
	
}
