package com.vtu.api.model.entity;

import com.vtu.api.model.entity.enums.NetworkProvider;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airtime_topup")
public class AirtimeTopup extends XpressPayEntity {

    @Column(name = "network_provider")
    @Enumerated(EnumType.STRING)
    private NetworkProvider networkProvider;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;
}
