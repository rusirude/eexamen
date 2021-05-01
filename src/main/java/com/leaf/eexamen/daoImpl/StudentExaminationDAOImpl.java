package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.StudentExaminationDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.utility.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class StudentExaminationDAOImpl implements StudentExaminationDAO {

    @Autowired
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentExaminationEntity loadStudentExaminationEntity(long id) {
        return entityManager.getReference(StudentExaminationEntity.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentExaminationEntity findStudentExaminationEntity(long id) {
        return entityManager.find(StudentExaminationEntity.class, id);
    }

    @Override
    public void deleteStudentExaminationEntity(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<StudentExaminationEntity> criteriaQuery = criteriaBuilder.createCriteriaDelete(StudentExaminationEntity.class);
        Root<StudentExaminationEntity> root = criteriaQuery.from(StudentExaminationEntity.class);
        criteriaQuery.where(
                criteriaBuilder.equal(root.get(StudentExaminationEntity_.id), id)
        );

        entityManager.createQuery(criteriaQuery).executeUpdate();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveStudentExaminationEntity(StudentExaminationEntity studentExaminationEntity) {
        entityManager.persist(studentExaminationEntity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStudentExaminationEntity(StudentExaminationEntity studentExaminationEntity) {
        entityManager.merge(studentExaminationEntity);
    }

    @Override
    public <T> T getDataForGridForStudentBetweenSystemDate(DataTableRequestDTO dataTableRequestDTO, String type, List<String> status, Date systemDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationEntity.class);
        Root<StudentExaminationEntity> root = criteriaQuery.from(StudentExaminationEntity.class);
        criteriaQuery.select(root);
        CriteriaBuilder.In<String> codeIn = criteriaBuilder.in(root.get(StudentExaminationEntity_.statusEntity).get(StatusEntity_.code));
        status.forEach(codeIn::value);
        criteriaQuery.where(
                criteriaBuilder.and(
                        codeIn,
                        criteriaBuilder.equal(root.get(StudentExaminationEntity_.sysUserEntity).get(SysUserEntity_.username), dataTableRequestDTO.getSearch()),
                        criteriaBuilder.lessThanOrEqualTo(root.get(StudentExaminationEntity_.examinationEntity).get(ExaminationEntity_.effectiveOn), systemDate),
                        criteriaBuilder.greaterThanOrEqualTo(root.get(StudentExaminationEntity_.examinationEntity).get(ExaminationEntity_.expireOn), systemDate)
                )

        );

        criteriaQuery.orderBy(
                criteriaBuilder.asc(root.get(StudentExaminationEntity_.createdOn))
        );


        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<StudentExaminationEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }
    }


    @Override
    public <T> T getDataForGridForStudent(DataTableRequestDTO dataTableRequestDTO, String type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationEntity.class);
        Root<StudentExaminationEntity> root = criteriaQuery.from(StudentExaminationEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(), criteriaBuilder, root);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
                )

        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));


        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<StudentExaminationEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }
    }

    @Override
    public <T> T getDataForGridForStudentReport(DataTableRequestDTO dataTableRequestDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationEntity.class);
        Root<StudentExaminationEntity> root = criteriaQuery.from(StudentExaminationEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(), criteriaBuilder, root);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
                )

        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));


        return (T) entityManager.createQuery(criteriaQuery).getResultList();
    }
    private List<Predicate> createSearchPredicate(String searchValue, CriteriaBuilder cb, Root<StudentExaminationEntity> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!searchValue.isEmpty()) {
            predicates.add(cb.like(root.get(StudentExaminationEntity_.sysUserEntity).get(SysUserEntity_.name), "%" + searchValue + "%"));
            predicates.add(cb.like(root.get(StudentExaminationEntity_.examinationEntity).get(ExaminationEntity_.description), "%" + searchValue + "%"));
            predicates.add(cb.like(root.get(StudentExaminationEntity_.statusEntity).get(StatusEntity_.description), "%" + searchValue + "%"));
        } else {
            predicates.add(cb.conjunction());
        }
        return predicates;
    }

    private List<Order> createSortOrder(String sortColumnName, String sortOrder, CriteriaBuilder cb, Root<StudentExaminationEntity> root) {
        List<Order> orders = new ArrayList<>();

        Expression<?> ex = root.get(SysRoleEntity_.updatedOn);


        if ("student".equals(sortColumnName)) {
            ex = root.get(StudentExaminationEntity_.sysUserEntity).get(SysUserEntity_.name);
        } else if ("examination".equals(sortColumnName)) {
            ex = root.get(StudentExaminationEntity_.examinationEntity).get(ExaminationEntity_.description);
        } else if ("status".equals(sortColumnName)) {
            ex = root.get(StudentExaminationEntity_.statusEntity).get(StatusEntity_.description);
        }

        orders.add(("asc".equals(sortOrder)) ? cb.asc(ex) : cb.desc(ex));

        return orders;
    }


}
