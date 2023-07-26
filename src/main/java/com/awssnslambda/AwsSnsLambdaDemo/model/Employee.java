package com.awssnslambda.AwsSnsLambdaDemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	 private Integer empId;
	    private String name;
	    private String emailId;
}
