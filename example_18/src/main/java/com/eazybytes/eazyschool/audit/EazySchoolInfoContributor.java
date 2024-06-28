package com.eazybytes.eazyschool.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EazySchoolInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> eazyMap = new HashMap<>();
        eazyMap.put("App Name","EazySchool");
        eazyMap.put("Description","Eazy School Web Application for students and admins");
        eazyMap.put("Version","1.0.0");
        eazyMap.put("Contact Email","info@eazyschool.com");
        eazyMap.put("Mobile Number","9008007006");
        builder.withDetail("eazyschool-info",eazyMap);
    }
}
