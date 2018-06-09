package com.pongp.jobinterview.atm.service.repository;

import java.util.List;

public interface IRepository<T> 
{
	  void add(T item);

	  void update(T item);

	  void remove(T item);

	  List<T> FindAll();
}
