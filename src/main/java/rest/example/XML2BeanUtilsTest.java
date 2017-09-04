package rest.example;



import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.xml.sax.SAXException;
import rest.fuyou.MerchantRepeatCheck;
import rest.fuyou.Term;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class XML2BeanUtilsTest {
	@Test
	//测试简单属性
	public void testPsersonBean() throws Exception {
		String xmlString = XML2BeanUtils.bean2XmlString(createPerson());
		System.out.println(xmlString);
		PersonBean person = XML2BeanUtils.xmlString2Bean(PersonBean.class, xmlString);
		System.out.println(person);
	}

	@Test
	//测试复杂属性
	public void testUser() throws Exception {
		String xmlString = XML2BeanUtils.bean2XmlString(createUser());
		System.out.println(xmlString);
		User user = XML2BeanUtils.xmlString2Bean(User.class, xmlString);
		System.out.println(user);
	}

	public PersonBean createPerson() {
		PersonBean personBean = new PersonBean();
		personBean.setAge(99);
		personBean.setName("hhhhhhh");
		Term t1=new Term();
		t1.setTerm_id("1");
		t1.setTerm_name("qq");
		t1.setTerm_serial_no("1312432");
		Term t2=new Term();
		t2.setTerm_id("2");
		t2.setTerm_name("qq2");
		t2.setTerm_serial_no("221312432");
		Term t3=new Term();
		t3.setTerm_id("3");
//		t3.setTerm_name("qq3");
//		t3.setTerm_serial_no("2321312432");
		List<Term> termList =new ArrayList<Term>();
		termList.add(t1);
		termList.add(t2);
		termList.add(t3);
		personBean.setTerms(termList);
		return personBean;
	}

	//创建复杂的用户对象
	public User createUser() {
		User user = new User();
		user.setAge(18);
		user.setUserName("张三");
		user.setHobbyArray(new String[] { "篮球", "足球", "乒乓球", "羽毛球" });
		user.setHobbyList(Arrays.asList(new String[] { "游泳", "蛙游", "蝶泳", "自由泳", "狗刨" }));
		user.setPerson(createPerson());
		Map<String, PersonBean> personMap = new HashMap<String, PersonBean>();
		for (int i = 0; i < 5; i++) {
			personMap.put("person" + i, new PersonBean("person" + i, i));
		}
		user.setPersonMap(personMap);
		return user;
	}

	@Test
	public void testMerchantRepeatCheck() throws IntrospectionException, SAXException, IOException {
		String xmlString = XML2BeanUtils.bean2XmlString(createMerchantRepeatCheck());
		System.out.println(xmlString.replace("MerchantRepeatCheck","xml"));
		MerchantRepeatCheck merchantRepeatCheck = XML2BeanUtils.xmlString2Bean(MerchantRepeatCheck.class, xmlString);
		System.out.println(merchantRepeatCheck);
	}

	public MerchantRepeatCheck createMerchantRepeatCheck() throws UnsupportedEncodingException {
//		mchnt_name=富友测试商户&trace_no=161202000088& key=12345678901234567890123456789012
		MerchantRepeatCheck merchantRepeatCheck = new MerchantRepeatCheck();
//		merchantRepeatCheck.setTrace_no("10010119620410327532");
//		merchantRepeatCheck.setIns_cd("123456");
//		merchantRepeatCheck.setMchnt_name("爱信诺科贸公司");
		merchantRepeatCheck.setTrace_no("161202000088");
//		merchantRepeatCheck.setIns_cd("123456");
		merchantRepeatCheck.setMchnt_name("富友测试商户");
        String sign=merchantRepeatCheck.getMchnt_name()+"&"+merchantRepeatCheck.getTrace_no()+"12345678901234567890123456789012";
	    merchantRepeatCheck.setSign(DigestUtils.md5Hex(sign.getBytes("UTF-8")));
		return merchantRepeatCheck;
	}


}
