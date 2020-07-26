package com.xmxe.study_demo.other;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

import com.xmxe.study_demo.stream.LambdaService;
import com.xmxe.study_demo.proxy.dynamicproxy.CglibProxy;
import com.xmxe.study_demo.proxy.dynamicproxy.Dao;
import com.xmxe.study_demo.proxy.dynamicproxy.DynamicProxyHandler;
import com.xmxe.study_demo.proxy.staticproxy.BuyHouse;
import com.xmxe.study_demo.proxy.staticproxy.impl.BuyHouseImpl;
import com.xmxe.study_demo.proxy.staticproxy.impl.BuyHouseProxy;

public class MineTest {
	
	protected Logger logger = Logger.getLogger(MineTest.class);

	

	@Test
	public void log() {
		logger.info("记录手动将信息输出到文件");
	}

	@Test
	public void testSend() {

		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://localhost:8080/jn/ws/user?wsdl");

		// 需要密码的情况需要加上用户名和密码
		// client.getOutInterceptors().add(new
		// ClientLoginInterceptor(USER_NAME,PASS_WORD));
		Object[] objects = new Object[0];
		try {

			// invoke("方法名",参数1,参数2,参数3....);
			objects = client.invoke("add1", "1", "小明");
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createTimer() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("---");
			}
		}, 0, 60000);
		// 三个参数
		// 第一个执行内容：内容是定时任务的执行内容，通过实现抽象类来完成这个动作
		// 第二个参数内容：是在第多少时间之后开始执行定时任务内容，该参数不能小于0
		// 第三个参数内容：是在两个任务之间的执行时间间隔，该参数不能小于等于0
	}

	// 定义一个整数数组，如何找出一个整数是否在这个数组里并获得整数的下标
	@Test
	public void 二分查找() {
		int[] array = new int[1000];
		for (int i = 0; i < 1000; i++) {
			array[i] = i * 14;
		}
		System.out.println(binarySearch(array, 2422));
	}

	public int binarySearch(int[] array, int target) {
		// 查找范围起点
		int start = 0;
		// 查找范围终点
		int end = array.length - 1;
		// 查找范围中位数
		int mid;
		// 迭代进行二分查找
		while (start <= end) {
			/*
			 * 我从c需要方向说明溢出原因，如果你定义一个假如是16位的mid，
			 * 那么如果start和end很大，二者求和超过16位那就溢出，高位那个进位其实被计算机舍弃了，那么得到的mid也就是错误的下标。
			 */
			// mid=(start+end)/2 有可能溢出
			mid = start + (end - start) / 2;
			if (array[mid] == target) {
				return mid;
			} else if (array[mid] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return -1;
	}

	@Test
	public void staticProxy() {
		BuyHouse bh = new BuyHouseProxy();
		bh.buyHouse();
	}

	@Test
	public void dynamicProxy() {
		BuyHouse buyHouse = new BuyHouseImpl();
		BuyHouse proxyBuyHouse = (BuyHouse) Proxy.newProxyInstance(BuyHouse.class.getClassLoader(),
				new Class[] { BuyHouse.class }, new DynamicProxyHandler(buyHouse));
		proxyBuyHouse.buyHouse();
	}

	@Test
	public void cglibProxy() {
		CglibProxy daoProxy = new CglibProxy();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Dao.class);
		enhancer.setCallback(daoProxy);
		Dao dao = (Dao) enhancer.create();
		dao.update();
		dao.select();
	}

	@Test
	public void memory() {
		Student a = new Student(1, "a");
		Student b = a;
		System.out.println(b.toString());// age=1 name=a
		a.setAge(2);
		System.out.println(b.toString());// age=2 name=a 内存地址引用的a a发生改变，b也改变
		a = new Student(3, "b");
		System.out.println(a.toString());// age=3 name=b
		System.out.println(b);// age=2 name=a a是一个新的内存地址 b还是原来的内存地址，b不变

		Student c = new Student(4, "c");
		Map<String, Student> map = new HashMap<>();
		map.put("a", a);
		map.put("c", c);
		Map<String, Student> m = map;
		System.out.println(m);// {a=age=3 name=b, c=age=4 name=c}
		map.remove("a");
		System.out.println(m);// {c=age=4 name=c} 内存地址引用的map map发生改变 m也跟着改变
		map = new HashMap<>();
		System.out.println(map);// {} map是一个新的内存地址
		System.out.println(m);// {c=age=4 name=c} 原来的内存地址引用没有变化
	}

}

class Student {
	int age;
	String name;

	public Student(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return "age=" + age + " name=" + name;
	}
}