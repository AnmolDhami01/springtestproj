package com.newSpring.testApp.ResponseEntinty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusDescription {
    private int statusCode;
    private String statusDescription;

}
