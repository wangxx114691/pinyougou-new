package com.itheima.core.pojo.template;

import java.util.ArrayList;
import java.util.List;

public class TypeTemplateCheckQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected String fields;

    public TypeTemplateCheckQuery() {
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

    public void setFields(String fields) {
        this.fields=fields;
    }

    public String getFields() {
        return fields;
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTypeDescIsNull() {
            addCriterion("type_desc is null");
            return (Criteria) this;
        }

        public Criteria andTypeDescIsNotNull() {
            addCriterion("type_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTypeDescEqualTo(String value) {
            addCriterion("type_desc =", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescNotEqualTo(String value) {
            addCriterion("type_desc <>", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescGreaterThan(String value) {
            addCriterion("type_desc >", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescGreaterThanOrEqualTo(String value) {
            addCriterion("type_desc >=", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescLessThan(String value) {
            addCriterion("type_desc <", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescLessThanOrEqualTo(String value) {
            addCriterion("type_desc <=", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescLike(String value) {
            addCriterion("type_desc like", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescNotLike(String value) {
            addCriterion("type_desc not like", value, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescIn(List<String> values) {
            addCriterion("type_desc in", values, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescNotIn(List<String> values) {
            addCriterion("type_desc not in", values, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescBetween(String value1, String value2) {
            addCriterion("type_desc between", value1, value2, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andTypeDescNotBetween(String value1, String value2) {
            addCriterion("type_desc not between", value1, value2, "typeDesc");
            return (Criteria) this;
        }

        public Criteria andBrandIdsIsNull() {
            addCriterion("brand_ids is null");
            return (Criteria) this;
        }

        public Criteria andBrandIdsIsNotNull() {
            addCriterion("brand_ids is not null");
            return (Criteria) this;
        }

        public Criteria andBrandIdsEqualTo(String value) {
            addCriterion("brand_ids =", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsNotEqualTo(String value) {
            addCriterion("brand_ids <>", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsGreaterThan(String value) {
            addCriterion("brand_ids >", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsGreaterThanOrEqualTo(String value) {
            addCriterion("brand_ids >=", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsLessThan(String value) {
            addCriterion("brand_ids <", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsLessThanOrEqualTo(String value) {
            addCriterion("brand_ids <=", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsLike(String value) {
            addCriterion("brand_ids like", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsNotLike(String value) {
            addCriterion("brand_ids not like", value, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsIn(List<String> values) {
            addCriterion("brand_ids in", values, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsNotIn(List<String> values) {
            addCriterion("brand_ids not in", values, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsBetween(String value1, String value2) {
            addCriterion("brand_ids between", value1, value2, "brandIds");
            return (Criteria) this;
        }

        public Criteria andBrandIdsNotBetween(String value1, String value2) {
            addCriterion("brand_ids not between", value1, value2, "brandIds");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsIsNull() {
            addCriterion("custom_attribute_items is null");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsIsNotNull() {
            addCriterion("custom_attribute_items is not null");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsEqualTo(String value) {
            addCriterion("custom_attribute_items =", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsNotEqualTo(String value) {
            addCriterion("custom_attribute_items <>", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsGreaterThan(String value) {
            addCriterion("custom_attribute_items >", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsGreaterThanOrEqualTo(String value) {
            addCriterion("custom_attribute_items >=", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsLessThan(String value) {
            addCriterion("custom_attribute_items <", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsLessThanOrEqualTo(String value) {
            addCriterion("custom_attribute_items <=", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsLike(String value) {
            addCriterion("custom_attribute_items like", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsNotLike(String value) {
            addCriterion("custom_attribute_items not like", value, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsIn(List<String> values) {
            addCriterion("custom_attribute_items in", values, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsNotIn(List<String> values) {
            addCriterion("custom_attribute_items not in", values, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsBetween(String value1, String value2) {
            addCriterion("custom_attribute_items between", value1, value2, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andCustomAttributeItemsNotBetween(String value1, String value2) {
            addCriterion("custom_attribute_items not between", value1, value2, "customAttributeItems");
            return (Criteria) this;
        }

        public Criteria andSpecIdsIsNull() {
            addCriterion("spec_ids is null");
            return (Criteria) this;
        }

        public Criteria andSpecIdsIsNotNull() {
            addCriterion("spec_ids is not null");
            return (Criteria) this;
        }

        public Criteria andSpecIdsEqualTo(String value) {
            addCriterion("spec_ids =", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsNotEqualTo(String value) {
            addCriterion("spec_ids <>", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsGreaterThan(String value) {
            addCriterion("spec_ids >", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsGreaterThanOrEqualTo(String value) {
            addCriterion("spec_ids >=", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsLessThan(String value) {
            addCriterion("spec_ids <", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsLessThanOrEqualTo(String value) {
            addCriterion("spec_ids <=", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsLike(String value) {
            addCriterion("spec_ids like", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsNotLike(String value) {
            addCriterion("spec_ids not like", value, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsIn(List<String> values) {
            addCriterion("spec_ids in", values, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsNotIn(List<String> values) {
            addCriterion("spec_ids not in", values, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsBetween(String value1, String value2) {
            addCriterion("spec_ids between", value1, value2, "specIds");
            return (Criteria) this;
        }

        public Criteria andSpecIdsNotBetween(String value1, String value2) {
            addCriterion("spec_ids not between", value1, value2, "specIds");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNull() {
            addCriterion("seller_id is null");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNotNull() {
            addCriterion("seller_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellerIdEqualTo(String value) {
            addCriterion("seller_id =", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotEqualTo(String value) {
            addCriterion("seller_id <>", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThan(String value) {
            addCriterion("seller_id >", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThanOrEqualTo(String value) {
            addCriterion("seller_id >=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThan(String value) {
            addCriterion("seller_id <", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThanOrEqualTo(String value) {
            addCriterion("seller_id <=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLike(String value) {
            addCriterion("seller_id like", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotLike(String value) {
            addCriterion("seller_id not like", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdIn(List<String> values) {
            addCriterion("seller_id in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotIn(List<String> values) {
            addCriterion("seller_id not in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdBetween(String value1, String value2) {
            addCriterion("seller_id between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotBetween(String value1, String value2) {
            addCriterion("seller_id not between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerNameIsNull() {
            addCriterion("seller_name is null");
            return (Criteria) this;
        }

        public Criteria andSellerNameIsNotNull() {
            addCriterion("seller_name is not null");
            return (Criteria) this;
        }

        public Criteria andSellerNameEqualTo(String value) {
            addCriterion("seller_name =", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotEqualTo(String value) {
            addCriterion("seller_name <>", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameGreaterThan(String value) {
            addCriterion("seller_name >", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameGreaterThanOrEqualTo(String value) {
            addCriterion("seller_name >=", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameLessThan(String value) {
            addCriterion("seller_name <", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameLessThanOrEqualTo(String value) {
            addCriterion("seller_name <=", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameLike(String value) {
            addCriterion("seller_name like", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotLike(String value) {
            addCriterion("seller_name not like", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameIn(List<String> values) {
            addCriterion("seller_name in", values, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotIn(List<String> values) {
            addCriterion("seller_name not in", values, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameBetween(String value1, String value2) {
            addCriterion("seller_name between", value1, value2, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotBetween(String value1, String value2) {
            addCriterion("seller_name not between", value1, value2, "sellerName");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("check_status like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("check_status not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
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