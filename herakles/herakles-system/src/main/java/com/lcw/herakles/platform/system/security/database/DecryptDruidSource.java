package com.lcw.herakles.platform.system.security.database;

//import com.alibaba.druid.filter.config.ConfigTools;
//import com.alibaba.druid.pool.DruidDataSource;
//
///**
// * @author chenwulou
// *
// */
//@SuppressWarnings("all")
//public class DecryptDruidSource extends DruidDataSource {
//	@Override
//	public void setUsername(String username) {
//		try {
//			username = ConfigTools.decrypt(username);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		super.setUsername(username);
//	}
//	@Override
//	public void setUrl(String url) {
//		try {
//			url = ConfigTools.decrypt(url);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		super.setUrl(url+"?useUnicode=true&characterEncoding=utf8&mysqlEncoding=utf8&useSSL=false");
//	}
//}
