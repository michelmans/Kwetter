package dao;

import domain.Group;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GroupDao {

    @PersistenceContext
    EntityManager entityManager;

    public void addGroup(Group group){
        entityManager.persist(group);
    }

    public Group update(Group group){
        return entityManager.merge(group);
    }

    public Group getGroupByName(String name){
        return entityManager.find(Group.class, name);
    }

}
