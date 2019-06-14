package org.zongf.plugin.mybatis.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Description: 增强版PageInterceptor 拦截器
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-13 14:29
 */
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class PageInterceptorPlus extends PageInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 默认不开启分页
        boolean pageAble = false;

        // 解析参数, 看是否包含PageBounds参数
        Object parameter = invocation.getArgs()[1];
        PageBounds pagerBounds = null;

        if( parameter instanceof MapperMethod.ParamMap){
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameter;
            Set<Map.Entry<String,Object>> set = paramMap.entrySet();
            for (Map.Entry<String, Object> entry : set)
                if (entry.getValue() instanceof PageBounds) {
                    pagerBounds = (PageBounds) entry.getValue();
                }
        } else if (parameter instanceof PageBounds) {
            pagerBounds = (PageBounds) parameter;
        }

        // 如果pageBounds 不为空, 则表示需要分页
        if (pagerBounds != null) {
            PageHelper.startPage(pagerBounds.getPage(), pagerBounds.getPageSize());
            pageAble = true;
        }

        // 执行查询方法
        Object object = super.intercept(invocation);

        // 封装结果集
        if (pageAble) {
            return new PageResult<>((List) object);
        }else {
            return object;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }
}
