package com.vtu.api.mapper;

import com.vtu.api.dto.request.AirtimeTopupRequest;
import com.vtu.api.dto.request.XpressPayAirtimeTopupRequest;
import com.vtu.api.dto.response.AirtimeTopupResponse;
import com.vtu.api.model.entity.AirtimeTopup;
import com.vtu.api.model.entity.enums.NetworkProvider;
import io.micrometer.common.util.StringUtils;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AirtimeTopupMapper extends DefaultMapper {

    @Named("toNetworkProviderEnum")
    static NetworkProvider toNetworkProviderEnum(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }

        return NetworkProvider.valueOf(param);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "networkProvider", qualifiedByName = "toNetworkProviderEnum")
    AirtimeTopup createAirtimeTopup(AirtimeTopupRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    XpressPayAirtimeTopupRequest.TopupDetails createTopupDetails(AirtimeTopupRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AirtimeTopupResponse createTopupResponse(AirtimeTopupRequest request);

    default XpressPayAirtimeTopupRequest createApiRequest(AirtimeTopupRequest request) {
        XpressPayAirtimeTopupRequest.TopupDetails topupDetails = createTopupDetails(request);
        return XpressPayAirtimeTopupRequest.builder()
                .topupDetails(topupDetails)
                .uniqueCode(NetworkProvider.valueOf(request.getNetworkProvider()).getUniqueCode())
                .build();
    }


}
