package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MasterDataDTO extends CommonDTO {
	
	private String code;
	private String value;
}
