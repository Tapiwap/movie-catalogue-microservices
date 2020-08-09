package com.tutorials.micro.moviecatalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueItem {

    private String name, description;
    private int rating;
}
