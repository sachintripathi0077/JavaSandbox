package com.eazybytes.eazyschool.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Component("eazySchoolProps")
@Data
@ConfigurationProperties(prefix = "eazyschool")
@Validated
public class EazySchoolProps {

    @Min(value = 5, message = "Page size must be GREATER than or equal to 5")
    @Max(value = 25, message = "Page size must be LESS than or equal to 25")
    private int pageSize;

    private Map<String,String> contact;

    private List<String> branches;
}
