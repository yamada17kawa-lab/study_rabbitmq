package com.nuliyang.consumer.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    private Long id;

    private String msg;

    private People people;

}
