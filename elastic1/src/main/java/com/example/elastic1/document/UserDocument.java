package com.example.elastic1.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "users")
public class UserDocument {
    
    // "_id":"j1UoEZgBsJ0ey9K8qtGT"
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String name;
    
    @Field(type = FieldType.Long)
    private Long age;

    @Field(type = FieldType.Boolean)
    private Boolean isActive;

}
