package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.StudentDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(path = "/student")
public class StudentController {

	private StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewSysRole() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("student");
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/loadRefDataForStudent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadStudentRefereceData() {
		return studentService.getReferenceDataForStudent();
	}
	
	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveStudent(@RequestBody StudentDTO studentDTO) {
		return studentService.saveStudent(studentDTO);
	}
	
	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateStudent(@RequestBody StudentDTO studentDTO) {
		return studentService.updateStudent(studentDTO);
	}

	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> deleteStudent(@RequestBody StudentDTO studentDTO) {
		return studentService.deleteStudent(studentDTO);
	}
	
	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/loadStudents", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadStudentsDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return studentService.getStudentsForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_STUD')")
	@RequestMapping(path = "/loadStudentByUsername", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<StudentDTO> loadStudentByUsername(@RequestBody StudentDTO studentDTO) {
		return studentService.findStudent(studentDTO);
	}
}
