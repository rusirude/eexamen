package com.leaf.eexamen.utility;

import com.leaf.eexamen.entity.CommonEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Component
public class CommonMethod {
    public Date getSystemDate2() {
        return new Date();
    }

    public Date getSystemDate() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.of(CommonConstant.DATE_TIMEZONE_JACKSON));
        Calendar ret = new GregorianCalendar(timeZone);
        ret.setTimeInMillis(calendar.getTimeInMillis() +
                timeZone.getOffset(calendar.getTimeInMillis()) -
                TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
        return ret.getTime();
    }

    public Date addHoursAndMinutesToDate(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public String getUsername() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    public String dateTimeToString(Date date,String format){
        return new SimpleDateFormat(format).format(date);
    }

    public String dateTimeToString(Date date){
        return new SimpleDateFormat(CommonConstant.SYSTEM_DATE_TIME_FORMAT).format(date);
    }
    public Date stringToDateTime(String date) throws ParseException {
        return new SimpleDateFormat(CommonConstant.SYSTEM_DATE_TIME_FORMAT).parse(date);
    }

    public <T extends CommonEntity> void getPopulateEntityWhenInsert(T t) {
        t.setCreatedBy(this.getUsername());
        t.setCreatedOn(this.getSystemDate());
        t.setUpdatedBy(this.getUsername());
        t.setUpdatedOn(this.getSystemDate());
    }

    public <T extends CommonEntity> void getPopulateEntityWhenUpdate(T t) {
        t.setUpdatedBy(this.getUsername());
        t.setUpdatedOn(this.getSystemDate());
    }

    public <T extends CommonEntity> void getPopulateEntityWhenInsert(T t, String username) {
        t.setCreatedBy(this.getUsername());
        t.setCreatedOn(this.getSystemDate());
        t.setUpdatedBy(this.getUsername());
        t.setUpdatedOn(this.getSystemDate());
    }

    public <T extends CommonEntity> void getPopulateEntityWhenUpdate(T t, String username) {
        t.setUpdatedBy(this.getUsername());
        t.setUpdatedOn(this.getSystemDate());
    }
}
