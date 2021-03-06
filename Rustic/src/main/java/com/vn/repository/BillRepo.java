package com.vn.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vn.jpa.AuthUser;
import com.vn.jpa.Bill;

@Repository(value = "billRepo")
public interface BillRepo extends JpaRepository<Bill, Long> {

	@Query(value = "SELECT b FROM Bill b WHERE (b.createDate BETWEEN :fromDate AND :toDate)"
			+ "AND (:status = 0 OR b.status = :status)" + "AND (:code IS NULL OR :code = '' OR b.code = :code)"
			+ "AND (b.isDelete = :delete)", nativeQuery = false)
	Page<Bill> findAllBillParam(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
			@Param("status") Integer status, @Param("code") String code, @Param("delete") String isDelete,
			Pageable pageable);

	@Query(value = "SELECT b FROM Bill b WHERE (b.createDate BETWEEN :fromDate AND :toDate)"
			+ "AND (b.typeStatus = :typeStatus)" + "AND (b.isDelete = :delete)", nativeQuery = false)
	Page<Bill> findAllBillTypeParam(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
			@Param("typeStatus") Integer typeStatus, @Param("delete") String isDelete, Pageable pageable);

	@Query(value = "SELECT b FROM Bill b WHERE (b.createDate BETWEEN :fromDate AND :toDate)"
			+ "AND (b.shipper = :shipper)" + "AND (b.isDelete = :delete)", nativeQuery = false)
	Page<Bill> findAllBillByShip(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
			@Param("shipper") AuthUser shipper, @Param("delete") String isDelete, Pageable pageable);

	@Query(value = "SELECT b.id FROM Bill b " + " WHERE (b.code = :code)", nativeQuery = false)
	Long checkExistByCode(@Param(value = "code") String code);

	Bill findByCode(String code);

	List<Bill> findByTypeStatusAndMailStatus(Integer type, Integer statusMail);

	@Query(value = "SELECT b.createDate, SUM(b.total) FROM Bill b "
			+ "WHERE (b.createDate BETWEEN :fromDate AND :toDate)"
			+ "AND (b.isDelete = 'N') AND (b.status = 1)" + "GROUP BY b.createDate", nativeQuery = false)
	List<Object[]> listSumTotalForDashboard(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	@Query(value = "SELECT b.createDate, b.code, b.total, b.typeStatus, b.status FROM Bill b "
			+ "WHERE b.authUser.id = :id")
	Page<Object[]> pageBillForShowProfileUser(@Param("id") Long id, Pageable pageable);

}
