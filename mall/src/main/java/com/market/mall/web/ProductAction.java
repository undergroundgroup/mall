package com.market.mall.web;


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
	
	@RequestMapping("details")
	public String showproduct(Model model,HttpServletRequest request){
		String name = request.getParameter("pname");
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andPnameEqualTo(name);
		List<Product> productdetails = productMapper.selectByExample(productExample);
		model.addAttribute("detailsproduct", productdetails);
		System.out.println(productdetails);
		return "single";
	}
	
	@RequestMapping(value="Queryall",method=RequestMethod.POST)
	public String Queryall(@RequestParam("search") String search,HttpServletRequest request,Model model){
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andPnameLike(search);
		List<Product> producttype = productMapper.selectByExample(productExample);
		model.addAttribute("productitem", producttype);
		System.out.println(producttype);
		return "product";
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
