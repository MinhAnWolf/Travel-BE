package com.vocation.travel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UploadImage {
  private String data;
  private final String type = "image/png";
  private String name;
}
