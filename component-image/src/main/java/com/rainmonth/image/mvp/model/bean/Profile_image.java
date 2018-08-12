/**
  * Copyright 2018 bejson.com 
  */
package com.rainmonth.image.mvp.model.bean;

import java.io.Serializable;

/**
 * Auto-generated: 2018-08-11 14:16:51
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Profile_image implements Serializable{

    private String small;
    private String medium;
    private String large;
    public void setSmall(String small) {
         this.small = small;
     }
     public String getSmall() {
         return small;
     }

    public void setMedium(String medium) {
         this.medium = medium;
     }
     public String getMedium() {
         return medium;
     }

    public void setLarge(String large) {
         this.large = large;
     }
     public String getLarge() {
         return large;
     }

}