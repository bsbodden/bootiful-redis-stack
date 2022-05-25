package com.redis.stack.demo.models.json;

import org.springframework.data.annotation.Id;

import com.redis.om.spring.annotations.Document;
import com.google.gson.annotations.SerializedName;
import com.redis.om.spring.annotations.AutoComplete;
import com.redis.om.spring.annotations.AutoCompletePayload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Document
public class CharacterEntry {
  @Id
  private String id;

  @AutoComplete @NonNull
  private String name;

  @AutoCompletePayload("name")
  private String type;

  @AutoCompletePayload("name")
  @SerializedName("first appearance")
  private String firstAppearance;

  @AutoCompletePayload("name") @NonNull
  private Integer appearances;

  private String eye;
  private String hair;
  private String sex;
  private Integer year;
}
