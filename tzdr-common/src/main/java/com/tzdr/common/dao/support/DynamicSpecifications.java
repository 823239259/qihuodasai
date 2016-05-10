package com.tzdr.common.dao.support;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jodd.util.StringUtil;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;

import com.tzdr.common.utils.Dates;



public class DynamicSpecifications {
	private static final ConversionService conversionService = new EnhanceDefaultConversionService();

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
		return new Specification<T>() {
			@SuppressWarnings({"unchecked", "rawtypes"})
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters != null && !(filters.isEmpty())) {

					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName,
						// 转换为Task.user.name属性
						String[] names = StringUtil.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// convert value from string to target type
						Class attributeClass = expression.getJavaType();
						if (!attributeClass.equals(String.class) && filter.value instanceof String
								&& conversionService.canConvert(String.class, attributeClass)) {
							// 对小于等于特殊处理.
							if (filter.operator == SearchFilter.Operator.LTE
									&& attributeClass.isAssignableFrom(Date.class)) {
								String oriValue = (String) filter.value;
								if (oriValue.length() == Dates.CHINESE_DATE_FORMAT_LINE.length()) {
									filter.value = Dates.addDay(Dates.parse(oriValue));
								}
							} else {
								filter.value = conversionService.convert(filter.value, attributeClass);
							}
						}
						// logic operator
						switch (filter.operator) {
							case IN:
								predicates.add(builder.in(expression).value(filter.value));
								break;
							case EQ :
								predicates.add(builder.equal(expression, filter.value));
								break;
							case LIKE :
								predicates.add(builder.like(expression, "%" + filter.value + "%"));
								break;
							case GT :
								predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
								break;
							case LT :
								predicates.add(builder.lessThan(expression, (Comparable) filter.value));
								break;
							case GTE :
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case LTE :
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case NOT :
								predicates.add(builder.notEqual(expression, (Comparable) filter.value));
								break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
}
