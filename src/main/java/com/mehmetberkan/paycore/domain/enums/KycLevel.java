package com.mehmetberkan.paycore.domain.enums;

public enum KycLevel {
    LEVEL_0, // sadece kayıt olmuş kullanıcı
    LEVEL_1, // temel doğrulama
    LEVEL_2, // kimlik doğrulama
    LEVEL_3  // tam doğrulama
}
