package com.market.mall.web;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.market.mall.bean.Product;
import com.market.mall.bean.ProductExample;
import com.market.mall.dao.ProductMapper;


@Controller
public class ProductAction {
	
	@Resource
	private ProductMapper productMapper;
	

	/**
	 * 根据类型显示
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("shopindex1")
	public String shopindex1(Model model,HttpServletRequest request){
		String type = request.getParameter("type");
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andTypeEqualTo(type);
		List<Product> producttype = productMapper.selectByExample(productExample);
		model.addAttribute("productitem", producttype);
		System.out.println(producttype);
		return "product";
	}
	
	/**
	 * 显示商品详情
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("details")
	public String showproduct(Model model,HttpServletRequest request) throws ParseException{
		String name = request.getParameter("pname");
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andPnameEqualTo(name);
		List<Product> productdetails = productMapper.selectByExample(productExample);
		model.addAttribute("detailsproduct", productdetails);
		System.out.println(productdetails);
		
		/**
		 * 根据最新上线查（3天内）
		 */
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间"+df1.format(new Date()));
        Date date1=df1.parse(df1.format(new Date()));
        
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3); //得到前一天
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("前三天时间"+df.format(date));
		Date date2=df.parse(df.format(date));
		
		ProductExample pe=new ProductExample();
		pe.createCriteria().andDateBetween(date2, date1);
		
		List<Product> producttype1=productMapper.selectByExample(pe);
		model.addAttribute("newproduct", producttype1);
		
		return "single";
	}
	
	/**
	 * 查询所有，模糊查询
	 * @param search
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="Queryall",method=RequestMethod.POST)
	public String Queryall(@RequestParam("search") String search,HttpServletRequest request,Model model){
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andPnameLike("%"+search+"%");
		List<Product> producttype = productMapper.selectByExample(productExample);
		model.addAttribute("productitem", producttype);
		System.out.println(producttype);
		return "product";
	}
	
	/**
	 * 查询所有
	 * @param search
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="Found",method=RequestMethod.POST)
	public String Found(@RequestParam("find") String search,HttpServletRequest request,Model model){
		ProductExample productExample=new ProductExample();
		if("find"==null&&"find".trim().isEmpty()==true){
			List<Product> producttype1=productMapper.selectByExample(null);
			model.addAttribute("productitem", producttype1);
			return "product";
		}else{
			productExample.createCriteria().andPnameLike("%"+search+"%");
			List<Product> producttype = productMapper.selectByExample(productExample);
			model.addAttribute("productitem", producttype);
			System.out.println(producttype);
			return "product";
		}
	}
	
	/**
	 * 展示最新商品
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("new")
	public String newproduct(Model model,HttpServletRequest request) throws ParseException{
		String name = request.getParameter("pname");
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andPnameEqualTo(name);
		List<Product> productdetails = productMapper.selectByExample(productExample);
		model.addAttribute("detailsproduct", productdetails);
		System.out.println(productdetails);
		
		/**
		 * 根据最新上线查（3天内）
		 */
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间"+df1.format(new Date()));
        Date date1=df1.parse(df1.format(new Date()));
        
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3); //得到前一天
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("前三天时间"+df.format(date));
		Date date2=df.parse(df.format(date));
		
		ProductExample pe=new ProductExample();
		pe.createCriteria().andDateBetween(date2, date1);
		
		List<Product> producttype1=productMapper.selectByExample(pe);
		model.addAttribute("newproduct", producttype1);
		
		return "single";
	}
	
	/*@RequestMapping("shopindex1")
	public String shopindex1(Model model,HttpServletRequest request){
		String type = request.getParameter("type");
		List<Product> producttype=productMapper.selectByType(type);
		model.addAttribute("productitem", producttype);
		request.getSession().setAttribute("productitem", producttype);
		return "product";
	}
	*/
	/*@RequestMapping("product")
	public String selectByType(
			@ModelAttribute Model model,HttpServletRequest request) {
		String type = (String)request.getSession().getAttribute("type");
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andTypeEqualTo(type);
		List<Product> producttype = productMapper.selectByExample(productExample);
		model.addAttribute("productitem", producttype);
		System.out.println(producttype);
		return "product";
	}*/
	
	
}
