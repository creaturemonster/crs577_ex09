package com.gdr.forex;

public enum Currency {
    USD("USA", "Dollar"),
    EUR("European Union", "Euro"),
    SEK("Sweden", "Kroner"),
    JPY("Japan", "Yen"),
    INR("India", "Rupee");
    
    private String country;
    private String name;
    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    private Currency(String country, String name) {
        this.country = country;
        this.name = name;
    }
    
}
