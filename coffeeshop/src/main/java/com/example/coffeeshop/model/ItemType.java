package com.example.coffeeshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum ItemType {
  COFFEE(105, 2),
  TEA(107, 2),
  CAKE(215, 2),
  UNKNOWN(0,2);

  final long price;
  final int scale;
}
