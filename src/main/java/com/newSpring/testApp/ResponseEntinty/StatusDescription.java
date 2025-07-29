package com.newSpring.testApp.ResponseEntinty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDescription {
    private int statusCode;
    private String statusDescription;

}
