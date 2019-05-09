package com.market.mall;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.market.mall.dao.ProductMapper;
import com.market.mall.dao.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallApplicationTests {
	
	@Resource
	private UserMapper um;
	@Resource
	private ProductMapper pm;

	@Test
	public void contextLoads() {
		System.out.println(pm.selectByName("Cashew Nuts, 100g"));
	}

}
