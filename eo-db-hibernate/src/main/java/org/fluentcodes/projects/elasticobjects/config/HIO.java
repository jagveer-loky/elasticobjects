package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.elasticobjects.EOScalar;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Created by Werner on 09.10.2016.
 * Refactored 17.5.2018 with Builder
 */
public class HIO {

    private final HConfig hConfig;

    public HIO(final HConfig config)  {
        this.hConfig = config;
    }


    public Session getSession()  {
        return hConfig.getSession();
    }

    private String createQuery(ModelInterface model, Or or) {
        StringBuffer sql = new StringBuffer(" from " + model.getModelKey() + " where ");
        if (or == null || or.isEmpty()) {
            sql.append(" 1=1");
            return sql.toString();
        }
        sql.append(or.createQuery());
        return sql.toString();
    }

    public List read(ModelInterface model, ListParams listParams)  {
        if (listParams == null) {
            throw new Exception("Null value for listParams not allowed");
        }
        Session session = getSession();
        Query query = session
                .createQuery(createQuery(model, listParams.getFilter()));

        if (listParams.getFilter() == null || listParams.getFilter().isEmpty()) {
            return query.list();
        }
        Map<String, Object> keyValueMap = listParams.getFilter().getKeyValueMap();
        for (String idKey : keyValueMap.keySet()) {
            Object value = keyValueMap.get(idKey);
            String key = idKey.replaceAll("_\\d+$", "");

            Object transfer = ScalarConverter.transform(model.getField(key).getModelClass(), value);
            query.setParameter(idKey, transfer);
        }

        return query.list();
    }

    public List readAll(ModelInterface model)  {
        return read(model, new ListParams());
    }

    public int delete(ModelInterface model, ListParams listParams)  {
        List list = read(model, listParams);
        int counter = 0;
        for (Object dbObject : list) {
            delete(dbObject);
            counter++;
        }
        return counter;
    }

    public Object find(ModelInterface model, Object object)  {
        if (object == null) {
            return null;
        }
        try {
            Object id = model.get(model.getIdKey(), object);
            if (id != null) {
                return getSession().byId(model.getModelClass()).load((Serializable) id);
            }
        } catch (Exception e) {

        }
        if (model.getNaturalKeys() != null && !model.getNaturalKeys().isEmpty()) {
            ListParams listParams = new ListParams();
            for (String key : model.getNaturalKeys()) {
                listParams.addAnd(key, model.get(key, object));
            }

            List result = read(model, listParams);
            if (result.size() == 0) {
                return null;
                //throw new Exception("Could not find value neither by id nor natural keys!");
            }
            return result.get(0);
        }
        throw new Exception("No id defined for object " + model.getModelKey());
    }


    public void delete(ModelInterface model, Object object)  {
        delete(find(model, object));
    }

    public boolean delete(Object object)  {
        if (object == null) {
            throw new Exception("Tried to delete with null object");
        }
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.delete(object);
        tx.commit();
        session.flush();
        return true;
    }


    public Object write(ModelInterface model, Object object)  {
        EOBuilder adapterBuilder = new EOBuilder(model.getConfigsCache());
        //TODO.setEoExtension(this);
        if (object instanceof List) {
            adapterBuilder.setModels(List.class, model.getModelClass());
        } else if (object instanceof Map) {
            adapterBuilder.setModels(Map.class, model.getModelClass());
        } else {
            adapterBuilder.setModels(model);
        }
        EO adapter = adapterBuilder.map(object);
        return adapter.get();
    }

    public boolean insert(ModelInterface model, Object object)  {
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.persist(object);
        tx.commit();
        session.flush();
        return true;
    }


    public boolean update(ModelInterface model, Object dbObject)  {
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.update(dbObject);
        tx.commit();
        session.flush();
        return true;
    }

    public void executeUpdate(List<String> sqlList)  {
        for (String sql : sqlList) {
            executeUpdate(sql);
        }
    }

    //https://stackoverflow.com/questions/3492453/hibernate-and-delete-all
    public boolean executeUpdate(String sql)  {
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        tx.commit();
        session.flush();
        return true;
    }

    public Object doBeforeMap(EO adapter, Object objectToMap)  {
        if (adapter.isList()) {
            return adapter.getModel().create();
        } else if (adapter.isMap()) {
            return adapter.getModel().create();
        } else if (adapter.isObject()) {
            Object dbObject = find(adapter.getModel(), objectToMap);
            if (dbObject == null) {
                ((EOScalar) adapter).setInsert(true);
                return adapter.getModel().create();
            }
            return dbObject;
        }
        adapter.warn("Problem no value defined");
        return null;

    }

    public void doAfterMap(EO adapter)  {
        if (adapter.isObject()) {
            if (((EOScalar) adapter).isInsert()) {
                insert(adapter.getModel(), adapter.get());
                Object dbObject = find(adapter.getModel(), adapter.get());
                EO parentAdapter = ((EOScalar) adapter).getParentAdapter();
                String parentKey = ((EOScalar) adapter).getParentKey();
                if (parentAdapter != null) {
                    parentAdapter.remove(parentKey);
                    parentAdapter.add(parentKey).set(dbObject);
                } else {
                    //adapter.addList().set(dbObject);
                }
            } else {
                update(adapter.getModel(), adapter.get());
            }
        }
    }
}
