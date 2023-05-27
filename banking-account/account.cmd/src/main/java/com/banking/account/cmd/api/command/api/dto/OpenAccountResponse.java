package com.banking.account.cmd.api.command.api.dto;

import com.banking.account.cmd.common.dto.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OpenAccountResponse extends BaseResponse {

	private String id;

	public OpenAccountResponse(String message, String id) {
		super(message);
		this.id = id;
	}

}
