package com.itheima.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.core.ContentService;
import com.itheima.core.dao.ad.ContentDao;
import com.itheima.core.pojo.ad.Content;
import com.itheima.core.pojo.ad.ContentQuery;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private ContentDao contentDao;
	// 为了后面的查询缓存, 需要注入RedisTemplate, 但是因为是第三方的,不能直接注入, 需要在common层中配置相关的bean
	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public List<Content> findAll() {
		List<Content> list = contentDao.selectByExample(null);
		return list;
	}

	@Override
	public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Content> page = (Page<Content>)contentDao.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void add(Content content) {
		contentDao.insertSelective(content);
	}

	/**
	 * 修改广告内容,这个方法比较麻烦, 涉及到两个库, 并且要控制事务
	 * @param content
	 */
	@Override
	public void edit(Content content) {
		Content c = contentDao.selectByPrimaryKey(content.getCategoryId());
		contentDao.updateByPrimaryKeySelective(content);	// 先更新mysql这样可以有效控制事务
		if (!c.getCategoryId().equals(content.getCategoryId())){
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
		redisTemplate.boundHashOps("content").delete(c.getCategoryId());
	}

	@Override
	public Content findOne(Long id) {
		Content content = contentDao.selectByPrimaryKey(id);
		return content;
	}

	@Override
	public void delAll(Long[] ids) {
		if(ids != null){
			for(Long id : ids){
				contentDao.deleteByPrimaryKey(id);
			}
		}
	}


    @Override
    public List<Content> findByCategoryId(Long categoryId) {
		// 首先判断是否有缓存, 然后再添加进数据库
		List<Content> contents = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
		// 如果没有,则请求数据库
		if (null == contents || contents.size() == 0){
			// 从数据库中获取
			ContentQuery query = new ContentQuery();
			query.setOrderByClause("sort_order desc");
			query.createCriteria().andCategoryIdEqualTo(categoryId).andStatusEqualTo("1");
			contents = contentDao.selectByExample(query);
			// 保存一份到Redis
			redisTemplate.boundHashOps("content").put(categoryId,contents);
			redisTemplate.boundHashOps("content").expire(24, TimeUnit.HOURS);	// expire设置Redis中的保存时间
		}
		return contents;
    }

}
