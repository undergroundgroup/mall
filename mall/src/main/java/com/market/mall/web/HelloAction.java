package com.market.mall.web;

import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.market.mall.bean.Cache;
import com.market.mall.bean.Mylist;
import com.market.mall.bean.Product;
import com.market.mall.bean.ProductExample;
import com.market.mall.bean.User;
import com.market.mall.bean.isOne;
import com.market.mall.dao.DBHelper;
import com.market.mall.dao.MylistMapper;
import com.market.mall.dao.ProductMapper;
import com.market.mall.dao.UserMapper;
import com.market.mall.util.Data;
import com.market.mall.util.MyUtils;
import com.market.mall.dao.Picture;

@Controller
public class HelloAction {
	
	@Resource
	private ProductMapper pm;
	
	@Resource
	private UserMapper um;
	
	@Resource
	private MylistMapper mm;
	
	
	@RequestMapping("welcome")
	public String hello(){
		return "index";
	}
	
	@RequestMapping("returnhome")
	public String returnhome(){
		return "index";
	}
	/**
	 * 登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		String uname = request.getParameter("uname");
		String upwd = request.getParameter("upwd");
		User user = um.login(uname,upwd);
		if (user!=null){
			request.getSession().setAttribute("user",user);
			return "index";
		}else{
			request.setAttribute("msg", "登录失败，请确认用户名或密码是否正确");
		}
		return "index";
	
	}
	
	/**
	 * 显示图片到网页
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
		//设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/jpeg");
		//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		Picture picture = new Picture();
		try {
			picture.getRandcode(request, response);//输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册
	 * @param u
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/reg")
	public String register(User u,HttpServletRequest request,Model model){
		String uname=request.getParameter("uname");
		String email=request.getParameter("email");
		String upwd = request.getParameter("upwd");
		String code = request.getParameter("code");
		String ccode=(String) request.getSession().getAttribute("checkcode");
		System.out.println(ccode);
		
		
		if(code.equalsIgnoreCase(ccode)){
			User uu=um.selectByUname(uname);
			if(uu==null){
				int result=um.register(uname, email, upwd);
				if(result>0){
					request.setAttribute("msg", "注册成功");
				}else{
					request.setAttribute("msg", "注册失败");
				}
			}else{
				request.setAttribute("msg", "该用户已存在");
			}
		}else{
			request.setAttribute("msg", "验证码错误");
		}	
		return "index";
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping("quit")
	public String quit(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		request.setAttribute("msg", "已退出");
		return "index";
	}
	
	/**
	 * 产品展示
	 * @return
	 */
	@RequestMapping("shopindex")
	public String mallindex(Model model,HttpServletRequest request){
		List<Product> producttype = pm.selectByExample(null);
		model.addAttribute("productitem", producttype);
		System.out.println(producttype);
		return "product";
	}
	
	/**
	 * 加入购物车
	 * @param size
	 * @param request
	 * @return
	 */
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
	
	/**
	 * 清空购物车
	 * @param request
	 * @return
	 */
	@RequestMapping("del")
	public String del(HttpServletRequest request){
		String pid = request.getParameter("id");
		String sql = "delete from cache where pid=?";
		int result = DBHelper.update(sql, pid);
		return "redirect:tomylist";
	}
	
	/**
	 * 支付
	 * @param request
	 * @param uname
	 * @param tel
	 * @param addr
	 * @param city
	 * @return
	 */
	@RequestMapping("payfor")
	public String pay(HttpServletRequest request, @RequestParam("name") String uname,
			@RequestParam("number") String tel, @RequestParam("landmark") String addr, @RequestParam("city") String city){
		List<Cache> ca =  DBHelper.select("select * from cache", Cache.class);
		List<Mylist> or = DBHelper.select("select * from Mylist", Mylist.class);
		isOne.rmb = 0;
		if(or.size() == 0){
			isOne.isOne = 1;
		}else{
			isOne.isOne = or.get(or.size()-1).getIsone() + 1;
		}
		User u = um.selectByUname(uname);
		int uid = u.getUid();
		for(int i = 0; i < ca.size(); i++){
			int pid = ca.get(i).getPid();   //获取pid
			int amount = Integer.parseInt(ca.get(i).getAmount());
			float money = (float) (ca.get(i).getMoney()*amount);
			isOne.rmb = money+isOne.rmb;  //获取money
			Date time= new java.sql.Date(new java.util.Date().getTime());
			Mylist o = new Mylist();
			o.setUid(uid);
			o.setPid(pid);
			o.setBeginTime(time);
			o.setMoney(money);
			o.setCreateTime(time);
			o.setStatus("0");
			o.setAmount(amount);
			o.setIsone(isOne.isOne);
			mm.insert(o);
			
			request.getSession().setAttribute("money",isOne.rmb );
		}
		u.setAddr(addr);
		u.setTel(tel);
		um.updateByPrimaryKey(u);
		request.getSession().removeAttribute("user");
		request.getSession().setAttribute("user", u);
		return "payment";
	}
	
	
	/**
	 * 找回密码
	 * @param request
	 * @return
	 */
	@RequestMapping("forget")
	public String findpwd(HttpServletRequest request){
		String uname=request.getParameter("uname");
		String email=request.getParameter("email");
		String code=request.getParameter("code");
		String upwd=request.getParameter("pwd");
		String rpwd=request.getParameter("rpwd");
		Data d=new Data();
		System.out.println(d.code);
		if(code.equals(d.code)){
    		if(rpwd.equals(upwd)){
    			int result=um.updatePwd(upwd,email,uname);
        		if(result>0){
        			request.setAttribute("msg", "修改成功！");
        			d.code="";
        			return "index";
        		}else{
        			request.setAttribute("msg", "修改失败,请确认用户名是否错误！");
        		}
    		}else{
    			request.setAttribute("msg", "两次输入的密码不匹配");
    		}
    	}else{
    		request.setAttribute("msg", "验证码错误，请确认是否输入错误");
    	}
		return "index";
	}
	
	
	/**
	 * 发送邮件
	 * @param request
	 */
	@RequestMapping("SendCode")
	@ResponseBody
	public void sendMail(HttpServletRequest request){
		String email=request.getParameter("email");
		MyUtils mu=new MyUtils();
		mu.sendMail(email);
		Data d=new Data();
		System.out.println(d.code);
	}
	
	/**
	 * 支付
	 * @return
	 */
	@RequestMapping("payment")
	public String payment(){
		return "payment";
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	@RequestMapping("single")
	public String single(){
		return "single";
	}
	
	/**
	 * 个人信息修改
	 * @return
	 */
	@RequestMapping("personal")
	public String change(HttpServletRequest request){
		String uname=request.getParameter("uname");
		String email=request.getParameter("email");
		String addr=request.getParameter("addr");
		String tel=request.getParameter("tel");
		String sex=request.getParameter("sex");
		System.out.println(uname);
		int result=um.updatepersonal(email,addr,tel,sex,uname);
		if(result>0){
			User user = um.selectByUname(uname);
			request.getSession().removeAttribute("user");
			request.getSession().setAttribute("user", user);
			return "index";
		}else{
			request.setAttribute("msg", "修改失败");
		}
		return "index";
	}
	
	/**
	 * 购买方式1:货到付款
	 * @return
	 */
	@RequestMapping("payway1")
	public String payway1(HttpServletRequest request){
		List<Mylist> list = mm.selectByisOne(isOne.isOne);
		String state = "1";
		String pay = "货到付款";
		for(int i=0; i<list.size(); i++){
			Mylist m = new Mylist();
			m.setPay(pay);
			m.setStatus(state);
			m.setIsone(isOne.isOne);
			mm.updateByisOne(m);
		}
		request.setAttribute("msg", "下单成功！请等待发货");
		return "index";
	}
	
	/**
	 * 支付宝支付
	 * @param request
	 * @return
	 */
	@RequestMapping("pay")
	public String payfor(HttpServletRequest request){
		String oid=request.getParameter("WIDout_trade_no");
		String oname=request.getParameter("WIDsubject");
		String money=request.getParameter("WIDtotal_amount");
		System.out.println(oid);
		System.out.println(oname);
		System.out.println(money);	
		List<Mylist> list = mm.selectByisOne(isOne.isOne);
		String state = "1";
		String pay = "支付宝付";
		for(int i=0; i<list.size(); i++){
			Mylist m = new Mylist();
			m.setPay(pay);
			m.setStatus(state);
			m.setIsone(isOne.isOne);
			mm.updateByisOne(m);
		}
		return "alipay.trade.page.pay";
	}
	
	
	
	/**
	 * 购买方式2:银行卡付款
	 * @return
	 */
	@RequestMapping("payway2")
	public String payway2(HttpServletRequest request){
		List<Mylist> list = mm.selectByisOne(isOne.isOne);
		String state = "1";
		String pay = "银行卡付款";
		for(int i=0; i<list.size(); i++){
			Mylist m = new Mylist();
			m.setPay(pay);
			m.setStatus(state);
			m.setIsone(isOne.isOne);
			mm.updateByisOne(m);
		}
		request.setAttribute("msg", "付款成功！");
		return "index";
	}
	
	/**
	 * 购买方式3:网上银行支付
	 * @return
	 */
	@RequestMapping("payway3")
	public String payway3(HttpServletRequest request){
		List<Mylist> list = mm.selectByisOne(isOne.isOne);
		String state = "1";
		String pay = "网上银行付款";
		for(int i=0; i<list.size(); i++){
			Mylist m = new Mylist();
			m.setPay(pay);
			m.setStatus(state);
			m.setIsone(isOne.isOne);
			mm.updateByisOne(m);
		}
		request.setAttribute("msg", "付款成功！");
		return "index";
	}
	
	/**
	 * 购买方式4:网上银行支付
	 * @return
	 */
	@RequestMapping("payway4")
	public String payway4(HttpServletRequest request){
		List<Mylist> list = mm.selectByisOne(isOne.isOne);
		String state = "1";
		String pay = "Paypal";
		for(int i=0; i<list.size(); i++){
			Mylist m = new Mylist();
			m.setPay(pay);
			m.setStatus(state);
			m.setIsone(isOne.isOne);
			mm.updateByisOne(m);
		}
		request.setAttribute("msg", "付款成功！");
		return "index";
	}
	
}
