package com.market.mall;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.market.mall.bean.Cache;
import com.market.mall.bean.Mylist;
import com.market.mall.bean.User;
import com.market.mall.bean.isOne;
import com.market.mall.dao.DBHelper;
import com.market.mall.dao.MylistMapper;
import com.market.mall.dao.ProductMapper;
import com.market.mall.dao.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallApplicationTests {
	
	@Resource
	private UserMapper um;
	@Resource
	private ProductMapper pm;
	@Resource
	private MylistMapper mm;

	@Test
	public void contextLoads() {
		String uname = "admin";
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
		}
	}

}
