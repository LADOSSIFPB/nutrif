package br.edu.ifpb.nutrif.hibernate;

import javax.persistence.Query;

public class QueryUtil<T> {
	
	public T getUniqueResult(Query query) {
		return (T) query.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

}
