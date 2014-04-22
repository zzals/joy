package com.se.joy.model;

public class LoginVO
{
  private String idx;
  private String m_id;
  private String m_pwd;
  private String m_nm;
  private String birth;
  private String mail;
  private String mobile;
  private String school_grade_cd;
  private String school_cd;
  private String grade;
  private String auth;
  private boolean isLogin;
  
  public String getIdx()
  {
    return this.idx;
  }
  
  public void setIdx(String idx)
  {
    this.idx = idx;
  }
  
  public String getM_id()
  {
    return this.m_id;
  }
  
  public void setM_id(String m_id)
  {
    this.m_id = m_id;
  }
  
  public String getM_pwd()
  {
    return this.m_pwd;
  }
  
  public void setM_pwd(String m_pwd)
  {
    this.m_pwd = m_pwd;
  }
  
  public String getM_nm()
  {
    return this.m_nm;
  }
  
  public void setM_nm(String m_nm)
  {
    this.m_nm = m_nm;
  }
  
  public String getBirth()
  {
    return this.birth;
  }
  
  public void setBirth(String birth)
  {
    this.birth = birth;
  }
  
  public String getMail()
  {
    return this.mail;
  }
  
  public void setMail(String mail)
  {
    this.mail = mail;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getAuth()
  {
    return this.auth;
  }
  
  public void setAuth(String auth)
  {
    this.auth = auth;
  }
  
  public boolean isLogin()
  {
    return this.isLogin;
  }
  
  public void setLogin(boolean isLogin)
  {
    this.isLogin = isLogin;
  }
  
  public String getSchool_grade_cd()
  {
    return this.school_grade_cd;
  }
  
  public void setSchool_grade_cd(String school_grade_cd)
  {
    this.school_grade_cd = school_grade_cd;
  }
  
  public String getSchool_cd()
  {
    return this.school_cd;
  }
  
  public void setSchool_cd(String school_cd)
  {
    this.school_cd = school_cd;
  }
  
  public String getGrade()
  {
    return this.grade;
  }
  
  public void setGrade(String grade)
  {
    this.grade = grade;
  }
}
