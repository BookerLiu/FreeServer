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

        public Criteria andYunusernameIsNull() {
            addCriterion("yunusername is null");
            return (Criteria) this;
        }

        public Criteria andYunusernameIsNotNull() {
            addCriterion("yunusername is not null");
            return (Criteria) this;
        }

        public Criteria andYunusernameEqualTo(String value) {
            addCriterion("yunusername =", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameNotEqualTo(String value) {
            addCriterion("yunusername <>", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameGreaterThan(String value) {
            addCriterion("yunusername >", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameGreaterThanOrEqualTo(String value) {
            addCriterion("yunusername >=", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameLessThan(String value) {
            addCriterion("yunusername <", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameLessThanOrEqualTo(String value) {
            addCriterion("yunusername <=", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameLike(String value) {
            addCriterion("yunusername like", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameNotLike(String value) {
            addCriterion("yunusername not like", value, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameIn(List<String> values) {
            addCriterion("yunusername in", values, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameNotIn(List<String> values) {
            addCriterion("yunusername not in", values, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameBetween(String value1, String value2) {
            addCriterion("yunusername between", value1, value2, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunusernameNotBetween(String value1, String value2) {
            addCriterion("yunusername not between", value1, value2, "yunusername");
            return (Criteria) this;
        }

        public Criteria andYunpasswordIsNull() {
            addCriterion("yunpassword is null");
            return (Criteria) this;
        }

        public Criteria andYunpasswordIsNotNull() {
            addCriterion("yunpassword is not null");
            return (Criteria) this;
        }

        public Criteria andYunpasswordEqualTo(String value) {
            addCriterion("yunpassword =", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordNotEqualTo(String value) {
            addCriterion("yunpassword <>", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordGreaterThan(String value) {
            addCriterion("yunpassword >", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordGreaterThanOrEqualTo(String value) {
            addCriterion("yunpassword >=", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordLessThan(String value) {
            addCriterion("yunpassword <", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordLessThanOrEqualTo(String value) {
            addCriterion("yunpassword <=", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordLike(String value) {
            addCriterion("yunpassword like", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordNotLike(String value) {
            addCriterion("yunpassword not like", value, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordIn(List<String> values) {
            addCriterion("yunpassword in", values, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordNotIn(List<String> values) {
            addCriterion("yunpassword not in", values, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordBetween(String value1, String value2) {
            addCriterion("yunpassword between", value1, value2, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andYunpasswordNotBetween(String value1, String value2) {
            addCriterion("yunpassword not between", value1, value2, "yunpassword");
            return (Criteria) this;
        }

        public Criteria andSinausernameIsNull() {
            addCriterion("sinausername is null");
            return (Criteria) this;
        }

        public Criteria andSinausernameIsNotNull() {
            addCriterion("sinausername is not null");
            return (Criteria) this;
        }

        public Criteria andSinausernameEqualTo(String value) {
            addCriterion("sinausername =", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameNotEqualTo(String value) {
            addCriterion("sinausername <>", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameGreaterThan(String value) {
            addCriterion("sinausername >", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameGreaterThanOrEqualTo(String value) {
            addCriterion("sinausername >=", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameLessThan(String value) {
            addCriterion("sinausername <", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameLessThanOrEqualTo(String value) {
            addCriterion("sinausername <=", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameLike(String value) {
            addCriterion("sinausername like", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameNotLike(String value) {
            addCriterion("sinausername not like", value, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameIn(List<String> values) {
            addCriterion("sinausername in", values, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameNotIn(List<String> values) {
            addCriterion("sinausername not in", values, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameBetween(String value1, String value2) {
            addCriterion("sinausername between", value1, value2, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinausernameNotBetween(String value1, String value2) {
            addCriterion("sinausername not between", value1, value2, "sinausername");
            return (Criteria) this;
        }

        public Criteria andSinapasswordIsNull() {
            addCriterion("sinapassword is null");
            return (Criteria) this;
        }

        public Criteria andSinapasswordIsNotNull() {
            addCriterion("sinapassword is not null");
            return (Criteria) this;
        }

        public Criteria andSinapasswordEqualTo(String value) {
            addCriterion("sinapassword =", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordNotEqualTo(String value) {
            addCriterion("sinapassword <>", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordGreaterThan(String value) {
            addCriterion("sinapassword >", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordGreaterThanOrEqualTo(String value) {
            addCriterion("sinapassword >=", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordLessThan(String value) {
            addCriterion("sinapassword <", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordLessThanOrEqualTo(String value) {
            addCriterion("sinapassword <=", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordLike(String value) {
            addCriterion("sinapassword like", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordNotLike(String value) {
            addCriterion("sinapassword not like", value, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordIn(List<String> values) {
            addCriterion("sinapassword in", values, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordNotIn(List<String> values) {
            addCriterion("sinapassword not in", values, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordBetween(String value1, String value2) {
            addCriterion("sinapassword between", value1, value2, "sinapassword");
            return (Criteria) this;
        }

        public Criteria andSinapasswordNotBetween(String value1, String value2) {
            addCriterion("sinapassword not between", value1, value2, "sinapassword");
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

        public Criteria andSinaUrlIsNull() {
            addCriterion("sina_url is null");
            return (Criteria) this;
        }

        public Criteria andSinaUrlIsNotNull() {
            addCriterion("sina_url is not null");
            return (Criteria) this;
        }

        public Criteria andSinaUrlEqualTo(String value) {
            addCriterion("sina_url =", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlNotEqualTo(String value) {
            addCriterion("sina_url <>", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlGreaterThan(String value) {
            addCriterion("sina_url >", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlGreaterThanOrEqualTo(String value) {
            addCriterion("sina_url >=", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlLessThan(String value) {
            addCriterion("sina_url <", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlLessThanOrEqualTo(String value) {
            addCriterion("sina_url <=", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlLike(String value) {
            addCriterion("sina_url like", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlNotLike(String value) {
            addCriterion("sina_url not like", value, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlIn(List<String> values) {
            addCriterion("sina_url in", values, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlNotIn(List<String> values) {
            addCriterion("sina_url not in", values, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlBetween(String value1, String value2) {
            addCriterion("sina_url between", value1, value2, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andSinaUrlNotBetween(String value1, String value2) {
            addCriterion("sina_url not between", value1, value2, "sinaUrl");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andBzIsNull() {
            addCriterion("bz is null");
            return (Criteria) this;
        }

        public Criteria andBzIsNotNull() {
            addCriterion("bz is not null");
            return (Criteria) this;
        }

        public Criteria andBzEqualTo(String value) {
            addCriterion("bz =", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotEqualTo(String value) {
            addCriterion("bz <>", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThan(String value) {
            addCriterion("bz >", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzGreaterThanOrEqualTo(String value) {
            addCriterion("bz >=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThan(String value) {
            addCriterion("bz <", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLessThanOrEqualTo(String value) {
            addCriterion("bz <=", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzLike(String value) {
            addCriterion("bz like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotLike(String value) {
            addCriterion("bz not like", value, "bz");
            return (Criteria) this;
        }

        public Criteria andBzIn(List<String> values) {
            addCriterion("bz in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotIn(List<String> values) {
            addCriterion("bz not in", values, "bz");
            return (Criteria) this;
        }

        public Criteria andBzBetween(String value1, String value2) {
            addCriterion("bz between", value1, value2, "bz");
            return (Criteria) this;
        }

        public Criteria andBzNotBetween(String value1, String value2) {
            addCriterion("bz not between", value1, value2, "bz");
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