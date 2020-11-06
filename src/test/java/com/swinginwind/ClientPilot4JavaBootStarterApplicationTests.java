package com.swinginwind;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import meshFHE.funcLib.Cipher;
import meshFHE.funcLib.GMap;
import meshFHE.funcLib.IntHE;
import meshFHE.funcLib.SSKey;
import meshFHE.starter.ClientPilot4JavaBootStarterApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClientPilot4JavaBootStarterApplication.class)
public class ClientPilot4JavaBootStarterApplicationTests {

	@Test
	public void test() throws Exception {
		// 测试生成用户私钥和运算字典
		String uid = UUID.randomUUID().toString();
		// 根据用户标识生成私钥并持久化私钥，在KEY_DIR目录下生成秘钥文件
		SSKey key11 = IntHE.genKey(uid);
		// 根据用户标识获取私钥
		SSKey key12 = IntHE.getKey(uid);
		System.out.println(key12);
		// 根据秘钥生成运算字典，在GMAP_DIR目录下生成秘钥文件
		GMap gmap11 = IntHE.genGMap(key11, uid);
		System.out.println(gmap11);
		// 根据用户标识获取运算字典
		GMap gmap12 = IntHE.getGMap(uid);
		System.out.println(gmap12);

		// 生成第二个秘钥，用于测试密文转换
		String uid2 = UUID.randomUUID().toString();
		SSKey key21 = IntHE.genKey(uid2);
		System.out.println(key21);
		SSKey key22 = IntHE.getKey(uid2);
		System.out.println(key22);
		GMap gmap21 = IntHE.genGMap(key21, uid2);
		System.out.println(gmap21);
		GMap gmap22 = IntHE.getGMap(uid2);
		System.out.println(gmap22);

		for (int i = 0; i < 100; i++) {
			// 生成随机明文
			double d1 = new Random().nextInt(100);
			double d2 = new Random().nextInt(100);
			System.out.println("d1,d2:" + d1 + "," + d2);
			// 使用uid私钥加密d1，生成密文cipher1
			Cipher cipher1 = IntHE.encrypt(d1, uid);
			// 使用uid私钥加密d2，生成密文cipher2
			Cipher cipher2 = IntHE.encrypt(d2, uid);

			// 使用uid运算字典测试cipher1和cipher2密文加法运算，得到计算后的密文cipher3
			Cipher cipher3 = IntHE.add(cipher1, cipher2, uid);
			// 使用uid私钥解密cipher3，得到明文结果result
			double result = IntHE.decrypt(cipher3, uid);
			System.out.println("add:" + result);

			// 使用uid运算字典测试cipher1和cipher2密文减法运算，得到计算后的密文cipher3
			cipher3 = IntHE.substract(cipher1, cipher2, uid);
			// 使用uid私钥解密cipher3，得到明文结果result
			result = IntHE.decrypt(cipher3, uid);
			System.out.println("substract:" + result);

			// 使用uid运算字典测试cipher1和cipher2密文乘法运算，得到计算后的密文cipher3
			cipher3 = IntHE.multiply(cipher1, cipher2, uid);
			// 使用uid私钥解密cipher3，得到明文结果result
			result = IntHE.decrypt(cipher3, uid);
			System.out.println("multiply:" + result);

			// 使用uid运算字典测试cipher1和cipher2密文比较运算，得到比较结果
			String r = IntHE.compare(cipher1, cipher2, uid);
			System.out.println("compare:" + r);

			// 使用uid和uid2运算字典测试cipher3密文转换运算，得到新的运算字典的密文cipher4
			Cipher cipher4 = IntHE.transfer(cipher3, uid, uid2);
			// 使用uid2私钥解密cipher4，得到明文结果result
			result = IntHE.decrypt(cipher4, uid2);
			System.out.println("transfer:" + result);

		}
		System.out.println("");
	}

}
