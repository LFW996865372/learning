package com.cgb.luofenwu.spring.demo;

import com.cgb.luofenwu.spring.framework.annotation.LfwService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 查询业务
 * @Author LFW
 *
 */
@LfwService
public class QueryService implements IQueryService {

	/**
	 * 查询
	 */
	public String query(String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
 		return json;
	}

}
