package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.dto.request.SwiftCodeRequest;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeByCountryResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodeDetailResponse;
import com.github.hkereb.swiftcodeapi.dto.response.SwiftCodePartialResponse;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SwiftCodeMapper {

    public SwiftCode mapToEntity(SwiftCodeRequest request) {
        SwiftCode code = new SwiftCode();

        code.setSwiftCode(request.getSwiftCode());
        code.setBankName(request.getBankName());
        code.setBankAddress(request.getAddress());
        code.setCountryISO2(request.getCountryISO2());
        code.setCountryName(request.getCountryName());
        code.setIsHeadquarter(request.isHeadquarter());

        // omitted in request
        code.setCodeType(null);
        code.setTimeZone(null);

        return code;
    }

    public SwiftCodePartialResponse mapToPartialResponse(SwiftCode entity) {
        return new SwiftCodePartialResponse(
                entity.getBankAddress(),
                entity.getBankName(),
                entity.getCountryISO2(),
                Boolean.TRUE.equals(entity.getIsHeadquarter()),
                entity.getSwiftCode()
        );
    }

    public SwiftCodeDetailResponse mapToResponse(SwiftCode entity, List<SwiftCode> branches) {
        List<SwiftCodePartialResponse> branchResponses = branches.stream()
                .map(this::mapToPartialResponse)
                .collect(Collectors.toList());

        return new SwiftCodeDetailResponse(
                entity.getBankAddress(),
                entity.getBankName(),
                entity.getCountryISO2(),
                entity.getCountryName(),
                Boolean.TRUE.equals(entity.getIsHeadquarter()),
                entity.getSwiftCode(),
                branchResponses
        );
    }

    public  SwiftCodeDetailResponse mapToResponse(SwiftCode entity) {
        return mapToResponse(entity, Collections.emptyList());
    }

    public SwiftCodeByCountryResponse mapToByCountryResponse(String countryISO2, String countryName, List<SwiftCode> codes) {
        List<SwiftCodePartialResponse> responses = codes.stream()
                .map(this::mapToPartialResponse)
                .collect(Collectors.toList());

        return new SwiftCodeByCountryResponse(
                countryISO2,
                countryName,
                responses
        );
    }

}