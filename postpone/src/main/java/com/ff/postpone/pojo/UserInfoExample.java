package com.ff.postpone.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCloudUserIsNull() {
            addCriterion("cloud_user is null");
            return (Criteria) this;
        }

        public Criteria andCloudUserIsNotNull() {
            addCriterion("cloud_user is not null");
            return (Criteria) this;
        }

        public Criteria andCloudUserEqualTo(String value) {
            addCriterion("cloud_user =", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserNotEqualTo(String value) {
            addCriterion("cloud_user <>", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserGreaterThan(String value) {
            addCriterion("cloud_user >", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_user >=", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserLessThan(String value) {
            addCriterion("cloud_user <", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserLessThanOrEqualTo(String value) {
            addCriterion("cloud_user <=", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserLike(String value) {
            addCriterion("cloud_user like", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserNotLike(String value) {
            addCriterion("cloud_user not like", value, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserIn(List<String> values) {
            addCriterion("cloud_user in", values, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserNotIn(List<String> values) {
            addCriterion("cloud_user not in", values, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserBetween(String value1, String value2) {
            addCriterion("cloud_user between", value1, value2, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudUserNotBetween(String value1, String value2) {
            addCriterion("cloud_user not between", value1, value2, "cloudUser");
            return (Criteria) this;
        }

        public Criteria andCloudPassIsNull() {
            addCriterion("cloud_pass is null");
            return (Criteria) this;
        }

        public Criteria andCloudPassIsNotNull() {
            addCriterion("cloud_pass is not null");
            return (Criteria) this;
        }

        public Criteria andCloudPassEqualTo(String value) {
            addCriterion("cloud_pass =", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassNotEqualTo(String value) {
            addCriterion("cloud_pass <>", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassGreaterThan(String value) {
            addCriterion("cloud_pass >", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_pass >=", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassLessThan(String value) {
            addCriterion("cloud_pass <", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassLessThanOrEqualTo(String value) {
            addCriterion("cloud_pass <=", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassLike(String value) {
            addCriterion("cloud_pass like", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassNotLike(String value) {
            addCriterion("cloud_pass not like", value, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassIn(List<String> values) {
            addCriterion("cloud_pass in", values, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassNotIn(List<String> values) {
            addCriterion("cloud_pass not in", values, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassBetween(String value1, String value2) {
            addCriterion("cloud_pass between", value1, value2, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andCloudPassNotBetween(String value1, String value2) {
            addCriterion("cloud_pass not between", value1, value2, "cloudPass");
            return (Criteria) this;
        }

        public Criteria andBlogUserIsNull() {
            addCriterion("blog_user is null");
            return (Criteria) this;
        }

        public Criteria andBlogUserIsNotNull() {
            addCriterion("blog_user is not null");
            return (Criteria) this;
        }

        public Criteria andBlogUserEqualTo(String value) {
            addCriterion("blog_user =", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserNotEqualTo(String value) {
            addCriterion("blog_user <>", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserGreaterThan(String value) {
            addCriterion("blog_user >", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserGreaterThanOrEqualTo(String value) {
            addCriterion("blog_user >=", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserLessThan(String value) {
            addCriterion("blog_user <", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserLessThanOrEqualTo(String value) {
            addCriterion("blog_user <=", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserLike(String value) {
            addCriterion("blog_user like", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserNotLike(String value) {
            addCriterion("blog_user not like", value, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserIn(List<String> values) {
            addCriterion("blog_user in", values, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserNotIn(List<String> values) {
            addCriterion("blog_user not in", values, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserBetween(String value1, String value2) {
            addCriterion("blog_user between", value1, value2, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogUserNotBetween(String value1, String value2) {
            addCriterion("blog_user not between", value1, value2, "blogUser");
            return (Criteria) this;
        }

        public Criteria andBlogPassIsNull() {
            addCriterion("blog_pass is null");
            return (Criteria) this;
        }

        public Criteria andBlogPassIsNotNull() {
            addCriterion("blog_pass is not null");
            return (Criteria) this;
        }

        public Criteria andBlogPassEqualTo(String value) {
            addCriterion("blog_pass =", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassNotEqualTo(String value) {
            addCriterion("blog_pass <>", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassGreaterThan(String value) {
            addCriterion("blog_pass >", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassGreaterThanOrEqualTo(String value) {
            addCriterion("blog_pass >=", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassLessThan(String value) {
            addCriterion("blog_pass <", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassLessThanOrEqualTo(String value) {
            addCriterion("blog_pass <=", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassLike(String value) {
            addCriterion("blog_pass like", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassNotLike(String value) {
            addCriterion("blog_pass not like", value, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassIn(List<String> values) {
            addCriterion("blog_pass in", values, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassNotIn(List<String> values) {
            addCriterion("blog_pass not in", values, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassBetween(String value1, String value2) {
            addCriterion("blog_pass between", value1, value2, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogPassNotBetween(String value1, String value2) {
            addCriterion("blog_pass not between", value1, value2, "blogPass");
            return (Criteria) this;
        }

        public Criteria andBlogUrlIsNull() {
            addCriterion("blog_url is null");
            return (Criteria) this;
        }

        public Criteria andBlogUrlIsNotNull() {
            addCriterion("blog_url is not null");
            return (Criteria) this;
        }

        public Criteria andBlogUrlEqualTo(String value) {
            addCriterion("blog_url =", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlNotEqualTo(String value) {
            addCriterion("blog_url <>", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlGreaterThan(String value) {
            addCriterion("blog_url >", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlGreaterThanOrEqualTo(String value) {
            addCriterion("blog_url >=", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlLessThan(String value) {
            addCriterion("blog_url <", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlLessThanOrEqualTo(String value) {
            addCriterion("blog_url <=", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlLike(String value) {
            addCriterion("blog_url like", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlNotLike(String value) {
            addCriterion("blog_url not like", value, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlIn(List<String> values) {
            addCriterion("blog_url in", values, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlNotIn(List<String> values) {
            addCriterion("blog_url not in", values, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlBetween(String value1, String value2) {
            addCriterion("blog_url between", value1, value2, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogUrlNotBetween(String value1, String value2) {
            addCriterion("blog_url not between", value1, value2, "blogUrl");
            return (Criteria) this;
        }

        public Criteria andBlogCookieIsNull() {
            addCriterion("blog_cookie is null");
            return (Criteria) this;
        }

        public Criteria andBlogCookieIsNotNull() {
            addCriterion("blog_cookie is not null");
            return (Criteria) this;
        }

        public Criteria andBlogCookieEqualTo(String value) {
            addCriterion("blog_cookie =", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieNotEqualTo(String value) {
            addCriterion("blog_cookie <>", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieGreaterThan(String value) {
            addCriterion("blog_cookie >", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieGreaterThanOrEqualTo(String value) {
            addCriterion("blog_cookie >=", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieLessThan(String value) {
            addCriterion("blog_cookie <", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieLessThanOrEqualTo(String value) {
            addCriterion("blog_cookie <=", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieLike(String value) {
            addCriterion("blog_cookie like", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieNotLike(String value) {
            addCriterion("blog_cookie not like", value, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieIn(List<String> values) {
            addCriterion("blog_cookie in", values, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieNotIn(List<String> values) {
            addCriterion("blog_cookie not in", values, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieBetween(String value1, String value2) {
            addCriterion("blog_cookie between", value1, value2, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andBlogCookieNotBetween(String value1, String value2) {
            addCriterion("blog_cookie not between", value1, value2, "blogCookie");
            return (Criteria) this;
        }

        public Criteria andNextTimeIsNull() {
            addCriterion("next_time is null");
            return (Criteria) this;
        }

        public Criteria andNextTimeIsNotNull() {
            addCriterion("next_time is not null");
            return (Criteria) this;
        }

        public Criteria andNextTimeEqualTo(Date value) {
            addCriterion("next_time =", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeNotEqualTo(Date value) {
            addCriterion("next_time <>", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeGreaterThan(Date value) {
            addCriterion("next_time >", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("next_time >=", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeLessThan(Date value) {
            addCriterion("next_time <", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeLessThanOrEqualTo(Date value) {
            addCriterion("next_time <=", value, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeIn(List<Date> values) {
            addCriterion("next_time in", values, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeNotIn(List<Date> values) {
            addCriterion("next_time not in", values, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeBetween(Date value1, Date value2) {
            addCriterion("next_time between", value1, value2, "nextTime");
            return (Criteria) this;
        }

        public Criteria andNextTimeNotBetween(Date value1, Date value2) {
            addCriterion("next_time not between", value1, value2, "nextTime");
            return (Criteria) this;
        }

        public Criteria andCloudTypeIsNull() {
            addCriterion("cloud_type is null");
            return (Criteria) this;
        }

        public Criteria andCloudTypeIsNotNull() {
            addCriterion("cloud_type is not null");
            return (Criteria) this;
        }

        public Criteria andCloudTypeEqualTo(String value) {
            addCriterion("cloud_type =", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeNotEqualTo(String value) {
            addCriterion("cloud_type <>", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeGreaterThan(String value) {
            addCriterion("cloud_type >", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeGreaterThanOrEqualTo(String value) {
            addCriterion("cloud_type >=", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeLessThan(String value) {
            addCriterion("cloud_type <", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeLessThanOrEqualTo(String value) {
            addCriterion("cloud_type <=", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeLike(String value) {
            addCriterion("cloud_type like", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeNotLike(String value) {
            addCriterion("cloud_type not like", value, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeIn(List<String> values) {
            addCriterion("cloud_type in", values, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeNotIn(List<String> values) {
            addCriterion("cloud_type not in", values, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeBetween(String value1, String value2) {
            addCriterion("cloud_type between", value1, value2, "cloudType");
            return (Criteria) this;
        }

        public Criteria andCloudTypeNotBetween(String value1, String value2) {
            addCriterion("cloud_type not between", value1, value2, "cloudType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeIsNull() {
            addCriterion("blog_type is null");
            return (Criteria) this;
        }

        public Criteria andBlogTypeIsNotNull() {
            addCriterion("blog_type is not null");
            return (Criteria) this;
        }

        public Criteria andBlogTypeEqualTo(String value) {
            addCriterion("blog_type =", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeNotEqualTo(String value) {
            addCriterion("blog_type <>", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeGreaterThan(String value) {
            addCriterion("blog_type >", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeGreaterThanOrEqualTo(String value) {
            addCriterion("blog_type >=", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeLessThan(String value) {
            addCriterion("blog_type <", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeLessThanOrEqualTo(String value) {
            addCriterion("blog_type <=", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeLike(String value) {
            addCriterion("blog_type like", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeNotLike(String value) {
            addCriterion("blog_type not like", value, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeIn(List<String> values) {
            addCriterion("blog_type in", values, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeNotIn(List<String> values) {
            addCriterion("blog_type not in", values, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeBetween(String value1, String value2) {
            addCriterion("blog_type between", value1, value2, "blogType");
            return (Criteria) this;
        }

        public Criteria andBlogTypeNotBetween(String value1, String value2) {
            addCriterion("blog_type not between", value1, value2, "blogType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeIsNull() {
            addCriterion("cookie_type is null");
            return (Criteria) this;
        }

        public Criteria andCookieTypeIsNotNull() {
            addCriterion("cookie_type is not null");
            return (Criteria) this;
        }

        public Criteria andCookieTypeEqualTo(String value) {
            addCriterion("cookie_type =", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeNotEqualTo(String value) {
            addCriterion("cookie_type <>", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeGreaterThan(String value) {
            addCriterion("cookie_type >", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeGreaterThanOrEqualTo(String value) {
            addCriterion("cookie_type >=", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeLessThan(String value) {
            addCriterion("cookie_type <", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeLessThanOrEqualTo(String value) {
            addCriterion("cookie_type <=", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeLike(String value) {
            addCriterion("cookie_type like", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeNotLike(String value) {
            addCriterion("cookie_type not like", value, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeIn(List<String> values) {
            addCriterion("cookie_type in", values, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeNotIn(List<String> values) {
            addCriterion("cookie_type not in", values, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeBetween(String value1, String value2) {
            addCriterion("cookie_type between", value1, value2, "cookieType");
            return (Criteria) this;
        }

        public Criteria andCookieTypeNotBetween(String value1, String value2) {
            addCriterion("cookie_type not between", value1, value2, "cookieType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}