package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.StudentDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.enums.*;
import com.leaf.eexamen.service.StudentService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import com.leaf.eexamen.utility.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {


	private SysUserDAO sysUserDAO;
	private SysRoleDAO sysRoleDAO;
	private StatusDAO statusDAO;
	private TitleDAO titleDAO;
	private CityDAO cityDAO;
	private StatusCategoryDAO statusCategoryDAO;
	private MasterDataDAO masterDataDAO;
	private SysUserAuthorityDAO sysUserAuthorityDAO;
	private SysUserSysRoleDAO sysUserSysRoleDAO;
	private StudentDAO studentDAO;
	private StudentExaminationDAO studentExaminationDAO;
	private EmailBodyDAO emailBodyDAO;
	private ExaminationDAO examinationDAO;
	private MailSenderService mailSenderService;

	private CommonMethod commonMethod;

	BCryptPasswordEncoder bCryptPasswordEncoder;


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<StudentDTO> saveStudent(StudentDTO studentDTO){
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Student Save Failed";
		SysUserEntity sysUserEntity;
		StudentEntity studentEntity;
		try {
			sysUserEntity = Optional.ofNullable(sysUserDAO.getSysUserEntityByUsername(studentDTO.getEmail())).orElse(new SysUserEntity());
			studentEntity = Optional.ofNullable(studentDAO.getStudentEntityByUsername(studentDTO.getEmail())).orElse(new StudentEntity());
			MasterDataEntity defaultPasswordMasterDataEntity = Optional.ofNullable(masterDataDAO.loadMasterDataEntity(MasterDataEnum.DEFAULT_PASSWORD.getCode())).orElse(new MasterDataEntity());
			MasterDataEntity studentRoleMasterDataEntity = Optional.ofNullable(masterDataDAO.loadMasterDataEntity(MasterDataEnum.STUDENT_ROLE.getCode())).orElse(new MasterDataEntity());
			EmailBodyEntity emailBodyEntity = emailBodyDAO.findEmailBodyEntityByCode(EmailEnum.EFSR.getCode());

			if(Optional.ofNullable(studentRoleMasterDataEntity.getValue()).orElse("").isEmpty()){
				description = "Configure Student Role in master Data";
			}
			else {
				if(sysUserEntity.getUsername() == null || DeleteStatusEnum.DELETE.getCode().equalsIgnoreCase(sysUserEntity.getStatusEntity().getCode())){

					TitleEntity titleEntity = titleDAO.findTitleEntityByCode(studentDTO.getTitleCode());
					CityEntity cityEntity = cityDAO.findCityEntityByCode(studentDTO.getCityCode());
					StatusEntity statusEntity = statusDAO.findStatusEntityByCode(studentDTO.getStatusCode());
					SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(studentRoleMasterDataEntity.getValue());

					sysUserEntity.setUsername(studentDTO.getEmail());
					sysUserEntity.setPassword(bCryptPasswordEncoder.encode(Optional.ofNullable(studentDTO.getPassword())
							.orElse(Optional.ofNullable(defaultPasswordMasterDataEntity.getValue())
									.orElse(""))));
					sysUserEntity.setTitleEntity(titleEntity);
					sysUserEntity.setName(studentDTO.getName());
					sysUserEntity.setStatusEntity(statusEntity);
					sysUserEntity.setResetRequest(false);
					sysUserEntity.setStudent(true);

					studentEntity.setUsername(studentDTO.getEmail());
					studentEntity.setInitialPassword(studentDTO.getPassword());
					studentEntity.setEmail(studentDTO.getEmail());
					studentEntity.setTelephone(studentDTO.getTelephone());
					studentEntity.setAddress(studentDTO.getAddress());
					studentEntity.setCompany(studentDTO.getCompany());
					studentEntity.setCityEntity(cityEntity);
					studentEntity.setZipCode(studentDTO.getZipCode());
					studentEntity.setVat(studentDTO.getVat());

					commonMethod.getPopulateEntityWhenInsert(sysUserEntity);
					commonMethod.getPopulateEntityWhenInsert(studentEntity);

					sysUserDAO.saveSysUserEntity(sysUserEntity);
					studentDAO.saveStudentEntity(studentEntity);

					SysUserSysRoleEntity sysUserSysRoleEntity = new SysUserSysRoleEntity();
					SysUserSysRoleEntityId id = new SysUserSysRoleEntityId();


					id.setSysUser(sysUserEntity.getUsername());
					id.setSysRole(sysRoleEntity.getId());

					sysUserSysRoleEntity.setSysUserSysRoleEntityId(id);
					sysUserSysRoleEntity.setSysUserEntity(sysUserEntity);
					sysUserSysRoleEntity.setSysRoleEntity(sysRoleEntity);


					sysUserSysRoleDAO.saveSysUserSysRoleEntity(sysUserSysRoleEntity);

					if(!Optional.ofNullable(studentDTO.getExamCode()).orElse("").isEmpty()){

						StatusEntity examStatusEntity = statusDAO.findStatusEntityByCode(ExamStatusEnum.PENDING.getCode());
						ExaminationEntity examinationEntity = examinationDAO.findExaminationEntityByCode(studentDTO.getExamCode());

						StudentExaminationEntity studentExaminationEntity = new StudentExaminationEntity();
						studentExaminationEntity.setSysUserEntity(sysUserEntity);
						studentExaminationEntity.setExaminationEntity(examinationEntity);
						studentExaminationEntity.setStatusEntity(examStatusEntity);

						commonMethod.getPopulateEntityWhenInsert(studentExaminationEntity);

						studentExaminationDAO.saveStudentExaminationEntity(studentExaminationEntity);

					}

					if(Optional.ofNullable(emailBodyEntity.getEnable()).orElse(false)){
						String subject = emailBodyEntity.getSubject()
								.replace("@Username",studentDTO.getEmail())
								.replace("@Password",studentDTO.getPassword());
						String content = emailBodyEntity.getContent()
								.replace("@Username",studentDTO.getEmail())
								.replace("@Password",studentDTO.getPassword());
						mailSenderService.sendEmailWithPlainText(studentDTO.getEmail(),subject, content);
					}
					code = ResponseCodeEnum.SUCCESS.getCode();
					description = "Student Save Successfully";


				}

				else {
					description = "Student is not Available in the System";
				}
			}



		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code,description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<StudentDTO> updateStudent(StudentDTO studentDTO) {
		
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Student Update Failed";
		try {
			TitleEntity titleEntity = titleDAO.findTitleEntityByCode(studentDTO.getTitleCode());
			CityEntity cityEntity = cityDAO.findCityEntityByCode(studentDTO.getCityCode());
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(studentDTO.getStatusCode());

			SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(studentDTO.getEmail());
			StudentEntity studentEntity = studentDAO.getStudentEntityByUsername(studentDTO.getEmail());

			sysUserEntity.setTitleEntity(titleEntity);
			sysUserEntity.setName(studentDTO.getName());
			sysUserEntity.setStatusEntity(statusEntity);


			studentEntity.setEmail(studentDTO.getEmail());
			studentEntity.setTelephone(studentDTO.getTelephone());
			studentEntity.setEmail(studentDTO.getEmail());
			studentEntity.setTelephone(studentDTO.getTelephone());
			studentEntity.setAddress(studentDTO.getAddress());
			studentEntity.setCompany(studentDTO.getCompany());
			studentEntity.setCityEntity(cityEntity);
			studentEntity.setZipCode(studentDTO.getZipCode());
			studentEntity.setVat(studentDTO.getVat());

			commonMethod.getPopulateEntityWhenUpdate(sysUserEntity);
			commonMethod.getPopulateEntityWhenUpdate(studentEntity);

			sysUserDAO.updateSysUserEntity(sysUserEntity);
			studentDAO.updateStudentEntity(studentEntity);
			
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Student Update Successfully";
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<StudentDTO> deleteStudent(StudentDTO studentDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Student Delete Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());			

			SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(studentDTO.getEmail());
			StudentEntity studentEntity = studentDAO.getStudentEntityByUsername(studentDTO.getEmail());
		
			sysUserAuthorityDAO.deleteSysUserAuthorityEntityBySysUser(studentDTO.getEmail());
			
			sysUserEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(sysUserEntity);
			commonMethod.getPopulateEntityWhenUpdate(studentEntity);

			sysUserDAO.updateSysUserEntity(sysUserEntity);
			studentDAO.updateStudentEntity(studentEntity);
			
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Student Delete Successfully";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<StudentDTO> findStudent(StudentDTO studentDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Student is Not Found";
		StudentDTO dto = new StudentDTO();
		try {

			SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(studentDTO.getEmail());
			StudentEntity studentEntity = studentDAO.getStudentEntityByUsername(studentDTO.getEmail());

			if (sysUserEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(sysUserEntity.getStatusEntity().getCode())) {
				
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Student is Found Successfully";
				
				dto.setUsername(sysUserEntity.getUsername());
				dto.setTitleCode(sysUserEntity.getTitleEntity().getCode());
				dto.setTitleDescription(sysUserEntity.getTitleEntity().getDescription());
				dto.setName(sysUserEntity.getName());
				dto.setEmail(studentEntity.getEmail());
				dto.setTelephone(studentEntity.getTelephone());
				dto.setAddress(studentEntity.getAddress());
				dto.setCompany(studentEntity.getCompany());
				dto.setCityCode(studentEntity.getCityEntity().getCode());
				dto.setCityDescription(studentEntity.getCityEntity().getDescription());
				dto.setZipCode(studentEntity.getZipCode());
				dto.setVat(studentEntity.getVat());
				dto.setStatusCode(sysUserEntity.getStatusEntity().getCode());
				dto.setStatusDescription(sysUserEntity.getStatusEntity().getDescription());
				dto.setCreatedBy(sysUserEntity.getCreatedBy());
				dto.setCreatedOn(sysUserEntity.getCreatedOn());
				dto.setUpdatedBy(sysUserEntity.getUpdatedBy());
				dto.setUpdatedOn(sysUserEntity.getUpdatedOn());

			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description, dto);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForStudent() {

		HashMap<String, Object> map = new HashMap<>();
		String code = ResponseCodeEnum.FAILED.getCode();
		try {
			List<?> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
					.getStatusEntities().stream()
					.sorted(Comparator.comparing(StatusEntity::getDescription)).map(s -> new DropDownDTO(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());
			
			List<?> title = titleDAO.findAllTitleEntities(DefaultStatusEnum.ACTIVE.getCode())
					.stream().map(t-> new DropDownDTO<>(t.getCode(), t.getDescription()))
					.collect(Collectors.toList());
			List<?> examination = examinationDAO.findAllExaminationEntities(DefaultStatusEnum.ACTIVE.getCode())
					.stream().map(t-> new DropDownDTO<>(t.getCode(), t.getDescription()))
					.collect(Collectors.toList());
			List<?> city = cityDAO.findAllCityEntities(DefaultStatusEnum.ACTIVE.getCode())
					.stream()
					.sorted(Comparator.comparing(CityEntity::getDescription))
					.map(t-> new DropDownDTO<>(t.getCode(), t.getDescription()))
					.collect(Collectors.toList());


			map.put("status", status);
			map.put("title", title);
			map.put("examination", examination);
			map.put("city", city);

			code = ResponseCodeEnum.SUCCESS.getCode();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, map);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public DataTableResponseDTO getStudentsForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<StudentDTO> list = new ArrayList<>();
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord = Long.valueOf(0);
		try {
			list = studentDAO.<List<SysUserEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						StudentDTO dto = new StudentDTO();
						dto.setEmail(entity.getUsername());
						dto.setTitleDescription(entity.getTitleEntity().getDescription());
						dto.setName(entity.getName());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = studentDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

			responseDTO.setData(list);
			responseDTO.setRecordsTotal(numOfRecord);
			responseDTO.setRecordsFiltered(numOfRecord);
			responseDTO.setDraw(dataTableRequestDTO.getDraw());

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return responseDTO;
	}




}
