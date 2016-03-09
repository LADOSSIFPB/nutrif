package br.edu.ifpb.nutrif.dao;

import java.util.List;

import br.edu.ifpb.nutrif.exception.SQLExceptionNutrIF;

public interface GenericDAO <PK, T> {
	
	public int insert(T entity) throws SQLExceptionNutrIF;

	public void update(T entity) throws SQLExceptionNutrIF;

	public int delete(PK pk) throws SQLExceptionNutrIF;

	public List<T> getAll() throws SQLExceptionNutrIF;
	
	public List<T> getAll(String namedQuery) throws SQLExceptionNutrIF;

	public T getById(PK pk) throws SQLExceptionNutrIF;

	public List<T> find(T entity) throws SQLExceptionNutrIF;

	//public List<T> convertToList(ResultSet rs) throws SQLExceptionNutrIF;
}
