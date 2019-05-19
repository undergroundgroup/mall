package com.market.mall.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.mall.bean.Admin;
import com.market.mall.bean.AdminExample;
import com.market.mall.bean.Mylist;
import com.market.mall.bean.Product;
import com.market.mall.bean.User;
import com.market.mall.bean.UserExample;
import com.market.mall.dao.AdminMapper;
import com.market.mall.dao.MylistMapper;
import com.market.mall.dao.ProductMapper;
import com.market.mall.dao.UserMapper;


/**
 *业务层必须加入事务管理
 */
@Service
//抛出业务异常 （编译器异常）时  回滚
@Transactional(rollbackFor=BizException.class)
public class UserBiz {
	
	@Resource
	private AdminMapper am;
	
	@Resource
	private UserMapper um;
	
	@Resource
	private ProductMapper pm;
	
	@Resource
	private MylistMapper mm;
	
	/**
	 * 用户登录的业务方法
	 * @param ui
	 * @return
	 * @throws BizException
	 */
	public Admin login(Admin a) throws BizException{
		
		if(a.getUsername()==null||a.getUsername().trim().isEmpty()){
			throw new BizException("请输入用户名！");
		}
		
		if(a.getPassword()==null||a.getPassword().trim().isEmpty()){
			throw new BizException("请输入密码！");
		}
		
		AdminExample example=new AdminExample();
		example.createCriteria().andUsernameEqualTo(a.getUsername()).andPasswordEqualTo(a.getPassword());
		
		List<Admin> list=am.selectByExample(example);
		
		if(list.size()==0){
			throw new BizException("用户名或密码错误！");
		}
		
		return list.get(0);
	}
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> ulist(){
		return um.selectByExample(null);	
	}
	
	/**
	 * 查询所有管理员
	 * @return
	 */
	public List<Admin> alist(){
		return am.selectByExample(null);
		
	}
	
	/**
	 * 查询所有产品
	 * @return
	 */
	public List<Product> plist(){
		return pm.selectByExample(null);
	}
	
	/**
	 * 查询用户总数量
	 * @return
	 */
	public long u(){
		return um.countByExample(null);
	}
	
	/**
	 * 查询管理员数量
	 * @return
	 */
	public long a(){
		return am.countByExample(null);
	}
	
	/**
	 * 查询产品数量
	 * @return
	 */
	public long p(){
		return pm.countByExample(null);
	}
	
	/**
	 * 查询所有订单信息
	 * @return
	 */
	public List<Mylist> olist(){
		return mm.selectByExample(null);
	}
	
	/**
	 * 查询订单数量
	 * @return
	 */
	public long o(){
		return mm.countByExample(null);
	}
	
}
