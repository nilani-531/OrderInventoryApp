package com.sprintProject.orderinventoryapplication.dto.responseDto;

public class ResponseStructure<T> 
{
private int status;
private String msg;

private T data;//T means here as all datatype accepted 



public int getStatus() {
	return status;
}                 
public void setStatus(int status) {
	this.status = status;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public T getData() {
	return data;
}
public void setData(T data) {
	this.data = data;
}
}


