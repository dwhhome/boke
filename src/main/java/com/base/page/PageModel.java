
package com.base.page;

import java.io.Serializable;
import java.util.List;

/**
 * 存放分页数据的模型
 */
public class PageModel<T> implements Serializable{

	/** 当前页号 */
	private int pageNo = 1;
	/** 每页显示的记录条数 */
	private int pageSize = 10;
	/** 总记录数 */
	private long recordCount=-1;
	/** 存放分页数据的集合 */
	private List<T> datas;

	/** 总页数根据pageSize和recordCount计算得出 */
	private int pageCount;

	public PageModel() {
	}

	public PageModel(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		if (this.getRecordCount() <= 0) {
			return 0;
		} else {
			pageCount = (int) ((recordCount + pageSize - 1) / pageSize);
			return pageCount;
		}
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}