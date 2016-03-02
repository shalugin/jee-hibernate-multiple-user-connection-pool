package ru.lanit;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.lanit.pool.HibernateUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
@Slf4j
public class MyRepo {

    @Inject
    private RequestData requestData;

    public RefReputationSource getData() {
        log.info("Login for DB: {}", requestData.getLogin());

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        log.info("{}", sessionFactory);

        RefReputationSource refReputationSource = new RefReputationSource();
        refReputationSource.setName(requestData.getLogin());

        try (Session session = sessionFactory.openSession()) {
            SQLQuery sqlQuery = session.createSQLQuery("SELECT now()");
            List list = sqlQuery.list();
            log.info("{}", list);
            refReputationSource.setName(refReputationSource.getName() + " " + list.get(0).toString());
        }

        return refReputationSource;
    }
}