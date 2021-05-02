package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.EmailBodyDAO;
import com.leaf.eexamen.entity.EmailBodyEntity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailBodyDAOImpl implements EmailBodyDAO {

    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailBodyEntity findEmailBodyEntityByCode(String code) {
        return entityManager.find(EmailBodyEntity.class, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveEmailBodyEntity(EmailBodyEntity emailBodyEntity) {
        entityManager.persist(emailBodyEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEmailBodyEntity(EmailBodyEntity emailBodyEntity) {
        entityManager.merge(emailBodyEntity);
    }

    @Override
    public List<EmailBodyEntity> findAllEmailBodyEntities() {
        List<EmailBodyEntity> emailBodyEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmailBodyEntity> criteriaQuery = criteriaBuilder.createQuery(EmailBodyEntity.class);
        Root<EmailBodyEntity> root = criteriaQuery.from(EmailBodyEntity.class);
        criteriaQuery.select(root);


        try {
            emailBodyEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return emailBodyEntities;
    }
}
