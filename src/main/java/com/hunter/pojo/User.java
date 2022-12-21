package com.hunter.pojo;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class User {
    private Integer id;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户名称
     */
    private String userName;

    private String userPassword;
    private Integer gender;
    private Date birthday;

    private String phone;
    private String address;

    /**
     * 用户角色
     */
    private Integer userRole;

    /**
     * 创建者
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新者
     */
    private Integer modifyBy;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 用户角色名称
     */
    private String userRoleName;

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getAge() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int birthYear = calendar.get(Calendar.YEAR);
        return currentYear - birthYear;
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return this.userName.equals(user.getUserName()) && this.gender.equals(user.getGender())
                && this.birthday.equals(user.getBirthday()) && this.phone.equals(user.getPhone())
                && this.address.equals(user.getAddress()) && this.userRole.equals(user.getUserRole());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("userName: ").append(this.userName).append("\n");
        sb.append("gender: ").append(this.gender).append("\n");
        sb.append("birthday: ").append(this.birthday).append("\n");
        sb.append("phone: ").append(this.phone).append("\n");
        sb.append("address: ").append(this.address).append("\n");
        sb.append("userRole: ").append(this.userRole).append("\n");
        return String.valueOf(sb);
    }
}