/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import com.gps.entities.Sms;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author amine.sagaama@gmail.com
 */

public abstract class AbstractFacade {

    private static final Logger logger = Logger.getLogger(AbstractFacade.class);

    protected abstract EntityManager getEntityManager();
    
    private Class<Sms> type;

    public Class<Sms> getType() {
        return type;
    }

    public void setType(Class<Sms> type) {
        this.type = type;
    }

    /**
     *
     * @param entity
     */
    public void create(Object entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        getEntityManager().refresh(entity);
    }

    /**
     *
     * @param entity
     */
    public void edit(Object entity) {
        getEntityManager().merge(entity);
//        getEntityManager().flush();
    }

//    /**
//     *
//     * @param entity
//     */
//    public void refresh(Object entity) {
//        getEntityManager().refresh(entity);
//    }

    /**
     *
     * @param entity
     */
    public void remove(Object entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
//        getEntityManager().flush();
    }
    
    public boolean deleteItems(Sms[] items) {
        for (Sms item : items) {
            if( item instanceof Sms){
                Sms sms = (Sms)item;
                if(sms.getIdSms() == 1){
                    continue;
                }
            }
            getEntityManager().remove(getEntityManager().merge(item));
        }
        return true;
    }

    /**
     *
     * @param entityClass
     * @param id
     * @return
     */
    public Object find(Class entityClass, Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public Object findLastObject(Class entityClass) {

        int max = count(entityClass);
        if (max == 0) {
            return null;
        }
        return findAll(entityClass, null, null).get(max - 1);
    }

    /**
     *
     * @param entityClass
     * @param attribute
     * @param id
     * @return
     */
    public Object findByAttribute(Class entityClass, String attribute, Object id) {
        Map conditions = new HashMap<>();
        String s;
        if (id instanceof Number) {
            s = "=" + id;
        } else {
            s = "='" + id + "'";
        }
        conditions.put(attribute, s);
        List<Object> list = findAll(entityClass, conditions, "");

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     *
     * @param entityClass
     * @param range
     * @return
     */
    public List findRange(Class entityClass, int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     *
     * @param entityClass
     * @return
     */
    public int count(Class entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Object> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     *
     * @param entityType
     * @param map
     * @param constructeur
     * @return
     */
    public List findAll(Class entityType, Map map, String constructeur) {
        return findByCriteria(map, entityType, "", constructeur);

    }

    public List findOrderedAll(Class entityType, Map map, String constructeur, String orderBy, String order) {
        return findByCriteriaOrderedList(map, entityType, "", constructeur, orderBy, order);
    }

    /**
     *
     * @param strQuery
     * @return
     */
    public List executeQuery(String strQuery) {

        Query query = getEntityManager().createQuery(strQuery);
        return query.getResultList();
    }

    /**
     *
     * @param conditions
     * @param c
     * @param operateur
     * @param constructeur
     * @return
     */
    public List findByCriteria(Map<String, String> conditions, final Class c, String operateur, String constructeur) {
        StringBuilder query = new StringBuilder();
        if (constructeur != null && constructeur.length() > 0) {
            query.append("SELECT ");
            query.append(constructeur);
        }

        query.append("SELECT classRecherche ");
        query.append(" FROM ");
        query.append(c.getSimpleName());
        query.append(" as classRecherche");

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
                query.append("classRecherche.");
                query.append(eachCondition.getKey());
                query.append(eachCondition.getValue());
                query.append(" ");
                i++;
                if (conditions.entrySet().size() != i) {
                    if (operateur.length() == 0) {
                        query.append(" AND ");
                    } else {
                        query.append(" ");
                        query.append(operateur);
                        query.append(" ");
                    }
                }
            }
        }
        return executeQuery(query.toString());
    }

    public List findByEqualCriteriaOnSameAttribute(List<String> values, final Class c, String operateur, String constructeur, String attribute) {
        StringBuilder query = new StringBuilder();
        if (constructeur != null && constructeur.length() > 0) {
            query.append("SELECT ");
            query.append(constructeur);


        }
        query.append("SELECT classRecherche ");
        query.append(" FROM ");
        query.append(c.getSimpleName());
        query.append(" as classRecherche");

        if (values != null && !values.isEmpty()) {
            query.append(" WHERE ");
            int i = 0;
            for (String currentValue : values) {
                query.append("classRecherche.");
                query.append(attribute);
                query.append(" = '");
                query.append(currentValue);
                query.append("' ");
                i++;
                if (values.size() != i) {
                    if (operateur.length() == 0) {
                        query.append(" AND ");
                    } else {
                        query.append(" ");
                        query.append(operateur);
                        query.append(" ");
                    }
                }
            }
        }
        return executeQuery(query.toString());
    }

    /**
     *
     * @param conditions
     * @param parameters
     * @param c
     * @param operateur
     * @param constructeur
     * @return
     */
    public List findByCriteria(Map<String, String> conditions, Map<String, Object> parameters, final Class c, String operateur, String constructeur) {
        StringBuilder query = new StringBuilder();
        if (constructeur != null && constructeur.length() > 0) {
            query.append("SELECT ");
            query.append(constructeur);


        }
        query.append("SELECT classRecherche ");
        query.append(" FROM ");
        query.append(c.getSimpleName());
        query.append(" as classRecherche");

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
                query.append("classRecherche.");
                query.append(eachCondition.getKey());
                query.append(eachCondition.getValue());
                query.append(" ");
                i++;
                if (conditions.entrySet().size() != i) {
                    if (operateur.length() == 0) {
                        query.append(" AND ");
                    } else {
                        query.append(" ");
                        query.append(operateur);
                        query.append(" ");
                    }
                }
            }
        }
//        logger.info(query);
        Query queryCreated = getEntityManager().createQuery(query.toString());
        if (parameters != null && !parameters.isEmpty()) {

            int i = 0;
            for (Map.Entry<String, Object> eachCondition : parameters.entrySet()) {
                queryCreated.setParameter(eachCondition.getKey(), eachCondition.getValue());
            }
        }
        return queryCreated.getResultList();
    }

    /**
     *
     * @param conditions
     * @param c
     * @param operateur
     * @param orderBy
     * @param constructeur
     * @return
     */
    public List findByCriteriaOrderedList(Map<String, String> conditions, final Class c,
            String operateur, String constructeur, String orderBy, String order) {
        StringBuilder query = new StringBuilder();
        if (constructeur != null && constructeur.length() > 0) {
            query.append("SELECT ");
            query.append(constructeur);
        }
        query.append("SELECT classRecherche ");
        query.append(" FROM ");
        query.append(c.getSimpleName());
        query.append(" as classRecherche");

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
                query.append("classRecherche.");
                query.append(eachCondition.getKey());
                query.append(eachCondition.getValue());
                query.append(" ");
                i++;
                if (conditions.entrySet().size() != i) {
                    if (operateur.length() == 0) {
                        query.append(" AND ");
                    } else {
                        query.append(" ");
                        query.append(operateur);
                        query.append(" ");
                    }
                }
            }
        }
        if (orderBy != null) {
            query.append(" ORDER BY ");
            query.append("classRecherche.");
            query.append(orderBy);
            query.append(" ").append(order);
        }
//        logger.info(query);

        return executeQuery(query.toString());
    }

    /**
     *
     * @param conditions
     * @param c
     * @param operateur
     * @param orderBy
     * @param constructeur
     * @return
     */
    public List findByCriteriaOrderedListManyOrderBy(Map<String, String> conditions, final Class c,
            String operateur, String constructeur, Map<String, String> orderBy) {
        String query = "";
        if (constructeur != null && constructeur.length() > 0) {
            query = query.concat("SELECT ");
            query = query.concat(constructeur);
        }
        query = query.concat("SELECT classRecherche ");
        query = query.concat(" FROM ");
        query = query.concat(c.getSimpleName());
        query = query.concat(" as classRecherche");

        if (conditions != null && !conditions.isEmpty()) {
            query = query.concat(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
                query = query.concat("classRecherche.");
                query = query.concat(eachCondition.getKey());
                query = query.concat(eachCondition.getValue());
                query = query.concat(" ");
                i++;
                if (conditions.entrySet().size() != i) {
                    if (operateur.length() == 0) {
                        query = query.concat(" AND ");
                    } else {
                        query = query.concat(" ");
                        query = query.concat(operateur);
                        query = query.concat(" ");
                    }
                }
            }
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            query = query.concat(" ORDER BY ");

            for (Map.Entry<String, String> eachCondition : orderBy.entrySet()) {
                query = query.concat(" classRecherche.");
                query = query.concat(eachCondition.getKey());
                query = query.concat(" " + eachCondition.getValue()).concat(",");
            }
        }

        query = query.subSequence(0, query.length() - 1).toString();

        logger.info(query);
        return executeQuery(query.toString());
    }

    /**
     *
     * @param conditions
     * @param c
     * @param operateur
     * @param orderBy
     * @param order
     * @param constructeur
     * @return
     */
    public List findByCriteria(Map<String, String> conditions,
            final Class c, String operateur, String orderBy, String order, String constructeur) {
        StringBuilder query = new StringBuilder();
        if (constructeur != null && constructeur.length() > 0) {
            query.append("SELECT ");
            query.append(constructeur);
        }
        query.append("SELECT classRecherche ");
        query.append(" FROM ");
        query.append(c.getSimpleName());
        query.append(" as classRecherche");

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
                query.append("classRecherche.");
                query.append(eachCondition.getKey());
                query.append(eachCondition.getValue());
                query.append(" ");
                i++;
                if (conditions.entrySet().size() != i) {
                    if (operateur.length() == 0) {
                        query.append(" AND ");
                    } else {
                        query.append(" ");
                        query.append(operateur);
                        query.append(" ");
                    }
                }
            }
        }
        if (orderBy != null) {
            query.append(" ORDER BY ");
            query.append(orderBy).append(" ");
            query.append(order);
        }
//        logger.info(query);
        return executeQuery(query.toString());
    }

    /**
     *
     * @param conditions
     * @param c
     * @param operateur
     * @param critereDistinction
     * @return
     */
    public List findChampsByCriteria(Map<String, String> conditions, final Class c, String operateur, String critereDistinction) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(critereDistinction);
        query.append(" FROM ");
        query.append(c.getSimpleName());
        query.append(" as classRecherche");

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
                query.append("classRecherche.");
                query.append(eachCondition.getKey());
                query.append(eachCondition.getValue());
                query.append(" ");
                i++;
                if (conditions.entrySet().size() != i) {
                    if (operateur.length() == 0) {
                        query.append(" AND ");
                    } else {
                        query.append(" ");
                        query.append(operateur);
                        query.append(" ");
                    }
                }
            }
        }
        return executeQuery(query.toString());
    }

//    /**
//     *
//     * @param conditions
//     * @param c
//     * @param operateur
//     * @return
//     */
//    public int deleteByCriteria(Map<String, String> conditions, final Class c, String operateur) {
//        StringBuilder query = new StringBuilder();
//        query.append("DELETE FROM ");
//        query.append(c.getSimpleName());
//        query.append(" as classRecherche");
//
//        if (conditions != null && !conditions.isEmpty()) {
//            query.append(" WHERE ");
//            int i = 0;
//            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
//                query.append("classRecherche.");
//                query.append(eachCondition.getKey());
//                query.append(eachCondition.getValue());
//                query.append(" ");
//                i++;
//                if (conditions.entrySet().size() != i) {
//                    if (operateur.length() == 0) {
//                        query.append(" AND ");
//                    } else {
//                        query.append(" ");
//                        query.append(operateur);
//                        query.append(" ");
//                    }
//                }
//            }
//        }
//
//        return getEntityManager().createQuery(query.toString()).executeUpdate();
//    }
//    /**
//     *
//     * @param conditions
//     * @param c
//     * @param operateur
//     * @return
//     *
//     */
//    public int updateByCriteria(Map<String, String> conditions, Map<String, String> values, final Class c, String operateur) {
//        StringBuilder query = new StringBuilder();
//        query.append("UPDATE ");
//        query.append(c.getSimpleName());
//        query.append(" as classUpdate");
//
//        if (values != null && !values.isEmpty()) {
//            query.append(" SET ");
//            int i = 0;
//            for (Map.Entry<String, String> eachValue : values.entrySet()) {
//                query.append("classUpdate.");
//                query.append(eachValue.getKey());
//                query.append(eachValue.getValue());
//                query.append(" ");
//                i++;
//                if (values.entrySet().size() != i) {
//                    if (operateur.length() == 0) {
//                        query.append(" , ");
//                    } else {
//                        query.append(" ");
//                        query.append(",");
//                        query.append(" ");
//                    }
//                }
//            }
//        }
//
//
//        if (conditions != null && !conditions.isEmpty()) {
//            query.append(" WHERE ");
//            int i = 0;
//            for (Map.Entry<String, String> eachCondition : conditions.entrySet()) {
//                query.append("classUpdate.");
//                query.append(eachCondition.getKey());
//                query.append(eachCondition.getValue());
//                query.append(" ");
//                i++;
//                if (conditions.entrySet().size() != i) {
//                    if (operateur.length() == 0) {
//                        query.append(" AND ");
//                    } else {
//                        query.append(" ");
//                        query.append(operateur);
//                        query.append(" ");
//                    }
//                }
//            }
//        }
//        return getEntityManager().createQuery(query.toString()).executeUpdate();
//    }
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Acceptable {
    }
}
