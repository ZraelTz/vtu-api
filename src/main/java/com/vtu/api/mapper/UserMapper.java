package com.vtu.api.mapper;

import com.vtu.api.dto.request.UserRegistrationRequest;
import com.vtu.api.model.entity.User;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper extends DefaultMapper {

    @Named("toLowercase")
    static String toLowercase(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }

        return param.toLowerCase();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", qualifiedByName = "toLowercase")
    @Mapping(source = "password", target = "password", ignore = true)
    User createUser(UserRegistrationRequest request);
}
