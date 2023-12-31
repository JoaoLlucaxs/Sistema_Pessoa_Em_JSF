package sistema.com.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import sistema.com.jpaUtil.JPAUtil;

public class DAOGeneric<A> implements Serializable{
	private static final long serialVersionUID = 1L;

	
	// Com merge podemos tanto salvar quanto atualizar
	public A merge(A entidade) {
		EntityManager entitymanager=JPAUtil.getEntityManager();
		EntityTransaction transaction=entitymanager.getTransaction();
		transaction.begin();
		
		A retornaMerge=entitymanager.merge(entidade);
		
		transaction.commit();
		entitymanager.close();
		
		return retornaMerge;
		
	}
	
	
	public void removerPorId(A entidade) {
		EntityManager entitymanager=JPAUtil.getEntityManager();
		EntityTransaction transaction=entitymanager.getTransaction();
		transaction.begin();
		
		Object id=JPAUtil.getPrimaryKey(entidade);
		
		entitymanager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
		.executeUpdate(); //por ser generico eu dou um getClass
		
		transaction.commit();
		
		entitymanager.close();
		
	}
	
	public List<A> getListEntity(Class<A> entidade){
		EntityManager entitymanager=JPAUtil.getEntityManager();
		// Startando a transaão
		EntityTransaction transaction=entitymanager.getTransaction();
		transaction.begin();
		
		@SuppressWarnings("unchecked")
		List<A> retornarLista=entitymanager.createQuery("from " + entidade.getName())
				.getResultList();
		
		
		transaction.commit();
		entitymanager.close();
		
		return retornarLista;
	}
	
}
