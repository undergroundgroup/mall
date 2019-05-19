package com.market.mall.web;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.market.mall.bean.Admin;
import com.market.mall.bean.Mylist;
import com.market.mall.bean.Product;
import com.market.mall.bean.User;
import com.market.mall.dao.AdminMapper;
import com.market.mall.dao.ProductMapper;
import com.market.mall.dao.UserMapper;



@Controller
@SessionAttributes("loginedUser")
public class UserAction {
	
	@Resource
	private UserBiz ubiz;
	@Resource
	private UserMapper um;
	@Resource
	private AdminMapper am;
	@Resource
	private ProductMapper pm;
	
	String src;
	
	@GetMapping("adminlogin")
	public String tologin(@ModelAttribute Admin ad){
		return "mgr_login";
	}
	
	/**
	 * 切换用户
	 * @param ad
	 * @return
	 */
	@GetMapping("changeuser")
	public String changuser(@ModelAttribute Admin ad){
		return "mgr_login";
	}
	
	/**
	 * 退出登录
	 * @param ad
	 * @return
	 */
	@GetMapping("exit")
	public String exit(@ModelAttribute Admin ad,HttpServletRequest request){
		request.getSession().removeAttribute("loginedUser");
		return "mgr_login";
	}
	
	/**
	 * 用户列表展示
	 * @param ui
	 * @param model
	 * @return
	 */
	@GetMapping("mlist")
	public String ulist(@ModelAttribute User ui,Model model){
		model.addAttribute("ulist",ubiz.ulist());
		model.addAttribute("unumber",ubiz.u());
		return "member_list";
	}
	
	/**
	 * 管理员列表展示
	 * @param ad
	 * @param model
	 * @return
	 */
	@GetMapping("alist")
	public String alist(@ModelAttribute Admin ad,Model model){
		model.addAttribute("alist",ubiz.alist());
		model.addAttribute("anumber",ubiz.a());
		return "admin_list";
	}
	
	/**
	 * 商品列表展示
	 * @param pd
	 * @param model
	 * @return
	 */
	@GetMapping("plist")
	public String plist(@ModelAttribute Product pd,Model model){
		model.addAttribute("plist",ubiz.plist());
		model.addAttribute("pnumber",ubiz.p());
		return "product_brand";
	}
	
	@GetMapping("plist2")
	public String plist2(@ModelAttribute Product pd,Model model){
		model.addAttribute("plist",ubiz.plist());
		model.addAttribute("pnumber",ubiz.p());
		return "product_list";
	}
	
	/**
	 * 订单列表展示
	 * @param ml
	 * @param model
	 * @return
	 */
	@GetMapping("olist")
	public String plist2(@ModelAttribute Mylist ml,Model model){
		model.addAttribute("olist",ubiz.olist());
		model.addAttribute("onumber",ubiz.o());
		return "order_list";
	}
	
	/**
	 * 添加商品
	 * @return
	 */
	@GetMapping("product")
	public String addorder(){
		return "product_add";
	}
	
	/*@PostMapping("productadd")
	public String addprodect(Product p,HttpServletRequest request,Model model,@RequestParam("file") MultipartFile file) throws IOException{
		String pname=request.getParameter("pname");
		double price=Double.parseDouble(request.getParameter("price"));
		String info=request.getParameter("info");
		int count=Integer.parseInt(request.getParameter("count"));
		double ship=Double.parseDouble(request.getParameter("ship"));
		String type=request.getParameter("type");
		
		//上传文件路径
		String path = request.getServletContext().getRealPath("/loaded/");
		System.out.println("路径:"+path);
		//上传文件名
		String filename = file.getOriginalFilename();
		System.out.println("文件名称:"+filename);
	    File filepath = new File(path,filename);
	    String str = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
		//判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) { 
        	filepath.getParentFile().mkdirs();
        }
        String[] s=filename.split("\\.");
        filename = str + "." + s[1];
        //将上传文件保存到一个目标文件当中
		file.transferTo(new File(path + File.separator + filename));
		
		src = path + filename;
		System.out.println(src);
		
		
		Date t=new Date();
		java.sql.Timestamp sqlts = new java.sql.Timestamp(t.getTime());	
		p.setPname(pname);
		p.setPrice(price);
		p.setInfo(info);
		p.setCount(count);
		p.setShip(ship);
		p.setDate(sqlts);
		p.setType(type);
		p.setImage(src);
		pm.insert(p);
		return "mgr_index";		
	}*/
	
	/*@RequestMapping("uploadImage")
	public String addImage(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws Exception{
				//上传文件路径
				String path = request.getServletContext().getRealPath("/loaded/");
				System.out.println("路径:"+path);
				//上传文件名
				String filename = file.getOriginalFilename();
				System.out.println("文件名称:"+filename);
			    File filepath = new File(path,filename);
			    String str = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
				//判断路径是否存在，如果不存在就创建一个
		        if (!filepath.getParentFile().exists()) { 
		        	filepath.getParentFile().mkdirs();
		        }
		        String[] s=filename.split("\\.");
		        filename = str + "." + s[1];
		        //将上传文件保存到一个目标文件当中
				file.transferTo(new File(path + File.separator + filename));
				
				src = path + filename;
				System.out.println(src);
				
//				String path = request.getServletContext().getRealPath("/loaded/");
				
				return "success";
				
			}*/
	
		//上传文件会自动绑定到MultipartFile中
		@RequestMapping(value="uploadImage",method=RequestMethod.POST)
		public String upload(HttpServletRequest request,
				@RequestParam("file") MultipartFile file) throws Exception {
			
		    //如果文件不为空，写入上传路径
			if(!file.isEmpty()) {

				//上传文件路径
				String path = request.getServletContext().getRealPath("/images/");
				System.out.println("路径:"+path);
				//上传文件名
				String filename = file.getOriginalFilename();
				System.out.println("文件名称:"+filename);
			    File filepath = new File(path,filename);
			    String str = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
				//判断路径是否存在，如果不存在就创建一个
		        if (!filepath.getParentFile().exists()) { 
		        	filepath.getParentFile().mkdirs();
		        }
		        String[] s=filename.split("\\.");
		        filename = str + "." + s[1];
		        //将上传文件保存到一个目标文件当中
				file.transferTo(new File(path + File.separator + filename));
				
				/*src = path + filename;*/
				src=filename;
				System.out.println(filename);
				System.out.println(src);
				
		//		String path = request.getServletContext().getRealPath("/loaded/");
				
		//		return "success";
				
			}
			return "product_brand";
				
		 }
		 
		 @RequestMapping(value="productadd",method=RequestMethod.POST)
		 public String productupload(HttpServletRequest request,Product p,Model model){
			 	String pname = request.getParameter("pname");
				double price = Double.parseDouble(request.getParameter("price"));
				Integer count = Integer.parseInt(request.getParameter("count"));
				double ship = Double.parseDouble(request.getParameter("ship"));
				String type = request.getParameter("type");
				String info = request.getParameter("info");
				Date date= new java.sql.Date(new java.util.Date().getTime());
				p.setPname(pname);
				p.setPrice(price);
				p.setInfo(info);
				
				p.setCount(count);
				p.setState(1);
				p.setShip(ship);
				
				p.setImage(src);
				p.setDate(date);
				p.setType(type);
				pm.insert(p);
				
				model.addAttribute("plist",ubiz.plist());
				model.addAttribute("pnumber",ubiz.p());
				
				return "product_brand";
		 }
	
	/**
	 * 添加管理员
	 * @return
	 */
	@GetMapping("admin")
	public String addadmin(){
		return "admin_add";
	}
	
	@GetMapping("adminadd")
	public String addAdmin(HttpServletRequest request, @RequestParam("username") String username){
		String password=request.getParameter("password");
		String password2=request.getParameter("password2");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		System.out.println(username);
		if(password2.equals(password)){
			Admin a=new Admin();
			a.setUsername(username);
			a.setPassword(password);
			a.setPhone(phone);
			a.setEmail(email);
			a.setState(1);
			am.insert(a);
			return "admin_list";
		}else{
			request.setAttribute("msg", "两次密码不匹配");
			return "mgr_index";
		}	
	}
	
	/**
	 * 登录
	 * @param ad
	 * @param errors
	 * @param model
	 * @return
	 */
	@PostMapping("mgr_login")
	public String login(@ModelAttribute @Valid Admin ad,Errors errors,Model model){
		
		if(errors.hasErrors()){
			return "mgr_login";
		}
		
		try {
			Admin db=ubiz.login(ad);
			model.addAttribute("loginedUser",db);
			return "mgr_index";
		} catch (BizException e) {
			e.printStackTrace();
			model.addAttribute("msg",e.getMessage());
			return "mgr_login";
		}
	}
		
	
	
}
